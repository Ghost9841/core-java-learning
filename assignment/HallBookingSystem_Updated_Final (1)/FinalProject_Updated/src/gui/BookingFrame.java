package gui;

import model.*;
import util.FileManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * BookingFrame.java
 * Allows a customer to select a hall, choose a date and time,
 * and either Book and Pay immediately, or Book Now and Pay Later.
 *
 * Opens maximized (full screen). Uses a clean two-column form layout
 * where every label and every input field share the same row height
 * and both columns are properly bordered.
 *
 * Business Rules:
 * - Bookings only allowed between 8:00 AM and 6:00 PM (hours 8 to 18)
 * - No double booking for the same hall, date, and time slot
 * - Price is calculated automatically when selections change
 */
public class BookingFrame extends JFrame {

    private final Customer customer;
    private JComboBox<String> hallCombo;
    private JTextField        dateField;
    private JComboBox<Integer> startHourCombo;
    private JComboBox<Integer> endHourCombo;
    private JLabel            priceLabel;
    private JComboBox<String> paymentMethodCombo;

    // Shared colours used throughout the form
    private static final Color LABEL_BG        = new Color(230, 240, 255);
    private static final Color LABEL_FG        = new Color(20,  60, 140);
    private static final Color LABEL_BORDER    = new Color(100, 140, 210);
    private static final Color FIELD_BORDER    = new Color(160, 180, 220);
    private static final Color PRICE_FG        = new Color(10,  110,  10);
    private static final Color PRICE_BORDER    = new Color(10,  110,  10);
    private static final Color PAGE_BG         = new Color(240, 246, 255);
    private static final Color SECTION_BORDER  = new Color(100, 140, 210);

