import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        Mainframe frame = new Mainframe(screenSize);
    }
}