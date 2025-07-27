# Real Estate Management System

## 💽 How to Set Up Database

1. Make sure MySQL is installed and running.
2. Run the following command to create the schema:

   mysql -u root -p < schema.sql

This will create a database named `realestate` with all required tables.

## 🚀 How to Run

1. Make sure Java 17+ is installed.
2. Compile the app:
   javac -cp "lib/*" -d bin src\dao\*.java src\Main.java

3. Package the app:
   jar cfm RealEstateApp.jar manifest.txt -C bin .

4. Run it:
   java -jar RealEstateApp.jar
