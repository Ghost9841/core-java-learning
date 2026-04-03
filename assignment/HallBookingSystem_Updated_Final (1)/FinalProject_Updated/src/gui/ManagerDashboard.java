package gui;

import model.*;
import util.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * ManagerDashboard.java
 * Manager panel for viewing sales reports and handling customer issues.
 *
 * OOP Concepts:
 * - INHERITANCE: Manager extends User
 * - ENCAPSULATION: manager field is private and final
 */
public class ManagerDashboard extends JFrame {

    private final Manager manager;

    public ManagerDashboard(Manager manager) {
        this.manager = manager;
        setTitle("Manager Dashboard - " + manager.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 320);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(245, 235, 255));

        JLabel titleLabel = new JLabel("Manager: " + manager.getUsername(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 17));
        titleLabel.setForeground(new Color(80, 0, 150));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        btnPanel.setBackground(new Color(245, 235, 255));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        String[] btnLabels = { "View Sales Dashboard", "View & Manage Issues", "Logout" };

        for (String label : btnLabels) {
            JButton btn = createButton(label);
            btnPanel.add(btn);
            btn.addActionListener(e -> handleAction(label));
        }

        mainPanel.add(btnPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void handleAction(String action) {
        switch (action) {
            case "View Sales Dashboard":
                new SalesDashboardFrame().setVisible(true);
                break;
            case "View & Manage Issues":
                showIssuesTable();
                break;
            case "Logout":
                int c = JOptionPane.showConfirmDialog(this, "Logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (c == JOptionPane.YES_OPTION) { new LoginFrame().setVisible(true); dispose(); }
                break;
        }
    }

    /**
     * Shows all customer issues in a table with status filter.
     * Clicking a row shows full issue details.
     * Manager can assign a Scheduler, change status, and respond to the issue.
     */
    private void showIssuesTable() {
        JFrame frame = new JFrame("Customer Issues");
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(this);
        frame.setLayout(new BorderLayout(5, 5));

        // --- Filter Panel ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter Issues"));
        filterPanel.setBackground(new Color(248, 244, 255));

        JComboBox<String> statusFilter = new JComboBox<>(new String[]{
            "All",
            Issue.STATUS_OPEN,
            Issue.STATUS_IN_PROGRESS,
            Issue.STATUS_DONE,
            Issue.STATUS_CLOSED,
            Issue.STATUS_CANCELLED
        });
        filterPanel.add(new JLabel("Filter by Status:"));
        filterPanel.add(statusFilter);

        JButton applyBtn = new JButton("Apply Filter");
        applyBtn.setBackground(new Color(80, 0, 150));
        applyBtn.setForeground(Color.WHITE);
        filterPanel.add(applyBtn);
        frame.add(filterPanel, BorderLayout.NORTH);

        // --- Table ---
        String[] cols = { "Issue ID", "Customer ID", "Booking ID", "Description", "Status", "Assigned To" };
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        Runnable loadIssues = () -> {
            model.setRowCount(0);
            String selectedStatus = (String) statusFilter.getSelectedItem();
            for (Issue i : FileManager.readIssues()) {
                if (!"All".equals(selectedStatus) && !i.getStatus().equals(selectedStatus)) continue;
                model.addRow(new Object[]{
                    i.getIssueId(), i.getCustomerId(), i.getBookingId(),
                    i.getDescription(), i.getStatus(), i.getAssignedSchedulerId()
                });
            }
        };

        loadIssues.run();
        applyBtn.addActionListener(e -> loadIssues.run());

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Show full details when a row is clicked
        table.getSelectionModel().addListSelectionListener(ev -> {
            if (ev.getValueIsAdjusting()) return;
            int row = table.getSelectedRow();
            if (row == -1) return;
            String issueId = (String) table.getValueAt(row, 0);
            for (Issue i : FileManager.readIssues()) {
                if (i.getIssueId().equals(issueId)) {
                    showIssueDetails(frame, i, model, row, loadIssues);
                    break;
                }
            }
        });

        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Action Buttons ---
        JButton assignBtn = new JButton("Assign Scheduler");
        assignBtn.setBackground(new Color(30, 80, 160));
        assignBtn.setForeground(Color.WHITE);
        assignBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(frame, "Please select an issue first."); return; }
            List<User> users = FileManager.readUsers();
            JComboBox<String> schedulerBox = new JComboBox<>();
            for (User u : users) {
                if (u.getRole().equals("SCHEDULER")) schedulerBox.addItem(u.getUserId() + " - " + u.getUsername());
            }
            if (schedulerBox.getItemCount() == 0) {
                JOptionPane.showMessageDialog(frame, "No schedulers found. Ask Admin to add one."); return;
            }
            int result = JOptionPane.showConfirmDialog(frame,
                new Object[]{ "Select Scheduler to assign:", schedulerBox },
                "Assign Scheduler", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String selected    = (String) schedulerBox.getSelectedItem();
                String schedulerId = selected.split(" - ")[0];
                String issueId     = (String) table.getValueAt(row, 0);
                List<Issue> allIssues = FileManager.readIssues();
                for (Issue i : allIssues) {
                    if (i.getIssueId().equals(issueId)) {
                        i.setAssignedSchedulerId(schedulerId);
                        i.setStatus(Issue.STATUS_IN_PROGRESS);
                        model.setValueAt(Issue.STATUS_IN_PROGRESS, row, 4);
                        model.setValueAt(schedulerId, row, 5);
                        break;
                    }
                }
                FileManager.writeIssues(allIssues);
                JOptionPane.showMessageDialog(frame, "Scheduler assigned. Status set to IN PROGRESS.");
            }
        });

