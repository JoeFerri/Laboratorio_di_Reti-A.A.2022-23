/**
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package assignment_2;


/**
 * Un oggetto Operabile possiede un'operazione
 * da svolgere.
 * 
 * @author Giuseppe Ferri
 */
public interface Operabile<E> {

  /**
   * @return l'operazione da svolgere
   */
  default Operazione<E> getOperazione() {
    return null;
  }
}
