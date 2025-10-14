import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Keyboard extends JPanel implements ActionListener {
    private String expression;
    private Boolean expressionComplete = false;
    Mainframe parent;
    JPanel numberPanel;
    JPanel operatorPanel;

    public Keyboard(Dimension keyboardSize, Mainframe parent) {
        this.parent = parent;
        expression = "";

        setPreferredSize(keyboardSize);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        Border buttonBorder = new LineBorder(Color.WHITE, 1);

        Dimension numberPanelSize = new Dimension(2 * keyboardSize.width / 3, keyboardSize.height);
        initNumberPanel(numberPanelSize, buttonFont, buttonBorder);

        Dimension operatorPanelSize = new Dimension(keyboardSize.width / 3, keyboardSize.height);
        initOperatorPanel(operatorPanelSize, buttonFont, buttonBorder);
    }

    private void initNumberPanel(Dimension numberPanelSize, Font font, Border border) {
        numberPanel = new JPanel();
        numberPanel.setPreferredSize(numberPanelSize);
        numberPanel.setLayout(new GridLayout(4, 3));

        for (int i = 0; i < 10; i++) {
            JButton button = new JButton(Integer.toString(i));
            button.setActionCommand(Integer.toString(i));
            button.addActionListener(this);

            button.setForeground(Color.WHITE);
            button.setBackground(Color.DARK_GRAY);
            button.setBorder(border);
            button.setFont(font);

            numberPanel.add(button);
        }

        JButton leftParenthesis = new JButton("(");
        leftParenthesis.setActionCommand("(");
        leftParenthesis.addActionListener(this);

        leftParenthesis.setForeground(Color.BLACK);
        leftParenthesis.setBackground(Color.ORANGE);
        leftParenthesis.setBorder(border);
        leftParenthesis.setFont(font);

        numberPanel.add(leftParenthesis);

        JButton rightParenthesis = new JButton(")");
        rightParenthesis.setActionCommand(")");
        rightParenthesis.addActionListener(this);

        rightParenthesis.setForeground(Color.BLACK);
        rightParenthesis.setBackground(Color.ORANGE);
        rightParenthesis.setBorder(border);
        rightParenthesis.setFont(font);

        numberPanel.add(rightParenthesis);

        add(numberPanel);
    }

    private void initOperatorPanel(Dimension operatorPanelSize, Font font, Border border) {
        operatorPanel = new JPanel();
        operatorPanel.setPreferredSize(operatorPanelSize);
        operatorPanel.setLayout(new GridLayout(4, 2));

        JButton del = new JButton("DEL");
        del.setActionCommand("del");
        del.addActionListener(this);

        del.setForeground(Color.WHITE);
        del.setBackground(Color.BLUE);
        del.setBorder(border);
        del.setFont(font);

        operatorPanel.add(del);

        JButton clear = new JButton("AC");
        clear.setActionCommand("ac");
        clear.addActionListener(this);

        clear.setForeground(Color.WHITE);
        clear.setBackground(Color.RED);
        clear.setBorder(border);
        clear.setFont(font);

        operatorPanel.add(clear);

        JButton plus = new JButton("+");
        plus.setActionCommand("+");
        plus.addActionListener(this);

        plus.setForeground(Color.BLACK);
        plus.setBackground(Color.ORANGE);
        plus.setBorder(border);
        plus.setFont(font);

        operatorPanel.add(plus);

        JButton minus = new JButton("-");
        minus.setActionCommand("-");
        minus.addActionListener(this);

        minus.setForeground(Color.BLACK);
        minus.setBackground(Color.ORANGE);
        minus.setBorder(border);
        minus.setFont(new Font("Arial", Font.BOLD, 30));

        operatorPanel.add(minus);

        JButton multiply = new JButton("x");
        multiply.setActionCommand("*");
        multiply.addActionListener(this);

        multiply.setForeground(Color.BLACK);
        multiply.setBackground(Color.ORANGE);
        multiply.setBorder(border);
        multiply.setFont(font);

        operatorPanel.add(multiply);

        JButton divide = new JButton("/");
        divide.setActionCommand("/");
        divide.addActionListener(this);

        divide.setForeground(Color.BLACK);
        divide.setBackground(Color.ORANGE);
        divide.setBorder(border);
        divide.setFont(font);

        operatorPanel.add(divide);

        JButton dot = new JButton(".");
        dot.setActionCommand(".");
        dot.addActionListener(this);

        dot.setForeground(Color.BLACK);
        dot.setBackground(Color.ORANGE);
        dot.setBorder(border);
        dot.setFont(new Font("Arial", Font.BOLD, 30));

        operatorPanel.add(dot);

        JButton equal = new JButton("=");
        equal.setActionCommand("=");
        equal.addActionListener(this);

        equal.setForeground(Color.BLACK);
        equal.setBackground(Color.ORANGE);
        equal.setBorder(border);
        equal.setFont(font);

        operatorPanel.add(equal);

        add(operatorPanel);
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (expressionComplete == false) {
            switch (action) {
                case "del":
                    delete();
                    break;

                case "ac":
                    expression = "";
                    break;

                case "=":
                    expressionComplete = true;
                    break;

                // Operators and Parenthesis needs padding to be parsed correctly
                case "+":
                case "-":
                case "*":
                case "/":
                    expression = expression + " " + action + " ";       //left and right padding for parenthesis
                    break;

                case "(":
                    expression = expression + action + " ";     //right padding for left parenthesis
                    break;

                case ")":
                    expression = expression + " " + action;     //left padding for right parenthesis
                    break;

                default:
                    expression = expression + action;
                    break;
            }
        }
        else {
            switch (e.getActionCommand()) {
                case "del":
                    delete();
                    expressionComplete = false;
                    break;

                case "ac":
                    expression = "";
                    expressionComplete = false;
                    break;

                default:
                    break;
            }
        }
        parent.keyboardPressed(action);
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

    public String getExpression() {
        return expression;
    }

    public Boolean expressionIsComplete() {
        return expressionComplete;
    }
}
