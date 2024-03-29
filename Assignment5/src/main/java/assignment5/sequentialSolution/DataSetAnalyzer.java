package assignment5.sequentialSolution;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents the sequential dataset analyzer class which helps to analyze the given data set and
 * generate output
 */
public class DataSetAnalyzer {

  private final String courseFilePath;
  private final String studentFilePath;
  private String summaryOutputDirectory;
  private static final String DATE_HEADER = "date";
  private static final String SUM_CLICK_HEADER = "sum_click";
  private static final String OUTPUT_EXTENSION = ".csv";

  private static final int MODULE_INDEX = 0;
  private static final int INITIAL_COUNT = 0;
  private static final int PRESENTATION_INDEX = 1;
  private static final int DATE_INDEX = 4;
  private static final int SUM_CLICK_INDEX = 5;
  private static final String COURSE_SEPARATOR = "_";
  private static final String SUMMARY_FOLDER = "summary";

  /**
   * Creates a new instance of sequential dataset analyzer given the course file path and student
   * file path
   *
   * @param courseFilePath  course file path
   * @param studentFilePath student file path
   */
  public DataSetAnalyzer(String courseFilePath, String studentFilePath) {
    this.courseFilePath = courseFilePath;
    this.studentFilePath = studentFilePath;
    this.summaryOutputDirectory = assignment5.concurrentSolution.DataSetAnalyzer.deleteAndCreateDirectory(
        courseFilePath, SUMMARY_FOLDER);
  }

  /**
   * Process the given set of files and generate output
   */
  public void process() {
    Map<String, Map<String, Integer>> courseAndClick = processCourseFile();
    processStudentFile(courseAndClick);
    writeOutputFiles(courseAndClick);
  }

  /**
   * Read the course file content and add the course name keys in hashmap
   *
   * @return map of course names
   */
  private Map<String, Map<String, Integer>> processCourseFile() {
    Map<String, Map<String, Integer>> courseAndClick = new HashMap<>();
    CsvFileReader csvFileReader = new CsvFileReader(this.courseFilePath);
    csvFileReader.readData();//skip the header

    String[] courseRecord;
    while ((courseRecord = csvFileReader.readData()) != null) {
      var courseKey =
          courseRecord[MODULE_INDEX] + COURSE_SEPARATOR + courseRecord[PRESENTATION_INDEX];
      courseAndClick.put(courseKey, new HashMap<>());
    }

    csvFileReader.closeFile();
    return courseAndClick;
  }

  /**
   * go through the student file and keep adding the click and save it to the hashmap
   *
   * @param courseAndClick map of course name and sum click
   */
  private void processStudentFile(Map<String, Map<String, Integer>> courseAndClick) {
    CsvFileReader csvFileReader = new CsvFileReader(this.studentFilePath);
    csvFileReader.readData();//skip the header

    String[] studentRecord;
    while ((studentRecord = csvFileReader.readData()) != null) {
      var courseKey =
          studentRecord[MODULE_INDEX] + COURSE_SEPARATOR + studentRecord[PRESENTATION_INDEX];

      Map<String, Integer> dateAndClick = courseAndClick.getOrDefault(courseKey, new HashMap<>());
      Integer newVal =
          dateAndClick.getOrDefault(studentRecord[DATE_INDEX], INITIAL_COUNT) + Integer.parseInt(
              studentRecord[SUM_CLICK_INDEX]);
      dateAndClick.put(studentRecord[DATE_INDEX], newVal);
      courseAndClick.put(courseKey, dateAndClick);
    }
  }

  /**
   * go through the mao and create course files based on the key inside hashmap
   *
   * @param courseAndClick map of course name and sum click
   */
  private void writeOutputFiles(Map<String, Map<String, Integer>> courseAndClick) {
    String[] outputHeader = new String[]{DATE_HEADER, SUM_CLICK_HEADER};

    for (var courseClick : courseAndClick.entrySet()) {
      String filePath = this.summaryOutputDirectory + courseClick.getKey() + OUTPUT_EXTENSION;
      CsvFileWriter csvFileWriter = new CsvFileWriter(filePath);
      csvFileWriter.writeData(outputHeader);

      for (var dateClick : courseClick.getValue().entrySet()) {
        csvFileWriter.writeData(new String[]{dateClick.getKey(), dateClick.getValue().toString()});
      }
      csvFileWriter.closeFile();
    }
  }

  /**
   * Compares if the given instance of dataset analyzer is same as the current one
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
    DataSetAnalyzer that = (DataSetAnalyzer) o;
    return Objects.equals(this.courseFilePath, that.courseFilePath) && Objects.equals(
        this.studentFilePath, that.studentFilePath);
  }

  /**
   * Generates a hashcode for the current instance of dataset analyzer
   *
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.courseFilePath, this.studentFilePath);
  }

  /**
   * Generates the string representation of dataset analyzer instance
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "DataSetAnalyzer{" +
        "courseFilePath='" + this.courseFilePath + '\'' +
        ", studentFilePath='" + this.studentFilePath + '\'' +
        '}';
  }
}
