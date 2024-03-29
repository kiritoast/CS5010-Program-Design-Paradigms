package assignment3.problem1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArgumentParserTest {
  private ArgumentParser parser1;
  private ArgumentParser parser2;
  private String[] args1;
  private String[] args2;

  private HashMap<String, String> expectedMap1;

  @Test
  void testGetArguments() {
    // test case 1: valid arguments
    String[] args1 = {"--email", "--email-template", "email-template.txt", "--output-dir", "output", "--csv-file", "data.csv"};
    ArgumentParser parser1 = new ArgumentParser(args1);
    HashMap<String, String> expectedMap1 = new HashMap<>();
    expectedMap1.put("--email", "true");
    expectedMap1.put("--email-template", "email-template.txt");
    expectedMap1.put("--output-dir", "output");
    expectedMap1.put("--csv-file", "data.csv");
    assertEquals(expectedMap1, parser1.getArguments());

    // test case 2: missing required argument --output-dir
    String[] args2 = {"--email", "--email-template", "email-template.txt", "--csv-file", "data.csv"};
    IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> new ArgumentParser(args2));
    assertEquals("Missing required argument: --output-dir", exception2.getMessage());

    // test case 3: missing required argument --csv-file
    String[] args3 = {"--email", "--email-template", "email-template.txt", "--output-dir", "output"};
    IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, () -> new ArgumentParser(args3));
    assertEquals("Missing required argument: --csv-file", exception3.getMessage());

    // test case 4: missing argument --email-template
    String[] args4 = {"--email", "--output-dir", "output", "--csv-file", "data.csv"};
    IllegalArgumentException exception4 = assertThrows(IllegalArgumentException.class, () -> new ArgumentParser(args4));
    assertEquals("Missing argument for --email-template. Required if --email is used.", exception4.getMessage());

    // test case 5: missing argument --letter-template
    String[] args5 = {"--letter", "--output-dir", "output", "--csv-file", "data.csv"};
    IllegalArgumentException exception5 = assertThrows(IllegalArgumentException.class, () -> new ArgumentParser(args5));
    assertEquals("Missing argument for --letter-template. Required if --letter is used.", exception5.getMessage());

    // test case 6: invalid argument
    String[] args6 = {"--invalid-argument", "--output-dir", "output", "--csv-file", "data.csv"};
    IllegalArgumentException exception6 = assertThrows(IllegalArgumentException.class, () -> new ArgumentParser(args6));
    assertEquals("Invalid argument: --invalid-argument", exception6.getMessage());

    // test case 7: missing argument value for --email-template
    String[] args7 = {"--email", "--email-template", "--output-dir", "output", "--csv-file", "data.csv"};
    IllegalArgumentException exception7 = assertThrows(IllegalArgumentException.class, () -> new ArgumentParser(args7));
    assertEquals("Invalid argument: output", exception7.getMessage());

    // test case 8: missing argument value for --letter-template
    String[] args8 = {"--letter", "--letter-template", "--output-dir", "output", "--csv-file", "data.csv"};
    IllegalArgumentException exception8 = assertThrows(IllegalArgumentException.class, () -> new ArgumentParser(args8));
    assertEquals("Invalid argument: output", exception8.getMessage());
  }

  @BeforeEach
  void setUp() {
    args1 = new String[]{"--email", "--email-template", "email-template.txt", "--output-dir",
        "output", "--csv-file", "data.csv"};
    args2 = new String[]{"--letter", "--letter-template", "letter-template.txt", "--output-dir",
        "output", "--csv-file", "data.csv"};

    parser1 = new ArgumentParser(args1);
    parser2 = new ArgumentParser(args2);
    expectedMap1 = new HashMap<>();
    expectedMap1.put("--email", "true");
    expectedMap1.put("--email-template", "email-template.txt");
    expectedMap1.put("--output-dir", "output");
    expectedMap1.put("--csv-file", "data.csv");

  }

  @Test
  void getArguments() {
    assertEquals(expectedMap1, parser1.getArguments());
  }

  @Test
  void testEquals() {
    Assertions.assertFalse(parser1.equals(null));
    Assertions.assertFalse(parser1.equals(new Object()));
    Assertions.assertTrue(parser1.equals(parser1));
    Assertions.assertFalse(parser1.equals(parser2));
    Assertions.assertFalse(parser1.equals(null));

  }

  @Test
  void testHashCode() {
    assertEquals(parser1.hashCode(), parser1.hashCode());

  }
}
