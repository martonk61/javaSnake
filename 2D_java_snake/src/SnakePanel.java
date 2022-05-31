import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class SnakePanel extends JPanel {
    private final Random rand;
    private final int PANEL_WIDTH = 600;
    private final int PANEL_HEIGHT = 600;
    private final int UNIT_PIXEL = 20;
    private int SPEED = 120;

    private int AppleX, AppleY;
    private int GoldAX = -UNIT_PIXEL, GoldAY = -UNIT_PIXEL;
    private char direction;
    boolean running = false;
    boolean exit = false;
    boolean isPressed = false;
    double mouseX = 0.0, mouseY = 0.0;

    BufferedImage apple, RestartB, MenuB, MenuBackgroundB, GameOver, snakeTitle, startButton, difficultyButton, exitButton, ChooseDifficulty;
    BufferedImage Beg, Beg_clicked, Med, Med_clicked, Hard, Hard_clicked, GoldApple;

    InputStream music;
    AudioStream audio;

    boolean restartmenu = false;
    boolean mainmenu = false;
    boolean difficultymenu = false;
    boolean settingsmenu = false;

    SnakePlayer Player = new SnakePlayer(1, 1, 'R', 0, 1, 3, 3);

    SnakePanel() {
        rand = new Random();
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        try {
            music = new FileInputStream("menuMusic.wav");
            audio = new AudioStream(music);
            apple = ImageIO.read(new File("apple.png"));
            RestartB = ImageIO.read(new File("RestartButton.png"));
            MenuB = ImageIO.read(new File("MenuButton.png"));
            MenuBackgroundB = ImageIO.read(new File("MenuBackground.png"));
            GameOver = ImageIO.read(new File("GameOver.png"));
            snakeTitle = ImageIO.read(new File("SnakeTitle.png"));
            startButton = ImageIO.read(new File("StartButton.png"));
            difficultyButton = ImageIO.read(new File("DifficultyButton.png"));
            exitButton = ImageIO.read(new File("exitButton.png"));
            ChooseDifficulty = ImageIO.read(new File("ChooseDiff.png"));
            GoldApple = ImageIO.read(new File("GoldApple.png"));

            Beg = ImageIO.read(new File("Beginner.png"));
            Beg_clicked = ImageIO.read(new File("Beginner_clicked.png"));
            Med = ImageIO.read(new File("Medium.png"));
            Med_clicked = ImageIO.read(new File("Medium_clicked.png"));
            Hard = ImageIO.read(new File("Hard.png"));
            Hard_clicked = ImageIO.read(new File("Hard_clicked.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        mainmenu = true;
        AudioPlayer.player.start(audio);
    }

    public void StartSnake() {
        running = true;
        int backGroundColor = 4633;
        this.setBackground(new Color(backGroundColor));

        //Az első mezőből indul a kígyó, jobbra mozog
        Player = new SnakePlayer(12271107, 13264642, 'R', 0, UNIT_PIXEL, PANEL_WIDTH, PANEL_HEIGHT);
        direction = 'R';

        //Létrehoz egy új almát
        NewApple();
    }

    //új alma
    public void NewApple() {
        AppleX = rand.nextInt(PANEL_WIDTH / UNIT_PIXEL);
        AppleY = rand.nextInt(PANEL_HEIGHT / UNIT_PIXEL);
    }

    public void NewGoldApple() {
        if (rand.nextInt(5) >= 4) {
            GoldAX = rand.nextInt(PANEL_WIDTH / UNIT_PIXEL);
            GoldAY = rand.nextInt(PANEL_HEIGHT / UNIT_PIXEL);
        }
    }

    //vizsgálja, hogy a kígyó tud-e almát enni
    public void checkApple() {
        if (running) {
            if (AppleX == Player.getSnakeX(0) / UNIT_PIXEL && AppleY == Player.getSnakeY(0) / UNIT_PIXEL) {
                Player.setSnakeBody(Player.getSnakeBody() + 1);
                NewApple();
                if (GoldAY == -UNIT_PIXEL) NewGoldApple();
            }
            if (GoldAX == Player.getSnakeX(0) / UNIT_PIXEL && GoldAY == Player.getSnakeY(0) / UNIT_PIXEL) {
                GoldAX = -UNIT_PIXEL;
                GoldAY = -UNIT_PIXEL;
                Player.setSnakeBody(Player.getSnakeBody() + 3);
            }

        }
    }

    //vizsgálja, hogy a kígyó nekiütközött-e a falnak
    public void checkCollosion() {
        if (running) {
            if (Player.getSnakeX(0) < 0 || Player.getSnakeX(0) >= PANEL_WIDTH || Player.getSnakeY(0) < 0 || Player.getSnakeY(0) >= PANEL_HEIGHT) {
                running = false;
            }
            for (int i = 1; i < Player.getSnakeBody(); i++) {
                if (Player.getSnakeX(0) == Player.getSnakeX(i) && Player.getSnakeY(0) == Player.getSnakeY(i)) {
                    running = false;
                    break;
                }
            }
        }
    }

    //a kígyó mozgásáért felel
    public void move() {
        if (running) {
            int[] snakex = Player.getSnakeX();
            int[] snakey = Player.getSnakeY();

            //eggyel elmozdul, helyet csinálva a fejnek
            for (int i = Player.getSnakeBody() - 1; i >= 0; i--) {
                snakex[i + 1] = snakex[i];
                snakey[i + 1] = snakey[i];
            }

            //a fejet az iránynak megfelelően mozgatja el
            switch (direction) {
                case 'R': {
                    snakex[0] = snakex[1] + UNIT_PIXEL;
                    break;
                }
                case 'L': {
                    snakex[0] = snakex[1] - UNIT_PIXEL;
                    break;
                }
                case 'U': {
                    snakey[0] = snakey[1] - UNIT_PIXEL;
                    break;
                }
                case 'D': {
                    snakey[0] = snakey[1] + UNIT_PIXEL;
                    break;
                }
            }
            Player.setSnakeX(snakex);
            Player.setSnakeY(snakey);
        }
    }

    public void RestartMenu() {
        if (restartmenu && !mainmenu && !difficultymenu && !settingsmenu) {
            if (mouseX > (double) PANEL_WIDTH / 2 - 90 && mouseX < (double) PANEL_WIDTH / 2 - 90 + 180 && mouseY > 300 && mouseY < 370 && isPressed) {
                StartSnake();
                restartmenu = false;
            } else if (mouseX > (double) PANEL_WIDTH / 2 - 90 && mouseX < (double) PANEL_WIDTH / 2 - 90 + 180 && mouseY > 220 && mouseY < 290 && isPressed) {
                restartmenu = false;
                mainmenu = true;
                repaint();
            }
            isPressed = false;
        }
    }

    public void MainMenu() {
        if (mainmenu && !restartmenu) {
            if (mouseX > (double) PANEL_WIDTH / 2 - 90 && mouseX < (double) PANEL_WIDTH / 2 + 90 && mouseY > 200 && mouseY < 270 && isPressed) {
                StartSnake();
                mainmenu = false;
            } else if (mouseX > (double) PANEL_WIDTH / 2 - 90 && mouseX < (double) PANEL_WIDTH / 2 + 90 && mouseY > 290 && mouseY < 360 && isPressed) {
                //Difficulty
                mainmenu = false;
                difficultymenu = true;
                repaint();
            } else if (mouseX > (double) PANEL_WIDTH / 2 - 90 && mouseX < (double) PANEL_WIDTH / 2 + 90 && mouseY > 380 && mouseY < 450 && isPressed) {
                exit = false;
                System.exit(0);
            }
            isPressed = false;
        }
    }

    public void dificultyMenu() {
        if (difficultymenu && !mainmenu && !restartmenu) {
            if (mouseX > (double) PANEL_WIDTH / 2 - 90 && mouseX < (double) PANEL_WIDTH / 2 + 90 && mouseY > 200 && mouseY < 270 && isPressed) {
                difficultymenu = false;
                mainmenu = true;
                repaint();
            } else if (mouseX > 40 && mouseX < 185 && mouseY > 450 && mouseY < 520 && isPressed) {
                SPEED = 200;
            } else if (mouseX > 220 && mouseX < 365 && mouseY > 450 && mouseY < 520 && isPressed) {
                SPEED = 120;
            } else if (mouseX > 400 && mouseX < 545 && mouseY > 450 && mouseY < 520 && isPressed) {
                SPEED = 70;
            }
            repaint();
            isPressed = false;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        //Kirajzolja az almát
        g.drawImage(apple, AppleX * UNIT_PIXEL, AppleY * UNIT_PIXEL, UNIT_PIXEL, UNIT_PIXEL, null);
        g.drawImage(GoldApple, GoldAX * UNIT_PIXEL, GoldAY * UNIT_PIXEL, UNIT_PIXEL, UNIT_PIXEL, null);

        //Kirajzolja a kígyó testét
        if (isRunning()) {
            for (int i = 0; i < Player.getSnakeBody(); i++) {
                if (i == 0) {
                    int snakeHeadColor = Player.getHeadColor();
                    g.setColor(new Color(snakeHeadColor));
                    g.fillRect(Player.getSnakeX(i), Player.getSnakeY(i), UNIT_PIXEL, UNIT_PIXEL);
                } else {
                    int snakeBodyColor = Player.getBodyColor();
                    g.setColor(new Color(snakeBodyColor));
                    g.fillRect(Player.getSnakeX(i), Player.getSnakeY(i), UNIT_PIXEL, UNIT_PIXEL);
                }
            }
        }

        // Kirajzolja az ujraindito menut
        if (!running && !restartmenu && !mainmenu && !difficultymenu && !settingsmenu) {
            g.drawImage(MenuBackgroundB, 0, 0, null);
            g.drawImage(GameOver, 0, 60, null);
            g.drawImage(MenuB, PANEL_WIDTH / 2 - 90, 220, 180, 70, null);
            g.drawImage(RestartB, PANEL_WIDTH / 2 - 90, 300, 180, 70, null);
            g.setFont(new Font("Segoe UI", 1, 30));
            g.drawString("Score: " + Player.getSnakeBody(), PANEL_WIDTH / 2 - 60, 450);
            restartmenu = true;
        }
        if (mainmenu) {
            g.drawImage(MenuBackgroundB, 0, 0, null);
            g.drawImage(snakeTitle, 0, 10, null);
            g.drawImage(startButton, PANEL_WIDTH / 2 - 90, 200, 180, 70, null);
            g.drawImage(difficultyButton, PANEL_WIDTH / 2 - 90, 290, 180, 70, null);
            g.drawImage(exitButton, PANEL_WIDTH / 2 - 90, 380, 180, 70, null);
        }
        if (difficultymenu) {
            g.drawImage(MenuBackgroundB, 0, 0, null);
            g.drawImage(snakeTitle, 0, 10, null);
            g.drawImage(MenuB, PANEL_WIDTH / 2 - 90, 200, 180, 70, null);
            g.drawImage(ChooseDifficulty, -2, 320, null);
            if (SPEED == 200) g.drawImage(Beg_clicked, PANEL_WIDTH / 4 - 130, 450, null);
            else g.drawImage(Beg, PANEL_WIDTH / 4 - 130, 450, null);
            if (SPEED == 120) g.drawImage(Med_clicked, PANEL_WIDTH / 2 - 85, 450, null);
            else g.drawImage(Med, PANEL_WIDTH / 2 - 85, 450, null);
            if (SPEED == 70) g.drawImage(Hard_clicked, PANEL_WIDTH / 4 * 3 - 50, 450, null);
            else g.drawImage(Hard, PANEL_WIDTH / 4 * 3 - 50, 450, null);
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < PANEL_HEIGHT / UNIT_PIXEL; i++) {
            g.drawLine(0, i * UNIT_PIXEL, PANEL_WIDTH, i * UNIT_PIXEL);
        }
        for (int i = 0; i < PANEL_WIDTH / UNIT_PIXEL; i++) {
            g.drawLine(i * UNIT_PIXEL, 0, i * UNIT_PIXEL, PANEL_HEIGHT);
        }
    }

    public boolean isRunning() {
        return running;
    }

    public char getDirection() {
        return this.direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public boolean isExit() {
        return exit;
    }

    public int getSPEED() {
        return SPEED;
    }
}
