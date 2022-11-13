/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import java.io.Serializable;
import static com.gf.utils.Utils.printflushDebug;


/**
 * Astrae il concetto di stato interno corrente di gioco.
 * <p>
 * Associato ad una istanza di {@link assignment_6.model.IdResponse} rappresenta
 * un comando e i rispettivi dati del comando che il client invia al server.
 */
public class UserRecord implements Serializable, DataServerProvider {
  
  public final transient int  idResponse;
  public final Player         player;
  public final Monster        monster;
  public final int            matches;
  public final int            won;
  public final int            lost;
  public final int            round;
  public       String         message;

  public static final UserRecord EMPTY_RECORD =
    new UserRecord(IdResponse.UNDEFINED.index,
                    Player.EMPTY_PLAYER,
                    Monster.EMPTY_MONSTER,
                    -1,-1,-1,-1);


  public UserRecord  (int     idResponse,
                      Player  player,
                      Monster monster,
                      int     matches,
                      int     won,
                      int     lost,
                      int     round,
                      String  message) {
    
    this.idResponse = idResponse;
    this.player = player;
    this.monster = monster;
    this.matches = matches;
    this.won = won;
    this.lost = lost;
    this.round = round;
    this.message = message;
  }

  public UserRecord  (int     idResponse,
                      Player  player,
                      Monster monster,
                      int     matches,
                      int     won,
                      int     lost,
                      int     round) {
    
    this(idResponse,player,monster,matches,won,lost,round,"");
  }


  /**
   * Uno stato indefinito è uno stato creato ma non ancora inizializzato.
   */
  public static boolean isUndefined (UserRecord record) {
    return record == null || record.toDataServer().equals(EMPTY_RECORD.toDataServer());
  }


  /**
   * @return un nuovo stato in cui è settato il path dell'immagine del giocatore.
   */
  public static UserRecord changeImagePath (UserRecord userRecord, String imagePath) {
    printflushDebug("UserRecord.changeImagePath() -> userRecord: <%s> imagePath: <%s>\n",userRecord,imagePath);
    Player player = userRecord.player;
    return new UserRecord(
                IdResponse.UNDEFINED.index,
                new Player(player.name,player.getHealth(),player.maxHealth,imagePath,player.getPotion(),player.maxPotion),
                userRecord.monster,
                userRecord.matches,
                userRecord.won,
                userRecord.lost,
                userRecord.round,
                userRecord.message);
  }


  /**
   * @return un nuovo stato in cui è settato il nome (username) del giocatore.
   */
  public static UserRecord changeNamePlayer (UserRecord userRecord, String namePlayer) {
    printflushDebug("UserRecord.changeNamePlayer() -> userRecord: <%s> namePlayer: <%s>\n",userRecord,namePlayer);
    Player player = userRecord.player;
    return new UserRecord(
                IdResponse.UNDEFINED.index,
                new Player(namePlayer,player.getHealth(),player.maxHealth,player.imagePath,player.getPotion(),player.maxPotion),
                userRecord.monster,
                userRecord.matches,
                userRecord.won,
                userRecord.lost,
                userRecord.round,
                userRecord.message);
  }

  
  /**
   * @return un nuovo stato in cui è settato il valore della vita del giocatore.
   */
  public static UserRecord changeHealthPlayer (UserRecord userRecord, int health) {
    printflushDebug("UserRecord.changeHealthPlayer() -> userRecord: <%s> health: <%s>\n",userRecord,health);
    Player player = userRecord.player;
    return new UserRecord(
                IdResponse.UNDEFINED.index,
                new Player(player.name,Math.min(health,player.maxHealth),player.maxHealth,player.imagePath,player.getPotion(),player.maxPotion),
                userRecord.monster,
                userRecord.matches,
                userRecord.won,
                userRecord.lost,
                userRecord.round,
                userRecord.message);
  }

  @Override
  public String toDataServer () {
    return this.player.toDataServer() + ";" +
            this.monster.toDataServer() + ";" +
              this.matches + "," + this.won + "," + this.lost + "," + this.round + "," +
                this.message;
  }

  
  /**
   * Converte una stringa ricevuta dal server in una istanza UserRecord.
   */
  public static UserRecord fromDataServer (String data) {
    printflushDebug("UserRecord.fromDataServer() -> data: <%s>\n",data);
    
    String id_tokens[] = data.split(":");
    assert id_tokens.length == 2 : "Errore nel parsing del UserRecord!"; 
    int idResponse = Integer.parseInt(id_tokens[0]);

    if (id_tokens[1].indexOf(";") != -1) { // idResponse:player;monster;...,...,...
      String[] dataTokens = id_tokens[1].split(";");
      assert dataTokens.length == 3 : "Errore nel parsing del UserRecord!"; 
      Player player = Player.fromDataServer(dataTokens[0]);
      Monster monster = Monster.fromDataServer(dataTokens[1]);
      String[] tokens = dataTokens[2].split(",");
      assert tokens.length == 5 : "Errore nel parsing del UserRecord!"; 

      return new UserRecord(idResponse,
                            player,
                            monster,
                            Integer.parseInt(tokens[0]),
                            Integer.parseInt(tokens[1]),
                            Integer.parseInt(tokens[2]),
                            Integer.parseInt(tokens[3]),
                            tokens[4]);
    }
    // idResponse:message
    return new UserRecord(idResponse,
                          EMPTY_RECORD.player,
                          EMPTY_RECORD.monster,
                          EMPTY_RECORD.matches,
                          EMPTY_RECORD.won,
                          EMPTY_RECORD.lost,
                          EMPTY_RECORD.round,
                          id_tokens[1]);
  }

  @Override
  public String toString () {
    return this.toDataServer();
  }

}