    public BookingFrame(Customer customer) {
        this.customer = customer;
        setTitle("Book a Hall");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Open maximized / full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(600, 500));
        initComponents();
    }

    // -------------------------------------------------------------------------
    //  UI Construction
    // -------------------------------------------------------------------------

    private void initComponents() {
        // Outer wrapper — centres the card in the window
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(PAGE_BG);
        add(wrapper);

        // Card panel that holds everything
        JPanel card = new JPanel(new BorderLayout(0, 20));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECTION_BORDER, 2),
            BorderFactory.createEmptyBorder(30, 40, 25, 40)
        ));

        // --- Title ---
        JLabel title = new JLabel("Book a Hall", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(LABEL_FG);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        card.add(title, BorderLayout.NORTH);

        // --- Form section ---
        JPanel formSection = new JPanel(new BorderLayout(0, 12));
        formSection.setBackground(Color.WHITE);
        formSection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(SECTION_BORDER, 1),
            "  Booking Details  ",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13), LABEL_FG
        ));

        JPanel formGrid = new JPanel(new GridBagLayout());
        formGrid.setBackground(Color.WHITE);
        formGrid.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        GridBagConstraints lc = labelConstraints();
        GridBagConstraints fc = fieldConstraints();

        // Row 0 — Hall
        lc.gridy = fc.gridy = 0;
        formGrid.add(buildLabel("Select Hall:"), lc);
        hallCombo = new JComboBox<>();
        loadHallsIntoCombo();
        styleCombo(hallCombo);
        formGrid.add(hallCombo, fc);

        // Row 1 — Date
        lc.gridy = fc.gridy = 1;
        formGrid.add(buildLabel("Event Date  (YYYY-MM-DD):"), lc);
        dateField = new JTextField(LocalDate.now().plusDays(1).toString());
        styleField(dateField);
        formGrid.add(dateField, fc);

        // Row 2 — Start hour
        lc.gridy = fc.gridy = 2;
        formGrid.add(buildLabel("Start Hour  (8 – 17):"), lc);
        startHourCombo = new JComboBox<>();
        for (int h = 8; h <= 17; h++) startHourCombo.addItem(h);
        styleCombo(startHourCombo);
        formGrid.add(startHourCombo, fc);

        // Row 3 — End hour
        lc.gridy = fc.gridy = 3;
        formGrid.add(buildLabel("End Hour  (9 – 18):"), lc);
        endHourCombo = new JComboBox<>();
        for (int h = 9; h <= 18; h++) endHourCombo.addItem(h);
        endHourCombo.setSelectedItem(10);
        styleCombo(endHourCombo);
        formGrid.add(endHourCombo, fc);

        // Row 4 — Estimated price (read-only display)
        lc.gridy = fc.gridy = 4;
        formGrid.add(buildLabel("Estimated Price:"), lc);
        priceLabel = new JLabel("RM 0.00", SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 15));
        priceLabel.setForeground(PRICE_FG);
        priceLabel.setOpaque(true);
        priceLabel.setBackground(new Color(230, 255, 230));
        priceLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRICE_BORDER, 2),
            BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));
        // Wrap in a left-aligned panel so it doesn't stretch across the whole column
        JPanel priceWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        priceWrap.setBackground(Color.WHITE);
        priceWrap.add(priceLabel);
        formGrid.add(priceWrap, fc);

        // Row 5 — Payment method
        lc.gridy = fc.gridy = 5;
        formGrid.add(buildLabel("Payment Method:"), lc);
        paymentMethodCombo = new JComboBox<>(new String[]{
            Payment.METHOD_CASH, Payment.METHOD_CARD, Payment.METHOD_ONLINE
        });
        styleCombo(paymentMethodCombo);
        formGrid.add(paymentMethodCombo, fc);

        formSection.add(formGrid, BorderLayout.CENTER);
        card.add(formSection, BorderLayout.CENTER);

        // --- Button row ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnPanel.setBackground(Color.WHITE);

        JButton bookPayBtn   = buildButton("Book & Pay Now",       new Color(20, 130, 20));
        JButton bookLaterBtn = buildButton("Book Now, Pay Later",  new Color(30,  80, 160));
        JButton cancelBtn    = buildButton("Cancel",               new Color(180, 50,  50));

        btnPanel.add(bookPayBtn);
        btnPanel.add(bookLaterBtn);
        btnPanel.add(cancelBtn);
        card.add(btnPanel, BorderLayout.SOUTH);

        // Place card in centre of wrapper with a max width
        GridBagConstraints wc = new GridBagConstraints();
        wc.anchor = GridBagConstraints.CENTER;
        Dimension cardSize = new Dimension(640, 520);
        card.setPreferredSize(cardSize);
        wrapper.add(card, wc);

        // --- Listeners ---
        hallCombo.addActionListener(e -> calculatePrice());
        startHourCombo.addActionListener(e -> calculatePrice());
        endHourCombo.addActionListener(e -> calculatePrice());

        bookPayBtn.addActionListener(e -> handleBooking(true));
        bookLaterBtn.addActionListener(e -> handleBooking(false));
        cancelBtn.addActionListener(e -> dispose());

        calculatePrice();
    }

    // -------------------------------------------------------------------------
    //  GridBagConstraints helpers
    // -------------------------------------------------------------------------

    /** Constraints for the LEFT (label) column. */
    private GridBagConstraints labelConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx   = 0;
        c.fill    = GridBagConstraints.BOTH;
        c.anchor  = GridBagConstraints.WEST;
        c.insets  = new Insets(6, 4, 6, 0);
        c.weightx = 0.38;
        return c;
    }

    /** Constraints for the RIGHT (field) column. */
    private GridBagConstraints fieldConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx   = 1;
        c.fill    = GridBagConstraints.HORIZONTAL;
        c.anchor  = GridBagConstraints.WEST;
        c.insets  = new Insets(6, 8, 6, 4);
        c.weightx = 0.62;
        return c;
    }

    // -------------------------------------------------------------------------
    //  Widget factory helpers
    // -------------------------------------------------------------------------

    /** Styled label: coloured background + border, matching height to fields. */
    private JLabel buildLabel(String text) {
        JLabel lbl = new JLabel("  " + text, SwingConstants.LEFT);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        lbl.setForeground(LABEL_FG);
        lbl.setOpaque(true);
        lbl.setBackground(LABEL_BG);
        lbl.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LABEL_BORDER, 1),
            BorderFactory.createEmptyBorder(6, 4, 6, 4)
        ));
        return lbl;
    }

    /** Applies a consistent border and preferred height to a text field. */
    private void styleField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(FIELD_BORDER, 1),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
    }

    /** Applies a consistent border to a combo box. */
    private <T> void styleCombo(JComboBox<T> combo) {
        combo.setFont(new Font("Arial", Font.PLAIN, 13));
        combo.setBorder(BorderFactory.createLineBorder(FIELD_BORDER, 1));
        combo.setBackground(Color.WHITE);
    }

    /** Creates a styled action button. */
    private JButton buildButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bg.darker(), 1),
            BorderFactory.createEmptyBorder(9, 22, 9, 22)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // -------------------------------------------------------------------------
    //  Business logic
    // -------------------------------------------------------------------------

    private void loadHallsIntoCombo() {
        hallCombo.removeAllItems();
        for (Hall h : FileManager.readHalls()) {
            if (h.isAvailable()) {
                hallCombo.addItem(h.getHallId() + " | " + h.getHallName()
                    + " (" + h.getHallType() + ") - RM" + h.getRatePerHour() + "/hr");
            }
        }
    }

    private void calculatePrice() {
        priceLabel.setText(String.format("RM %.2f", getCalculatedPrice()));
    }

    private double getCalculatedPrice() {
        if (hallCombo.getSelectedItem() == null) return 0;
        String selected = (String) hallCombo.getSelectedItem();
        Hall hall = findHallById(selected.split(" \\| ")[0]);
        if (hall == null) return 0;
        int start = (int) startHourCombo.getSelectedItem();
        int end   = (int) endHourCombo.getSelectedItem();
        return (end > start) ? (end - start) * hall.getRatePerHour() : 0;
    }

    private Hall findHallById(String hallId) {
        for (Hall h : FileManager.readHalls()) { if (h.getHallId().equals(hallId)) return h; }
        return null;
    }

    /**
     * Core booking handler.
     * @param payNow true  → Book & Pay immediately
     *               false → Book now, pay later (UNPAID status)
     */
    private void handleBooking(boolean payNow) {
        if (hallCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "No halls available. Please ask staff to add halls.");
            return;
        }

        String selected = (String) hallCombo.getSelectedItem();
        String hallId   = selected.split(" \\| ")[0];
        Hall   hall     = findHallById(hallId);
        String dateStr  = dateField.getText().trim();
        int startHour   = (int) startHourCombo.getSelectedItem();
        int endHour     = (int) endHourCombo.getSelectedItem();

        // Date validation
        LocalDate eventDate;
        try {
            eventDate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD (e.g. 2025-12-01).");
            return;
        }
        if (!eventDate.isAfter(LocalDate.now())) {
            JOptionPane.showMessageDialog(this, "Event date must be in the future.");
            return;
        }
        if (startHour < 8 || endHour > 18) {
            JOptionPane.showMessageDialog(this, "Bookings are only allowed between 8:00 AM and 6:00 PM.");
            return;
        }
        if (endHour <= startHour) {
            JOptionPane.showMessageDialog(this, "End hour must be after start hour.");
            return;
        }
        if (isDoubleBooked(hallId, dateStr, startHour, endHour)) {
            JOptionPane.showMessageDialog(this,
                "This hall is already booked for the selected time slot. Please choose a different time.");
            return;
        }

        double totalAmount   = getCalculatedPrice();
        String paymentMethod = (String) paymentMethodCombo.getSelectedItem();
        String payLaterNote  = payNow ? "" : "\nPayment: To be paid later via 'Make Payment'";

        int confirm = JOptionPane.showConfirmDialog(this,
            String.format("Confirm booking?\n\nHall  : %s\nDate  : %s\nTime  : %02d:00 – %02d:00\nTotal : RM %.2f%s",
                hall.getHallName(), dateStr, startHour, endHour, totalAmount, payLaterNote),
            "Confirm Booking", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String bookingId     = FileManager.generateId("B");
        String paymentStatus = payNow ? "PAID" : "UNPAID";

        Booking booking = new Booking(
            bookingId, customer.getUserId(), hallId, hall.getHallName(),
            dateStr, startHour, endHour, totalAmount,
            Booking.STATUS_CONFIRMED, paymentStatus
        );
        List<Booking> bookings = FileManager.readBookings();
        bookings.add(booking);
        FileManager.writeBookings(bookings);

        if (payNow) {
            String paymentId = FileManager.generateId("P");
            Payment payment  = new Payment(paymentId, bookingId, totalAmount,
                                           paymentMethod, LocalDate.now().toString());
            List<Payment> payments = FileManager.readPayments();
            payments.add(payment);
            FileManager.writePayments(payments);
            String receipt = payment.generateReceipt(
                hall.getHallName(), dateStr,
                String.format("%02d:00 - %02d:00", startHour, endHour));
            JOptionPane.showMessageDialog(this, receipt, "Payment Receipt", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Booking confirmed!\nBooking ID: " + bookingId
                + "\nYou can complete the payment later via 'Make Payment' on your dashboard.",
                "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
        }
        dispose();
    }

    private boolean isDoubleBooked(String hallId, String date, int newStart, int newEnd) {
        for (Booking b : FileManager.readBookings()) {
            if (!b.getHallId().equals(hallId))             continue;
            if (!b.getEventDate().equals(date))            continue;
            if (b.getStatus().equals(Booking.STATUS_CANCELLED)) continue;
            if (newStart < b.getEndHour() && newEnd > b.getStartHour()) return true;
        }
        return false;
    }
}
