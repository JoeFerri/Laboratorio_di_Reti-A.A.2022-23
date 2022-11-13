/**
 * @author Giuseppe Ferri
 */
package assignment_6;


import static com.gf.utils.Utils.printflushDebug;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.gf.utils.FlagSingleArgumentConsumer;
import com.gf.utils.Utils;

import assignment_6.model.ActuatorMatch;
import assignment_6.model.Database;
import assignment_6.model.Flags;
import assignment_6.model.IdResponse;
import assignment_6.model.Images;
import assignment_6.model.KeyPath;
import assignment_6.model.LoginRecord;
import assignment_6.model.LoginUserRecord;
import assignment_6.model.Monster;
import assignment_6.model.Paths;
import assignment_6.model.Player;
import assignment_6.model.UserRecord;


/**
 * L'istanza di tipo Server viene avviata da un processo separato
 * dal processo principale.
 */
public class Server {

  // porta di ascolto: range
  public static final int PORT_MIN = 3000;
  public static final int PORT_MAX = 4000;

  public static final int THREADS_POOL_MAX = 50;

  // valori minimi e massimi per la generazione casuale
  // della vita di giocatore e mostri
  public static final int HEALTH_PLAYER_MIN = 900;
  public static final int HEALTH_PLAYER_MAX = 1000;
  public static final int POTION_PLAYER_MIN = 400;
  public static final int POTION_PLAYER_MAX = 500;
  public static final int HEALTH_MONSTER_MIN = HEALTH_PLAYER_MIN + 100;
  public static final int HEALTH_MONSTER_MAX = HEALTH_PLAYER_MAX + 100;

  // dopo ogni partita vinta, il successivo mostro ha un livello superiore
  public static final float FACTOR_LEVEL = 0.1f;  // fattore di crescita della vita
                                                  // dei mostri rispetto al loro livello
  
  // valori minimi e massimi per la riduzione della vita durante il combattimento
  public static final int HEALTH_FIGHT_PLAYER_MIN = 30;
  public static final int HEALTH_FIGHT_PLAYER_MAX = 80;
  public static final int HEALTH_FIGHT_MONSTER_MIN = 20;
  public static final int HEALTH_FIGHT_MONSTER_MAX = 90;

  /**
   * Una istanza ServerListener elabora una singola richiesta
   * da parte del client.
   */
  private class ServerListener implements Runnable {

    Server server = null;
    Socket socket = null;

    ServerListener (Server server, Socket socket) {
      printflushDebug("ServerListener.new()...\n");
      this.server = server;
      this.socket = socket;
    }

    /**
     * Inizializza lo stato del gioco a inizio partita
     * o dopo la creazione di un nuovo utente con il login new.
     */
    private UserRecord initMatch (UserRecord userRecord, int level) {
      int maxHealthPlayer = Utils.getRndm(HEALTH_PLAYER_MIN,HEALTH_PLAYER_MAX);
      int maxPotion = Utils.getRndm(POTION_PLAYER_MIN,POTION_PLAYER_MAX);
      int healthMonsterMin = HEALTH_MONSTER_MIN + (int)(HEALTH_MONSTER_MIN * FACTOR_LEVEL * level);
      int healthMonsterMax = HEALTH_MONSTER_MAX + (int)(HEALTH_MONSTER_MAX * FACTOR_LEVEL * level);
      int maxHealthMonster = Utils.getRndm(healthMonsterMin,healthMonsterMax);
      String[] paths = Images.getPathsByType("monster");
      String imagePathMonster = paths[Utils.getRndm(0,paths.length-1)]; // "../images/monsters/ghost.png"

      return new UserRecord(IdResponse.UNDEFINED.index,
                            new Player( userRecord.player.name,
                                        maxHealthPlayer,
                                        maxHealthPlayer,
                                        userRecord.player.imagePath,
                                        maxPotion,
                                        maxPotion),
                            new Monster("",
                                        maxHealthMonster,
                                        maxHealthMonster,
                                        imagePathMonster,
                                        level),
                            userRecord.matches != -1 ? userRecord.matches : 0,
                            userRecord.won != -1 ? userRecord.won : 0,
                            userRecord.lost != -1 ? userRecord.lost : 0,
                            1,
                            userRecord.message);
    }

    
    /**
     * Nuovo stato di gioco dopo l'abbandono o la chiusura della finestra di gioco.
     */
    private UserRecord abandonsMatch (UserRecord userRecord) {
      return  new UserRecord( IdResponse.UNDEFINED.index,
                              new Player (userRecord.player.name,
                                          0,
                                          userRecord.player.maxHealth,
                                          userRecord.player.imagePath,
                                          0,
                                          userRecord.player.maxPotion),
                              userRecord.monster,
                              userRecord.matches +1,
                              userRecord.won,
                              userRecord.lost +1,
                              1,
                              "ABANDONS");
    }

