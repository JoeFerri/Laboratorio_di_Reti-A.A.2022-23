/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static com.gf.utils.Utils.printflushDebug;
import static com.gf.utils.Utils.eprintflush;

import assignment_6.security.crypto.SecurityUtils;


/**
 * Astrae il concetto di database.
 * Il database è implementato utilizzando una List per i dati di login
 * e una Map per lo stato corrente di gioco degli utenti.
 * <p>
 * Il database ha uno stato virtuale rappresentato con le List e Map sopra indicate,
 * e uno stato fisico rappresentato da due file criptati logins.db e database.db
 * <p>
 * I dati su disco sono criptati.
 */
public class Database {
  
  private final List<LoginRecord> logins = new ArrayList<>();
  private final Map<LoginRecord,UserRecord> records = new HashMap<>();

  private static final String DATABASE_LOGIN_PATH = Paths.get(KeyPath.DATABASE_BASE) + "/" + "logins.db";
  private final String path;
  private final String password;


  public Database (String path, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
                                                          InvalidAlgorithmParameterException, InvalidKeySpecException, ClassNotFoundException,
                                                            BadPaddingException, IllegalBlockSizeException, IOException {
    
    printflushDebug("Database.new()...\n");
    this.path = path;
    this.password = password;
    this.initFiles();
    this.loadLogins();
    this.loadRecords();
  }


  private void initFiles () {
    printflushDebug("Database.initFiles()...\n");
    try {
      new File(this.path).createNewFile();
      new File(DATABASE_LOGIN_PATH).createNewFile();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  } 

  /**
   * @deprecated
   */
  public UserRecord checkLogin (String username, String passwordRaw) {
    return null;
  }


  /**
   * Salva su disco (logins.db) i dati dei logins effettuati o creati.
   */
  public void saveLogins() throws IOException, NoSuchAlgorithmException,
                                    InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException,
                                      IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException {
    
    printflushDebug("Database.saveLogins() -> logins:\n  %s\n",this.logins != null ? this.logins : "[]");
    if (this.logins.size() > 0) {
      String plainText = "";
      for (LoginRecord login : this.logins)
        plainText += ((!plainText.equals("")) ? ";" : "") + login.toDataServer();

      // criptazione dei dati
      String cipherText = SecurityUtils.encryptPasswordBased(plainText,this.password,new char[]{'1','2','3','4','5','6','7','8'});

      BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_LOGIN_PATH));
      writer.write(cipherText);
      writer.close();
      printflushDebug("Database.saveLogins() -> scrittura file <%s> eseguita.\n",DATABASE_LOGIN_PATH);
    }
  }


  /**
   * Carica da disco (logins.db) i dati dei logins creati in precedenza.
   */
  public void loadLogins() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
                                    InvalidAlgorithmParameterException, InvalidKeySpecException,
                                      ClassNotFoundException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {

    BufferedReader reader = new BufferedReader(new FileReader(DATABASE_LOGIN_PATH));
    String cipherText = "";
    String line = null;
    while ((line = reader.readLine()) != null)
      cipherText += line;
    reader.close();

    // decriptazione dei dati
    String plainText = SecurityUtils.dencryptPasswordBased(cipherText,this.password,new char[]{'1','2','3','4','5','6','7','8'});

    if (!plainText.equals("")) {
      List<String> loginTokens = Arrays.asList(plainText.split(";"));

      this.logins.clear();
      printflushDebug("Database.loadLogins() -> lettura file...\n");
      for (String token : loginTokens) {
        printflushDebug("Database.loadLogins() -> token: <%s>\n",token);
        this.logins.add(LoginRecord.fromDataServer(token));
      }
      printflushDebug("Database.loadLogins() -> logins:\n  %s\n",this.logins != null ? this.logins : "[]");
    }
  }


