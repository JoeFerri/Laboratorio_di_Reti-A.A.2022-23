/**
 * @author Giuseppe Ferri
 */
package assignment_2;


/**
 * Classe di utilità.
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
