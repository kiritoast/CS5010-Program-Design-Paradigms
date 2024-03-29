package assignment4.problem1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class InputProcessor contains information about the input directory path, the list of grammar
 * titles, the list of file paths, previous option, sentence generator, and continue flag.
 */
public class InputProcessor {

  private File fileDirectory;
  private List<String> grammarTitles;
  private List<String> filePaths;
  private String previousOption;
  private SentenceGenerator sentenceGenerator;
  private boolean continueFlag;
  private BufferedReader reader;

  private static final int ZERO = 0;
  private static final int ONE = 1;
  private static final char SUFFIX_SEPARATOR = '.';
  private static final String BLANK = "";
  private static final String INDEX_TITLE_SEPARATOR = ". ";
  private static final int NEGATIVE_ONE = -1;
  private static final String NUMBER_MATCHER = "[0-9]+";

  private static final String QUIT_FLAG = "q";
  private static final String REUSE_TRUE_FLAG = "y";
  private static final String REUSE_FALSE_FLAG = "n";
  private static final String SUFFIX_JSON = "json";
  private static final String KEY_GRAMMAR_TITLE = "grammarTitle";

  private static final String LOADING_MSG = "\nLoading grammars...";
  private static final String APP_CLOSED_MSG = "\nSentence Generator App Closed.";
  private static final String DISPLAY_MSG = "\nThe following grammars are available:";
  private static final String REUSE_GRAMMAR_MSG = "\nWhich would you like to use?  (q to quit)";
  private static final String INVALID_GRAMMAR_IDX_MSG = "\nInvalid Index. Please input again:  (q to quit)";
  private static final String REPEAT_GRAMMAR_MSG = "\nWould you like another? (y/n)";
  private static final String INVALID_REPEAT_MSG = "\nInvalid Input. Please input again:  (q to quit)";
  private static final String NEW_LINE = "\n";

  /**
   * Constructs a new input processor, based upon all of provided input parameters; the input
   * directory path is converted as File object, the list of grammar titles and the list of file
   * paths set as Empty list, previous option set as empty string, sentence generator set as empty
   * object, and continue flag set as true.
   *
   * @param fileDirectory the input directory path as File type
   */
  public InputProcessor(File fileDirectory) {
    this.fileDirectory = fileDirectory;
    this.grammarTitles = new ArrayList<>();
    this.filePaths = new ArrayList<>();
    this.previousOption = BLANK;
    this.continueFlag = true;
  }

  /**
   * Start the process of input process.
   *
   * @throws IOException thrown if the input directory or the input option is not correct
   */
  public void process() throws IOException {
    initialize();
    while (this.continueFlag) {
      displayAndProcess();
    }
  }

  /**
   * Show loading message, and read file information from input directory.
   */
  private void initialize() {
    logMessage(LOADING_MSG);
    reader = new BufferedReader(new InputStreamReader(System.in));
    populateOptions();
  }

  /**
   * Read file information from input directory, and store them in the grammarTitles and filePaths
   * fields
   */
  private void populateOptions() {
    File[] files = this.fileDirectory.listFiles();

    for (File file : files) {
      String filePath = file.getAbsolutePath();
      if (getFileExtension(filePath).equals(SUFFIX_JSON)) {
        JsonReader jsonReader = new JsonReader(filePath);
        String grammarTitle = jsonReader.readValue(KEY_GRAMMAR_TITLE);
        this.grammarTitles.add(grammarTitle);
        this.filePaths.add(filePath);
      }
    }
  }

  /**
   * Display all the grammar files with index, and process based on user's input option.
   *
   * @throws IOException thrown if the input option is not valid.
   */
  private void displayAndProcess() throws IOException {
    displayOptions();
    String option = readOption();

    if (option.equals(QUIT_FLAG)) {
      this.continueFlag = false;
      logMessage(APP_CLOSED_MSG);
    } else {
      processOption(option);
    }
  }

  /**
   * Display all the grammar files with index.
   */
  private void displayOptions() {
    logMessage(DISPLAY_MSG);
    for (int i = ZERO; i < this.grammarTitles.size(); i++) {
      logMessage((i + ONE) + INDEX_TITLE_SEPARATOR + this.grammarTitles.get(i));
    }
    logMessage(REUSE_GRAMMAR_MSG);
  }

  /**
   * Return the option that are given by user's input.
   *
   * @return the user's input option
   * @throws IOException thrown if the input option is not valid.
   */
  private String readOption() throws IOException {
    return reader.readLine();
  }

