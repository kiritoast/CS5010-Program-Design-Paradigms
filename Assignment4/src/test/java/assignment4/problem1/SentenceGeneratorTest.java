package assignment4.problem1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SentenceGeneratorTest {

  private SentenceGenerator sentenceGenerator;

  @BeforeEach
  void setUp() {
    sentenceGenerator = new SentenceGenerator(
        "Grammar" + File.separator + "single_poem_grammar.json");
  }

  @Test
  void generate() {
    Assertions.assertEquals("The waves sigh warily tonight.", sentenceGenerator.generate());
  }

  @Test
  void generateFail() {
    sentenceGenerator = new SentenceGenerator(
        "Grammar" + File.separator + "single_poem_grammar_fail.json");
    Assertions.assertEquals(String.format(SentenceGenerator.NOT_EXISTING_MESSAGE, "adverb"),
        sentenceGenerator.generate());
  }

  @Test
  void testEquals() {
    Assertions.assertEquals(true, sentenceGenerator.equals(sentenceGenerator));
  }

  @Test
  void testEqualsNull() {
    Assertions.assertEquals(false, sentenceGenerator.equals(null));
  }

  @Test
  void testEqualsDiffType() {
    Assertions.assertEquals(false, sentenceGenerator.equals("null"));
  }

  @Test
  void testEqualsSame() {
    Assertions.assertEquals(true, sentenceGenerator.equals(
        new SentenceGenerator("Grammar" + File.separator + "single_poem_grammar.json")));
  }

  @Test
  void testHashCode() {
    Assertions.assertNotEquals(100, sentenceGenerator.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertNotEquals("test", sentenceGenerator.toString());
  }
}