/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


/**
 * Chiave identificativa di una label.
 * @see {@link assignment_6.model.Labels}
 */
public enum KeyLabel {

  START_TITLE               (0),
  LOGIN                     (1),
  ROUND                     (2),
  WON                       (3),
  LOST                      (4),
  MATCHES                   (5),
  FIGHT                     (6),
  DRINK_POTION              (7),
  ABANDONS                  (8),
  PLAYER_TITLE              (9),
  MONSTER_TITLE             (10),
  USERNAME                  (11),
  LEVEL                     (12),
  MONSTER_HEALTH_VALUE      (13),
  MONSTER_MAX_HEALTH_VALUE  (14),
  PLAYER_HEALTH_VALUE       (15),
  PLAYER_MAX_HEALTH_VALUE   (16),
  PLAYER_POTION_VALUE       (17),
  PLAYER_MAX_POTION_VALUE   (18),
  NEW_LOGIN                 (19),
  LOAD_LOGIN                (20);

  
  final int index;
  KeyLabel (int index) {
    this.index = index;
  }

}
