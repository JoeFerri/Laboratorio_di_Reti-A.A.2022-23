/**
 * @author Giuseppe Ferri
 */
package assignment_3;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
// import utils.AccessMonitor;
// import utils.AccessMonitorSimple;
import utils.Utils;


/**
 * Struttura dati che rappresenta una singola schedulazione di un utente.
 */
public class SchedulazioneUtente implements Runnable {

  private final Utente utente;
  private final BlockingQueue<Utente> utentiTerminati;
  // private final AccessMonitor accessMonitor = new AccessMonitorSimple();
  private volatile boolean terminazioneAvvenuta = false;
  
  
  public SchedulazioneUtente (Utente utente, BlockingQueue<Utente> utentiTerminati) {
    this.utente = utente;
    this.utentiTerminati = utentiTerminati;
    
    Utils.tprintflushDebug("%s creata.\n",this);
  }
  
  
  public Utente getUtente () {
    return this.utente;
  }
  
  private synchronized boolean getTerminazioneAvvenuta () {
    return this.terminazioneAvvenuta;
  }
  
  private synchronized void setTerminazioneAvvenuta () {
    this.terminazioneAvvenuta = true;
  }

  @Override
  public void run() {
    //// se lo scheduler crea nuovi Thread di schedulazione
    //// e questi vengono avviati, vanno a vuoto perché
    //// un Thread ha già preso l'accesso al monitor `AccessMonitor`
    // if (this.accessMonitor.getAccess() && !this.getTerminazioneAvvenuta()) {
    if (!this.getTerminazioneAvvenuta()) {
      Utils.tprintflushDebug("Inizio %s.\n",this);
      
      boolean continua = true;
      ExecutorService executorService = Executors.newSingleThreadExecutor();
      
        Utils.tprintflushDebug("%s sottomissione nel service.\n",this);
        Future<Boolean> future = executorService.submit(this.utente);
  
        try {
          Utils.tprintflushDebug("%s in attesa di future.\n",this);
          continua = future.get();
          Utils.tprintflushDebug("%s -> future con valore %b.\n",this,continua);
        }
        catch (ExecutionException e) {
          Utils.printflushCurrThread(e);
        }
        catch (InterruptedException e) {
          Utils.printflushCurrThread(e);
        }
      
      try {
        executorService.shutdown();
        Utils.tprintflushDebug("%s in attesa di terminazione...\n",this);
        executorService.awaitTermination(1,TimeUnit.SECONDS);
        if (!continua) {
          Utils.tprintflushDebug("%s inserisce in coda terminati l'utente %s.\n",this,utente);
          assert !this.utentiTerminati.contains(utente) : "La coda dei terminati contiene già " + utente; //!
          this.utentiTerminati.put(utente);
          this.setTerminazioneAvvenuta();
        }
      }
      catch (InterruptedException e) {
        Utils.printflushCurrThread(e);
      }
      finally {
        Utils.tprintflushDebug("%s terminazione forzata...\n",this);
        executorService.shutdownNow();
        // this.accessMonitor.endAccess();
      }
    }
  }

  public String toString () {
    return "SchedulazioneUtente-\"" + this.getUtente() + "\"";
  }
}
