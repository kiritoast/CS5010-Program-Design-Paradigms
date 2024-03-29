package assignment6.chatRoomApp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

/**
 * Represent the client handler class which helps to communicate with a client once server
 * establishes connection
 */
public class ClientHandler implements Runnable {

  private final Socket clientSocket;
  private final ServerMessageHandler serverMessageHandler;

  /**
   * Creates a new instance of client handler class given the socket and server message handler
   * details
   *
   * @param clientSocket         socket to communicate with the client
   * @param serverMessageHandler helper class to process and respond to the server-client
   *                             communication
   */
  public ClientHandler(Socket clientSocket, ServerMessageHandler serverMessageHandler) {
    this.clientSocket = clientSocket;
    this.serverMessageHandler = serverMessageHandler;
  }

  /**
   * Method which helps to keep the server-client communication
   */
  @Override
  public void run() {
    DataInputStream dataInputStream = null;
    DataOutputStream dataOutputStream = null;

    try {
      dataOutputStream = new DataOutputStream(
          new BufferedOutputStream(clientSocket.getOutputStream()));
      dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));

      boolean isConnectionAlive;
      do {
        int messageIdentifier = dataInputStream.readInt();
        isConnectionAlive = serverMessageHandler.process(messageIdentifier, dataInputStream,
            dataOutputStream);
      } while (isConnectionAlive);

    } catch (IOException e) {
      serverMessageHandler.removeClientInformation(
          dataOutputStream);//when client terminates without proper communication, remove the client information
      throw new RuntimeException(e);
    } finally {
      try {
        dataInputStream.close();
        dataOutputStream.close();
        clientSocket.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * This method is used to compare two instances of client handler class
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
    ClientHandler that = (ClientHandler) o;
    return Objects.equals(clientSocket, that.clientSocket) && Objects.equals(
        serverMessageHandler, that.serverMessageHandler);
  }

  /**
   * This method is used to generate the hash code of client handler class
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(clientSocket, serverMessageHandler);
  }

  /**
   * This method is used to generate the string representation of client handler class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "ClientHandler{" +
        "clientSocket=" + clientSocket +
        ", serverMessageHandler=" + serverMessageHandler +
        '}';
  }
}
