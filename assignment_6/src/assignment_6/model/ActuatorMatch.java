/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import java.util.function.UnaryOperator;


/**
 * Utilizzata nella classe {@link assignment_6.Server}
 * per la modifica dello stato del gioco.
 */
@FunctionalInterface
public interface ActuatorMatch extends UnaryOperator<UserRecord> {
  ;
}
