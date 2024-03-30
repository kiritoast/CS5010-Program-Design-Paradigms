package assignment3.problem1;

import java.io.*;
import java.util.*;

/**
 * Class csv reader that take a csv file and recode the data into arraylist of strings
 *
 */
public class CSVReader {

  private String csvPath;
  private List<String> dataHeader;
  private List<List<String>> data;

  /**
   * constructor of the csv reader, turn data into two arraylist
   * @param csvPath where the csv stores
   * @throws FileNotFoundException file not found exception
   */

  public CSVReader(String csvPath) throws FileNotFoundException {
    this.csvPath = csvPath;
    this.data = new ArrayList<>();
    this.dataHeader = new ArrayList<>();
    Scanner scanner = new Scanner(new File(csvPath));
    boolean isHeader = true;
    while (scanner.hasNext()) {
      if (isHeader) {
        isHeader = false;
        this.dataHeader = parseLine(scanner.nextLine());
      } else {
        List<String> currentLine = parseLine(scanner.nextLine());
        if (currentLine.size() == this.dataHeader.size()) {
          this.data.add(currentLine);
        }
      }
    }
    scanner.close();
  }

  /**
   *  get the length for the data, shows how many total users.
   * @return size for data array
   */
  public int length(){
    return this.data.size();
}

  /**
   *
   * @param line each csv line that needs to be parser
   * @return arraylist for data inside each row
   */
  private List<String> parseLine(String line) {
    return Arrays.stream(line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"))
        .map((str) -> str.replace("\"", ""))
        .toList();
  }

  /**
   * get one user information in the form of hashmap, key is datahead and value is data
   * @param userNumber index for which user need to be tested
   * @return hashmap for user info
   */
  public HashMap<String, String> getUserInfo(int userNumber){
    HashMap<String, String> user = new HashMap<>();
    Iterator<String> itDataHeader = this.dataHeader.iterator();
    Iterator<String> itData = data.get(userNumber).iterator();
    while (itDataHeader.hasNext() && itData.hasNext()) {
      user.put(itDataHeader.next(), itData.next());
    }
    return user;
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
    CSVReader csvReader = (CSVReader) o;
    return Objects.equals(csvPath, csvReader.csvPath) && Objects.equals(
        dataHeader, csvReader.dataHeader) && Objects.equals(data, csvReader.data);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(csvPath, dataHeader, data);
  }
}
