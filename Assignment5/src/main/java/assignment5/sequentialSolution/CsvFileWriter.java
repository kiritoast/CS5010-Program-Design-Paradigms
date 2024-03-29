package assignment5.sequentialSolution;

import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVWriter;
import java.util.Objects;

/**
 * Represent the customized CSV writer with the information of the output file path.
 */
public class CsvFileWriter {

  private FileWriter fileWriter;
  private CSVWriter csvWriter;

  /**
   * Creates a customized CSV writer given the output file path
   *
   * @param filePath the output file path
   */
  public CsvFileWriter(String filePath) {
    try {
      this.fileWriter = new FileWriter(filePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.csvWriter = new CSVWriter(this.fileWriter);
  }

  /**
   * Write the provided content to the end of output file
   *
   * @param data the provided content
   */
  public void writeData(String[] data) {
    this.csvWriter.writeNext(data);
  }

  /**
   * Close all the helper csv reader and helper file reader
   */
  public void closeFile() {
    try {
      this.csvWriter.close();
      this.fileWriter.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * This method is used to compare two instances of json reader class
   *
   * @param o instance to be compared
   * @return true or false
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CsvFileWriter that = (CsvFileWriter) o;
    return csvWriter.equals(that.csvWriter);
  }

  /**
   * This method is used to generate the hash code of json reader class
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(csvWriter);
  }

  /**
   * This method is used to generate the string representation of json reader class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "CsvFileWriter{" +
        "csvWriter=" + csvWriter +
        '}';
  }
}
