import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Mainframe extends javax.swing.JFrame implements ActionListener {
    int width;
    int height;
    String expression = "";
    Boolean expressionComplete = false;
    JTextPane expressionDisplay;
    JTextPane resultDisplay;


    public Mainframe(Dimension screenSize) {
        width = screenSize.width;
        height = screenSize.height;
        setSize(screenSize);
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(screenSize);

        JPanel display = new JPanel();
        setUpDisplay(display);

        JPanel keyboard = new JPanel();
        setUpKeyboard(keyboard);

        add(mainPanel);
        mainPanel.add(display);
        mainPanel.add(keyboard);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (expressionComplete == false) {
            switch (action) {
                //todo: fix delete not working with spaces DONE
                case "del":
                    delete();
                    expressionDisplay.setText(expression);
                    break;

                case "ac":
                    expression = "";
                    expressionDisplay.setText("");
                    resultDisplay.setText("");
                    break;

                case "=":
                    expressionComplete = true;
                    resultDisplay.setText(String.valueOf(parseExpression(expression)));
                    break;

                // Operators and Parenthesis needs padding to be parsed correctly
                case "+":
                case "-":
                case "*":
                case "/":
                    expression = expression + " " + action + " ";       //left and right padding for parenthesis
                    expressionDisplay.setText(expression);
                    break;

                case "(":
                    expression = expression + action + " ";     //right padding for left parenthesis
                    expressionDisplay.setText(expression);
                    break;

                case ")":
                    expression = expression + " " + action;     //left padding for right parenthesis
                    expressionDisplay.setText(expression);
                    break;

                default:
                    expression = expression + action;
                    expressionDisplay.setText(expression);
                    break;
            }
        }
        else if (expressionComplete) {
            switch (e.getActionCommand()) {
                //todo: fix delete not working with spaces DONE
                case "del":
                    delete();
                    expressionDisplay.setText(expression);
                    resultDisplay.setText("");
                    expressionComplete = false;
                    break;

                case "ac":
                    expression = "";
                    expressionDisplay.setText("");
                    resultDisplay.setText("");
                    expressionComplete = false;
                    break;

                default:
                    break;
            }
        }
    }

    private void setUpDisplay(JPanel display) {
        Dimension displaySize = new Dimension(width, height / 3);
        display.setPreferredSize(displaySize);
        display.setMaximumSize(displaySize);
        display.setLayout(new BoxLayout(display, BoxLayout.Y_AXIS));
        display.setBackground(Color.WHITE);


        expressionDisplay = new JTextPane();
        Dimension expressionDisplaySize = new Dimension(width, height / 6);
        expressionDisplay.setPreferredSize(expressionDisplaySize);
        expressionDisplay.setMinimumSize(expressionDisplaySize);
        expressionDisplay.setBackground(Color.BLACK);

        StyledDocument expressionDisplayDoc = expressionDisplay.getStyledDocument();
        SimpleAttributeSet expressionDisplayAttributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(expressionDisplayAttributes, StyleConstants.ALIGN_LEFT);
        StyleConstants.setFontSize(expressionDisplayAttributes, 48);
        StyleConstants.setForeground(expressionDisplayAttributes, Color.WHITE);
        expressionDisplayDoc.setParagraphAttributes(0, expressionDisplayDoc.getLength(), expressionDisplayAttributes, true);
        expressionDisplay.setText("Expression");

        resultDisplay = new JTextPane();
        Dimension resultDisplaySize = new Dimension(width, height / 6);
        resultDisplay.setPreferredSize(resultDisplaySize);
        resultDisplay.setMinimumSize(resultDisplaySize);
        resultDisplay.setBackground(Color.BLACK);

        StyledDocument resultDisplayDoc = resultDisplay.getStyledDocument();
        SimpleAttributeSet resultDisplayAttributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(resultDisplayAttributes, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setFontSize(resultDisplayAttributes, 48);
        StyleConstants.setBold(resultDisplayAttributes, true);
        StyleConstants.setForeground(resultDisplayAttributes, Color.WHITE);
        resultDisplayDoc.setParagraphAttributes(0, resultDisplayDoc.getLength(), resultDisplayAttributes, true);
        resultDisplay.setText("Result");

        display.add(expressionDisplay);
        display.add(resultDisplay);
    }

    private void setUpKeyboard(JPanel keyboard) {
        Dimension keyboardSize = new Dimension(width, 2 * height / 3);
        keyboard.setPreferredSize(keyboardSize);
        keyboard.setMaximumSize(keyboardSize);
        keyboard.setLayout(new BoxLayout(keyboard, BoxLayout.X_AXIS));
        keyboard.setBackground(Color.GRAY);

        JPanel numberPanel = new JPanel();
        numberPanel.setBackground(Color.GRAY);
        numberPanel.setLayout(new GridLayout(4, 3));

        for (int i = 0; i < 10; i++) {
            JButton button = new JButton(Integer.toString(i));
            button.setActionCommand(Integer.toString(i));
            button.addActionListener(this);
            numberPanel.add(button);
        }

        JButton leftParenthesis = new JButton("(");
        leftParenthesis.setActionCommand("(");
        leftParenthesis.addActionListener(this);
        numberPanel.add(leftParenthesis);

        JButton rightParenthesis = new JButton(")");
        rightParenthesis.setActionCommand(")");
        rightParenthesis.addActionListener(this);
        numberPanel.add(rightParenthesis);

        JPanel operatorPanel = new JPanel();
        operatorPanel.setBackground(Color.GRAY);
        operatorPanel.setLayout(new GridLayout(4, 2));

        JButton clear = new JButton("AC");
        clear.setActionCommand("ac");
        clear.addActionListener(this);
        operatorPanel.add(clear);

        JButton del = new JButton("DEL");
        del.setActionCommand("del");
        del.addActionListener(this);
        operatorPanel.add(del);

        JButton plus = new JButton("+");
        plus.setActionCommand("+");
        plus.addActionListener(this);
        operatorPanel.add(plus);

        JButton minus = new JButton("-");
        minus.setActionCommand("-");
        minus.addActionListener(this);
        operatorPanel.add(minus);

        JButton multiply = new JButton("x");
        multiply.setActionCommand("*");
        multiply.addActionListener(this);
        operatorPanel.add(multiply);

        JButton divide = new JButton("/");
        divide.setActionCommand("/");
        divide.addActionListener(this);
        operatorPanel.add(divide);

        JButton dot = new JButton(".");
        dot.setActionCommand(".");
        dot.addActionListener(this);
        operatorPanel.add(dot);

        JButton equals = new JButton("=");
        equals.setActionCommand("=");
        equals.addActionListener(this);
        operatorPanel.add(equals);

        keyboard.add(numberPanel);
        keyboard.add(operatorPanel);
    }

    private double parseExpression(String expression) {
        String[] tokens = expression.split(" ");
        for (int i = 0; i < tokens.length; i++) {
            System.out.println(tokens[i]);
        }
        ArrayList<String> outputQueue = new ArrayList<String>();
        ArrayList<String> operatorStack = new ArrayList<String>();
        //for (char c : expression.toCharArray()) {
        //    if (Character.isDigit(c)) {

        //    }
        //}
        return 0;
    }

    private void delete() {
        if (expression.length() < 2) {
            expression = "";
        }
        else {
        char lastCharOfExpression = expression.charAt(expression.length() - 1);
        char secondLastCharOfExpression = expression.charAt(expression.length() - 2);
        switch (lastCharOfExpression) {
            case ' ':
                switch (secondLastCharOfExpression) {
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        expression = expression.substring(0, expression.length() - 3);
                        break;

                    case '(':
                        expression = expression.substring(0, expression.length() - 2);
                        break;
                }
                break;

            case ')':
                expression = expression.substring(0, expression.length() - 2);
                break;

            default:
                expression = expression.substring(0, expression.length() - 1);
                break;
            }
        }
    }

    public static void main(String[] args) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        new Mainframe(screenSize);
    }
}
