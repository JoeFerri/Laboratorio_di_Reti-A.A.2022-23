/**
 * @author Giuseppe Ferri
 */
package assignment_3;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import utils.Utils;


/**
 * Astrae l'idea di utente tesista.
 * Ad ogni tesista è associato un software.
 */
public class Tesista extends Utente {
  
  // quante volte ha usato un computer
  private int ciclo = 0;
  
  private Software software = null;
  private int computerId = -1;

  
  public Tesista (String nome, Software software, int cicliMin, int cicliMax) {
    super(nome,
            Ruolo.build("Tesista",1),
              cicliMin,
                cicliMax);
    this.software = software != null ? software : Software.getRndm();
  }
  
  public Tesista (String nome, Software software) {
    this(nome,software,0,0);
  }
  
  public Tesista (Software software) {
    this(null,software);
  }
  
  public Tesista (String nome) {
    this(nome,null);
  }
  
  public Tesista () {
    this((String)null);
  }


  public Software getSoftware () {
    return this.software;
  }
  
  public synchronized void setComputerId (int id) {
    assert id > 0 : "Errore settaggio, computer id deve essere maggiore di 0!"; //!
    this.computerId = id;
    Utils.tprintflushDebug("%s settato con id %d.\n",this,this.computerId);
  }

  /**
   * @param size numero di tesisti da istanziare
   * @param cicliMin minimo numero di accessi al laboratorio
   * @param cicliMax massimo numero di accessi al laboratorio
   * @return una collezione di tesisti
   */
  public static Collection<Tesista> factory (int size, int cicliMin, int cicliMax) {
    Set<Tesista> utenti = new HashSet<Tesista>(size);
    for (int i = 0; i < size; i++)
      utenti.add(new Tesista(null,null,cicliMin,cicliMax));
    assert utenti.size() == size : "Errore nella generazione dei tesisti!"; //!
    return utenti;
  }

  public static Collection<Tesista> factory (int size) {
    return Tesista.factory(size,0,0);
  }

  /**
   * Ciclo di "vita" del tesista.
   */
  @Override
  public Boolean call() throws Exception {
    Utils.tprintflushDebug("%s nel ciclo %d.\n",this,this.ciclo);
    if (this.ciclo >= this.cicli)
      return false;
    assert this.ciclo < this.cicli : "Il tesista " + this + " sta eseguendo più operazioni del dovuto!"; //!
    Computer computer = this.getTutor().getAccessoComputer(this,this.computerId); //! bloccante
    assert computer != null : "Errore di accesso, computer assegnato nullo!"; //!
    assert computer.getId() == this.computerId :
      "\n" + Thread.currentThread() + ":" + this + " -> " +
      "Il computer id " + computer.getId() + " non corrisponde all'id " + this.computerId + "!";//!
    Utils.printflush("%s -> '''%s'''\n",this,computer.getEseguiOutput(this.software));
    this.getTutor().rilasciaAccessoComputer(this); //! bloccante
    assert this.getTutor().hasAccessoComputer(this) == false : "Il tesista ha ancora accesso al computer!"; //!
    
    return (++this.ciclo) < this.cicli;
  }

  @Override
  public String toString () {
    return super.toString() + (!Utils.preferences.getBoolean("DEBUG", false) ? "" :
      (this.getSoftware() != null ? ("-" + this.getSoftware()) : "") +
        ((this.computerId != -1 && this.computerId != 0) ? (":computerId-" + this.computerId) : ""));
  }
}
