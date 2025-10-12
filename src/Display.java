import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Color;

public class Display extends javax.swing.JPanel {
    private JTextPane expressionDisplay;
    private JTextPane resultDisplay;

    public Display(Dimension displaySize) {
        setPreferredSize(displaySize);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Dimension expressionDisplaySize = new Dimension(displaySize.width, displaySize.height / 6);
        initExpressionDisplay(expressionDisplaySize);

        Dimension resultDisplaySize = new Dimension(displaySize.width, displaySize.height / 6);
        initResultDisplay(resultDisplaySize);
    }

    private void initExpressionDisplay(Dimension displaySize) {
        expressionDisplay = new JTextPane();
        expressionDisplay.setPreferredSize(displaySize);
        expressionDisplay.setBackground(Color.BLACK);

        StyledDocument expressionDisplayDoc = expressionDisplay.getStyledDocument();
        SimpleAttributeSet expressionDisplayAttributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(expressionDisplayAttributes, StyleConstants.ALIGN_LEFT);
        StyleConstants.setFontSize(expressionDisplayAttributes, 48);
        StyleConstants.setForeground(expressionDisplayAttributes, Color.WHITE);
        expressionDisplayDoc.setParagraphAttributes(0, expressionDisplayDoc.getLength(), expressionDisplayAttributes, true);
        expressionDisplay.setText("");

        add(expressionDisplay);
    }

    private void initResultDisplay(Dimension displaySize) {
        resultDisplay = new JTextPane();
        resultDisplay.setPreferredSize(displaySize);
        resultDisplay.setBackground(Color.BLACK);

        StyledDocument resultDisplayDoc = resultDisplay.getStyledDocument();
        SimpleAttributeSet resultDisplayAttributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(resultDisplayAttributes, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setFontSize(resultDisplayAttributes, 48);
        StyleConstants.setBold(resultDisplayAttributes, true);
        StyleConstants.setForeground(resultDisplayAttributes, Color.WHITE);
        resultDisplayDoc.setParagraphAttributes(0, resultDisplayDoc.getLength(), resultDisplayAttributes, true);
        resultDisplay.setText("");

        add(resultDisplay);
    }

    public void setExpression(String expression) {
        expressionDisplay.setText(expression);
    }

    public String getExpression() {
        return expressionDisplay.getText();
    }

    public void clearExpression() {
        expressionDisplay.setText("");
    }

    public void setResult(String result) {
        resultDisplay.setText(result);
    }

    public String getResult () {
        return resultDisplay.getText();
    }

    public void clearResult() {
        resultDisplay.setText("");
    }

}