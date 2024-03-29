package assignment6.chatRoomApp;

import java.io.DataOutputStream;
import java.util.Objects;

/**
 * class that represents the input message handler in client side. it will take user input and save
 * it in class MessageFormat and pass it to the server.
 */
public class UiInputHandler {

  private final ProtocolHandler protocolHandler;
  private final String clientName;
  private static final int SPACE_INDEX_AFTER_MSG_HEADER = 1;
  private static final int MSG_HEADER_INDEX = 0;
  private static final String BLANK_SPACE_STR = " ";
  private static final String HELP_MSG_HEADER = "?";
  /**
   * String message that shows up after user enter "?"
   */
  private static final String HELP_MSG = """
      • logoff: sends a DISCONNECT_MESSAGE to the server
      • who: sends a QUERY_CONNECTED_USERS to the server
      • @user <message>: sends a DIRECT_MESSAGE to the specified user to the server
      • @all <message>: sends a BROADCAST_MESSAGE to the server, to be sent to all users connected
      • !user: sends a SEND_INSULT message to the server, to be sent to the specified user              
      """;
  private static final String LOGOFF_MSG_HEADER = "logoff";
  private static final String USER_QUERY_MSG_HEADER = "who";
  private static final String MSG_STARTER_CHAR = "@";
  private static final int VALID_MSG_SIZE = 2;
  /**
   * error message when user pass the message but not the content
   */
  private static final String INVALID_MSG_SIZE_EXCEPTION = "User is expected to pass the message content as well.";
  private static final String BROADCAST_MSG_HEADER = "@all";
  private static final int MSG_START_OFFSET = 1;
  private static final int RECIPIENT_START_OFFSET = 1;
  private static final String INSULT_MSG_HEADER = "!";
  /**
   * error message when user input the wrong command
   */
  private static final String INVALID_MSG_EXCEPTION = "You have given an unknown command, press '?' to list all available commands.";
  private static final int FIRST_SUBSTR_INDEX = 0;
  private static final int NON_SUBSTR_INDEX = -1;
  private static final int NEXT_SUBSTR_OFFSET = 1;

  /**
   * Constructs a UiInputHandler object with a protocol handler and a client name
   *
   * @param clientName the name of the client
   */

  public UiInputHandler(String clientName) {
    protocolHandler = new ProtocolHandler();
    this.clientName = clientName;
  }

  /**
   * Initializes the UiInputHandler by sending the client name to the server
   *
   * @param dataOutputStream the data output stream to send the message to the server
   */
  public void initialize(DataOutputStream dataOutputStream) {
    MessageFormat messageFormat = new MessageFormat(null, null, null, clientName, null);
    protocolHandler.writeConnectRequest(dataOutputStream, messageFormat);
  }

  /**
   * Reads user input from the console and sends the message to the server in the correct format.
   *
   * @param input            the string input from console
   * @param dataOutputStream the data output stream to send the message to the server
   * @return boolean if the client is connected
   */
  public boolean process(String input, DataOutputStream dataOutputStream) {
    input = input.trim();
    int firstSpace = ordinalIndexOf(input, BLANK_SPACE_STR, SPACE_INDEX_AFTER_MSG_HEADER);
    String identifier = input.split(BLANK_SPACE_STR)[MSG_HEADER_INDEX];

    if (input.equals(HELP_MSG_HEADER)) {// list all commands
      logMessage(HELP_MSG);
    } else if (input.equals(LOGOFF_MSG_HEADER)) {// sends a DISCONNECT_MESSAGE to the server
      MessageFormat messageFormat = new MessageFormat(null, null, null, clientName, null);
      protocolHandler.writeDisconnectRequest(dataOutputStream, messageFormat);
      return false;//close connection
    } else if (input.equals(USER_QUERY_MSG_HEADER)) {// sends a QUERY_CONNECTED_USERS to the server
      MessageFormat messageFormat = new MessageFormat(null, null, null, clientName, null);
      protocolHandler.writeQueryUsersRequest(dataOutputStream, messageFormat);
    } else if (input.startsWith(MSG_STARTER_CHAR)) {// send message
      if (input.split(BLANK_SPACE_STR).length < VALID_MSG_SIZE) {// test if there is a valid message
        System.out.println(INVALID_MSG_SIZE_EXCEPTION);
      } else if (identifier.equals(BROADCAST_MSG_HEADER)) {// send BROADCAST_MESSAGE
        String message = input.substring(firstSpace + MSG_START_OFFSET);
        MessageFormat messageFormat = new MessageFormat(null, message, null, clientName, null);
        protocolHandler.writeBroadcastMessage(dataOutputStream, messageFormat);
      } else {// sends a DIRECT_MESSAGE
        String message = input.substring(firstSpace + MSG_START_OFFSET);
        String recipient = input.substring(RECIPIENT_START_OFFSET, firstSpace);
        MessageFormat messageFormat = new MessageFormat(null, message, null, clientName,
            recipient);
        protocolHandler.writeDirectMessage(dataOutputStream, messageFormat);
      }
    } else if (input.startsWith(INSULT_MSG_HEADER) && !input.contains(
        BLANK_SPACE_STR)) {// sends a SEND_INSULT
      String recipient = input.substring(RECIPIENT_START_OFFSET);
      MessageFormat messageFormat = new MessageFormat(null, null, null, clientName, recipient);
      protocolHandler.writeInsultMessage(dataOutputStream, messageFormat);
    } else {// Error message to user
      logMessage(INVALID_MSG_EXCEPTION);
    }
    return true;//connection is active
  }

  /**
   * log message to console
   *
   * @param message message that needs to be displayed
   */
  private static void logMessage(String message) {
    System.out.println(message);
  }

  /**
   * Returns the position of the nth occurrence of a substring within a string
   *
   * @param str    string to search within
   * @param substr substr the substring to search for
   * @param n      occurrence of the substring to find
   * @return the position of the nth occurrence of the substring within the string, or -1 if not
   * found
   */
  private static int ordinalIndexOf(String str, String substr, int n) {
    int pos = str.indexOf(substr);
    while (--n > FIRST_SUBSTR_INDEX && pos != NON_SUBSTR_INDEX) {
      pos = str.indexOf(substr, pos + NEXT_SUBSTR_OFFSET);
    }
    return pos;
  }

  /**
   * This method is used to compare two instances of UiInputHandler
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
    UiInputHandler that = (UiInputHandler) o;
    return Objects.equals(protocolHandler, that.protocolHandler)
        && Objects.equals(clientName, that.clientName);
  }

  /**
   * This method is used to generate the hash code of UiInputHandler
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(protocolHandler, clientName);
  }

  /**
   * This method is used to generate the string representation of chat client class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "UiInputHandler{" +
        "protocolHandler=" + protocolHandler +
        ", clientName='" + clientName + '\'' +
        '}';
  }
}
