/**
 * @author Giuseppe Ferri
 */
package assignment_3;


import java.util.concurrent.Callable;
import utils.Utils;
import utils.Nome;


/**
 * Astrae l'idea di utente.
 */
public abstract class Utente implements Callable<Boolean> {

  public static final int CICLI_MIN = 1;
  public static final int CICLI_MAX = 5;
  
  private final String nome;
  private Ruolo ruolo = null;
  private Tutor tutor = null;
  // quante volte l'utente può usare al massimo un computer
  protected final int cicli;
  
  /**
   * Istanzia un utente.
   * @param nome il nome proprio dell'utente
   * @param ruolo il ruolo dell'utente
   * @param cicliMin minimo numero di accessi al laboratorio
   * @param cicliMax massimo numero di accessi al laboratorio
   */
  public Utente (String nome, Ruolo ruolo, int cicliMin, int cicliMax) {
    this.nome = nome != null ? nome : Nome.getRndm();
    this.ruolo = ruolo != null ? ruolo : Ruolo.NESSUN_RUOLO;
    int cicliSx = cicliMin > 0 ? cicliMin : CICLI_MIN;
    int cicliDx = cicliMax > 0 ? cicliMax : CICLI_MAX;
    if (cicliSx > cicliDx)
      throw new IllegalArgumentException(
        "Il valore minimo dei cicli deve essere minore del valore massimo!");

    this.cicli = 
      Utils.getRndm(cicliSx,cicliDx);

    Utils.tprintflush("%s creato/a.\n",this);
  }
  
  public Utente (String nome, Ruolo ruolo) {
    this(nome,ruolo,0,0);
  }
  
  public Utente (Ruolo ruolo) {
    this(null,ruolo);
  }
  
  public Utente (String nome) {
    this(nome,null);
  }
  
  public Utente () {
    this((String)null);
  }

  
  public Ruolo getRuolo () {
    return this.ruolo;
  }
  
  public void setTutor (Tutor tutor) {
    this.tutor = tutor;
  }
  
  public Tutor getTutor () {
    return this.tutor;
  }
  
  @Override
  public String toString () {
    return this.ruolo + " " + this.nome;
  }
}
