package assignment6.chatRoomApp;

import java.io.DataInputStream;
import java.util.List;
import java.util.Objects;

import static assignment6.chatRoomApp.ChatRoomProtocol.*;

/**
 * Represents the client message handler class which helps to read the message from server side and
 * print it to console
 */
public class ClientMessageHandler {

  private final ProtocolHandler protocolHandler;
  /**
   * Message to display the count of active clients in chat room
   */
  public static final String CLIENT_COUNT_MESSAGE = "Number of other active clients in Chat room : %s";
  /**
   * Message to display the client name
   */
  public static final String CLIENT_NAME_MESSAGE = "Client name : %s";
  /**
   * Message to display the broadcast message
   */
  public static final String BROADCAST_MESSAGE_VALUE = "Broadcast message from %s : %s";
  /**
   * Message to display the direct message
   */
  public static final String DIRECT_INSULT_MESSAGE = "%s -> %s: %s";

  /**
   * Creates a new instance of client message handler class given the protocol handler instance
   *
   * @param protocolHandler class which helps to translate the server-client communication in
   *                        accordance with the protocol used
   */
  public ClientMessageHandler(ProtocolHandler protocolHandler) {
    this.protocolHandler = protocolHandler;
  }

  /**
   * Method which helps to read the message from server side and print it ot console
   *
   * @param messageIdentifier message identifier
   * @param dataInputStream   input stream
   * @return true or false to indicate if connection is alive
   */
  public boolean process(int messageIdentifier, DataInputStream dataInputStream) {
    switch (messageIdentifier) {
      case CONNECT_RESPONSE -> {
        MessageFormat messageFormat = protocolHandler.readConnectResponse(dataInputStream);
        logMessage(messageFormat.getMessage());
        return (!messageFormat.isSuccess());//helps to signal ServerHandler if a disconnect request has come
      }
      case QUERY_USER_RESPONSE -> {
        MessageFormat messageFormat = protocolHandler.readQueryUsersResponse(dataInputStream);
        List<String> clients = messageFormat.getClients();
        logMessage(String.format(CLIENT_COUNT_MESSAGE, clients.size()));
        for (String client : clients) {
          logMessage(String.format(CLIENT_NAME_MESSAGE, client));
        }
        return true;//return if connection is alive
      }
      case BROADCAST_MESSAGE -> {
        MessageFormat messageFormat = protocolHandler.readBroadcastMessage(dataInputStream);
        logMessage(String.format(BROADCAST_MESSAGE_VALUE, messageFormat.getSender(),
            messageFormat.getMessage()));
        return true;//return if connection is alive
      }
      case DIRECT_MESSAGE, SEND_INSULT -> {
        MessageFormat messageFormat = protocolHandler.readDirectMessage(dataInputStream);
        logMessage(String.format(DIRECT_INSULT_MESSAGE, messageFormat.getSender(),
            messageFormat.getRecipient(), messageFormat.getMessage()));
        return true;//return if connection is alive
      }
      case FAILED_MESSAGE -> {
        MessageFormat messageFormat = protocolHandler.readFailedMessage(dataInputStream);
        logMessage(messageFormat.getMessage());
        return true;//return if connection is alive
      }
    }
    return true;
  }

  /**
   * This method helps to print the given message to console
   *
   * @param message message
   */
  private void logMessage(String message) {
    System.out.println(message);
  }

  /**
   * This method is used to compare two instances of client message handler class
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
    ClientMessageHandler that = (ClientMessageHandler) o;
    return Objects.equals(protocolHandler, that.protocolHandler);
  }

  /**
   * This method is used to generate the hash code of client message handler class
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(protocolHandler);
  }

  /**
   * This method is used to generate the string representation of client message handler class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "ClientMessageHandler{" +
        "protocolHandler=" + protocolHandler +
        '}';
  }
}
