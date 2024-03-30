package assignment3.problem1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileGeneratorTest {
  private FileGenerator fileGenerator;
  private CSVReader csvReader;
  private TemplateReader templateReader;
  @BeforeEach
  void setUp() throws IOException {
    csvReader = new CSVReader( "src/main/resources/insurance-company-members.csv");
    templateReader = new TemplateReader("src/main/resources/tempTest.txt");

    fileGenerator = new FileGenerator(csvReader, templateReader, new HashMap<>());
  }

  @Test
  void generateOneFile() {
    HashMap<String, String> userInfo = new HashMap<>();
    userInfo.put("first_name", "John");
    userInfo.put("last_name", "Doe");
    userInfo.put("age", "35");
    String expectedOutput = "I am writing this to test template for testnullis for the persona Johnand Doe. Best wishes.";

    assertEquals(expectedOutput, fileGenerator.generateOneFile(userInfo));
  }

  @Test
  void outputFiles() throws IOException {
    String outputPath = "src/test/resources/output";
    fileGenerator = new FileGenerator(csvReader, templateReader, new HashMap<>());
    fileGenerator.setOutputPath(outputPath);

    fileGenerator.outputFiles();

    assertEquals(500, new File(outputPath).listFiles().length);

  }

  @Test
  void testEquals() throws IOException {
    CSVReader csvReader2 = new CSVReader("src/main/resources/test.csv");
    TemplateReader templateReader2 = new TemplateReader("src/main/resources/tempTest.txt");

    FileGenerator fileGenerator1 = new FileGenerator(csvReader, templateReader, new HashMap<>());
    FileGenerator fileGenerator2 = new FileGenerator(csvReader, templateReader, new HashMap<>());
    FileGenerator fileGenerator3 = new FileGenerator(csvReader2, templateReader, new HashMap<>());
    FileGenerator fileGenerator4 = new FileGenerator(csvReader, templateReader2, new HashMap<>());
    FileGenerator fileGenerator5 = new FileGenerator(csvReader2, templateReader2, new HashMap<>());
    assertTrue(fileGenerator1.equals(fileGenerator1));
    assertTrue(fileGenerator1.equals(fileGenerator2)); // same objects
    assertFalse(fileGenerator1.equals(null)); // null check
    assertFalse(fileGenerator1.equals("some string")); // different class check
    assertFalse(fileGenerator1.equals(fileGenerator3)); // csvReader check
    assertTrue(fileGenerator1.equals(fileGenerator4)); // templateReader check
    assertFalse(fileGenerator1.equals(fileGenerator5)); // both csvReader and templateReader checks

  }

  @Test
  void testHashCode() {
    assertEquals(fileGenerator.hashCode(), fileGenerator.hashCode());

  }

}