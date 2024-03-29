package assignment6.chatRoomApp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import assignment4.problem1.SentenceGenerator;
import java.util.Objects;

/**
 * The ProtocolHandler class represents the frame data handler and is implemented form the
 * ChatRoomProtocol interface
 */
public class ProtocolHandler implements ChatRoomProtocol {

  private final SentenceGenerator sentenceGenerator;
  private static final int BLANK_SPACE_CHAR = 32;
  private static final int START_STEP_QUERY_USER = 0;
  private static final String GRAMMAR_FILE = "Grammar" + File.separator + "poem_grammar.json";

  /**
   * Create a protocol handler providing a sentence generator to generate the ransom insult
   * message.
   */
  public ProtocolHandler() {
    this.sentenceGenerator = new SentenceGenerator(GRAMMAR_FILE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void writeConnectRequest(DataOutputStream dataOutputStream, MessageFormat messageFormat) {
    writeSenderRequest(CONNECT_MESSAGE, dataOutputStream, messageFormat);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MessageFormat readConnectRequest(DataInputStream dataInputStream) {
    try {
      dataInputStream.readChar();
      dataInputStream.readInt();
      dataInputStream.readChar();
      String sender = dataInputStream.readUTF();
      return (new MessageFormat(null, null, null, sender, null));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void writeConnectResponse(DataOutputStream dataOutputStream, MessageFormat messageFormat) {
    try {
      dataOutputStream.writeInt(CONNECT_RESPONSE);
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeBoolean(messageFormat.isSuccess());
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeInt(messageFormat.getMessage().length());
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeUTF(messageFormat.getMessage());
      dataOutputStream.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MessageFormat readConnectResponse(DataInputStream dataInputStream) {
    try {
      dataInputStream.readChar();
      Boolean isSuccess = dataInputStream.readBoolean();
      dataInputStream.readChar();
      dataInputStream.readInt();
      dataInputStream.readChar();
      String message = dataInputStream.readUTF();
      return (new MessageFormat(isSuccess, message, null, null, null));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void writeDisconnectRequest(DataOutputStream dataOutputStream,
      MessageFormat messageFormat) {
    writeSenderRequest(DISCONNECT_MESSAGE, dataOutputStream, messageFormat);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MessageFormat readDisconnectRequest(DataInputStream dataInputStream) {
    try {
      dataInputStream.readChar();
      dataInputStream.readInt();
      dataInputStream.readChar();
      return (new MessageFormat(null, null, null, dataInputStream.readUTF(), null));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void writeDisconnectResponse(DataOutputStream dataOutputStream,
      MessageFormat messageFormat) {
    writeConnectResponse(dataOutputStream, messageFormat);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void writeQueryUsersRequest(DataOutputStream dataOutputStream,
      MessageFormat messageFormat) {
    writeSenderRequest(QUERY_CONNECTED_USERS, dataOutputStream, messageFormat);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MessageFormat readQueryUsersRequest(DataInputStream dataInputStream) {
    try {
      dataInputStream.readChar();
      dataInputStream.readInt();
      dataInputStream.readChar();
      return (new MessageFormat(null, null, null, dataInputStream.readUTF(), null));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void writeQueryUsersResponse(DataOutputStream dataOutputStream,
      MessageFormat messageFormat) {
    try {
      List<String> clients = messageFormat.getClients();
      dataOutputStream.writeInt(QUERY_USER_RESPONSE);
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeInt(clients.size());

      for (String client : clients) {
        dataOutputStream.writeChar(BLANK_SPACE_CHAR);
        dataOutputStream.writeInt(client.length());
        dataOutputStream.writeChar(BLANK_SPACE_CHAR);
        dataOutputStream.writeUTF(client);
      }
      dataOutputStream.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MessageFormat readQueryUsersResponse(DataInputStream dataInputStream) {
    try {
      List<String> clients = new ArrayList<>();

      dataInputStream.readChar();
      int count = dataInputStream.readInt();
      for (int i = START_STEP_QUERY_USER; i < count; i++) {
        dataInputStream.readChar();
        dataInputStream.readInt();
        dataInputStream.readChar();
        clients.add(dataInputStream.readUTF());
      }
      return (new MessageFormat(null, null, clients, null, null));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void writeBroadcastMessage(DataOutputStream dataOutputStream,
      MessageFormat messageFormat) {
    try {
      dataOutputStream.writeInt(BROADCAST_MESSAGE);
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeInt(messageFormat.getSender().length());
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeUTF(messageFormat.getSender());
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeInt(messageFormat.getMessage().length());
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeUTF(messageFormat.getMessage());
      dataOutputStream.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MessageFormat readBroadcastMessage(DataInputStream dataInputStream) {
    try {
      dataInputStream.readChar();
      dataInputStream.readInt();
      dataInputStream.readChar();
      String sender = dataInputStream.readUTF();
      dataInputStream.readChar();
      dataInputStream.readInt();
      dataInputStream.readChar();
      String message = dataInputStream.readUTF();
      return (new MessageFormat(null, message, null, sender, null));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void writeDirectMessage(DataOutputStream dataOutputStream, MessageFormat messageFormat) {
    try {
      writeSenderRecipient(DIRECT_MESSAGE, dataOutputStream, messageFormat);
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeInt(messageFormat.getMessage().length());
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeUTF(messageFormat.getMessage());
      dataOutputStream.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MessageFormat readDirectMessage(DataInputStream dataInputStream) {
    try {
      dataInputStream.readChar();
      dataInputStream.readInt();
      dataInputStream.readChar();
      String sender = dataInputStream.readUTF();
      dataInputStream.readChar();
      dataInputStream.readInt();
      dataInputStream.readChar();
      String recipient = dataInputStream.readUTF();
      dataInputStream.readChar();
      dataInputStream.readInt();
      dataInputStream.readChar();
      return (new MessageFormat(null, dataInputStream.readUTF(), null, sender, recipient));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void writeFailedMessage(DataOutputStream dataOutputStream, MessageFormat messageFormat) {
    try {
      dataOutputStream.writeInt(FAILED_MESSAGE);
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeInt(messageFormat.getMessage().length());
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeUTF(messageFormat.getMessage());
      dataOutputStream.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MessageFormat readFailedMessage(DataInputStream dataInputStream) {
    try {
      dataInputStream.readChar();
      dataInputStream.readInt();
      dataInputStream.readChar();
      String message = dataInputStream.readUTF();
      return (new MessageFormat(null, message, null, null, null));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void writeInsultMessage(DataOutputStream dataOutputStream, MessageFormat messageFormat) {
    try {
      writeSenderRecipient(SEND_INSULT, dataOutputStream, messageFormat);
      dataOutputStream.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MessageFormat readInsultMessage(DataInputStream dataInputStream) {
    try {
      dataInputStream.readChar();
      dataInputStream.readInt();
      dataInputStream.readChar();
      String sender = dataInputStream.readUTF();
      dataInputStream.readChar();
      dataInputStream.readInt();
      dataInputStream.readChar();
      String message = this.sentenceGenerator.generate();
      return (new MessageFormat(null, message, null, sender, dataInputStream.readUTF()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Write the frame data containing sender information to the data output stream based on the
   * message type
   *
   * @param messageIdentifier the message type
   * @param dataOutputStream  the data output stream
   * @param messageFormat     the frame data containing sender information
   */
  private void writeSenderRequest(int messageIdentifier, DataOutputStream dataOutputStream,
      MessageFormat messageFormat) {
    try {
      dataOutputStream.writeInt(messageIdentifier);
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeInt(messageFormat.getSender().length());
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeUTF(messageFormat.getSender());
      dataOutputStream.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Write the frame data containing sender and recipient information to the data output stream
   * based on the message type
   *
   * @param messageIdentifier the message type
   * @param dataOutputStream  the data output stream
   * @param messageFormat     the frame data containing sender and recipient information
   */
  private void writeSenderRecipient(int messageIdentifier, DataOutputStream dataOutputStream,
      MessageFormat messageFormat) {
    try {
      dataOutputStream.writeInt(messageIdentifier);
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeInt(messageFormat.getSender().length());
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeUTF(messageFormat.getSender());
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeInt(messageFormat.getRecipient().length());
      dataOutputStream.writeChar(BLANK_SPACE_CHAR);
      dataOutputStream.writeUTF(messageFormat.getRecipient());
      dataOutputStream.flush();
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
    ProtocolHandler that = (ProtocolHandler) o;
    return sentenceGenerator.equals(that.sentenceGenerator);
  }

  /**
   * Returns a hash code value for the object. *
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(sentenceGenerator);
  }

  /**
   * Returns a string representation of the object. In general, the toString method returns a string
   * that "textually represents" this object. *
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return "ProtocolHandler{" +
        "sentenceGenerator=" + sentenceGenerator +
        '}';
  }
}
