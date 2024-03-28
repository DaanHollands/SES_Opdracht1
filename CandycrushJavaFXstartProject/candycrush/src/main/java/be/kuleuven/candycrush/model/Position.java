package be.kuleuven.candycrush.model;

import java.util.ArrayList;

public record Position(int x, int y, BoardSize boardSize) {
    public Position {
        if (x >= boardSize.width() || x < 0) throw new IllegalArgumentException("Geen geldige positie");
        if (y >= boardSize.height() || y < 0) throw new IllegalArgumentException("Geen geldige positie");
    }

    public int toIndex(){
        return x + y*boardSize().width();
    }

    public static Position fromIndex(int index, BoardSize boardSize){
        int x = index % boardSize.width();
        int y = index / boardSize.height();

        return new Position(x, y, boardSize);
    }

    public Iterable<Position> neighbourPosition(){
        ArrayList<Position> positions = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    positions.add(new Position(x+i,y+j, boardSize));
                }catch (IllegalArgumentException e){
                    System.out.println(e);
                }
            }
        }

        return positions;
    }

    boolean isLastColumn(){
        return x == boardSize().width()-1;
    }
}
