/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


/**
 * Una istanza DataServerProvider è in grado di fornire una
 * copia del proprio stato interno in formato stringa
 * secondo la sintassi comprensibile dal server.
 */
public interface DataServerProvider {
  
  public String toDataServer ();

}
