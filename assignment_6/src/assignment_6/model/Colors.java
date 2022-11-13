/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import java.awt.Color;
import java.util.HashMap;
import java.util.Map;


/**
 * Classe di utilità per la memorizzazione dei colori usati dal programma.
 */
public final class Colors {

  private static final Map<KeyColor,Color> map;
  

  static {
    map = new HashMap<>();
    map.put(KeyColor.MAIN,      new Color(252, 227, 212));
    map.put(KeyColor.INFO_TOKEN,new Color(207, 186, 174));
    map.put(KeyColor.POTION,new Color(148, 14, 150));
    map.put(KeyColor.HEALTH,new Color(22, 163, 20));
  }

  private Colors () {
    ;
  }

  public static Color get (KeyColor key) {
    return map.get(key);
  }
}
