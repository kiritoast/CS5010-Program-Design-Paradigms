package assignment5.concurrentSolution;

import assignment5.sequentialSolution.CsvFileReader;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * Represents the CSV reader producer with the information of the source file path, the buffer
 * queue, and the helper latch.
 */
public class ReaderProducer implements Runnable {

  private String filePath;
  private BlockingQueue<String[]> readQueue;
  private CountDownLatch latch;
  private static final int INITIAL_INDEX = 0;

  /**
   * Creates a CSV reader producer given the source file path, the buffer queue, and the helper
   * latch
   *
   * @param filePath  the source file path
   * @param readQueue the buffer queue
   * @param latch     the helper latch
   */
  public ReaderProducer(String filePath, BlockingQueue<String[]> readQueue, CountDownLatch latch) {
    this.filePath = filePath;
    this.readQueue = readQueue;
    this.latch = latch;
  }

  /**
   * This method is invoked to read the course information from the source file path and then store
   * it to the buffer queue.
   */
  @Override
  public void run() {
    CsvFileReader csvFileReader = new CsvFileReader(this.filePath);
    csvFileReader.readData();
    String[] line;
    try {
      while ((line = csvFileReader.readData()) != null) {
        this.readQueue.put(line);
      }
      csvFileReader.closeFile();
      for (int i = INITIAL_INDEX; i < CSVConcurrentReader.READER_CONSUMER_NUMBER; i++) {
        this.readQueue.put(new String[]{});
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      this.latch.countDown();
    }
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
    ReaderProducer that = (ReaderProducer) o;
    return filePath.equals(that.filePath);
  }

  /**
   * This method is used to generate the hash code of json reader class
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(filePath);
  }

  /**
   * This method is used to generate the string representation of json reader class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "ReaderProducer{" +
        "filePath='" + filePath + '\'' +
        '}';
  }
}
