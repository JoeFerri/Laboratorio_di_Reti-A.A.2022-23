/**
 * @author Giuseppe Ferri
 */
package assignment_4;


import java.util.concurrent.ConcurrentHashMap;


/**
 * Struttura dati per la modifica concorrente
 * delle occorrenze nei files.
 */
public class ConcurrentCharsReport extends CharsReport {
  
  public ConcurrentCharsReport (boolean[] arguments) {
    super(arguments, new ConcurrentHashMap<>());
  }
  
}
