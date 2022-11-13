/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


/**
 * Chiave identificativa di un testo.
 * @see {@link assignment_6.model.Texts}
 */
public enum KeyText {

  LOGIN_MESSAGE             (0),
  LOGIN_TITLE               (1),
  LOGIN_NEW_TITLE           (2),
  LOGIN_LOAD_TITLE          (3),
  LOGIN_NEW                 (4),
  LOGIN_LOAD                (5),
  LOGIN_USERNAME            (6),
  LOGIN_PASSWORD            (7),
  LOGIN_ERROR_EXISTING      (8),
  LOGIN_ERROR               (9),
  ERROR                     (10),
  RECORD_INIT_ERROR         (11);

  
  final int index;
  KeyText (int index) {
    this.index = index;
  }

}
