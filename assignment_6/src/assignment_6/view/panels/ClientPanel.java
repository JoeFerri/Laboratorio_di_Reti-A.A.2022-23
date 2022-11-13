/**
 * @author Giuseppe Ferri
 */
package assignment_6.view.panels;


import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import assignment_6.model.Colors;
import assignment_6.model.UserRecord;
import assignment_6.model.KeyColor;


/**
 * Pannello principale della finestra di gioco.
 */
public class ClientPanel extends JPanel {

  private final ClientActionsPanel clientActionsPanel;


  public ClientPanel (UserRecord userRecord) {
    
    this.setLayout(new GridBagLayout());
    this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    this.setBackground(Colors.get(KeyColor.MAIN));
    
    GridBagConstraints gbc = null;

    // giocatore
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.LINE_START;
    gbc.gridx = 0;
    gbc.gridy = 0;
    this.add(new ClientPlayerPanel(userRecord.player),gbc);

    // mostro
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.LINE_END;
    gbc.gridx = 2;
    gbc.gridy = 0;
    this.add(new ClientMonsterPanel(userRecord.monster),gbc);

    // azioni
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.PAGE_END;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 3;
    gbc.insets = new Insets(10,10,20,10);
    this.clientActionsPanel = new ClientActionsPanel();
    this.add(this.clientActionsPanel,gbc);

    // info
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.PAGE_END;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 3;
    this.add(new ClientInfoPanel(userRecord),gbc);
  }
  
  public JButton getFightButton () {
    return this.clientActionsPanel.getFightButton();
  }

  public JButton getDrinkPotionButton () {
    return this.clientActionsPanel.getDrinkPotionButton();
  }

  public JButton getAbandonsButton () {
    return this.clientActionsPanel.getAbandonsButton();
  }
}
