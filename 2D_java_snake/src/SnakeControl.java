import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SnakeControl implements Runnable {
    SnakePanel sp;

    public SnakeControl(SnakePanel sp) {
        this.sp = sp;
        sp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                sp.mouseX = e.getX();
                sp.mouseY = e.getY();
                sp.isPressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                sp.isPressed = false;
            }
        });
        sp.requestFocus();
        sp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W && sp.getDirection() != 'D') {
                    sp.setDirection('U');
                } else if (e.getKeyCode() == KeyEvent.VK_S && sp.getDirection() != 'U') {
                    sp.setDirection('D');
                } else if (e.getKeyCode() == KeyEvent.VK_A && sp.getDirection() != 'R') {
                    sp.setDirection('L');
                } else if (e.getKeyCode() == KeyEvent.VK_D && sp.getDirection() != 'L') {
                    sp.setDirection('R');
                }
            }
        });
    }

    @Override
    public void run() {
        while (!sp.isExit()) {
            try {
                Thread.sleep(sp.getSPEED());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (sp.isRunning()) {
                sp.checkCollosion();
                sp.move();
                sp.checkApple();
                sp.repaint();
            } else {
                sp.RestartMenu();
                sp.MainMenu();
                sp.dificultyMenu();
            }
        }
    }
}

