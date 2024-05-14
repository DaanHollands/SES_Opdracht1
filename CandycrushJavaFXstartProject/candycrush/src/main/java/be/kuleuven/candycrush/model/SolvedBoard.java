package be.kuleuven.candycrush.model;


import java.util.ArrayList;
import java.util.List;

public class SolvedBoard {

    private List<PositionPair> solution;

    private int highScore = 0;

    public SolvedBoard(List<PositionPair> solution, int score) {
        this.solution = solution;
        this.highScore = score;
    }

    public List<PositionPair> getSolution() {
        return solution;
    }

    public int getHighScore(){
        return highScore;
    }

    public SolvedBoard(){
        solution = new ArrayList<>();
        highScore = 0;
    }

    public SolvedBoard solve(CandycrushModel model) {
        return solve(new SolvedBoardSolution(model, solution), null);
    }

    public SolvedBoard solve(SolvedBoardSolution currentSolution, SolvedBoard bestSolve)  {
        if (currentSolution.isComplete()) {
            if (bestSolve == null || currentSolution.isBetterThan(bestSolve)) {
                return currentSolution.getSolvedBoard();
            } else {
                return null;
            }
        }
        for (PositionPair move : currentSolution.getValidSwaps()) {
            SolvedBoardSolution maybeSolution = null;
            try {
                maybeSolution = (SolvedBoardSolution) currentSolution.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            maybeSolution.applySwap(move);
            SolvedBoard foundSolution = solve(maybeSolution, bestSolve);
            if(foundSolution != null){
                bestSolve = foundSolution;
            }
        }
        return bestSolve;
    }
}

