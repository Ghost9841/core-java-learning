package model;

/**
 * Administrator.java
 * Represents an admin staff member who manages users and system.
 * Extends User - demonstrates INHERITANCE.
 */
public class Administrator extends User {

    // Constructor
    public Administrator(String userId, String username, String password,
                         String email, String phone) {
        super(userId, username, password, email, phone, "ADMIN");
    }

    // Implements abstract method from User (Polymorphism)
    @Override
    public String getDashboardTitle() {
        return "Administrator Dashboard - Hall Booking System";
    }

    /**
     * Create an Administrator object from a CSV line read from file.
     */
    public static Administrator fromCSV(String[] parts) {
        Administrator a = new Administrator(parts[0], parts[1], parts[2], parts[3], parts[4]);
        a.setBlocked(Boolean.parseBoolean(parts[6]));
        return a;
    }
}
