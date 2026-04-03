package model;

/**
 * Hall.java
 * Represents a hall that can be booked by customers.
 * Demonstrates ENCAPSULATION with private fields and public getters/setters.
 */
public class Hall {

    // Hall types as constants
    public static final String TYPE_AUDITORIUM = "Auditorium";
    public static final String TYPE_BANQUET    = "Banquet Hall";
    public static final String TYPE_MEETING    = "Meeting Room";

    private String hallId;
    private String hallName;
    private String hallType;   // Auditorium, Banquet Hall, Meeting Room
    private int capacity;
    private double ratePerHour; // in RM
    private boolean isAvailable;

    // Constructor
    public Hall(String hallId, String hallName, String hallType,
                int capacity, double ratePerHour, boolean isAvailable) {
        this.hallId = hallId;
        this.hallName = hallName;
        this.hallType = hallType;
        this.capacity = capacity;
        this.ratePerHour = ratePerHour;
        this.isAvailable = isAvailable;
    }

    // Getters and Setters
    public String getHallId() { return hallId; }
    public void setHallId(String hallId) { this.hallId = hallId; }

    public String getHallName() { return hallName; }
    public void setHallName(String hallName) { this.hallName = hallName; }

    public String getHallType() { return hallType; }
    public void setHallType(String hallType) { this.hallType = hallType; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public double getRatePerHour() { return ratePerHour; }
    public void setRatePerHour(double ratePerHour) { this.ratePerHour = ratePerHour; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    /**
     * Convert hall data to CSV format for file storage.
     * Format: hallId,hallName,hallType,capacity,ratePerHour,isAvailable
     */
    public String toCSV() {
        return hallId + "," + hallName + "," + hallType + "," +
               capacity + "," + ratePerHour + "," + isAvailable;
    }

    /**
     * Create a Hall object from a CSV line.
     */
    public static Hall fromCSV(String[] parts) {
        return new Hall(
            parts[0],
            parts[1],
            parts[2],
            Integer.parseInt(parts[3]),
            Double.parseDouble(parts[4]),
            Boolean.parseBoolean(parts[5])
        );
    }

    /**
     * Get the default rate for a hall type.
     */
    public static double getDefaultRate(String type) {
        switch (type) {
            case TYPE_AUDITORIUM: return 300.0;
            case TYPE_BANQUET:    return 100.0;
            case TYPE_MEETING:    return 50.0;
            default:             return 50.0;
        }
    }

    /**
     * Get the default capacity for a hall type.
     */
    public static int getDefaultCapacity(String type) {
        switch (type) {
            case TYPE_AUDITORIUM: return 1000;
            case TYPE_BANQUET:    return 300;
            case TYPE_MEETING:    return 30;
            default:             return 30;
        }
    }

    @Override
    public String toString() {
        return hallName + " (" + hallType + ")";
    }
}
