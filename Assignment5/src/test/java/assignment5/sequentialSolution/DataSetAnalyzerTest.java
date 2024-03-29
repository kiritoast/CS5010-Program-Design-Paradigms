package assignment5.sequentialSolution;

import java.io.File;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataSetAnalyzerTest {

  private DataSetAnalyzer dataSetAnalyzer;

  @BeforeEach
  void setUp() {
    dataSetAnalyzer = new DataSetAnalyzer(
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator + "courses.csv",
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator + "studentVle.csv");
  }

  @Test
  void processOutputFileGenerated() {
    dataSetAnalyzer.process();
    File file = new File(
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator + "summaryTest"
            + File.separator + "AA_2013A.csv");
    Assertions.assertEquals(true, file.exists());
  }

  @Test
  void verifyOutputContentHeader() {
    dataSetAnalyzer.process();
    CsvFileReader csvFileReader = new CsvFileReader(
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator + "summaryTest"
            + File.separator + "AA_2013A.csv");
    String[] header = new String[]{"date", "sum_click"};
    Assertions.assertEquals(true, Arrays.equals(header, csvFileReader.readData()));
    csvFileReader.closeFile();
  }

  @Test
  void verifyOutputContentData() {
    dataSetAnalyzer.process();
    CsvFileReader csvFileReader = new CsvFileReader(
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator + "summaryTest"
            + File.separator + "AA_2013A.csv");
    csvFileReader.readData();//skip header
    String[] data = new String[]{"-10", "6"};
    Assertions.assertEquals(true, Arrays.equals(data, csvFileReader.readData()));
    csvFileReader.closeFile();
  }

  @Test
  void testEquals() {
    Assertions.assertEquals(true, dataSetAnalyzer.equals(dataSetAnalyzer));
  }

  @Test
  void testEqualsNull() {
    Assertions.assertEquals(false, dataSetAnalyzer.equals(null));
  }

  @Test
  void testEqualsDiff() {
    Assertions.assertEquals(false, dataSetAnalyzer.equals("null"));
  }

  @Test
  void testEqualsDiffVal() {
    Assertions.assertEquals(false, dataSetAnalyzer.equals(new DataSetAnalyzer("a1", "b1")));
  }

  @Test
  void testEqualsSameVal() {
    Assertions.assertEquals(true, dataSetAnalyzer.equals(new DataSetAnalyzer(
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator + "courses.csv",
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator
            + "studentVle.csv")));
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