/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;


/**
 * Classe di utilità per la memorizzazione delle immagini usate dal programma.
 */
public final class Images {

  private static final Map<String,ImageIcon> mapImageIcon;
  private static final Map<KeyImagePath,String> mapPaths;
  
  private static final String dirImages = "../images/";

  static {
    mapImageIcon  = new HashMap<>();
    mapPaths      = new HashMap<>();


    mapPaths.put(KeyImagePath.POTION_ICON_16,dirImages + "icons/potion-16.png");
    mapPaths.put(KeyImagePath.POTION_ICON_24,dirImages + "icons/potion-24.png");
    mapPaths.put(KeyImagePath.POTION_ICON_32,dirImages + "icons/potion-32.png");
    mapPaths.put(KeyImagePath.POTION_ICON_64,dirImages + "icons/potion-64.png");
    mapPaths.put(KeyImagePath.HEALTH_ICON_16,dirImages + "icons/mental-health-16.png");
    mapPaths.put(KeyImagePath.HEALTH_ICON_24,dirImages + "icons/mental-health-24.png");
    mapPaths.put(KeyImagePath.HEALTH_ICON_32,dirImages + "icons/mental-health-32.png");
    mapPaths.put(KeyImagePath.HEALTH_ICON_64,dirImages + "icons/mental-health-64.png");

    // TODO riempire la mappa in un ciclo for
    mapPaths.put(KeyImagePath.HEROE_1,dirImages  + "heroes/adventurer.2.png");
    mapPaths.put(KeyImagePath.HEROE_2,dirImages  + "heroes/adventurer.png");
    mapPaths.put(KeyImagePath.HEROE_3,dirImages  + "heroes/alchemy.png");
    mapPaths.put(KeyImagePath.HEROE_4,dirImages  + "heroes/assasin.png");
    mapPaths.put(KeyImagePath.HEROE_5,dirImages  + "heroes/barbarian.png");
    mapPaths.put(KeyImagePath.HEROE_6,dirImages  + "heroes/bow.png");
    mapPaths.put(KeyImagePath.HEROE_7,dirImages  + "heroes/crossbow.png");
    mapPaths.put(KeyImagePath.HEROE_8,dirImages  + "heroes/dragon.png");
    mapPaths.put(KeyImagePath.HEROE_9,dirImages  + "heroes/druid.png");
    mapPaths.put(KeyImagePath.HEROE_10,dirImages + "heroes/gunnery.png");
    mapPaths.put(KeyImagePath.HEROE_11,dirImages + "heroes/jester.png");
    mapPaths.put(KeyImagePath.HEROE_12,dirImages + "heroes/knight.2.png");
    mapPaths.put(KeyImagePath.HEROE_13,dirImages + "heroes/knight.3.png");
    mapPaths.put(KeyImagePath.HEROE_14,dirImages + "heroes/knight.png");
    mapPaths.put(KeyImagePath.HEROE_15,dirImages + "heroes/magician.png");
    mapPaths.put(KeyImagePath.HEROE_16,dirImages + "heroes/martial.png");
    mapPaths.put(KeyImagePath.HEROE_17,dirImages + "heroes/monk.png");
    mapPaths.put(KeyImagePath.HEROE_18,dirImages + "heroes/ninja.png");
    mapPaths.put(KeyImagePath.HEROE_19,dirImages + "heroes/priest.2.png");
    mapPaths.put(KeyImagePath.HEROE_20,dirImages + "heroes/priest.png");
    mapPaths.put(KeyImagePath.HEROE_21,dirImages + "heroes/prince.png");
    mapPaths.put(KeyImagePath.HEROE_22,dirImages + "heroes/princess.png");
    mapPaths.put(KeyImagePath.HEROE_23,dirImages + "heroes/queen.png");
    mapPaths.put(KeyImagePath.HEROE_24,dirImages + "heroes/samurai.png");
    mapPaths.put(KeyImagePath.HEROE_25,dirImages + "heroes/soldier.png");
    mapPaths.put(KeyImagePath.HEROE_26,dirImages + "heroes/swordsman.png");
    mapPaths.put(KeyImagePath.HEROE_27,dirImages + "heroes/thief.png");
    mapPaths.put(KeyImagePath.HEROE_28,dirImages + "heroes/villager.2.png");
    mapPaths.put(KeyImagePath.HEROE_29,dirImages + "heroes/villager.png");
    mapPaths.put(KeyImagePath.HEROE_30,dirImages + "heroes/wizard.2.png");
    mapPaths.put(KeyImagePath.HEROE_31,dirImages + "heroes/wizard.png");

    mapPaths.put(KeyImagePath.MONSTER_1,dirImages  + "monsters/armor.png");
    mapPaths.put(KeyImagePath.MONSTER_2,dirImages  + "monsters/clown.png");
    mapPaths.put(KeyImagePath.MONSTER_3,dirImages  + "monsters/dracula.png");
    mapPaths.put(KeyImagePath.MONSTER_4,dirImages  + "monsters/dragon.png");
    mapPaths.put(KeyImagePath.MONSTER_5,dirImages  + "monsters/executioner.png");
    mapPaths.put(KeyImagePath.MONSTER_6,dirImages  + "monsters/ghost.2.png");
    mapPaths.put(KeyImagePath.MONSTER_7,dirImages  + "monsters/ghost.png");
    mapPaths.put(KeyImagePath.MONSTER_8,dirImages  + "monsters/knight.2.png");
    mapPaths.put(KeyImagePath.MONSTER_9,dirImages  + "monsters/knight.png");
    mapPaths.put(KeyImagePath.MONSTER_10,dirImages + "monsters/monster.png");
    mapPaths.put(KeyImagePath.MONSTER_11,dirImages + "monsters/mummy.png");
    mapPaths.put(KeyImagePath.MONSTER_12,dirImages + "monsters/nun.png");
    mapPaths.put(KeyImagePath.MONSTER_13,dirImages + "monsters/pirate.png");
    mapPaths.put(KeyImagePath.MONSTER_14,dirImages + "monsters/reaper.png");
    mapPaths.put(KeyImagePath.MONSTER_15,dirImages + "monsters/skeleton.png");
    mapPaths.put(KeyImagePath.MONSTER_16,dirImages + "monsters/spider.png");
    mapPaths.put(KeyImagePath.MONSTER_17,dirImages + "monsters/werewolf.png");
    mapPaths.put(KeyImagePath.MONSTER_18,dirImages + "monsters/witch.png");
    mapPaths.put(KeyImagePath.MONSTER_19,dirImages + "monsters/wizard.png");
    mapPaths.put(KeyImagePath.MONSTER_20,dirImages + "monsters/zombie.png");
  }

  private Images () {
    ;
  }

  public static String getPath (KeyImagePath key) {
    return mapPaths.get(key);
  }

  public static String[] getPathsByType (String type) {
    return mapPaths.entrySet().stream().filter(e -> e.getKey().type.equals(type)).map(e -> e.getValue()).toArray(String[]::new);
  }

  public static ImageIcon[] getbyType (String type) {
    return Arrays.asList(getPathsByType(type)).stream().map(p -> getByPath(p)).toArray(ImageIcon[]::new);
  }

  public static ImageIcon getByPath (String path) {
    ImageIcon img = mapImageIcon.get(path);
    if (img == null) {
      img = new ImageIcon(path);
      mapImageIcon.put(path,img);
    }
    return img;
  }
}
