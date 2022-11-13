/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Classe di utilità per la memorizzazione dei fonts usati dal programma.
 */
public final class Fonts {

  private static final Map<KeyFont,Font> map;

  public static final String algerianRegularPath = "../fonts/Algerian_Regular.ttf";

  public static final float titleSize           = 40.0f;
  public static final float loginSize           = 20.0f;
  public static final float infoTokenSize       = 24.0f;
  public static final float infoTokenBottomSize = 20.0f;
  public static final float actionsTokenSize    = 18.0f;
  public static final float usernameSize        = 14.0f;


  static {
    map = new HashMap<>();
    map.put(KeyFont.TITLE,Fonts.getFont(Fonts.algerianRegularPath,Fonts.titleSize));
    map.put(KeyFont.LOGIN,Fonts.getFont(Fonts.algerianRegularPath,Fonts.loginSize));
    map.put(KeyFont.INFO_TOKEN_BOTTOM,Fonts.getFont(Fonts.algerianRegularPath,Fonts.infoTokenBottomSize));
    map.put(KeyFont.INFO_TOKEN,Fonts.getFont(Fonts.algerianRegularPath,Fonts.infoTokenSize));
    map.put(KeyFont.ACTIONS_TOKEN,Fonts.getFont(Fonts.algerianRegularPath,Fonts.actionsTokenSize));
    map.put(KeyFont.USERNAME,Fonts.getFont(Fonts.algerianRegularPath,Fonts.usernameSize));
  }

  private Fonts () {
    ;
  }

  public static Font getFont (String path, float size) {
    Font font = null;
    try {
      // forse il programma è stato avviato dentro un archivio .jar
      font = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getClassLoader().getResourceAsStream(path));
    } catch (FontFormatException | IOException e1) {
      try {
        // il programma è stato avviato normalmente
        font = Font.createFont(Font.TRUETYPE_FONT, new File(path));
      }
      catch (FontFormatException | IOException e2) {
        e2.printStackTrace();
      }
    }
    return size > 0 ? font.deriveFont(size) : font;
  }

  public static Font getFont (String path) {
    return Fonts.getFont(path,0f);
  }

  public static Font get (KeyFont key) {
    return map.get(key);
  }

}
