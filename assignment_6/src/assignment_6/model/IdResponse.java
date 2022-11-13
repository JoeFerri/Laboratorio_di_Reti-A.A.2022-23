/**
 * @author Giuseppe Ferri
 */
package assignment_6.model;


import java.util.Arrays;


/**
 * Definisce i codici identificativi dei comandi
 * che il client invia al server e delle
 * risposte del server.
 */
public enum IdResponse {

  UNDEFINED         (0),
  OK                (1),
  ERROR             (2),
  EXIT              (3),
  FIGHT             (4),
  DRINK_POTION      (5),
  ABANDONS          (6),
  LOGIN_SAVE        (7),
  LOGIN_LOAD        (8),
  LOGIN_EXISTS      (9),
  LOGIN_NOT_FOUND   (10),
  LOGIN_DELETE      (11),
  RECORD_LOAD       (12),
  RECORD_SAVE       (13),
  RECORD_DELETE     (14),
  RECORD_INIT       (15);


  
  final public int index;
  IdResponse (int index) {
    this.index = index;
  }

  static public IdResponse byIndex (int index) {
    return Arrays.asList(IdResponse.values())
            .stream()
            .filter(ir -> ir.index == index)
            .findFirst()
            .orElse(UNDEFINED);
  }
}
