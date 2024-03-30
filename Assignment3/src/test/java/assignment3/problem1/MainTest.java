package assignment3.problem1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
  private ByteArrayOutputStream outputStream;

  @BeforeEach
  void setUp() {
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
  }

  @AfterEach
  void tearDown() {
    System.setOut(System.out);
  }

  @Test
  void main_givenValidArguments_generatesFiles() throws IOException {
    String[] args = {
        "--email",
        "--email-template",
        "src/main/resources/email-template.txt",
        "--output-dir",
        "src/main/resources/output",
        "--csv-file",
        "src/main/resources/insurance-company-members.csv"
    };
    String[] args2 = {
        "--letter",
        "--letter-template",
        "src/main/resources/letter-template.txt",
        "--output-dir",
        "src/main/resources/output",
        "--csv-file",
        "src/main/resources/insurance-company-members.csv"
    };

    Main.main(args);
    Main.main(args2);

    String[] expectedOutputs = {
        "Generating email messages from email-template.txt using members.csv...\n",
        "Creating output/email_1.txt...\n",
        "Creating output/email_2.txt...\n",
        "Done.\n"
    };

    String[] actualOutputs = outputStream.toString().split(System.lineSeparator());

    assertEquals(2, actualOutputs.length);


    assertTrue(new File("src/main/resources/output/Abel_Maclead.txt").exists());
    assertTrue(new File("src/main/resources/output/Adell_Lipkin.txt").exists());
  }

  @Test
  void main_givenInvalidArguments_printsUsage() throws IOException {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Main.main(new String[]{"--invalid-argument"});

    String expectedOutput = ""
        + "Usage:\n" +
        "--email                        only generate email messages\n" +
        "--email-template <file>       accept a filename that holds the email template.\n" +
        "--letter                       only generate letters\n" +
        "--letter-template <file>      accept a filename that holds the letter template.\n" +
        "--output-dir <path>           accept the name of a folder, all output is placed in this folder\n" +
        "--csv-file <path>             accept the name of the csv file to process\n";

    assertEquals(expectedOutput, expectedOutput);
  }
}
