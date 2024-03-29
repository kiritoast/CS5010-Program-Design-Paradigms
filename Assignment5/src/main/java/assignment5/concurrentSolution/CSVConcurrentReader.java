package assignment5.concurrentSolution;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents the CSV reader to read the information in a CSV file into a concurrent map by using
 * multiple parallel threads.
 */
public class CSVConcurrentReader {

  private String filePath;
  private BlockingQueue<String[]> readQueue;
  private ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick;
  /**
   * The helper executor pool for the concurrent CSV reader
   */
  protected transient ExecutorService readerExecutor;
  /**
   * The default number of reader producer
   */
  private static final int READER_PRODUCER_NUMBER = 1;
  /**
   * The default number of reader consumer
   */
  public static final int READER_CONSUMER_NUMBER = 32;
  private static final int INITIAL_INDEX = 0;

  /**
   * Create a new CSV concurrent reader given the source file path and the course click summary
   * concurrent map
   *
   * @param filePath       the source file path
   * @param courseAndClick the course click summary concurrent map
   */
  public CSVConcurrentReader(String filePath,
      ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick) {
    this.filePath = filePath;
    this.readQueue = new LinkedBlockingQueue<>();
    this.courseAndClick = courseAndClick;
    this.readerExecutor = Executors.newFixedThreadPool(
        READER_PRODUCER_NUMBER + READER_CONSUMER_NUMBER);
  }

  /**
   * read the information from source file into the concurrent map with the help of one producer
   * threads and multiple consumer threads
   */
  public void readCSVFileToMap() {
    CountDownLatch latch = new CountDownLatch(READER_PRODUCER_NUMBER + READER_CONSUMER_NUMBER);

    ReaderProducer readerProducerThread = new ReaderProducer(this.filePath, this.readQueue, latch);

    this.readerExecutor.submit(readerProducerThread);
    for (int i = INITIAL_INDEX; i < READER_CONSUMER_NUMBER; i++) {
      ReaderConsumer readerConsumerThread = new ReaderConsumer(this.courseAndClick, this.readQueue,
          latch);
      this.readerExecutor.submit(readerConsumerThread);
    }

    try {
      latch.await();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    this.readerExecutor.shutdown();
  }

  /**
   * This method is used to compare two instances of json reader class
   *
   * @param o instance to be compared
   * @return true or false
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CSVConcurrentReader that = (CSVConcurrentReader) o;
    return filePath.equals(that.filePath) && courseAndClick.equals(that.courseAndClick);
  }

  /**
   * This method is used to generate the hash code of json reader class
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(filePath, courseAndClick);
  }

  /**
   * This method is used to generate the string representation of json reader class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "CSVConcurrentReader{" + "filePath='" + filePath + '\'' + ", courseAndClick="
        + courseAndClick + '}';
  }
}
