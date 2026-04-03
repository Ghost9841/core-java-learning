package model;

/**
 * Payment.java
 * Represents a payment made for a booking.
 * Stores payment method and amount for receipt generation.
 */
public class Payment {

    // Payment methods
    public static final String METHOD_CASH   = "Cash";
    public static final String METHOD_CARD   = "Card";
    public static final String METHOD_ONLINE = "Online Transfer";

    private String paymentId;
    private String bookingId;
    private double amount;
    private String paymentMethod;
    private String paymentDate; // Format: YYYY-MM-DD

    // Constructor
    public Payment(String paymentId, String bookingId, double amount,
                   String paymentMethod, String paymentDate) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    /**
     * Convert payment data to CSV format.
     * Format: paymentId,bookingId,amount,paymentMethod,paymentDate
     */
    public String toCSV() {
        return paymentId + "," + bookingId + "," + amount + "," +
               paymentMethod + "," + paymentDate;
    }

    /**
     * Create a Payment object from a CSV line.
     */
    public static Payment fromCSV(String[] parts) {
        return new Payment(
            parts[0],
            parts[1],
            Double.parseDouble(parts[2]),
            parts[3],
            parts[4]
        );
    }

    /**
     * Generate a receipt string for display.
     */
    public String generateReceipt(String hallName, String eventDate, String timeRange) {
        return "============================\n" +
               "        PAYMENT RECEIPT       \n" +
               "============================\n" +
               "Payment ID   : " + paymentId + "\n" +
               "Booking ID   : " + bookingId + "\n" +
               "Hall         : " + hallName + "\n" +
               "Event Date   : " + eventDate + "\n" +
               "Time         : " + timeRange + "\n" +
               "Amount Paid  : RM " + String.format("%.2f", amount) + "\n" +
               "Method       : " + paymentMethod + "\n" +
               "Date Paid    : " + paymentDate + "\n" +
               "============================\n" +
               "  Thank you for your booking!\n" +
               "============================";
    }
}
