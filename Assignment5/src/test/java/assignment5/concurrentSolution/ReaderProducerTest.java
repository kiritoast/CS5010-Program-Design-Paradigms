package assignment5.concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderProducerTest {

  private String filePath;
  private BlockingQueue<String[]> readQueue;
  private CountDownLatch latch;
  private static final int TEST_LATCH_NUM = 3;

  ReaderProducer readerProducer;

  @BeforeEach
  void setUp() {
    filePath = "DataSet" + File.separator + "test" + File.separator + "courses.csv";
    this.readQueue = new LinkedBlockingQueue<>();
    latch = new CountDownLatch(TEST_LATCH_NUM);
    readerProducer = new ReaderProducer(filePath, readQueue, latch);
  }

  @Test
  void run() {
    readerProducer.run();
    String[] expect = new String[]{"AAA", "2013J", "268"};
    Assertions.assertEquals(expect[0], readQueue.peek()[0]);
    Assertions.assertEquals(expect[1], readQueue.peek()[1]);
    Assertions.assertEquals(expect[2], readQueue.peek()[2]);
  }

  @Test
  void testEquals() {
    String filePathTest = "DataSet" + File.separator + "test" + File.separator + "courses.csv";
    BlockingQueue<String[]> readQueueTest = new LinkedBlockingQueue<>();
    CountDownLatch latchTest = new CountDownLatch(TEST_LATCH_NUM);
    ReaderProducer readerProducerTest = new ReaderProducer(filePathTest, readQueueTest, latchTest);

    Assertions.assertTrue(this.readerProducer.equals(readerProducerTest));

    Assertions.assertEquals(this.readerProducer, this.readerProducer);
    Assertions.assertFalse(this.readerProducer.equals(null));
    Assertions.assertFalse(this.readerProducer.equals(1));

    String filePathTest1 = "DataSet" + File.separator + "test" + File.separator + "coursesTest.csv";
    BlockingQueue<String[]> readQueueTest1 = new LinkedBlockingQueue<>();
    CountDownLatch latchTest1 = new CountDownLatch(TEST_LATCH_NUM);
    ReaderProducer readerProducerTest1 = new ReaderProducer(filePathTest1, readQueueTest1,
        latchTest1);
    Assertions.assertFalse(this.readerProducer.equals(readerProducerTest1));
  }

  @Test
  void testHashcode() {
    String filePathTest = "DataSet" + File.separator + "test" + File.separator + "courses.csv";
    BlockingQueue<String[]> readQueueTest = new LinkedBlockingQueue<>();
    CountDownLatch latchTest = new CountDownLatch(TEST_LATCH_NUM);
    ReaderProducer readerProducerTest = new ReaderProducer(filePathTest, readQueueTest, latchTest);

    Assertions.assertTrue(this.readerProducer.hashCode() == readerProducerTest.hashCode());
  }

  @Test
  void testToString() {
    String filePathTest = "DataSet" + File.separator + "test" + File.separator + "courses.csv";
    BlockingQueue<String[]> readQueueTest = new LinkedBlockingQueue<>();
    CountDownLatch latchTest = new CountDownLatch(TEST_LATCH_NUM);
    ReaderProducer readerProducerTest = new ReaderProducer(filePathTest, readQueueTest, latchTest);

    Assertions.assertTrue(this.readerProducer.toString().equals(readerProducerTest.toString()));
  }
}