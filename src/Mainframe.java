import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.util.ArrayList;

public class Mainframe extends javax.swing.JFrame {
    Display display;
    Keyboard keyboard;

    public Mainframe(Dimension screenSize) {
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

            case "=":
                try {
                    String result = calculateExpression(parseExpression(keyboard.getExpression()));
                    display.setResult(result);
                }
                catch (RuntimeException e) {
                    display.setResult(e.getMessage());
                }
                break;

            case "del":
                if (keyboard.expressionIsComplete()) {
                    display.clearResult();
                    display.setExpression(keyboard.getExpression());
                }
                else {
                    display.setExpression(keyboard.getExpression());
                }
                break;

            case "ac":
                display.clearExpression();
                display.clearResult();
                break;

            default:
                display.setExpression(keyboard.getExpression());
                break;
        }
    }

    private ArrayList<String> parseExpression(String expression) {
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

        double result;
        for (String parsedToken : parsedTokens) {
            switch (parsedToken) {
                case "+":
                    result = Double.parseDouble(stack.get(stack.size() - 1)) + Double.parseDouble(stack.get(stack.size() - 2));
                    stack.removeLast();
                    stack.removeLast();
                    stack.add(Double.toString(result));
                    break;

                case "-":
                    result = Double.parseDouble(stack.get(stack.size() - 2)) - Double.parseDouble(stack.get(stack.size() - 1));
                    stack.removeLast();
                    stack.removeLast();
                    stack.add(Double.toString(result));
                    break;

                case "*":
                    result = Double.parseDouble(stack.get(stack.size() - 1)) * Double.parseDouble(stack.get(stack.size() - 2));
                    stack.removeLast();
                    stack.removeLast();
                    stack.add(Double.toString(result));
                    break;

                case "/":
                    result = Double.parseDouble(stack.get(stack.size() - 1)) / Double.parseDouble(stack.get(stack.size() - 2));
                    stack.removeLast();
                    stack.removeLast();
                    stack.add(Double.toString(result));
                    break;

                default:
                    result = Double.parseDouble(parsedToken);
                    stack.add(Double.toString(result));
                    break;
            }
        }
        return stack.getFirst();
    }
}
