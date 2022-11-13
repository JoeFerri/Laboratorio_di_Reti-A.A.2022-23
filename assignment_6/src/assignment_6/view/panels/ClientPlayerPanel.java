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
import assignment_6.model.Player;
import assignment_6.model.KeyColor;
import assignment_6.model.KeyImagePath;
import assignment_6.model.KeyLabel;
import assignment_6.model.Labels;


/**
 * Sotto-pannello del {@link assignment_6.view.panels.ClientPanel}.
 * Usato per mostrare i dati del giocatore.
 */
public class ClientPlayerPanel extends JPanel {

  
  public ClientPlayerPanel (Player player) {
    
    this.setLayout(new GridBagLayout());
    this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    this.setBackground(Colors.get(KeyColor.MAIN));
    
    JLabel label = null;
    ImageLabelPanel img = null;
    JProgressBar bar = null;
    GridBagConstraints gbc = null;
    
    // title
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    this.add(Labels.get(KeyLabel.PLAYER_TITLE),gbc);
    
    // username
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 3;
    gbc.insets = new Insets(0,0,15,0);
    label = Labels.get(KeyLabel.USERNAME);
    label.setText(player.name);
    this.add(label,gbc);
    
    // valore massimo pozione
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 1;
    gbc.gridy = 2;
    label = Labels.get(KeyLabel.PLAYER_MAX_POTION_VALUE);
    label.setText("" + player.maxPotion);
    this.add(label,gbc);
    
    // valore massimo salute
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 2;
    gbc.gridy = 2;
    gbc.insets = new Insets(0,10,0,0);
    label = Labels.get(KeyLabel.PLAYER_MAX_HEALTH_VALUE);
    label.setText("" + player.maxHealth);
    this.add(label,gbc);
    
    // giocatore
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.LINE_START;
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.insets = new Insets(0,0,0,10);
    // img = ImageLabelPanel.getScaledWidth(player.image,(int)(Screen.width*0.13));
    img = ImageLabelPanel.getScaledWidth(player.image,250);
    this.add(img,gbc);

    // pozione
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.LINE_END;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 1;
    gbc.gridy = 3;

    bar = new JProgressBar(JProgressBar.VERTICAL,0,player.maxPotion);
    Dimension sizePotionBar = bar.getPreferredSize();
    sizePotionBar.width = (int)(sizePotionBar.width*2);

    bar.setSize(sizePotionBar);
    bar.setValue(player.getPotion());
    bar.setBackground(Colors.get(KeyColor.MAIN));
    bar.setForeground(Colors.get(KeyColor.POTION));
    bar.setStringPainted(true);
    bar.setOpaque(false);
    this.add(bar,gbc);

    // salute
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.LINE_END;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 2;
    gbc.gridy = 3;
    gbc.insets = new Insets(0,10,0,0);

    bar = new JProgressBar(JProgressBar.VERTICAL,0,player.maxHealth);
    Dimension sizeHealthBar = bar.getPreferredSize();
    sizeHealthBar.width = (int)(sizeHealthBar.width*1.8);

    bar.setSize(sizeHealthBar);
    bar.setValue(player.getHealth());
    bar.setBackground(Colors.get(KeyColor.MAIN));
    bar.setForeground(Colors.get(KeyColor.HEALTH));
    bar.setStringPainted(true);
    this.add(bar,gbc);
    
    // pozione icon
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 1;
    gbc.gridy = 4;
    gbc.insets = new Insets(3,0,0,0);
    img = ImageLabelPanel.getScaledWidth(Images.getPath(KeyImagePath.POTION_ICON_24),sizePotionBar.width);
    img.setBackground(Colors.get(KeyColor.MAIN));
    this.add(img,gbc);
    
    // salute icon
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 2;
    gbc.gridy = 4;
    gbc.insets = new Insets(3,10,0,0);
    img = ImageLabelPanel.getScaledWidth(Images.getPath(KeyImagePath.HEALTH_ICON_24),sizeHealthBar.width);
    img.setBackground(Colors.get(KeyColor.MAIN));
    this.add(img,gbc);
    
    // valore pozione
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 1;
    gbc.gridy = 5;
    label = Labels.get(KeyLabel.PLAYER_POTION_VALUE);
    label.setText("" + player.getPotion());
    this.add(label,gbc);
    
    // valore salute
    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 2;
    gbc.gridy = 5;
    gbc.insets = new Insets(0,10,0,0);
    label = Labels.get(KeyLabel.PLAYER_HEALTH_VALUE);
    label.setText("" + player.getHealth());
    this.add(label,gbc);
  }
}