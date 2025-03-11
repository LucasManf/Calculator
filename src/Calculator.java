import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator implements ActionListener {

    JFrame frame;
    JTextField textField;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[9];
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, equButton, delButton, clrButton, negButton;
    JPanel panel;
    boolean ongoingOperation = false;


    Font font = new Font("Verdana", Font.BOLD, 30);

    double num1 = 0, num2 = 0, result = 0;
    char operator;

    Color backgroundColor = new Color(0,0,0);
    Color buttonColor = new Color(60, 60, 60);
    Color operatorButtonColor = new Color(85, 85, 85);
    Color textColor = Color.WHITE;
    Color equButtonColor = new Color(255, 85, 85);

    Calculator() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 550);
        frame.setLayout(null);
        frame.getContentPane().setBackground(backgroundColor);

        textField = new JTextField();
        textField.setBounds(50, 25, 300, 50);
        textField.setFont(font);
        textField.setEditable(false);
        textField.setHorizontalAlignment(SwingConstants.RIGHT);

        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("DEL");
        clrButton = new JButton("CLR");
        negButton = new JButton("(-)");

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clrButton;
        functionButtons[8] = negButton;

        for (int i = 0; i < 9; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFont(font);
            functionButtons[i].setFocusable(false);

            functionButtons[i].setBackground(operatorButtonColor);
            functionButtons[i].setForeground(textColor);
            functionButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            functionButtons[i].setOpaque(true);
        }

        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(font);
            numberButtons[i].setFocusable(false);

            numberButtons[i].setBackground(buttonColor);
            numberButtons[i].setForeground(textColor);
            numberButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            numberButtons[i].setOpaque(true);
        }

        delButton.setBounds(150, 430, 100, 50);
        clrButton.setBounds(250, 430, 100, 50);
        negButton.setBounds(50, 430, 100, 50);

        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        panel.setBackground(backgroundColor);

        equButton.setBackground(equButtonColor);

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(mulButton);
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);

        frame.add(delButton);
        frame.add(clrButton);
        frame.add(negButton);
        frame.add(panel);
        frame.add(textField);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]){
                if (textField.getText().equals(String.valueOf(result)) ) {
                    textField.setText("");
                    if (!ongoingOperation) {
                        num1 = 0;
                        num2 = 0;
                        result = 0;
                    }
                }
                textField.setText(textField.getText().concat(String.valueOf(i)));
            }
        }
        if (e.getSource() == decButton) {
            textField.setText(textField.getText().concat("."));
        }

        if (e.getSource() == addButton || e.getSource() == subButton || e.getSource() == mulButton || e.getSource() == divButton) {

            if (textField.getText().isEmpty()) {
                return;
            }
            if (!ongoingOperation) {
                num1 = Double.parseDouble(textField.getText());
                operator = e.getSource() == addButton ? '+' :
                           e.getSource() == subButton ? '-' :
                           e.getSource() == mulButton ? '*' : '/';
                textField.setText("");
                ongoingOperation = true;
            } else {
                equFunction();
                operator = e.getSource() == addButton ? '+' :
                           e.getSource() == subButton ? '-' :
                           e.getSource() == mulButton ? '*' : '/';
                num1 = result;
            }
        }
        if (e.getSource() == equButton) {
            equFunction();
            ongoingOperation = false;
        }
        if (e.getSource() == clrButton) {
            textField.setText("");
            ongoingOperation = false;
            num1 = 0;
            num2 = 0;
            result = 0;
        }
        if (e.getSource() == delButton) {
            String string = textField.getText();
            textField.setText("");
            for (int i = 0; i < string.length()-1; i++) {
                textField.setText(textField.getText()+string.charAt(i));
            }
        }
        if (e.getSource() == negButton) {
            double temp = Double.parseDouble(textField.getText());
            temp*=-1;
            textField.setText(String.valueOf(temp));
        }
    }

    private void equFunction() {
        if (!textField.getText().isEmpty()) {
            num2 = Double.parseDouble(textField.getText());
        } else {
            return;
        }
        result = switch (operator) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> num1 / num2;
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };
        textField.setText(String.valueOf(result));
        num1 = result;
    }
}
