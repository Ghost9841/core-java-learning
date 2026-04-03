package model;

/**
 * Manager.java
 * Represents a manager who views sales and manages issues.
 * Extends User - demonstrates INHERITANCE.
 */
public class Manager extends User {

    // Constructor
    public Manager(String userId, String username, String password,
                   String email, String phone) {
        super(userId, username, password, email, phone, "MANAGER");
    }

    // Implements abstract method from User (Polymorphism)
    @Override
    public String getDashboardTitle() {
        return "Manager Dashboard - Hall Booking System";
    }

    /**
     * Create a Manager object from a CSV line read from file.
     */
    public static Manager fromCSV(String[] parts) {
        Manager m = new Manager(parts[0], parts[1], parts[2], parts[3], parts[4]);
        m.setBlocked(Boolean.parseBoolean(parts[6]));
        return m;
    }
}
