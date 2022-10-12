/**
 * @author Giuseppe Ferri
 */
package assignment_3;


import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import utils.Utils;


/**
 * Astrae l'idea di utente professore
 */
public class Professore extends Utente {
  
  // quante volte ha usato il laboratorio
  private int ciclo = 0;

  
  public Professore (String nome, int cicliMin, int cicliMax) {
    super(nome,
            Ruolo.build("Professore",2),
              cicliMin,
                cicliMax);
  }
  
  public Professore (String nome) {
    this(nome,0,0);
  }

  public Professore () {
    this(null);
  }

  
  /**
   * @param size numero di professori da istanziare
   * @param cicliMin minimo numero di accessi al laboratorio
   * @param cicliMax massimo numero di accessi al laboratorio
   * @return una collezione di professori
   */
  public static Collection<Professore> factory (int size, int cicliMin, int cicliMax) {
    Set<Professore> utenti = new HashSet<Professore>(size);
    for (int i = 0; i < size; i++)
      utenti.add(new Professore(null,cicliMin,cicliMax));
    assert utenti.size() == size : "Errore nella generazione dei professori!"; //!
    return utenti;
  }
  
  public static Collection<Professore> factory (int size) {
    return Professore.factory(size,0,0);
  }

  /**
   * Ciclo di "vita" del professore.
   */
  @Override
  public Boolean call() throws Exception {
    Utils.tprintflushDebug("%s nel ciclo %d.\n",this,this.ciclo);
    if (this.ciclo >= this.cicli)
      return false;
    assert this.ciclo < this.cicli : "Il professore sta eseguendo più operazioni del dovuto!"; //!
    Computer[] computers = this.getTutor().getAccessoComputerPieno(this);
    assert computers != null : "Errore di accesso, lista di computer assegnati nulla!"; //!
    assert computers.length == this.getTutor().getComputersLength() : "Accesso pieno ai computer non soddisfatto!"; //!
    Arrays.stream(computers).forEach(computer -> Utils.printflush("%s -> '''%s'''\n",this,computer.getEseguiOutput("Samba")));
    this.getTutor().rilasciaAccessoComputer(this);
    assert this.getTutor().hasAccessoComputer(this) == false : "Il professore ha ancora accesso al computer!"; //!
    
    return (++this.ciclo) < this.cicli;
  }
}
