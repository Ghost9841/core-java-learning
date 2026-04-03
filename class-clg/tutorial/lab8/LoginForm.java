import javax.swing.*;
import java.awt.event.*;


public class LoginForm {
    LoginForm() {
        JFrame f = new JFrame("Login Form");
        f.setSize(600,500);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        JLabel nameLabel = new JLabel("Name : ");
        nameLabel.setBounds(50, 50, 100, 30);
        f.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 30);
        f.add(nameField);

        JLabel passwordLabel = new JLabel("Password : ");
        passwordLabel.setBounds(50, 100, 100, 30);
        f.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 200, 30);
        f.add(passwordField);
        
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 150, 100, 30);
        f.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String name = nameField.getText();
                String password = new String(passwordField.getPassword());
                if (name.equals("admin") && password.equals("admin")) {
                    JOptionPane.showMessageDialog(f, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(f, "Invalid username or password.");
                }
            }
        });
        f.setVisible(true);
    }
    public static void main(String[] args) {
        new LoginForm();
    }
}