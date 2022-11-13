/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import java.io.Serializable;

import javax.swing.ImageIcon;

import static com.gf.utils.Utils.printflushDebug;


/**
 * Astrae il concetto di personaggio del gioco.
 */
public abstract class Character implements Serializable, DataServerProvider {
  
  public final String name;       // username|file_name
  public final String title;      // Giocatore|Mostro
  public final String imagePath;
  public final transient ImageIcon image;

  public final int maxHealth;
  private int health;


  private Character (String name, String title, int health, int maxHealth, String imagePath, ImageIcon image) {
    this.name = name;
    this.title = title;
    this.health = health;
    this.maxHealth = maxHealth;
    this.imagePath = imagePath;
    this.image = image;
    printflushDebug("Character.new() -> <%s>\n",this);
  }


  public Character (String name, String title, int health, int maxHealth, String imagePath) {
    this(name,title,health,maxHealth,imagePath,imagePath.equals("") ? null : Images.getByPath(imagePath));
  }


  public Character (String name, String title, int health, int maxHealth, ImageIcon image) {
    this(name,title,health,maxHealth,"",image);
  }

  public void setHealth (int health) {
    this.health = health;
  }

  public int getHealth () {
    return this.health;
  }
  
  /**
   * Converte lo stato interno dell'istanza in una stringa
   * comprensibile dal server.
   */
  public String toDataServer () {
    return this.name + "," + this.title + "," + this.health + "," + this.maxHealth + "," + this.imagePath;
  }

  /**
   * Converte una stringa ricevuta dal server in una istanza Character.
   */
  public static Character fromDataServer (String data) {
    printflushDebug("Character.fromDataServer() -> data: <%s>\n",data);
    String[] tokens = data.split(",");
    assert tokens.length == 5 : "Errore nel parsing del Character!"; 
    return new Character (tokens[0],tokens[1],Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]),tokens[4]){};
  }

  @Override public String toString () {
    return Character.this.toDataServer();
  }
}
