import javax.swing.*;
import java.awt.*;

public class SimpleCalculator {
    SimpleCalculator(){
        JTextField txtNum1, txtNum2, txtResult;
        JButton btnAdd, btnSub, btnMul, btnDiv;

        Jframe f = new Jframe("Calculator");
        f.setSize(400,400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,2,10,10));
        panel.setBorder(BordeFactory.createEmptyBorder(10,10,10,10));

       JLabel lblNum1 = new JLabel("Number 1");
       txtNum1 = new JTextField();
       panel.add(lblNum1);
       panel.add(txtNum1);

       JLabel lblNum2 = new JLabel("Number 2");
       txtNum2 = new JTextField();
       panel.add(lblNum2);
       panel.add(txtNum2);

       JLabel lblResult = new JLabel("Result");
       txtResult = new JTextField();
       panel.add(lblResult);
       panel.add(txtResult);

       btnAdd = new JButton("+");
       btnSub = new JButton("-");
       btnMul = new JButton("*");
       btnDiv = new JButton("/");

       panel.add(btnAdd);
       panel.add(btnSub);
       panel.add(btnMul);
       panel.add(btnDiv);

       f.add(panel);

       btnAdd.addActionListener(this);
       btnSub.addActionListener(this);
       btnMul.addActionListener(this);
       btnDiv.addActionListener(this);
       f.setVisible(true);
       
    }

    public static void main(String[] args) {
        
    }
}
