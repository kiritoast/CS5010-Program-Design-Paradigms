package assignment5.sequentialSolution;

import java.util.List;

/**
 * Represents the university data set analyzer application which helps to analyze the given data set
 * in a sequential manner
 */
public class UniversityDataAnalyzerApp {

  private static final int COURSE_FILE_INDEX = 0;
  private static final int STUDENT_FILE_INDEX = 1;

  /**
   * Main class which helps to invoke the application. It will accept the directory path, where the
   * data set files are present
   *
   * @param args directory path as first argument
   */
  public static void main(String[] args) {
    ArgumentValidator argumentValidator = new ArgumentValidator(args);
    List<String> filePaths = argumentValidator.validate();//validates the given argument

    DataSetAnalyzer dataSetAnalyzer = new DataSetAnalyzer(filePaths.get(COURSE_FILE_INDEX),
        filePaths.get(STUDENT_FILE_INDEX));
    dataSetAnalyzer.process();//process the data set and generate output files
  }
}
