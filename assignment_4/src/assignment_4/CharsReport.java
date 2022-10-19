/**
 * @author Giuseppe Ferri
 */
package assignment_4;


import java.text.Normalizer;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import static assignment_4.Costants.NOT_SIGNIFICANT_LONG;;


/**
 * Struttura dati per tenere traccia delle occorrenze delle lettere alfabetiche.
 * Ogni occorrenza aumenta di 1 un contatore della relativa lettera.
 * Tra le occorrenze della lettera 'a' ci sono ad esempio: 'a', 'A', 'à', 'á';
 * le occorrenze 'à', 'á' vengono contate soltanto se passato il parametro "--normalize".
 */
public abstract class CharsReport {
  
  private final boolean[] arguments;
  protected final Map<Character,Long> mapReport;
  private String msg = "";
  private boolean error = false;

  
  public CharsReport (boolean[] arguments, Map<Character,Long> mapReport) {
    this.arguments = arguments;
    this.mapReport = mapReport;
    this.init(0);
  }

  /**
   * Inizializza la mappa delle occorrenze.
   * @param value il valore di default.
   */
  private void init (long value) {
    char c = 'a'-1;
    while ('z' > c++)
      this.mapReport.put(c,value);
  }

  /**
   * Invalida le informazioni raccolte sulle occorrenze.
   * @param msg un messaggio di errore
   */
  public synchronized void invalidate (String msg) {
    if (!this.error) {
      this.msg = msg;
      this.error = true;
      this.init(NOT_SIGNIFICANT_LONG);
    }
  }

  /**
   * Invalida le informazioni raccolte sulle occorrenze.
   */
  public synchronized void invalidate () {
    this.invalidate("");
  }
  
  /**
   * Controlla se il carattere `c` è un'occorrenza valida
   * e setta opportunamente il nuovo valore associato alla lettera.
   * <p>
   * Effettua la normalizzazione del carattere se è attivo "--normalize".
   * @param c il carattere da controllare
   * @return la traccia dell'elaborazione
   */
  public CheckReport checkAndSet (char c) {
    boolean setted = false;
    long oldCount = NOT_SIGNIFICANT_LONG;
    long newCount = NOT_SIGNIFICANT_LONG;
    String msg = "";
    boolean error = false;

    if (!this.error) {
      synchronized (this.mapReport) {
        if (this.arguments[Flags.NORMALIZE.index]) {
        String norm =
          Normalizer
            .normalize(String.valueOf(c), Normalizer.Form.NFKD)   // \p{L} means "all letters"
              .replaceAll("\\p{M}", "");       // \p{M} means "all marks"
        if (norm.length() > 0)
          c = norm.charAt(0);
        else
          c = '\0';
        }
        
        c = Character.toLowerCase(c);
        if (this.mapReport.containsKey(c)) {
          setted = true;
          oldCount = this.mapReport.get(c);
          newCount = oldCount + 1;
          this.mapReport.put(c,newCount);
        }
      }
    }
    else {
      msg = this.msg;
      error = true;
    }

    return new CheckReport(setted,oldCount,newCount,msg,error);
  }
  
  /**
   * Controlla se il carattere `c` è un'occorrenza valida
   * e setta opportunamente il nuovo valore associato alla lettera.
   * <p>
   * Effettua la normalizzazione del carattere se è attivo "--normalize".
   * @param c il carattere da controllare
   * @return la traccia dell'elaborazione
   */
  public CheckReport checkAndSet (int c) {
    return this.checkAndSet((char)c);
  }
  
  public Set<Entry<Character,Long>> entrySet () {
    return this.mapReport.entrySet();
  }

  @Override
  public String toString () {
    return (this.error ? ("(ERROR" + (this.msg.length() > 0 ? (":" + this.msg) : "") + ")") : "") + this.mapReport.entrySet().toString();
  }
}
