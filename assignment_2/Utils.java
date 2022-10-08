/**
 * Copyright (C) 2022 Giuseppe Ferri
 * 
 * Utils is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * Utils is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program (see file COPYING).  If not, see <http://www.gnu.org/licenses/>.
 */
package assignment_2;


/**
 * Classe di utilità.
 * 
 * @author Giuseppe Ferri
 */
public final class Utils {

  private Utils () {
    ;
  }
  
  /**
   * Fornisce un intero pseudocasuale.
   * @param max limite superiore
   * @param min limite inferiore
   * @return un intero compreso tra min e max
   */
  static long getRndm (long min, long max) {
    return (long) Math.floor(Math.random()*(max - min + 1) + min);
  }

  /**
   * Stampa su standard output gli argomenti e svuota subito il buffer.
   * @see {@link java.io.PrintStream#printf(String,Object)}
   */
  public static void printflush (String format, Object ... args) {
    System.out.printf(format, args);
    System.out.flush();
  }

  /**
   * Stampa su standard output l'eccezione e il nome del Thread
   * che l'ha generata. Svuota subito il buffer.
   * @see {@link java.io.PrintStream#printf(String,Object)}
   */
  public static void printflushCurrThread (String exception){
    Utils.printflush("%s in %s.\n",exception,Thread.currentThread().getName());
  }
}
