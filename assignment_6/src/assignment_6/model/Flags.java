/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


// flags per gli argomenti passati al programma
public enum Flags {

  /**
   * Il programma è in stato di debugging
   */
  DEBUG(0),

  /**
   * Asserzioni abilitate nel server
   */
  ASSERT(0);

  
  public final int index;
  Flags (int index) {
    this.index = index;
  }

  public static int size () {
    return Flags.values().length;
  }

}
