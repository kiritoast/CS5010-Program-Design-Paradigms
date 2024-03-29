package assignment6.chatRoomApp;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class ChatClientTest {
  private ChatClient chatClient;
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private Socket acceptedSocket;
  private ProtocolHandler protocolHandler;
  private DataOutputStream dataOutputStream;
  @BeforeEach
  void setUp() throws IOException {
    protocolHandler = new ProtocolHandler();

    serverSocket = new ServerSocket(4800);
    clientSocket = new Socket("localhost",4800);

    acceptedSocket = serverSocket.accept();
    chatClient = new ChatClient(clientSocket);

    dataOutputStream = new DataOutputStream(
        new BufferedOutputStream(acceptedSocket.getOutputStream()));
  }

  @AfterEach
  void close() throws IOException {
    dataOutputStream.close();
    acceptedSocket.close();
    serverSocket.close();
    if(!clientSocket.isClosed())//for test cases like equals, hashcode, to string
      clientSocket.close();
  }

  @Test
  void startDisconnect() {
    System.setIn(new ByteArrayInputStream("minu".getBytes()));
    protocolHandler.writeConnectResponse(dataOutputStream,new MessageFormat(false,"Connection could not be established",null,null,null));
    chatClient.start();
    Assertions.assertTrue(clientSocket.isClosed());
  }

  @Test
  void startConnect() {
    System.setIn(new ByteArrayInputStream("anu".getBytes()));
    protocolHandler.writeConnectResponse(dataOutputStream,new MessageFormat(true,"There are 0 other connected clients",null,null,null));
    System.setIn(new ByteArrayInputStream("logoff".getBytes()));
    protocolHandler.writeConnectResponse(dataOutputStream,new MessageFormat(true,"You are no longer connected",null,null,null));
    chatClient.start();
    Assertions.assertTrue(clientSocket.isClosed());
  }

  @Test
  void testEquals() {
    Assertions.assertTrue(chatClient.equals(chatClient));
  }

  @Test
  void testEqualsNull() {
    Assertions.assertFalse(chatClient.equals(null));
  }

  @Test
  void testEqualsDiff() {
    Assertions.assertFalse(chatClient.equals("null"));
  }
  @Test
  void testEqualsDiffArg() throws IOException {
    Assertions.assertFalse(
        chatClient.equals(new ChatClient(new Socket("localhost",5000))));
  }

  @Test
  void testHashCode() {
    Assertions.assertNotEquals(1, chatClient.hashCode());
  }
  @Test
  void testToString() {
    Assertions.assertNotEquals("test", chatClient.toString());
  }
}