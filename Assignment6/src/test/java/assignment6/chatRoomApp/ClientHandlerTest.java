package assignment6.chatRoomApp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class ClientHandlerTest {

  private  ClientHandler clientHandler;
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private Socket acceptedSocket;
  private DataOutputStream dataOutputStream;
  private ServerMessageHandler serverMessageHandler;
  private ProtocolHandler protocolHandler;
  private Thread clientHandlerThread;
  private static final int BLANK_SPACE_CHAR = 32;
  @BeforeAll
  void setUp() throws IOException {
    protocolHandler = new ProtocolHandler();
    serverMessageHandler = new ServerMessageHandler();

    serverSocket = new ServerSocket(4500);
    clientSocket = new Socket("localhost",4500);

    dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
    dataOutputStream.writeInt(ChatRoomProtocol.CONNECT_MESSAGE);
    dataOutputStream.writeChar(BLANK_SPACE_CHAR);
    dataOutputStream.writeInt(3);
    dataOutputStream.writeChar(BLANK_SPACE_CHAR);
    dataOutputStream.writeUTF("Amy");

    acceptedSocket = serverSocket.accept();
    clientHandler = new ClientHandler(clientSocket,serverMessageHandler);
    clientHandlerThread = new Thread(new ClientHandler(acceptedSocket,serverMessageHandler));
    clientHandlerThread.start();
  }
  @AfterAll
  void close() throws IOException {
    dataOutputStream.close();
    clientSocket.close();
    serverSocket.close();
  }

  @Test
  void run() throws InterruptedException, IOException {
    Assertions.assertEquals(1,serverMessageHandler.getActiveClientMap().size());
    Assertions.assertTrue(serverMessageHandler.getActiveClientMap().containsKey("Amy"));

    dataOutputStream.writeInt(ChatRoomProtocol.DISCONNECT_MESSAGE);
    dataOutputStream.writeChar(BLANK_SPACE_CHAR);
    dataOutputStream.writeInt(3);
    dataOutputStream.writeChar(BLANK_SPACE_CHAR);
    dataOutputStream.writeUTF("Amy");

    clientHandlerThread.join();
    Assertions.assertEquals(0,serverMessageHandler.getActiveClientMap().size());
    Assertions.assertFalse(serverMessageHandler.getActiveClientMap().containsKey("Amy"));
  }

  @Test
  void testEquals() {
    Assertions.assertTrue(clientHandler.equals(clientHandler));
  }

  @Test
  void testEqualsNull() {
    Assertions.assertFalse(clientHandler.equals(null));
  }

  @Test
  void testEqualsDiff() {
    Assertions.assertFalse(clientHandler.equals("null"));
  }
  @Test
  void testEqualsDiffArg() {
    Assertions.assertFalse(
        clientHandler.equals(new ClientHandler(acceptedSocket, new ServerMessageHandler())));
  }

  @Test
  void testHashCode() {
    Assertions.assertNotEquals(1, clientHandler.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertNotEquals("test", clientHandler.toString());
  }
}