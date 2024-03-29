package assignment5.concurrentSolution;

import assignment5.sequentialSolution.CsvFileReader;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterProcessorTest {

  private WriterProcessor writerProcessor;
  private ConcurrentMap<String, Integer> dateClick;

  @BeforeEach
  void setUp() {
    dateClick = new ConcurrentHashMap<>();
    dateClick.put("8", 7);
    String path =
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator + "WriterProcSummary"
            + File.separator + "BB_2014B.csv";
    writerProcessor = new WriterProcessor(path, dateClick, new CountDownLatch(1));
  }

  @Test
  void runFileGenerated() {
    writerProcessor.run();
    File file = new File(
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator + "WriterProcSummary"
            + File.separator + "BB_2014B.csv");
    Assertions.assertEquals(true, file.exists());
  }

  @Test
  void runVerifyHeader() {
    writerProcessor.run();
    CsvFileReader csvFileReader = new CsvFileReader(
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator + "WriterProcSummary"
            + File.separator + "BB_2014B.csv");
    String[] header = new String[]{"date", "sum_click"};
    Assertions.assertEquals(true, Arrays.equals(header, csvFileReader.readData()));
    csvFileReader.closeFile();
  }

  @Test
  void runVerifyData() {
    writerProcessor.run();
    CsvFileReader csvFileReader = new CsvFileReader(
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator + "WriterProcSummary"
            + File.separator + "BB_2014B.csv");
    csvFileReader.readData();//skip header
    String[] data = new String[]{"8", "7"};
    Assertions.assertEquals(true, Arrays.equals(data, csvFileReader.readData()));
    csvFileReader.closeFile();
  }

  @Test
  void testEquals() {
    Assertions.assertEquals(true, writerProcessor.equals(writerProcessor));
  }

  @Test
  void testEqualsNull() {
    Assertions.assertEquals(false, writerProcessor.equals(null));
  }

  @Test
  void testEqualsDiff() {
    Assertions.assertEquals(false, writerProcessor.equals("null"));
  }

  @Test
  void testEqualsDiffVal() {
    String path =
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator + "WriterProcSummary"
            + File.separator + "BB_2014B.csv";
    WriterProcessor writerProcessor1 = new WriterProcessor(path, dateClick, new CountDownLatch(1));
    Assertions.assertEquals(false, writerProcessor.equals(writerProcessor1));
  }

  @Test
  void testHashCode() {
    Assertions.assertNotEquals(1, writerProcessor.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertNotEquals("test", writerProcessor.toString());
  }
}