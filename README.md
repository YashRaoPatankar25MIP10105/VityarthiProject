# Campus Resource Booking System

A small **Java command-line application** for keeping track of shared campus resources such as projectors, laptops, and other equipment.

The idea is simple: instead of maintaining separate notes about who borrowed what, the program keeps students, resources, and bookings together in one place. It also keeps the data after the program is closed and can generate a basic booking report.

## What the project can do

The application is built around three main parts:

### 1. Student management
- Add a student with an ID, name, and course.
- View the students currently stored in the system.
- Prevent duplicate student IDs.

### 2. Resource management
- Add a campus resource such as a projector or laptop.
- View whether a resource is available or already booked.
- Prevent duplicate resource IDs.

### 3. Booking management
- Book an available resource for a student.
- Set a borrowing period from 1 to 14 days.
- Return a booked resource.
- View booking status, including overdue bookings.
- Export the booking details to a CSV report.

A small `demo` mode is also included so the project can be checked quickly without entering all the data manually.

## Java concepts used

The project was kept intentionally small so that each class has a clear purpose. The main syllabus concepts used are:

- **OOP:** classes, inheritance, abstraction, encapsulation
- **Collections:** `ArrayList` and collection-based searching/filtering
- **File I/O:** reading and writing local files using `java.nio.file`
- **Exception handling:** validation errors and file-related errors are handled without crashing the application unexpectedly
- **Concurrency:** `ExecutorService` is used while creating the booking report
- **Packages:** the Java classes are organized under the `campus` package
- **Command-line execution:** the complete project can be compiled and run from a terminal

## Requirements

- **JDK 17 or later**
- No IDE is required
- No external libraries or database server are required

## Running the project

Make sure the terminal is opened in the project folder.

### Start the application

```bash
./run.sh
```

This opens a simple menu where you can add students, add resources, create bookings, return resources, view bookings, and export a report.

### Run the demo

```bash
./run.sh demo
```

The demo creates a small example using a student and a resource, creates a booking, displays the stored information, and generates a report.

### Run the validation tests

```bash
./test.sh
```

The current checks include invalid student data and duplicate resource IDs.

## Example workflow

A normal session looks roughly like this:

```text
Add student
    ↓
Add resource
    ↓
Book resource
    ↓
View booking / check due date
    ↓
Return resource
    ↓
Export report
```

The application uses a menu instead of a GUI because the project is meant to demonstrate the Java programming concepts directly and remain easy to run in a terminal environment.

## Project structure

```text
VityarthiProject/
│
├── src/
│   └── campus/
│       ├── App.java
│       ├── Person.java
│       ├── Student.java
│       ├── Resource.java
│       ├── Booking.java
│       ├── CampusStore.java
│       ├── CampusService.java
│       └── ValidationTest.java
│
├── run.sh
├── test.sh
├── statement.md
├── README.md
└── PROJECT_REPORT.pdf
```

### What the classes do

- `App.java` - command-line menu and program entry point
- `Person.java` - base abstract class for people in the system
- `Student.java` - student details and validation
- `Resource.java` - resource details and availability
- `Booking.java` - booking details and booking status
- `CampusStore.java` - saves and loads local data
- `CampusService.java` - main application logic for students, resources, and bookings
- `ValidationTest.java` - basic validation checks

## Data storage

The project does not require MySQL or another database server. Small local files are created inside the `data/` folder so that students, resources, and bookings are still available when the program is opened again.

When a report is exported, the file is written to:

```text
out/booking-report.csv
```

## Keeping the project simple

This project is intentionally a **small academic application**, not a full college management system. There is no login system, web interface, or remote database. The focus is on implementing the Java concepts from the course in a useful and understandable workflow.

That also makes the project easier to compile, run, test, and explain during evaluation.

## Project documentation

`statement.md` contains the problem statement, project scope, target users, and high-level features.

The project report is submitted separately as required by the course and contains the detailed design, diagrams, implementation discussion, testing, challenges, and learnings.
