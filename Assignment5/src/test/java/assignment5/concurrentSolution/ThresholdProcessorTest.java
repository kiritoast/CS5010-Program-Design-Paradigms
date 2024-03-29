package assignment5.concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import assignment5.sequentialSolution.CsvFileReader;
import java.io.File;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThresholdProcessorTest {

  private String testDataPath;
  private ThresholdProcessor thresholdProcessor;
  private static final String TEST_THRESHOLD = "2";
  private static final String TEST_THRESHOLD_COMPARE = "3";
  private static final String DEFAULT_EXTENSION = "";
  private static final String TEST_EXTENSION = "csv";

  @BeforeEach
  void setUp() {
    File testDirectory = new File(
        "src" + File.separator + "test" + File.separator + "java" + File.separator + "assignment5"
            + File.separator + "TestDataSet");
    this.testDataPath = testDirectory.getAbsolutePath();
    this.thresholdProcessor = new ThresholdProcessor(
        Paths.get(testDataPath, "summaryTest").toString(),
        Paths.get(testDataPath, "thresholdTest").toString() + File.separator, TEST_THRESHOLD);
  }

  @Test
  void filterBasedOnThreshold() {
    this.thresholdProcessor.filterBasedOnThreshold();
    CsvFileReader activityFile = new CsvFileReader(
        testDataPath + File.separator + "thresholdTest" + File.separator + "activity-"
            + TEST_THRESHOLD + ".csv");
    String[] header = activityFile.readData();
    String[] content = activityFile.readData();
    String[] headerCompare = new String[]{"module_presentation", "date", "total_clicks"};
    String[] contentCompare = new String[]{"AA_2014A", "-10", "3"};
    Assertions.assertTrue(compareStringArray(headerCompare, header));
    Assertions.assertTrue(compareStringArray(contentCompare, content));
  }

  @Test
  void testGetFileExtension() {
    Assertions.assertEquals(
        thresholdProcessor.getFileExtension("DataSet" + File.separator + "test.csv"),
        TEST_EXTENSION);
    Assertions.assertEquals(thresholdProcessor.getFileExtension("test.csv"), TEST_EXTENSION);
    Assertions.assertEquals(thresholdProcessor.getFileExtension("test"), DEFAULT_EXTENSION);
    Assertions.assertEquals(thresholdProcessor.getFileExtension(null), DEFAULT_EXTENSION);
  }

  @Test
  void testEquals() {
    ThresholdProcessor thresholdProcessorTest = new ThresholdProcessor(
        Paths.get(testDataPath, "summaryTest").toString(),
        Paths.get(testDataPath, "thresholdTest").toString() + File.separator, TEST_THRESHOLD);
    Assertions.assertTrue(this.thresholdProcessor.equals(thresholdProcessorTest));

    ThresholdProcessor thresholdProcessorTest1 = new ThresholdProcessor(
        Paths.get(testDataPath, "summaryTest1").toString(),
        Paths.get(testDataPath, "thresholdTest").toString() + File.separator, TEST_THRESHOLD);
    Assertions.assertFalse(this.thresholdProcessor.equals(thresholdProcessorTest1));

    ThresholdProcessor thresholdProcessorTest2 = new ThresholdProcessor(
        Paths.get(testDataPath, "summaryTest").toString(),
        Paths.get(testDataPath, "thresholdTest1").toString() + File.separator, TEST_THRESHOLD);
    Assertions.assertFalse(this.thresholdProcessor.equals(thresholdProcessorTest2));

    ThresholdProcessor thresholdProcessorTest3 = new ThresholdProcessor(
        Paths.get(testDataPath, "summaryTest").toString(),
        Paths.get(testDataPath, "thresholdTest").toString() + File.separator,
        TEST_THRESHOLD_COMPARE);
    Assertions.assertFalse(this.thresholdProcessor.equals(thresholdProcessorTest3));

    Assertions.assertEquals(this.thresholdProcessor, this.thresholdProcessor);
    Assertions.assertFalse(this.thresholdProcessor.equals(null));
    Assertions.assertFalse(this.thresholdProcessor.equals(1));
  }

  @Test
  void testHashCode() {
    ThresholdProcessor thresholdProcessorTest = new ThresholdProcessor(
        Paths.get(testDataPath, "summaryTest").toString(),
        Paths.get(testDataPath, "thresholdTest").toString() + File.separator, TEST_THRESHOLD);
    Assertions.assertTrue(this.thresholdProcessor.hashCode() == thresholdProcessorTest.hashCode());
  }

  @Test
  void testToString() {
    ThresholdProcessor thresholdProcessorTest = new ThresholdProcessor(
        Paths.get(testDataPath, "summaryTest").toString(),
        Paths.get(testDataPath, "thresholdTest").toString() + File.separator, TEST_THRESHOLD);
    Assertions.assertTrue(
        this.thresholdProcessor.toString().equals(thresholdProcessorTest.toString()));
  }

  private boolean compareStringArray(String[] a, String[] b) {
    if (a == b) {
      return true;
    }
    if (a == null && b != null || a != null && b == null || a.length != b.length) {
      return false;
    }

    boolean flag = true;
    for (int i = 0; i < a.length; i++) {
      if (!a[i].equals(b[i])) {
        flag = false;
      }
    }
    return flag;
  }
}