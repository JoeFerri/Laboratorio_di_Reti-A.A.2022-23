/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import static com.gf.utils.Utils.printflushDebug;


/**
 * Classe di utilità per la connessione e comunicazione con il server.
 * TODO come implementazione si è scelto di utilizzare dei metodi statici e creare sempre nuove connessioni (Socket)
 * TODO il client potrebbe mantenere una solo connessione Socket e usare metodi di istanza
 */
public final class Client {


  private Client () {
    ;
  }
  

  /**
   * Metodo per la comunicazione client -> server
   * @param idResponse codice comando
   * @param message dati del comando
   * @param port porta di ascolto
   * @return un nuovo stato di gioco
   */
  private static UserRecord toFromServer (IdResponse idResponse, DataServerProvider message, int port) {
    UserRecord record = null;

    try (Socket socket = new Socket("localhost", port);
          Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true)) {
    
    out.println(idResponse.index + ":" + message.toDataServer());
    record = UserRecord.fromDataServer(in.nextLine());
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return record;
  }

  /**
   * Crea un nuovo profilo nel database.
   */
  public static UserRecord saveLogin (LoginRecord loginRecord, int port) throws UnknownHostException, IOException {
    printflushDebug("Client.saveLogin() -> login: <%s> port: <%d>\n",loginRecord,port);
    return toFromServer (IdResponse.LOGIN_SAVE,loginRecord,port);
  }

  /**
   * Carica un vecchio profilo dal database.
   */
  public static UserRecord loadLogin (LoginRecord loginRecord, int port) {
    printflushDebug("Client.loadLogin() -> login: <%s> port: <%d>\n",loginRecord,port);
    return toFromServer (IdResponse.LOGIN_LOAD,loginRecord,port);
  }

  /**
   * Salva lo stato corrente del gioco.
   */
  public static UserRecord saveRecord (LoginRecord loginRecord, int port) {
    printflushDebug("Client.saveRecord() -> login: <%s> port: <%d>\n",loginRecord,port);
    return toFromServer (IdResponse.RECORD_SAVE,loginRecord,port);
  }

  /**
   * Carica lo stato corrente del gioco.
   */
  public static UserRecord loadRecord (LoginRecord loginRecord, int port) {
    printflushDebug("Client.loadRecord() -> login: <%s> port: <%d>\n",loginRecord,port);
    return toFromServer (IdResponse.RECORD_LOAD,loginRecord,port);
  }

  /**
   * Inizializza lo stato corrente del gioco.
   */
  public static UserRecord initRecord(LoginRecord loginRecord, UserRecord userRecord, int port) {
    printflushDebug("Client.initRecord() -> login: <%s> port: <%d>\n",loginRecord,port);
    return toFromServer (IdResponse.RECORD_INIT,new LoginUserRecord(loginRecord,userRecord),port);
  }

  /**
   * Invia al server l'azione di abbandono.
   */
  public static UserRecord abandons(LoginRecord loginRecord, UserRecord userRecord, int port) {
    printflushDebug("Client.abandons() -> login: <%s> port: <%d>\n",loginRecord,port);
    return toFromServer (IdResponse.ABANDONS,new LoginUserRecord(loginRecord,userRecord),port);
  }

  /**
   * Invia al server l'azione di combattimento.
   */
  public static UserRecord fight(LoginRecord loginRecord, UserRecord userRecord, int port) {
    printflushDebug("Client.fight() -> login: <%s> port: <%d>\n",loginRecord,port);
    return toFromServer (IdResponse.FIGHT,new LoginUserRecord(loginRecord,userRecord),port);
  }

  /**
   * Invia al server l'azione di bevuta pozione.
   */
  public static UserRecord drinkPotion(LoginRecord loginRecord, UserRecord userRecord, int port) {
    printflushDebug("Client.drinkPotion() -> login: <%s> port: <%d>\n",loginRecord,port);
    return toFromServer (IdResponse.DRINK_POTION,new LoginUserRecord(loginRecord,userRecord),port);
  }

}
