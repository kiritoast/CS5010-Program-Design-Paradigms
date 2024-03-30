package assignment3.problem1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class that take a txt template and create arraylist fillin, which need to be replaced by user data
 * and template pieces, which are seperated by each fillin data
 */
public class TemplateReader {

  private String templatePath;
  private String template;
  private List<String> templatePieces;
  private List<String> fillIn;


  /**
   * constructor for template reader
   * @param templatePath path for the template
   * @throws IOException throw exception if there is no file
   */
  public TemplateReader(String templatePath) throws IOException {
    this.templatePath = templatePath;
    this.template =  Files.readString(Paths.get(templatePath));
    this.fillIn = parseTemplate(template);
  }

  /**
   *  parse the template and store into user info and template pieces
   * @param template String template that store template
   * @return arraylist of user info
   */
  private List<String> parseTemplate(String template){
    String regex = "\\[\\[(.)*?\\]\\]";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(template);
    List<String> userInfo = new ArrayList<>();
    while (matcher.find()) {
      userInfo.add(matcher.group().replace("[[", "").replace("]]", ""));
    }
    this.templatePieces = Arrays.stream(pattern.split(this.template)).toList();
    return userInfo;
  }

  /**
   * get fill in arraylist
   * @return the fillin
   */
  public List<String> getFillIn() {
    return fillIn;
  }
  /**
   * get the template string
   * @return the template String
   */
  public String getTemplate() {
    return template;
  }

  /**
   * get template pieces
   * @return template pieces arraylist
   */
  public List<String> getTemplatePieces() {
    return templatePieces;
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
    TemplateReader that = (TemplateReader) o;
    return Objects.equals(templatePath, that.templatePath) && Objects.equals(
        template, that.template) && Objects.equals(templatePieces, that.templatePieces)
        && Objects.equals(fillIn, that.fillIn);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(templatePath, template, templatePieces, fillIn);
  }
}
