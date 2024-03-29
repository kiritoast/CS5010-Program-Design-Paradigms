package assignment5.concurrentSolution;

import assignment5.sequentialSolution.ArgumentValidator;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Class Validator that validate for Argument that also save a threshold
 */
public class ConcurrentArgValidator extends ArgumentValidator {

  /**
   * The error message that will be thrown when the input threshold is not a valid number
   */
  public static final String THRESHOLD_NOT_A_NUMBER = "Error: Threshold must be a number.";
  private static final int THRESHOLD_INDEX = 1;
  private static final int VALID_CONCURRENT_THRESHOLD_INPUT = 2;

  /**
   * Creates a new instance of argument validator class given the argument
   *
   * @param args argument passed
   */
  public ConcurrentArgValidator(String[] args) {
    super(args);
  }

  /**
   * process the argument and save into argument
   *
   * @return List of strings that store the argument contains class file and threshold
   */
  public List<String> validate() {
    List<String> argValList = super.validate();
    validateThreshold(argValList);
    return argValList;
  }

  /**
   * test if the argument is null of valid length
   *
   * @return boolean true if valid
   */
  protected boolean isArgCountValid() {
    return (super.isArgCountValid() || (this.args != null
        && this.args.length == VALID_CONCURRENT_THRESHOLD_INPUT));
  }

  /**
   * validate if the threshold is a number and valid for the input
   *
   * @param argValList array list that stores infor about threshold
   */
  private void validateThreshold(List<String> argValList) {
    if (this.args.length == VALID_CONCURRENT_THRESHOLD_INPUT) {
      String threshold = this.args[THRESHOLD_INDEX];
      try {
        Integer.parseInt(threshold);
        argValList.add(this.args[THRESHOLD_INDEX]);
      } catch (NumberFormatException e) {
        throw new NumberFormatException(THRESHOLD_NOT_A_NUMBER);
      }
    }
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
    ConcurrentArgValidator that = (ConcurrentArgValidator) o;
    return Arrays.equals(this.args, that.args);
  }

  /**
   * This method is used to generate the hash code of CSV concurrent reader class
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Arrays.hashCode(this.args);
  }

  /**
   * This method is used to generate the string representation of CSV concurrent reader class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "CSVConcurrentReader{" + "filePath='" + Arrays.toString(args) + '}';
  }
}
