/**
 * @author Giuseppe Ferri
 */
package assignment_4;


import java.util.HashMap;


/**
 * Struttura dati per la modifica non concorrente
 * delle occorrenze nei files.
 */
public class HashCharsReport extends CharsReport {
  
  public HashCharsReport (boolean[] arguments) {
    super(arguments,new HashMap<>());
  }
  
}
