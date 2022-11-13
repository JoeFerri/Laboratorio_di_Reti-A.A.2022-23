/**
 * @author Giuseppe Ferri
 */
package assignment_6.view.panels;

import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**
 * Pannello con immagine come background.
 * TODO la classe non è utilizzata nel programma
 */
public class ImagePanel extends JPanel {

  private Image img;

  public ImagePanel(String img) {
    this(new ImageIcon(img).getImage());
  }

  public ImagePanel(Image img) {
    this.img = img;
    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
    setSize(size);
    setLayout(null);
  }

  public void paintComponent(Graphics g) {
    setOpaque(false);
    super.paintComponent(g);
    g.drawImage(img, 0, 0, null);
  }

}
