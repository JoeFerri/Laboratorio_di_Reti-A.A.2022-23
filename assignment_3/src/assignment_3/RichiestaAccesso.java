/**
 * @author Giuseppe Ferri
 */
package assignment_3;


import utils.Utils;


/**
 * Struttura dati per la gestione delle informazioni utili
 * alla raccolta, messa in coda, ed elaborazione
 * delle richieste di accesso ai computer da parte degli utenti.
 * Queste strutture sono direttamente gestite dal tutor.
 */
public class RichiestaAccesso implements Comparable<RichiestaAccesso> {

  static private long indexRA = 0;
  private final Utente utente;
  // id = 0 : qualsiasi computer, id != 0 : computer specifico
  private final int id;
  // result = id di un computer libero o 0 altrimenti
  private volatile int result = 0;
  private final long timestamp;
  private final long index = indexRA++;
  
  
  public RichiestaAccesso (Utente utente, int id) {
    this.utente = utente;
    this.id = id;
    this.timestamp = System.currentTimeMillis();
    
    Utils.tprintflushDebug("%s creata.\n",this);
  }

  public RichiestaAccesso (Utente utente) {
    this(utente,0);
  }
  
  public Utente getUtente () {
    return this.utente;
  }
  
  public int getId () {
    return this.id;
  }
  
  public long getTimestamp () {
    return this.timestamp;
  }
  
  // synchronized?
  public void setResult (int id) {
    this.result = id;
  }
  
  // synchronized?
  public int getResult () {
    return this.result;
  }

  //! deve accordarsi con compareTo
  //! @see ConcurrentSkipListSet
  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;

    if (!(o instanceof RichiestaAccesso)) {
      return false;
    }
      
    RichiestaAccesso ra = (RichiestaAccesso) o;
    return this.utente == ra.utente && this.index == ra.index && (this.id - ra.id == 0) && (this.timestamp - ra.timestamp == 0);
  }

  //! deve accordarsi con equals
  //! @see ConcurrentSkipListSet
  @Override
  public int compareTo(RichiestaAccesso o) {
    if (this == o || (this.utente == o.utente && this.index == o.index && (this.id - o.id == 0) && (this.timestamp - o.timestamp == 0))) //? in accordo con equals()
      return 0;
    if (this.getUtente() == o.getUtente()) // caso del Professore
      return this.getId() - o.getId();
    if (this.getUtente().getRuolo().getPriorita() > o.getUtente().getRuolo().getPriorita())
      return -1;
    else if (this.getUtente().getRuolo().getPriorita() < o.getUtente().getRuolo().getPriorita())
      return 1;
      
    return this.getTimestamp() <= o.getTimestamp() ? -1 : 1;
  }
  
  @Override
  public String toString () {
    return "RichiestaAccesso: " + this.getUtente() + ".(id=" + this.getId() + ",result=" + this.getResult() + ",timestamp=" + this.getTimestamp() + ")";
  }
}
