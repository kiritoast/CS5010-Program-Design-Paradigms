package assignment5.sequentialSolution;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represent the argument validator class which helps to validate the given argument. It verifies
 * whether a directory path is given and expected data files are present in that directory
 */
public class ArgumentValidator {

  /**
   * Set of arguments to be validated
   */
  protected final String[] args;
  private String fileDirectory;

  /**
   * This error message is shown when user has not passed the argument correctly
   */
  public static final String INVALID_ARGUMENT_ERROR = "Error: This application is expecting parameter folder path to be passed. Please check the readme.md file for details.";
  /**
   * This error message is shown if file is not present in the given directory path
   */
  public static final String FILE_NOT_FOUND_ERROR = "Error: File '%s' is not found in the given folder path '%s'.";
  private static final String COURSE_FILE_NAME = "courses.csv";
  private static final String STUDENT_FILE_NAME = "studentVle.csv";
  private static final String DEFAULT_FILE_PATH = "";
  private static final int INPUT_FOLDER_INDEX = 0;
  private static final int FILE_PATH_OFFSET = 1;
  private static final int VALID_SEQUENTIAL_INPUT_NUM = 1;

  /**
   * Creates a new instance of argument validator class given the argument
   *
   * @param args argument passed
   */
  public ArgumentValidator(String[] args) {
    this.args = args;
  }

  /**
   * This method validates the given argument and confirms that file structure is present as
   * expected
   *
   * @return file path of course file and student file
   */
  public List<String> validate() {
    validateArguments();
    populateFileDirectory();
    return validateFiles();
  }

  /**
   * Determines whether an argument has been passed
   */
  private void validateArguments() {
    if (!isArgCountValid()) {
      throw new RuntimeException(INVALID_ARGUMENT_ERROR);
    }
  }

  /**
   * determine if the argument is not null or at certain length
   *
   * @return boolean see if argument is valid
   */
  protected boolean isArgCountValid() {
    return (this.args != null && this.args.length == VALID_SEQUENTIAL_INPUT_NUM);
  }

  /**
   * generate the file directory for later create of directory
   */
  private void populateFileDirectory() {
    this.fileDirectory =
        this.args[INPUT_FOLDER_INDEX] + (this.args[INPUT_FOLDER_INDEX].endsWith(File.separator)
            ? DEFAULT_FILE_PATH : File.separator);
  }

  /**
   * Determine whether the expected set of files are present in the given directory path
   *
   * @return file path of course and student file
   */
  private List<String> validateFiles() {
    List<String> filePaths = new ArrayList<>();
    filePaths.add(this.fileDirectory + COURSE_FILE_NAME);
    filePaths.add(this.fileDirectory + STUDENT_FILE_NAME);

    for (String filePath : filePaths) {
      validateFile(filePath);
    }

    return filePaths;
  }

  /**
   * Determines whether a file exists given the file path
   *
   * @param filePath path of file
   */
  private void validateFile(String filePath) {
    File file = new File(filePath);
    if (!file.exists()) {
      throw new RuntimeException(String.format(FILE_NOT_FOUND_ERROR,
          filePath.substring(filePath.lastIndexOf(File.separator) + FILE_PATH_OFFSET),
          this.args[INPUT_FOLDER_INDEX]));
    }
  }

  /**
   * Determines whether the current instance of argument validator class is same as the given
   * instance
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
    ArgumentValidator that = (ArgumentValidator) o;
    return Arrays.equals(this.args, that.args);
  }

  /**
   * Generates a hashcode for the current argument validator instance
   *
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return Arrays.hashCode(this.args);
  }

  /**
   * String representation of the current argument validator instance
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "ArgumentValidator{" +
        "args=" + Arrays.toString(this.args) +
        '}';
  }
}
