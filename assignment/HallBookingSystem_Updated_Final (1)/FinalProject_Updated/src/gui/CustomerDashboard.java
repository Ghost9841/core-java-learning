package gui;

import model.Customer;
import model.User;
import util.FileManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * CustomerDashboard.java
 * Main menu for logged-in customers.
 * Provides access to all customer features via buttons.
 */
public class CustomerDashboard extends JFrame {

    private final Customer customer;

    public CustomerDashboard(Customer customer) {
        this.customer = customer;
        setTitle("Customer Dashboard - Welcome, " + customer.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(240, 248, 255));

        // Header
        JLabel titleLabel = new JLabel("Welcome, " + customer.getUsername(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(30, 80, 160));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Buttons panel
        JPanel btnPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        btnPanel.setBackground(new Color(240, 248, 255));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        String[] btnLabels = {
            "View Available Halls",
            "Book a Hall",
            "My Booking History",
            "Make Payment",
            "Raise an Issue",
            "Update My Profile",
            "Logout"
        };

        for (String label : btnLabels) {
            JButton btn = createButton(label);
            btnPanel.add(btn);

            btn.addActionListener(e -> {
                switch (label) {
                    case "View Available Halls":
                        new HallManagementFrame(customer, true).setVisible(true);
                        break;
                    case "Book a Hall":
                        new BookingFrame(customer).setVisible(true);
                        break;
                    case "My Booking History":
                        new BookingHistoryFrame(customer).setVisible(true);
                        break;
                    case "Make Payment":
                        new BookingHistoryFrame(customer, true).setVisible(true);
                        break;
                    case "Raise an Issue":
                        new IssueFrame(customer).setVisible(true);
                        break;
                    case "Update My Profile":
                        showUpdateProfile();
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

    /** Creates a styled button. */
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(30, 80, 160));
        btn.setForeground(Color.WHITE);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (text.equals("Logout")) {
            btn.setBackground(new Color(180, 50, 50));
        }
        return btn;
    }

    /** Show a dialog to update username, email, phone, or password. */
    private void showUpdateProfile() {
        JTextField newUsername = new JTextField(customer.getUsername());
        JTextField newEmail    = new JTextField(customer.getEmail());
        JTextField newPhone    = new JTextField(customer.getPhone());
        JPasswordField newPass = new JPasswordField();

        Object[] fields = {
            "Username:", newUsername,
            "Email:", newEmail,
            "Phone:", newPhone,
            "New Password (leave blank to keep):", newPass
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Update Profile", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String uname = newUsername.getText().trim();
            String email = newEmail.getText().trim();
            String phone = newPhone.getText().trim();
            String pass  = new String(newPass.getPassword()).trim();

            if (uname.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty.");
                return;
            }

            // Update in file
            List<User> users = FileManager.readUsers();
            for (User u : users) {
                if (u.getUserId().equals(customer.getUserId())) {
                    u.setUsername(uname);
                    u.setEmail(email);
                    u.setPhone(phone);
                    if (!pass.isEmpty()) u.setPassword(pass);
                    customer.setUsername(uname);
                    customer.setEmail(email);
                    customer.setPhone(phone);
                    if (!pass.isEmpty()) customer.setPassword(pass);
                    break;
                }
            }
            FileManager.writeUsers(users);
            setTitle("Customer Dashboard - Welcome, " + customer.getUsername());
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}
