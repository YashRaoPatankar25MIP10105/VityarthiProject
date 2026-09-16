# Campus Resource Booking System

A small Java commandline project for managing campus resources and their bookings.

## Why this project?
Campus equipment is easy to track on paper, but checking availability and due dates becomes messy. This program keeps the basic workflow in one place without needing a GUI or a database server.

## Features
- Add and list students
- Add and list resources
- Book and return a resource
- Show active and overdue bookings
- Export a simple CSV report
- Save data locally so it remains after the program closes

## Java concepts used
- Classes, inheritance and encapsulation
- Collections (`ArrayList`)
- File I/O (`java.nio.file`)
- Exception handling and input validation
- Basic concurrency using `ExecutorService` for report generation
- Packages and commandline execution

## Run
Requires JDK 17 or later.

```bash
./run.sh
```

Sample run:

```bash
./run.sh demo
```

Validation tests:

```bash
./test.sh
```

## Project structure
```text
src/campus/
  App.java
  Person.java
  Student.java
  Resource.java
  Booking.java
  CampusStore.java
  CampusService.java
  ValidationTest.java
run.sh
test.sh
statement.md
PROJECT_REPORT.pdf
```

## Data
The program stores small CSV files in `data/` and creates `out/bookingreport.csv` when a report is exported.
