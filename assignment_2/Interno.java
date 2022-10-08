/**
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package assignment_2;


import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * L'Interno dell'UfficioPostale composto dagli sportelli
 * e da una piccola Saletta di attesa.
 * 
 * @author Giuseppe Ferri
 */
public class Interno extends ThreadPoolExecutor {

  /**
   * Costruisce un Interno.
   * Se uno sportello resta inattivo oltre keepAliveTime
   * viene chiuso (rimosso dal pool).
   * @param sportelliSize numero di sportelli
   * @param salaInternaSize numero di posti a sedere nella Saletta
   * @param keepAliveTime tempo limite di inattività
   * @param unit unità di misura del tempo
   */
  public Interno(int sportelliSize, int salaInternaSize, long keepAliveTime, TimeUnit unit) {
    super(sportelliSize,sportelliSize,keepAliveTime,unit,new Saletta(salaInternaSize));
  }
  
  @Override
  public String toString () {
    return "Interno";
  }

}
