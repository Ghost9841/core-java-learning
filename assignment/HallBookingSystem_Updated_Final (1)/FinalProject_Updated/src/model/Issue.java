package model;

/**
 * Issue.java
 * Represents a complaint or issue raised by a customer about a booking.
 * Manager can view, assign to a Scheduler, respond to issues, and change status.
 *
 * OOP Concept: ENCAPSULATION - all fields are private, accessed via getters/setters.
 */
public class Issue {

    public static final String STATUS_OPEN        = "OPEN";
    public static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    public static final String STATUS_DONE        = "DONE";
    public static final String STATUS_CLOSED      = "CLOSED";
    public static final String STATUS_CANCELLED   = "CANCELLED";
    public static final String STATUS_ASSIGNED    = "ASSIGNED";

    private String issueId;
    private String customerId;
    private String bookingId;
    private String description;
    private String status;
    private String assignedSchedulerId;
    private String managerResponse;

    public Issue(String issueId, String customerId, String bookingId,
                 String description, String status, String assignedSchedulerId) {
        this(issueId, customerId, bookingId, description, status, assignedSchedulerId, "");
    }

    public Issue(String issueId, String customerId, String bookingId,
                 String description, String status, String assignedSchedulerId,
                 String managerResponse) {
        this.issueId             = issueId;
        this.customerId          = customerId;
        this.bookingId           = bookingId;
        this.description         = description;
        this.status              = status;
        this.assignedSchedulerId = assignedSchedulerId;
        this.managerResponse     = (managerResponse == null) ? "" : managerResponse;
    }

    public String getIssueId() { return issueId; }
    public void setIssueId(String issueId) { this.issueId = issueId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAssignedSchedulerId() { return assignedSchedulerId; }
    public void setAssignedSchedulerId(String id) { this.assignedSchedulerId = id; }

    public String getManagerResponse() { return managerResponse; }
    public void setManagerResponse(String managerResponse) {
        this.managerResponse = (managerResponse == null) ? "" : managerResponse;
    }

    public String toCSV() {
        return issueId + "," + customerId + "," + bookingId + ","
             + description.replace(",", ";") + ","
             + status + ","
             + assignedSchedulerId + ","
             + managerResponse.replace(",", ";");
    }

    public static Issue fromCSV(String[] parts) {
        String response = (parts.length > 6) ? parts[6].replace(";", ",") : "";
        return new Issue(
            parts[0], parts[1], parts[2],
            parts[3].replace(";", ","),
            parts[4],
            parts.length > 5 ? parts[5] : "",
            response
        );
    }
}
