package campus;

public class Student extends Person {
    private final String course;

    public Student(String id, String name, String course) {
        super(id, name);
        if (id.isBlank() || name.isBlank() || course.isBlank()) {
            throw new IllegalArgumentException("Student details cannot be empty.");
        }
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return getId() + " | " + getName() + " | " + course;
    }
}
