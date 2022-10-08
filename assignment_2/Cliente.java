/**
 * @author Giuseppe Ferri
 */
package assignment_2;


/**
 * Astrae il concetto di cliente che 
 * possiede un'operazione da svolgere.
 */
public class Cliente implements Operabile<Cliente> {

  private final Operazione<Cliente> operazione;
  private long numerino = Ingresso.NESSUN_NUMERINO; // numerino preso all'ingresso

  public Cliente() {
    super();
    this.operazione = new Operazione<Cliente>(this);
  }
  

  @Override
  public Operazione<Cliente> getOperazione() {
    return this.operazione;
  }
  
  public long getNumerino () {
    return this.numerino;
  }
  
  public long setNumerino (long numerino) {
    this.numerino = numerino;
    return numerino;
  }

  @Override
  public String toString () {
    return "cliente[" + (this.numerino == Ingresso.NESSUN_NUMERINO ? "senza-numerino" : this.numerino) + "]";
  }

}