    /**
     * Nuovo stato di gioco dopo la richiesta di combattimento.
     */
    private UserRecord fightMatch (UserRecord userRecord) {
      int healthPlayer = userRecord.player.getHealth() - Utils.getRndm(HEALTH_FIGHT_PLAYER_MIN,HEALTH_FIGHT_PLAYER_MAX);
      if (healthPlayer < 0) healthPlayer = 0;
      int healthMonster = userRecord.monster.getHealth() - Utils.getRndm(HEALTH_FIGHT_MONSTER_MIN,HEALTH_FIGHT_MONSTER_MAX);
      if (healthMonster < 0) healthMonster = 0;
      int matches = userRecord.matches, won = userRecord.won, lost = userRecord.lost;
      int level = userRecord.monster.level;
      if (healthPlayer == 0) {
        if (healthMonster == 0) // pareggio
          matches++;
        else { // sconfitta
          matches++;
          lost++;
        }
      }
      else if (healthMonster == 0) { // vittoria
        matches++;
        won++;
        level++;
      }
      return new UserRecord(IdResponse.UNDEFINED.index,
                            new Player( userRecord.player.name,
                                        healthPlayer,
                                        userRecord.player.maxHealth,
                                        userRecord.player.imagePath,
                                        userRecord.player.getPotion(),
                                        userRecord.player.maxPotion),
                            new Monster("",
                                        healthMonster,
                                        userRecord.monster.maxHealth,
                                        userRecord.monster.imagePath,
                                        level),
                            matches,
                            won,
                            lost,
                            userRecord.round +1,
                            "FIGHT");
    }

    /**
     * Nuovo stato di gioco dopo la richiesta di bevuta pozione.
     */
    private UserRecord drinkPotionMatch (UserRecord userRecord) {
      int potion = 0;
      int potionPlayer = userRecord.player.getPotion();
      int healthPlayer = userRecord.player.getHealth();
      if (potionPlayer != 0) {
        potion = Utils.getRndm(HEALTH_FIGHT_PLAYER_MIN,Math.min(HEALTH_FIGHT_PLAYER_MAX,potionPlayer));
        potionPlayer = userRecord.player.getPotion() - potion;
        if (potionPlayer < 0) potionPlayer = 0;
        healthPlayer = userRecord.player.getHealth() + potion;
        if (healthPlayer > userRecord.player.maxHealth) healthPlayer = userRecord.player.maxHealth;
      }
      return new UserRecord(IdResponse.UNDEFINED.index,
                            new Player( userRecord.player.name,
                                        healthPlayer,
                                        userRecord.player.maxHealth,
                                        userRecord.player.imagePath,
                                        potionPlayer,
                                        userRecord.player.maxPotion),
                            userRecord.monster,
                            userRecord.matches,
                            userRecord.won,
                            userRecord.lost,
                            userRecord.round +1,
                            "DRINK_POTION");
    }

