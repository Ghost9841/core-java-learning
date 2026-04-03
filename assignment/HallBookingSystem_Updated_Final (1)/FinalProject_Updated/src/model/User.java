package model;

/**
 * User.java (Abstract Class)
 * Base class for all user types in the system.
 * Demonstrates ABSTRACTION and ENCAPSULATION.
 * All user types (Customer, Scheduler, Administrator, Manager) extend this class.
 */
public abstract class User {

    // Private fields - Encapsulation
    private String userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
    private boolean isBlocked;

    // Constructor
    public User(String userId, String username, String password,
                String email, String phone, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.isBlocked = false;
    }

    // Abstract method - must be implemented by subclasses (Abstraction)
    public abstract String getDashboardTitle();

    // Getters and Setters (Encapsulation)
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isBlocked() { return isBlocked; }
    public void setBlocked(boolean blocked) { isBlocked = blocked; }

    /**
     * Convert user data to CSV format for file storage.
     * Format: userId,username,password,email,phone,role,isBlocked
     */
    public String toCSV() {
        return userId + "," + username + "," + password + "," +
               email + "," + phone + "," + role + "," + isBlocked;
    }

    @Override
    public String toString() {
        return "User{id=" + userId + ", username=" + username + ", role=" + role + "}";
    }
}
