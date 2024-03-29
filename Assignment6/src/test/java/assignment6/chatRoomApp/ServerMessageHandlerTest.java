package assignment6.chatRoomApp;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class ServerMessageHandlerTest {
  private ByteArrayOutputStream outContent;

  private final PrintStream originalOut = System.out;
  private ProtocolHandler protocolHandler;
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private Socket acceptedSocket;
  private DataOutputStream serverOutput;
  private DataInputStream serverInput;
  private DataInputStream clientInput;
  private DataOutputStream clientOutput;
  private ServerMessageHandler serverMessageHandler;

  @BeforeEach
  void setUp() throws IOException {
    this.protocolHandler = new ProtocolHandler();
    this.serverMessageHandler = new ServerMessageHandler();

    serverSocket = new ServerSocket(1500);
    clientSocket = new Socket("localhost",1500);
    acceptedSocket = serverSocket.accept();
    serverOutput = new DataOutputStream(acceptedSocket.getOutputStream());
    serverInput = new DataInputStream(acceptedSocket.getInputStream());
    clientInput = new DataInputStream(clientSocket.getInputStream());
    clientOutput = new DataOutputStream(clientSocket.getOutputStream());
  }

  @BeforeEach
  void setupEach(){
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @AfterEach
  void close()  throws IOException{
    serverInput.close();
    serverOutput.close();
    clientOutput.close();
    clientInput.close();
    acceptedSocket.close();
    clientSocket.close();
    serverSocket.close();
  }
  @Test
  void processConnectMessage() throws IOException {
    String clientName = "c1";
    MessageFormat messageFormat = new MessageFormat(null, null, null, clientName, null);
    protocolHandler.writeConnectRequest(clientOutput, messageFormat);
    int messageIdentifier = serverInput.readInt();
    Assertions.assertTrue(serverMessageHandler.process(messageIdentifier, serverInput, serverOutput));
  }

  @Test
  void processDisconnectMessage() throws IOException {
    String clientName = "c1";
    MessageFormat messageFormat = new MessageFormat(null, null, null, clientName, null);
    protocolHandler.writeDisconnectRequest(clientOutput, messageFormat);
    int messageIdentifier = serverInput.readInt();
    Assertions.assertFalse(serverMessageHandler.process(messageIdentifier, serverInput, serverOutput));
  }

  @Test
  void processQueryConnectedUsers() throws IOException {
    String client1 = "c1";
    String client2 = "c2";

    MessageFormat connectClient1 = new MessageFormat(null, null, null, client1, null);
    MessageFormat connectClient2 = new MessageFormat(null, null, null, client2, null);
    protocolHandler.writeConnectRequest(clientOutput, connectClient1);
    int messageIdentifierClientConnect1 = serverInput.readInt();
    serverMessageHandler.process(messageIdentifierClientConnect1, serverInput, serverOutput);
    protocolHandler.writeConnectRequest(clientOutput, connectClient2);
    int messageIdentifierClientConnect2 = serverInput.readInt();
    serverMessageHandler.process(messageIdentifierClientConnect2, serverInput, serverOutput);

    MessageFormat messageFormat = new MessageFormat(null, null, null, client1, null);
    protocolHandler.writeQueryUsersRequest(clientOutput, messageFormat);
    int messageIdentifier = serverInput.readInt();
    Assertions.assertTrue(serverMessageHandler.process(messageIdentifier, serverInput, serverOutput));
  }

  @Test
  void processBroadcastMessage() throws IOException {
    String client1 = "c1";
    String client2 = "c2";
    String message = "all";

    MessageFormat connectClient1 = new MessageFormat(null, null, null, client1, null);
    MessageFormat connectClient2 = new MessageFormat(null, null, null, client2, null);
    protocolHandler.writeConnectRequest(clientOutput, connectClient1);
    int messageIdentifierClientConnect1 = serverInput.readInt();
    serverMessageHandler.process(messageIdentifierClientConnect1, serverInput, serverOutput);
    protocolHandler.writeConnectRequest(clientOutput, connectClient2);
    int messageIdentifierClientConnect2 = serverInput.readInt();
    serverMessageHandler.process(messageIdentifierClientConnect2, serverInput, serverOutput);

    MessageFormat broadcastMessage = new MessageFormat(null, message, null, client1, client2);
    protocolHandler.writeBroadcastMessage(clientOutput,broadcastMessage);

    int messageIdentifier = serverInput.readInt();
    Assertions.assertTrue(serverMessageHandler.process(messageIdentifier, serverInput, serverOutput));
  }

  @Test
  void processBroadcastMessageNoOther() throws IOException {
    String client1 = "c1";
    String message = "all";

    MessageFormat connectClient1 = new MessageFormat(null, null, null, client1, null);
    protocolHandler.writeConnectRequest(clientOutput, connectClient1);
    int messageIdentifierClientConnect1 = serverInput.readInt();
    serverMessageHandler.process(messageIdentifierClientConnect1, serverInput, serverOutput);

    MessageFormat broadcastMessage = new MessageFormat(null, message, null, client1, client1);
    protocolHandler.writeBroadcastMessage(clientOutput,broadcastMessage);

    int messageIdentifier = serverInput.readInt();
    Assertions.assertTrue(serverMessageHandler.process(messageIdentifier, serverInput, serverOutput));
  }

  @Test
  void processDirectMessage() throws IOException {
    String client1 = "c1";
    String client2 = "c2";
    String message = "direct";

    MessageFormat connectClient1 = new MessageFormat(null, null, null, client1, null);
    MessageFormat connectClient2 = new MessageFormat(null, null, null, client2, null);
    protocolHandler.writeConnectRequest(clientOutput, connectClient1);
    int messageIdentifierClientConnect1 = serverInput.readInt();
    serverMessageHandler.process(messageIdentifierClientConnect1, serverInput, serverOutput);
    protocolHandler.writeConnectRequest(clientOutput, connectClient2);
    int messageIdentifierClientConnect2 = serverInput.readInt();
    serverMessageHandler.process(messageIdentifierClientConnect2, serverInput, serverOutput);

    MessageFormat directMessage = new MessageFormat(null, message, null, client1, client2);
    protocolHandler.writeDirectMessage(clientOutput,directMessage);

    int messageIdentifier = serverInput.readInt();
    Assertions.assertTrue(serverMessageHandler.process(messageIdentifier, serverInput, serverOutput));
  }

  @Test
  void processInsultMessage() throws IOException {
    String client1 = "c1";
    String client2 = "c2";

    MessageFormat connectClient1 = new MessageFormat(null, null, null, client1, null);
    MessageFormat connectClient2 = new MessageFormat(null, null, null, client2, null);
    protocolHandler.writeConnectRequest(clientOutput, connectClient1);
    int messageIdentifierClientConnect1 = serverInput.readInt();
    serverMessageHandler.process(messageIdentifierClientConnect1, serverInput, serverOutput);
    protocolHandler.writeConnectRequest(clientOutput, connectClient2);
    int messageIdentifierClientConnect2 = serverInput.readInt();
    serverMessageHandler.process(messageIdentifierClientConnect2, serverInput, serverOutput);

    MessageFormat directMessage = new MessageFormat(null, null, null, client1, client2);
    protocolHandler.writeInsultMessage(clientOutput,directMessage);

    int messageIdentifier = serverInput.readInt();
    Assertions.assertTrue(serverMessageHandler.process(messageIdentifier, serverInput, serverOutput));
  }

  @Test
  void processUnknownMsg() {
    Assertions.assertTrue(serverMessageHandler.process(1, serverInput, serverOutput));
  }

  @Test
  void removeClientInformation() throws IOException {
    String client1 = "c1";

    MessageFormat connectClient1 = new MessageFormat(null, null, null, client1, null);
    protocolHandler.writeConnectRequest(clientOutput, connectClient1);
    int messageIdentifierClientConnect1 = serverInput.readInt();
    serverMessageHandler.process(messageIdentifierClientConnect1, serverInput, serverOutput);
    serverMessageHandler.removeClientInformation(serverOutput);
    Assertions.assertEquals(serverMessageHandler.getActiveClientMap(), new HashMap<String, DataOutputStream>());

  }

  @Test
  void getActiveClientMap() throws IOException {
    String client1 = "c1";

    MessageFormat connectClient1 = new MessageFormat(null, null, null, client1, null);
    protocolHandler.writeConnectRequest(clientOutput, connectClient1);
    int messageIdentifierClientConnect1 = serverInput.readInt();
    serverMessageHandler.process(messageIdentifierClientConnect1, serverInput, serverOutput);
    Map<String, DataOutputStream> clientMap = new HashMap<>();
    clientMap.put("c1", clientOutput);
    Assertions.assertEquals(serverMessageHandler.getActiveClientMap().keySet(), clientMap.keySet());
  }

  @Test
  void testSendMessageToSpecificClient() throws IOException {
    String client1 = "c1";
    String client2 = "c2";
    String client3 = "c3";
    String message = "direct";

    MessageFormat connectClient1 = new MessageFormat(null, null, null, client1, null);
    MessageFormat connectClient2 = new MessageFormat(null, null, null, client2, null);
    protocolHandler.writeConnectRequest(clientOutput, connectClient1);
    int messageIdentifierClientConnect1 = serverInput.readInt();
    serverMessageHandler.process(messageIdentifierClientConnect1, serverInput, serverOutput);
    protocolHandler.writeConnectRequest(clientOutput, connectClient2);
    int messageIdentifierClientConnect2 = serverInput.readInt();
    serverMessageHandler.process(messageIdentifierClientConnect2, serverInput, serverOutput);

    MessageFormat directMessage1 = new MessageFormat(null, message, null, client1, client3);
    protocolHandler.writeDirectMessage(clientOutput,directMessage1);

    int messageIdentifier1 = serverInput.readInt();
    Assertions.assertTrue(serverMessageHandler.process(messageIdentifier1, serverInput, serverOutput));

    MessageFormat directMessage2 = new MessageFormat(null, message, null, client1, client1);
    protocolHandler.writeDirectMessage(clientOutput,directMessage2);

    int messageIdentifier2 = serverInput.readInt();
    Assertions.assertTrue(serverMessageHandler.process(messageIdentifier2, serverInput, serverOutput));
  }

  @Test
  void testNoSeat() throws IOException {
    for (int i = 0; i < 10; i++) {
      MessageFormat connectClient = new MessageFormat(null, null, null, "client" + i, null);
      protocolHandler.writeConnectRequest(clientOutput, connectClient);
      int messageIdentifierClientConnect = serverInput.readInt();
      serverMessageHandler.process(messageIdentifierClientConnect, serverInput, serverOutput);
    }

    MessageFormat connectClient = new MessageFormat(null, null, null, "client11", null);
    protocolHandler.writeConnectRequest(clientOutput, connectClient);
    int messageIdentifierClientConnect = serverInput.readInt();
    Assertions.assertFalse(serverMessageHandler.process(messageIdentifierClientConnect, serverInput, serverOutput));
  }

  @Test
  void testDuplicateClient() throws IOException {
    MessageFormat connectClient = new MessageFormat(null, null, null, "client1", null);
    protocolHandler.writeConnectRequest(clientOutput, connectClient);
    int messageIdentifierClientConnect = serverInput.readInt();
    serverMessageHandler.process(messageIdentifierClientConnect, serverInput, serverOutput);
    protocolHandler.writeConnectRequest(clientOutput, connectClient);
    messageIdentifierClientConnect = serverInput.readInt();
    Assertions.assertFalse(serverMessageHandler.process(messageIdentifierClientConnect, serverInput, serverOutput));
  }

  @Test
  void testEquals() {
      ServerMessageHandler serverMessageHandlerTest = new ServerMessageHandler();
      Assertions.assertEquals(serverMessageHandlerTest, this.serverMessageHandler);

      Assertions.assertEquals(this.serverMessageHandler, this.serverMessageHandler);
      Assertions.assertFalse(this.serverMessageHandler.equals(null));
      Assertions.assertFalse(this.serverMessageHandler.equals(1));
    }

  @Test
  void testHashCode() {
    ServerMessageHandler serverMessageHandlerTest = new ServerMessageHandler();
    Assertions.assertEquals(serverMessageHandlerTest, this.serverMessageHandler);

    Assertions.assertEquals(this.serverMessageHandler.hashCode(), this.serverMessageHandler.hashCode());
  }

  @Test
  void testToString() {
    ServerMessageHandler serverMessageHandlerTest = new ServerMessageHandler();
    Assertions.assertEquals(serverMessageHandlerTest, this.serverMessageHandler);

    Assertions.assertEquals(this.serverMessageHandler.toString(), this.serverMessageHandler.toString());
  }
}