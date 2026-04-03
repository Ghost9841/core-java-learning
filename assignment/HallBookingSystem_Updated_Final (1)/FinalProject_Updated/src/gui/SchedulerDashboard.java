package gui;

import model.*;
import util.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * SchedulerDashboard.java
 * Main menu for a logged-in Scheduler staff member.
 *
 * Features:
 * - Manage Halls (Add / Edit / Delete / Toggle Availability)
 * - Set Hall Availability Schedule
 * - Set Hall Maintenance Schedule
 * - View All Bookings
 * - View My Assigned Issues
 * - Logout
 *
 * OOP Concepts:
 * - INHERITANCE: Scheduler extends User
 * - ENCAPSULATION: scheduler field is private final
 */
public class SchedulerDashboard extends JFrame {

    private final Scheduler scheduler;

    public SchedulerDashboard(Scheduler scheduler) {
        this.scheduler = scheduler;
        setTitle("Scheduler Dashboard - " + scheduler.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(440, 480);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(240, 255, 240));

        JLabel titleLabel = new JLabel("Scheduler: " + scheduler.getUsername(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(20, 120, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        btnPanel.setBackground(new Color(240, 255, 240));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        String[] btnLabels = {
            "Manage Halls (Add / Edit / Delete)",
            "View All Halls",
            "Set Hall Availability Schedule",
            "Set Hall Maintenance Schedule",
            "View All Bookings",
            "View My Assigned Issues",
            "Logout"
        };

        for (String label : btnLabels) {
            JButton btn = createButton(label);
            btnPanel.add(btn);

            btn.addActionListener(e -> {
                switch (label) {
                    case "Manage Halls (Add / Edit / Delete)":
                        new HallManagementFrame(scheduler, false).setVisible(true);
                        break;
                    case "View All Halls":
                        new HallManagementFrame(scheduler, true).setVisible(true);
                        break;
                    case "Set Hall Availability Schedule":
                        showScheduleDialog("Availability");
                        break;
                    case "Set Hall Maintenance Schedule":
                        showScheduleDialog("Maintenance");
                        break;
                    case "View All Bookings":
                        new BookingHistoryFrame(null).setVisible(true);
                        break;
                    case "View My Assigned Issues":
                        showAssignedIssues();
                        break;
                    case "Logout":
                        logout();
                        break;
                }
            });
        }

        mainPanel.add(btnPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    /** Shows issues assigned to this scheduler by the manager. */
    private void showAssignedIssues() {
        List<Issue> allIssues = FileManager.readIssues();

        String[] cols = { "Issue ID", "Customer ID", "Booking ID", "Description", "Status", "Manager Response" };
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        for (Issue i : allIssues) {
            if (scheduler.getUserId().equals(i.getAssignedSchedulerId())) {
                model.addRow(new Object[]{
                    i.getIssueId(), i.getCustomerId(), i.getBookingId(),
                    i.getDescription(), i.getStatus(),
                    i.getManagerResponse().isEmpty() ? "(No response)" : i.getManagerResponse()
                });
            }
        }

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Click row to view full details
        table.getSelectionModel().addListSelectionListener(ev -> {
            if (ev.getValueIsAdjusting()) return;
            int row = table.getSelectedRow();
            if (row == -1) return;
            String issueId = (String) table.getValueAt(row, 0);
            for (Issue i : FileManager.readIssues()) {
                if (i.getIssueId().equals(issueId)) {
                    String details =
                        "Issue ID       : " + i.getIssueId() + "\n" +
                        "Customer ID    : " + i.getCustomerId() + "\n" +
                        "Booking ID     : " + i.getBookingId() + "\n" +
                        "Status         : " + i.getStatus() + "\n\n" +
                        "Description:\n" + i.getDescription() + "\n\n" +
                        "Manager Response:\n" + (i.getManagerResponse().isEmpty() ? "(No response yet)" : i.getManagerResponse());
                    JTextArea area = new JTextArea(details, 12, 38);
                    area.setEditable(false);
                    area.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    JOptionPane.showMessageDialog(this, new JScrollPane(area),
                        "Issue Details: " + i.getIssueId(), JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        });

        JFrame frame = new JFrame("My Assigned Issues - " + scheduler.getUsername());
        frame.setSize(850, 400);
        frame.setLocationRelativeTo(this);
        frame.setLayout(new BorderLayout(5, 5));
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnRow = new JPanel(new FlowLayout());
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> frame.dispose());
        btnRow.add(closeBtn);
        frame.add(btnRow, BorderLayout.SOUTH);

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No issues are currently assigned to you.");
            return;
        }
        frame.setVisible(true);
    }

    private void showScheduleDialog(String type) {
        List<Hall> halls = FileManager.readHalls();
        if (halls.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No halls found. Please add a hall first.");
            return;
        }

        JComboBox<String> hallCombo = new JComboBox<>();
        for (Hall h : halls) hallCombo.addItem(h.getHallId() + " | " + h.getHallName() + " (" + h.getHallType() + ")");

        JTextField startDateField = new JTextField(LocalDate.now().toString(), 12);
        JTextField endDateField   = new JTextField(LocalDate.now().plusDays(1).toString(), 12);

        JTextArea remarksArea = new JTextArea(3, 25);
        remarksArea.setLineWrap(true);
        remarksArea.setWrapStyleWord(true);
        if (type.equals("Availability")) {
            remarksArea.setText("Example: Hall reserved for July 2024 Conference on Sustainable Energy.");
        } else {
            remarksArea.setText("Example: Air conditioning check and lighting repairs.");
        }
        JScrollPane remarksScroll = new JScrollPane(remarksArea);

        Object[] fields = {
            "Select Hall:", hallCombo,
            "Start Date (YYYY-MM-DD):", startDateField,
            "End Date (YYYY-MM-DD):", endDateField,
            "Remarks (optional):", remarksScroll
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Set Hall " + type + " Schedule", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String startStr = startDateField.getText().trim();
            String endStr   = endDateField.getText().trim();
            LocalDate startDate, endDate;
            try {
                startDate = LocalDate.parse(startStr);
                endDate   = LocalDate.parse(endStr);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.\nExample: 2025-08-01");
                return;
            }
            if (!endDate.isAfter(startDate)) {
                JOptionPane.showMessageDialog(this, "End date must be after start date.");
                return;
            }
            String selectedItem = (String) hallCombo.getSelectedItem();
            String hallId = selectedItem.split(" \\| ")[0];
            String remarks = remarksArea.getText().trim();

            List<Hall> allHalls = FileManager.readHalls();
            String hallName = "";
            for (Hall h : allHalls) {
                if (h.getHallId().equals(hallId)) {
                    h.setAvailable(type.equals("Availability"));
                    hallName = h.getHallName();
                    break;
                }
            }
            FileManager.writeHalls(allHalls);

            String summary =
                "Schedule Set Successfully!\n" +
                "================================\n" +
                "Type     : " + type + "\n" +
                "Hall     : " + hallName + "\n" +
                "Start    : " + startDate + "\n" +
                "End      : " + endDate + "\n" +
                (remarks.isEmpty() ? "" : "Remarks  : " + remarks + "\n") +
                "================================\n" +
                (type.equals("Maintenance")
                    ? "Hall marked as UNAVAILABLE for this period."
                    : "Hall marked as AVAILABLE for this period.");

            JOptionPane.showMessageDialog(this, summary, type + " Schedule Saved", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(20, 120, 20));
        btn.setForeground(Color.WHITE);
        if (text.equals("Logout"))                   btn.setBackground(new Color(180, 50, 50));
        if (text.contains("Maintenance"))            btn.setBackground(new Color(180, 100, 0));
        if (text.contains("Availability Schedule"))  btn.setBackground(new Color(0, 100, 180));
        if (text.contains("Assigned Issues"))        btn.setBackground(new Color(80, 0, 150));
        return btn;
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}
