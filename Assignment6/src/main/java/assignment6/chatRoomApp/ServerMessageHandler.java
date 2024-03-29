package assignment6.chatRoomApp;

import static assignment6.chatRoomApp.ChatRoomProtocol.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents the message handler in server side containing the protocol handler and the map of
 * current active clients.
 */
public class ServerMessageHandler {

  private final ProtocolHandler protocolHandler;
  private final Map<String, DataOutputStream> activeClientMap;
  private static final String NO_SEAT_MSG = "There is no available seat in this chat room.";
  private static final String DUPLICATE_USERNAME_MSG = "Duplicate username.";
  private static final String CONNECTED_RETURN_MSG = "There are %s other connected clients.";
  private static final String DISCONNECTED_RETURN_MSG = "You are no longer connected.";
  private static final String NONE_RECIPIENT_FAIL_MSG = "Recipient not found.";
  private static final String SENDER_RECIPIENT_SAME_FAIL_MSG = "Cannot send message to yourself.";
  private static final String NO_OTHERS_FAIL_MSG = "Failed to send message, because there are no other clients.";
  private static final int VALID_MEMBER_NUM_TO_SEND = 1;
  private static final int VALID_MAX_MEMBER = 10;
  private static final String BROADCAST_FLAG = "broadcast";
  private static final String DIRECT_FLAG = "direct";

  /**
   * Create a server message handler, and initialize the protocol handler and the map of current
   * active clients
   */
  public ServerMessageHandler() {
    this.protocolHandler = new ProtocolHandler();
    this.activeClientMap = new HashMap<>();
  }

  /**
   * Process the message frame data from the client side based on the message type. After
   * processing, return the result message frame data to specified clients
   *
   * @param messageIdentifier the message type
   * @param dataInputStream   the data input stream
   * @param dataOutputStream  the data output stream
   * @return true if the connection is alive
   */
  public boolean process(int messageIdentifier, DataInputStream dataInputStream,
      DataOutputStream dataOutputStream) {
    switch (messageIdentifier) {
      case CONNECT_MESSAGE -> {
        MessageFormat messageFormat = protocolHandler.readConnectRequest(dataInputStream);
        validateClientConnection(messageFormat, dataOutputStream);
        protocolHandler.writeConnectResponse(dataOutputStream, messageFormat);
        return messageFormat.isSuccess();//return if connection is successful
      }
      case DISCONNECT_MESSAGE -> {
        MessageFormat messageFormat = protocolHandler.readDisconnectRequest(dataInputStream);
        removeClientConnection(messageFormat);
        protocolHandler.writeDisconnectResponse(dataOutputStream, messageFormat);
        return !messageFormat.isSuccess();//return if disconnecting
      }
      case QUERY_CONNECTED_USERS -> {
        MessageFormat messageFormat = protocolHandler.readQueryUsersRequest(dataInputStream);
        populateActiveClients(messageFormat);
        protocolHandler.writeQueryUsersResponse(dataOutputStream, messageFormat);
        return true;//return if connection is alive
      }
      case BROADCAST_MESSAGE -> {
        MessageFormat messageFormat = protocolHandler.readBroadcastMessage(dataInputStream);
        return sendMessageToAllUsers(messageFormat, BROADCAST_FLAG);
      }
      case DIRECT_MESSAGE -> {
        MessageFormat messageFormat = protocolHandler.readDirectMessage(dataInputStream);
        sendMessageToSpecificClient(messageFormat);
        return true;//return if connection is alive
      }
      case SEND_INSULT -> {
        MessageFormat messageFormat = protocolHandler.readInsultMessage(dataInputStream);
        return sendMessageToAllUsers(messageFormat, DIRECT_FLAG);
      }
    }
    return true;
  }

  /**
   * This method helps to remove the client related information from server side, if client
   * terminates without properly communicating with server
   *
   * @param dataOutputStream data output stream
   */
  public void removeClientInformation(DataOutputStream dataOutputStream) {
    Optional<Entry<String, DataOutputStream>> clientEntry = activeClientMap.entrySet().stream()
        .filter(entry -> entry.getValue().equals(dataOutputStream)).findFirst();
    clientEntry.ifPresent(clientInfo -> activeClientMap.remove(
        clientInfo.getKey()));
  }

  /**
   * Get the map of current active clients
   *
   * @return the map of current active clients
   */
  public Map<String, DataOutputStream> getActiveClientMap() {
    return activeClientMap;
  }

  /**
   * Send the message frame data to the data output stream of specific clients. If failed, send a
   * fail message instead.
   *
   * @param messageFormat the message frame data
   */
  private void sendMessageToSpecificClient(MessageFormat messageFormat) {
    String sender = messageFormat.getSender();
    String recipient = messageFormat.getRecipient();
    DataOutputStream senderOutputStream = activeClientMap.get(sender);
    DataOutputStream otherClientOutputStream = activeClientMap.get(recipient);
    if (recipient == null || otherClientOutputStream == null) {
      MessageFormat failMessage = new MessageFormat(null, NONE_RECIPIENT_FAIL_MSG, null, null,
          null);
      protocolHandler.writeFailedMessage(senderOutputStream, failMessage);
    } else if (recipient.equals(sender)) {
      MessageFormat failMessage = new MessageFormat(null, SENDER_RECIPIENT_SAME_FAIL_MSG, null,
          null, null);
      protocolHandler.writeFailedMessage(senderOutputStream, failMessage);
    } else {
      protocolHandler.writeDirectMessage(otherClientOutputStream, messageFormat);
    }
  }

