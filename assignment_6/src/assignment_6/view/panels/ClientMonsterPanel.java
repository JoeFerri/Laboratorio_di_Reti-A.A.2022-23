/**
 * @author Giuseppe Ferri
 */
package assignment_6.view.panels;


import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import assignment_6.model.Colors;
import assignment_6.model.Images;
import assignment_6.model.Monster;
import assignment_6.model.KeyColor;
import assignment_6.model.KeyImagePath;
import assignment_6.model.KeyLabel;
import assignment_6.model.Labels;


/**
 * Sotto-pannello del {@link assignment_6.view.panels.ClientPanel}.
 * Usato per mostrare i dati del mostro.
 */
public class ClientMonsterPanel extends JPanel {

  
  public ClientMonsterPanel (Monster monster) {
    
    this.setLayout(new GridBagLayout());
    this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    this.setBackground(Colors.get(KeyColor.MAIN));
    
    JLabel label = null;
    String[] tokens = null;
    ImageLabelPanel img = null;
    JProgressBar bar = null;
    GridBagConstraints gbc = null;
    
    // title
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    this.add(Labels.get(KeyLabel.MONSTER_TITLE),gbc);
    
    // livello
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    gbc.insets = new Insets(0,0,15,0);
    label = Labels.get(KeyLabel.LEVEL);
    tokens = label.getText().split(" ");
    label.setText(tokens[0] + " " + monster.level);
    this.add(label,gbc);
    
    // valore massimo salute
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.insets = new Insets(0,10,0,0);
    label = Labels.get(KeyLabel.MONSTER_MAX_HEALTH_VALUE);
    label.setText("" + monster.maxHealth);
    this.add(label,gbc);

    // salute
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.LINE_END;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.insets = new Insets(0,10,0,0);

    bar = new JProgressBar(JProgressBar.VERTICAL,0,monster.maxHealth);
    Dimension sizeHealthBar = bar.getPreferredSize();
    sizeHealthBar.width = (int)(sizeHealthBar.width*1.8);

    bar.setSize(sizeHealthBar);
    bar.setValue(monster.getHealth());
    bar.setBackground(Colors.get(KeyColor.MAIN));
    bar.setForeground(Colors.get(KeyColor.HEALTH));
    bar.setStringPainted(true);
    this.add(bar,gbc);
    
    // mostro
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.LINE_END;
    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.insets = new Insets(0,10,0,0);
    // img = ImageLabelPanel.getScaledWidth(monster.image,(int)(Screen.width*0.13));
    img = ImageLabelPanel.getScaledWidth(monster.image,250);
    this.add(img,gbc);
    
    // salute icon
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.insets = new Insets(3,10,0,0);
    img = ImageLabelPanel.getScaledWidth(Images.getPath(KeyImagePath.HEALTH_ICON_24),sizeHealthBar.width);
    img.setBackground(Colors.get(KeyColor.MAIN));
    this.add(img,gbc);
    
    // valore salute
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.insets = new Insets(0,10,0,0);
    label = Labels.get(KeyLabel.MONSTER_HEALTH_VALUE);
    label.setText("" + monster.getHealth());
    this.add(label,gbc);
  }
}