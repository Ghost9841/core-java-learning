package model;

/**
 * Scheduler.java
 * Represents a scheduler staff member who manages halls.
 * Extends User - demonstrates INHERITANCE.
 */
public class Scheduler extends User {

    // Constructor
    public Scheduler(String userId, String username, String password,
                     String email, String phone) {
        super(userId, username, password, email, phone, "SCHEDULER");
    }

    // Implements abstract method from User (Polymorphism)
    @Override
    public String getDashboardTitle() {
        return "Scheduler Dashboard - Hall Booking System";
    }

    /**
     * Create a Scheduler object from a CSV line read from file.
     */
    public static Scheduler fromCSV(String[] parts) {
        Scheduler s = new Scheduler(parts[0], parts[1], parts[2], parts[3], parts[4]);
        s.setBlocked(Boolean.parseBoolean(parts[6]));
        return s;
    }
}
