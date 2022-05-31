public class SnakeMenu {
    public static void main(String[] args) {
        SnakePanel snakePanel = new SnakePanel();
        SnakeFrame snakeFrame = new SnakeFrame(snakePanel);
        SnakeControl snakeControl = new SnakeControl(snakePanel);
        Thread snakeMove = new Thread(snakeControl);
        snakeMove.start();

    }
}
