package be.kuleuven.candycrushjavafx;

import java.util.ArrayList;
public class CandyCrushModel {
    private ArrayList<Candy> grid = new ArrayList<>();
    private int score;

    public CandyCrushModel(int hoogteGrid, int lengteGrid){
        for (int i = 0; i < hoogteGrid*lengteGrid; i++) {
            grid.add(new Candy());
        }
        this.score = 0;
    }


    public ArrayList<Candy> getGrid() {
        return grid;
    }

    public int getScore() {
        return score;
    }

    public boolean removeNeighbours(int gridX, int gridY){



        return false;
    }

}
