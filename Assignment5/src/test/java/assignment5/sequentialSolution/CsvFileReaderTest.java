package assignment5.sequentialSolution;

//import static org.junit.Assert.assertArrayEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {

  private CsvFileReader csvFileReader;
  private final String TEST_FILE_PATH = "DataSet" + File.separator + "courses.csv";


  @BeforeEach
  void setUp() {
    csvFileReader = new CsvFileReader(TEST_FILE_PATH);
  }

  @AfterEach
  public void tearDown() {
    csvFileReader.closeFile();
  }

  @Test
  void readData() {
    String expected = "code_module";
    String[] actual = csvFileReader.readData();
    Assertions.assertEquals(expected, actual[0]);
  }

  @Test
  public void testReadData_withInvalidFile_throwsRuntimeException() {
    String invalidFilePath = "invalid-file.csv";
    Assertions.assertThrows(RuntimeException.class, () -> new CsvFileReader(invalidFilePath));
  }

  @Test
  public void testEquals() {
    CsvFileReader reader1 = new CsvFileReader("DataSet" + File.separator + "courses.csv");
    CsvFileReader reader2 = reader1;
    CsvFileReader reader3 = new CsvFileReader("DataSet" + File.separator + "studentVle.csv");
    CsvFileReader reader4 = null;

    Assertions.assertTrue(reader1.equals(reader2)); // same file path
    Assertions.assertFalse(reader1.equals(reader3)); // different file path
    Assertions.assertFalse(reader1.equals(reader4)); // different file path

    Assertions.assertEquals(this.csvFileReader, this.csvFileReader);
    Assertions.assertFalse(this.csvFileReader.equals(null));
    Assertions.assertFalse(this.csvFileReader.equals(1));
  }

  @Test
  public void testHashCode() {
    Assertions.assertEquals(csvFileReader.hashCode(), csvFileReader.hashCode()); // same file path
  }

  @Test
  public void testToString() {
    Assertions.assertEquals(csvFileReader.toString(), csvFileReader.toString());
  }

}