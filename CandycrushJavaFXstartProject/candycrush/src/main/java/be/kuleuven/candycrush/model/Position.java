package be.kuleuven.candycrush.model;

import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
//                    System.out.println(e.getMessage());;
                }
            }
        }

        return positions;
    }


    public Stream<Position> walkLeft(){
        return IntStream.rangeClosed(0, x)
                .mapToObj(i -> new Position(x - i, y, boardSize));
    }

    public Stream<Position> walkRight(){
        return IntStream.rangeClosed(x, boardSize().width()-1)
                .mapToObj(i -> new Position(i, y, boardSize));
    }

    public Stream<Position> walkUp(){
        return IntStream.rangeClosed(0, y)
                .mapToObj(i -> new Position(x, y - i, boardSize));
    }

    public Stream<Position> walkDown(){
        return IntStream.rangeClosed(y, boardSize().height()-1)
                .mapToObj(i -> new Position(x, i, boardSize));
    }

    boolean isLastColumn(){
        return x == boardSize().width()-1;
    }

    @Override
    public String toString() {
        return "(x: " + (x+1) + " y: " + (y+1) + ")";
    }
}
