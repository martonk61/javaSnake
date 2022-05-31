import javax.swing.*;
import java.awt.*;

public class SnakeFrame extends JFrame {
    public SnakeFrame(SnakePanel snakePanel) {
        this.setLayout(new BorderLayout());
        this.add(snakePanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
