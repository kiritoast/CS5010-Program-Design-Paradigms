# 2023 Spring CS 5010 Repository NEU

CS 5010 serves as the introductory course for students in Northeastern's MS program, aiming to achieve two primary objectives. Firstly, it equips students with foundational skills in program design, encompassing problem analysis, test suite development, and utilization of various program design paradigms. Secondly, the course fosters an understanding of programming as a collaborative endeavor, emphasizing teamwork, code presentation to review panels, and adaptation to evolving code bases.

## **Key Course Components:**

- **Assignment Structure:** Each assignment undergoes rigorous unit testing and Maven-based build processes. Compliance with quality standards, including achieving 75% or higher Jacoco Coverage and zero reporting in Check Style, FindBug, and PMD, is mandatory.
  
- **Workload:** The average workload for each assignment is approximately 40 hours per week, equivalent to one month of full-time Software Development Engineer (SDE) production.

## **Course Learning Outcomes:**

Upon completion of CS 5010, students will have developed the following competencies:

- Designing object-oriented solutions for small and moderately-sized problems
- Implementing object-oriented designs using Java
- Generating comprehensive documentation for developed solutions
- Designing and implementing unit tests using the JUnit testing framework
- Creating, refining, and expressing designs using graphical notation such as UML diagrams
- Exploring existing documentation to effectively describe and utilize libraries and frameworks

Through these objectives and learning outcomes, CS 5010 aims to equip students with the necessary skills and knowledge to excel in the field of software development.

## Assignment List

### [Assignment 1](https://github.com/kiritoast/CS5010-Program-Design-Paradigms/tree/main/assignment1)

- Frequent Flyer Management System
- Vehicle Management System
- Tax Calculator System

### [Assignment 2](https://github.com/kiritoast/CS5010-Program-Design-Paradigms/tree/main/assignment2)
- Inventory Management that organize inventory
- Online Ordering and Fulfillment that enable online ordering with in-store pickup for customer
- Implement shopping carts and process orders by updating stock, handling substitutions, and generating receipts.

### [Assignment 3](https://github.com/kiritoast/CS5010-Program-Design-Paradigms/tree/main/assignment3)
This project is designed to take a CSV file containing customer information and generate email or letter files based on provided templates

### [Assignment 4](https://github.com/kiritoast/CS5010-Program-Design-Paradigms/tree/main/Assignment4)

The project aims to build an interactive application to finish the following tasks:
- read and store the file information and non-terminals from grammar files
- expend and generate sentences based on non-terminals
- implement a UI to interactive with users


### [Assignment 5](https://github.com/kiritoast/CS5010-Program-Design-Paradigms/tree/main/Assignment5)

The project aims to build an data analyzer to finish the following tasks:
- sequential solution:
    - read the course file, and initialize the key of the collection map with the course name
    - read the studentVle file, and set the value of the collection map
    - write the collection map to summary files
- concurrent solution:
    - read the course file, and initialize the key of the collection map with the course name
    - read the studentVle file, and set the value of the collection map in parallel
    - write the collection map to summary files in parallel
    - In addition, if the threshold option is given, the program will read all the summary files from the previous step, and generate an activity file that only contains valid courses


### [Assignment 6](https://github.com/kiritoast/CS5010-Program-Design-Paradigms/tree/main/Assignment6)

The project aims to build a simple chat room application to finish the following tasks:
- Server side: Responsible for managing the client connections (up to 10 clients can be connected and in a chat room at one time), accepting messages from one client and sending the messages to all attached clients;
- Client side: Send a message that is public in the chat room, or that goes directly to a single, specified client;
- Protocol part: Deal with the input message; transfer and process the message as the MessageFormat type.
