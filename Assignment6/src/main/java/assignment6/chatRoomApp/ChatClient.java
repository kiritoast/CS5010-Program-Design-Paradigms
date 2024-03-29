package assignment6.chatRoomApp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

/**
 * The ChatClient class represents a client in the chatroom application. It connects to the server,
 * initializes a connection by getting the username from the user, and then runs the client message
 * handler and user input handler on separate thread
 */
public class ChatClient {

  private final Socket serverSocket;
  private final DataInputStream dataInputStream;
  private final DataOutputStream dataOutputStream;
  private UiInputHandler uiInputHandler;
  private final ClientMessageHandler clientMessageHandler;
  private String username;
  private Scanner scanner;

  private static final String WELCOME_MSG = "\nWelcome to the chatroom\n";
  private static final String ASK_USERNAME_MSG = "Please enter your username: ";

  /**
   * Constructs a ChatClient object with a socket connection to the server, input and output
   * streams, and a client message handler with a protocol handler.
   *
   * @param socket a socket connection to the server
   */
  public ChatClient(Socket socket) {
    try {
      this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
      this.dataInputStream = new DataInputStream(socket.getInputStream());
      this.serverSocket = socket;
      clientMessageHandler = new ClientMessageHandler(new ProtocolHandler());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * request to connect to the server, and initialize the input and server message handler
   */
  public void start() {
    try {
      boolean isConnected = initializeConnection();
      if (isConnected) {
        invokeUiServerHandlers();
      }
    } catch (InterruptedException | IOException e) {
      throw new RuntimeException(e);
    } finally {
      closeConnection();
    }
  }

  /**
   * Initializes the connection by prompting the user to enter their username
   *
   * @return boolean if the connection is successful to the server
   * @throws IOException if there is an error in the input/output streams
   */
  private boolean initializeConnection() throws IOException {
    populateClientName();
    return requestConnection();
  }

  /**
   * prompt user to enter username and save it
   */
  private void populateClientName() {
    logMessage(WELCOME_MSG + ASK_USERNAME_MSG);
    scanner = new Scanner(System.in);
    this.username = scanner.next();
  }

  /**
   * initialize ui input handler instance send connection request to server
   *
   * @return if the connection is successful to server
   * @throws IOException if there is an error in the input/output streams
   */
  private boolean requestConnection() throws IOException {
    uiInputHandler = new UiInputHandler(username);
    uiInputHandler.initialize(dataOutputStream);
    int messageIdentifier = dataInputStream.readInt();
    return (!clientMessageHandler.process(messageIdentifier, dataInputStream));
  }

  /**
   * Initializes the client message handler and user input handler on separate threads. Waits for
   * both threads to complete before closing the connection
   *
   * @throws InterruptedException thread is interrupted while waiting for completion
   */
  private void invokeUiServerHandlers() throws InterruptedException {
    Thread serverHandler = new Thread(new ServerHandler(clientMessageHandler, dataInputStream));
    serverHandler.start();
    Thread uiHandler = new Thread(new UiHandler(scanner, uiInputHandler, dataOutputStream));
    uiHandler.start();
    serverHandler.join();
    uiHandler.join();
  }

  /**
   * Closes the connection by closing the input and output streams, socket connection, and scanner
   */
  private void closeConnection() {
    try {
      dataInputStream.close();
      dataOutputStream.close();
      serverSocket.close();
      scanner.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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
   * This method is used to generate the string representation of chat client class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "ChatClient{" +
        "serverSocket=" + serverSocket +
        ", dataInputStream=" + dataInputStream +
        ", dataOutputStream=" + dataOutputStream +
        ", uiInputHandler=" + uiInputHandler +
        ", clientMessageHandler=" + clientMessageHandler +
        ", username='" + username + '\'' +
        ", scanner=" + scanner +
        '}';
  }

  /**
   * This method is used to compare two instances of chat client class
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
    ChatClient that = (ChatClient) o;
    return Objects.equals(serverSocket, that.serverSocket) && Objects.equals(
        dataInputStream, that.dataInputStream) && Objects.equals(dataOutputStream,
        that.dataOutputStream) && Objects.equals(uiInputHandler, that.uiInputHandler)
        && Objects.equals(clientMessageHandler, that.clientMessageHandler)
        && Objects.equals(username, that.username) && Objects.equals(scanner,
        that.scanner);
  }

  /**
   * This method is used to generate the hash code of chat client class
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(serverSocket, dataInputStream, dataOutputStream, uiInputHandler,
        clientMessageHandler, username, scanner);
  }
}
