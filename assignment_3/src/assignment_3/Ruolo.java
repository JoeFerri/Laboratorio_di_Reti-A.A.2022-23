/**
 * @author Giuseppe Ferri
 */
package assignment_3;


import java.util.HashMap;


/**
 * Astrae l'idea di ruolo con priorità.
 * La classe è implementata con il pattern singleton:
 * <pre>{@code
 * r1 = build("A",1);
 * r2 = build("A",2);
 * r1 == r2 == {"A",1} -> true}</pre>
 */
public class Ruolo {

  private static final HashMap<String,Ruolo> map = new HashMap<String,Ruolo>();
  public static final Ruolo NESSUN_RUOLO = Ruolo.build("");
  private String etichetta = "";
  private int priorita = 0;
  
  
  private Ruolo (String etichetta, int priorita) {
    this.etichetta = etichetta;
    this.priorita = priorita;
    map.put(etichetta, this);
  }
  
  /**
   * @param etichetta il nome del ruolo
   * @param priorita la priorità del ruolo
   * @return una nuova istanza o una vecchia istanza con la stessa etichetta 
   */
  public static Ruolo build (String etichetta, int priorita) {
    Ruolo ruolo = map.get(etichetta);
    return ruolo != null ? ruolo : new Ruolo(etichetta,priorita);
  }
  
  public static Ruolo build (String etichetta) {
    return build(etichetta,0);
  }
  
  public int getPriorita () {
    return this.priorita;
  }
  
  @Override
  public String toString () {
    return this.etichetta;
  }
}
