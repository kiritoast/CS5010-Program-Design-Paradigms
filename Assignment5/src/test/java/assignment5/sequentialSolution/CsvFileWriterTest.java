package assignment5.sequentialSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {

  private CsvFileWriter csvFileWriter;
  private String filePath;

  @BeforeEach
  void setUp() {
    this.filePath = "DataSet" + File.separator + "test.csv";
    this.csvFileWriter = new CsvFileWriter(filePath);
  }


  @Test
  void writeData() throws IOException {

    String[] testData = {"John", "Doe", "john.doe@example.com"};
    csvFileWriter.writeData(testData);
    csvFileWriter.closeFile();

    String expectedOutput = "\"John\",\"Doe\",\"john.doe@example.com\"\n";
    String actualOutput = new String(Files.readAllBytes(Paths.get(filePath)));
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  void testCloseThrowException() {
    CsvFileWriter csvFileWriter = new CsvFileWriter(filePath);
    String[] testData = {"John", "Doe", "john.doe@example.com"};

    csvFileWriter.writeData(testData);
    csvFileWriter.closeFile();
    Assertions.assertThrows(RuntimeException.class, () -> csvFileWriter.closeFile());
  }

  @Test
  public void testEquals() {
    CsvFileWriter writer1 = new CsvFileWriter("DataSet" + File.separator + "test.csv");
    CsvFileWriter writer2 = writer1;
    CsvFileWriter writer3 = new CsvFileWriter("DataSet" + File.separator + "test1.csv");
    CsvFileWriter writer4 = null;

    assertTrue(writer1.equals(writer2)); // same file path
    assertFalse(writer1.equals(writer3)); // different file path
    assertFalse(writer1.equals(writer4)); // different file path

    Assertions.assertEquals(this.csvFileWriter, this.csvFileWriter);
    Assertions.assertFalse(this.csvFileWriter.equals(null));
    Assertions.assertFalse(this.csvFileWriter.equals(1));
  }

  @Test
  public void testHashCode() {
    assertEquals(csvFileWriter.hashCode(), csvFileWriter.hashCode()); // same file path
  }

  @Test
  public void testToString() {
    assertEquals(csvFileWriter.toString(), csvFileWriter.toString());
  }


}