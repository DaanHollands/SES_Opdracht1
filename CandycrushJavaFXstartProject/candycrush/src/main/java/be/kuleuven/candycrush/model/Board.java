package be.kuleuven.candycrush.model;
import com.google.common.collect.*;
import org.checkerframework.checker.units.qual.C;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
public class Board<T> implements Cloneable {
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

    public synchronized void swapCells(PositionPair positionPair){
        var temp = getCellAt(positionPair.position1());
        replaceCellAt(positionPair.position1(), getCellAt(positionPair.position2()));
        replaceCellAt(positionPair.position2(), temp);
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

    public Map<Position, T> getList(){
        return Collections.unmodifiableMap(list);
    }

    @Override
    public Board<T> clone() {
        try {
            @SuppressWarnings("unchecked") // This cast is safe because the Board is being cloned to the same type T
            Board<T> clonedBoard = (Board<T>) super.clone();
            clonedBoard.list = new ConcurrentHashMap<>(list); // Deep copy the list
            clonedBoard.reverseList = ArrayListMultimap.create(); // Create a new reverseList
            for (Map.Entry<T, Collection<Position>> entry : reverseList.asMap().entrySet()) {
                clonedBoard.reverseList.putAll(entry.getKey(), new ArrayList<>(entry.getValue())); // Deep copy positions in reverseList
            }
            return clonedBoard;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); // Re-throw as unchecked exception
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        list.forEach((position, cell) -> builder.append(position).append(": ").append(cell).append("\n"));
        return builder.toString();
    }
}
