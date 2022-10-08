/**
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package assignment_2;


import java.util.concurrent.Callable;


/**
 * Un oggetto Operazione è un servizio posseduto da un oggeto di tipo E
 * che deve essere svolto da uno sportello (Thread).
 * 
 * @author Giuseppe Ferri
 */
public class Operazione<E> implements Callable<Long> {

  private final long DURATION_MIN = 100;  // millisecondi
  private final long DURATION_MAX = 1500; // millisecondi
  private final long time; // tempo per l'operazione
  private final E element; // il possessore dell'operazione
  
  public Operazione (E element) {
    this.time = Utils.getRndm(DURATION_MIN, DURATION_MAX);
    this.element = element;
  }
  
  @Override
  public Long call() throws Exception {
    try {
      Utils.printflush("Sportello[%s] sta operando %s...\n",this.getId(),this.element);
      Thread.sleep(this.time); // simulo l'operazione
      Utils.printflush("Sportello[%s] ha terminato di operare %s\n",this.getId(),this.element);
    } catch (InterruptedException e) {
      Utils.printflush("InterruptedException in %s.\n",this.getId());
    }
    return this.time;
  }
  
  /**
   * @return l'identificatore del Thread che esegue l'operazione
   */
  public String getId () {
    String id = Thread.currentThread().getName();
    return id.substring(id.lastIndexOf('-') + 1);
  }
  
}
