

import model.*;

/**
 * OOP.java
 * Demonstrates Classes and Objects using the Hall Booking Management System models.
 */
public class OOPDemo {
    public static void main(String[] args) {

        // ==========================================
        // CLASSES AND OBJECTS
        // Creating objects (instances) from our model classes
        // ==========================================

        // Customer object from Customer class
        Customer customer = new Customer("U001", "john_doe", "pass123",
                                          "john@gmail.com", "9841234567");

        // Scheduler object from Scheduler class
        Scheduler scheduler = new Scheduler("S001", "ali_sched", "sched123",
                                             "ali@gmail.com", "9812345678");

        // Administrator object from Administrator class
        Administrator admin = new Administrator("A001", "raj_admin", "admin123",
                                                 "raj@gmail.com", "9856789012");

        // Manager object from Manager class
        Manager manager = new Manager("M001", "sara_mgr", "mgr123",
                                       "sara@gmail.com", "9867890123");

        // Hall object from Hall class
        Hall hall = new Hall("H001", "Grand Hall", Hall.TYPE_AUDITORIUM,
                              1000, 300.0, true);

        // Booking object from Booking class
        Booking booking = new Booking("B001", "U001", "H001", "Grand Hall",
                                       "2026-04-10", 9, 12, 1500.0,
                                       Booking.STATUS_CONFIRMED, "PAID");

        // Issue object from Issue class
        Issue issue = new Issue("I001", "U001", "B001",
                                 "Hall was not clean", Issue.STATUS_OPEN, "");

        // ==========================================
        // Printing all objects
        // ==========================================
        System.out.println("===== CLASSES AND OBJECTS =====\n");

        System.out.println("-- User Objects --");
        System.out.println(customer);
        System.out.println(scheduler);
        System.out.println(admin);
        System.out.println(manager);

        System.out.println("\n-- Hall Object --");
        System.out.println(hall);

        System.out.println("\n-- Booking Object --");
        System.out.println(booking);

        System.out.println("\n-- Issue Object --");
        System.out.println("Issue{id=" + issue.getIssueId()
                         + ", customer=" + issue.getCustomerId()
                         + ", status=" + issue.getStatus()
                         + ", description=" + issue.getDescription() + "}");
    }
}
