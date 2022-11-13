/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import static com.gf.utils.Utils.printflushDebug;


/**
 * Astrae il concetto di mostro del gioco.
 */
public class Monster extends Character {
  
  public final int level;

  public static final Monster EMPTY_MONSTER =
    new Monster("undefined",-1,-1,"undefined",-1);

  
  public Monster (String name, int health, int maxHealth, String imagePath, int level) {
    super(name,"Mostro",health,maxHealth,imagePath);
    this.level = level;
  }
  
  @Override
  public String toDataServer () {
    return super.toDataServer() + "," + this.level;
  }

  /**
   * Converte una stringa ricevuta dal server in una istanza Monster.
   */
  public static Monster fromDataServer (String data) {
    printflushDebug("Monster.fromDataServer() -> data: <%s>\n",data);
    String[] tokens = data.split(",");
    assert tokens.length == 6 : "Errore nel parsing del Monster!";
    return new Monster( tokens[0],
                        //? tokens[1] -> title
                        Integer.parseInt(tokens[2]),
                        Integer.parseInt(tokens[3]),
                        tokens[4],
                        Integer.parseInt(tokens[5]));
  }
}
