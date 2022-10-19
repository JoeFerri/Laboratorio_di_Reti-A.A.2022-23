/**
 * @author Giuseppe Ferri
 */
package assignment_4;


import java.nio.file.Paths;
import java.util.Set;
import java.util.Map.Entry;


/**
 * Struttura dati per tenere traccia delle occorrenze di un singolo file.
 */
public class FileReport {
  
  private final String fileName;
  private final CharsReport charsReport;
  private String msg = "";
  private boolean error = false;


  /**
   * @param arguments argomenti passati da riga di comando
   * @param fileName il path del file da associare
   */
  public FileReport (boolean[] arguments, String fileName) {
    this.charsReport = new HashCharsReport(arguments);
    this.fileName = fileName;
  }


  public String getFileName () {
    return this.fileName;
  }

  public Set<Entry<Character,Long>> entrySet () {
    return this.charsReport.entrySet();
  }

  /**
   * Controlla se il carattere `c` è un'occorrenza valida
   * e setta opportunamente il nuovo valore associato alla lettera.
   * @param c il carattere del file da controllare
   * @return la traccia dell'elaborazione
   */
  protected CheckReport checkAndSet (char c) {
    return this.charsReport.checkAndSet(c);
  }

  /**
   * Controlla se il carattere `c` è un'occorrenza valida
   * e setta opportunamente il nuovo valore associato alla lettera.
   * @param c il carattere del file da controllare
   * @return la traccia dell'elaborazione
   */
  protected CheckReport checkAndSet (int c) {
    return this.charsReport.checkAndSet(c);
  }

  /**
   * Invalida le informazioni raccolte sul file.
   * @param msg un messaggio di errore
   */
  public synchronized void invalidate (String msg) {
    this.msg = msg;
    this.charsReport.invalidate(msg);
    this.error = true;
  }

  /**
   * Invalida le informazioni raccolte sul file.
   */
  public synchronized void invalidate () {
    this.invalidate("");
  }

  /**
   * @return l'ultimo messaggio di errore registrato
   */
  public String getMsg () {
    return this.msg;
  }

  /**
   * @return true se il file è valido per l'elaborazione o la lettura
   */
  public boolean isValid () {
    return this.error;
  }

  @Override
  public String toString () {
    return this.toString(0);
  }

  public String toString (int length) {
    String name = Paths.get(this.fileName).getFileName().toString();
    String spaces = "";
    for (int i = 0; i < length - name.length(); i++)
      spaces += " ";
    return (this.error ? ("(ERROR" + (this.msg.length() > 0 ? (":" + this.msg) : "") + ")") : "") + name + spaces + " : " + this.charsReport;
  }
}
