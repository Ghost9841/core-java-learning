package gui;

import model.*;
import util.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * BookingHistoryFrame.java
 * Displays booking records with filtering support.
 *
 * - For customers: shows only their own bookings with filter (All / Upcoming / Past)
 *   and allows cancellation (at least 3 days before event date).
 * - For staff (customer == null): shows ALL bookings.
 * - paymentMode: shows only unpaid bookings and lets customer pay.
 *
 * OOP Concept: ENCAPSULATION — all fields private, passed via constructor.
 */
public class BookingHistoryFrame extends JFrame {

    private final Customer customer;    // null if a staff member is viewing
    private final boolean paymentMode;
    private DefaultTableModel tableModel;
    private JTable bookingTable;
    private JComboBox<String> filterCombo; // Filter: All, Upcoming, Past

    // Constructor for customer (no payment mode)
    public BookingHistoryFrame(Customer customer) {
        this(customer, false);
    }

    // Full constructor
    public BookingHistoryFrame(Customer customer, boolean paymentMode) {
        this.customer = customer;
        this.paymentMode = paymentMode;
        setTitle(customer == null ? "All Bookings" :
                 paymentMode ? "Make Payment" : "My Booking History");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(780, 460);
        setLocationRelativeTo(null);
        initComponents();
        loadBookings("All");
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---------- Filter Panel (only shown to customers in non-payment mode) ----------
        if (customer != null && !paymentMode) {
            JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            filterPanel.setBorder(BorderFactory.createTitledBorder("Filter Bookings"));
            filterPanel.add(new JLabel("Show:"));

            filterCombo = new JComboBox<>(new String[]{ "All", "Upcoming", "Past" });
            filterPanel.add(filterCombo);

            JButton applyBtn = new JButton("Apply");
            applyBtn.addActionListener(e -> loadBookings((String) filterCombo.getSelectedItem()));
            filterPanel.add(applyBtn);

            mainPanel.add(filterPanel, BorderLayout.NORTH);
        }

        // ---------- Table ----------
        String[] columns = { "Booking ID", "Hall", "Date", "Time", "Amount (RM)", "Status", "Payment" };
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        bookingTable = new JTable(tableModel);
        bookingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainPanel.add(new JScrollPane(bookingTable), BorderLayout.CENTER);

        // ---------- Buttons ----------
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        if (customer != null && !paymentMode) {
            // Show cancel button for customers in history mode
            JButton cancelBtn = new JButton("Cancel Booking");
            cancelBtn.setBackground(new Color(180, 50, 50));
            cancelBtn.setForeground(Color.WHITE);
            cancelBtn.setFocusPainted(false);
            cancelBtn.addActionListener(e -> cancelSelectedBooking());
            btnPanel.add(cancelBtn);
        }

        if (paymentMode) {
            // Show Pay button in payment mode
            JButton payBtn = new JButton("Pay Now");
            payBtn.setBackground(new Color(20, 120, 20));
            payBtn.setForeground(Color.WHITE);
            payBtn.setFocusPainted(false);
            payBtn.addActionListener(e -> payForBooking());
            btnPanel.add(payBtn);
        }

        JButton closeBtn = new JButton("Close");
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(e -> dispose());
        btnPanel.add(closeBtn);

        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    /**
     * Load bookings into the table.
     * @param filter "All", "Upcoming", or "Past" — only used in customer mode.
     */
    private void loadBookings(String filter) {
        tableModel.setRowCount(0);
        List<Booking> bookings = FileManager.readBookings();
        LocalDate today = LocalDate.now();

        for (Booking b : bookings) {
            // If customer view, show only their own bookings
            if (customer != null && !b.getCustomerId().equals(customer.getUserId())) continue;

            // If payment mode, show only unpaid bookings
            if (paymentMode && b.getPaymentStatus().equals("PAID")) continue;

            // Apply upcoming / past filter
            if (filter != null && !filter.equals("All")) {
                try {
                    LocalDate eventDate = LocalDate.parse(b.getEventDate());
                    boolean isUpcoming  = !eventDate.isBefore(today);

                    if (filter.equals("Upcoming") && !isUpcoming) continue;
                    if (filter.equals("Past")     &&  isUpcoming) continue;
                } catch (Exception ex) {
                    // If date can't be parsed, show the booking anyway
                }
            }

            tableModel.addRow(new Object[]{
                b.getBookingId(),
                b.getHallName(),
                b.getEventDate(),
                b.getTimeRange(),
                String.format("%.2f", b.getTotalAmount()),
                b.getStatus(),
                b.getPaymentStatus()
            });
        }
    }

    /**
     * Cancel the selected booking.
     * Rule enforced: cancellation only allowed at least 3 days before the event date.
     */
    private void cancelSelectedBooking() {
        int row = bookingTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a booking to cancel."); return; }

        String bookingId = (String) tableModel.getValueAt(row, 0);
        String dateStr   = (String) tableModel.getValueAt(row, 2);
        String status    = (String) tableModel.getValueAt(row, 5);

        // Cannot cancel an already-cancelled booking
        if (status.equals(Booking.STATUS_CANCELLED)) {
            JOptionPane.showMessageDialog(this, "This booking is already cancelled.");
            return;
        }

        // Enforce 3-day cancellation rule
        try {
            LocalDate eventDate  = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            long daysUntilEvent  = ChronoUnit.DAYS.between(LocalDate.now(), eventDate);

            if (daysUntilEvent < 3) {
                JOptionPane.showMessageDialog(this,
                    "Cannot cancel this booking.\n" +
                    "Cancellation must be at least 3 days before the event date.\n" +
                    "(Days remaining: " + daysUntilEvent + ")",
                    "Cancellation Not Allowed", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error reading event date.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to cancel this booking?\nThis action cannot be undone.",
            "Confirm Cancellation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            List<Booking> bookings = FileManager.readBookings();
            for (Booking b : bookings) {
                if (b.getBookingId().equals(bookingId)) {
                    b.setStatus(Booking.STATUS_CANCELLED);
                    break;
                }
            }
            FileManager.writeBookings(bookings);

            // Reload with current filter
            String currentFilter = (filterCombo != null) ? (String) filterCombo.getSelectedItem() : "All";
            loadBookings(currentFilter);
            JOptionPane.showMessageDialog(this, "Booking cancelled successfully.");
        }
    }

    /** Process payment for the selected unpaid booking. */
    private void payForBooking() {
        int row = bookingTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a booking to pay for."); return; }

        String bookingId = (String) tableModel.getValueAt(row, 0);
        String amountStr = (String) tableModel.getValueAt(row, 4);
        double amount    = Double.parseDouble(amountStr);

        JComboBox<String> methodBox = new JComboBox<>(new String[]{
            Payment.METHOD_CASH, Payment.METHOD_CARD, Payment.METHOD_ONLINE
        });

        int result = JOptionPane.showConfirmDialog(this,
            new Object[]{
                String.format("Amount to pay: RM %.2f", amount),
                "Select Payment Method:", methodBox
            },
            "Make Payment", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String method    = (String) methodBox.getSelectedItem();
            String today     = LocalDate.now().toString();
            String paymentId = FileManager.generateId("P");

            // Mark booking as PAID and CONFIRMED
            List<Booking> bookings = FileManager.readBookings();
            Booking paid = null;
            for (Booking b : bookings) {
                if (b.getBookingId().equals(bookingId)) {
                    b.setPaymentStatus("PAID");
                    b.setStatus(Booking.STATUS_CONFIRMED);
                    paid = b;
                    break;
                }
            }
            FileManager.writeBookings(bookings);

            // Save payment record
            Payment payment = new Payment(paymentId, bookingId, amount, method, today);
            List<Payment> payments = FileManager.readPayments();
            payments.add(payment);
            FileManager.writePayments(payments);

            // Show receipt in a popup
            if (paid != null) {
                String receipt = payment.generateReceipt(
                    paid.getHallName(), paid.getEventDate(), paid.getTimeRange());
                JOptionPane.showMessageDialog(this, receipt, "Payment Receipt", JOptionPane.INFORMATION_MESSAGE);
            }

            loadBookings("All");
        }
    }
}
