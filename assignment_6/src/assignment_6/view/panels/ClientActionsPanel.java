/**
 * @author Giuseppe Ferri
 */
package assignment_6.view.panels;


import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import assignment_6.model.Colors;
import assignment_6.model.KeyColor;
import assignment_6.model.KeyLabel;
import assignment_6.model.Labels;


/**
 * Sotto-pannello del {@link assignment_6.view.panels.ClientPanel}.
 * Usato per mostrare i pulsanti per le azioni di gioco.
 */
public class ClientActionsPanel extends JPanel {

  private final JButton fightButton;
  private final JButton drinkPotionButton;
  private final JButton abandonsButton;

  
  public ClientActionsPanel () {
    
    this.setLayout(new GridBagLayout());
    this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    this.setBackground(Colors.get(KeyColor.MAIN));
    
    JButton fightButton = null;
    JButton drinkPotionButton = null;
    JButton abandonsButton = null;
    Dimension size = null, tempSize = null;
    GridBagConstraints gbc = null;
    GridBagConstraints gbcGlue = null;

    // bevi pozione
    drinkPotionButton = new JButton();
    drinkPotionButton.setOpaque(false);
    drinkPotionButton.setContentAreaFilled(false);
    drinkPotionButton.add(Labels.get(KeyLabel.DRINK_POTION));
    size = drinkPotionButton.getPreferredSize();

    // combatti
    fightButton = new JButton();
    fightButton.setOpaque(false);
    fightButton.setContentAreaFilled(false);
    fightButton.add(Labels.get(KeyLabel.FIGHT));
    tempSize = fightButton.getPreferredSize();
    tempSize.height = size.height;
    fightButton.setPreferredSize(tempSize);

    // abbandona
    abandonsButton = new JButton();
    abandonsButton.setOpaque(false);
    abandonsButton.setContentAreaFilled(false);
    abandonsButton.add(Labels.get(KeyLabel.ABANDONS));
    tempSize = abandonsButton.getPreferredSize();
    tempSize.height = size.height;
    abandonsButton.setPreferredSize(tempSize);

    // generale
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.NONE;
    gbc.weightx = 1.0;
    gbc.gridy = 0;

    // glue
    gbcGlue = new GridBagConstraints();
    gbcGlue.fill = GridBagConstraints.HORIZONTAL;
    gbcGlue.weightx = 1.0;
    gbcGlue.gridy = 0;

    // combatti
    gbc.gridx = 0;
    this.fightButton = fightButton;
    this.add(fightButton,gbc);

    // glue
    gbcGlue.gridx = 1;
    this.add(Box.createGlue(), gbc);

    // bevi pozione
    gbc.gridx = 2;
    this.drinkPotionButton = drinkPotionButton;
    this.add(drinkPotionButton,gbc);

    // glue
    gbcGlue.gridx = 3;
    this.add(Box.createGlue(), gbc);

    // abbandona
    gbc.gridx = 4;
    this.abandonsButton = abandonsButton;
    this.add(abandonsButton,gbc);
  }

  public JButton getFightButton () {
    return this.fightButton;
  }

  public JButton getDrinkPotionButton () {
    return this.drinkPotionButton;
  }

  public JButton getAbandonsButton () {
    return this.abandonsButton;
  }
}
