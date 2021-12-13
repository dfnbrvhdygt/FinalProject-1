
 import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class Main {
public static void main(String[] args) {
        try {
            // set look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        // create a frame
        JFrame frame = new Calculator();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
class Calculator extends JFrame {
//    create private member of class
    //to use for computer text create unchangeable font "monspaced"
    private final Font BIGGER_FONT = new Font("monspaced",Font.PLAIN, 20);
    // create a text field
    private JTextField textfield;
    // create a boolean to check textfield
    private boolean aBoolean = true;
    // set operator default value
    private String operatorType = "=";
    // create subclass object to specify calculator actions
    private CalculatorOp calAction = new CalculatorOp();
    // create constructor, initialize class members
    public Calculator() {
        // create text field
        textfield = new JTextField("", 12);
        // to able to write textfield from rigt side
        textfield.setHorizontalAlignment(JTextField.RIGHT);
        //set font to textfield
        textfield.setFont(BIGGER_FONT);

        // create subclass obj to listen numbers, should keep write digit until operators
        ActionListener numberListener = new NumberListener();
        // create digit order
        String buttonOrder = "1234567890";
        // create a panel
        JPanel buttonPanel = new JPanel();
        //This method changes layout-related information
        buttonPanel.setLayout(new GridLayout(4, 4, 4, 4));
        // create digit buttons
        for (int i = 0; i < buttonOrder.length(); i++) {
            String buttonKey = buttonOrder.substring(i, i+1);
            // create digit button
             JButton button = new JButton(buttonKey);
             //  add action listeners with subclass obj
             button.addActionListener(numberListener);
             //set font
             button.setFont(BIGGER_FONT);
            // add elements to panel
             buttonPanel.add(button);

        }

        // create subclass obj to check input. All logic is implemented in this method
        ActionListener operatorListener = new OperatorListener();
        // create a panel
        JPanel operatorPanel = new JPanel();
        //This method changes layout-related information
        operatorPanel.setLayout(new GridLayout(4, 4, 4, 4));
        // create operator order
        String[] opOrder = {"+", "-", "*", "/","=","C", "pow","sin","cos","log"};
        for (int i = 0; i < opOrder.length; i++) {
            // create operator button
            JButton button = new JButton(opOrder[i]);
            // add action listeners with subclass obj
            button.addActionListener(operatorListener);
            //set font
            button.setFont(BIGGER_FONT);
            //add elements to panel
            operatorPanel.add(button);
        }
        // create a parent panel with necessary config
        JPanel parent = new JPanel();
        parent.setLayout(new BorderLayout(4, 4));
        // add text field to panel with correct location
        parent.add(textfield, BorderLayout.NORTH );
        // add digit panel with correct location
        parent.add(buttonPanel , BorderLayout.WEST);
        // add operator panel with correct location
        parent.add(operatorPanel , BorderLayout.EAST);
        // set parent as a parent panel
        this.setContentPane(parent);
        //set window to be sized to fit the preferred size and layouts of its subcomponents
        this.pack();
        //set name to panel
        this.setTitle("Calculator");
        this.setResizable(false);
    }
    // this method will help  to set class member default values
    private void action() {
        aBoolean = true;
        textfield.setText("");
        operatorType = "=";
        calAction.setTotal("");
    }
    class OperatorListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
        /* create custom method that checks user input.
         Base on it, canalizes required action
        */
            String displayText = textfield.getText();
            if (event.getActionCommand().equals("sin")) {
                textfield.setText("" + Math.sin(Double.valueOf(displayText).doubleValue()));

            }else if (event.getActionCommand().equals("cos")) {
                textfield.setText("" + Math.cos(Double.valueOf(displayText).doubleValue()));

            }else if (event.getActionCommand().equals("log")) {
                textfield.setText("" + Math.log(Double.valueOf(displayText).doubleValue()));

            } else if (event.getActionCommand().equals("C")) {
                textfield.setText("");
            }else {
                if (aBoolean) {
                    action();
                }else {
                    aBoolean = true;
                    if (operatorType.equals("=")) {
                        calAction.setTotal(displayText);
                    }else if (operatorType.equals("+")) {
                        calAction.add(displayText);
                    }else if (operatorType.equals("-")) {
                        calAction.subtract(displayText);
                    }else if (operatorType.equals("*")) {
                        calAction.multiply(displayText);
                    }else if (operatorType.equals("/")) {
                        calAction.divide(displayText);
                    }else if (operatorType.equals("pow")) {
                        calAction.pow(displayText);
                    }
                    textfield.setText("" + calAction.getTotalString());
                    operatorType = event.getActionCommand();
                }
            }
        }
    }
    class NumberListener implements ActionListener {
        /* create  a custom method that checks the input
        whether it is digit or not.
        it keeps input as digit until input is operator
         */
        public void actionPerformed(ActionEvent event) {
            String digit = event.getActionCommand();
            if (aBoolean) {
                textfield.setText(digit);
                aBoolean = false;
            } else {
                textfield.setText(textfield.getText() + digit);
            }
        }
    }
    public class CalculatorOp {
        private int total;
        // create constructor
        public CalculatorOp() {
            total = 0; // set to total default value
        }
        // create custom method to return total as a String
        public String getTotalString() {
            return ""+total;
        }
        // create custom method to convert string total to int
        public void setTotal(String n) {
            total = convertToNumber(n);
        }
        // create custom addition method
        public void add(String n) {
            total += convertToNumber(n);
        }
        //create custom subtraction method
        public void subtract(String n) {
            total -= convertToNumber(n);
        }
        // create custom multiplication method
        public void multiply(String n) {
            total *= convertToNumber(n);
        }
        //create custom division method
        public void divide(String n) {
            total /= convertToNumber(n);
        }
        //  create custom power method
        public void pow(String n) {
            total = (int)Math.pow(total,convertToNumber(n));
        }
        // create custom method to convert string number to int
        private int convertToNumber(String n) {
            try {
                return Integer.parseInt(n);
            }
            catch (NumberFormatException e) {
                return 0;
            }
        }
    }
}