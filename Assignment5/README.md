# Assignment 5 - Course and studentVle Data Analyzer

<!-- TOC -->
* [Assignment 5 - Course and studentVle Data Analyzer](#assignment-5---course-and-studentvle-data-analyzer)
  * [Function](#function)
  * [How to Launch the App](#how-to-launch-the-app)
  * [Output Structure](#output-structure)
  * [Description of Key Classes and Methods](#description-of-key-classes-and-methods)
    * [Helper classes](#helper-classes)
    * [Class for reading course file by using thread](#class-for-reading-course-file-by-using-thread)
    * [Classes for reading studentVle file in parallel](#classes-for-reading-studentvle-file-in-parallel)
    * [Classes for writing summary files in parallel](#classes-for-writing-summary-files-in-parallel)
    * [Classes for processing threshold part in parallel](#classes-for-processing-threshold-part-in-parallel)
  * [Assumption](#assumption)
<!-- TOC -->

## Function

The project aims to build an data analyzer to finish the following tasks:
* sequential solution:
  * read the course file, and initialize the key of the collection map with the course name
  * read the studentVle file, and set the value of the collection map
  * write the collection map to summary files
* concurrent solution:
  * read the course file, and initialize the key of the collection map with the course name
  * read the studentVle file, and set the value of the collection map in parallel
  * write the collection map to summary files in parallel
  * In addition, if the threshold option is given, the program will read all the summary files from the previous step, and generate an activity file that only contains valid courses

## How to Launch the App
This project can be run from Intellij by running the configuration 
1. Select ```Run | Edit Configurations``` from the main menu
  

2. In the ```Run/Debug Configuration``` dialog, click the ```Add New Configuration``` and choose ```Application``` 
  

3. Rename the current configuration and set the fields as followed:
   * The setting for sequential solution
     * SDK: ```Java 17```
     * Module: ```Assignment5.main```
     * Main class: ```assignment5.sequentialSolution.UniversityDataAnalyzerApp```
     * Program arguments: \<the source directory>
   * The setting for concurrent solution without threshold argument
     * SDK: ```Java 17```
     * Module: ```Assignment5.main```
     * Main class: ```assignment5.concurrentSolution.UniversityDataAnalyzerApp```
     * Program arguments: \<the source directory>
   * The setting for concurrent solution with threshold argument
     * SDK: ```Java 17```
     * Module: ```Assignment5.main```
     * Main class: ```assignment5.concurrentSolution.UniversityDataAnalyzerApp```
     * Program arguments: \<the source directory> \<threshold>
   * p.s. \<the source directory> is the source directory which contains the course and studentVle files; \<threshold> is the threshold used to filter the valid courses.
  

4. Run the configuration from the main menu directly


## Output Structure
1. Sequential solution:  
   Within the input directory, a summary folder will be generated which contains all the summary files  
  

2. Concurrent solution without threshold:  
   Within the innput directory, a summary folder will be generated which contains all the summary files
  

3. Concurrent solution with threshold:  
   Within the input directory, a summary folder will be generated which contains all the summary files  
   Within the input directory, a threshold folder will be generated which contains the activity-threshold file
    
* It is worth mentioning that the summary/threshold folder will be re-generated every time when runs the program.

## Description of Key Classes and Methods

### Helper classes
1. [CsvFileReader.java](src%2Fmain%2Fjava%2Fassignment5%2FsequentialSolution%2FCsvFileReader.java)

   The helper CSV file reader
   1. CsvFileReader: the constructor to initialize the file reader and the csv reader
      * parameter: the source file path
   2. readData: return the content of the next line in the source file
      * return: the content of next line as String[]
   3. closeFile: close the file reader and the csv reader
  

2. [CsvFileWriter.java](src%2Fmain%2Fjava%2Fassignment5%2FsequentialSolution%2FCsvFileWriter.java)

   The helper CSV file writer
   1. CsvFileWriter: the constructor to initialize the file reader and the csv reader
      * parameter: the output file path
   2. writeData: write the content to the end of output file
      * parameter: the content to write
   3. closeFile: close the file reader and the csv reader

### Class for reading course file by using thread
1. [InitializeProcessor.java](src%2Fmain%2Fjava%2Fassignment5%2FconcurrentSolution%2FInitializeProcessor.java)

   The runnable class to read the course file
   1. run: This method can be invoked to read the information in the course file to initialize a concurrent map in which the key is the course, and the value is an empty concurrent map.

### Classes for reading studentVle file in parallel
1. [CSVConcurrentReader.java](src%2Fmain%2Fjava%2Fassignment5%2FconcurrentSolution%2FCSVConcurrentReader.java)

   The launcher to read the studentVle file
   1. readCSVFileToMap: read the information from source file into the concurrent map with the help of one producer threads and multiple consumer threads. All threads are running in parallel.
  

2. [ReaderProducer.java](src%2Fmain%2Fjava%2Fassignment5%2FconcurrentSolution%2FReaderProducer.java)

   The producer for reading the studentVle file which implements the Runnable interface
   1. run: This method can be invoked to read the course information from the source file path and then store it to the buffer queue.
  

3. [ReaderConsumer.java](src%2Fmain%2Fjava%2Fassignment5%2FconcurrentSolution%2FReaderConsumer.java)

   The consumer for reading the studentVle file which implements the Runnable interface
   1. run: This method can be invoked to get the course information from the buffer queue, then store it to the concurrent map.

### Classes for writing summary files in parallel
1. [CSVConcurrentWriter.java](src%2Fmain%2Fjava%2Fassignment5%2FconcurrentSolution%2FCSVConcurrentWriter.java)

   The launcher to write the summary files
   1. writeOutputFiles: generate multiple threads which will create the output summary files and write the content.
  

2. [WriterProcessor.java](src%2Fmain%2Fjava%2Fassignment5%2FconcurrentSolution%2FWriterProcessor.java)

   The runnable class helps to create a summary file and write the content
   1. run: This method can be invoked by each writer thread to create the output file and write its content.

### Classes for processing threshold part in parallel
1. [ThresholdProcessor.java](src%2Fmain%2Fjava%2Fassignment5%2FconcurrentSolution%2FThresholdProcessor.java)

   The launcher to identify and output valid course by using threshold
   1. filterBasedOnThreshold: Read and filter summary files by using multiple producer threads, and output the result by using one consumer thread. All the threads are running in parallel.
  

2. [ThresholdProducer.java](src%2Fmain%2Fjava%2Fassignment5%2FconcurrentSolution%2FThresholdProducer.java)

   The producer for threshold part which implements the Runnable interface
   1. run: This method can be invoked to read the summary file, find the valid pairs, then store them to the buffer queue.
  

3. [ThresholdConsumer.java](src%2Fmain%2Fjava%2Fassignment5%2FconcurrentSolution%2FThresholdConsumer.java)

   The consumer for threshold part which implements the Runnable interface
   1. run: This method can be invoked by the writer thread to create the threshold output file and write its content

## Assumption
1. The input arguments should be valid; otherwise, an exception will be thrown.
2. All the courses in studentVle file can be found in course file.
3. For the courses that is contained in course file but is not contained in studentVle file, an empty summary file with header row will be generated.
4. The generated summary directory only contains the valid summary files