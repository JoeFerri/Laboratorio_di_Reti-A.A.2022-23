/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


/**
 * Chiave identificativa di un path.
 * @see {@link assignment_6.model.Paths}
 */
public enum KeyPath {

  DATABASE_BASE             (0);

  
  final int index;
  KeyPath (int index) {
    this.index = index;
  }

}
