package campus;

public class Resource {
    private final String id;
    private final String name;
    private boolean available;

    public Resource(String id, String name) {
        if (id.isBlank() || name.isBlank()) {
            throw new IllegalArgumentException("Resource details cannot be empty.");
        }
        this.id = id;
        this.name = name;
        this.available = true;
    }

    public Resource(String id, String name, boolean available) {
        this.id = id;
        this.name = name;
        this.available = available;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return id + " | " + name + " | " + (available ? "Available" : "Booked");
    }
}
