/**
 * @author Giuseppe Ferri
 */
package assignment_2;


import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;


/**
 * Un Salone dell'UfficioPostale adiacente alla Saletta interna.
 * Ha una capacità illimitata.
 */
public class Salone extends LinkedBlockingQueue<Operabile<?>> {
  
  private Lock lock = new ReentrantLock();
  private Condition condVar = lock.newCondition();
  private volatile boolean is_open = false;
  private volatile boolean liberato = false;

  private static final long serialVersionUID = 1L;

  public Salone() {
    super();
  }
  
  @Override
  public void put(Operabile<?> cl) throws InterruptedException {
    lock.lock();
    try {
      while (this.is_open == false) {
        try {
          Utils.printflush("\nIl Salone è ancora chiuso...\n\n");
          condVar.await();
        } catch (InterruptedException e) {
          Utils.printflushCurrThread("InterruptedException");
        }
      }
      if (!this.liberato) // caso del Thread risvegliato da libera()
        super.put(cl);
    } finally {
      lock.unlock();
    }
  }

  /**
   * Apre il Salone.
   */
  public void open () {
    lock.lock();
    this.is_open = true;
    Utils.printflush("Salone aperto.\n\n");
    this.condVar.signalAll();
    lock.unlock();
  }
  
  /**
   * Chiude il Salone.
   */
  public void close () {
    lock.lock();
    this.is_open = false;
    Utils.printflush("Salone chiuso.\n\n");
    this.condVar.signalAll();
    lock.unlock();
  }
  
  public synchronized boolean isOpen () {
    return this.is_open;
  }
  
  /**
   * Fa uscire tutti i clienti dal Salone.
   * @return una collezione con i clienti rimossi
   */
  public ArrayList<Operabile<?>> libera () {
    lock.lock();
    ArrayList<Operabile<?>> lista = new ArrayList<Operabile<?>>();
    this.drainTo(lista);
    
    printLiberati(lista);

    this.liberato = true;
    this.condVar.signalAll();
    lock.unlock();
    
    return lista;
  }

  /**
   * Stampa su standard output le informazioni
   * sui clienti liberati dal Salone.
   * @param lista i clienti liberati
   */
  private void printLiberati (ArrayList<Operabile<?>> lista) {
    String liberato_dal = " liberato dal Salone.\n";
    String output = "\nLiberati dal Salone " + lista.size() + " clienti.\n\n" +
      lista.subList(0, (lista.size() < 10 ? lista.size() -1 : 9))
        .stream()
        .map(Object::toString)
        .collect(Collectors.joining(liberato_dal,"",liberato_dal));
    if (lista.size() >= 10) {
      output += "... altri liberati dal Salone...\n";
      output += lista.get(lista.size()-1) + " liberato dal Salone.\n\n";
    }
    Utils.printflush(output);
  }
}
