package assignment6.chatRoomApp;

import java.io.DataOutputStream;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represents a runnable handler to process the input command from client app
 */
public class UiHandler implements Runnable {

  private final Scanner scanner;
  private final UiInputHandler uiInputHandler;
  private final DataOutputStream dataOutputStream;

  private static final String HELPER_MSG = """
      \nWelcome to the chatroom, here are the list of available commands:
      • ?: show the menu options
      • logoff: sends a DISCONNECT_MESSAGE to the server
      • who: sends a QUERY_CONNECTED_USERS to the server
      • @user <message>: sends a DIRECT_MESSAGE to the specified user to the server
      • @all <message>: sends a BROADCAST_MESSAGE to the server, to be sent to all users connected
      • !user: sends a SEND_INSULT message to the server, to be sent to the specified user
      """;

  /**
   * Creates an input handler with the information of the console scanner, the UI message handler
   * and the client output stream
   *
   * @param scanner          the console scanner
   * @param uiInputHandler   the UI message handler
   * @param dataOutputStream the client output stream
   */
  public UiHandler(Scanner scanner, UiInputHandler uiInputHandler,
      DataOutputStream dataOutputStream) {
    this.scanner = scanner;
    this.uiInputHandler = uiInputHandler;
    this.dataOutputStream = dataOutputStream;
  }

  @Override
  public void run() {
    try {
      logMessage(HELPER_MSG);
      boolean isConnected;
      String input;
      scanner.nextLine();//don't remove
      do {
        input = scanner.nextLine();
        isConnected = uiInputHandler.process(input, dataOutputStream);
      } while (isConnected);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static void logMessage(String message) {
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
    UiHandler uiHandler = (UiHandler) o;
    return uiInputHandler.equals(uiHandler.uiInputHandler)
        && dataOutputStream.equals(uiHandler.dataOutputStream);
  }

  /**
   * Returns a hash code value for the object. *
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(uiInputHandler, dataOutputStream);
  }

  /**
   * Returns a string representation of the object. In general, the toString method returns a string
   * that "textually represents" this object. *
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return "UiHandler{" +
        ", uiInputHandler=" + uiInputHandler +
        ", dataOutputStream=" + dataOutputStream +
        '}';
  }
}
