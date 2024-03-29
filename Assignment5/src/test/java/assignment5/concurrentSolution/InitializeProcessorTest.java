package assignment5.concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InitializeProcessorTest {

  private String testDataPath;
  private InitializeProcessor initializeProcessor;

  @BeforeEach
  void setUp() {
    File testDirectory = new File(
        "src" + File.separator + "test" + File.separator + "java" + File.separator + "assignment5"
            + File.separator + "TestDataSet");
    this.testDataPath = testDirectory.getAbsolutePath();
    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    this.initializeProcessor = new InitializeProcessor(
        Paths.get(testDataPath, "courses.csv").toString(), courseAndClick);
  }

  @Test
  void run() {
    this.initializeProcessor.run();
    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    courseAndClick.put("AAA_2013J", new ConcurrentHashMap<String, Integer>());
    InitializeProcessor initializeProcessorTest = new InitializeProcessor(
        Paths.get(testDataPath, "courses.csv").toString(), courseAndClick);
    Assertions.assertTrue(this.initializeProcessor.equals(initializeProcessorTest));
  }

  @Test
  void testEquals() {
    this.initializeProcessor.run();

    Assertions.assertEquals(this.initializeProcessor, this.initializeProcessor);
    Assertions.assertFalse(this.initializeProcessor.equals(null));
    Assertions.assertFalse(this.initializeProcessor.equals(1));

    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    courseAndClick.put("AAA_2013J", new ConcurrentHashMap<String, Integer>());

    InitializeProcessor initializeProcessorTest1 = new InitializeProcessor(
        Paths.get(testDataPath, "coursesTest.csv").toString(), courseAndClick);
    Assertions.assertFalse(this.initializeProcessor.equals(initializeProcessorTest1));

    courseAndClick.put("AAA_2013B", new ConcurrentHashMap<String, Integer>());

    InitializeProcessor initializeProcessorTest2 = new InitializeProcessor(
        Paths.get(testDataPath, "courses.csv").toString(), courseAndClick);
    Assertions.assertFalse(this.initializeProcessor.equals(initializeProcessorTest2));
  }

  @Test
  void testHashCode() {
    this.initializeProcessor.run();
    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    courseAndClick.put("AAA_2013J", new ConcurrentHashMap<String, Integer>());
    InitializeProcessor initializeProcessorTest = new InitializeProcessor(
        Paths.get(testDataPath, "courses.csv").toString(), courseAndClick);
    Assertions.assertTrue(
        this.initializeProcessor.hashCode() == initializeProcessorTest.hashCode());

  }

  @Test
  void testToString() {
    this.initializeProcessor.run();
    ConcurrentMap<String, ConcurrentMap<String, Integer>> courseAndClick = new ConcurrentHashMap<>();
    courseAndClick.put("AAA_2013J", new ConcurrentHashMap<String, Integer>());
    InitializeProcessor initializeProcessorTest = new InitializeProcessor(
        Paths.get(testDataPath, "courses.csv").toString(), courseAndClick);
    Assertions.assertTrue(
        this.initializeProcessor.toString().equals(initializeProcessorTest.toString()));
  }
}