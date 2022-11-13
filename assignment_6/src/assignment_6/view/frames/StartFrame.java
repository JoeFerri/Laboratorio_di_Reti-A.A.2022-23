/**
 * @author Giuseppe Ferri
 */
package assignment_6.view.frames;


import javax.swing.JButton;
import javax.swing.JFrame;

import assignment_6.view.panels.StartPanel;


/**
 * Finestra grafica di partenza del gioco.
 */
public class StartFrame extends JFrame {

  private final StartPanel panel;
  
  public StartFrame () {
    
    //this.setSize((int)(Screen.width*0.3),(int)(Screen.height*0.5));
    this.setSize(560,530);

    //this.setLayout(new BorderLayout());
    this.setLocationRelativeTo(null);
    this.setResizable(false);

    StartPanel panel = new StartPanel();
    this.panel = panel;
    this.getContentPane().add(panel);
    //frame.pack();
  }

  public JButton getLoginButton () {
    return this.panel.getLoginButton();
  }
}
