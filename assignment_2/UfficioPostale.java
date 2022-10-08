/**
 * @author Giuseppe Ferri
 */
package assignment_2;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * Un UfficioPostale caratterizzato da un Salone
 * tra l'Ingresso e l'Interno, e da una Saletta interna
 * adiacente agli sportelli.
 */
public class UfficioPostale {

  private final Salone salone;
  private final Interno interno;
  private final int sportelliSize;
  private final long tempoApertura;
  private final Ingresso ingresso;
  
  
  /**
   * Costruisce un UfficioPostale.
   * Se uno sportello resta inattivo oltre keepAliveTime
   * viene chiuso (rimosso dal pool).
   * @param esterno l'Esterno da cui provengono i clienti
   * @param sportelliSize numero di sportelli
   * @param salaInternaSize numero di posti a sedere nella Saletta
   * @param keepAliveTime tempo limite di inattività (millisecondi)
   * @param tempoApertura tempo di aperturna dell'UfficioPostale (millisecondi)
   */
  public UfficioPostale (Esterno<Cliente> esterno, int sportelliSize, int salaInternaSize, int keepAliveTime, long tempoApertura) {
    super();
    this.salone = new Salone();
    this.interno = new Interno(sportelliSize, salaInternaSize, keepAliveTime, TimeUnit.MILLISECONDS);
    this.sportelliSize = sportelliSize;
    long time = System.currentTimeMillis();
    this.tempoApertura = time + tempoApertura;
    this.ingresso = new Ingresso(esterno,this.salone);

    Utils.printflush("\nUfficioPostale creato.\n\n");
  }
  
  /**
   * Apre l'UfficioPostale.
   */
  public void open () throws InterruptedException {
    this.ingresso.start();
    // simulo un ritardo tra l'apertura dell'Ingresso e il Salone
    Thread.sleep(1000);
    this.salone.open();

    List<Future<?>> futures = new ArrayList<Future<?>>();
    Future<?> f;
    Cliente cliente;

    Utils.printflush("UfficioPostale aperto.\n\n");
    
    // Attività dell'UfficioPostale
    while (System.currentTimeMillis() < this.tempoApertura) {
      cliente = (Cliente) this.salone.poll(1000,TimeUnit.MILLISECONDS);
      while (cliente != null) {
        try {
          f = this.interno.submit(cliente.getOperazione());
          futures.add(f);
          cliente = null;
        }
        catch (RejectedExecutionException e) {
          Utils.printflush("\n%s aspetta tra il Salone e la saletta.\n\n",cliente);
          Thread.sleep(200);
        }
      }
    }
    
    Utils.printflush("\nUfficioPostale chiuso.\n\n");
    
    this.salone.close();
    this.ingresso.close();
    
    Utils.printflush("Terminazione delle operazioni dell'ufficio...\n\n");
    long time = 0; // tempo totale operazioni eseguite
    for(Future<?> future : futures)
      try {
        time += (long)future.get(10,TimeUnit.SECONDS);
      }
      catch (InterruptedException | ExecutionException | TimeoutException e) {
        Utils.printflushCurrThread("Exception");
      }
    
    this.interno.shutdown();
    try {
      if (!this.interno.awaitTermination(10,TimeUnit.SECONDS)) {
        this.interno.shutdownNow();
        if (!this.interno.awaitTermination(10,TimeUnit.SECONDS))
            Utils.printflushCurrThread("Il pool non termina!");
      }
    } catch (InterruptedException ie) {
      this.interno.shutdownNow();
      Thread.currentThread().interrupt();
    }
    Utils.printflush("\nOperazioni dell'ufficio terminate!\n\n");
    Utils.printflush("\ntempo totale operazioni eseguite: %dms.\n",time);
    Utils.printflush("\ntempo medio operazioni per sportello: %dms.\n\n",time/this.sportelliSize);
  }
}
