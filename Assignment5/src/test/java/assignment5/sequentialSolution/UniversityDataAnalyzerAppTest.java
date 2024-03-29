package assignment5.sequentialSolution;

import java.io.File;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UniversityDataAnalyzerAppTest {

  private String[] args;

  @BeforeEach
  void setUp() {
    args = new String[]{"DataSet" + File.separator + "SeqSingleCourseStudent"};
  }

  @Test
  void mainFileGenerated() {
    UniversityDataAnalyzerApp.main(args);
    File file = new File(
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator + "summaryTest"
            + File.separator + "AA_2013A.csv");
    Assertions.assertEquals(true, file.exists());
  }

  @Test
  void mainVerifyOutputHeader() {
    UniversityDataAnalyzerApp.main(args);
    CsvFileReader csvFileReader = new CsvFileReader(
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator + "summaryTest"
            + File.separator + "AA_2013A.csv");
    String[] header = new String[]{"date", "sum_click"};
    Assertions.assertEquals(true, Arrays.equals(header, csvFileReader.readData()));
    csvFileReader.closeFile();
  }

  @Test
  void mainVerifyOutputData() {
    UniversityDataAnalyzerApp.main(args);
    CsvFileReader csvFileReader = new CsvFileReader(
        "DataSet" + File.separator + "SeqSingleCourseStudent" + File.separator + "summaryTest"
            + File.separator + "AA_2013A.csv");
    csvFileReader.readData();//skip header
    String[] data = new String[]{"-10", "6"};
    Assertions.assertEquals(true, Arrays.equals(data, csvFileReader.readData()));
    csvFileReader.closeFile();
  }
}