package assignment4.problem1;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the sentence generator which helps to generate sentence from the given grammar
 */
public class SentenceGenerator {

  private JsonReader jsonReader;

  private static final String START = "start";
  private static final String NON_TERMINAL_PATTERN = "<(.*?)>";
  private static final String FORMAT_SPACE_PATTERN = "\\s+";
  private static final String FORMAT_SEPARATOR_PATTERN = "(\\w)(\\s+)([.,;!?])";
  private static final String FORMAT_SINGLE_PATTERN = "(\\s)('s\\s)";
  private static final String SINGLE_SPACE = " ";
  private static final String GROUP_TWO = "$2";
  private static final String GROUP_ONE_THREE = "$1$3";
  private static final int ZERO = 0;
  private static final int ONE = 1;
  /**
   * This message is used to inform the end user that sentence generation failed due to an undefined
   * non-terminal
   */
  public static final String NOT_EXISTING_MESSAGE = "Program couldn't generate a message as non-terminal '%s' is not defined.";

  /**
   * Creates a new instance of sentence generator given the file path of file
   *
   * @param filePath file path
   */
  public SentenceGenerator(String filePath) {
    this.jsonReader = new JsonReader(filePath);
  }

  /**
   * Generates a new set of sentences
   *
   * @return generated sentence
   */
  public String generate() {
    try {
      String start = getRandomString(START);
      String sentence = generateSentence(start);
      sentence = formatSentence(sentence);
      return sentence;
    } catch (RuntimeException exception) {
      return exception.getMessage();//if message generation fails due to undefined non-terminal, this block returns that message
    }
  }

  /**
   * Generates a sentence from the given input
   *
   * @param input start of the sentence
   * @return final generated sentence
   */
  private String generateSentence(String input) {
    Pattern pattern = Pattern.compile(NON_TERMINAL_PATTERN);
    Matcher matcher = pattern.matcher(input);

    if (matcher.find()) {
      String nonTerminal = matcher.group(ONE);
      input = input.replace(matcher.group(ZERO), getRandomString(nonTerminal));
      input = generateSentence(input);
    }

    return input;
  }

  /**
   * Gives a string randomly from the set of strings stored under the given key
   *
   * @param key key in the json file
   * @return randomly selected string
   */
  private String getRandomString(String key) {
    List<String> valueList = this.jsonReader.readArrayValue(key);

    if (valueList.size() == ZERO) {
      throw new RuntimeException(String.format(NOT_EXISTING_MESSAGE,
          key)); //message generation failed as the given key is not defined
    }

    return getRandomString(valueList);
  }

  /**
   * Gives a string randomly from the list of strings
   *
   * @param valueList list of strings
   * @return randomly selected string
   */
  private String getRandomString(List<String> valueList) {
    int index = getRandomNumber(valueList.size());
    return valueList.get(index);
  }

  /**
   * Generate a random number
   *
   * @param upperBound upper boundary of the generated number
   * @return random number
   */
  private int getRandomNumber(int upperBound) {
    Random random = new Random();
    return random.nextInt(upperBound);
  }

  /**
   * Format the generated sentence to make it more readable
   *
   * @param sentence generated sentence
   * @return formatted sentence
   */
  private String formatSentence(String sentence) {
    // format multiple space pattern
    sentence = sentence.replaceAll(FORMAT_SPACE_PATTERN, SINGLE_SPACE);
    //format separator lines like -> no space should be allowed before separators like comma, full stop etc.
    sentence = sentence.replaceAll(FORMAT_SEPARATOR_PATTERN, GROUP_ONE_THREE);
    //to handle words like -> Rousseau 's
    sentence = sentence.replaceAll(FORMAT_SINGLE_PATTERN, GROUP_TWO);
    return sentence;
  }

  /**
   * This method is used to compare two instances of sentence generator class
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
    SentenceGenerator that = (SentenceGenerator) o;
    return Objects.equals(this.jsonReader, that.jsonReader);
  }

  /**
   * This method is used to generate the hashcode of sentence generator class
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.jsonReader);
  }

  /**
   * This method is used to generate the string representation of sentence generator class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "SentenceGenerator{" +
        "jsonReader=" + this.jsonReader +
        '}';
  }
}
