package be.kuleuven.candycrush.model;


import java.util.ArrayList;

public record BoardSize(int width , int height) {
    public BoardSize {
        if (width<0||height<0) throw new IllegalArgumentException("Kan geen negatieve getallen aannemen");
    }

    public Iterable<Position> positions(){
        ArrayList<Position> positions = new ArrayList<>();
        for (int i = 0; i <= width*height - 1; i++) {
            positions.add(Position.fromIndex(i, this));
        }
        return positions;
    }
}
