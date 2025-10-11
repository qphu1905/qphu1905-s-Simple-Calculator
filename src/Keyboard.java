import javax.swing.*;
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

        Dimension numberPanelSize = new Dimension(2 * keyboardSize.width / 3, keyboardSize.height);
        initNumberPanel(numberPanelSize);

        Dimension operatorPanelSize = new Dimension(keyboardSize.width / 3, keyboardSize.height);
        initOperatorPanel(operatorPanelSize);
    }

    private void initNumberPanel(Dimension numberPanelSize) {
        numberPanel = new JPanel();
        numberPanel.setPreferredSize(numberPanelSize);
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

        add(numberPanel);
    }

    private void initOperatorPanel(Dimension operatorPanelSize) {
        operatorPanel = new JPanel();
        operatorPanel.setPreferredSize(operatorPanelSize);
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