    /**
     * Funzione generale per il cambio di stato del gioco.
     */
    // match("ABANDONS",userRecord,id_tokens[1],out,actuator)
    private UserRecord match (String action, UserRecord userRecord, String id_token, PrintWriter out, ActuatorMatch actuator)
      throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IOException,
                IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException, InvalidAlgorithmParameterException{

      printflushDebug("ServerListener -> %s\n",action);
      LoginUserRecord loginUserRecord = LoginUserRecord.fromDataServer(id_token);
      LoginRecord login = loginUserRecord.login;
      userRecord = loginUserRecord.record;
      printflushDebug("ServerListener -> login: <%s> userRecord: <%s>\n",login,userRecord);
      
      userRecord = actuator.apply(userRecord);

      printflushDebug("ServerListener -> userRecord: <%s>\n",userRecord);
      this.server.database.addRecord(login,userRecord);
      String output = IdResponse.OK.index + ":" + userRecord.toDataServer();
      printflushDebug("ServerListener -> server invia: <%s>\n",output);
      printflushDebug("ServerListener -> idResponse: OK-%d\n",IdResponse.OK.index);
      out.println(output);
      printflushDebug("ServerListener -> server ha inviato: <%s>\n",output);

      return userRecord;
    }



    @Override
    public void run() {
      printflushDebug("ServerListener.run()...\n");
      try (Scanner in = new Scanner(this.socket.getInputStream());
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(),true)) {

        while (this.server.isOn() && in.hasNextLine()) {
          printflushDebug("ServerListener -> in.nextLine()...\n");
          String data = in.nextLine();
          printflushDebug("ServerListener -> server get: <%s>\n",data);

          // TODO gestire gli input errati
          String[] id_tokens = data.split(":");
          
          int idResponse = Integer.parseInt(id_tokens[0]);
          printflushDebug("ServerListener -> idResponse: %d\n",idResponse);
          
          String[] tokens                 = null;
          String username                 = null;
          String password                 = null;
          LoginUserRecord loginUserRecord = null;
          LoginRecord login               = null;
          UserRecord userRecord           = null;
          String output                   = null;



          switch (IdResponse.byIndex(idResponse)) {

            // creazione di un nuovo login
            case LOGIN_SAVE:
              printflushDebug("ServerListener -> LOGIN_SAVE\n");
              tokens = id_tokens[1].split(",");
              username = tokens[0];
              password = tokens[1];
              login = new LoginRecord(username,password);
              printflushDebug("ServerListener -> login: <%s>\n",login);

              if (this.server.database.getLogin(username) == null) {
                printflushDebug("ServerListener -> login non presente\n");
                this.server.database.addLogin(login);
                userRecord = UserRecord.changeNamePlayer(UserRecord.EMPTY_RECORD,username);
                userRecord.message = "LOGIN_SAVE";
                this.server.database.addRecord(login,userRecord);
                output = IdResponse.OK.index + ":" + userRecord.toDataServer();
                printflushDebug("ServerListener -> server invia: <%s>\n",output);
                printflushDebug("ServerListener -> idResponse: OK-%d\n",IdResponse.OK.index);
                out.println(output);
                printflushDebug("ServerListener -> server ha inviato: <%s>\n",output);
              }
              else {
                printflushDebug("ServerListener -> idResponse: LOGIN_EXISTS-%d\n",IdResponse.LOGIN_EXISTS.index);
                output = IdResponse.LOGIN_EXISTS.index + ":LOGIN_EXISTS";
                out.println(output);
                printflushDebug("ServerListener -> server ha inviato: <%s>\n",output);
              }
              break;
              
            // caricamento di un vecchio login
            case LOGIN_LOAD:
              printflushDebug("ServerListener -> LOGIN_LOAD\n");
              tokens = id_tokens[1].split(",");
              username = tokens[0];
              password = tokens[1];
              printflushDebug("ServerListener -> login: <%s,%s>\n",username,password);
              if (this.server.database.getLogin(username) != null) {
                printflushDebug("ServerListener -> username presente\n");
                userRecord = this.server.database.getRecord(new LoginRecord(username,password));
                if (userRecord != null) {
                  userRecord.message = "LOGIN_LOAD";
                  output = IdResponse.OK.index + ":" + userRecord.toDataServer();
                  printflushDebug("ServerListener -> server invia: <%s>\n",output);
                  printflushDebug("ServerListener -> idResponse: OK-%d\n",IdResponse.OK.index);
                  out.println(output);
                  printflushDebug("ServerListener -> server ha inviato: <%s>\n",output);
                }
                else {
                  printflushDebug("ServerListener -> idResponse: LOGIN_NOT_FOUND-%d\n",IdResponse.LOGIN_NOT_FOUND.index);
                  output = IdResponse.LOGIN_NOT_FOUND.index + ":LOGIN_NOT_FOUND";
                  out.println(output);
                  printflushDebug("ServerListener -> server ha inviato: <%s>\n",output);
                }
              }
              else {
                printflushDebug("ServerListener -> idResponse: LOGIN_NOT_FOUND-%d\n",IdResponse.LOGIN_NOT_FOUND.index);
                output = IdResponse.LOGIN_NOT_FOUND.index + ":LOGIN_NOT_FOUND";
                out.println(output);
                printflushDebug("ServerListener -> server ha inviato: <%s>\n",output);
              }
              break;
                
            // inizializzazione stato del gioco
            case RECORD_INIT:
              printflushDebug("ServerListener -> RECORD_INIT\n");
              loginUserRecord = LoginUserRecord.fromDataServer(id_tokens[1]);
              login = loginUserRecord.login;
              userRecord = loginUserRecord.record;
              userRecord.message = "RECORD_INIT";
              printflushDebug("ServerListener -> login: <%s> userRecord: <%s>\n",login,userRecord);

              // nuova partita dopo creazione account
              int level = userRecord.monster.level != -1 ? userRecord.monster.level : 1;
              // nuova partita dopo vittoria sconfitta o pareggio
              if (userRecord.player.getHealth() == 0) level = 1;
              userRecord = initMatch(userRecord,level);
              
              printflushDebug("ServerListener -> userRecord: <%s>\n",userRecord);
              this.server.database.addRecord(login,userRecord);
              output = IdResponse.OK.index + ":" + userRecord.toDataServer();
              printflushDebug("ServerListener -> server invia: <%s>\n",output);
              printflushDebug("ServerListener -> idResponse: OK-%d\n",IdResponse.OK.index);
              out.println(output);
              printflushDebug("ServerListener -> server ha inviato: <%s>\n",output);
              break;
                
            // abbandono
            case ABANDONS:
            userRecord = match( "ABANDONS",
                                userRecord,
                                id_tokens[1],
                                out,
                                new ActuatorMatch() {
                                  @Override
                                  public UserRecord apply (UserRecord userRecord) {
                                    return abandonsMatch(userRecord);
                                  }
                                });
              break;
              
              // combattimento
              case FIGHT:
                userRecord = match( "FIGHT",
                                    userRecord,
                                    id_tokens[1],
                                    out,
                                    new ActuatorMatch() {
                                      @Override
                                      public UserRecord apply (UserRecord userRecord) {
                                        return fightMatch(userRecord);
                                      }
                                    });
                break;
                
              // bevuta pozione
              case DRINK_POTION:
              userRecord = match( "DRINK_POTION",
                                  userRecord,
                                  id_tokens[1],
                                  out,
                                  new ActuatorMatch() {
                                    @Override
                                    public UserRecord apply (UserRecord userRecord) {
                                      return drinkPotionMatch(userRecord);
                                    }
                                  });
                break;
                
              // uscita dal gioco e terminazione del server
              case EXIT:
                printflushDebug("ServerListener -> EXIT\n");
                this.server.database.saveRecords();
                this.server.off();
                break;
            
              // ASSERT: non deve essere eseguita
              default:
                printflushDebug("ServerListener -> default\n");
                output = IdResponse.UNDEFINED.index + ":" + "La richiesta non trova corrispondenza tra i comandi!";
                printflushDebug("ServerListener -> server invia: <%s>\n",output);
                printflushDebug("ServerListener -> idResponse: UNDEFINED-%d\n",IdResponse.UNDEFINED.index);
                out.println(output);
                printflushDebug("ServerListener -> server ha inviato: <%s>\n",output);
                break;
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        System.err.flush();
        System.err.println("Errore con il socket: " + socket);
      }

      printflushDebug("ServerListener -> chiusura socket...\n");
      try {
        this.socket.close();
      } catch (IOException e) {
        e.printStackTrace();
        System.err.flush();
      }
      printflushDebug("ServerListener -> socket chiuso.\n");
    }

  }


