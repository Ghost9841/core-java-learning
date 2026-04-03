package model;

/**
 * Customer.java
 * Represents a customer who can book halls.
 * Extends User - demonstrates INHERITANCE.
 */
public class Customer extends User {

    // Constructor
    public Customer(String userId, String username, String password,
                    String email, String phone) {
        super(userId, username, password, email, phone, "CUSTOMER");
    }

    // Implements abstract method from User (Polymorphism)
    @Override
    public String getDashboardTitle() {
        return "Customer Dashboard - Hall Booking System";
    }

    /**
     * Create a Customer object from a CSV line read from file.
     * Format: userId,username,password,email,phone,role,isBlocked
     */
    public static Customer fromCSV(String[] parts) {
        Customer c = new Customer(parts[0], parts[1], parts[2], parts[3], parts[4]);
        c.setBlocked(Boolean.parseBoolean(parts[6]));
        return c;
    }
}
