/**
 * @author Giuseppe Ferri
 */
package assignment_4;


import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Struttura dati per tenere traccia delle occorrenze di una lista di file.
 */
public class FilesReport {
  
  private final Map<String,FileReport> fileReports = new HashMap<>();
  private final CharsReport charsReport;
  private String msg = "";
  private boolean error = false;
  private int fileNameLength = 0;


  /**
   * @param arguments argomenti passati da riga di comando
   * @param fileNames la lista di path dei file da associare
   */
  public FilesReport (boolean[] arguments, String ...fileNames) {
    this.charsReport = new ConcurrentCharsReport(arguments);
    String name = null;
    for (String fileName : fileNames) {
      this.fileReports.put(fileName,new FileReport(arguments,fileName));
      name = Paths.get(fileName).getFileName().toString();
      // per la formattazione dell'output su stdout
      if (this.fileNameLength < name.length())
        this.fileNameLength = name.length();
    }
  }


  public Set<Entry<String, FileReport>> entrySetFiles () {
    return this.fileReports.entrySet();
  }

  public Set<Entry<Character,Long>> entrySet () {
    return this.charsReport.entrySet();
  }

  /**
   * Controlla se il carattere `c` è un'occorrenza valida
   * e setta opportunamente il nuovo valore associato alla lettera.
   * @param fileName il file da controllare
   * @param c il carattere del file da controllare
   * @return le tracce dell'elaborazione globale (index 0) e del singolo file (index 1)
   */
  public CheckReport[] checkAndSet (String fileName, char c) {
    CheckReport cr = this.fileReports.get(fileName).checkAndSet(c); // thread to FileReport : 1 to 1
    CheckReport crg = null;
    synchronized (this.charsReport) { // threads to CharsReport : n to 1
      crg = this.charsReport.checkAndSet(c);
    }
    return new CheckReport[]{crg,cr};
  }

  /**
   * Controlla se il carattere `c` è un'occorrenza valida
   * e setta opportunamente il nuovo valore associato alla lettera.
   * @param fileName il file da controllare
   * @param c il carattere del file da controllare
   * @return le tracce dell'elaborazione globale (index 0) e del singolo file (index 1)
   */
  public CheckReport[] checkAndSet (String fileName, int c) {
    return this.checkAndSet(fileName,(char)c);
  }

  /**
   * Invalida le informazioni raccolte sul file e sul report globale.
   * @param fileName il file da invalidare
   * @param msg un messaggio di errore
   */
  public synchronized void invalidate (String fileName, String msg) {
    if (fileName != null)
      this.fileReports.get(fileName).invalidate(msg);
    this.charsReport.invalidate(msg);
    this.msg = msg;
    this.error = true;
  }

  /**
   * Invalida le informazioni raccolte sul file e sul report globale.
   * @param fileName il file da invalidare
   */
  public synchronized void invalidate (String fileName) {
    this.invalidate(fileName,"");
  }

  /**
   * Invalida le informazioni raccolte sul report globale.
   */
  public synchronized void invalidate () {
    this.invalidate(null,"");
  }

  /**
   * @return l'ultimo messaggio di errore registrato
   */
  public String getMsg () {
    return this.msg;
  }

  /**
   * @return true se il file è valido per l'elaborazione o la lettura
   */
  public synchronized boolean isValid () {
    return this.error;
  }

  /**
   * Crea e popola il file di output con le informazioni raccolte
   * sulle occorrenze nei file con il formato:
   * <pre>{@code
<a>,<numero di occorrenze>\n
<b>,<numero di occorrenze>\n
              ...         \n
<z>,<numero di occorrenze>
   * }</pre>
   * @param pathFile il nome (path) del file da generare
   * @throws IOException
   */
  public void toFile (String pathFile) throws IOException {
    FileWriter fileWriter = new FileWriter(pathFile);

    try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
      List<Entry<Character, Long>> entries = new ArrayList<>(this.charsReport.entrySet());
      entries.sort((e1,e2) -> e1.getKey().compareTo(e2.getKey()));

      boolean newline = false;
      for (Entry<Character, Long> entry : entries) {
        if (newline)
          printWriter.printf("%c",'\n');
        if (!newline)
          newline = true;
        printWriter.printf("%c,%d",entry.getKey(),entry.getValue());
      }
    }
  }

  @Override
  public String toString () {
    String sreports = "";
    List<FileReport> lsreports = new ArrayList<>(this.fileReports.values());
    lsreports.sort((r1,r2) -> r1.getFileName().compareTo(r2.getFileName()));
    for (FileReport fr : lsreports)
      sreports += (sreports.length() > 0 ? "\n    " : "    ") + fr.toString(this.fileNameLength);
    return "total:\n" + this.charsReport + "\n\n    fileReports:\n" + sreports + "\n";
  }
  
}
