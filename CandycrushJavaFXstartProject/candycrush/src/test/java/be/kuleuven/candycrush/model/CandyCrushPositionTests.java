package be.kuleuven.candycrush.model;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
public class CandyCrushPositionTests {

    @Test
    public void gegevenBoardsize_wanneerIsGoedeIndex_geeftIndexTerug() {
        Position pos = new Position(2,2,new BoardSize(10,10));
        int index = pos.toIndex();
        assertThat(index).isEqualTo(22);
    }

    @Test
    public void gegevenBoarsize_wanneerGegevenIndex_geeftPositionTerug() {
        Position pos = new Position(10,10,new BoardSize(10,10));
        Position result = pos.fromIndex(22, new BoardSize(10,10));
        assertThat(result).isEqualTo(new Position(2,2,new BoardSize(10,10)));

    }

    @Test
    public void gegevenBoardsize_WannerNeighborPosition_geeftDeIndexenVanPositionsWeer() {
        Position pos = new Position(1,1,new BoardSize(10,10));
        Iterable<Position> result = pos.neighbourPosition();
        BoardSize board = new BoardSize(10,10);
        ArrayList<Position> array = new ArrayList<>(List.of(
                new Position(0,0, board), new Position(1,0, board), new Position(2,0, board),
                new Position(0,1, board), new Position(1,1, board), new Position(2,1, board),
                new Position(0,2, board), new Position(1,2, board), new Position(2,2, board)
        ));

        assertThat(result).containsAll(array);
    }

    @Test
    public void gegevenBoardsize_WanneerTestVoorLaatsteColumn_geeftTrueTerugIndienLaatste() {
        Position pos = new Position(9,1,new BoardSize(10,10));
        boolean result = pos.isLastColumn();
        assertThat(result).isEqualTo(true);
}
}