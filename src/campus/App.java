package campus;

import java.nio.file.Path;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        CampusService service = new CampusService(new CampusStore());
        if (args.length > 0 && args[0].equalsIgnoreCase("demo")) {
            demo(service);
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("=== Campus Resource Booking ===");
        boolean running = true;
        while (running) {
            System.out.println("\n1. Add student\n2. List students\n3. Add resource\n4. List resources"
                    + "\n5. Book resource\n6. Return resource\n7. View bookings\n8. Export report\n9. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> addStudent(sc, service);
                    case "2" -> service.getStudents().forEach(System.out::println);
                    case "3" -> addResource(sc, service);
                    case "4" -> service.getResources().forEach(System.out::println);
                    case "5" -> book(sc, service);
                    case "6" -> { System.out.print("Booking ID: "); service.returnResource(sc.nextLine()); System.out.println("Returned."); }
                    case "7" -> { service.getBookings().forEach(System.out::println); System.out.println("Overdue: " + service.markOverdueCount()); }
                    case "8" -> { Path path = service.exportReport(); System.out.println("Report: " + path); }
                    case "9" -> running = false;
                    default -> System.out.println("Please choose 1-9.");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }

    private static void addStudent(Scanner sc, CampusService s) {
        System.out.print("Student ID: "); String id = sc.nextLine();
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Course: "); String course = sc.nextLine();
        s.addStudent(id, name, course);
        System.out.println("Student added.");
    }

    private static void addResource(Scanner sc, CampusService s) {
        System.out.print("Resource ID: "); String id = sc.nextLine();
        System.out.print("Name: "); String name = sc.nextLine();
        s.addResource(id, name);
        System.out.println("Resource added.");
    }

    private static void book(Scanner sc, CampusService s) {
        System.out.print("Student ID: "); String student = sc.nextLine();
        System.out.print("Resource ID: "); String resource = sc.nextLine();
        System.out.print("Days (1-14): "); int days = Integer.parseInt(sc.nextLine());
        System.out.println("Booked: " + s.book(student, resource, days));
    }

    private static void demo(CampusService service) {
        try {
            if (service.getStudents().stream().noneMatch(s -> s.getId().equalsIgnoreCase("S101"))) {
                service.addStudent("S101", "Aman", "CSE");
            }
            if (service.getResources().stream().noneMatch(r -> r.getId().equalsIgnoreCase("R101"))) {
                service.addResource("R101", "Projector");
            }
            if (service.getBookings().stream().noneMatch(b -> b.getStudentId().equalsIgnoreCase("S101") && b.getResourceId().equalsIgnoreCase("R101") && b.isActive())) {
                System.out.println(service.book("S101", "R101", 3));
            }
            System.out.println("\nStudents:"); service.getStudents().forEach(System.out::println);
            System.out.println("Resources:"); service.getResources().forEach(System.out::println);
            System.out.println("Bookings:"); service.getBookings().forEach(System.out::println);
            System.out.println("Report: " + service.exportReport());
        } catch (RuntimeException e) {
            System.out.println("Demo could not run: " + e.getMessage());
        }
    }
}
