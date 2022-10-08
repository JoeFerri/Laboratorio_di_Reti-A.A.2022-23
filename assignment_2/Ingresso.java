/**
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package assignment_2;


/**
 * Un oggetto Ingresso mette in collegamento
 * l'Esterno con il Salone, e si occupa
 * del passaggio dei clienti dal primo al secondo.
 * Ad ogni Cliente viene assegnato un numerino (long).
 * 
 * @author Giuseppe Ferri
 */
public class Ingresso extends Thread {

  private boolean is_open = false;

  public final static long NESSUN_NUMERINO = 0;
  private static long numerino = 0;

  //? durata minima e massima dell'apertura dell'ingresso
  private final long DURATION_MIN = 10; // millisecondi
  private final long DURATION_MAX = 80; // millisecondi
  
  private final Esterno<Cliente> esterno;
  private final Salone salone;
  
  public Ingresso (Esterno<Cliente> esterno, Salone salone) {
    this.esterno = esterno;
    this.salone = salone;
  }

  /**
   * Chiude l'Ingresso.
   */
  public synchronized void close () {
    this.is_open = false;
    Utils.printflush("\nIngresso tra Salone ed Esterno chiuso.\n\n");

    this.salone.libera();
  }

  @Override
  public void run() {
    this.is_open = true;
    Utils.printflush("Ingresso tra Salone ed Esterno aperto.\n\n");
    
    Cliente cliente = null;
    while (this.is_open) {
      try {
        // un cliente passa dall'esterno all'ingresso
        cliente = this.esterno.get();
        cliente.setNumerino(++Ingresso.numerino);
        // il cliente passa dall'ingresso al salone grande
        this.salone.put(cliente);
        Utils.printflush("%s entrato nel salone.\n",cliente);
        // simulo un ritardo tra l'ingresso dei clienti
        Thread.sleep(Utils.getRndm(DURATION_MIN, DURATION_MAX));
      } catch (InterruptedException e) {
        Utils.printflushCurrThread("InterruptedException");
      }
    }
  }

}
