package assignment6.chatRoomApp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.DataInputStream;
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
class ServerHandlerTest {
  private ProtocolHandler protocolHandler;
  private ClientMessageHandler clientMessageHandler;
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private Socket acceptedSocket;
  private DataOutputStream serverOutput;
  private DataInputStream serverInput;
  private DataInputStream clientInput;
  private DataOutputStream clientOutput;
  private ServerHandler serverHandler;
  private ServerMessageHandler serverMessageHandler;
  private static final int BLANK_SPACE_CHAR = 32;
  @BeforeEach
  void setUp() throws IOException {
    serverMessageHandler = new ServerMessageHandler();
    protocolHandler = new ProtocolHandler();
    clientMessageHandler = new ClientMessageHandler(protocolHandler);
    serverSocket = new ServerSocket(1900);
    clientSocket = new Socket("localhost",1900);
    acceptedSocket = serverSocket.accept();
    serverOutput = new DataOutputStream(acceptedSocket.getOutputStream());
    serverInput = new DataInputStream(acceptedSocket.getInputStream());
    clientInput = new DataInputStream(clientSocket.getInputStream());
    clientOutput = new DataOutputStream(clientSocket.getOutputStream());
    this.serverHandler = new ServerHandler(clientMessageHandler, clientInput);
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
  void run() throws IOException, InterruptedException {
    Thread thread = new Thread(serverHandler);
    clientOutput.writeInt(ChatRoomProtocol.CONNECT_MESSAGE);
    clientOutput.writeChar(BLANK_SPACE_CHAR);
    clientOutput.writeInt(4);
    clientOutput.writeChar(BLANK_SPACE_CHAR);
    clientOutput.writeUTF("test");
    thread.start();
    int msgType1 = serverInput.readInt();
    serverMessageHandler.process(msgType1, serverInput, serverOutput);
    Assertions.assertTrue(thread.isAlive());
    clientOutput.writeInt(ChatRoomProtocol.DISCONNECT_MESSAGE);
    clientOutput.writeChar(BLANK_SPACE_CHAR);
    clientOutput.writeInt(4);
    clientOutput.writeChar(BLANK_SPACE_CHAR);
    clientOutput.writeUTF("test");
    int msgType2 = serverInput.readInt();
    serverMessageHandler.process(msgType2, serverInput, serverOutput);

    thread.join();
    Assertions.assertTrue(!thread.isAlive());
  }

  @Test
  void testEquals() throws IOException {
    ServerHandler serverHandlerTest = new ServerHandler(clientMessageHandler, clientInput);
    Assertions.assertEquals(serverHandlerTest, this.serverHandler);

    ServerHandler serverHandlerTest1 = new ServerHandler(null, clientInput);
    Assertions.assertFalse(this.serverHandler.equals(serverHandlerTest1));

    ServerHandler serverHandlerTest2 = new ServerHandler(clientMessageHandler, null);
    Assertions.assertFalse(this.serverHandler.equals(serverHandlerTest2));

    Assertions.assertEquals(this.serverHandler, this.serverHandler);
    Assertions.assertFalse(this.serverHandler.equals(null));
    Assertions.assertFalse(this.serverHandler.equals(1));
  }

  @Test
  void testHashCode() {
    ServerHandler serverHandlerTest = new ServerHandler(clientMessageHandler, clientInput);
    Assertions.assertEquals(serverHandlerTest, this.serverHandler);

    Assertions.assertEquals(this.serverHandler.hashCode(), this.serverHandler.hashCode());
  }

  @Test
  void testToString() {
    ServerHandler serverHandlerTest = new ServerHandler(clientMessageHandler, clientInput);
    Assertions.assertEquals(serverHandlerTest, this.serverHandler);

    Assertions.assertEquals(this.serverHandler.toString(), this.serverHandler.toString());
  }
}