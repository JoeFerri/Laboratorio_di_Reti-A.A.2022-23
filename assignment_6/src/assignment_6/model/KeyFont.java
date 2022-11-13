/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


/**
 * Chiave identificativa di un font.
 * @see {@link assignment_6.model.Fonts}
 */
public enum KeyFont {

  TITLE             (0),
  LOGIN             (1),
  INFO_TOKEN        (2),
  INFO_TOKEN_BOTTOM (3),
  ACTIONS_TOKEN     (4),
  USERNAME          (5);

  
  final int index;
  KeyFont (int index) {
    this.index = index;
  }

}
