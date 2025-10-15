package main.test;

import main.Mainframe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class MainframeTest {

    public Mainframe TestMainframe = new Mainframe(new Dimension(200, 200));;

    @Test
    public void twoPlusTwo() {
        double expected = 4;

        TestMainframe.setExpression("2 + 2");
        TestMainframe.keyboardPressed("=");

        double actual = TestMainframe.getResult();
        assertEquals(expected, actual);
    }

    @Test
    public void twoMinusTwo() {
        double expected = -1;

        TestMainframe.setExpression("2 - 3");
        TestMainframe.keyboardPressed("=");

        double actual = TestMainframe.getResult();
        assertEquals(expected, actual);
    }
}
