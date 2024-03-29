package assignment4.problem1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonReaderTest {

  private JsonReader jsonReader;

  @BeforeEach
  void setUp() {
    jsonReader = new JsonReader("Grammar" + File.separator + "single_poem_grammar.json");
  }

  @Test
  void readValue() {
    Assertions.assertEquals("Single Poem Generator", jsonReader.readValue("grammarTitle"));
  }

  @Test
  void readArrayValue() {
    List<String> valueList = jsonReader.readArrayValue("object");
    Assertions.assertEquals("waves", valueList.get(0));
  }

  @Test
  void testEquals() {
    Assertions.assertEquals(true, jsonReader.equals(jsonReader));
  }

  @Test
  void testEqualsNull() {
    Assertions.assertEquals(false, jsonReader.equals(null));
  }

  @Test
  void testEqualsDiffType() {
    Assertions.assertEquals(false, jsonReader.equals("null"));
  }

  @Test
  void testEqualsSame() {
    Assertions.assertEquals(true,
        jsonReader.equals(new JsonReader("Grammar" + File.separator + "single_poem_grammar.json")));
  }

  @Test
  void testHashCode() {
    Assertions.assertNotEquals(100, jsonReader.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertNotEquals("test", jsonReader.toString());
  }
}