/**
 * @author Giuseppe Ferri
 */
package assignment_6.view.panels;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import assignment_6.model.Colors;
import assignment_6.model.KeyColor;
import assignment_6.model.KeyLabel;
import assignment_6.model.Labels;


/**
 * Pannello principale della finestra di partenza.
 */
public class StartPanel extends JPanel {

  private final JButton loginButton;

  
  public StartPanel () {
    
    this.setLayout(new GridBagLayout());
    this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    this.setBackground(Colors.get(KeyColor.MAIN));
    
    GridBagConstraints gbc = null;

    
    // glue
    gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.VERTICAL;
    gbc.weighty = 0.5;
    gbc.gridx = 1;
    gbc.gridy = 0;
    this.add(Box.createGlue(), gbc);

    // title
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1.0;
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 3;
    this.add(Labels.get(KeyLabel.START_TITLE),gbc);

    // glue
    gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.VERTICAL;
    gbc.weighty = 0.5;
    gbc.gridx = 1;
    gbc.gridy = 2;
    this.add(Box.createGlue(), gbc);

    // login
    JButton button = new JButton();
    button.setOpaque(false);
    button.setContentAreaFilled(false);
    button.add(Labels.get(KeyLabel.LOGIN));
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.LAST_LINE_END;
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 3;
    gbc.insets = new Insets(0,0,2,4);
    this.add(button,gbc);

    this.loginButton = button;
  }

  public JButton getLoginButton () {
    return this.loginButton;
  }
}