  /**
   * Send the message frame data to the data output stream of all other clients. If failed, send a
   * fail message instead.
   *
   * @param messageFormat the message frame data
   * @param flag          the indicator to determine to send a broadcast message or a direct
   *                      message
   * @return true if the connection is alive; otherwise, false.
   */
  private boolean sendMessageToAllUsers(MessageFormat messageFormat, String flag) {
    String sender = messageFormat.getSender();
    String recipient = messageFormat.getRecipient();
    DataOutputStream senderOutputStream = activeClientMap.get(sender);
    DataOutputStream otherClientOutputStream = activeClientMap.get(recipient);
    if (BROADCAST_FLAG.equals(flag)) {
      if (activeClientMap.keySet().size() == VALID_MEMBER_NUM_TO_SEND
          && activeClientMap.containsKey(messageFormat.getSender())) {
        MessageFormat failMessage = new MessageFormat(null, NO_OTHERS_FAIL_MSG, null, null,
            null);
        protocolHandler.writeFailedMessage(senderOutputStream, failMessage);
        return true;
      }
    } else if (DIRECT_FLAG.equals(flag)) {
      if (recipient == null || otherClientOutputStream == null) {
        MessageFormat failMessage = new MessageFormat(null, NONE_RECIPIENT_FAIL_MSG, null, null,
            null);
        protocolHandler.writeFailedMessage(senderOutputStream, failMessage);
        return true;
      } else if (recipient.equals(sender)) {
        MessageFormat failMessage = new MessageFormat(null, SENDER_RECIPIENT_SAME_FAIL_MSG, null,
            null, null);
        protocolHandler.writeFailedMessage(senderOutputStream, failMessage);
        return true;
      }
    }
    for (Map.Entry<String, DataOutputStream> otherClientEntry : activeClientMap.entrySet()) {
      if (!otherClientEntry.getKey().equals(messageFormat.getSender())) {
        DataOutputStream recipientClientOutputStream = otherClientEntry.getValue();
        if (flag != null) {
          if (flag.equals(BROADCAST_FLAG)) {
            protocolHandler.writeBroadcastMessage(recipientClientOutputStream, messageFormat);
          } else if (flag.equals(DIRECT_FLAG)) {
            protocolHandler.writeDirectMessage(recipientClientOutputStream, messageFormat);
          }
        }
      }
    }
    return true;
  }

  /**
   * Validate whether a new client is connecting to the chat room
   *
   * @param messageFormat    the message frame data of connect request
   * @param dataOutputStream the new client's data output stream
   */
  private void validateClientConnection(MessageFormat messageFormat,
      DataOutputStream dataOutputStream) {
    boolean responseFlag = false;
    String message;

    if (activeClientMap.size() >= VALID_MAX_MEMBER) {
      message = NO_SEAT_MSG;
    } else if (activeClientMap.containsKey(messageFormat.getSender())) {
      message = DUPLICATE_USERNAME_MSG;
    } else {
      message = String.format(CONNECTED_RETURN_MSG, activeClientMap.size());
      activeClientMap.put(messageFormat.getSender(), dataOutputStream);
      responseFlag = true;
    }

    messageFormat.setSuccess(responseFlag);
    messageFormat.setMessage(message);
  }

  /**
   * Remove the sender of frame data from the map of active clients
   *
   * @param messageFormat the sender of frame data
   */
  private void removeClientConnection(MessageFormat messageFormat) {
    activeClientMap.remove(messageFormat.getSender());
    messageFormat.setSuccess(true);
    messageFormat.setMessage(DISCONNECTED_RETURN_MSG);
  }

  /**
   * Set the list of the current active clients except the sender into the message frame data
   *
   * @param messageFormat the input message frame data
   */
  private void populateActiveClients(MessageFormat messageFormat) {
    List<String> activeClients = activeClientMap.keySet().stream()
        .filter(client -> !client.equals(messageFormat.getSender()))
        .toList();
    messageFormat.setClients(activeClients);
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
    ServerMessageHandler that = (ServerMessageHandler) o;
    return protocolHandler.equals(that.protocolHandler) && activeClientMap.equals(
        that.activeClientMap);
  }

  /**
   * Returns a hash code value for the object. *
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(protocolHandler, activeClientMap);
  }

  /**
   * Returns a string representation of the object. In general, the toString method returns a string
   * that "textually represents" this object. *
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return "ServerMessageHandler{" +
        "protocolHandler=" + protocolHandler +
        ", activeClientMap=" + activeClientMap +
        '}';
  }
}
