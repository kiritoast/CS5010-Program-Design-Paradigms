package assignment6.chatRoomApp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Represents the server class in chat room application
 */
public class ChatServer {

  private static final int PORT_NUMBER = 3000;
  private static final String SERVER_RUNNING_MESSAGE =
      "\nChatroom server is running on port number " + PORT_NUMBER;
  private static final String DUPLICATE_SERVER_MESSAGE = "\nFailed to launch the chatroom server as another server is already running on the same port";

  /**
   * Method which helps to start a chat server and waits for clients to join the chat room
   */
  public void start() {
    ExecutorService serverExecutor = Executors.newCachedThreadPool();
    ServerMessageHandler serverMessageHandler = new ServerMessageHandler();
    ServerSocket serverSocket = null;

    try {
      serverSocket = new ServerSocket(PORT_NUMBER);
      logMessage(SERVER_RUNNING_MESSAGE);

      while (!serverSocket.isClosed()) {
        Socket clientSocket = serverSocket.accept();
        serverExecutor.submit(new ClientHandler(clientSocket, serverMessageHandler));
      }
    } catch (IOException e) {
      if (serverSocket == null) {
        logMessage(DUPLICATE_SERVER_MESSAGE);
        return;
      }
      throw new RuntimeException(e);
    } finally {
      try {
        if (serverSocket != null) {
          serverSocket.close();
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * This method helps to log the messages to console
   *
   * @param message message
   */
  private static void logMessage(String message) {
    System.out.println(message);
  }
}
