/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import java.io.Serializable;

import static com.gf.utils.Utils.printflushDebug;

/**
 * Astrae il concetto di profilo utente.
 */
public class LoginRecord implements Serializable, DataServerProvider {
  
  public final String username;
  public final String password;

  public LoginRecord (String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public boolean equals (Object obj) {
    if (obj == null || !(obj instanceof LoginRecord))
      return false;
    LoginRecord lr = (LoginRecord)obj;
    return this.username.equals(lr.username) && this.password.equals(lr.password);
  }

  @Override
  public int hashCode () {
    return new String(this.toDataServer()).hashCode();
  }

  // TODO l'invio è in chiaro
  @Override
  public String toDataServer() {
    return this.username + "," + this.password;
  }

  /**
   * Converte una stringa ricevuta dal server in una istanza LoginRecord.
   */
  public static LoginRecord fromDataServer (String data) {
    printflushDebug("LoginRecord.fromDataServer() -> data: <%s>\n",data);
    String[] tokens = data.split(",");
    assert tokens.length == 2 : "Errore nel parsing del LoginRecord!";
    return new LoginRecord(tokens[0],tokens[1]);
  }

  @Override
  public String toString () {
    return this.toDataServer();
  }
}
