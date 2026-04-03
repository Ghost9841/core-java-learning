package gui;

import model.Customer;
import util.FileManager;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * RegisterFrame.java
 * Allows new customers to create an account.
 * Validates email format and phone number (digits only, exactly 10 digits).
 */
public class RegisterFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;
    private JTextField phoneField;

    public RegisterFrame() {
        setTitle("Customer Registration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("New Customer Registration", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(30, 80, 160));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 5, 7, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Username:", "Password:", "Confirm Password:", "Email:", "Phone:"};
        JComponent[] fields = {
            usernameField        = new JTextField(18),
            passwordField        = new JPasswordField(18),
            confirmPasswordField = new JPasswordField(18),
            emailField           = new JTextField(18),
            phoneField           = new JTextField(18)
        };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            formPanel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            formPanel.add(fields[i], gbc);
        }

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(30, 130, 30));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFocusPainted(false);

        buttonPanel.add(registerBtn);
        buttonPanel.add(cancelBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        registerBtn.addActionListener(e -> handleRegister());
        cancelBtn.addActionListener(e -> dispose());
    }

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirm  = new String(confirmPasswordField.getPassword()).trim();
        String email    = emailField.getText().trim();
        String phone    = phoneField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Email format validation
        if (!email.matches("^[\\w.+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(this,
                "Invalid email address. Please enter a valid email (e.g. user@example.com).",
                "Invalid Email", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Phone number validation: digits only, exactly 10 digits
        if (!phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this,
                "Phone number must contain numbers only (no spaces or symbols).",
                "Invalid Phone", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (phone.length() != 10) {
            JOptionPane.showMessageDialog(this,
                "Phone number must be exactly 10 digits. You entered " + phone.length() + " digit(s).",
                "Invalid Phone", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (FileManager.usernameExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already taken. Please choose another.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newId = FileManager.generateId("U");
        Customer newCustomer = new Customer(newId, username, password, email, phone);

        List<User> users = FileManager.readUsers();
        users.add(newCustomer);
        FileManager.writeUsers(users);

        JOptionPane.showMessageDialog(this,
            "Registration successful! You can now login.",
            "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
