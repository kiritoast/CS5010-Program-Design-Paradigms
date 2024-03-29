package assignment5.sequentialSolution;

import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;
import java.util.Objects;

/**
 * Represent the customized CSV reader with the information of the source file path.
 */
public class CsvFileReader {

  private FileReader fileReader;
  private CSVReader csvReader;

  /**
   * Creates a customized CSV reader given the source file path
   *
   * @param filePath the source file path
   */
  public CsvFileReader(String filePath) {
    try {
      this.fileReader = new FileReader(filePath);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    this.csvReader = new CSVReader(this.fileReader);
  }

  /**
   * Read and return the next line
   *
   * @return the string array which contains the content of next line
   */
  public String[] readData() {
    try {
      return (this.csvReader.readNext());
    } catch (IOException | CsvValidationException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Close all the helper csv reader and helper file reader
   */
  public void closeFile() {
    try {
      this.csvReader.close();
      this.fileReader.close();
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
    CsvFileReader that = (CsvFileReader) o;
    return csvReader.equals(that.csvReader);
  }

  /**
   * This method is used to generate the hash code of json reader class
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(csvReader);
  }

  /**
   * This method is used to generate the string representation of json reader class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "CsvFileReader{" +
        "csvReader=" + csvReader +
        '}';
  }
}
