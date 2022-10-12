/**
 * @author Giuseppe Ferri
 */
package assignment_3;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import utils.Utils;


/**
 * Astrae l'idea di utente studente
 */
public class Studente extends Utente {
  
  // quante volte ha usato un computer
  private int ciclo = 0;

  
  public Studente (String nome, int cicliMin, int cicliMax) {
    super(nome,
          Ruolo.build("Studente"),
            cicliMin,
              cicliMax);
  }
  
  public Studente (String nome) {
    this(nome,0,0);
  }
  
  public Studente () {
    this(null);
  }

  /**
   * @param size numero di studenti da istanziare
   * @param cicliMin minimo numero di accessi al laboratorio
   * @param cicliMax massimo numero di accessi al laboratorio
   * @return una collezione di studenti
   */
  public static Collection<Studente> factory (int size, int cicliMin, int cicliMax) {
    Set<Studente> utenti = new HashSet<Studente>(size);
    for (int i = 0; i < size; i++)
      utenti.add(new Studente(null,cicliMin,cicliMax));
    assert utenti.size() == size : "Errore nella generazione degli studenti!"; //!
    return utenti;
  }

  public static Collection<Studente> factory (int size) {
    return Studente.factory(size,0,0);
  }
  
  /**
   * Ciclo di "vita" dello studente.
   */
  @Override
  public Boolean call() throws Exception {
    Utils.tprintflushDebug("%s nel ciclo %d.\n",this,this.ciclo);
    if (this.ciclo >= this.cicli)
      return false;
    assert this.ciclo < this.cicli : "Lo studente sta eseguendo più operazioni del dovuto!"; //!
    Computer computer = this.getTutor().getAccessoComputer(this); //! bloccante
    assert computer != null : "Errore di accesso, computer assegnato nullo!"; //!
    Utils.printflush("%s -> '''%s'''\n",this,computer.getEseguiOutput("Geany"));
    this.getTutor().rilasciaAccessoComputer(this);
    assert this.getTutor().hasAccessoComputer(this) == false : "Lo studente ha ancora accesso al computer!"; //!
    
    return (++this.ciclo) < this.cicli;
  }
}
