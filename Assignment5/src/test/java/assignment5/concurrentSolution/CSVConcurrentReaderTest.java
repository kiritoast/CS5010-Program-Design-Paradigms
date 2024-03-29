package assignment5.concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CSVConcurrentReaderTest {

  private String testDataPath;
  private CSVConcurrentReader csvConcurrentReader;

  @BeforeEach
  void setUp() {
    File testDirectory = new File(
        "src" + File.separator + "test" + File.separator + "java" + File.separator + "assignment5"
            + File.separator + "TestDataSet");
    this.testDataPath = testDirectory.getAbsolutePath();
    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    courseAndClick.put("AAA_2013J", new ConcurrentHashMap<String, Integer>());
    this.csvConcurrentReader = new CSVConcurrentReader(
        Paths.get(this.testDataPath, "studentVle.csv").toString(), courseAndClick);

  }

  @Test
  void testReadCSVFileToMap() {
    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    ConcurrentMap<String, Integer> dataAndClick = new ConcurrentHashMap<>();
    dataAndClick.put("-10", 6);
    courseAndClick.put("AAA_2013J", dataAndClick);
    CSVConcurrentReader csvConcurrentReaderTest = new CSVConcurrentReader(
        Paths.get(this.testDataPath, "studentVle.csv").toString(), courseAndClick);
    this.csvConcurrentReader.readCSVFileToMap();
    Assertions.assertTrue(this.csvConcurrentReader.equals(csvConcurrentReaderTest));
  }

  @Test
  void testEquals() {
    this.csvConcurrentReader.readCSVFileToMap();

    Assertions.assertEquals(this.csvConcurrentReader, this.csvConcurrentReader);
    Assertions.assertFalse(this.csvConcurrentReader.equals(null));
    Assertions.assertFalse(this.csvConcurrentReader.equals(1));

    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    ConcurrentMap<String, Integer> dataAndClick = new ConcurrentHashMap<>();
    dataAndClick.put("-10", 6);
    courseAndClick.put("AAA_2013J", dataAndClick);

    CSVConcurrentReader csvConcurrentReaderTest1 = new CSVConcurrentReader(
        Paths.get(this.testDataPath, "studentVleTest.csv").toString(), courseAndClick);
    Assertions.assertFalse(this.csvConcurrentReader.equals(csvConcurrentReaderTest1));

    dataAndClick.put("10", 1);
    CSVConcurrentReader csvConcurrentReaderTest2 = new CSVConcurrentReader(
        Paths.get(this.testDataPath, "studentVle.csv").toString(), courseAndClick);
    Assertions.assertFalse(this.csvConcurrentReader.equals(csvConcurrentReaderTest2));
  }

  @Test
  void testHashCode() {
    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    ConcurrentMap<String, Integer> dataAndClick = new ConcurrentHashMap<>();
    dataAndClick.put("-10", 6);
    courseAndClick.put("AAA_2013J", dataAndClick);
    CSVConcurrentReader csvConcurrentReaderTest = new CSVConcurrentReader(
        Paths.get(this.testDataPath, "studentVle.csv").toString(), courseAndClick);
    this.csvConcurrentReader.readCSVFileToMap();
    Assertions.assertTrue(
        this.csvConcurrentReader.hashCode() == csvConcurrentReaderTest.hashCode());
  }

  @Test
  void testToString() {
    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    ConcurrentMap<String, Integer> dataAndClick = new ConcurrentHashMap<>();
    dataAndClick.put("-10", 6);
    courseAndClick.put("AAA_2013J", dataAndClick);
    CSVConcurrentReader csvConcurrentReaderTest = new CSVConcurrentReader(
        Paths.get(this.testDataPath, "studentVle.csv").toString(), courseAndClick);
    this.csvConcurrentReader.readCSVFileToMap();
    Assertions.assertTrue(
        this.csvConcurrentReader.toString().equals(csvConcurrentReaderTest.toString()));
  }
}