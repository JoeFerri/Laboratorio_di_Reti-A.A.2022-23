/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


/**
 * Chiave identificativa di un colore.
 * @see {@link assignment_6.model.Colors}
 */
public enum KeyColor {

  MAIN        (0),
  INFO_TOKEN  (1),
  POTION      (2),
  HEALTH      (3);

  
  final int index;
  KeyColor (int index) {
    this.index = index;
  }

}
