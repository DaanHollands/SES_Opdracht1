package be.kuleuven.candycrush.model;

import java.util.Objects;

public record PositionPair(Position position1, Position position2) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionPair that = (PositionPair) o;
        return Objects.equals(position1, that.position1) && Objects.equals(position2, that.position2)
                 || Objects.equals(position1, that.position2) && Objects.equals(position2, that.position1);
    }

    @Override
    public String toString() {
        return "(r" + (position1.x()+1) + "c" + (position1.y()+1) + ") ⇄ (r" + (position2.x()+1) + "c" + (position2.y()+1) + ")";
    }
}