  /**
   * Salva su disco (database.db) lo stato interno di gioco degli utenti.
   */
  public void saveRecords() throws IOException, NoSuchAlgorithmException,
                                    InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException,
                                      IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException {

    printflushDebug("Database.saveRecords() -> records:\n  %s\n",this.records != null ? this.records.values() : "[]");
    
    if (this.records.size() > 0) {
      String plainText = "";
      for (UserRecord record : this.records.values())
        plainText += ((!plainText.equals("")) ? "|" : "") + record.toDataServer();

      // criptazione dei dati
      String cipherText = SecurityUtils.encryptPasswordBased(plainText,this.password,new char[]{'1','2','3','4','5','6','7','8'});

      BufferedWriter writer = new BufferedWriter(new FileWriter(this.path));
      writer.write(cipherText);
      writer.close();
      printflushDebug("Database.saveRecords() -> scrittura file <%s> eseguita.\n",this.path);
    }
  }


  /**
   * Carica da disco (database.db) lo stato interno di gioco degli utenti.
   */
  public void loadRecords() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
                                    InvalidAlgorithmParameterException, InvalidKeySpecException, ClassNotFoundException,
                                      BadPaddingException, IllegalBlockSizeException, InvalidKeyException {

    BufferedReader reader = new BufferedReader(new FileReader(this.path));
    String cipherText = "";
    String line = null;
    while ((line = reader.readLine()) != null)
      cipherText += line;
    reader.close();

    // decriptazione dei dati
    String plainText = SecurityUtils.dencryptPasswordBased(cipherText,this.password,new char[]{'1','2','3','4','5','6','7','8'});

    if (!plainText.equals("")) {
      List<String> recordTokens = Arrays.asList(plainText.split("[|]"));

      this.records.clear();
      printflushDebug("Database.loadRecords() -> lettura file...\n");
      for (String token : recordTokens) {
        printflushDebug("Database.loadRecords() -> token: <%s>\n",token);
        UserRecord record = UserRecord.fromDataServer(IdResponse.UNDEFINED.index + ":" + token);
        LoginRecord login = this.logins.stream().filter(l -> l.username.equals(record.player.name)).findFirst().orElse(null);
        if (login != null)
          this.records.put(login,record);
        else
          eprintflush("Un record interno al database risulta orfano di login!");
      }
    }
    printflushDebug("Database.loadRecords() -> records:\n  %s\n",this.records != null ? this.records.values() : "[]");
  }


  /**
   * Aggiunge un nuovo profilo nello spazio virtuale.
   */
  public boolean addLogin (LoginRecord login) throws InvalidKeyException, NoSuchAlgorithmException,
                                                        NoSuchPaddingException,InvalidAlgorithmParameterException,
                                                          IllegalBlockSizeException, InvalidKeySpecException, IOException, BadPaddingException {
    printflushDebug("Database.addLogin() -> login: <%s>\n",login);
    if (this.logins.contains(login)) {
      printflushDebug("Database.addLogin() -> login già presente.\n");
      return false;
    }
    this.logins.add(login);
    this.saveLogins();
    return true;
  }


  /**
   * Carica un profilo dallo spazio virtuale.
   */
  public LoginRecord getLogin (String username) {
    printflushDebug("Database.getLogin() -> username: <%s>\n",username);
    LoginRecord login = this.logins.stream().filter(l -> l.username.equals(username)).findFirst().orElse(null);
    printflushDebug("Database.getLogin() -> login: <%s>\n",login);
    return login;
  }


  /**
   * Aggiunge un nuovo stato di gioco di un profilo nello spazio virtuale.
   */
  public boolean addRecord (LoginRecord login, UserRecord userRecord) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
                                                                          InvalidAlgorithmParameterException, IllegalBlockSizeException,
                                                                            InvalidKeySpecException,IOException, BadPaddingException {
    printflushDebug("Database.addRecord() -> login: <%s> userRecord: <%s>\n",login,userRecord);
    if (!this.logins.contains(login)) {
      printflushDebug("Database.addRecord() -> login non presente.\n");
      return false;
    }
    assert login.username.equals(userRecord.player.name) : "Errore interno, login e player non combaciano!";
    this.records.put(login,userRecord);
    //this.saveRecords(); //! spostato alla chiusura del server
    return true;
  }


  /**
   * Carica uno stato di gioco di un profilo dallo spazio virtuale.
   */
  public UserRecord getRecord (LoginRecord login) {
    printflushDebug("Database.getRecord() -> login: <%s>\n",login);
    return this.records.get(login);
  }

}
