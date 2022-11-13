/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;


/**
 * Classe di utilità per la memorizzazione delle labels usate dal programma.
 */
public class Labels {

  private static final Map<KeyLabel,JLabel> map;
  
  public static final String startTitleString   = "DUNGEON ADVENTURES";
  public static final String loginString        = "login";
  public static final String roundString        = "round";
  public static final String wonString          = "vinte";
  public static final String lostString         = "perse";
  public static final String matchesString      = "partite";
  public static final String fightString        = "combatti";
  public static final String drinkPotionString  = "<html>bevi<br/>pozione</html>";
  public static final String abandonsString     = "abbandona";
  public static final String playerString       = "giocatore";
  public static final String monsterString      = "mostro";
  public static final String levelString        = "liv.";


  static {
    map = new HashMap<>();

    set(Labels.startTitleString,KeyFont.TITLE,KeyLabel.START_TITLE,JLabel.CENTER);
    set(Labels.loginString,KeyFont.LOGIN,KeyLabel.LOGIN,JLabel.CENTER);
    set(Labels.roundString,KeyFont.INFO_TOKEN,KeyLabel.ROUND,JLabel.CENTER);
    set(Labels.wonString,KeyFont.INFO_TOKEN_BOTTOM,KeyLabel.WON,JLabel.CENTER);
    set(Labels.lostString,KeyFont.INFO_TOKEN_BOTTOM,KeyLabel.LOST,JLabel.CENTER);
    set(Labels.matchesString,KeyFont.INFO_TOKEN_BOTTOM,KeyLabel.MATCHES,JLabel.CENTER);
    set(Labels.fightString,KeyFont.ACTIONS_TOKEN,KeyLabel.FIGHT,JLabel.CENTER);
    set(Labels.drinkPotionString,KeyFont.ACTIONS_TOKEN,KeyLabel.DRINK_POTION,JLabel.CENTER);
    set(Labels.abandonsString,KeyFont.ACTIONS_TOKEN,KeyLabel.ABANDONS,JLabel.CENTER);
    set(Labels.playerString,KeyFont.INFO_TOKEN,KeyLabel.PLAYER_TITLE,JLabel.CENTER);
    set(Labels.monsterString,KeyFont.INFO_TOKEN,KeyLabel.MONSTER_TITLE,JLabel.CENTER);
    set("",KeyFont.USERNAME,KeyLabel.USERNAME,JLabel.CENTER);
    set(Labels.levelString,KeyFont.USERNAME,KeyLabel.LEVEL,JLabel.CENTER);
    set("",KeyFont.USERNAME,KeyLabel.MONSTER_HEALTH_VALUE,JLabel.CENTER);
    set("",KeyFont.USERNAME,KeyLabel.MONSTER_MAX_HEALTH_VALUE,JLabel.CENTER);
    set("",KeyFont.USERNAME,KeyLabel.PLAYER_HEALTH_VALUE,JLabel.CENTER);
    set("",KeyFont.USERNAME,KeyLabel.PLAYER_MAX_HEALTH_VALUE,JLabel.CENTER);
    set("",KeyFont.USERNAME,KeyLabel.PLAYER_POTION_VALUE,JLabel.CENTER);
    set("",KeyFont.USERNAME,KeyLabel.PLAYER_MAX_POTION_VALUE,JLabel.CENTER);
    set("",KeyFont.USERNAME,KeyLabel.NEW_LOGIN,JLabel.CENTER);
    set("",KeyFont.USERNAME,KeyLabel.LOAD_LOGIN,JLabel.CENTER);
  }

  private Labels () {
    ;
  }

  public static JLabel get (KeyLabel key) {
    return map.get(key);
  }

  private static void set (String text, KeyFont keyFont, KeyLabel keyLabel, int alignment) {
    JLabel label = new JLabel(text);
    label.setFont(Fonts.get(keyFont));
    label.setHorizontalAlignment(alignment);
    map.put(keyLabel,label);
  }
}
