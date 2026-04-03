package gui;

import model.*;
import util.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * IssueFrame.java
 * Allows a customer to raise a new issue or view their existing issues.
 * Customers can click on an issue to see full details including the manager response.
 */
public class IssueFrame extends JFrame {

    private final Customer customer;
    private DefaultTableModel tableModel;
    private JTable table;

    public IssueFrame(Customer customer) {
        this.customer = customer;
        setTitle("My Issues");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 440);
        setLocationRelativeTo(null);
        initComponents();
        loadIssues();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("My Issues / Complaints", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 15));
        mainPanel.add(title, BorderLayout.NORTH);

        String[] cols = { "Issue ID", "Booking ID", "Description", "Status", "Assigned To", "Manager Response" };
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Click row to view full details
        table.getSelectionModel().addListSelectionListener(ev -> {
            if (ev.getValueIsAdjusting()) return;
            int row = table.getSelectedRow();
            if (row == -1) return;
            String issueId = (String) tableModel.getValueAt(row, 0);
            for (Issue i : FileManager.readIssues()) {
                if (i.getIssueId().equals(issueId)) {
                    showIssueDetails(i);
                    break;
                }
            }
        });

        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton raiseBtn = new JButton("Raise New Issue");
        raiseBtn.setBackground(new Color(30, 80, 160));
        raiseBtn.setForeground(Color.WHITE);
        raiseBtn.addActionListener(e -> showRaiseIssueDialog());

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());

        btnPanel.add(raiseBtn);
        btnPanel.add(closeBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void loadIssues() {
        tableModel.setRowCount(0);
        for (Issue i : FileManager.readIssues()) {
            if (i.getCustomerId().equals(customer.getUserId())) {
                tableModel.addRow(new Object[]{
                    i.getIssueId(), i.getBookingId(), i.getDescription(),
                    i.getStatus(), i.getAssignedSchedulerId(),
                    i.getManagerResponse().isEmpty() ? "(Awaiting response)" : i.getManagerResponse()
                });
            }
        }
    }

    /** Show full details of an issue including manager response. */
    private void showIssueDetails(Issue issue) {
        String details =
            "Issue ID       : " + issue.getIssueId() + "\n" +
            "Booking ID     : " + issue.getBookingId() + "\n" +
            "Status         : " + issue.getStatus() + "\n" +
            "Assigned To    : " + (issue.getAssignedSchedulerId().isEmpty() ? "Not yet assigned" : issue.getAssignedSchedulerId()) + "\n\n" +
            "Your Issue Description:\n" + issue.getDescription() + "\n\n" +
            "Manager Response:\n" + (issue.getManagerResponse().isEmpty() ? "(No response yet. Please check back later.)" : issue.getManagerResponse());

        JTextArea area = new JTextArea(details, 13, 40);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, new JScrollPane(area),
            "Issue Details: " + issue.getIssueId(), JOptionPane.INFORMATION_MESSAGE);
    }

    private void showRaiseIssueDialog() {
        List<Booking> bookings = FileManager.readBookings();
        JComboBox<String> bookingBox = new JComboBox<>();
        for (Booking b : bookings) {
            if (b.getCustomerId().equals(customer.getUserId()) &&
                b.getStatus().equals(Booking.STATUS_CONFIRMED)) {
                bookingBox.addItem(b.getBookingId() + " | " + b.getHallName() + " on " + b.getEventDate());
            }
        }

        if (bookingBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "You have no confirmed bookings to raise an issue for.");
            return;
        }

        JTextArea description = new JTextArea(4, 25);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(description);

        Object[] fields = { "Select Booking:", bookingBox, "Describe the issue:", scrollDesc };
        int result = JOptionPane.showConfirmDialog(this, fields, "Raise an Issue", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String desc = description.getText().trim();
            if (desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please describe the issue.");
                return;
            }
            String selected  = (String) bookingBox.getSelectedItem();
            String bookingId = selected.split(" \\| ")[0];
            String issueId   = FileManager.generateId("I");
            Issue issue = new Issue(issueId, customer.getUserId(), bookingId, desc, Issue.STATUS_OPEN, "");
            List<Issue> issues = FileManager.readIssues();
            issues.add(issue);
            FileManager.writeIssues(issues);
            loadIssues();
            JOptionPane.showMessageDialog(this, "Issue raised successfully. A manager will review it soon.");
        }
    }
}
