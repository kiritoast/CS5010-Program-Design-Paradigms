package assignment3.problem1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TemplateReaderTest {
  private TemplateReader templateReader;
  @BeforeEach
  void setUp() throws IOException {
    templateReader = new TemplateReader("src/main/resources/tempTest.txt");
  }

  @Test
  void getFillIn() {
    List<String> str = new ArrayList<>();
    str.add("email");
    str.add("first_name");
    str.add("last_name");
    Assertions.assertEquals(str, templateReader.getFillIn());
  }

  @Test
  void getTemplate() {
    String TrueTemplate = "I am writing this to test template for test[[email]]is for the persona [[first_name]]and [[last_name]]. Best wishes.";
    Assertions.assertEquals(TrueTemplate, templateReader.getTemplate());
  }

  @Test
  void getTemplatePieces() {
    List<String> trueTemplatePieces = new ArrayList<>();
    trueTemplatePieces.add("I am writing this to test template for test");
    trueTemplatePieces.add("is for the persona ");
    trueTemplatePieces.add("and ");
    trueTemplatePieces.add(". Best wishes.");
    Assertions.assertEquals(trueTemplatePieces, templateReader.getTemplatePieces());

  }

  @Test
  void testEquals() throws IOException {
    TemplateReader templateReader1 = new TemplateReader( "src/main/resources/tempTest.txt");
    Assertions.assertFalse(templateReader.equals(null));
    Assertions.assertFalse(templateReader.equals(new Object()));
    Assertions.assertTrue(templateReader.equals(templateReader));
    Assertions.assertTrue(templateReader.equals(templateReader1));
    Assertions.assertTrue(templateReader.equals(templateReader1));

  }

  @Test
  void testHashCode() throws IOException {
    TemplateReader templateReader1 = new TemplateReader( "src/main/resources/tempTest.txt");
    Assertions.assertEquals(templateReader.hashCode(), templateReader1.hashCode());

  }
}