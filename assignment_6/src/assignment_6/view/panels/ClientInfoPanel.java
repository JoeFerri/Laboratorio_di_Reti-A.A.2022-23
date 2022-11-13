/**
 * @author Giuseppe Ferri
 */
package assignment_6.view.panels;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import assignment_6.model.Colors;
import assignment_6.model.KeyColor;
import assignment_6.model.KeyLabel;
import assignment_6.model.Labels;
import assignment_6.model.UserRecord;


/**
 * Sotto-pannello del {@link assignment_6.view.panels.ClientPanel}.
 * Usato per mostrare i dati della partita.
 */
public class ClientInfoPanel extends JPanel {

  
  public ClientInfoPanel (UserRecord userRecord) {
    
    this.setLayout(new GridBagLayout());
    this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    this.setBackground(Colors.get(KeyColor.INFO_TOKEN));
    
    JLabel label = null;
    String[] tokens = null;
    GridBagConstraints gbc = null;
    
    // round
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    label = Labels.get(KeyLabel.ROUND);
    tokens = label.getText().split(" ");
    label.setText(tokens[0] + " " + userRecord.round);
    this.add(label,gbc);
    
    // won
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.gridx = 0;
    gbc.gridy = 1;
    label = Labels.get(KeyLabel.WON);
    tokens = label.getText().split(" ");
    label.setText(tokens[0] + " " + userRecord.won);
    this.add(label,gbc);
    
    // matches
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 1;
    gbc.gridy = 1;
    label = Labels.get(KeyLabel.MATCHES);
    tokens = label.getText().split(" ");
    label.setText(tokens[0] + " " + userRecord.matches);
    this.add(label,gbc);
    
    // lost
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.gridx = 2;
    gbc.gridy = 1;
    label = Labels.get(KeyLabel.LOST);
    tokens = label.getText().split(" ");
    label.setText(tokens[0] + " " + userRecord.lost);
    this.add(label,gbc);
  }
}