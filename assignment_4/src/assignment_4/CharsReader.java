/**
 * @author Giuseppe Ferri
 */
package assignment_4;


import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;


/**
 * Task per l'analisi di un singolo file di testo
 * e per il conteggio delle occorrenze (lettere) nello stesso.
 * Il task chiama il metodo {@link assignment_4.FilesReport#checkAndSet(String, int)} per ogni carattere del file.
 * Se il file non esiste o ci sono errori, viene chiamato il metodo {@link assignment_4.FilesReport#invalidate()},
 * che invalida il {@link FileReport} relativo al file, e il {@link CharsReport} globale.
 */
public class CharsReader implements Callable<Boolean> {

  private final String fileName;
  private final Charset charset;
  private final FilesReport filesReport;


  /**
   * Istanzia un nuovo task CharsReader.
   * @param fileName il nome del file da analizzare
   * @param charset la codifica del file di testo
   * @param filesReport la struttura dati su cui registrare le occorrenze
   */
  public CharsReader (String fileName, Charset charset, FilesReport filesReport) {
    this.fileName = fileName;
    this.charset = charset;
    this.filesReport = filesReport;
  }

  @Override
  public Boolean call() throws Exception {
    Path path = Paths.get(this.fileName);
    if (!Files.exists(path)) {
      this.filesReport.invalidate(this.fileName,"Il file non esiste!");
      return false;
    }

    BufferedReader bf = Files.newBufferedReader(path,this.charset);
    int c;
    try {
      while ((c = bf.read()) != -1)
        this.filesReport.checkAndSet(this.fileName,c);
    }
    catch (MalformedInputException e) {
      //! TEST: leggere il file test/data/file_5.utf_16.txt senza passare --utf-16 produce questa eccezione
      this.filesReport.invalidate(this.fileName,"Codifica del file non corrispondente!");
      return false;
    }

    return true;
  }
  
}
