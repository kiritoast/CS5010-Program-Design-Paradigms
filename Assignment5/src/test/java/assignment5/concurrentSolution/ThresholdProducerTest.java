package assignment5.concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThresholdProducerTest {

  private String testDataPath;
  private ThresholdProducer thresholdProducer;
  private static final int TEST_LATCH_NUM = 1;
  private static final Integer TEST_THRESHOLD = 2;
  private static final Integer TEST_THRESHOLD_COMPARE = 1;

  @BeforeEach
  void setUp() {
    File testDirectory = new File(
        "src" + File.separator + "test" + File.separator + "java" + File.separator + "assignment5"
            + File.separator + "TestDataSet" + File.separator + "summaryTest");
    this.testDataPath = testDirectory.getAbsolutePath();
    this.thresholdProducer = new ThresholdProducer(TEST_THRESHOLD,
        Paths.get(testDataPath, "AA_2014A.csv").toString(), new LinkedBlockingQueue<String[]>(),
        new CountDownLatch(TEST_LATCH_NUM));
  }

  @Test
  void run() {
    this.thresholdProducer.run();
    BlockingQueue<String[]> queueTest = new LinkedBlockingQueue<>();
    queueTest.add(new String[]{"AA_2014A", "-10", "3"});
    ThresholdProducer thresholdProducerTest = new ThresholdProducer(TEST_THRESHOLD,
        Paths.get(testDataPath, "AA_2014A.csv").toString(), queueTest,
        new CountDownLatch(TEST_LATCH_NUM));
    Assertions.assertTrue(this.thresholdProducer.equals(thresholdProducerTest));
  }

  @Test
  void getCourseName() {
    Assertions.assertEquals(this.thresholdProducer.getCourseName(), "AA_2014A");

    BlockingQueue<String[]> queueTest = new LinkedBlockingQueue<>();
    queueTest.add(new String[]{"AA_2014A", "-10", "3"});
    ThresholdProducer thresholdProducerTest = new ThresholdProducer(TEST_THRESHOLD,
        Paths.get(testDataPath, "AA_2014A").toString(), queueTest,
        new CountDownLatch(TEST_LATCH_NUM));
    Assertions.assertEquals(thresholdProducerTest.getCourseName(), "AA_2014A");
  }

  @Test
  void testEquals() {
    this.thresholdProducer.run();

    Assertions.assertEquals(this.thresholdProducer, this.thresholdProducer);
    Assertions.assertFalse(this.thresholdProducer.equals(null));
    Assertions.assertFalse(this.thresholdProducer.equals(1));

    BlockingQueue<String[]> queueTest = new LinkedBlockingQueue<>();
    queueTest.add(new String[]{"AA_2014A", "-10", "3"});

    ThresholdProducer thresholdProducerTest1 = new ThresholdProducer(TEST_THRESHOLD_COMPARE,
        Paths.get(testDataPath, "AA_2014A.csv").toString(), queueTest,
        new CountDownLatch(TEST_LATCH_NUM));
    Assertions.assertFalse(this.thresholdProducer.equals(thresholdProducerTest1));

    ThresholdProducer thresholdProducerTest2 = new ThresholdProducer(TEST_THRESHOLD,
        Paths.get(testDataPath, "AA_2014ATest.csv").toString(), queueTest,
        new CountDownLatch(TEST_LATCH_NUM));
    Assertions.assertFalse(this.thresholdProducer.equals(thresholdProducerTest2));
  }

  @Test
  void testHashCode() {
    this.thresholdProducer.run();
    BlockingQueue<String[]> queueTest = new LinkedBlockingQueue<>();
    queueTest.add(new String[]{"AA_2014A", "-10", "3"});
    ThresholdProducer thresholdProducerTest = new ThresholdProducer(TEST_THRESHOLD,
        Paths.get(testDataPath, "AA_2014A.csv").toString(), queueTest,
        new CountDownLatch(TEST_LATCH_NUM));
    Assertions.assertTrue(this.thresholdProducer.hashCode() == thresholdProducerTest.hashCode());

  }

  @Test
  void testToString() {
    this.thresholdProducer.run();
    BlockingQueue<String[]> queueTest = new LinkedBlockingQueue<>();
    queueTest.add(new String[]{"AA_2014A", "-10", "3"});
    ThresholdProducer thresholdProducerTest = new ThresholdProducer(TEST_THRESHOLD,
        Paths.get(testDataPath, "AA_2014A.csv").toString(), queueTest,
        new CountDownLatch(TEST_LATCH_NUM));
    Assertions.assertTrue(
        this.thresholdProducer.toString().equals(thresholdProducerTest.toString()));
  }
}