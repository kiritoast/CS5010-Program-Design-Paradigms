package assignment5.concurrentSolution;

import java.util.List;

/**
 * Represents the university data set analyzer application which helps to analyze the given data set
 * in a concurrent manner
 */
public class UniversityDataAnalyzerApp {

  private static final int COURSE_FILE_INDEX = 0;
  private static final int STUDENT_FILE_INDEX = 1;
  private static final int THRESHOLD_FILE_INDEX = 2;
  private static final int VALID_NON_THRESHOLD_INPUT_NUMBER = 2;

  /**
   * Main class which helps to invoke the application. It will accept the directory path, where the
   * data set files are present. The second argument is optional and is the threshold
   *
   * @param args directory path as first argument; the threshold as second argument. the threshold
   *             is optional
   */
  public static void main(String[] args) {
    ConcurrentArgValidator concurrentArgValidator = new ConcurrentArgValidator(args);
    List<String> argValList = concurrentArgValidator.validate();

    DataSetAnalyzer dataSetAnalyzer;

    if (argValList.size() == VALID_NON_THRESHOLD_INPUT_NUMBER) {
      dataSetAnalyzer = new DataSetAnalyzer(argValList.get(COURSE_FILE_INDEX),
          argValList.get(STUDENT_FILE_INDEX));
      dataSetAnalyzer.process();
    } else {
      dataSetAnalyzer = new DataSetAnalyzer(argValList.get(COURSE_FILE_INDEX),
          argValList.get(STUDENT_FILE_INDEX), argValList.get(THRESHOLD_FILE_INDEX));
      dataSetAnalyzer.processThreshold();
    }
  }
}
