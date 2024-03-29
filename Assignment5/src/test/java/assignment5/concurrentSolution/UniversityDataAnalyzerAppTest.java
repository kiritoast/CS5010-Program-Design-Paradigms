package assignment5.concurrentSolution;

import static org.junit.jupiter.api.Assertions.*;

import assignment5.sequentialSolution.CsvFileReader;
import java.io.File;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UniversityDataAnalyzerAppTest {

  private String[] argsNonThreshold;
  private String[] argsThreshold;

  @BeforeEach
  void setUp() {
    this.argsNonThreshold = new String[]{
        "DataSet" + File.separator + "ConcurrentCourseStudent" + File.separator + "non_threshold"};
    this.argsThreshold = new String[]{
        "DataSet" + File.separator + "ConcurrentCourseStudent" + File.separator + "threshold", "3"};
  }

  @Test
  void mainNonThreshold() {
    UniversityDataAnalyzerApp.main(argsNonThreshold);

    String summaryFile =
        "DataSet" + File.separator + "ConcurrentCourseStudent" + File.separator + "non_threshold"
            + File.separator + "summary" + File.separator + "AAA_2013J.csv";
    File file = new File(summaryFile);
    Assertions.assertEquals(true, file.exists());

    CsvFileReader csvFileReader = new CsvFileReader(summaryFile);
    String[] header = csvFileReader.readData();
    String[] content = csvFileReader.readData();
    Assertions.assertTrue(Arrays.equals(header, new String[]{"date", "sum_click"}));
    Assertions.assertTrue(Arrays.equals(content, new String[]{"-10", "6"}));
  }

  @Test
  void mainThreshold() {
    UniversityDataAnalyzerApp.main(argsThreshold);

    String summaryFile1 =
        "DataSet" + File.separator + "ConcurrentCourseStudent" + File.separator + "threshold"
            + File.separator + "summary" + File.separator + "AAA_2013J.csv";
    String summaryFile2 =
        "DataSet" + File.separator + "ConcurrentCourseStudent" + File.separator + "threshold"
            + File.separator + "summary" + File.separator + "AAA_2013A.csv";
    String activityFile =
        "DataSet" + File.separator + "ConcurrentCourseStudent" + File.separator + "threshold"
            + File.separator + "threshold" + File.separator + "activity-3.csv";

    File file1 = new File(summaryFile1);
    Assertions.assertEquals(true, file1.exists());
    File file2 = new File(summaryFile2);
    Assertions.assertEquals(true, file2.exists());

    CsvFileReader csvFileReader1 = new CsvFileReader(summaryFile1);
    String[] header1 = csvFileReader1.readData();
    String[] content1 = csvFileReader1.readData();
    Assertions.assertTrue(Arrays.equals(header1, new String[]{"date", "sum_click"}));
    Assertions.assertTrue(Arrays.equals(content1, new String[]{"-10", "6"}));
    CsvFileReader csvFileReader2 = new CsvFileReader(summaryFile2);
    String[] header2 = csvFileReader2.readData();
    String[] content2 = csvFileReader2.readData();
    Assertions.assertTrue(Arrays.equals(header2, new String[]{"date", "sum_click"}));
    Assertions.assertTrue(Arrays.equals(content2, new String[]{"-10", "1"}));

    CsvFileReader csvFileReader3 = new CsvFileReader(activityFile);
    String[] header3 = csvFileReader3.readData();
    String[] content3 = csvFileReader3.readData();
    Assertions.assertTrue(
        Arrays.equals(header3, new String[]{"module_presentation", "date", "total_clicks"}));
    Assertions.assertTrue(Arrays.equals(content3, new String[]{"AAA_2013J", "-10", "6"}));
  }
}