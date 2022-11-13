/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


/**
 * Chiave identificativa di un path immagine.
 * @see {@link assignment_6.model.Images}
 */
public enum KeyImagePath {

  POTION_ICON_16    (0 ,"potion"),
  POTION_ICON_24    (1 ,"potion"),
  POTION_ICON_32    (2 ,"potion"),
  POTION_ICON_64    (3 ,"potion"),
  HEALTH_ICON_16    (4 ,"potion"),
  HEALTH_ICON_24    (5 ,"potion"),
  HEALTH_ICON_32    (6 ,"potion"),
  HEALTH_ICON_64    (7 ,"potion"),

  HEROE_1           (8 ,"hero"),
  HEROE_2           (9 ,"hero"),
  HEROE_3           (10,"hero"),
  HEROE_4           (11,"hero"),
  HEROE_5           (12,"hero"),
  HEROE_6           (13,"hero"),
  HEROE_7           (14,"hero"),
  HEROE_8           (15,"hero"),
  HEROE_9           (16,"hero"),
  HEROE_10          (17,"hero"),
  HEROE_11          (18,"hero"),
  HEROE_12          (19,"hero"),
  HEROE_13          (20,"hero"),
  HEROE_14          (21,"hero"),
  HEROE_15          (22,"hero"),
  HEROE_16          (23,"hero"),
  HEROE_17          (24,"hero"),
  HEROE_18          (25,"hero"),
  HEROE_19          (26,"hero"),
  HEROE_20          (27,"hero"),
  HEROE_21          (28,"hero"),
  HEROE_22          (29,"hero"),
  HEROE_23          (30,"hero"),
  HEROE_24          (31,"hero"),
  HEROE_25          (32,"hero"),
  HEROE_26          (33,"hero"),
  HEROE_27          (34,"hero"),
  HEROE_28          (35,"hero"),
  HEROE_29          (36,"hero"),
  HEROE_30          (37,"hero"),
  HEROE_31          (38,"hero"),

  MONSTER_1         (39,"monster"),
  MONSTER_2         (40,"monster"),
  MONSTER_3         (41,"monster"),
  MONSTER_4         (42,"monster"),
  MONSTER_5         (43,"monster"),
  MONSTER_6         (44,"monster"),
  MONSTER_7         (45,"monster"),
  MONSTER_8         (46,"monster"),
  MONSTER_9         (47,"monster"),
  MONSTER_10        (48,"monster"),
  MONSTER_11        (49,"monster"),
  MONSTER_12        (50,"monster"),
  MONSTER_13        (51,"monster"),
  MONSTER_14        (52,"monster"),
  MONSTER_15        (53,"monster"),
  MONSTER_16        (54,"monster"),
  MONSTER_17        (55,"monster"),
  MONSTER_18        (56,"monster"),
  MONSTER_19        (57,"monster"),
  MONSTER_20        (58,"monster");

  
  final int index;
  final String type;
  KeyImagePath (int index, String type) {
    this.index = index;
    this.type = type;
  }

}
