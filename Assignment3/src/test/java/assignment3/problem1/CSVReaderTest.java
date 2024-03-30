package assignment3.problem1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CSVReaderTest {
  private static final String PATH_PREFIX = "/Users/jijim/Documents/5010/Student_jijiming/assignment3/";
  private CSVReader csvReader;
  @BeforeEach
  void setUp() throws FileNotFoundException {
    csvReader = new CSVReader(PATH_PREFIX + "src/main/resources/insurance-company-members.csv");
  }
  @Test
  void length() {
    Assertions.assertEquals(500, csvReader.length());
  }

  @Test
  void getUserInfo() {
    HashMap<String, String> user = new HashMap<>();
    user = csvReader.getUserInfo(20);
    String first_name = user.get("first_name");
    Assertions.assertEquals(first_name, "Bette");

  }

  @Test
  void testEquals() throws FileNotFoundException {
    CSVReader csvReader1 = new CSVReader(PATH_PREFIX + "src/main/resources/insurance-company-members.csv");
    Assertions.assertFalse(csvReader.equals(null));
    Assertions.assertFalse(csvReader.equals(new Object()));
    Assertions.assertTrue(csvReader.equals(csvReader));
    Assertions.assertTrue(csvReader.equals(csvReader1));
    Assertions.assertTrue(csvReader.equals(csvReader1));

  }

  @Test
  void testHashCode() throws FileNotFoundException {
    CSVReader csvReader1 = new CSVReader(PATH_PREFIX + "src/main/resources/insurance-company-members.csv");
    Assertions.assertEquals(csvReader1.hashCode(), csvReader.hashCode());

  }
}