package assignment4.problem1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 * Represents the json reader which helps to read a json file
 */
public class JsonReader {

  private JSONObject jsonObject;

  /**
   * Creates a new instance of json reader and parse the file content
   *
   * @param filePath file path
   */
  public JsonReader(String filePath) {
    FileReader fileReader = openFile(filePath);
    setJsonObject(fileReader);
    closeFile(fileReader);
  }

  /**
   * Read the data from json object given the key
   *
   * @param key key value
   * @return value corresponding to given key
   */
  public String readValue(String key) {
    return (String) this.jsonObject.get(getCaseInsensitiveKey(key));
  }

  /**
   * Read the data array from json object given the key
   *
   * @param key key value
   * @return array value corresponding to given key
   */
  public List<String> readArrayValue(String key) {
    List<String> valueList = new ArrayList<>();

    JSONArray values = (JSONArray) this.jsonObject.get(getCaseInsensitiveKey(key));
    if (values != null) {
      for (Object value : values) {
        valueList.add((String) value);
      }
    }

    return (valueList);
  }

  /**
   * Open the file given the file path
   *
   * @param filePath file path
   * @return file reader
   */
  private FileReader openFile(String filePath) {
    FileReader fileReader;
    try {
      fileReader = new FileReader(filePath);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    return fileReader;
  }

  /**
   * Parse the file reader and store the json object
   *
   * @param fileReader file reader
   */
  private void setJsonObject(FileReader fileReader) {
    JSONParser parser = new JSONParser();
    try {
      this.jsonObject = (JSONObject) parser.parse(fileReader);
    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * This method is used to find the case-insensitive version of given key
   *
   * @param key key value
   * @return case-insensitive key
   */
  private Object getCaseInsensitiveKey(String key) {
    Stream<?> stream = this.jsonObject.keySet().stream();
    Optional<?> optional = stream.filter(e -> e != null && e.toString().equalsIgnoreCase(key))
        .findFirst();

    return optional.orElse(null);
  }

  /**
   * Close the file
   *
   * @param fileReader file reader
   */
  private void closeFile(FileReader fileReader) {
    try {
      fileReader.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * This method is used to compare two instances of json reader class
   *
   * @param o instance to be compared
   * @return true or false
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonReader that = (JsonReader) o;
    return Objects.equals(this.jsonObject, that.jsonObject);
  }

  /**
   * This method is used to generate the hash code of json reader class
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.jsonObject);
  }

  /**
   * This method is used to generate the string representation of json reader class
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return "JsonReader{" +
        "jsonObject=" + this.jsonObject +
        '}';
  }
}
