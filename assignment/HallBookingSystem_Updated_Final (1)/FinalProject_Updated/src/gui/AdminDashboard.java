package gui;

import model.*;
import util.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * AdminDashboard.java
 * Administrator panel for managing scheduler and customer accounts,
 * viewing all bookings with filters, and generating a booking summary report.
 *
 * OOP Concepts:
 * - INHERITANCE: Administrator extends User
 * - ENCAPSULATION: admin field is private and final
 * - POLYMORPHISM: getDashboardTitle() is overridden in Administrator
 */
public class AdminDashboard extends JFrame {

    private final Administrator admin;

    public AdminDashboard(Administrator admin) {
        this.admin = admin;
        setTitle("Admin Dashboard - " + admin.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 420);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(255, 245, 235));

        JLabel titleLabel = new JLabel("Administrator: " + admin.getUsername(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 17));
        titleLabel.setForeground(new Color(150, 70, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        btnPanel.setBackground(new Color(255, 245, 235));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        String[] btnLabels = {
            "Add Scheduler Account",
            "View / Manage Schedulers",
            "View / Manage Customers",
            "View All Bookings",
            "Generate Booking Report",
            "Logout"
        };

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
            case "Add Scheduler Account":
                showAddSchedulerDialog();
                break;
            case "View / Manage Schedulers":
                showUsersTable("SCHEDULER");
                break;
            case "View / Manage Customers":
                showUsersTable("CUSTOMER");
                break;
            case "View All Bookings":
                showAllBookingsWithFilter();
                break;
            case "Generate Booking Report":
                generateReport();
                break;
            case "Logout":
                int c = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (c == JOptionPane.YES_OPTION) { new LoginFrame().setVisible(true); dispose(); }
                break;
        }
    }

    private void showAddSchedulerDialog() {
        JTextField uname = new JTextField(15);
        JPasswordField pass = new JPasswordField(15);
        JTextField email = new JTextField(15);
        JTextField phone = new JTextField(15);

        Object[] fields = { "Username:", uname, "Password:", pass, "Email:", email, "Phone:", phone };
        int result = JOptionPane.showConfirmDialog(this, fields, "Add New Scheduler", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String username = uname.getText().trim();
            String password = new String(pass.getPassword()).trim();
            String emailVal = email.getText().trim();
            String phoneVal = phone.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password are required.");
                return;
            }
            if (password.length() < 6) {
                JOptionPane.showMessageDialog(this, "Password must be at least 6 characters.");
                return;
            }
            if (FileManager.usernameExists(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose another.");
                return;
            }

            String id = FileManager.generateId("U");
            Scheduler scheduler = new Scheduler(id, username, password, emailVal, phoneVal);
            List<User> users = FileManager.readUsers();
            users.add(scheduler);
            FileManager.writeUsers(users);
            JOptionPane.showMessageDialog(this, "Scheduler account created successfully!");
        }
    }

