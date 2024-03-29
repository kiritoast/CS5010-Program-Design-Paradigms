package assignment6.chatRoomApp;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents a runnable handler to process the message sent from server
 */
public class ServerHandler implements Runnable {

  private final ClientMessageHandler clientMessageHandler;
  private final DataInputStream dataInputStream;

  /**
   * Creates a server handler with the information of client message handler and the client input
   * stream
   *
   * @param clientMessageHandler the client message handler
   * @param dataInputStream      the client input stream
   */
  public ServerHandler(ClientMessageHandler clientMessageHandler, DataInputStream dataInputStream) {
    this.clientMessageHandler = clientMessageHandler;
    this.dataInputStream = dataInputStream;
  }

  @Override
  public void run() {
    try {
      boolean isConnected;
      do {
        int messageIdentifier = dataInputStream.readInt();
        isConnected = clientMessageHandler.process(messageIdentifier, dataInputStream);
      } while (isConnected);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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
    ServerHandler that = (ServerHandler) o;
    return clientMessageHandler.equals(that.clientMessageHandler) && dataInputStream.equals(
        that.dataInputStream);
  }

  /**
   * Returns a hash code value for the object. *
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(clientMessageHandler, dataInputStream);
  }

  /**
   * Returns a string representation of the object. In general, the toString method returns a string
   * that "textually represents" this object. *
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return "ServerHandler{" +
        "clientMessageHandler=" + clientMessageHandler +
        ", dataInputStream=" + dataInputStream +
        '}';
  }
}
