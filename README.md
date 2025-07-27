# 🏠 Real Estate Management System (JDBC)

A command-line Java application for managing real estate listings, agents, buyers, inquiries, and offers. Built using Java and MySQL with JDBC.

---

## 📁 Project Structure

```
├── src/ # Java source files
│ ├── dao/ # DAO classes
│ └── Main.java # Entry point
├── lib/ # MySQL connector JAR
├── schema.sql # SQL script to create database schema
├── README.md # This file
```
---

## 💽 Database Setup

You must create the required database schema before running the application.

### 1. ✅ Prerequisites

- MySQL installed and running
- A MySQL user (e.g. `root`) with privileges

### 2. ✅ Run the Schema

Run the following command:

```
mysql -u root -p < schema.sql
```
This will:

Create the realestate database

Create all required tables and constraints

#### 🏃 How to Run the Application
After compiling the code using javac, run the application using:

```
java -cp "bin;lib/*" Main
```
Or if using packages:

```
java -cp "bin;lib/*" your.package.Main
```
Replace your.package.Main with the actual class name if you’re using packages.

#### 🔐 SQL Connection Prompts
At startup, the program will ask you to input MySQL connection details:

```
Enter MySQL host (default: localhost):
Enter MySQL port (default: 3306):
Enter database name: realestate
Enter username: 
Enter password:
```
You can press Enter to accept the default host and port values.

✅ Once connected, the main menu will appear.

---

### 🧠 Features

* Add agencies, agents, buyers, and properties

* Submit inquiries and offers

* Search properties by keyword (full-text)

* View inquiries on a property from the last month

* Get average agent response time

* Get average time on market for sold properties

* Withdraw offers and list pending ones

--- 
### 📌 Notes
Requires Java 17+

Works with MySQL 8.0+

Ensure mysql-connector-j-9.4.0.jar is in the lib/ folder

All database interaction is handled via the DAO layer