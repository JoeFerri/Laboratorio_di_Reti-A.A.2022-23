/**
 * avvio: java assignment_2.Assignment
 * 
 * @author Giuseppe Ferri
 */
package assignment_2;


/**
 * Il programma simula un ufficio postale con 4 sportelli
 * e una saletta interna.
 * L'ufficio comunica con l'esterno tramite un salone grande
 * il quale a sua volta riceve clienti attraverso un ingresso.
 */
public class Assignment {

  public static void main(String[] args) {
    
    int sportelliSize = 4;
    int salaInternaSize = 10;
    int keepAliveTime = 1000;  // millisecondi
    long tempoApertura = 5000; // millisecondi

    Esterno<Cliente> esterno = Cliente::new;
    UfficioPostale ufficio = new UfficioPostale(esterno,sportelliSize,salaInternaSize,keepAliveTime,tempoApertura);
    
    try {
      ufficio.open();
    } catch (InterruptedException e) {
      Utils.printflushCurrThread("InterruptedException");
    }
  }

}
