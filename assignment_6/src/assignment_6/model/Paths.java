/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import java.util.HashMap;
import java.util.Map;


/**
 * Classe di utilità per la memorizzazione dei path usati dal programma.
 */
public final class Paths {

  private static final Map<KeyPath,String> map;
  
  public static final String databaseBasePath     = ".";


  static {
    map = new HashMap<>();

    map.put(KeyPath.DATABASE_BASE,databaseBasePath);
  }

  private Paths () {
    ;
  }

  public static String get (KeyPath key) {
    return map.get(key);
  }

}
