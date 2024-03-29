package assignment6.chatRoomApp;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class ClientMessageHandlerTest {

  private ByteArrayOutputStream outContent;
  private final PrintStream originalOut = System.out;
  private ClientMessageHandler clientMessageHandler;
  private ProtocolHandler protocolHandler;
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private Socket acceptedSocket;
  private DataOutputStream dataOutputStream;
  private DataInputStream dataInputStream;

  @BeforeAll
  void setUp() throws IOException {
    protocolHandler = new ProtocolHandler();
    clientMessageHandler = new ClientMessageHandler(protocolHandler);

    serverSocket = new ServerSocket(4600);
    clientSocket = new Socket("localhost",4600);
    acceptedSocket = serverSocket.accept();
    dataOutputStream = new DataOutputStream(acceptedSocket.getOutputStream());
    dataInputStream = new DataInputStream(clientSocket.getInputStream());
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

  @AfterAll
  void close()  throws IOException{
    dataOutputStream.close();
    dataInputStream.close();
    acceptedSocket.close();
    clientSocket.close();
    serverSocket.close();
  }

  @Test
  void processConnectResponse() throws IOException {
    String message = "Connection is successful.";
    protocolHandler.writeConnectResponse(dataOutputStream,new MessageFormat(true,message,null,null,null));
    int messageIdentifier = dataInputStream.readInt();
    clientMessageHandler.process(messageIdentifier, dataInputStream);
    Assertions.assertEquals(message, outContent.toString().replace("\n", "").replace("\r",""));
  }

  @Test
  void processQueryResponse() throws IOException {
    List<String> clients = Arrays.asList("Bob");
    protocolHandler.writeQueryUsersResponse(dataOutputStream,new MessageFormat(null,null, clients,null,null));
    int messageIdentifier = dataInputStream.readInt();
    clientMessageHandler.process(messageIdentifier, dataInputStream);
    Assertions.assertEquals(String.format(ClientMessageHandler.CLIENT_COUNT_MESSAGE, clients.size()) +
        String.format(ClientMessageHandler.CLIENT_NAME_MESSAGE, clients.get(0)), outContent.toString().replace("\n", "").replace("\r",""));
  }

  @Test
  void processBroadcastMessage() throws IOException {
    String message = "This is a broadcast message.";
    protocolHandler.writeBroadcastMessage(dataOutputStream,new MessageFormat(true,message,null,"Aby",null));
    int messageIdentifier = dataInputStream.readInt();
    clientMessageHandler.process(messageIdentifier, dataInputStream);
    Assertions.assertEquals(String.format(ClientMessageHandler.BROADCAST_MESSAGE_VALUE,"Aby",message), outContent.toString().replace("\n", "").replace("\r",""));
  }

  @Test
  void processDirectMessage() throws IOException {
    String message = "This is a direct message.";
    protocolHandler.writeDirectMessage(dataOutputStream,new MessageFormat(true,message,null,"Aby","Tom"));
    int messageIdentifier = dataInputStream.readInt();
    clientMessageHandler.process(messageIdentifier, dataInputStream);
    Assertions.assertEquals(String.format(ClientMessageHandler.DIRECT_INSULT_MESSAGE,"Aby","Tom",message), outContent.toString().replace("\n", "").replace("\r",""));
  }

  @Test
  void processFailedMessage() throws IOException {
    String message = "This is a failed message.";
    protocolHandler.writeFailedMessage(dataOutputStream,new MessageFormat(true,message,null,null,null));
    int messageIdentifier = dataInputStream.readInt();
    clientMessageHandler.process(messageIdentifier, dataInputStream);
    Assertions.assertEquals(message, outContent.toString().replace("\n", "").replace("\r",""));
  }

  @Test
  void testEquals() {
    Assertions.assertTrue(clientMessageHandler.equals(clientMessageHandler));
  }

  @Test
  void testEqualsNull() {
    Assertions.assertFalse(clientMessageHandler.equals(null));
  }

  @Test
  void testEqualsDiff() {
    Assertions.assertFalse(clientMessageHandler.equals("null"));
  }
  @Test
  void testEqualsDiffArg() {
    Assertions.assertTrue(
        clientMessageHandler.equals(new ClientMessageHandler(new ProtocolHandler())));
  }

  @Test
  void testHashCode() {
    Assertions.assertNotEquals(1, clientMessageHandler.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertNotEquals("test", clientMessageHandler.toString());
  }
}