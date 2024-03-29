package assignment6.chatRoomApp;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * The interface is designed to represent the chat room protocol providing the read and write
 * functions for client and server to process the message frame data.
 */
public interface ChatRoomProtocol {

  /**
   * send connect message to the server
   */
  int CONNECT_MESSAGE = 19;
  /**
   * send connect response to the client
   */
  int CONNECT_RESPONSE = 20;
  /**
   * send disconnect message to the server
   */
  int DISCONNECT_MESSAGE = 21;
  /**
   * send query request to the server
   */
  int QUERY_CONNECTED_USERS = 22;
  /**
   * send query request to the client
   */
  int QUERY_USER_RESPONSE = 23;
  /**
   * send broadcast message request to the server
   */
  int BROADCAST_MESSAGE = 24;
  /**
   * send direct message request to the server
   */
  int DIRECT_MESSAGE = 25;
  /**
   * send fail message request to the server
   */
  int FAILED_MESSAGE = 26;
  /**
   * send insult message request to the server
   */
  int SEND_INSULT = 27;

  /**
   * Write the frame data of connect request into the data output stream
   *
   * @param dataOutputStream the data output stream
   * @param messageFormat    the provided message frame of connect request
   */
  void writeConnectRequest(DataOutputStream dataOutputStream, MessageFormat messageFormat);

  /**
   * Read the information from connect request from the data input stream and store the information
   * into the frame data of connect request
   *
   * @param dataInputStream the data input stream
   * @return the frame data of connect request
   */
  MessageFormat readConnectRequest(DataInputStream dataInputStream);

  /**
   * Write the frame data of connect response into the data output stream
   *
   * @param dataOutputStream the data output stream
   * @param messageFormat    the provided message frame of connect response
   */
  void writeConnectResponse(DataOutputStream dataOutputStream, MessageFormat messageFormat);

  /**
   * Read the information from connect response from the data input stream and store the information
   * into the frame data of connect response
   *
   * @param dataInputStream the data input stream
   * @return the frame data of connect response
   */
  MessageFormat readConnectResponse(DataInputStream dataInputStream);

  /**
   * Write the frame data of disconnect request into the data output stream
   *
   * @param dataOutputStream the data output stream
   * @param messageFormat    the provided message frame of disconnect request
   */
  void writeDisconnectRequest(DataOutputStream dataOutputStream, MessageFormat messageFormat);

  /**
   * Read the information from disconnect request from the data input stream and store the
   * information into the frame data of disconnect request
   *
   * @param dataInputStream the data input stream
   * @return the frame data of disconnect request
   */
  MessageFormat readDisconnectRequest(DataInputStream dataInputStream);

  /**
   * Write the frame data of disconnect response into the data output stream
   *
   * @param dataOutputStream the data output stream
   * @param messageFormat    the provided message frame of disconnect response
   */
  void writeDisconnectResponse(DataOutputStream dataOutputStream, MessageFormat messageFormat);

  /**
   * Write the frame data of query user request into the data output stream
   *
   * @param dataOutputStream the data output stream
   * @param messageFormat    the provided message frame of query user request
   */
  void writeQueryUsersRequest(DataOutputStream dataOutputStream, MessageFormat messageFormat);

  /**
   * Read the information from query user request from the data input stream and store the
   * information into the frame data of query user request
   *
   * @param dataInputStream the data input stream
   * @return the frame data of query user request
   */
  MessageFormat readQueryUsersRequest(DataInputStream dataInputStream);

  /**
   * Write the frame data of query user response into the data output stream
   *
   * @param dataOutputStream the data output stream
   * @param messageFormat    the provided message frame of query user response
   */
  void writeQueryUsersResponse(DataOutputStream dataOutputStream, MessageFormat messageFormat);

  /**
   * Read the information from query user response from the data input stream and store the
   * information into the frame data of query user response
   *
   * @param dataInputStream the data input stream
   * @return the frame data of query user response
   */
  MessageFormat readQueryUsersResponse(DataInputStream dataInputStream);

  /**
   * Write the frame data of broadcast message into the data output stream
   *
   * @param dataOutputStream the data output stream
   * @param messageFormat    the provided message frame of broadcast message
   */
  void writeBroadcastMessage(DataOutputStream dataOutputStream, MessageFormat messageFormat);

  /**
   * Read the information from broadcast message from the data input stream and store the
   * information into the frame data of broadcast message
   *
   * @param dataInputStream the data input stream
   * @return the frame data of broadcast message
   */
  MessageFormat readBroadcastMessage(DataInputStream dataInputStream);

  /**
   * Write the frame data of direct message into the data output stream
   *
   * @param dataOutputStream the data output stream
   * @param messageFormat    the provided message frame of direct message
   */
  void writeDirectMessage(DataOutputStream dataOutputStream, MessageFormat messageFormat);

  /**
   * Read the information from direct message from the data input stream and store the information
   * into the frame data of direct message
   *
   * @param dataInputStream the data input stream
   * @return the frame data of direct message
   */
  MessageFormat readDirectMessage(DataInputStream dataInputStream);

  /**
   * Write the frame data of failed message into the data output stream
   *
   * @param dataOutputStream the data output stream
   * @param messageFormat    the provided message frame of failed message
   */
  void writeFailedMessage(DataOutputStream dataOutputStream, MessageFormat messageFormat);

  /**
   * Read the information from failed message from the data input stream and store the information
   * into the frame data of failed message
   *
   * @param dataInputStream the data input stream
   * @return the frame data of failed message
   */
  MessageFormat readFailedMessage(DataInputStream dataInputStream);

  /**
   * Write the frame data of insult message into the data output stream
   *
   * @param dataOutputStream the data output stream
   * @param messageFormat    the provided message frame of insult message
   */
  void writeInsultMessage(DataOutputStream dataOutputStream, MessageFormat messageFormat);

  /**
   * Read the information from insult message from the data input stream and store the information
   * into the frame data of insult message
   *
   * @param dataInputStream the data input stream
   * @return the frame data of insult message
   */
  MessageFormat readInsultMessage(DataInputStream dataInputStream);
}
