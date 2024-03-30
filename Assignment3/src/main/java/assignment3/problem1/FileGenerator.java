package assignment3.problem1;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;

/**
 * class that combine template and csv data into a string, also output the file into
 * required path
 */
public class FileGenerator {
  private static final String PATH_PREFIX = "/Users/jijim/Documents/5010/Student_jijiming/assignment3/";

  private CSVReader csvReader;
  private HashMap<String, String> arguments;
  private String Template;
  private String outputPath;
  private TemplateReader templateReader;

  /**
   * constructor for file generator that runs in main
   * @param csvReader class csvReader that take a csv file
   * @param templateReader class template reader that take a txt template
   * @param arguments arguments that was taken from the command line
   */
  public FileGenerator(CSVReader csvReader, TemplateReader templateReader, HashMap <String, String> arguments) {
    this.csvReader = csvReader;
    this.arguments = arguments;
    this.templateReader = templateReader;
    this.Template = templateReader.getTemplate();
    this.outputPath = arguments.get("--output-dir");
  }

  /**
   * take user file and combine it with template piece and make into one string
   * @param UserInfo user info that takes from the csv file
   * @return the string that after each template is done and ready to output
   */
  public String generateOneFile(HashMap<String, String> UserInfo){
    int length = Math.max(templateReader.getTemplatePieces().size(), templateReader.getFillIn().size());
    StringBuilder fileString = new StringBuilder();
    for(int i = 0; i < length; i++){
      if(i < templateReader.getTemplatePieces().size()){
        fileString.append(templateReader.getTemplatePieces().get(i));
      }
      if(i < templateReader.getFillIn().size()){
        fileString.append(UserInfo.get(templateReader.getFillIn().get(i)));
      }
    }
    return fileString.toString();
  }

  /**
   * created multiple files depends on the number of rows in the csv file
   * @throws IOException exception if no file is created
   */
  public void outputFiles() throws IOException {
    File directory = new File(outputPath);
    if (!directory.exists()) {
      directory.mkdirs();
    }
    for(int i = 0; i < csvReader.length(); i ++){
      HashMap<String,String> userInfo = csvReader.getUserInfo(i);
      String outputString = generateOneFile(userInfo);
      Files.writeString(Path.of(PATH_PREFIX+outputPath+"/"+userInfo.get("first_name")+"_"+userInfo.get("last_name")+".txt"), outputString, UTF_8);
    }
  }

  /**
   * set the output path for files
   * @param outputPath string that contain info about output path
   */
  public void setOutputPath(String outputPath) {
    this.outputPath = outputPath;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileGenerator that = (FileGenerator) o;
    return Objects.equals(csvReader, that.csvReader) && Objects.equals(arguments,
        that.arguments) && Objects.equals(Template, that.Template)
        && Objects.equals(outputPath, that.outputPath) && Objects.equals(
        templateReader, that.templateReader);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(csvReader, arguments, Template, outputPath, templateReader);
  }
}
