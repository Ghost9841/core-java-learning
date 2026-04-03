package util;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileManager.java
 * Handles ALL reading and writing of .txt files.
 * Automatically creates data folder and files if they don't exist.
 * Uses simple CSV (comma-separated values) format.
 */
public class FileManager {

    // Data directory and file paths
    private static final String DATA_DIR       = "data/";
    private static final String USERS_FILE     = DATA_DIR + "users.txt";
    private static final String HALLS_FILE     = DATA_DIR + "halls.txt";
    private static final String BOOKINGS_FILE  = DATA_DIR + "bookings.txt";
    private static final String ISSUES_FILE    = DATA_DIR + "issues.txt";
    private static final String PAYMENTS_FILE  = DATA_DIR + "payments.txt";

    // =============================================
    //  INITIALIZATION
    // =============================================

    /**
     * Creates the data folder and all required .txt files if they don't exist.
     * Also seeds a default admin and manager account on first run.
     */
    public static void initializeFiles() {
        // Create data directory
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Create each file if missing
        createFileIfNotExists(USERS_FILE);
        createFileIfNotExists(HALLS_FILE);
        createFileIfNotExists(BOOKINGS_FILE);
        createFileIfNotExists(ISSUES_FILE);
        createFileIfNotExists(PAYMENTS_FILE);

        // Seed default admin and manager if users.txt is empty
        List<User> users = readUsers();
        if (users.isEmpty()) {
            users.add(new Administrator("U001", "admin", "admin123", "admin@hall.com", "0123456789"));
            users.add(new Manager("U002", "manager", "manager123", "manager@hall.com", "0198765432"));
            writeUsers(users);
        }
    }

    /** Creates a file at the given path if it does not already exist. */
    private static void createFileIfNotExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Could not create file: " + path);
            }
        }
    }

    // =============================================
    //  USER READ / WRITE
    // =============================================

    /**
     * Read all users from users.txt.
     * Each line is one user in CSV format.
     * The role field determines which subclass to create.
     */
    public static List<User> readUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 7) continue;

                String role = parts[5];
                // Create the correct subclass based on role
                switch (role) {
                    case "CUSTOMER":  users.add(Customer.fromCSV(parts));       break;
                    case "SCHEDULER": users.add(Scheduler.fromCSV(parts));      break;
                    case "ADMIN":     users.add(Administrator.fromCSV(parts));  break;
                    case "MANAGER":   users.add(Manager.fromCSV(parts));        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading users: " + e.getMessage());
        }
        return users;
    }

    /**
     * Write all users to users.txt, overwriting the file.
     */
    public static void writeUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User u : users) {
                bw.write(u.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing users: " + e.getMessage());
        }
    }

    // =============================================
    //  HALL READ / WRITE
    // =============================================

    /**
     * Read all halls from halls.txt.
     */
    public static List<Hall> readHalls() {
        List<Hall> halls = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(HALLS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 6) continue;
                halls.add(Hall.fromCSV(parts));
            }
        } catch (IOException e) {
            System.err.println("Error reading halls: " + e.getMessage());
        }
        return halls;
    }

    /**
     * Write all halls to halls.txt, overwriting the file.
     */
    public static void writeHalls(List<Hall> halls) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(HALLS_FILE))) {
            for (Hall h : halls) {
                bw.write(h.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing halls: " + e.getMessage());
        }
    }

    // =============================================
    //  BOOKING READ / WRITE
    // =============================================

    /**
     * Read all bookings from bookings.txt.
     */
    public static List<Booking> readBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 10) continue;
                bookings.add(Booking.fromCSV(parts));
            }
        } catch (IOException e) {
            System.err.println("Error reading bookings: " + e.getMessage());
        }
        return bookings;
    }

    /**
     * Write all bookings to bookings.txt, overwriting the file.
     */
    public static void writeBookings(List<Booking> bookings) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKINGS_FILE))) {
            for (Booking b : bookings) {
                bw.write(b.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing bookings: " + e.getMessage());
        }
    }

    // =============================================
    //  ISSUE READ / WRITE
    // =============================================

    /**
     * Read all issues from issues.txt.
     */
    public static List<Issue> readIssues() {
        List<Issue> issues = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ISSUES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 5) continue;
                issues.add(Issue.fromCSV(parts));
            }
        } catch (IOException e) {
            System.err.println("Error reading issues: " + e.getMessage());
        }
        return issues;
    }

    /**
     * Write all issues to issues.txt, overwriting the file.
     */
    public static void writeIssues(List<Issue> issues) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ISSUES_FILE))) {
            for (Issue i : issues) {
                bw.write(i.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing issues: " + e.getMessage());
        }
    }

    // =============================================
    //  PAYMENT READ / WRITE
    // =============================================

    /**
     * Read all payments from payments.txt.
     */
    public static List<Payment> readPayments() {
        List<Payment> payments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PAYMENTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 5) continue;
                payments.add(Payment.fromCSV(parts));
            }
        } catch (IOException e) {
            System.err.println("Error reading payments: " + e.getMessage());
        }
        return payments;
    }

    /**
     * Write all payments to payments.txt, overwriting the file.
     */
    public static void writePayments(List<Payment> payments) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PAYMENTS_FILE))) {
            for (Payment p : payments) {
                bw.write(p.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing payments: " + e.getMessage());
        }
    }

    // =============================================
    //  HELPER METHODS
    // =============================================

    /**
     * Generate a unique ID with a given prefix and the current timestamp.
     * Example: generateId("B") -> "B1718200000000"
     */
    public static String generateId(String prefix) {
        return prefix + System.currentTimeMillis();
    }

    /**
     * Find a user by username and password (for login).
     */
    public static User findUser(String username, String password) {
        List<User> users = readUsers();
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Check if a username already exists (for registration).
     */
    public static boolean usernameExists(String username) {
        List<User> users = readUsers();
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
}
