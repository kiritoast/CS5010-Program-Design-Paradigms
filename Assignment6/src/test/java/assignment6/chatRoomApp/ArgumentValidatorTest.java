package assignment6.chatRoomApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArgumentValidatorTest {
  private ArgumentValidator argumentValidator;

  @Test
  void testValidArguments() {
    String[] args = {"localhost", "3000"};
    ArgumentValidator validator = new ArgumentValidator(args);
    Assertions.assertTrue(validator.validate());
  }

  @Test
  void testInvalidArguments() {
    String[] args = {"localhost", "invalidPort"};
    ArgumentValidator validator = new ArgumentValidator(args);
    Assertions.assertFalse(validator.validate());
  }

  @Test
  void testInvalidIPAddress() {
    String[] args = {"256.0.0.1", "8080"};
    ArgumentValidator validator = new ArgumentValidator(args);
    Assertions.assertFalse(validator.validate());
  }
  @Test
  void testInvalidArgsLength(){
    String[] args = new String[]{};
    ArgumentValidator validator = new ArgumentValidator(args);
    Assertions.assertFalse(validator.validate());
  }
  @Test
  void testInvalidPortSize(){
    String[] args = new String[]{"256.0.0.1\", \"-1"};
    ArgumentValidator validator = new ArgumentValidator(args);
    Assertions.assertFalse(validator.validate());
    String[] args2 = new String[]{"256.0.0.1\", \"65536"};
    ArgumentValidator validator2 = new ArgumentValidator(args2);
    Assertions.assertFalse(validator.validate());
  }
  @Test
  void testEquals() {
    String[] args = {"localhost", "3000"};
    ArgumentValidator validator = new ArgumentValidator(args);
    Assertions.assertEquals(true, validator.equals(validator));
  }

  @Test
  void testEqualsNull() {
    String[] args = {"localhost", "3000"};
    ArgumentValidator validator = new ArgumentValidator(args);
    Assertions.assertEquals(false, validator.equals(null));
  }

  @Test
  void testEqualsDiff() {
    String[] args = {"localhost", "3000"};
    ArgumentValidator validator = new ArgumentValidator(args);
    Assertions.assertEquals(false, validator.equals("null"));
  }

  @Test
  void testEqualsDiffArg() {
    String[] args = {"localhost", "3000"};
    ArgumentValidator validator = new ArgumentValidator(args);
    Assertions.assertEquals(false,
        validator.equals(new ArgumentValidator(new String[]{"Data"})));
  }
  @Test
  void testHashCode() {
    String[] args = {"localhost", "3000"};
    ArgumentValidator validator = new ArgumentValidator(args);
    Assertions.assertNotEquals(1, validator.hashCode());
  }

  @Test
  void testToString() {
    String[] args = {"localhost", "3000"};
    ArgumentValidator validator = new ArgumentValidator(args);
    Assertions.assertNotEquals("test", validator.toString());
  }
}