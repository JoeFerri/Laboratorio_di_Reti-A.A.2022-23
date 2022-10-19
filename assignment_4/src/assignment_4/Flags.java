package assignment_4;

// flags per gli argomenti passati al programma
public enum Flags {

  /**
   * Il programma è in stato di debugging
   */
  DEBUG(0),

  /**
   * Sono ignorati gli argomenti passati e
   * vengono elaborati tutti i file contenuti nella cartella assignment_4/data
   */
  TEST_ALL_TEXT_TXT(1),

  /**
   * I caratteri vengono normalizzati.
   * Dopo la normalizzazione 'a' == 'A' == 'à' == 'á'
   * Senza la normalizzazione (default) 'a' == 'A', ma 'à' viene ignorato
   */
  NORMALIZE(2),

  /**
   * ISO Latin Alphabet No. (8 bit)
   */
  ISO_8859_1(3),

  /**
   * Seven-bit ASCII, a.k.a.
   */
  US_ASCII(4),

  /**
   * Sixteen-bit UCS Transformation Format,
   * byte order identified by an optional byte-order mark
   */
  UTF_16(5);

  
  final int index;
  Flags (int index) {
    this.index = index;
  }

  public static int size () {
    return Flags.values().length;
  }

}
