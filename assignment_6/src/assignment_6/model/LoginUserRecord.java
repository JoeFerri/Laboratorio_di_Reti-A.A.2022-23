/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import static com.gf.utils.Utils.printflushDebug;


/**
 * Astrae il concetto di stato interno corrente di gioco di
 * uno specifico profilo utente.
 */
public class LoginUserRecord implements DataServerProvider {

  public final LoginRecord login;
  public final UserRecord  record;


  public LoginUserRecord (LoginRecord login, UserRecord record) {
    this.login = login;
    this.record = record;
  }


  @Override
  public String toDataServer() {
    return this.login.toDataServer() + "|" + this.record.toDataServer();
  }

  /**
   * Converte una stringa ricevuta dal server in una istanza LoginUserRecord.
   */
  public static LoginUserRecord fromDataServer (String data) {
    printflushDebug("LoginUserRecord.fromDataServer() -> data: <%s>\n",data);
    String[] login_tokens = data.split("[|]");
    assert login_tokens.length == 2 : "Errore nel parsing del LoginUserRecord!";
    return new LoginUserRecord(
      LoginRecord.fromDataServer(login_tokens[0]),
        UserRecord.fromDataServer(IdResponse.UNDEFINED.index + ":" + login_tokens[1]));
  }
  
  @Override
  public String toString () {
    return this.toDataServer();
  }
}
