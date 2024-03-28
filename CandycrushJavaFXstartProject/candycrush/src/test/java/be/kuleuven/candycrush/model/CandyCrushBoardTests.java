package be.kuleuven.candycrush.model;
import be.kuleuven.candycrush.model.candies.normalCandy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.Function;
public class CandyCrushBoardTests {

    @Test
    public void testFill() {
        BoardSize boardSize = new BoardSize(5, 5); // Assuming BoardSize is a class with a constructor that takes width and height
        Function<Position, Candy> cellCreator = position -> new normalCandy(0); // Replace T with your actual class

        Board<Candy> board = new Board<>(boardSize, cellCreator);

        for (int i = 0; i < boardSize.height(); i++) {
            for (int j = 0; j < boardSize.width(); j++) {
                Candy cell = board.getCellAt(new Position(i, j, boardSize));
                assertNotNull(cell);
            }
        }
    }

    @Test
    public void testCopyTo() {
        BoardSize boardSize = new BoardSize(5, 5);
        Function<Position, Candy> cellCreator = position -> new normalCandy(0);

        Board<Candy> board1 = new Board<>(boardSize, cellCreator);
        Board<Candy> board2 = new Board<>(boardSize, cellCreator);

        board1.copyTo(board2);

        for (int i = 0; i < boardSize.height(); i++) {
            for (int j = 0; j < boardSize.width(); j++) {
                Position pos = new Position(i, j, boardSize);
                assertEquals(board1.getCellAt(pos), board2.getCellAt(pos));
            }
        }
    }

}
