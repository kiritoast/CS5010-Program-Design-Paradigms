package assignment5.sequentialSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArgumentValidatorTest {

  private ArgumentValidator argumentValidator;

  @BeforeEach
  void setUp() {
    String[] args = new String[]{"DataSet"};
    argumentValidator = new ArgumentValidator(args);
  }

  @Test
  void validateCourseFile() {
    List<String> filePaths = argumentValidator.validate();
    Assertions.assertEquals("DataSet" + File.separator + "courses.csv", filePaths.get(0));
  }

  @Test
  void validateStudentFile() {
    argumentValidator = new ArgumentValidator(new String[]{"DataSet" + File.separator});
    List<String> filePaths = argumentValidator.validate();
    Assertions.assertEquals("DataSet" + File.separator + "studentVle.csv", filePaths.get(1));
  }

  @Test
  void validateNull() {
    argumentValidator = new ArgumentValidator(null);
    Exception exception = Assertions.assertThrows(RuntimeException.class,
        () -> argumentValidator.validate());
    Assertions.assertEquals(ArgumentValidator.INVALID_ARGUMENT_ERROR, exception.getMessage());
  }

  @Test
  void validateNoArg() {
    argumentValidator = new ArgumentValidator(new String[]{});
    Exception exception = Assertions.assertThrows(RuntimeException.class,
        () -> argumentValidator.validate());
    Assertions.assertEquals(ArgumentValidator.INVALID_ARGUMENT_ERROR, exception.getMessage());
  }

  @Test
  void validateFileNotFound() {
    argumentValidator = new ArgumentValidator(new String[]{"Data"});
    Exception exception = Assertions.assertThrows(RuntimeException.class,
        () -> argumentValidator.validate());
    Assertions.assertEquals(
        String.format(ArgumentValidator.FILE_NOT_FOUND_ERROR, "courses.csv", "Data"),
        exception.getMessage());
  }

  @Test
  void testEquals() {
    Assertions.assertEquals(true, argumentValidator.equals(argumentValidator));
  }

  @Test
  void testEqualsNull() {
    Assertions.assertEquals(false, argumentValidator.equals(null));
  }

  @Test
  void testEqualsDiff() {
    Assertions.assertEquals(false, argumentValidator.equals("null"));
  }

  @Test
  void testEqualsDiffArg() {
    Assertions.assertEquals(false,
        argumentValidator.equals(new ArgumentValidator(new String[]{"Data"})));
  }

  @Test
  void testHashCode() {
    Assertions.assertNotEquals(1, argumentValidator.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertNotEquals("test", argumentValidator.toString());
  }
}