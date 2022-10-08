/**
 * Copyright (C) 2022 Giuseppe Ferri
 * 
 * Assignment_2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * Assignment_2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program (see file COPYING).  If not, see <http://www.gnu.org/licenses/>.
 */
package assignment_2;


/**
 * Il programma simula un ufficio postale con 4 sportelli
 * e una saletta interna.
 * L'ufficio comunica con l'esterno tramite un salone grande
 * il quale a sua volta riceve clienti attraverso un ingresso.
 * 
 * @author Giuseppe Ferri
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
