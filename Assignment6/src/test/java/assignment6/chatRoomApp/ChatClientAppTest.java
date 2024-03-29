package assignment6.chatRoomApp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChatClientAppTest {

  private ByteArrayOutputStream outContent;
  private final PrintStream originalOut = System.out;
  @BeforeEach
  void setUp() {
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  void mainServerNotUp() {
    ChatClientApp.main(new String[]{"localhost","3400"});
    Assertions.assertEquals(ChatClientApp.SERVER_NOT_AVAILABLE.replace("\n", "").replace("\r",""), outContent.toString().replace("\n", "").replace("\r",""));
  }
}