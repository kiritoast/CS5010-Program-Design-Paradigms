package assignment5.concurrentSolution;


import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConcurrentArgValidatorTest {

  private ConcurrentArgValidator concurrentArgValidator;

  @BeforeEach
  void setUp() {
    String[] args = new String[]{"DataSet", "300"};
    concurrentArgValidator = new ConcurrentArgValidator(args);
  }

  @Test
  void validateCourseFile() {
    List<String> filePaths = concurrentArgValidator.validate();
    Assertions.assertEquals("DataSet" + File.separator + "courses.csv", filePaths.get(0));
  }

  @Test
  void validateStudentFile() {
    concurrentArgValidator = new ConcurrentArgValidator(new String[]{"DataSet" + File.separator});
    List<String> filePaths = concurrentArgValidator.validate();
    Assertions.assertEquals("DataSet" + File.separator + "studentVle.csv", filePaths.get(1));
  }

  @Test
  void validateNull() {
    concurrentArgValidator = new ConcurrentArgValidator(null);
    Exception exception = Assertions.assertThrows(RuntimeException.class,
        () -> concurrentArgValidator.validate());
    Assertions.assertEquals(concurrentArgValidator.INVALID_ARGUMENT_ERROR, exception.getMessage());
  }

  @Test
  void validateFileNotFound() {
    concurrentArgValidator = new ConcurrentArgValidator(new String[]{"Data"});
    Exception exception = Assertions.assertThrows(RuntimeException.class,
        () -> concurrentArgValidator.validate());
    Assertions.assertEquals(
        String.format(ConcurrentArgValidator.FILE_NOT_FOUND_ERROR, "courses.csv", "Data"),
        exception.getMessage());
  }

  @Test
  void validateNullThreshold() {
    concurrentArgValidator = new ConcurrentArgValidator(new String[]{"DataSet", "tt"});
    Exception exception = Assertions.assertThrows(RuntimeException.class,
        () -> concurrentArgValidator.validate());
    Assertions.assertEquals(ConcurrentArgValidator.THRESHOLD_NOT_A_NUMBER, exception.getMessage());
  }

  @Test
  void testEquals() {
    String[] args = new String[]{"DataSet", "300"};
    ConcurrentArgValidator concurrentArgValidatorTest = new ConcurrentArgValidator(args);
    Assertions.assertEquals(concurrentArgValidatorTest, this.concurrentArgValidator);

    Assertions.assertEquals(this.concurrentArgValidator, this.concurrentArgValidator);
    Assertions.assertFalse(this.concurrentArgValidator.equals(null));
    Assertions.assertFalse(this.concurrentArgValidator.equals(1));
  }

  @Test
  void testHashCode() {
    String[] args = new String[]{"DataSet", "300"};
    ConcurrentArgValidator concurrentArgValidatorTest = new ConcurrentArgValidator(args);
    Assertions.assertEquals(concurrentArgValidatorTest.hashCode(),
        this.concurrentArgValidator.hashCode());
  }

  @Test
  void testToString() {
    String[] args = new String[]{"DataSet", "300"};
    ConcurrentArgValidator concurrentArgValidatorTest = new ConcurrentArgValidator(args);
    Assertions.assertEquals(concurrentArgValidatorTest.toString(),
        this.concurrentArgValidator.toString());
  }

}