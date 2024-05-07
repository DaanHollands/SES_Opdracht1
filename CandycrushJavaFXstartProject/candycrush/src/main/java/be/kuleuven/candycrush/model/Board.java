package be.kuleuven.candycrush.model;
import com.google.common.collect.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
public class Board<T> {
    private volatile Map<Position, T> list = new ConcurrentHashMap<>();

    private volatile Multimap<T, Position> reverseList = ArrayListMultimap.create();
    private final BoardSize boardSize;


    public BoardSize getBoardSize(){
        return boardSize;
    }

    public Board(BoardSize boardSize, Function<Position, T> CellCreator) {
        this.boardSize = boardSize;
        fill(CellCreator);
    }

    public synchronized T getCellAt(Position position){
        if(position.toIndex() <= list.size()){
            return list.get(position);
        } else {
            throw new IndexOutOfBoundsException();

        }
    }

    public synchronized void replaceCellAt(Position position, T newCell){
        if(position.toIndex() <= list.size()){
            reverseList.remove(list.get(position), position);
            list.replace(position, newCell);
            reverseList.put(newCell, position);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public synchronized void fill(Function<Position, T> CellCreator){
        for (int i = 0; i < boardSize.height(); i++) {
            for (int j = 0; j < boardSize.width(); j++) {
                Position pos = new Position(i, j, getBoardSize());
                list.put(pos, CellCreator.apply(pos));
                reverseList.put(CellCreator.apply(pos), pos);
            }
        }
    }



    public synchronized List<Position> getPositionsOfElement(T cell){
        return Collections.unmodifiableList((List<Position>) reverseList.get(cell));
    }

    public synchronized void copyTo(Board<T> otherBoard){
        if(boardSize.equals(otherBoard.getBoardSize())){
            for (int i = 0; i < boardSize.height(); i++) {
                for (int j = 0; j < boardSize.width(); j++) {
                    Position pos = new Position(i, j, boardSize);
                    otherBoard.replaceCellAt(pos, getCellAt(pos));
                }
            }
        } else {
            throw new IllegalArgumentException("Different Boardsize!");
        }
    }
}
