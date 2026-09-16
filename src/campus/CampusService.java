package campus;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;

public class CampusService {
    private final CampusStore store;
    private final List<Student> students;
    private final List<Resource> resources;
    private final List<Booking> bookings;
    private int bookingNumber;

    public CampusService(CampusStore store) {
        this.store = store;
        this.students = new ArrayList<>(store.loadStudents());
        this.resources = new ArrayList<>(store.loadResources());
        this.bookings = new ArrayList<>(store.loadBookings());
        this.bookingNumber = bookings.size() + 1;
        for (Booking b : bookings) bookingNumber = Math.max(bookingNumber, numberOf(b.getId()) + 1);
    }

    private int numberOf(String id) {
        try { return Integer.parseInt(id.replace("B", "")); }
        catch (NumberFormatException e) { return 0; }
    }

    public void addStudent(String id, String name, String course) {
        if (findStudent(id) != null) throw new IllegalArgumentException("Student ID already exists.");
        students.add(new Student(id, name, course));
        store.saveStudents(students);
    }

    public void addResource(String id, String name) {
        if (findResource(id) != null) throw new IllegalArgumentException("Resource ID already exists.");
        resources.add(new Resource(id, name));
        store.saveResources(resources);
    }

    public List<Student> getStudents() { return Collections.unmodifiableList(students); }
    public List<Resource> getResources() { return Collections.unmodifiableList(resources); }
    public List<Booking> getBookings() { return Collections.unmodifiableList(bookings); }

    public Booking book(String studentId, String resourceId, int days) {
        Student s = findStudent(studentId);
        Resource r = findResource(resourceId);
        if (s == null) throw new IllegalArgumentException("Student not found.");
        if (r == null) throw new IllegalArgumentException("Resource not found.");
        if (!r.isAvailable()) throw new IllegalStateException("Resource is already booked.");
        if (days < 1 || days > 14) throw new IllegalArgumentException("Days must be between 1 and 14.");

        Booking booking = new Booking("B" + bookingNumber++, studentId, resourceId,
                LocalDate.now().plusDays(days));
        bookings.add(booking);
        r.setAvailable(false);
        store.saveBookings(bookings);
        store.saveResources(resources);
        return booking;
    }

    public void returnResource(String bookingId) {
        Booking booking = findBooking(bookingId);
        if (booking == null) throw new IllegalArgumentException("Booking not found.");
        if (!booking.isActive()) throw new IllegalStateException("Booking is already closed.");
        booking.close();
        Resource resource = findResource(booking.getResourceId());
        if (resource != null) resource.setAvailable(true);
        store.saveBookings(bookings);
        store.saveResources(resources);
    }

    public long markOverdueCount() {
        return bookings.stream().filter(Booking::isActive)
                .filter(b -> b.getDueDate().isBefore(LocalDate.now())).count();
    }

    public Path exportReport() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try {
            Future<Path> future = pool.submit(() -> {
                Path file = Paths.get("out", "booking-report.csv");
                Files.createDirectories(file.getParent());
                List<String> lines = new ArrayList<>();
                lines.add("BookingId,StudentId,ResourceId,DueDate,Status");
                for (Booking b : bookings) {
                    lines.add(String.join(",", b.getId(), b.getStudentId(), b.getResourceId(),
                            b.getDueDate().toString(), b.status()));
                }
                Files.write(file, lines);
                return file;
            });
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Report task was interrupted.", e);
        } catch (ExecutionException | java.io.UncheckedIOException e) {
            throw new IllegalStateException("Could not create report.", e);
        } finally {
            pool.shutdown();
        }
    }

    private Student findStudent(String id) {
        return students.stream().filter(s -> s.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    private Resource findResource(String id) {
        return resources.stream().filter(r -> r.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    private Booking findBooking(String id) {
        return bookings.stream().filter(b -> b.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}
