package assignment5.concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import assignment5.sequentialSolution.CsvFileReader;
import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderConsumerTest {

  private String testDataPath;
  private ReaderConsumer readerConsumer;
  private static final int TEST_LATCH_NUM = 1;

  @BeforeEach
  void setUp() {
    File testDirectory = new File(
        "src" + File.separator + "test" + File.separator + "java" + File.separator + "assignment5"
            + File.separator + "TestDataSet");
    this.testDataPath = testDirectory.getAbsolutePath();
    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    courseAndClick.put("AAA_2013J", new ConcurrentHashMap<String, Integer>());
    BlockingQueue<String[]> queue = new LinkedBlockingQueue<>();
    queue.add(new String[]{"AAA", "2013J", "3", "3", "-10", "4"});
    queue.add(new String[]{"AAA", "2013J", "3", "3", "-10", "1"});
    queue.add(new String[]{});
    this.readerConsumer = new ReaderConsumer(courseAndClick, queue,
        new CountDownLatch(TEST_LATCH_NUM));
  }

  @Test
  void run() {
    this.readerConsumer.run();
    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    ConcurrentMap<String, Integer> dateAndClick = new ConcurrentHashMap<>();
    dateAndClick.put("-10", 5);
    courseAndClick.put("AAA_2013J", dateAndClick);
    BlockingQueue<String[]> queue = new LinkedBlockingQueue<>();
    ReaderConsumer readerConsumerTest = new ReaderConsumer(courseAndClick, queue,
        new CountDownLatch(TEST_LATCH_NUM));

    Assertions.assertTrue(this.readerConsumer.equals(readerConsumerTest));
  }

  @Test
  void testEquals() {
    this.readerConsumer.run();

    Assertions.assertEquals(this.readerConsumer, this.readerConsumer);
    Assertions.assertFalse(this.readerConsumer.equals(null));
    Assertions.assertFalse(this.readerConsumer.equals(1));

    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    ConcurrentMap<String, Integer> dateAndClick = new ConcurrentHashMap<>();
    dateAndClick.put("-10", 6);
    courseAndClick.put("AAA_2013J", dateAndClick);
    BlockingQueue<String[]> queue = new LinkedBlockingQueue<>();
    ReaderConsumer readerConsumerTest = new ReaderConsumer(courseAndClick, queue,
        new CountDownLatch(TEST_LATCH_NUM));

    Assertions.assertFalse(this.readerConsumer.equals(readerConsumerTest));
  }

  @Test
  void testHashCode() {
    this.readerConsumer.run();
    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    ConcurrentMap<String, Integer> dateAndClick = new ConcurrentHashMap<>();
    dateAndClick.put("-10", 5);
    courseAndClick.put("AAA_2013J", dateAndClick);
    BlockingQueue<String[]> queue = new LinkedBlockingQueue<>();
    ReaderConsumer readerConsumerTest = new ReaderConsumer(courseAndClick, queue,
        new CountDownLatch(TEST_LATCH_NUM));

    Assertions.assertTrue(this.readerConsumer.hashCode() == readerConsumerTest.hashCode());
  }

  @Test
  void testToString() {
    this.readerConsumer.run();
    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    ConcurrentMap<String, Integer> dateAndClick = new ConcurrentHashMap<>();
    dateAndClick.put("-10", 5);
    courseAndClick.put("AAA_2013J", dateAndClick);
    BlockingQueue<String[]> queue = new LinkedBlockingQueue<>();
    ReaderConsumer readerConsumerTest = new ReaderConsumer(courseAndClick, queue,
        new CountDownLatch(TEST_LATCH_NUM));

    Assertions.assertTrue(this.readerConsumer.toString().equals(readerConsumerTest.toString()));
  }
}