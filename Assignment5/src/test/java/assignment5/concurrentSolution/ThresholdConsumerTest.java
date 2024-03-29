package assignment5.concurrentSolution;

import assignment5.sequentialSolution.CsvFileReader;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThresholdConsumerTest {

  private ThresholdConsumer thresholdConsumer;
  private BlockingQueue<String[]> thresholdQueue;

  @BeforeEach
  void setUp() throws InterruptedException {
    thresholdQueue = new LinkedBlockingQueue<>();
    thresholdQueue.put(new String[]{"CC_2015C", "10", "100"});
    thresholdQueue.put(new String[]{});
    String path = "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator
        + "ThresholdConsOutput" + File.separator + "activity-50.csv";
    thresholdConsumer = new ThresholdConsumer(path, thresholdQueue, new CountDownLatch(1));
  }

  @Test
  void runFileGenerated() {
    thresholdConsumer.run();
    File file = new File("DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator
        + "ThresholdConsOutput" + File.separator + "activity-50.csv");
    Assertions.assertEquals(true, file.exists());
  }

  @Test
  void runVerifyHeader() {
    thresholdConsumer.run();
    CsvFileReader csvFileReader = new CsvFileReader(
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator
            + "ThresholdConsOutput" + File.separator + "activity-50.csv");
    String[] header = new String[]{"module_presentation", "date", "total_clicks"};
    Assertions.assertEquals(true, Arrays.equals(header, csvFileReader.readData()));
    csvFileReader.closeFile();
  }

  @Test
  void runVerifyData() {
    thresholdConsumer.run();
    CsvFileReader csvFileReader = new CsvFileReader(
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator
            + "ThresholdConsOutput" + File.separator + "activity-50.csv");
    csvFileReader.readData();//skip header
    String[] data = new String[]{"CC_2015C", "10", "100"};
    Assertions.assertEquals(true, Arrays.equals(data, csvFileReader.readData()));
    csvFileReader.closeFile();
  }

  @Test
  void testEquals() {
    Assertions.assertEquals(true, thresholdConsumer.equals(thresholdConsumer));
  }

  @Test
  void testEqualsNull() {
    Assertions.assertEquals(false, thresholdConsumer.equals(null));
  }

  @Test
  void testEqualsDiff() {
    Assertions.assertEquals(false, thresholdConsumer.equals("null"));
  }

  @Test
  void testEqualsDiffVal() throws InterruptedException {
    String path = "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator
        + "ThresholdConsOutput" + File.separator + "activity-50.csv";
    ThresholdConsumer thresholdConsumer1 = new ThresholdConsumer(path, thresholdQueue,
        new CountDownLatch(1));
    Assertions.assertEquals(false, thresholdConsumer.equals(thresholdConsumer1));
  }

  @Test
  void testHashCode() {
    Assertions.assertNotEquals(1, thresholdConsumer.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertNotEquals("test", thresholdConsumer.toString());
  }
}