  /**
   * Generate a sentence based on user's input option which is an index of grammar file, and ask the
   * user to repeat or not.
   *
   * @param option the user's input option
   * @throws IOException thrown if the input index or repeat option is not valid.
   */
  private void processOption(String option) throws IOException {
    String checkedOption = checkIndexOption(option);
    if (!checkedOption.equals(QUIT_FLAG)) {
      if (!this.previousOption.equals(checkedOption)) {
        this.sentenceGenerator = new SentenceGenerator(
            this.filePaths.get(Integer.parseInt(checkedOption) - ONE));
      }
      String sentence = this.sentenceGenerator.generate();
      logMessage(NEW_LINE + sentence);
      repeatCurrentGrammar();
    }
  }

  /**
   * Check the input index option is valid; if not, the user will be asked to input a correct index
   * or input 'q' to quit.
   *
   * @param originOption the original input index
   * @return the valid input index
   * @throws IOException thrown if the input index option is not valid.
   */
  private String checkIndexOption(String originOption) throws IOException {
    String option = originOption;
    while (!option.matches(NUMBER_MATCHER) || Integer.parseInt(option) < ONE
        || Integer.parseInt(option) > this.grammarTitles.size()) {
      logMessage(INVALID_GRAMMAR_IDX_MSG);
      option = readOption();
      if (option.equals(QUIT_FLAG)) {
        break;
      }
    }
    return option;
  }

  /**
   * Ask user to re-generate a sentence from the previous chosen grammar file.
   *
   * @throws IOException thrown if the input repeat option is not valid.
   */
  private void repeatCurrentGrammar() throws IOException {
    boolean repeatFlag = true;
    while (repeatFlag) {
      logMessage(REPEAT_GRAMMAR_MSG);
      String option = readOption();
      String checkedOption = checkBooleanOption(option);
      if (checkedOption.equals(QUIT_FLAG)) {
        break;
      }
      if (checkedOption.equals(REUSE_TRUE_FLAG)) {
        String sentence = this.sentenceGenerator.generate();
        logMessage(NEW_LINE + sentence);
      } else {
        repeatFlag = false;
      }
    }
  }

  /**
   * Check the input repeat option is valid; if not, the user will be asked to input a correct
   * repeat option or input 'q' to quit.
   *
   * @param originOption the original repeat index
   * @return the valid input repeat option
   * @throws IOException thrown if the input repeat option is not valid.
   */
  private String checkBooleanOption(String originOption) throws IOException {
    String option = originOption;
    while (!option.equals(REUSE_TRUE_FLAG) && !option.equals(REUSE_FALSE_FLAG)) {
      logMessage(INVALID_REPEAT_MSG);
      option = readOption();
      if (option.equals(QUIT_FLAG)) {
        break;
      }
    }
    return option;
  }

  /**
   * Get the suffix of the given file path; If the path is not valid, an empty string will be
   * returned.
   *
   * @param fullName the given file path
   * @return the suffix of the given file path
   */
  private static String getFileExtension(String fullName) {
    if (fullName != null) {
      String fileName = new File(fullName).getName();
      int dotIndex = fileName.lastIndexOf(SUFFIX_SEPARATOR);
      return (dotIndex == NEGATIVE_ONE) ? BLANK : fileName.substring(dotIndex + ONE);
    }
    return BLANK;
  }

  /**
   * display the message according to the given string
   *
   * @param message the message which needs to be displayed
   */
  private void logMessage(String message) {
    System.out.println(message);
  }

  /**
   * Indicates whether some other object is "equal to" this one. *
   *
   * @param o the reference object with which to compare.
   * @return true if this object is the same as the obj argument; false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InputProcessor that = (InputProcessor) o;
    return this.continueFlag == that.continueFlag && this.fileDirectory.equals(that.fileDirectory)
        && this.grammarTitles.equals(that.grammarTitles) && this.filePaths.equals(that.filePaths)
        && this.previousOption.equals(that.previousOption) && this.sentenceGenerator.equals(
        that.sentenceGenerator);
  }

  /**
   * Returns a hash code value for the object. *
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.fileDirectory, this.grammarTitles, this.filePaths, this.previousOption,
        this.sentenceGenerator,
        this.continueFlag);
  }

  /**
   * Returns a string representation of the object. In general, the toString method returns a string
   * that "textually represents" this object. *
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return "InputProcessor{" +
        "fileDirectory=" + this.fileDirectory +
        ", grammarTitles=" + this.grammarTitles +
        ", filePaths=" + this.filePaths +
        ", previousOption='" + this.previousOption + '\'' +
        ", sentenceGenerator=" + this.sentenceGenerator +
        ", continueFlag=" + this.continueFlag +
        '}';
  }
}
