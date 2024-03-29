package assignment6.chatRoomApp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UiInputHandlerTest {

  private UiInputHandler uiInputHandler;
  private ByteArrayOutputStream outputStream;

  @BeforeEach
  void setUp() {
    uiInputHandler = new UiInputHandler("test-client");
    outputStream = new ByteArrayOutputStream();
  }

  @Test
  void initialize() {
    uiInputHandler.initialize(new DataOutputStream(outputStream));
  }

  @Test
  void testProcess_help() {
    String input = "?";
    boolean isActive = uiInputHandler.process(input, new DataOutputStream(outputStream));
    Assertions.assertEquals(true, isActive);
  }

  @Test
  void testProcess_logoff() {
    String input = "logoff";
    boolean isActive = uiInputHandler.process(input, new DataOutputStream(outputStream));
    Assertions.assertEquals(false, isActive);
  }

  @Test
  void testProcess_broadcast() {
    String input = "@all message";
    boolean isActive = uiInputHandler.process(input, new DataOutputStream(outputStream));
    String expectedOutput = outputStream.toString();
    Assertions.assertEquals(true, isActive);
    Assertions.assertEquals(expectedOutput, outputStream.toString());
  }

  @Test
  void testProcess_userQuery() {
    String input = "who";
    boolean isActive = uiInputHandler.process(input, new DataOutputStream(outputStream));
    String expectedOutput = outputStream.toString();
    Assertions.assertEquals(true, isActive);
    Assertions.assertEquals(expectedOutput, outputStream.toString());
  }

  @Test
  void testProcess_directMessage() {
    String input = "@user message";
    boolean isActive = uiInputHandler.process(input, new DataOutputStream(outputStream));
    String expectedOutput = outputStream.toString();
    Assertions.assertEquals(true, isActive);
    Assertions.assertEquals(expectedOutput, outputStream.toString());
  }

  @Test
  void testProcess_insult() {
    String input = "!user";
    boolean isActive = uiInputHandler.process(input, new DataOutputStream(outputStream));
    String expectedOutput = outputStream.toString();
    Assertions.assertEquals(true, isActive);
    Assertions.assertEquals(expectedOutput, outputStream.toString());
  }

  @Test
  public void testOrdinalIndexOfNotFound() {
    String input = " ";
    boolean isActive = uiInputHandler.process(input, new DataOutputStream(outputStream));
    String expectedOutput = outputStream.toString();
    Assertions.assertEquals(true, isActive);
    Assertions.assertEquals(expectedOutput, outputStream.toString());

  }


  @Test
  void testEquals() {
    Assertions.assertTrue(uiInputHandler.equals(uiInputHandler));
  }

  @Test
  void testEqualsNull() {
    Assertions.assertFalse(uiInputHandler.equals(null));
  }

  @Test
  void testEqualsDiff() {
    Assertions.assertFalse(uiInputHandler.equals("null"));
  }

  @Test
  void testEqualsDiffArg() {
    Assertions.assertFalse(
        uiInputHandler.equals(new UiInputHandler("client_test")));
  }

  @Test
  void testHashCode() {
    Assertions.assertNotEquals(1, uiInputHandler.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertNotEquals("test", uiInputHandler.toString());
  }

}