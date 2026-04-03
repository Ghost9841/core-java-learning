package model;

/**
 * Booking.java
 * Represents a hall booking made by a customer.
 * Stores booking details including date, time, and payment status.
 */
public class Booking {

    // Booking status constants
    public static final String STATUS_PENDING   = "PENDING";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_CANCELLED = "CANCELLED";

    private String bookingId;
    private String customerId;
    private String hallId;
    private String hallName;
    private String eventDate;   // Format: YYYY-MM-DD
    private int startHour;      // 8 to 17 (8 AM to 5 PM start max)
    private int endHour;        // 9 to 18 (must end by 6 PM)
    private double totalAmount;
    private String status;
    private String paymentStatus; // PAID or UNPAID

    // Constructor
    public Booking(String bookingId, String customerId, String hallId,
                   String hallName, String eventDate, int startHour,
                   int endHour, double totalAmount, String status, String paymentStatus) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.hallId = hallId;
        this.hallName = hallName;
        this.eventDate = eventDate;
        this.startHour = startHour;
        this.endHour = endHour;
        this.totalAmount = totalAmount;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getHallId() { return hallId; }
    public void setHallId(String hallId) { this.hallId = hallId; }

    public String getHallName() { return hallName; }
    public void setHallName(String hallName) { this.hallName = hallName; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }

    public int getStartHour() { return startHour; }
    public void setStartHour(int startHour) { this.startHour = startHour; }

    public int getEndHour() { return endHour; }
    public void setEndHour(int endHour) { this.endHour = endHour; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    /**
     * Get a readable time range string like "10:00 - 14:00"
     */
    public String getTimeRange() {
        return String.format("%02d:00 - %02d:00", startHour, endHour);
    }

    /**
     * Convert booking data to CSV format for file storage.
     * Format: bookingId,customerId,hallId,hallName,eventDate,startHour,endHour,totalAmount,status,paymentStatus
     */
    public String toCSV() {
        return bookingId + "," + customerId + "," + hallId + "," + hallName + "," +
               eventDate + "," + startHour + "," + endHour + "," +
               totalAmount + "," + status + "," + paymentStatus;
    }

    /**
     * Create a Booking object from a CSV line.
     */
    public static Booking fromCSV(String[] parts) {
        return new Booking(
            parts[0],
            parts[1],
            parts[2],
            parts[3],
            parts[4],
            Integer.parseInt(parts[5]),
            Integer.parseInt(parts[6]),
            Double.parseDouble(parts[7]),
            parts[8],
            parts[9]
        );
    }

    @Override
    public String toString() {
        return "Booking{id=" + bookingId + ", hall=" + hallName +
               ", date=" + eventDate + ", status=" + status + "}";
    }
}
