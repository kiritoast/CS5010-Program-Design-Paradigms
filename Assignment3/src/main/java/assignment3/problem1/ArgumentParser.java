package assignment3.problem1;


import java.util.HashMap;
import java.util.Objects;

/**
 * class argument parser that takes args and store into hashmaps
 */
public class ArgumentParser {
  private final HashMap<String, String> argsMap = new HashMap<>();

  /**
   * constructor for argument parser
   * check if the command is valid
   * @param args args that was pass into main from configuration
   * @throws IllegalArgumentException throw exception if false command line
   */
  public ArgumentParser(String[] args) throws IllegalArgumentException {
    int i = 0;
    while (i < args.length) {
      String arg = args[i++];

      switch (arg) {
        case "--email":
          argsMap.put("--email", "true");
          break;

        case "--email-template":
          if (i < args.length) {
            argsMap.put("--email-template", args[i++]);
          } else {
            throw new IllegalArgumentException("Missing argument for --email-template");
          }
          break;

        case "--letter":
          argsMap.put("--letter", "true");
          break;

        case "--letter-template":
          if (i < args.length) {
            argsMap.put("--letter-template", args[i++]);
          } else {
            throw new IllegalArgumentException("Missing argument for --letter-template");
          }
          break;

        case "--output-dir":
          if (i < args.length) {
            argsMap.put("--output-dir", args[i++]);
          } else {
            throw new IllegalArgumentException("Missing argument for --output-dir");
          }
          break;

        case "--csv-file":
          if (i < args.length) {
            argsMap.put("--csv-file", args[i++]);
          } else {
            throw new IllegalArgumentException("Missing argument for --csv-file");
          }
          break;

        default:
          throw new IllegalArgumentException("Invalid argument: " + arg);
      }
    }

    if (!argsMap.containsKey("--output-dir")) {
      throw new IllegalArgumentException("Missing required argument: --output-dir");
    }

    if (!argsMap.containsKey("--csv-file")) {
      throw new IllegalArgumentException("Missing required argument: --csv-file");
    }

    if (argsMap.containsKey("--email") && !argsMap.containsKey("--email-template")) {
      throw new IllegalArgumentException("Missing argument for --email-template. Required if --email is used.");
    }

    if (argsMap.containsKey("--letter") && !argsMap.containsKey("--letter-template")) {
      throw new IllegalArgumentException("Missing argument for --letter-template. Required if --letter is used.");
    }
  }

  /**
   * getter for the argument maps
   * @return hashmap for arguments
   */
  public HashMap<String, String> getArguments() {
    return argsMap;
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
    ArgumentParser that = (ArgumentParser) o;
    return Objects.equals(argsMap, that.argsMap);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(argsMap);
  }
}