    /**
     * Shows a table of users filtered by role (SCHEDULER or CUSTOMER).
     * Includes filter controls: Username, Blocked status, and User ID.
     */
    private void showUsersTable(String role) {
        JFrame frame = new JFrame("Manage " + role + "s");
        frame.setSize(720, 480);
        frame.setLocationRelativeTo(this);
        frame.setLayout(new BorderLayout(5, 5));

        // --- Filter Panel ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter " + role + "s"));
        filterPanel.setBackground(new Color(255, 250, 245));

        JTextField usernameFilter = new JTextField(10);
        JComboBox<String> blockedFilter = new JComboBox<>(new String[]{"All", "Blocked", "Not Blocked"});
        JTextField idFilter = new JTextField(8);

        filterPanel.add(new JLabel("Username:"));
        filterPanel.add(usernameFilter);
        filterPanel.add(new JLabel("  Status:"));
        filterPanel.add(blockedFilter);
        filterPanel.add(new JLabel("  " + role.charAt(0) + role.substring(1).toLowerCase() + " ID:"));
        filterPanel.add(idFilter);

        JButton applyFilterBtn = new JButton("Apply Filter");
        applyFilterBtn.setBackground(new Color(150, 70, 0));
        applyFilterBtn.setForeground(Color.WHITE);
        filterPanel.add(applyFilterBtn);

        JButton clearFilterBtn = new JButton("Clear");
        clearFilterBtn.addActionListener(e -> {
            usernameFilter.setText("");
            blockedFilter.setSelectedIndex(0);
            idFilter.setText("");
        });
        filterPanel.add(clearFilterBtn);
        frame.add(filterPanel, BorderLayout.NORTH);

        // --- Table ---
        String[] columns = { "ID", "Username", "Email", "Phone", "Blocked" };
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        Runnable loadTable = () -> {
            model.setRowCount(0);
            String uFilter    = usernameFilter.getText().trim().toLowerCase();
            String bFilter    = (String) blockedFilter.getSelectedItem();
            String idFilterVal = idFilter.getText().trim().toLowerCase();

            for (User u : FileManager.readUsers()) {
                if (!u.getRole().equals(role)) continue;
                if (!uFilter.isEmpty() && !u.getUsername().toLowerCase().contains(uFilter)) continue;
                if (!idFilterVal.isEmpty() && !u.getUserId().toLowerCase().contains(idFilterVal)) continue;
                if ("Blocked".equals(bFilter) && !u.isBlocked()) continue;
                if ("Not Blocked".equals(bFilter) && u.isBlocked()) continue;
                model.addRow(new Object[]{
                    u.getUserId(), u.getUsername(), u.getEmail(),
                    u.getPhone(), u.isBlocked() ? "Yes" : "No"
                });
            }
        };

        loadTable.run();
        applyFilterBtn.addActionListener(e -> loadTable.run());

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Action Buttons ---
        JButton blockBtn = new JButton("Toggle Block/Unblock");
        blockBtn.setBackground(new Color(200, 120, 0));
        blockBtn.setForeground(Color.WHITE);
        blockBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(frame, "Select a user first."); return; }
            String uid = (String) table.getValueAt(row, 0);
            List<User> updated = FileManager.readUsers();
            for (User u : updated) {
                if (u.getUserId().equals(uid)) {
                    u.setBlocked(!u.isBlocked());
                    model.setValueAt(u.isBlocked() ? "Yes" : "No", row, 4);
                    break;
                }
            }
            FileManager.writeUsers(updated);
            JOptionPane.showMessageDialog(frame, "User block status updated.");
        });

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(new Color(180, 50, 50));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(frame, "Select a user first."); return; }
            String uid = (String) table.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(frame, "Delete this user permanently?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                List<User> updated = FileManager.readUsers();
                updated.removeIf(u -> u.getUserId().equals(uid));
                FileManager.writeUsers(updated);
                model.removeRow(row);
                JOptionPane.showMessageDialog(frame, "User deleted successfully.");
            }
        });

        JPanel btnRow = new JPanel(new FlowLayout());
        btnRow.add(blockBtn);

        if (role.equals("SCHEDULER")) {
            JButton editBtn = new JButton("Edit Scheduler");
            editBtn.setBackground(new Color(30, 80, 160));
            editBtn.setForeground(Color.WHITE);
            editBtn.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row == -1) { JOptionPane.showMessageDialog(frame, "Select a scheduler first."); return; }
                String uid = (String) table.getValueAt(row, 0);
                List<User> allUsers = FileManager.readUsers();
                User target = null;
                for (User u : allUsers) { if (u.getUserId().equals(uid)) { target = u; break; } }
                if (target == null) return;

                JTextField newEmail = new JTextField(target.getEmail(), 15);
                JTextField newPhone = new JTextField(target.getPhone(), 15);
                JPasswordField newPass = new JPasswordField(15);
                Object[] editFields = { "Email:", newEmail, "Phone:", newPhone, "New Password (leave blank to keep):", newPass };
                int res = JOptionPane.showConfirmDialog(frame, editFields,
                    "Edit Scheduler: " + target.getUsername(), JOptionPane.OK_CANCEL_OPTION);
                if (res == JOptionPane.OK_OPTION) {
                    String ev = newEmail.getText().trim();
                    String pv = newPhone.getText().trim();
                    String pw = new String(newPass.getPassword()).trim();
                    if (ev.isEmpty() || pv.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Email and phone cannot be empty.");
                        return;
                    }
                    for (User u : allUsers) {
                        if (u.getUserId().equals(uid)) {
                            u.setEmail(ev); u.setPhone(pv);
                            if (!pw.isEmpty()) u.setPassword(pw);
                            model.setValueAt(ev, row, 2);
                            model.setValueAt(pv, row, 3);
                            break;
                        }
                    }
                    FileManager.writeUsers(allUsers);
                    JOptionPane.showMessageDialog(frame, "Scheduler updated successfully.");
                }
            });
            btnRow.add(editBtn);
        }

        btnRow.add(deleteBtn);
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> frame.dispose());
        btnRow.add(closeBtn);

        frame.add(btnRow, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /**
     * Shows all bookings with filter controls:
     * Upcoming, Past, Paid, Unpaid, Cancelled.
     */
    private void showAllBookingsWithFilter() {
        JFrame frame = new JFrame("All Bookings");
        frame.setSize(860, 500);
        frame.setLocationRelativeTo(this);
        frame.setLayout(new BorderLayout(5, 5));

        // --- Filter Panel ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter Bookings"));
        filterPanel.setBackground(new Color(255, 250, 245));

        JComboBox<String> filterCombo = new JComboBox<>(new String[]{
            "All", "Upcoming", "Past", "Paid", "Unpaid", "Cancelled"
        });
        filterPanel.add(new JLabel("Show:"));
        filterPanel.add(filterCombo);

        JButton applyBtn = new JButton("Apply Filter");
        applyBtn.setBackground(new Color(150, 70, 0));
        applyBtn.setForeground(Color.WHITE);
        filterPanel.add(applyBtn);
        frame.add(filterPanel, BorderLayout.NORTH);

        // --- Table ---
        String[] columns = { "Booking ID", "Customer ID", "Hall", "Date", "Time", "Amount (RM)", "Status", "Payment" };
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        Runnable loadBookings = () -> {
            model.setRowCount(0);
            String filter    = (String) filterCombo.getSelectedItem();
            LocalDate today  = LocalDate.now();

            for (Booking b : FileManager.readBookings()) {
                if ("Upcoming".equals(filter)) {
                    try { if (LocalDate.parse(b.getEventDate()).isBefore(today)) continue; } catch (Exception ex) {}
                } else if ("Past".equals(filter)) {
                    try { if (!LocalDate.parse(b.getEventDate()).isBefore(today)) continue; } catch (Exception ex) {}
                } else if ("Paid".equals(filter)) {
                    if (!"PAID".equals(b.getPaymentStatus())) continue;
                } else if ("Unpaid".equals(filter)) {
                    if ("PAID".equals(b.getPaymentStatus())) continue;
                } else if ("Cancelled".equals(filter)) {
                    if (!Booking.STATUS_CANCELLED.equals(b.getStatus())) continue;
                }
                model.addRow(new Object[]{
                    b.getBookingId(), b.getCustomerId(), b.getHallName(),
                    b.getEventDate(), b.getTimeRange(),
                    String.format("%.2f", b.getTotalAmount()),
                    b.getStatus(), b.getPaymentStatus()
                });
            }
        };

        loadBookings.run();
        applyBtn.addActionListener(e -> loadBookings.run());

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnRow = new JPanel(new FlowLayout());
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> frame.dispose());
        btnRow.add(closeBtn);
        frame.add(btnRow, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void generateReport() {
        List<Booking> bookings = FileManager.readBookings();
        int total = bookings.size(), confirmed = 0, cancelled = 0, pending = 0;
        double totalRevenue = 0;

        for (Booking b : bookings) {
            switch (b.getStatus()) {
                case Booking.STATUS_CONFIRMED: confirmed++; break;
                case Booking.STATUS_CANCELLED: cancelled++; break;
                default: pending++; break;
            }
            if (b.getStatus().equals(Booking.STATUS_CONFIRMED) && b.getPaymentStatus().equals("PAID"))
                totalRevenue += b.getTotalAmount();
        }

        String report =
            "========== BOOKING REPORT ==========\n" +
            "Total Bookings   : " + total + "\n" +
            "Confirmed        : " + confirmed + "\n" +
            "Pending          : " + pending + "\n" +
            "Cancelled        : " + cancelled + "\n" +
            "Total Revenue    : RM " + String.format("%.2f", totalRevenue) + "\n" +
            "====================================";

        JOptionPane.showMessageDialog(this, report, "Booking Report", JOptionPane.INFORMATION_MESSAGE);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(150, 70, 0));
        btn.setForeground(Color.WHITE);
        if (text.equals("Logout")) btn.setBackground(new Color(180, 50, 50));
        return btn;
    }
}
