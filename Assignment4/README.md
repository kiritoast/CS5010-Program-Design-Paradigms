# Assignment 4 - Sentence Generator Application

<!-- TOC -->
* [Assignment 4 - Sentence Generator Application](#assignment-4---sentence-generator-application)
  * [Function](#function)
  * [How to Launch the App](#how-to-launch-the-app)
  * [Description of Key Classes and Methods](#description-of-key-classes-and-methods)
  * [Description of the Project](#description-of-the-project)
    * [UI Design / Operating Steps](#ui-design--operating-steps)
    * [Assumption](#assumption)
  * [Usage Example](#usage-example)
<!-- TOC -->

## Function

The project aims to build an interactive application to finish the following tasks:
* read and store the file information and non-terminals from grammar files
* expend and generate sentences based on non-terminals
* implement a UI to interactive with users


## How to Launch the App

The input argument should be a valid directory in which contains several grammar files with json suffix. 
    
This project can be run from Intellij or in terminal:
* In Intellij configuration, set ```Main class``` as ```assignment4.problem1.SentenceGeneratorApp```; set ```Program arguments``` as the path of source directory. Then, run the configuration directly.
   

* In terminal:
  1. Run task ```doAll``` in the build.gradle file, and the build folder will be generated automatically. The Javadoc and test report can be found in build folder.
  2. Change the current directory to the root directory of this project.
        ```
        cd <project_path>
        ```
  3. Launch this project via gradle command with a valid source directory. For example:
     ```
     ./gradlew run --args='<the path of source directory>' -q --console=plain
     ```
        ```<the path of source directory>``` should be replaced by the path of valid source directory which contains grammar files.
    
  
* One choice of the path of source directory is ```Grammar``` under the project root directory which contains several valid and invalid grammar files for testing purposes.

## Description of Key Classes and Methods
1. [SentenceGeneratorApp.java](src%2Fmain%2Fjava%2Fassignment4%2Fproblem1%2FSentenceGeneratorApp.java)
   
   The launcher of Sentence Generator App which can handle the input argument with help of validateArgument method.
   1. validateArgument() method: Check the input argument is a valid folder path
      * parameter: the input arguments
      * return: the folder path as File type
   2. main() method: The main method to launch the Sentence Generator App.
   

2. [InputProcessor.java](src%2Fmain%2Fjava%2Fassignment4%2Fproblem1%2FInputProcessor.java)

   This class aims to read user's option and generate sentences based on user's option.
   1. populateOptions() method: Read file information from input directory, and store them in the grammarTitles and filePaths fields
   2. processOption(String option): Generate a sentence based on user's input option which is an index of grammar file, and ask the user to repeat or not.
      * parameter: the user's option
   3. repeatCurrentGrammar() method: Ask user to re-generate a sentence from the previous chosen grammar file.
   

3. [SentenceGenerator.java](src%2Fmain%2Fjava%2Fassignment4%2Fproblem1%2FSentenceGenerator.java)

   The sentence generator which helps to generate sentence from the given grammar.
   1. generateSentence(String input) method: Generates a sentence from the given input. This method will match the non-terminals recursively until there is no non-terminals in sentence.
      * parameter: start of the sentence
      * return: final generated sentence
   2. formatSentence(String sentence)  method: Format the generated sentence to make it more readable
      * parameter: generated sentence
      * return: formatted sentence
   

4. [JsonReader.java](src%2Fmain%2Fjava%2Fassignment4%2Fproblem1%2FJsonReader.java)

   The json reader which helps to read a json file.
   1. JsonReader(String filePath) method: the constructor to parse the grammar file into a json object
      * paramater: the path of grammar file
   2. readValue(String key) method: read the data from json object given the key.
      * paramater: the key value
      * return: the value corresponding to given key
   3. readArrayValue(String key) method: read the data array from json object given the key.
      * paramater: the key value
      * return: the array value corresponding to given key

## Description of the Project
### UI Design / Operating Steps
1. Launch the app with a valid directory.

   The console will display a loading message and the grammar titles with their indexes which is the home page. Then, the user will be asked to choose an index to generate a sentence based on the corresponding grammar file.
    ```
    Loading grammars...

    The following grammars are available:
    1. Single Poem Generator
    2. Insult Generator
    3. Single Poem Generator Fail
    4. Poem Generator
    5. Term Paper Generator

    Which would you like to use?  (q to quit)
    ```
2. Choose an index to generate a sentence or Input ```q``` to close the app.

   The console will display the automate-generated sentence, and ask the user whether to generate a sentence from the same grammar file.

    ```
    Which would you like to use?  (q to quit)
    1

    The waves sigh warily tonight.

    Would you like another? (y/n)
   ```

   2.1 If the user does not input a valid index, the app will ask the user to input again. Input ```q``` to return to the home page
    ```
    Which would you like to use?  (q to quit)
    -1
   
    Invalid Index. Please input again:  (q to quit)
    ```
3. Decide whether to re-generate a sentence from the same grammar file

   Input ```y``` to re-generate; input ```n``` to return to the home page.
    ```
    Would you like another? (y/n)
    y

    The waves sigh warily tonight.
    ```
   or
    ```
    Would you like another? (y/n)
    n

    The following grammars are available:
    1. Single Poem Generator
    ......
    ```
   3.1 If the user does not input ```y``` or ```n```, the app will ask the user to input again. Input ```q``` to return to the home page
    ```
    Would you like another? (y/n)
    1

    Invalid Input. Please input again:  (q to quit)
    ```
   
### Assumption
   1. The user may launch the app with an invalid directory path as input argument.
      
      To solve it, we will throw the corresponding exception that display the error message. 
   

   2. The user may input invalid or wrong option. 

      To solve it, the app will continue to ask for an input until this input is valid. In addition, we also design the escape flag ```q``` to help user return to the home page
   

   3. The user may want to re-generate a sentence based on the previous option. 
      
      To solve it, the app will ask user whether to re-generate. In code, if the option is the same as the previouus one, the app can reuse the previous sentence generator and have no need to read the file again.
   

   4. The source directory may contain invalid grammar files

      To solve it, the app will only open and read the file with ```json``` suffix.


## Usage Example
This application can be invoked by running the main method in SentenceGeneratorApp.
It is expecting the file directory path to be passed as an argument, where the set of grammar files are available.
Sample usage is given below.

Once the file directory path is given, the application will display the set of grammar files available at that location and allows the user to choose the grammar, from which sentence needs to be generated. This process continues until user requests to quit the program.
In case there are any grammars with non-terminals that are not defined, the program may fail to generate a sentence and appropriate message will be shown.

Usage :
< relative or absolute path of file directory >

Example :
Grammar

Sample UI :
```
Loading grammars...

The following grammars are available:
1. Single Poem Generator
2. Insult Generator
3. Single Poem Generator Fail
4. Poem Generator
5. Term Paper Generator

Which would you like to use?  (q to quit)
4

The big yellow flowers sigh grumpily tonight.

Would you like another? (y/n)
y

The big yellow flowers die warily tonight.

Would you like another? (y/n)
y

The waves die warily tonight.

Would you like another? (y/n)
n

The following grammars are available:
1. Single Poem Generator
2. Insult Generator
3. Single Poem Generator Fail
4. Poem Generator
5. Term Paper Generator

Which would you like to use?  (q to quit)
3

Program couldn't generate a message as non-terminal 'adverb' is not defined.

Would you like another? (y/n)
n

The following grammars are available:
1. Single Poem Generator
2. Insult Generator
3. Single Poem Generator Fail
4. Poem Generator
5. Term Paper Generator

Which would you like to use?  (q to quit)
q

Sentence Generator App Closed.
```
