import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.*;

public class RadioButtons {
    RadioButtons(){
        JFrame f = new JFrame("Background Color Selecto");
        f.setSize(600,500);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20,50));

        JRadioButton redButton = new JRadioButton("Red");
        JRadioButton greenButton = new JRadioButton("Green");
        JRadioButton blueButton = new JRadioButton("Blue");
        JRadioButton whiteBtn = new JRadioButton("White");
        JRadioButton grayBtButton = new JRadioButton("Gray",true);
        JRadioButton darkButton = new JRadioButton("Dark");


        ButtonGroup bg = new ButtonGroup();
        bg.add(redButton);
        bg.add(greenButton);
        bg.add(blueButton);
        bg.add(whiteBtn);
        bg.add(grayBtButton);
        bg.add(darkButton);

        panel.add(redButton);
        panel.add(greenButton);
        panel.add(blueButton);
        panel.add(darkButton);
        panel.add(grayBtButton);
        panel.add(whiteBtn);


        f.add(panel);
        f.setVisible(true);

        redButton.addActionListener(ActionEvent -> panel.setBackground(Color.RED));
        greenButton.addActionListener(ActionEvent -> panel.setBackground(Color.GREEN));
        blueButton.addActionListener(ActionEvent -> panel.setBackground(Color.BLUE));
        whiteBtn.addActionListener(ActionEvent -> panel.setBackground(Color.WHITE));
        grayBtButton.addActionListener(ActionEvent -> panel.setBackground(Color.GRAY));
        darkButton.addActionListener(ActionEvent -> panel.setBackground(Color.DARK_GRAY));
    }   
    public static void main(String[] args) {
        new RadioButtons();
    }
}
