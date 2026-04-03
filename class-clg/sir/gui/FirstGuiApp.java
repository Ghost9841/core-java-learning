import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class FirstGuiApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(800,600);
        frame.setTitle("First Gui App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS)); // how to display components inside a frame
        
        JLabel userNameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Pasword: ");
        JButton button = new JButton("Login");
        JTextField textField = new JTextField(10);
        JTextField textField2 = new JTextField(10);

        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        panel.setLayout(new FlowLayout());
        
        panel.add(userNameLabel);
        panel.add(textField2);

        panel2.add(passwordLabel);
        panel2.add(textField);

        panel3.add(button);

        Object[] column = new Object[]{"id","name","salary"};
        Object[] dataObjects = new Object[]{"1","Aakash","10000000"};
        JTable table = new JTable();

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.addRow(dataObjects);
        model.addColumn(column);

        frame.add(panel);
        frame.add(panel2); 
        frame.add(panel3); 
        frame.add(table);
        frame.setVisible(true);
    }
}