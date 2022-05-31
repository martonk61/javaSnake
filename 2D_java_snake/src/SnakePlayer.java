public class SnakePlayer {
    private int[] snakeX;
    private int[] snakeY;
    private final int headColor;
    private final int bodyColor;
    private int snakeBody;

    public SnakePlayer(int headColor, int bodyColor, char direction, int start_y, int UNIT_PIXEL, int PANEL_WIDTH, int PANEL_HEIGHT) {
        this.headColor = headColor;
        this.bodyColor = bodyColor;
        snakeX = new int[(PANEL_WIDTH / UNIT_PIXEL) * (PANEL_WIDTH / UNIT_PIXEL)];
        snakeY = new int[(PANEL_HEIGHT / UNIT_PIXEL) * (PANEL_HEIGHT / UNIT_PIXEL)];
        snakeX[2] = -UNIT_PIXEL;
        snakeX[1] = -UNIT_PIXEL;
        snakeX[0] = UNIT_PIXEL;
        snakeY[0] = snakeY[1] = snakeY[2] = start_y;
        snakeBody = 3;
    }

    public int getSnakeX(int i) {
        return snakeX[i];
    }

    public int[] getSnakeX() {
        return snakeX;
    }

    public void setSnakeX(int[] snakeX) {
        this.snakeX = snakeX;
    }

    public int getSnakeY(int i) {
        return snakeY[i];
    }

    public int[] getSnakeY() {
        return snakeY;
    }

    public void setSnakeY(int[] snakeY) {
        this.snakeY = snakeY;
    }

    public int getSnakeBody() {
        return snakeBody;
    }

    public void setSnakeBody(int snakeBody) {
        this.snakeBody = snakeBody;
    }

    public int getHeadColor() {
        return headColor;
    }

    public int getBodyColor() {
        return bodyColor;
    }
}
