/**
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package assignment_2;


import java.util.concurrent.ArrayBlockingQueue;


/**
 * La Saletta interna dell'UfficioPostale.
 * 
 * @author Giuseppe Ferri
 */
public class Saletta extends ArrayBlockingQueue<Runnable> {

  private static final long serialVersionUID = 1L;

  /**
   * @param k numero di posti a sedere nella Saletta
   */
  public Saletta(int k) {
    super(k);
  }
  
}
