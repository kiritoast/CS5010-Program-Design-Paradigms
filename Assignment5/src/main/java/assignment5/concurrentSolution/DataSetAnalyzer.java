package assignment5.concurrentSolution;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Represents the concurrent dataset analyzer class which helps to analyze the given data set and
 * generate output
 */
public class DataSetAnalyzer {

  private final String courseFilePath;
  private final String studentFilePath;
  private String summaryOutputDirectory;
  private String thresholdOutputDirectory;
  private String threshold;
  private ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick;

  private static final int FILE_START_OFFSET = 0;
  private static final int FILE_END_OFFSET = 1;
  private static final String SUMMARY_FOLDER = "summary";
  private static final String THRESHOLD_FOLDER = "threshold";

  /**
   * Creates a new instance of concurrent dataset analyzer given the course file path and student
   * file path
   *
   * @param courseFilePath  course file path
   * @param studentFilePath student file path
   */
  public DataSetAnalyzer(String courseFilePath, String studentFilePath) {
    this.courseFilePath = courseFilePath;
    this.studentFilePath = studentFilePath;
    this.summaryOutputDirectory = deleteAndCreateDirectory(courseFilePath, SUMMARY_FOLDER);
  }

  /**
   * Creates a new instance of concurrent dataset analyzer given the course file path, student file
   * path and threshold
   *
   * @param courseFilePath  course file path
   * @param studentFilePath student file path
   * @param threshold       the threshold used to identify valid course
   */
  public DataSetAnalyzer(String courseFilePath, String studentFilePath, String threshold) {
    this(courseFilePath, studentFilePath);
    this.threshold = threshold;
    this.thresholdOutputDirectory = deleteAndCreateDirectory(courseFilePath, THRESHOLD_FOLDER);
  }

  /**
   * Process the given set of files and generate output for non-threshold option
   */
  public void process() {
    this.courseAndClick = new ConcurrentHashMap<>();
    processCourseFile();
    processStudentFile();
    writeOutputFiles();
  }

  /**
   * Read the course file, and initialize the concurrent map
   */
  private void processCourseFile() {
    Thread courseFileReader = new Thread(
        new InitializeProcessor(this.courseFilePath, this.courseAndClick));
    courseFileReader.start();
    try {
      courseFileReader.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Read the student fil, and set the concurrent map
   */
  private void processStudentFile() {
    CSVConcurrentReader csvConcurrentReader = new CSVConcurrentReader(this.studentFilePath,
        this.courseAndClick);
    csvConcurrentReader.readCSVFileToMap();
  }

  /**
   * write the valid course information to the summary folder for each course
   */
  private void writeOutputFiles() {
    CSVConcurrentWriter csvConcurrentWriter = new CSVConcurrentWriter(this.courseAndClick,
        this.summaryOutputDirectory);
    csvConcurrentWriter.writeOutputFiles();
  }

  /**
   * Process the given set of files and generate output for threshold option
   */
  public void processThreshold() {
    process();
    outputThresholdFile();
  }

  /**
   * Read all the summary files, and output the activity file
   */
  private void outputThresholdFile() {
    ThresholdProcessor thresholdProcessor = new ThresholdProcessor(this.summaryOutputDirectory,
        this.thresholdOutputDirectory, this.threshold);
    thresholdProcessor.filterBasedOnThreshold();
  }

  /**
   * Delete all the subdirectories and files in current directory recursively
   *
   * @param directory the current directory
   */
  private static void deleteDirectory(File directory) {
    if (directory.isDirectory()) {
      File[] files = directory.listFiles();
      if (files != null) {
        for (File file : files) {
          deleteDirectory(file);
        }
      }
    }
    directory.delete();
  }

  /**
   * Given a file path and directory name, delete and create the directory in the given file path
   *
   * @param inputFilePath input file path
   * @param directoryName directory name
   * @return path of newly created directory
   */
  public static String deleteAndCreateDirectory(String inputFilePath, String directoryName) {
    String currentDirectory = inputFilePath.substring(FILE_START_OFFSET,
        inputFilePath.lastIndexOf(File.separator) + FILE_END_OFFSET);
    String newDirectoryPath = currentDirectory + directoryName + File.separator;

    File newDirectory = new File(newDirectoryPath);
    if (newDirectory.exists()) {
      deleteDirectory(newDirectory);
    }
    newDirectory.mkdirs();

    return newDirectoryPath;
  }

  /**
   * {@inheritDoc}
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
    return Objects.equals(courseFilePath, that.courseFilePath) && Objects.equals(
        studentFilePath, that.studentFilePath) && Objects.equals(summaryOutputDirectory,
        that.summaryOutputDirectory) && Objects.equals(thresholdOutputDirectory,
        that.thresholdOutputDirectory) && Objects.equals(threshold, that.threshold)
        && Objects.equals(courseAndClick, that.courseAndClick);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(courseFilePath, studentFilePath, summaryOutputDirectory,
        thresholdOutputDirectory, threshold, courseAndClick);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "DataSetAnalyzer{" +
        "courseFilePath='" + courseFilePath + '\'' +
        ", studentFilePath='" + studentFilePath + '\'' +
        ", summaryOutputDirectory='" + summaryOutputDirectory + '\'' +
        ", thresholdOutputDirectory='" + thresholdOutputDirectory + '\'' +
        ", threshold='" + threshold + '\'' +
        ", courseAndClick=" + courseAndClick +
        '}';
  }
}
