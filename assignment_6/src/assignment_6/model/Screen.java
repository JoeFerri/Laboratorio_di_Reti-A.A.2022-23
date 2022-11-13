/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;


/**
 * Classe di utilità per la gestione dello schermo.
 */
public class Screen {
  
  public static final int width;
  public static final int height;

  static {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    width = gd.getDisplayMode().getWidth();
    height = gd.getDisplayMode().getHeight();
  }
  
  private Screen () {
    ;
  }
}
