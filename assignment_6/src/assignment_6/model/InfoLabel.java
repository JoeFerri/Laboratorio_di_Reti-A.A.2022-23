/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import javax.swing.Icon;
import javax.swing.JLabel;


/**
 * Una JLabel con un campo di informazioni.
 */
public class InfoLabel extends JLabel {
  
  private String info = "";


  InfoLabel () {
    super();
  }

  public InfoLabel(Icon image) {
    super(image);
  }

  public InfoLabel(Icon image, int horizontalAlignment) {
    super(image,horizontalAlignment);
  }

  public InfoLabel(String text) {
    super(text);
  }

  public InfoLabel(String text, Icon icon, int horizontalAlignment) {
    super(text,icon,horizontalAlignment);
  }
  
  public InfoLabel(String text, int horizontalAlignment) {
    super(text,horizontalAlignment);
  }

  public void setInfo (String info) {
    this.info = info;
  }

  public String getInfo () {
    return this.info;
  }
}
