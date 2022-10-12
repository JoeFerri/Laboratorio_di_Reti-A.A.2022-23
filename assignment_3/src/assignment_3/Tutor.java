/**
 * @author Giuseppe Ferri
 */
package assignment_3;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import utils.Utils;


/**
 * Astrae l'idea di tutor.
 * Il tutor si interpone tra gli utenti che richiedono di
 * accedere ai computer, e il laboratorio che contiene i computer.
 * Gestisce le richieste di accesso e ordina al laboratorio
 * di associare un computer ad un utente.
 */
public class Tutor {
  
  private Set<RichiestaAccesso> setRichieste =
    new ConcurrentSkipListSet<RichiestaAccesso>();
  private final Laboratorio lab;
  private final boolean tabelle;

  
  /**
   * Istanzia un tutor.
   * @param lab un laboratorio
   * @param tabelle se true vengono stampate a video le code degli utenti attivi o in attesa
   */
  public Tutor (Laboratorio lab, boolean tabelle) {
    this.lab = lab;
    this.tabelle = tabelle;
    
    Utils.tprintflush("%s creato.\n",this);
  }

  public Tutor (Laboratorio lab) {
    this(lab,false);
  }
  

  /**
   * @return il numero totale di computer del laboratorio
   */
  public int getComputersLength () {
    return this.lab.getComputersLength();
  }
  
  /**
   * Cuore del programma, si occupa di elaborare le richieste di accesso.
   * Tiene conto dell'ordine relativo alle priorità e al tempo in cui
   * sono state fatte le richieste.
   * Elimina le richieste elaborate.
   */
  private synchronized void elaboraRichiesteDiAccesso () {
    //assert setRichieste.size() > 0  : "Non ci sono richieste di accesso in memoria!"; //!
    List<RichiestaAccesso> raDaEliminare = new ArrayList<RichiestaAccesso>();
    // gli id vincolati sono gli id delle richieste elaborate in precedenza
    // se ad esempio un tesista con id = 2 non è stato soddisfatto
    // l'id = 2 viene vincolato in modo che uno studente
    // elaborato successivamente non possa ottenere l'accesso al computer con id = 2
    List<Integer> idsVincolati = new ArrayList<>();
    RichiestaAccesso assertRa = null; //! assert variable

    // ----------------------------------------------------------------------------------------- stampa tabelle
    if (this.tabelle) {
      StringBuilder boutput = new StringBuilder ("\nTabella Richieste Di Accesso:\n");
      for (RichiestaAccesso ra : this.setRichieste)
        boutput.append("|-> " + ra + "\n");
      boutput.append("--------------------------\n");

      boutput.append("Tabella Associazioni computer-utente:\n");
      this.lab.getAssociazioni()
        .stream()
          .sorted(Map.Entry.comparingByKey()) 
            .forEach(entry -> boutput.append("|-> Computer[" + entry.getKey() + "] Utente: " + entry.getValue() + "\n") );
      Utils.printflush(boutput + "--------------------------\n\n");
    }
    // ----------------------------------------------------------------------------------------- stampa

    for (RichiestaAccesso ra : this.setRichieste) {
      assert assertRa == null || assertRa.getUtente().getRuolo().getPriorita() >= ra.getUtente().getRuolo().getPriorita() : 
        "Le richieste di accesso non sono ordinate correttamente!"; //!
      // Questo assert non ha senso nel caso l'utente sia un Professore
      //assert !this.lab.isAssociatoUtenteAComputer(ra.getUtente()) : ra.getUtente() + " non dovrebbe essere associato a un computer!"; //!

      if (ra.getId() == 0) { //? caso: qualsiasi computer è ok per uno studente
        for (int id : this.lab.getComputersIds()) {
          if (!idsVincolati.contains(id)) {
            ra.setResult(this.lab.associaUtenteAComputer(ra.getUtente(),id));
            if (ra.getResult() != 0)
              break;
          }
        }
      }
      else if (!idsVincolati.contains(ra.getId())) //? caso: computer specifico per Tesista/Professore
        ra.setResult(this.lab.associaUtenteAComputer(ra.getUtente(),ra.getId()));

      if (ra.getId() != 0 && ra.getResult() == 0)
        idsVincolati.add(ra.getId());

      if (ra.getResult() != 0)
        raDaEliminare.add(ra);
      assertRa = ra; //! assert variable
    }
    for (RichiestaAccesso ra : raDaEliminare)
      this.setRichieste.remove(ra);
  }

