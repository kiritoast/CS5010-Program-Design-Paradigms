package assignment5.concurrentSolution;

import assignment5.sequentialSolution.CsvFileReader;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CSVConcurrentWriterTest {

  private CSVConcurrentWriter csvConcurrentWriter;

  @BeforeEach
  void setUp() {
    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    ConcurrentMap<String, Integer> dateClick = new ConcurrentHashMap<>();
    dateClick.put("-10", 3);
    courseAndClick.put("AA_2014A", dateClick);
    String path = "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator
        + "CsvConcWriterSummary" + File.separator;
    csvConcurrentWriter = new CSVConcurrentWriter(courseAndClick, path);
  }

  @Test
  void writeOutputFilesFileGenerated() {
    csvConcurrentWriter.writeOutputFiles();
    File file = new File("DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator
        + "CsvConcWriterSummary" + File.separator + "AA_2014A.csv");
    Assertions.assertEquals(true, file.exists());
  }

  @Test
  void writeOutputFilesVerifyHeader() {
    csvConcurrentWriter.writeOutputFiles();
    CsvFileReader csvFileReader = new CsvFileReader(
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator
            + "CsvConcWriterSummary" + File.separator + "AA_2014A.csv");
    String[] header = new String[]{"date", "sum_click"};
    Assertions.assertEquals(true, Arrays.equals(header, csvFileReader.readData()));
    csvFileReader.closeFile();
  }

  @Test
  void writeOutputFilesVerifyData() {
    csvConcurrentWriter.writeOutputFiles();
    CsvFileReader csvFileReader = new CsvFileReader(
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator
            + "CsvConcWriterSummary" + File.separator + "AA_2014A.csv");
    csvFileReader.readData();//skip header
    String[] data = new String[]{"-10", "3"};
    Assertions.assertEquals(true, Arrays.equals(data, csvFileReader.readData()));
    csvFileReader.closeFile();
  }

  @Test
  void testEquals() {
    Assertions.assertEquals(true, csvConcurrentWriter.equals(csvConcurrentWriter));
  }

  @Test
  void testEqualsNull() {
    Assertions.assertEquals(false, csvConcurrentWriter.equals(null));
  }

  @Test
  void testEqualsDiff() {
    Assertions.assertEquals(false, csvConcurrentWriter.equals("null"));
  }

  @Test
  void testEqualsDiffVal() {
    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick1 = new ConcurrentHashMap<>();
    ConcurrentMap<String, Integer> dateClick1 = new ConcurrentHashMap<>();
    dateClick1.put("-1", 2);
    courseAndClick1.put("GG_2014A", dateClick1);
    String path = "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator
        + "CsvConcWriterSummary" + File.separator;
    CSVConcurrentWriter csvConcurrentWriter1 = new CSVConcurrentWriter(courseAndClick1, path);
    Assertions.assertEquals(false, csvConcurrentWriter.equals(csvConcurrentWriter1));
  }

  @Test
  void testHashCode() {
    Assertions.assertNotEquals(1, csvConcurrentWriter.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertNotEquals("test", csvConcurrentWriter.toString());
  }
}