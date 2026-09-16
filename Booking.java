package campus;

import java.time.LocalDate;

public class Booking {
    private final String id;
    private final String studentId;
    private final String resourceId;
    private final LocalDate dueDate;
    private boolean active;

    public Booking(String id, String studentId, String resourceId, LocalDate dueDate) {
        this(id, studentId, resourceId, dueDate, true);
    }

    public Booking(String id, String studentId, String resourceId, LocalDate dueDate, boolean active) {
        this.id = id;
        this.studentId = studentId;
        this.resourceId = resourceId;
        this.dueDate = dueDate;
        this.active = active;
    }

    public String getId() { return id; }
    public String getStudentId() { return studentId; }
    public String getResourceId() { return resourceId; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isActive() { return active; }
    public void close() { active = false; }

    public String status() {
        if (!active) return "Returned";
        return dueDate.isBefore(LocalDate.now()) ? "Overdue" : "Active";
    }

    @Override
    public String toString() {
        return id + " | student=" + studentId + " | resource=" + resourceId
                + " | due=" + dueDate + " | " + status();
    }
}
