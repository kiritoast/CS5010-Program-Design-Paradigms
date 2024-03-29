package assignment5.concurrentSolution;

import assignment5.sequentialSolution.CsvFileWriter;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

/**
 * Represents the writer class which helps to create a summary file and write the content
 */
public class WriterProcessor implements Runnable {

  private String outputFilePath;
  private ConcurrentMap<String, Integer> dateAndSumClick;
  private CountDownLatch latch;
  private static final String DATE_HEADER = "date";
  private static final String SUM_CLICK_HEADER = "sum_click";

  /**
   * Creates a new instance of writer processor class given the output file path, map of sum click
   * information and countdown latch handle
   *
   * @param outputFilePath  path of output file
   * @param dateAndSumClick map of date and sum click information
   * @param latch           handle to count down latch
   */
  public WriterProcessor(String outputFilePath, ConcurrentMap<String, Integer> dateAndSumClick,
      CountDownLatch latch) {
    this.outputFilePath = outputFilePath;
    this.dateAndSumClick = dateAndSumClick;
    this.latch = latch;
  }

  /**
   * This method will be invoked by each writer thread to create the output file and write its
   * content
   */
  @Override
  public void run() {
    CsvFileWriter csvFileWriter = new CsvFileWriter(this.outputFilePath);

    csvFileWriter.writeData(new String[]{DATE_HEADER, SUM_CLICK_HEADER});

    for (Map.Entry<String, Integer> dateClick : this.dateAndSumClick.entrySet()) {
      csvFileWriter.writeData(new String[]{dateClick.getKey(), dateClick.getValue().toString()});
    }

    csvFileWriter.closeFile();
    this.latch.countDown();
  }

  /**
   * Compares if the given instance of writer processor is same as the current one
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
    WriterProcessor that = (WriterProcessor) o;
    return Objects.equals(outputFilePath, that.outputFilePath) && Objects.equals(
        dateAndSumClick, that.dateAndSumClick) && Objects.equals(latch, that.latch);
  }

  /**
   * Generates a hashcode for the current instance of writer processor
   *
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(outputFilePath, dateAndSumClick, latch);
  }

  /**
   * Generates the string representation of writer processor instance
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "WriterProcessor{" +
        "outputFilePath='" + outputFilePath + '\'' +
        ", dateAndSumClick=" + dateAndSumClick +
        ", latch=" + latch +
        '}';
  }
}
