package be.kuleuven.candycrush.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SolvedBoardSolution implements Cloneable{
    private CandycrushModel currentModel;
    private List<PositionPair> solution;

    public SolvedBoardSolution(CandycrushModel model, List<PositionPair> solution) {
        try {
            this.currentModel = (CandycrushModel) model.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        this.solution = new ArrayList<>(solution);
    }

    public boolean isComplete(){
        CandycrushModel model = null;
        try {
            model = (CandycrushModel) currentModel.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return model.getAllValidSwaps().isEmpty() && !model.updateBoard();
    }

    public Set<PositionPair> getValidSwaps(){
        return currentModel.getAllValidSwaps();
    }

    public void applySwap(PositionPair swap){
        currentModel.applySwap(swap);
        solution.add(swap);
    }

    public boolean isBetterThan(SolvedBoard solvedBoard){
        return currentModel.getScore() > solvedBoard.getHighScore();
    }

    public SolvedBoard getSolvedBoard(){
        return new SolvedBoard(solution, currentModel.getScore());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SolvedBoardSolution clone = (SolvedBoardSolution) super.clone();
        clone.currentModel = (CandycrushModel) currentModel.clone();
        clone.solution = new ArrayList<>(solution);
        return clone;
    }
}
