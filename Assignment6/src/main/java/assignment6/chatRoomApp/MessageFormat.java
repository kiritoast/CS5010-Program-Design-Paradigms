package assignment6.chatRoomApp;

import java.util.List;
import java.util.Objects;

/**
 * Represents the message format class which helps to represent the server-client communication in a
 * meaningful way
 */
public class MessageFormat {

  private Boolean success;
  private String message;
  private List<String> clients;
  private String sender;
  private String recipient;

  /**
   * Creates a new instance of message format class given the required parameters
   *
   * @param success   boolean which represents the status of connect or disconnect response
   * @param message   message passed during the server-client communication
   * @param clients   set of active clients in chat room
   * @param sender    client which sends a request
   * @param recipient client which receives a response
   */
  public MessageFormat(Boolean success, String message,
      List<String> clients, String sender, String recipient) {
    this.success = success;
    this.message = message;
    this.clients = clients;
    this.sender = sender;
    this.recipient = recipient;
  }

  /**
   * Getter method to give the value of isSuccess field
   *
   * @return true or false
   */
  public Boolean isSuccess() {
    return success;
  }

  /**
   * Getter method to give the value of message
   *
   * @return message
   */
  public String getMessage() {
    return message;
  }

  /**
   * Getter method to give the value of clients
   *
   * @return list of clients
   */
  public List<String> getClients() {
    return clients;
  }

  /**
   * Getter method to give the value of sender
   *
   * @return sender name
   */
  public String getSender() {
    return sender;
  }

  /**
   * Getter method to give the value of recipient
   *
   * @return recipient name
   */
  public String getRecipient() {
    return recipient;
  }

  /**
   * Setter method to set the value of isSuccess field
   *
   * @param success value to be set
   */
  public void setSuccess(Boolean success) {
    this.success = success;
  }

  /**
   * Setter method to set the value of message field
   *
   * @param message message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Setter method to set the value of client list
   *
   * @param clients list of clients
   */
  public void setClients(List<String> clients) {
    this.clients = clients;
  }

  /**
   * Setter method to set the value of sender name
   *
   * @param sender sender name
   */
  public void setSender(String sender) {
    this.sender = sender;
  }

  /**
   * Setter method to set the value of recipient name
   *
   * @param recipient recipient name
   */
  public void setRecipient(String recipient) {
    this.recipient = recipient;
  }

  /**
   * This method is used to compare two instances of message format class
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
    MessageFormat that = (MessageFormat) o;
    return Objects.equals(success, that.success) && Objects.equals(message,
        that.message) && Objects.equals(clients, that.clients) && Objects.equals(
        sender, that.sender) && Objects.equals(recipient, that.recipient);
  }

  /**
   * This method is used to generate the hash code of message format class
   *
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(success, message, clients, sender, recipient);
  }

  /**
   * This method is used to generate the string representation of message format class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "MessageFormat{" +
        "success=" + success +
        ", message='" + message + '\'' +
        ", clients=" + clients +
        ", sender='" + sender + '\'' +
        ", recipient='" + recipient + '\'' +
        '}';
  }
}
