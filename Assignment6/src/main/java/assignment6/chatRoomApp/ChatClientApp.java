package assignment6.chatRoomApp;

import java.io.IOException;
import java.net.Socket;

/**
 * Represents the chat room client that helps run the chatroom
 */
public class ChatClientApp {

  private static final int IP_INDEX = 0;
  private static final int PORT_INDEX = 1;
  /**
   * Message to notify that chat room server is not up and running
   */
  public static final String SERVER_NOT_AVAILABLE = "\nFailed to launch the client as chatroom server is not up";

  /**
   * main method to the client
   *
   * @param args take ip and server port
   */
  public static void main(String[] args) {
    Socket serverSocket = null;
    try {
      ArgumentValidator argumentValidator = new ArgumentValidator(args);
      boolean isValid = argumentValidator.validate();
      if (!isValid) {
        return;
      }

      String serverIP = args[IP_INDEX];
      int serverPort = Integer.parseInt(args[PORT_INDEX]);

      serverSocket = new Socket(serverIP, serverPort);
      ChatClient chatClient = new ChatClient(serverSocket);
      chatClient.start();
    } catch (IOException e) {
      if (serverSocket == null) {
        System.out.println(SERVER_NOT_AVAILABLE);
        return;
      }
      throw new RuntimeException(e);
    }
  }
}
