General Info:
Simple Stock Exchange System 

Technologies:
Built using Java8 and gradle, Unit tests written using JUnit5

Run:
gradle clean build - To check for compile errors or any test failures
jar -jar build/libs/geektrust-1.0-SNAPSHOT.jar <Path to input file>

Assumptions/Validations/Notes:
1. Used the same geektrust word for the finally built jar according to the instructions
2. Given input in the question file is not formatted properly.
   I am assuming only space to be the delimiter and parse accordingly
3. Question mentioned both stdin as input and text file as input in different
places - considering input from text file and output printed on stdout
4. Time is considered to be in default time zone of the system and converted to
epoch milliseconds by adding the current date when program is run - Hence, the 
   orders accepted are assumed that will all be of the single day
5. If we need to extend the system over multiple days, I have provided the reinitialize
   method which can be tweaked further with changing system requirements
6. Unit tests are doing a total of about 80% line coverage.

Input:
Text file which looks like this - 
#1 09:45 BAC sell 240.12 100
#2 09:46 BAC sell 237.45 90
#3 09:47 BAC buy 238.10 110
#4 09:48 BAC buy 237.80 10
#5 09:49 BAC buy 237.80 40
#6 09:50 BAC sell 236.00 50