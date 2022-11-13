/**
 * @author Giuseppe Ferri
 */
package assignment_6.view.frames;


import javax.swing.JButton;
import javax.swing.JFrame;

import assignment_6.model.UserRecord;
import assignment_6.view.panels.ClientPanel;


/**
 * Finestra grafica del gioco.
 */
public class ClientFrame extends JFrame {

  private ClientPanel panel = null;
  private UserRecord userRecord = null;


  public ClientFrame (UserRecord userRecord) {
    
    // this.setSize((int)(Screen.width*0.4),(int)(Screen.height*0.6));
    this.setSize(760,640);

    //this.setLayout(new BorderLayout());
    this.setLocationRelativeTo(null);
    this.setResizable(false);
  }
  
  public ClientFrame () {
    this(null);
  }

  public UserRecord getUserRecord () {
    return this.userRecord;
  }
  
  public JButton getFightButton () {
    return this.panel != null ? this.panel.getFightButton() : null;
  }

  public JButton getDrinkPotionButton () {
    return this.panel != null ? this.panel.getDrinkPotionButton() : null;
  }

  public JButton getAbandonsButton () {
    return this.panel != null ? this.panel.getAbandonsButton() : null;
  }

  /**
   * Crea un nuovo Panel a seguito di un cambiamento
   * di stato del gioco.
   */
  public void set (UserRecord userRecord) {
    if (userRecord != null) {
      ClientPanel panel = new ClientPanel(userRecord);
      this.setContentPane(panel);
      if (this.panel != null) {
        this.getContentPane().invalidate();
        this.getContentPane().validate();
        this.getContentPane().repaint();
      }

      this.userRecord = userRecord;
      this.panel = panel;
    }
  }
}
