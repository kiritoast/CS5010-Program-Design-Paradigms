package assignment6.chatRoomApp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class UiHandlerTest {
  private ByteArrayInputStream inputContent;
  private ProtocolHandler protocolHandler;
  private ClientMessageHandler clientMessageHandler;
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private Socket acceptedSocket;
  private DataOutputStream serverOutput;
  private DataInputStream serverInput;
  private DataInputStream clientInput;
  private DataOutputStream clientOutput;
  private UiHandler uiHandler;
  private Scanner scanner;
  private static final int BLANK_SPACE_CHAR = 32;
  private ServerMessageHandler serverMessageHandler;
  @BeforeEach
  void setUp() throws IOException {
    protocolHandler = new ProtocolHandler();
    clientMessageHandler = new ClientMessageHandler(protocolHandler);
    serverSocket = new ServerSocket(1100);
    clientSocket = new Socket("localhost",1100);
    acceptedSocket = serverSocket.accept();
    serverOutput = new DataOutputStream(acceptedSocket.getOutputStream());
    serverInput = new DataInputStream(acceptedSocket.getInputStream());
    clientInput = new DataInputStream(clientSocket.getInputStream());
    clientOutput = new DataOutputStream(clientSocket.getOutputStream());
    UiInputHandler uiInputHandler = new UiInputHandler("c1");
    scanner = new Scanner(System.in);
    this.uiHandler = new UiHandler(scanner, uiInputHandler, clientOutput);
    this.protocolHandler = new ProtocolHandler();
    this.serverMessageHandler = new ServerMessageHandler();
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
//    clientOutput.writeInt(ChatRoomProtocol.CONNECT_MESSAGE);
//    clientOutput.writeChar(BLANK_SPACE_CHAR);
//    clientOutput.writeInt(4);
//    clientOutput.writeChar(BLANK_SPACE_CHAR);
//    clientOutput.writeUTF("test");
//    int msgType1 = serverInput.readInt();
//    serverMessageHandler.process(msgType1, serverInput, serverOutput);
//
//    Thread thread = new Thread(uiHandler);
//    thread.start();
//    System.setIn(new ByteArrayInputStream("".getBytes()));
//    Assertions.assertTrue(thread.isAlive());

//    System.setIn(new ByteArrayInputStream("".getBytes()));
//    System.setIn(new ByteArrayInputStream("c1".getBytes()));
//    protocolHandler.readConnectRequest(serverInput);
//    int msgType1 = serverInput.readInt();
//    serverMessageHandler.process(msgType1, serverInput, serverOutput);
//    protocolHandler.readConnectResponse(clientInput);

//    System.setIn(new ByteArrayInputStream("logoff".getBytes()));
//    protocolHandler.readDisconnectRequest(serverInput);
//    int msgType2 = serverInput.readInt();
//    serverMessageHandler.process(msgType2, serverInput, serverOutput);
//    protocolHandler.readConnectResponse(clientInput);
//
//    thread.join();
//    Assertions.assertTrue(!thread.isAlive());
  }

  @Test
  void testEquals() {
    UiInputHandler uiInputHandler = new UiInputHandler("c1");
    UiHandler uiHandlerTest = new UiHandler(scanner, uiInputHandler, clientOutput);
    Assertions.assertTrue(this.uiHandler.equals(uiHandlerTest));

    UiHandler uiHandlerTest2 = new UiHandler(scanner, null, clientOutput);
    Assertions.assertFalse(this.uiHandler.equals(uiHandlerTest2));

    UiHandler uiHandlerTest3 = new UiHandler(scanner, uiInputHandler, null);
    Assertions.assertFalse(this.uiHandler.equals(uiHandlerTest3));

    Assertions.assertEquals(this.uiHandler, this.uiHandler);
    Assertions.assertFalse(this.uiHandler.equals(null));
    Assertions.assertFalse(this.uiHandler.equals(1));
  }

  @Test
  void testHashCode() {
    UiInputHandler uiInputHandler = new UiInputHandler("c1");
    UiHandler uiHandlerTest = new UiHandler(scanner, uiInputHandler, clientOutput);
    Assertions.assertEquals(this.uiHandler.hashCode(), uiHandlerTest.hashCode());
  }

  @Test
  void testToString() {
    UiInputHandler uiInputHandler = new UiInputHandler("c1");
    UiHandler uiHandlerTest = new UiHandler(scanner, uiInputHandler, clientOutput);
    Assertions.assertTrue(this.uiHandler.toString().equals(uiHandlerTest.toString()));
  }
}