  /**
   * Metodo invocato da un utente per ottenere l'accesso ad un computer.
   * @param utente l'utente che chiede di accedere
   * @param id l'identificativo del computer (0 per qualsiasi computer)
   * @return il computer associato all'utente
   * @throws InterruptedException
   */
  public synchronized Computer getAccessoComputer (Utente utente, int id) throws InterruptedException {
    RichiestaAccesso ra = new RichiestaAccesso(utente,id);
    boolean setted = this.setRichieste.add(ra);
    assert setted == true : ra + " non è stata inserita in memoria! " + this.setRichieste; //!

    while (ra.getResult() == 0) {
      ////assert this.setRichieste.contains(ra) : "Risultato della richiesta di accesso = 0 ma la richiesta non è presente in memoria!"; //!
      this.elaboraRichiesteDiAccesso();
      if (ra.getResult() != 0)
        break;
      ////assert !this.lab.isAssociatoUtenteAComputer(utente) : utente + " non dovrebbe essere associato a un computer!"; //!
      Utils.tprintflushDebug("%s si mette in attesa...\n",utente);
      wait();
    }
    
    return this.lab.getComputer(ra.getResult());
  }
  
  public synchronized Computer getAccessoComputer (Utente utente) throws InterruptedException {
    return this.getAccessoComputer(utente,0);
  }

  public synchronized Computer[] getAccessoComputer (Utente utente, int[] ids) throws InterruptedException {
    List<RichiestaAccesso> ras = new ArrayList<>();
    for (int id : ids) {
      RichiestaAccesso ra = new RichiestaAccesso(utente,id);
      boolean setted = this.setRichieste.add(ra);
      assert setted == true : ra + " non è stata inserita in memoria! " + this.setRichieste; //!
      ras.add(ra);
    }

    while (ras.stream().filter(ra -> ra.getResult() == 0).count() > 0) {
      /*
      assert ras.stream().filter(ra -> ra.getResult() == 0)
                .filter(ra -> this.setRichieste.contains(ra)).count() ==
                    ras.stream().filter(ra -> ra.getResult() == 0).count() :
                        "Uno o più risultati delle richieste di accesso = 0 ma le richieste non sono presenti in memoria!"; //!
      */
      this.elaboraRichiesteDiAccesso();
      if (ras.stream().filter(ra -> ra.getResult() == 0).count() == 0)
        break;
      // NOTE: manca assert utente associato a computer
      Utils.tprintflushDebug("%s si mette in attesa...\n",utente);
      wait();
    }
    
    Computer[] computers = new Computer[ids.length];
    for (int index = 0; index < ids.length; index++)
      computers[index] = this.lab.getComputer(ids[index]);
    assert Arrays.stream(computers).filter(computer -> computer == null).count() == 0 :
      "Errore nel recupero dei computer (null)!"; //!
          
    return computers;
  }

  // l'accesso a tutto il laboratorio è implementato con la
  // generazione di tante richieste di accesso quanti sono i computer
  public synchronized Computer[] getAccessoComputerPieno (Utente utente) throws InterruptedException {
    return getAccessoComputer(utente,this.lab.getComputersIds());
  }
  
  public synchronized void rilasciaAccessoComputer (Utente utente) {
    this.lab.disassociaUtenteDaComputer(utente);
    assert !this.hasAccessoComputer(utente) : "L'utente ha ancora accesso al computer!"; //!
    notifyAll();
  }

  public synchronized boolean hasAccessoComputer (Utente utente) {
    return this.lab.isAssociatoUtenteAComputer(utente);
  }
  
  @Override
  public String toString () {
    return "Tutor";
  }
}
