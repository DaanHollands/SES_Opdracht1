package be.kuleuven.candycrush.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public class Board<T> {
    private ArrayList<T> list;
    private BoardSize boardSize;

    public BoardSize getBoardSize(){
        return boardSize;
    }

    public Board(BoardSize boardSize, Function<Position, T> CellCreator) {
        this.boardSize = boardSize;
        this.list = new ArrayList<>(Collections.nCopies(boardSize.height()*boardSize.height(), null));
        fill(CellCreator);
    }

    public T getCellAt(Position position){
        if(position.toIndex() <= list.size()){
            return list.get(position.toIndex());
        } else {
            throw new IndexOutOfBoundsException();

        }
    }

    public void replaceCellAt(Position position, T newCell){
        if(position.toIndex() <= list.size()){
            list.set(position.toIndex(), newCell);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void fill(Function<Position, T> CellCreator){
        for (int i = 0; i < boardSize.height(); i++) {
            for (int j = 0; j < boardSize.width(); j++) {
                list.set((j + (boardSize.width() * i)), CellCreator.apply(new Position(i, j, boardSize)));
            }
        }
    }

    public void copyTo(Board<T> otherBoard){
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
