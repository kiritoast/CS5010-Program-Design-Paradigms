package assignment5.concurrentSolution;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents the threshold processor to filter the valid course date and click pair by using
 * multiple parallel threads.
 */
public class ThresholdProcessor {

  private BlockingQueue<String[]> thresholdQueue;
  /**
   * The helper executor pool for the threshold processor
   */
  protected transient ExecutorService thresholdExecutor;
  private String currentFolderOfSummaryFile;
  private Integer threshold;
  private String thresholdOutputPath;
  private static final int EXTENSION_OFFSET = 1;
  private static final char SUFFIX_SEPARATOR = '.';
  private static final String DEFAULT_INVALID_FOLDER = "";
  private static final int INVALID_SEPARATOR_INDEX = -1;
  private static final String VALID_EXTENSION = "csv";
  private static final int THRESHOLD_PRODUCER_NUMBER = 32;
  private static final int THRESHOLD_CONSUMER_NUMBER = 1;
  private static final String DEFAULT_FILE_PREFIX = "activity-";

  /**
   * Create a threshold processor given the summary output folder, the threshold output folder, and
   * the threshold.
   *
   * @param currentFolderOfSummaryFile the summary output folder
   * @param thresholdOutputPath        the threshold output folder
   * @param threshold                  the threshold used to filter the sum of click
   */
  public ThresholdProcessor(String currentFolderOfSummaryFile, String thresholdOutputPath,
      String threshold) {
    this.currentFolderOfSummaryFile = currentFolderOfSummaryFile;
    this.thresholdOutputPath = thresholdOutputPath;
    this.thresholdQueue = new LinkedBlockingQueue<>();
    this.threshold = Integer.parseInt(threshold);
    this.thresholdExecutor = Executors.newFixedThreadPool(
        THRESHOLD_PRODUCER_NUMBER + THRESHOLD_CONSUMER_NUMBER);
  }

  /**
   * Read and filter summary files by using multiple producer threads, and output the result by
   * using one consumer thread. All the threads are running inn parallel.
   */
  public void filterBasedOnThreshold() {
    File folder = new File(this.currentFolderOfSummaryFile);
    List<String> summaryFileList = new ArrayList<>();
    if (folder.listFiles() != null) {
      for (File file : folder.listFiles()) {
        String filePath = file.getAbsolutePath();
        if (getFileExtension(filePath).equals(VALID_EXTENSION)) {
          summaryFileList.add(filePath);
        }
      }

      CountDownLatch producerLatch = new CountDownLatch(summaryFileList.size());
      for (String file : summaryFileList) {
        this.thresholdExecutor.submit(
            new ThresholdProducer(this.threshold, file, this.thresholdQueue, producerLatch));
      }
      String thresholdOutputFilePath =
          this.thresholdOutputPath + DEFAULT_FILE_PREFIX + this.threshold + SUFFIX_SEPARATOR
              + VALID_EXTENSION;
      CountDownLatch consumerLatch = new CountDownLatch(THRESHOLD_CONSUMER_NUMBER);
      this.thresholdExecutor.submit(
          new ThresholdConsumer(thresholdOutputFilePath, this.thresholdQueue, consumerLatch));
      try {
        producerLatch.await();
        this.thresholdQueue.put(new String[]{});
        consumerLatch.await();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      this.thresholdExecutor.shutdown();
    }
  }

  /**
   * Find the extension for the given file
   *
   * @param fullName the file name
   * @return the extension of the given file; if not found, the default extension will be returned
   */
  public String getFileExtension(String fullName) {
    if (fullName != null) {
      String fileName = new File(fullName).getName();
      int dotIndex = fileName.lastIndexOf(SUFFIX_SEPARATOR);
      return (dotIndex == INVALID_SEPARATOR_INDEX) ? DEFAULT_INVALID_FOLDER
          : fileName.substring(dotIndex + EXTENSION_OFFSET);
    }
    return DEFAULT_INVALID_FOLDER;
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
    ThresholdProcessor that = (ThresholdProcessor) o;
    return currentFolderOfSummaryFile.equals(that.currentFolderOfSummaryFile) && threshold.equals(
        that.threshold) && thresholdOutputPath.equals(that.thresholdOutputPath);
  }

  /**
   * This method is used to generate the hash code of json reader class
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(currentFolderOfSummaryFile, threshold, thresholdOutputPath);
  }

  /**
   * This method is used to generate the string representation of json reader class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "ThresholdProcessor{" +
        "currentFolderOfSummaryFile='" + currentFolderOfSummaryFile + '\'' +
        ", threshold=" + threshold +
        ", thresholdOutputPath='" + thresholdOutputPath + '\'' +
        '}';
  }
}
