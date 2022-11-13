/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import static com.gf.utils.Utils.printflushDebug;


/**
 * Astrae il concetto di giocatore del gioco.
 */
public class Player extends Character {
  
  public final int maxPotion;
  private int potion;

  public static final Player EMPTY_PLAYER =
    new Player("undefined",-1,-1,"undefined",-1,-1);


  public Player (String name, int health, int maxHealth, String imagePath, int potion, int maxPotion) {
    super(name,"Giocatore",health,maxHealth,imagePath);
    this.maxPotion = maxPotion;
    this.potion = potion;
  }

  public void setPotion (int potion) {
    this.potion = potion;
  }

  public int getPotion () {
    return this.potion;
  }
  
  @Override
  public String toDataServer () {
    return super.toDataServer() + "," + this.potion + "," + this.maxPotion;
  }

  /**
   * Converte una stringa ricevuta dal server in una istanza Player.
   */
  public static Player fromDataServer (String data) {
    printflushDebug("Player.fromDataServer() -> data: <%s>\n",data);
    String[] tokens = data.split(",");
    assert tokens.length == 7 : "Errore nel parsing del Player!";
    return new Player(tokens[0],
                      //? tokens[1] -> title
                      Integer.parseInt(tokens[2]),
                      Integer.parseInt(tokens[3]),
                      tokens[4],
                      Integer.parseInt(tokens[5]),
                      Integer.parseInt(tokens[6]));
  }
  
}
