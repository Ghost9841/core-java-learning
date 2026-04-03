package gui;

import model.*;
import util.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * SalesDashboardFrame.java
 * Displays revenue and booking statistics for the Manager.
 * Allows filtering by Weekly, Monthly, or Yearly period.
 */
public class SalesDashboardFrame extends JFrame {

    private JLabel totalRevenueLabel;
    private JLabel totalBookingsLabel;
    private JLabel confirmedLabel;
    private JLabel cancelledLabel;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterCombo;

    public SalesDashboardFrame() {
        setTitle("Sales Dashboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 520);
        setLocationRelativeTo(null);
        initComponents();
        loadData("Monthly"); // Default view
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel title = new JLabel("Sales Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(80, 0, 150));
        mainPanel.add(title, BorderLayout.NORTH);

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter Period"));
        filterPanel.add(new JLabel("View:"));
        filterCombo = new JComboBox<>(new String[]{ "Weekly", "Monthly", "Yearly" });
        filterCombo.setSelectedItem("Monthly");
        JButton applyBtn = new JButton("Apply");
        filterPanel.add(filterCombo);
        filterPanel.add(applyBtn);
        applyBtn.addActionListener(e -> loadData((String) filterCombo.getSelectedItem()));

        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Summary"));

        totalRevenueLabel  = createStatLabel("Revenue: RM 0.00", new Color(20, 100, 20));
        totalBookingsLabel = createStatLabel("Total Bookings: 0", new Color(30, 80, 160));
        confirmedLabel     = createStatLabel("Confirmed: 0", new Color(0, 130, 0));
        cancelledLabel     = createStatLabel("Cancelled: 0", new Color(180, 50, 50));

        statsPanel.add(totalRevenueLabel);
        statsPanel.add(totalBookingsLabel);
        statsPanel.add(confirmedLabel);
        statsPanel.add(cancelledLabel);

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.add(filterPanel, BorderLayout.NORTH);
        topSection.add(statsPanel, BorderLayout.CENTER);
        mainPanel.add(topSection, BorderLayout.CENTER);

        // Bookings table
        String[] cols = { "Booking ID", "Hall", "Date", "Time", "Amount (RM)", "Status" };
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Booking Details"));
        mainPanel.add(scroll, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JLabel createStatLabel(String text, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(color);
        label.setBorder(BorderFactory.createLineBorder(color, 1));
        label.setOpaque(true);
        label.setBackground(new Color(245, 245, 255));
        return label;
    }

    /**
     * Load and display sales data filtered by period (Weekly, Monthly, Yearly).
     */
    private void loadData(String period) {
        tableModel.setRowCount(0);

        List<Booking> bookings = FileManager.readBookings();
        List<Payment> payments = FileManager.readPayments();

        LocalDate now = LocalDate.now();
        LocalDate cutoff;

        switch (period) {
            case "Weekly":  cutoff = now.minusWeeks(1); break;
            case "Yearly":  cutoff = now.minusYears(1); break;
            default:        cutoff = now.minusMonths(1); break; // Monthly
        }

        int total = 0, confirmed = 0, cancelled = 0;
        double revenue = 0;

        for (Booking b : bookings) {
            // Parse booking date and check if within period
            try {
                LocalDate bookingDate = LocalDate.parse(b.getEventDate());
                if (bookingDate.isBefore(cutoff)) continue;
            } catch (Exception ex) {
                continue;
            }

            total++;
            if (b.getStatus().equals(Booking.STATUS_CONFIRMED)) {
                confirmed++;
                if (b.getPaymentStatus().equals("PAID")) revenue += b.getTotalAmount();
            } else if (b.getStatus().equals(Booking.STATUS_CANCELLED)) {
                cancelled++;
            }

            tableModel.addRow(new Object[]{
                b.getBookingId(), b.getHallName(), b.getEventDate(),
                b.getTimeRange(), String.format("%.2f", b.getTotalAmount()), b.getStatus()
            });
        }

        // Update summary labels
        totalRevenueLabel.setText("Revenue: RM " + String.format("%.2f", revenue));
        totalBookingsLabel.setText("Total Bookings: " + total);
        confirmedLabel.setText("Confirmed: " + confirmed);
        cancelledLabel.setText("Cancelled: " + cancelled);
    }
}
