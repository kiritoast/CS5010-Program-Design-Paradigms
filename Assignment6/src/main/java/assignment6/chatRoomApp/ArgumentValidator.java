package assignment6.chatRoomApp;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * represent the class argument validate which helps validate the ip address and port.
 */
public class ArgumentValidator {

  String[] argument;
  /**
   * Error message to be thrown when the length of the argument array is invalid.
   */
  private static final String INVALID_ARGS_EXCEPTION = "\nThere should be one argument of server IP and the second one as server port.";
  private static final String DEFAULT_LOCAL_HOST = "localhost";
  /**
   * Error message to be thrown when the provided server IP is not valid.
   */
  private static final String INVALID_IP_EXCEPTION = "\nThe input server IP is not valid.";
  /**
   * Error message to be thrown when the provided server port is not valid.
   */
  private static final String INVALID_PORT_EXCEPTION = "\nThe input server port is not valid.";
  private static final int VALID_ARGS_NUM = 2;
  private static final int IP_INDEX = 0;
  private static final int PORT_INDEX = 1;
  private static final String VALID_IP_PART_MATCHER = "(\\d{1,2}|([01])\\d{2}|2[0-4]\\d|25[0-5])";
  private static final String IP_SEPARATOR = "\\.";
  private static final int PORT_LOWER_LIMIT = 0;
  private static final int PORT_UPPER_LIMIT = 65535;

  /**
   * Constructs a new ArgumentValidator instance with the given arguments to validate.
   *
   * @param argument an array of two String values representing the server IP address and port
   *                 number.
   */
  public ArgumentValidator(String[] argument) {
    this.argument = argument;
  }

  /**
   * Validates the server IP address and port number.
   *
   * @return boolean to indicate if validation is successful
   */
  public boolean validate() {
    if (argument.length != VALID_ARGS_NUM) {
      logMessage(INVALID_ARGS_EXCEPTION);
      return false;
    }
    String serverIP = argument[IP_INDEX];
    if (!isValidIPAddress(serverIP) && !serverIP.equals(DEFAULT_LOCAL_HOST)) {
      logMessage(INVALID_IP_EXCEPTION);
      return false;
    }
    String serverPort = argument[PORT_INDEX];
    if (!isValidPort(serverPort)) {
      logMessage(INVALID_PORT_EXCEPTION);
      return false;
    }
    return true;
  }

  /**
   * Checks if the provided IP address is valid.
   *
   * @param ip the IP address to be checked
   * @return true if the IP address is valid, false otherwise
   */
  private boolean isValidIPAddress(String ip) {
    String zeroTo255 = VALID_IP_PART_MATCHER;
    String regex =
        zeroTo255 + IP_SEPARATOR + zeroTo255 + IP_SEPARATOR + zeroTo255 + IP_SEPARATOR + zeroTo255;
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(ip);
    return m.matches();
  }

  /**
   * Checks if the provided port number is valid.
   *
   * @param serverPort the port number to be checked
   * @return true if the port number
   **/
  private boolean isValidPort(String serverPort) {
    int port;
    try {
      port = Integer.parseInt(serverPort);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return port > PORT_LOWER_LIMIT && port < PORT_UPPER_LIMIT;
  }

  /**
   * log string message to the console
   *
   * @param message string which need to be logged
   */
  private static void logMessage(String message) {
    System.out.println(message);
  }

  /**
   * This method is used to compare two instances of argument validator class
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
    return Arrays.equals(argument, that.argument);
  }

  /**
   * This method is used to generate the hash code of argument validator class
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Arrays.hashCode(argument);
  }

  /**
   * This method is used to generate the string representation of argument validator class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "ArgumentValidator{" +
        "argument=" + Arrays.toString(argument) +
        '}';
  }
}
