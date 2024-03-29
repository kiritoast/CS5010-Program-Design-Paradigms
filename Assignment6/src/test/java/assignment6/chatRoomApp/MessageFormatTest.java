package assignment6.chatRoomApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageFormatTest {

  private MessageFormat messageFormat;
  private List<String> clients;
  @BeforeEach
  void setUp() {
    clients = Arrays.asList("test1","test2");
    messageFormat = new MessageFormat(true,"test", clients,"sender","recipient");
  }

  @Test
  void isSuccess() {
    Assertions.assertTrue(messageFormat.isSuccess());
  }

  @Test
  void getMessage() {
    Assertions.assertEquals("test",messageFormat.getMessage());
  }

  @Test
  void getClients() {
    Assertions.assertNotEquals(new ArrayList<String>(),messageFormat.getClients());
  }

  @Test
  void getSender() {
    Assertions.assertEquals("sender",messageFormat.getSender());
  }

  @Test
  void getRecipient() {
    Assertions.assertEquals("recipient",messageFormat.getRecipient());
  }

  @Test
  void setSuccess() {
    messageFormat.setSuccess(false);
    Assertions.assertFalse(messageFormat.isSuccess());
  }

  @Test
  void setMessage() {
    messageFormat.setMessage("message");
    Assertions.assertEquals("message",messageFormat.getMessage());
  }

  @Test
  void setClients() {
    messageFormat.setClients(Arrays.asList("test"));
    Assertions.assertNotEquals(new ArrayList<String>(),messageFormat.getClients());

  }

  @Test
  void setSender() {
    messageFormat.setSender("s1");
    Assertions.assertEquals("s1",messageFormat.getSender());
  }

  @Test
  void setRecipient() {
    messageFormat.setRecipient("r1");
    Assertions.assertEquals("r1",messageFormat.getRecipient());
  }

  @Test
  void testEquals() {
    Assertions.assertTrue(messageFormat.equals(messageFormat));
  }

  @Test
  void testEqualsNull() {
    Assertions.assertFalse(messageFormat.equals(null));
  }

  @Test
  void testEqualsDiff() {
    Assertions.assertFalse(messageFormat.equals("null"));
  }
  @Test
  void testEqualsDiffArg() {
    Assertions.assertFalse(
        messageFormat.equals(new MessageFormat(true,"test",clients,"sender",null)));
  }

  @Test
  void testHashCode() {
    Assertions.assertNotEquals(1, messageFormat.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertNotEquals("test", messageFormat.toString());
  }
}