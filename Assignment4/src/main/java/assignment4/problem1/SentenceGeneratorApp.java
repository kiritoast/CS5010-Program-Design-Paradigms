package assignment4.problem1;

import java.io.File;
import java.io.IOException;

/**
 * The SentenceGeneratorApp class is the launcher of Sentence Generator App which can handle the
 * input argument.
 */
public class SentenceGeneratorApp {

  private static final String INVALID_ARGUMENT_ERROR = "Error: This application is expecting parameter folder path to be passed. Please check the readme.md file for details.";
  private static final String INVALID_FOLDER_ERROR = "Error: The given folder path '%s' is not valid.";
  private static final int ZERO = 0;
  private static final int ONE = 1;

  /**
   * Check the input argument is a valid folder path
   *
   * @param args the input argument
   * @return the folder path as File type
   * @throws RuntimeException thrown if the format of the input argument is not correct or the given
   *                          path is not a valid folder path.
   */
  private File validateArgument(String[] args) {
    if (args == null || args.length != ONE) {
      throw new RuntimeException(INVALID_ARGUMENT_ERROR);
    }

    File fileDirectory = new File(args[ZERO]);
    if (!(fileDirectory.exists() && fileDirectory.isDirectory())) {
      throw new RuntimeException(String.format(INVALID_FOLDER_ERROR, args[ZERO]));
    }

    return fileDirectory;
  }

  /**
   * The main method to launch the Sentence Generator App.
   *
   * @param args the input argument
   * @throws IOException thrown if the input argument does not match the requirement.
   */
  public static void main(String[] args) throws IOException {
    SentenceGeneratorApp app = new SentenceGeneratorApp();
    File fileDirectory = app.validateArgument(args);

    InputProcessor inputProcessor = new InputProcessor(fileDirectory);
    inputProcessor.process();
  }
}