        JButton statusBtn = new JButton("Change Status");
        statusBtn.setBackground(new Color(80, 0, 150));
        statusBtn.setForeground(Color.WHITE);
        statusBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(frame, "Please select an issue first."); return; }
            String[] options = { Issue.STATUS_IN_PROGRESS, Issue.STATUS_DONE, Issue.STATUS_CLOSED, Issue.STATUS_CANCELLED };
            JComboBox<String> statusBox = new JComboBox<>(options);
            String cur = (String) table.getValueAt(row, 4);
            for (String s : options) if (s.equals(cur)) { statusBox.setSelectedItem(s); break; }
            int result = JOptionPane.showConfirmDialog(frame,
                new Object[]{ "Select new status:", statusBox }, "Change Issue Status", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String newStatus = (String) statusBox.getSelectedItem();
                String issueId   = (String) table.getValueAt(row, 0);
                List<Issue> allIssues = FileManager.readIssues();
                for (Issue i : allIssues) {
                    if (i.getIssueId().equals(issueId)) { i.setStatus(newStatus); model.setValueAt(newStatus, row, 4); break; }
                }
                FileManager.writeIssues(allIssues);
                JOptionPane.showMessageDialog(frame, "Issue status updated to: " + newStatus);
            }
        });

        JButton respondBtn = new JButton("Respond to Issue");
        respondBtn.setBackground(new Color(0, 130, 100));
        respondBtn.setForeground(Color.WHITE);
        respondBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(frame, "Please select an issue first."); return; }
            String issueId = (String) table.getValueAt(row, 0);
            Issue target = null;
            for (Issue i : FileManager.readIssues()) { if (i.getIssueId().equals(issueId)) { target = i; break; } }
            if (target == null) return;

            JTextArea responseArea = new JTextArea(5, 30);
            responseArea.setLineWrap(true);
            responseArea.setWrapStyleWord(true);
            responseArea.setText(target.getManagerResponse());
            JScrollPane sp = new JScrollPane(responseArea);

            int res = JOptionPane.showConfirmDialog(frame,
                new Object[]{ "Enter your response (visible to customer):", sp },
                "Respond to Issue: " + issueId, JOptionPane.OK_CANCEL_OPTION);

            if (res == JOptionPane.OK_OPTION) {
                String responseText = responseArea.getText().trim();
                List<Issue> allIssues = FileManager.readIssues();
                for (Issue i : allIssues) {
                    if (i.getIssueId().equals(issueId)) { i.setManagerResponse(responseText); break; }
                }
                FileManager.writeIssues(allIssues);
                JOptionPane.showMessageDialog(frame, "Response saved. Customer can now view it.");
            }
        });

        JPanel btnRow = new JPanel(new FlowLayout());
        btnRow.add(assignBtn);
        btnRow.add(statusBtn);
        btnRow.add(respondBtn);
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> frame.dispose());
        btnRow.add(closeBtn);
        frame.add(btnRow, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /** Shows a popup with full issue details when a row is clicked. */
    private void showIssueDetails(JFrame parent, Issue issue, DefaultTableModel model, int row, Runnable reload) {
        String details =
            "Issue ID     : " + issue.getIssueId() + "\n" +
            "Customer ID  : " + issue.getCustomerId() + "\n" +
            "Booking ID   : " + issue.getBookingId() + "\n" +
            "Status       : " + issue.getStatus() + "\n" +
            "Assigned To  : " + (issue.getAssignedSchedulerId().isEmpty() ? "Not assigned" : issue.getAssignedSchedulerId()) + "\n\n" +
            "Description:\n" + issue.getDescription() + "\n\n" +
            "Manager Response:\n" + (issue.getManagerResponse().isEmpty() ? "(No response yet)" : issue.getManagerResponse());

        JTextArea area = new JTextArea(details, 14, 40);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(parent, new JScrollPane(area), "Issue Details: " + issue.getIssueId(), JOptionPane.INFORMATION_MESSAGE);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(80, 0, 150));
        btn.setForeground(Color.WHITE);
        if (text.equals("Logout")) btn.setBackground(new Color(180, 50, 50));
        return btn;
    }
}
