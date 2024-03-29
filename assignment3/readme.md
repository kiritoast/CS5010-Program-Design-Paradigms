1. This project is supposed to take a CSV file and generate files based on email or letter templates.

2. To run this program, open the main class and modify the configuration. An example configuration is shown below:
<code>
   --letter
   --letter-template
   src/main/resources/letter-template.txt
   --output-dir
   test_output/dr
   --csv-file
   src/main/resources/insurance-company-members.csv</code>
<br>
3. This configuration will take the CSV file from the root directory, use the letter template from the root directory, and output the files to the folder "example" under the root directory. The name of each file will be formatted as the first name + last name inside the "example" folder.


