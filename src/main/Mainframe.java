package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Mainframe extends javax.swing.JFrame {
    Display display;
    Keyboard keyboard;
    private String expression;
    private Boolean expressionComplete;
    private double result;

    public Mainframe(Dimension screenSize) {

        expression = "";
        expressionComplete = false;
        result = 0;

        int width = screenSize.width;
        int height = screenSize.height;
        setSize(screenSize);
        setTitle("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(screenSize);

        Dimension displaySize = new Dimension(width, height / 3);
        display = new Display(displaySize);

        Dimension keyboardSize = new Dimension(width, 2 * height / 3);
        keyboard = new Keyboard(keyboardSize, this);

        add(mainPanel);
        mainPanel.add(display);
        mainPanel.add(keyboard);
        setVisible(true);
    }

    public void keyboardPressed(String action) {

        switch (action) {
            case "del":

                if (expressionComplete) {

                    expressionComplete = false;

                }

                delete();
                display.setExpression(expression);
                display.clearResult();

                break;

            case "ac":

                if (expressionComplete) {

                    expressionComplete = false;

                }

                expression = "";
                display.clearExpression();
                display.clearResult();

                break;

            case "=":
                expressionComplete = true;

                try {

                    String resultString = calculateExpression(parseExpression());
                    result = Double.parseDouble(resultString);
                    display.setResult(resultString);

                }
                catch (RuntimeException e) {

                    display.setResult(e.getMessage());

                }

                break;

            // Operators and Parenthesis needs padding to be parsed correctly
            case "+":
            case "-":
            case "*":
            case "/":

                if (expressionComplete == false) {

                    expression = expression + " " + action + " ";       //left and right padding for parenthesis
                    display.setExpression(expression);

                }

                break;

            case "(":

                if (expressionComplete == false) {

                    expression = expression + action + " ";     //right padding for left parenthesis
                    display.setExpression(expression);

                }

                break;

            case ")":

                if (expressionComplete == false) {

                    expression = expression + " " + action;     //left padding for right parenthesis
                    display.setExpression(expression);

                }

                break;

            default:

                if (expressionComplete == false) {

                    expression = expression + action;
                    display.setExpression(expression);

                }

                break;
        }
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

    private ArrayList<String> parseExpression() {
        String[] tokens = expression.split(" ");
        switch (tokens[tokens.length - 1]) {
            case "+":
            case "-":
            case "*":
            case "/":
                throw new RuntimeException("Syntax error");
        }
        ArrayList<String> outputQueue = new ArrayList<>();
        ArrayList<String> operatorStack = new ArrayList<>();

        for (String token : tokens) {
            switch (token) {
                // Due to how the parser works the existence of an empty string
                // in the tokenized list means the user inserted two operands in
                // a row. A syntax error will be thrown.
                case "":
                    throw new RuntimeException("Syntax error");
                case "*":
                case "/":
                    while (!operatorStack.isEmpty() && !operatorStack.getLast().equals("(") && (operatorStack.getLast().equals("*") || operatorStack.getLast().equals("/"))) {
                        outputQueue.add(operatorStack.getLast());
                        operatorStack.removeLast();
                    }
                    operatorStack.add(token);
                    break;

                case "+":
                case "-":
                    while (!operatorStack.isEmpty() && !(operatorStack.getLast().equals("("))) {
                        outputQueue.add(operatorStack.getLast());
                        operatorStack.removeLast();
                    }
                    operatorStack.add(token);
                    break;

                case "(":
                    operatorStack.add(token);
                    break;

                case ")":
                    while (!operatorStack.isEmpty() && !(operatorStack.getLast().equals("("))) {
                        outputQueue.add(operatorStack.getLast());
                        operatorStack.removeLast();
                    }

                    if (operatorStack.isEmpty()) {
                        throw new RuntimeException("Syntax error");
                    } else if (operatorStack.getLast().equals("(")) {
                        operatorStack.removeLast();
                    }
                    break;

                default:
                    outputQueue.add(token);
            }
        }

        while (!operatorStack.isEmpty()) {
            if (operatorStack.getLast().equals("(")) {
                throw new RuntimeException("Syntax error");
            }
            else {
                outputQueue.add(operatorStack.getLast());
                operatorStack.removeLast();
            }
        }

        return outputQueue;
    }

    private String calculateExpression (ArrayList<String> parsedTokens) {
        ArrayList<String> stack = new ArrayList<>();

        double intermidiateResult;
        for (String parsedToken : parsedTokens) {
            switch (parsedToken) {
                case "+":
                    intermidiateResult = Double.parseDouble(stack.get(stack.size() - 1)) + Double.parseDouble(stack.get(stack.size() - 2));
                    stack.removeLast();
                    stack.removeLast();
                    stack.add(Double.toString(intermidiateResult));
                    break;

                case "-":
                    intermidiateResult = Double.parseDouble(stack.get(stack.size() - 2)) - Double.parseDouble(stack.get(stack.size() - 1));
                    stack.removeLast();
                    stack.removeLast();
                    stack.add(Double.toString(intermidiateResult));
                    break;

                case "*":
                    intermidiateResult = Double.parseDouble(stack.get(stack.size() - 1)) * Double.parseDouble(stack.get(stack.size() - 2));
                    stack.removeLast();
                    stack.removeLast();
                    stack.add(Double.toString(intermidiateResult));
                    break;

                case "/":
                    intermidiateResult = Double.parseDouble(stack.get(stack.size() - 1)) / Double.parseDouble(stack.get(stack.size() - 2));
                    stack.removeLast();
                    stack.removeLast();
                    stack.add(Double.toString(intermidiateResult));
                    break;

                default:
                    intermidiateResult = Double.parseDouble(parsedToken);
                    stack.add(Double.toString(intermidiateResult));
                    break;
            }
        }
        return stack.getFirst();
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public double getResult() {
        return result;
    }
}
