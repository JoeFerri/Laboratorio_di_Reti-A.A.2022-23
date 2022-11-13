/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import java.util.HashMap;
import java.util.Map;


/**
 * Classe di utilità per la memorizzazione dei testi usati dal programma.
 */
public class Texts {

  private static final Map<KeyText,String> map;
  
  public static final String messageLogin     = "Hai già un profilo utente?";
  public static final String titleLogin       = "Login";
  public static final String titleNewLogin    = "Login Salva";
  public static final String titleLoadLogin   = "Login Carica";
  public static final String newLogin         = "Nuovo";
  public static final String loadLogin        = "Carica";
  public static final String usernameLogin    = "Username";
  public static final String passwordLogin    = "Password";
  public static final String existingUser     = "Username già esistente!";
  public static final String errorLogin       = "Username inesistente o password errata!";
  public static final String error            = "Errore";
  public static final String initErrorRecord  = "Errore nell'inizializzazione dei dati dell'utente!";


  static {
    map = new HashMap<>();

    map.put(KeyText.LOGIN_MESSAGE,messageLogin);
    map.put(KeyText.LOGIN_TITLE,titleLogin);
    map.put(KeyText.LOGIN_NEW_TITLE,titleNewLogin);
    map.put(KeyText.LOGIN_LOAD_TITLE,titleLoadLogin);
    map.put(KeyText.LOGIN_NEW,newLogin);
    map.put(KeyText.LOGIN_LOAD,loadLogin);
    map.put(KeyText.LOGIN_USERNAME,usernameLogin);
    map.put(KeyText.LOGIN_PASSWORD,passwordLogin);
    map.put(KeyText.LOGIN_ERROR_EXISTING,existingUser);
    map.put(KeyText.LOGIN_ERROR,errorLogin);
    map.put(KeyText.ERROR,error);
    map.put(KeyText.RECORD_INIT_ERROR,initErrorRecord);
  }

  private Texts () {
    ;
  }

  public static String get (KeyText key) {
    return map.get(key);
  }

}
