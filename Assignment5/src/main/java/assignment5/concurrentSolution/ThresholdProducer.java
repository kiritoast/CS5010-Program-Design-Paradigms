package assignment5.concurrentSolution;

import assignment5.sequentialSolution.CsvFileReader;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * Represents the threshold process producer with the information of the summary file, the
 * threshold, the buffer queue, and the helper latch.
 */
public class ThresholdProducer implements Runnable {

  private String summaryFilePath;
  private Integer threshold;
  private BlockingQueue<String[]> thresholdQueue;
  private static final int DATE_INDEX = 0;
  private static final int SUM_CLICK_INDEX = 1;
  private static final String FILE_NAME_SEPARATOR = ".";
  private static final int FILE_NAME_OFFSET = 1;
  private static final int FILE_NAME_START_INDEX = 0;
  private static final int DOT_NOT_FOUND_INDEX = -1;
  private CountDownLatch latch;

  /**
   * Creates a threshold process producer given the summary file, the threshold, the buffer queue,
   * and the helper latch
   *
   * @param threshold       the threshold used to filter the sum of click
   * @param summaryFilePath the summary file
   * @param thresholdQueue  the buffer queue
   * @param latch           the helper latch
   */
  public ThresholdProducer(Integer threshold, String summaryFilePath,
      BlockingQueue<String[]> thresholdQueue, CountDownLatch latch) {
    this.threshold = threshold;
    this.summaryFilePath = summaryFilePath;
    this.thresholdQueue = thresholdQueue;
    this.latch = latch;
  }

  /**
   * This method is invoked to read the summary file, find the valid pairs, then store them to the
   * buffer queue
   */
  @Override
  public void run() {
    CsvFileReader csvFileReader = new CsvFileReader(this.summaryFilePath);
    csvFileReader.readData();
    String[] line;
    try {
      while ((line = csvFileReader.readData()) != null) {
        Integer sumClick = Integer.parseInt(line[SUM_CLICK_INDEX]);
        if (sumClick >= this.threshold) {
          String courseName = this.getCourseName();
          String date = line[DATE_INDEX];
          thresholdQueue.put(new String[]{courseName, date, String.valueOf(sumClick)});
        }
      }
      csvFileReader.closeFile();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    this.latch.countDown();
  }

  /**
   * Get the course key from the summary file path
   *
   * @return the course key
   */
  public String getCourseName() {
    String fileName = this.summaryFilePath.substring(
        this.summaryFilePath.lastIndexOf(File.separator) + FILE_NAME_OFFSET);
    int dotIdx = fileName.lastIndexOf(FILE_NAME_SEPARATOR);
    return (dotIdx == DOT_NOT_FOUND_INDEX) ? fileName
        : fileName.substring(FILE_NAME_START_INDEX, dotIdx);
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
    ThresholdProducer that = (ThresholdProducer) o;
    return summaryFilePath.equals(that.summaryFilePath) && threshold.equals(that.threshold);
  }

  /**
   * This method is used to generate the hash code of json reader class
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(summaryFilePath, threshold);
  }

  /**
   * This method is used to generate the string representation of json reader class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "ThresholdProducer{" +
        "summaryFilePath='" + summaryFilePath + '\'' +
        ", threshold=" + threshold +
        '}';
  }
}
