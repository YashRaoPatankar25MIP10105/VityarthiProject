package campus;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class CampusStore {
    private final Path folder = Paths.get("data");
    private final Path studentsFile = folder.resolve("students.csv");
    private final Path resourcesFile = folder.resolve("resources.csv");
    private final Path bookingsFile = folder.resolve("bookings.csv");

    public CampusStore() {
        try {
            Files.createDirectories(folder);
            createIfMissing(studentsFile);
            createIfMissing(resourcesFile);
            createIfMissing(bookingsFile);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not prepare data folder.", e);
        }
    }

    private void createIfMissing(Path file) throws IOException {
        if (Files.notExists(file)) Files.createFile(file);
    }

    public List<Student> loadStudents() {
        List<Student> result = new ArrayList<>();
        for (String line : read(studentsFile)) {
            String[] p = line.split("\\|", -1);
            if (p.length == 3) result.add(new Student(p[0], p[1], p[2]));
        }
        return result;
    }

    public List<Resource> loadResources() {
        List<Resource> result = new ArrayList<>();
        for (String line : read(resourcesFile)) {
            String[] p = line.split("\\|", -1);
            if (p.length == 3) result.add(new Resource(p[0], p[1], Boolean.parseBoolean(p[2])));
        }
        return result;
    }

    public List<Booking> loadBookings() {
        List<Booking> result = new ArrayList<>();
        for (String line : read(bookingsFile)) {
            String[] p = line.split("\\|", -1);
            if (p.length == 5) {
                result.add(new Booking(p[0], p[1], p[2], LocalDate.parse(p[3]), Boolean.parseBoolean(p[4])));
            }
        }
        return result;
    }

    public void saveStudents(Collection<Student> students) {
        List<String> lines = new ArrayList<>();
        for (Student s : students) lines.add(s.getId() + "|" + clean(s.getName()) + "|" + clean(s.getCourse()));
        write(studentsFile, lines);
    }

    public void saveResources(Collection<Resource> resources) {
        List<String> lines = new ArrayList<>();
        for (Resource r : resources) lines.add(r.getId() + "|" + clean(r.getName()) + "|" + r.isAvailable());
        write(resourcesFile, lines);
    }

    public void saveBookings(Collection<Booking> bookings) {
        List<String> lines = new ArrayList<>();
        for (Booking b : bookings) {
            lines.add(b.getId() + "|" + b.getStudentId() + "|" + b.getResourceId() + "|"
                    + b.getDueDate() + "|" + b.isActive());
        }
        write(bookingsFile, lines);
    }

    private String clean(String text) { return text.replace("|", "/").trim(); }

    private List<String> read(Path file) {
        try {
            return Files.readAllLines(file);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read " + file, e);
        }
    }

    private void write(Path file, List<String> lines) {
        try {
            Files.write(file, lines);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not write " + file, e);
        }
    }
}
