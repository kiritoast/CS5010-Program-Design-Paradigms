package assignment4.problem1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;


class InputProcessorTest {

  private InputProcessor inputProcessor;
  private InputProcessor inputProcessorTrue;

  private File fileDirectory;
  private File fileDirectoryNull;

  private final PrintStream originalOut = System.out;
  private final InputStream originalIn = System.in;

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


  @BeforeEach
  void setUp() {

    fileDirectory = new File("Grammar"+ File.separator + "SingleJsonFile");
    fileDirectoryNull = new File("Grammar" + File.separator + "EmptyFolder");

    inputProcessor = new InputProcessor(fileDirectory);
    inputProcessorTrue = new InputProcessor(fileDirectory);

    System.setOut(new PrintStream(outContent));

  }

  @AfterEach
  public void restoreStreams() {
    System.setIn(originalIn);
    System.setOut(originalOut);
  }

  @Test
  void testQuit() throws IOException {
    System.setIn(new ByteArrayInputStream("q".getBytes()));
    inputProcessor.process();
    assertEquals("""
                
        Loading grammars...
        
        The following grammars are available:
        1. Single Poem Generator
        
        Which would you like to use?  (q to quit)
        
        Sentence Generator App Closed.
        """.replace("\n", "").replace("\r", ""), outContent.toString().replace("\n", "").replace("\r", ""));
  }

  @Test
  void testCheckBooleanOption() throws IOException {
    System.setIn(new ByteArrayInputStream("1\nr\nq\nq\n".getBytes()));

    inputProcessor.process();
    assertEquals("""
                  
        Loading grammars...
        
        The following grammars are available:
        1. Single Poem Generator
        
        Which would you like to use?  (q to quit)
        
        The waves sigh warily tonight.
        
        Would you like another? (y/n)
        
        Invalid Input. Please input again:  (q to quit)
        
        The following grammars are available:
        1. Single Poem Generator
        
        Which would you like to use?  (q to quit)
        
        Sentence Generator App Closed.
        """.replace("\n", "").replace("\r", ""), outContent.toString().replace("\n", "").replace("\r", ""));

  }

  @Test
  void testCorrectInstruction() throws IOException {
    System.setIn(new ByteArrayInputStream("12\nwwe\nq\n1\ny\nn\nq\n".getBytes()));

    inputProcessor.process();

    assertEquals("""
                  
        Loading grammars...
        
        The following grammars are available:
        1. Single Poem Generator
        
        Which would you like to use?  (q to quit)
        
        Invalid Index. Please input again:  (q to quit)
        
        Invalid Index. Please input again:  (q to quit)
        
        The following grammars are available:
        1. Single Poem Generator
        
        Which would you like to use?  (q to quit)
        
        The waves sigh warily tonight.
        
        Would you like another? (y/n)
        
        The waves sigh warily tonight.
        
        Would you like another? (y/n)
        
        The following grammars are available:
        1. Single Poem Generator
        
        Which would you like to use?  (q to quit)
        
        Sentence Generator App Closed.
        """.replace("\n", "").replace("\r", ""), outContent.toString().replace("\n", "").replace("\r", ""));
  }

  @Test
  void checkNoneJsonFile() throws IOException {
    System.setIn(new ByteArrayInputStream("q\n".getBytes()));

    InputProcessor inputProcessorWrong = new InputProcessor(fileDirectoryNull);
    inputProcessorWrong.process();
    assertEquals("""
                
        Loading grammars...
                
        The following grammars are available:
                
        Which would you like to use?  (q to quit)
                
        Sentence Generator App Closed.
        """.replace("\n", "").replace("\r", ""), outContent.toString().replace("\n", "").replace("\r", ""));

  }


  @Test
  void testEquals() throws IOException {

    System.setIn(new ByteArrayInputStream("1\nr\nq\nq\n".getBytes()));
    inputProcessor.process();
    System.setIn(new ByteArrayInputStream("1\nr\nq\nq\n".getBytes()));
    inputProcessorTrue.process();
    InputProcessor inputProcessorFalse = new InputProcessor(fileDirectoryNull);

    assertTrue(inputProcessor.equals(inputProcessor));
    assertTrue(inputProcessor.equals(inputProcessorTrue));
    assertFalse(inputProcessor.equals(inputProcessorFalse));

    assertFalse(inputProcessor.equals(null));
    assertFalse(inputProcessor.equals(new Object()));

  }

  @Test
  void testHashCode() {
    Assertions.assertNotEquals(100, inputProcessor.hashCode());
  }

  @Test
  void testToString() {
    Assertions.assertNotEquals("test", inputProcessor.toString());
  }
}