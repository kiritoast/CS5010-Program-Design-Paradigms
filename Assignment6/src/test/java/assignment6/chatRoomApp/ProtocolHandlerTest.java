package assignment6.chatRoomApp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class ProtocolHandlerTest {

  private ByteArrayOutputStream outContent;

  private final PrintStream originalOut = System.out;
  private ClientMessageHandler clientMessageHandler;
  private ProtocolHandler protocolHandler;
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private Socket acceptedSocket;
  private DataOutputStream serverOutput;
  private DataInputStream serverInput;
  private DataInputStream clientMessageInput;
  private DataOutputStream clientUiOutput;

  @BeforeEach
  void setUp() throws IOException {
    this.protocolHandler = new ProtocolHandler();
    this.clientMessageHandler = new ClientMessageHandler(protocolHandler);

    serverSocket = new ServerSocket(1700);
    clientSocket = new Socket("localhost",1700);
    acceptedSocket = serverSocket.accept();
    serverOutput = new DataOutputStream(acceptedSocket.getOutputStream());
    serverInput = new DataInputStream(acceptedSocket.getInputStream());
    clientMessageInput = new DataInputStream(clientSocket.getInputStream());
    clientUiOutput = new DataOutputStream(clientSocket.getOutputStream());
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
    clientUiOutput.close();
    clientMessageInput.close();
    acceptedSocket.close();
    clientSocket.close();
    serverSocket.close();
  }

  @Test
  void testEquals() {
    ProtocolHandler protocolHandlerTest = new ProtocolHandler();
    Assertions.assertEquals(protocolHandlerTest, this.protocolHandler);

    Assertions.assertEquals(this.protocolHandler, this.protocolHandler);
    Assertions.assertFalse(this.protocolHandler.equals(null));
    Assertions.assertFalse(this.protocolHandler.equals(1));
  }

  @Test
  void testHashCode() {
    ProtocolHandler protocolHandlerTest = new ProtocolHandler();
    Assertions.assertEquals(protocolHandlerTest.hashCode(), this.protocolHandler.hashCode());
  }

  @Test
  void testToString() {
    ProtocolHandler protocolHandlerTest = new ProtocolHandler();
    Assertions.assertEquals(protocolHandlerTest.toString(), this.protocolHandler.toString());
  }

  @Test
  void testReadConnectRequest() throws IOException {
    String clientName = "c1";
    MessageFormat messageFormat = new MessageFormat(null, null, null, clientName, null);
    protocolHandler.writeConnectRequest(clientUiOutput, messageFormat);
    serverInput.readInt();
    MessageFormat connectReqMsg = protocolHandler.readConnectRequest(serverInput);
    Assertions.assertEquals(clientName, connectReqMsg.getSender());
  }

  @Test
  void testWriteConnectResponse() throws IOException {
    protocolHandler.writeConnectResponse(serverOutput, new MessageFormat(true, "connected", null, "c1", null));
    int messageIdentifier = clientMessageInput.readInt();
    clientMessageHandler.process(messageIdentifier, clientMessageInput);
    Assertions.assertEquals("connected", outContent.toString().replace("\n", "").replace("\r", ""));
  }

  @Test
  void testReadDisconnectRequest() throws IOException {
    String clientName = "c1";
    MessageFormat messageFormat = new MessageFormat(null, null, null, clientName, null);
    protocolHandler.writeDisconnectRequest(clientUiOutput, messageFormat);
    serverInput.readInt();
    MessageFormat disconnectReqMsg = protocolHandler.readDisconnectRequest(serverInput);
    Assertions.assertEquals(clientName, disconnectReqMsg.getSender());
  }

  @Test
  void testWriteDisconnectResponse() throws IOException {
    protocolHandler.writeDisconnectResponse(serverOutput, new MessageFormat(true, "disconnected", null, "c1", null));
    int messageIdentifier = clientMessageInput.readInt();
    clientMessageHandler.process(messageIdentifier, clientMessageInput);
    Assertions.assertEquals("disconnected", outContent.toString().replace("\n", "").replace("\r", ""));
  }

  @Test
  void testReadQueryUsersRequest() throws IOException {
    String clientName = "c1";
    MessageFormat messageFormat = new MessageFormat(null, null, null, clientName, null);
    protocolHandler.writeQueryUsersRequest(clientUiOutput, messageFormat);
    serverInput.readInt();
    MessageFormat queryUsersReqMsg = protocolHandler.readQueryUsersRequest(serverInput);
    Assertions.assertEquals(clientName, queryUsersReqMsg.getSender());
  }

  @Test
  void testWriteQueryUsersResponse() throws IOException {
    List<String> clients = new LinkedList<>();
    clients.add("c1");
    protocolHandler.writeQueryUsersResponse(serverOutput, new MessageFormat(true, null,  clients, null, null));
    int messageIdentifier = clientMessageInput.readInt();
    clientMessageHandler.process(messageIdentifier, clientMessageInput);
    Assertions.assertEquals(String.format(ClientMessageHandler.CLIENT_COUNT_MESSAGE, clients.size()) +
        String.format(ClientMessageHandler.CLIENT_NAME_MESSAGE, clients.get(0)), outContent.toString().replace("\n", "").replace("\r", ""));
  }

  @Test
  void testReadBroadcastMessage() throws IOException {
    String clientName = "c1";
    String message = "all";
    MessageFormat messageFormat = new MessageFormat(null, message, null, clientName, null);
    protocolHandler.writeBroadcastMessage(clientUiOutput, messageFormat);
    serverInput.readInt();
    MessageFormat broadcastMsg = protocolHandler.readBroadcastMessage(serverInput);
    Assertions.assertEquals(clientName, broadcastMsg.getSender());
    Assertions.assertEquals(message, broadcastMsg.getMessage());
  }

  @Test
  void testWriteBroadcastMessage() throws IOException {
    String clientName = "c1";
    String message = "all";
    MessageFormat messageFormat = new MessageFormat(null, message, null, clientName, null);
    protocolHandler.writeBroadcastMessage(serverOutput,messageFormat);
    int messageIdentifier = clientMessageInput.readInt();
    clientMessageHandler.process(messageIdentifier, clientMessageInput);
    Assertions.assertEquals(String.format(ClientMessageHandler.BROADCAST_MESSAGE_VALUE,clientName,message), outContent.toString().replace("\n", "").replace("\r", ""));
  }

  @Test
  void testReadDirectMessage() throws IOException {
    String clientName = "c1";
    String recipientName = "c2";
    String message = "direct";
    MessageFormat messageFormat = new MessageFormat(null, message, null, clientName, recipientName);
    protocolHandler.writeDirectMessage(clientUiOutput, messageFormat);
    serverInput.readInt();
    MessageFormat directMsg = protocolHandler.readDirectMessage(serverInput);
    Assertions.assertEquals(clientName, directMsg.getSender());
    Assertions.assertEquals(recipientName, directMsg.getRecipient());
    Assertions.assertEquals(message, directMsg.getMessage());
  }

  @Test
  void testReadInsultMessage() throws IOException {
    String clientName = "c1";
    String recipientName = "c2";
    MessageFormat messageFormat = new MessageFormat(null, null, null, clientName, recipientName);
    protocolHandler.writeInsultMessage(clientUiOutput, messageFormat);
    serverInput.readInt();
    MessageFormat insultMsg = protocolHandler.readInsultMessage(serverInput);
    Assertions.assertEquals(clientName, insultMsg.getSender());
    Assertions.assertEquals(recipientName, insultMsg.getRecipient());
  }

  @Test
  void testWriteDirectMessage() throws IOException {
    String clientName = "c1";
    String recipientName = "c2";
    String message = "all";
    MessageFormat messageFormat = new MessageFormat(null, message, null, clientName, recipientName);
    protocolHandler.writeDirectMessage(serverOutput,messageFormat);
    int messageIdentifier = clientMessageInput.readInt();
    clientMessageHandler.process(messageIdentifier, clientMessageInput);
    Assertions.assertEquals(String.format(ClientMessageHandler.DIRECT_INSULT_MESSAGE,clientName,recipientName,message), outContent.toString().replace("\n", "").replace("\r", ""));
  }
}