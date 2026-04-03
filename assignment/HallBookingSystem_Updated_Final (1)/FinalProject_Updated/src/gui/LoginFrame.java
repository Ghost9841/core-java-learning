package gui;

import model.*;
import util.FileManager;

import javax.swing.*;
import java.awt.*;

/**
 * LoginFrame.java
 * The main login screen for all users.
 * After successful login, opens the appropriate dashboard based on role.
 */
public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Hall Booking Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 320);
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(245, 245, 250));

        // Title label
        JLabel titleLabel = new JLabel("Hall Booking System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 80, 160));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username row
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(18);
        formPanel.add(usernameField, gbc);

        // Password row
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(18);
        formPanel.add(passwordField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setBackground(new Color(245, 245, 250));

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(30, 80, 160));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setPreferredSize(new Dimension(100, 35));

        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(80, 160, 80));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);

        JLabel hintLabel = new JLabel("New customer? Click Register.", SwingConstants.CENTER);
        hintLabel.setForeground(Color.GRAY);
        hintLabel.setFont(new Font("Arial", Font.ITALIC, 11));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(245, 245, 250));
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(hintLabel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // --- Action Listeners ---

        // Login button: validate credentials and open correct dashboard
        loginBtn.addActionListener(e -> handleLogin());

        // Allow pressing Enter to login
        passwordField.addActionListener(e -> handleLogin());

        // Register button: open RegisterFrame
        registerBtn.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
        });
    }

    /** Handles login logic: validate input, check credentials, open dashboard. */
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both username and password.",
                "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Look up user in file
        User user = FileManager.findUser(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this,
                "Incorrect username or password.",
                "Login Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (user.isBlocked()) {
            JOptionPane.showMessageDialog(this,
                "Your account has been blocked. Please contact admin.",
                "Account Blocked", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Open the correct dashboard based on user role
        switch (user.getRole()) {
            case "CUSTOMER":
                new CustomerDashboard((model.Customer) user).setVisible(true);
                break;
            case "SCHEDULER":
                new SchedulerDashboard((model.Scheduler) user).setVisible(true);
                break;
            case "ADMIN":
                new AdminDashboard((model.Administrator) user).setVisible(true);
                break;
            case "MANAGER":
                new ManagerDashboard((model.Manager) user).setVisible(true);
                break;
        }

        this.dispose(); // Close login window
    }
}