  /**
   * Si occupa di ricevere le richieste di connessione.
   */
  private final ServerSocket welcome;

  public  final int port;
  private volatile boolean isOn = false;

  private final Database database;


  /**
   * Astrae il concetto di Server del gioco.
   */
  public Server (int port) throws Exception {
    printflushDebug("Server.new()...\n");
    if (port < PORT_MIN || port > PORT_MAX)
      throw new Exception("La porta deve essere un intero tra " + PORT_MIN + " e " + PORT_MAX + "!");

    // TODO il database viene istanziato con un password in chiaro nel sorgente
    this.database = new Database(Paths.get(KeyPath.DATABASE_BASE) + "/" + "database.db", "server");

    printflushDebug("Server -> avvio welcome...\n");
    ServerSocket listener = null;
    try {
      listener = new ServerSocket(port);
      listener.setSoTimeout(10000); // 10 secondi
      this.welcome = listener;
      this.port = port;
    } catch (IOException e) {
      e.printStackTrace();
      System.err.flush();
      throw new IOException("\nLa porta non è disponibile!\nIl server non è connesso!\n\n");
    }
  }

  public static void main(String[] args) throws IOException {

    printflushDebug("Server.main() -> avvio...\n");
    
    // ---------------------------------------------------------------
    // - inizializzazione parametri e flags
    // ---------------------------------------------------------------
    boolean[] arguments = new boolean[Flags.size()];
    arguments[Flags.DEBUG.index] = false;

    args  = Utils.flagsSingleArgumentsParseAndSet  (args,
                                                    new FlagSingleArgumentConsumer("--debug",() -> {arguments[Flags.DEBUG.index] = true;})
            );
    Utils.preferences.putBoolean("DEBUG",arguments[Flags.DEBUG.index]);

    // re-direzione di standard-error e standard-output su file di log
      Utils.preferences.putBoolean("STDOUT_TO_FILE",true);
      Utils.preferences.putBoolean("STDERR_TO_FILE",true);

    Utils.init("Assignment.Server");
    // ---------------------------------------------------------------

    
    int port = Integer.parseInt(args[0]);

    Server server = null;
    try {
      server = new Server(port);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.flush();
    }

    printflushDebug("Server.main() -> server: <%s>\n",server);

    server.on();

    printflushDebug("Server.main() -> Il server è terminato.\n");
  }


