package assignment3.problem1;

import java.io.IOException;
import java.util.HashMap;

/**
 * main function that generate messages depends on templates that could be either
 * letter or eamil. The data is given from a csv file. it will also ouput the file to
 * the given directory. The user give commands to provided template type, file path.
 */
public class Main {
  private static final String EMAIL_TEMPLATE_OPTION = "--email-template";
  private static final String LETTER_OPTION = "--letter";
  private static final String LETTER_TEMPLATE_OPTION = "--letter-template";
  private static final String CSV_FILE_OPTION = "--csv-file";
  private static final String PATH_PREFIX = "/Users/jijim/Documents/5010/Student_jijiming/assignment3/";

  /**
   * main function to call to create a file based on the given form
   * @param args arguments that given by the user
   * @throws IOException exception will be thrown
   */
  public static void main(String[] args) throws IOException {
    try{
      System.out.println(args);
      ArgumentParser parser = new ArgumentParser(args);
      HashMap<String, String> arguments = parser.getArguments();

      String csvPath = arguments.get(CSV_FILE_OPTION);
      CSVReader csvReader = new CSVReader(PATH_PREFIX+csvPath);

      // test if its letter or email
      String tempPath = PATH_PREFIX;
      if(arguments.get(LETTER_OPTION) == "true"){
        tempPath = arguments.get(LETTER_TEMPLATE_OPTION);
      }else{
        tempPath = arguments.get(EMAIL_TEMPLATE_OPTION);
      }
      TemplateReader tem = new TemplateReader(PATH_PREFIX + tempPath);
      FileGenerator fileGenerator = new FileGenerator(csvReader,tem,arguments);
      fileGenerator.outputFiles();

    } catch (Exception e) {
      System.err.println(e.getMessage());
      printUsage();
    }

  }

  /**
   * print correct usage when the argument is wrong
   */
  private static void printUsage() {
    System.out.println("Usage:");
    System.out.println("--email                        only generate email messages");
    System.out.println("--email-template <file>       accept a filename that holds the email template.");
    System.out.println("--letter                       only generate letters");
    System.out.println("--letter-template <file>      accept a filename that holds the letter template.");
    System.out.println("--output-dir <path>           accept the name of a folder, all output is placed in this folder");
    System.out.println("--csv-file <path>             accept the name of the csv file to process");
  }

}
