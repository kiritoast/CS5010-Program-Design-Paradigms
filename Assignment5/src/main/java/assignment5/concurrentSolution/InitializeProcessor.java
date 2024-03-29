package assignment5.concurrentSolution;

import assignment5.sequentialSolution.CsvFileReader;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Represents the file processor to read the information in a CSV file to initialize a concurrent
 * map.
 */
public class InitializeProcessor implements Runnable {

  private ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick;
  private String filePath;
  private static final int MODULE_INDEX = 0;
  private static final int PRESENTATION_INDEX = 1;
  private static final String COURSE_SEPARATOR = "_";

  /**
   * Creates a course file processor given the course file path, and the concurrent map
   *
   * @param filePath       the course file path
   * @param courseAndClick the concurrent map
   */
  public InitializeProcessor(String filePath,
      ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick) {
    this.filePath = filePath;
    this.courseAndClick = courseAndClick;
  }

  /**
   * This method is invoked to read the information in the course file to initialize a concurrent
   * map in which the key is the course, and the value is an empty concurrent map.
   */
  @Override
  public void run() {
    CsvFileReader csvFileReader = new CsvFileReader(this.filePath);
    csvFileReader.readData();

    String[] courseRecord;
    while ((courseRecord = csvFileReader.readData()) != null) {
      var courseKey =
          courseRecord[MODULE_INDEX] + COURSE_SEPARATOR + courseRecord[PRESENTATION_INDEX];
      this.courseAndClick.put(courseKey, new ConcurrentHashMap<>());
    }

    csvFileReader.closeFile();
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
    InitializeProcessor that = (InitializeProcessor) o;
    return courseAndClick.equals(that.courseAndClick) && filePath.equals(that.filePath);
  }

  /**
   * This method is used to generate the hash code of json reader class
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(courseAndClick, filePath);
  }

  /**
   * This method is used to generate the string representation of json reader class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "InitializeProcessor{" +
        "courseAndClick=" + courseAndClick +
        ", filePath='" + filePath + '\'' +
        '}';
  }
}