  /**
   * Il server è in ascolto.
   */
  private synchronized void setOn () {
    printflushDebug("Server.setOn() <- true\n");
    this.isOn = true;
  } 

  /**
   * Mette in stato di ascolto il server.
   */
  public void on () {
    printflushDebug("Server.on()...\n");

    if (!this.isOn()) {
      this.setOn();
      
      printflushDebug("Server.on() -> creazione service...\n");
      ExecutorService service = Executors.newFixedThreadPool(THREADS_POOL_MAX);
      while (this.isOn())
        try {
          service.execute(new ServerListener(this,this.welcome.accept()));
        }
        catch (SocketTimeoutException e) {
          ; // nulla da fare
        }
        catch (IOException e) {
          e.printStackTrace();
          System.err.flush();
        }
      printflushDebug("Server.on() -> serviceShutdown()\n");
      Utils.serviceShutdown(service,16,TimeUnit.SECONDS);

      try {
        this.welcome.close();
        printflushDebug("Server.on() -> welcome.close()\n");
      } catch (IOException e) {
        e.printStackTrace();
        System.err.flush();
      }
    }
  }

  /**
   * Mette in stato di terminazione il server.
   */
  private synchronized void off () {
    printflushDebug("Server.off()...\n");
    if (this.isOn) {
      this.isOn = false;
      printflushDebug("Server.off() <-\n");
    }
  }

  public synchronized boolean isOn () {
    return this.isOn;
  }
  
}