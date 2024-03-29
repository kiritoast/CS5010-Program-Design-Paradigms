package assignment5.concurrentSolution;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Represents the writer class which helps to generate the output files by running on parallel
 * threads
 */
public class CSVConcurrentWriter {

  private ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick;
  private String outputFileDirPath;
  private ExecutorService writerProducerExecutor;

  private static final String OUTPUT_EXTENSION = ".csv";

  /**
   * Creates a new instance of csv concurrent writer given the course click summary map and output
   * directory file path
   *
   * @param courseAndClick    map of course and click details
   * @param outputFileDirPath output file directory path
   */
  public CSVConcurrentWriter(ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick,
      String outputFileDirPath) {
    this.courseAndClick = courseAndClick;
    this.outputFileDirPath = outputFileDirPath;
    this.writerProducerExecutor = Executors.newCachedThreadPool();
  }

  /**
   * This method helps to generate multiple threads which will create the output files and write the
   * content
   */
  public void writeOutputFiles() {
    try {
      CountDownLatch latch = new CountDownLatch(this.courseAndClick.size());

      for (Map.Entry<String, ConcurrentMap<String, Integer>> courseClick : this.courseAndClick.entrySet()) {
        String outputFilePath = this.outputFileDirPath + courseClick.getKey() + OUTPUT_EXTENSION;
        this.writerProducerExecutor.execute(
            new WriterProcessor(outputFilePath, courseClick.getValue(), latch));
      }

      latch.await();//wait until all threads are back
      this.writerProducerExecutor.shutdown();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Compares if the given instance of csv concurrent writer is same as the current one
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
    CSVConcurrentWriter that = (CSVConcurrentWriter) o;
    return Objects.equals(courseAndClick, that.courseAndClick) && Objects.equals(
        outputFileDirPath, that.outputFileDirPath);
  }

  /**
   * Generates a hashcode for the current instance of csv concurrent writer
   *
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(courseAndClick, outputFileDirPath);
  }

  /**
   * Generates the string representation of csv concurrent writer instance
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "CSVConcurrentWriter{" +
        "courseAndClick=" + courseAndClick +
        ", outputFileDirPath='" + outputFileDirPath + '\'' +
        ", writerProducerExecutor=" + writerProducerExecutor +
        '}';
  }
}
