package assignment5.concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataSetAnalyzerTest {

  private DataSetAnalyzer dataSetAnalyzer;
  private DataSetAnalyzer dataSetAnalyzerThreshold;

  @BeforeEach
  void setUp() {
    String courseFilePath =
        "src" + File.separator + "test" + File.separator + "java" + File.separator + "assignment5"
            + File.separator + "TestDataSet" + File.separator + "courses.csv";
    String studentFilePath =
        "src" + File.separator + "test" + File.separator + "java" + File.separator + "assignment5"
            + File.separator + "TestDataSet" + File.separator + "studentVle.csv";
    dataSetAnalyzer = new DataSetAnalyzer(courseFilePath, studentFilePath);
    dataSetAnalyzerThreshold = new DataSetAnalyzer(courseFilePath, studentFilePath, "10");
  }

  @Test
  void process() {
    dataSetAnalyzer.process();
  }

  @Test
  void processThreshold() {
    dataSetAnalyzerThreshold.processThreshold();
  }


  @Test
  void testEquals() {
    String courseFilePath = "testNullCourse.csv";
    String studentFile = "testNullStudent.csv";
    DataSetAnalyzer dataSetAnalyzer1 = new DataSetAnalyzer(courseFilePath, studentFile, "10");
    DataSetAnalyzer dataSetAnalyzer2 = new DataSetAnalyzer(courseFilePath, studentFile, "10");
    DataSetAnalyzer dataSetAnalyzer3 = new DataSetAnalyzer(courseFilePath, studentFile, "30");

    Assertions.assertTrue(dataSetAnalyzer1.equals(dataSetAnalyzer2));
    Assertions.assertFalse(dataSetAnalyzer3.equals(dataSetAnalyzer2));

    Assertions.assertTrue(dataSetAnalyzer.equals(dataSetAnalyzer));

    Assertions.assertFalse(dataSetAnalyzerThreshold.equals(dataSetAnalyzer));
    Assertions.assertFalse(dataSetAnalyzer.equals(null));


  }

  @Test
  void testHashCode() {
    Assertions.assertNotEquals(1, dataSetAnalyzer.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertNotEquals("test", dataSetAnalyzer.toString());
  }
}