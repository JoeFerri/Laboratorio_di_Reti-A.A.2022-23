/**
 * @author Giuseppe Ferri
 */
package assignment_2;


/**
 * Un oggetto Operabile possiede un'operazione
 * da svolgere.
 */
public interface Operabile<E> {

  /**
   * @return l'operazione da svolgere
   */
  default Operazione<E> getOperazione() {
    return null;
  }
}
