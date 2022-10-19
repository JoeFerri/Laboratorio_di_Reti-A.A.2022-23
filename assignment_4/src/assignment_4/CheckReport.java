/**
 * @author Giuseppe Ferri
 */
package assignment_4;


import static assignment_4.Costants.NOT_SIGNIFICANT_LONG;


/**
 * Struttura dati per tenere traccia dell'elaborazione
 * effettuata dai metodi checkAndSet().
 * @see {@link assignment_4.CharsReport#checkAndSet(char)}
 * @see {@link assignment_4.FileReport#checkAndSet(char)}
 * @see {@link assignment_4.FilesReport#checkAndSet(char)}
 */
public class CheckReport {
  
  /**
   * checkAndSet() ha trovato un'occorrenza
   * ed ha settato il nuovo valore
   */
  public final boolean setted;
  /**
   * vecchio valore occorrenza
   */
  public final long oldCount;
  /**
   * nuovo valore occorrenza
   */
  public final long newCount;
  /**
   * true se si è verificato un errore
   */
  public final boolean error;
  /**
   * messaggio di errore se presente
   */
  public final String msg;


  public static final CheckReport NOT_SIGNIFICANT_CHECK_REPORT =
    new CheckReport(false,NOT_SIGNIFICANT_LONG,NOT_SIGNIFICANT_LONG);


  /**
   * @param setted    checkAndSet() ha trovato un'occorrenza
   *                  ed ha settato il nuovo valore
   * @param oldCount  vecchio valore occorrenza
   * @param newCount  nuovo valore occorrenza
   * @param msg       messaggio di errore se presente
   * @param error     true se si è verificato un errore
   */
  public CheckReport (boolean setted, long oldCount, long newCount, String msg, boolean error) {
    this.setted = setted;
    this.oldCount = oldCount;
    this.newCount = newCount;
    this.msg = msg;
    this.error = error;
  }

  public CheckReport (boolean setted, long oldCount, long newCount, String msg) {
    this(setted,oldCount,newCount,msg,false);
  }

  public CheckReport (boolean setted, long oldCount, long newCount) {
    this(setted,oldCount,newCount,"",false);
  }

  @Override
  public String toString () {
    return (this.error ? ("(ERROR" + (this.msg.length() > 0 ? (":" + this.msg) : "") + ")") : "") + "{" + this.setted + "," + this.oldCount + "," + this.newCount + "}";
  }
}
