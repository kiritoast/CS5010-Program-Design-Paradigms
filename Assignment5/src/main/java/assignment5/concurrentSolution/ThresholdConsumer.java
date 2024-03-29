package assignment5.concurrentSolution;

import assignment5.sequentialSolution.CsvFileWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * Represents the Threshold writer class which helps to generate the summary file with threshold
 * values greater than or equal to the given value
 */
public class ThresholdConsumer implements Runnable {

  private String filePath;
  private BlockingQueue<String[]> thresholdQueue;
  private CountDownLatch latch;
  private static final String MODULE_PRESENTATION_HEADER = "module_presentation";
  private static final String DATE_HEADER = "date";
  private static final String TOTAL_CLICKS_HEADER = "total_clicks";

  /**
   * Creates a new instance of threshold consumer class given the output file path, queue of
   * threshold values and handle to count down latch
   *
   * @param filePath       output file path
   * @param thresholdQueue queue of threshold values to be written
   * @param latch          handle to count down latch
   */
  public ThresholdConsumer(String filePath, BlockingQueue<String[]> thresholdQueue,
      CountDownLatch latch) {
    this.filePath = filePath;
    this.thresholdQueue = thresholdQueue;
    this.latch = latch;
  }

  /**
   * This method is invoked by the writer thread to create the threshold output file and write its
   * content
   */
  @Override
  public void run() {
    try {
      CsvFileWriter csvFileWriter = new CsvFileWriter(this.filePath);
      csvFileWriter.writeData(
          new String[]{MODULE_PRESENTATION_HEADER, DATE_HEADER, TOTAL_CLICKS_HEADER});
      while (true) {
        String[] data = this.thresholdQueue.take();
        if (isEndOfProcess(data)) {
          break;
        }
        csvFileWriter.writeData(data);
      }
      csvFileWriter.closeFile();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    this.latch.countDown();
  }

  /**
   * This method helps to find out if the thread can stop execution
   *
   * @param data data read by the thread
   * @return true or false
   */
  private boolean isEndOfProcess(String[] data) {
    return (Arrays.equals(data, new String[]{}));
  }

  /**
   * Compares if the given instance of threshold consumer is same as the current one
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
    ThresholdConsumer that = (ThresholdConsumer) o;
    return Objects.equals(filePath, that.filePath) && Objects.equals(
        thresholdQueue, that.thresholdQueue) && Objects.equals(latch, that.latch);
  }

  /**
   * Generates a hashcode for the current instance of threshold consumer
   *
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(filePath, thresholdQueue, latch);
  }

  /**
   * Generates the string representation of threshold consumer instance
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "ThresholdConsumer{" +
        "filePath='" + filePath + '\'' +
        ", thresholdQueue=" + thresholdQueue +
        ", latch=" + latch +
        '}';
  }
}