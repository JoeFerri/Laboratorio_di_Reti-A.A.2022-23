/**
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package assignment_2;


/**
 * Astrae il concetto di Esterno.
 * I clienti sono inizialmente nello spazio esterno
 * all'ufficio postale (in realtà l'interfaccia utilizza
 * un generic per generalizzare).
 * 
 * @author Giuseppe Ferri
 */
public interface Esterno<E> {

  /**
   * @return un elemento dell'Esterno
   */
  E get();
}
