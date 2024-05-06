package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.model.candies.*;

import java.util.ArrayList;
import java.util.Random;

public class MultiThreadingClient extends Thread {
    private Board<Candy> board = new Board<>(new BoardSize(5, 5), this::getRandomCandy);

    public void run() {
      Random random = new Random();
        while (true) {
          var x = random.nextInt(board.getBoardSize().width());
          var y = random.nextInt(board.getBoardSize().height());
          var candy = getRandomCandy(null);
          board.replaceCellAt(new Position(x, y, board.getBoardSize()), candy);
          System.out.println(this.getName() + " changed Position: " + x + ", " + y + ", To Candy: " + candy);
            try {
                Thread.sleep(random.nextLong(1000, 5000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Candy getRandomCandy(Position position){
        Random random = new Random();
        int randomGetal = random.nextInt(9)+1;
        return switch (randomGetal) {
            case 1 -> new MultiCandy();
            case 2 -> new RareCandy();
            case 3 -> new RowSnapper();
            case 4 -> new TurnMaster();
            default -> new normalCandy(random.nextInt(4));
        };
    }

    public static void main(String[] args) {
        MultiThreadingClient thread1 = new MultiThreadingClient();
        MultiThreadingClient thread2 = new MultiThreadingClient();
        thread1.start();
        thread2.start();
    }
}
