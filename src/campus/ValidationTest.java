package campus;

public class ValidationTest {
    public static void main(String[] args) {
        CampusService service = new CampusService(new CampusStore());
        try {
            service.addStudent("TEST", "", "CSE");
            throw new AssertionError("Empty student name was accepted.");
        } catch (IllegalArgumentException expected) {
            System.out.println("PASS: empty student data rejected");
        }

        try {
            service.addResource("TEST-R", "Laptop");
            service.addResource("TEST-R", "Laptop");
            throw new AssertionError("Duplicate resource was accepted.");
        } catch (IllegalArgumentException expected) {
            System.out.println("PASS: duplicate resource rejected");
        }
        System.out.println("Validation tests completed.");
    }
}
