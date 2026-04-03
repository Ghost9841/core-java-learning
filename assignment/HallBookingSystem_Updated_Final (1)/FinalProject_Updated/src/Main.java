import gui.LoginFrame;
import util.FileManager;
import javax.swing.*;

/**
 * Main.java
 * Entry point of the Hall Booking Management System.
 * Initializes the file system and launches the login screen.
 */
public class Main {
    public static void main(String[] args) {
        // Initialize data files before launching GUI
        FileManager.initializeFiles();

        // Launch GUI on the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
