/**
 * @author Giuseppe Ferri
 */
package assignment_6.view.panels;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import assignment_6.model.InfoLabel;


/**
 * Pannello con immagine come background.
 * Viene utilizzata una JLabel per implementare lo sfondo con immagine.
 * TODO integrare il pannello con il meccanismo di inserimento componenti
 */
public class ImageLabelPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private final ImageIcon img;
  private final InfoLabel label;
  private final String path;

  public ImageLabelPanel(ImageIcon img, String path, Dimension size) {
    this.img = img != null ? img : new ImageIcon(path);
    this.path = path;
    size = size != null ? size : new Dimension(img.getIconWidth(), img.getIconHeight());
    img = new ImageIcon(img.getImage().getScaledInstance(size.width,size.height,Image.SCALE_DEFAULT));
    size = new Dimension(img.getIconWidth(),img.getIconHeight());
    
    this.label = new InfoLabel("");
    this.label.setInfo(path);
    this.label.setLayout(new BorderLayout());
    this.label.setHorizontalAlignment(SwingConstants.CENTER);
    //this.label.setBounds(628, 28, 169, 125);
    this.label.setSize(size);
    
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
    setSize(size);
    setLayout(null);
    
    super.add(label);
    this.label.setIcon(img);
  }

  public ImageLabelPanel(String path, Dimension size) {
    this(new ImageIcon(path),path,size);
  }
  
  public ImageLabelPanel(String path, int width, int height) {
    this(new ImageIcon(path),path,new Dimension(width,height));
  }
  
  public ImageLabelPanel(ImageIcon img, int width, int height) {
    this(img,"",new Dimension(width,height));
  }
  
  public ImageLabelPanel(ImageIcon img, String path, int width, int height) {
    this(img,path,new Dimension(width,height));
  }
  
  public ImageLabelPanel(String path) {
    this(null,path,null);
  }
  
  public ImageLabelPanel(ImageIcon img) {
    this(img,"",null);
  }

  public static ImageLabelPanel getScaledWidth (String path, int width) {
    return new ImageLabelPanel(path,width,-1);
  }

  public static ImageLabelPanel getScaledWidth (ImageIcon img, int width) {
    return new ImageLabelPanel(img,width,-1);
  }

  public static ImageLabelPanel getScaledWidth (ImageIcon img, String path, int width) {
    return new ImageLabelPanel(img,path,width,-1);
  }

  public static ImageLabelPanel getScaledHeight (String path, int height) {
    return new ImageLabelPanel(path,-1,height);
  }

  public static ImageLabelPanel getScaledHeight (ImageIcon img, int height) {
    return new ImageLabelPanel(img,-1,height);
  }

  public static ImageLabelPanel getScaledHeight (ImageIcon img, String path, int height) {
    return new ImageLabelPanel(img,path,-1,height);
  }


  public ImageIcon getImageIcon () {
    return this.img;
  }
  public InfoLabel getLabel () {
    return this.label;
  }

  public String getPath () {
    return this.path;
  }

}