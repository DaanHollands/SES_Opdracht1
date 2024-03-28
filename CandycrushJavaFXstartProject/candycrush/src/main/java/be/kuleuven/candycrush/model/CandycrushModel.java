package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.model.candies.*;

import java.util.ArrayList;
import java.util.Random;

public class CandycrushModel {
    private String speler;
    private int score;
    private Board<Candy> board;
    public CandycrushModel(String speler) {
        this.speler = speler;
        this.board = new Board<Candy>(new BoardSize(10, 10), this::getCandy);
    }

    public void changeName(String name){
        this.speler = name;
    }

    public Candy getCandy(Position position){
        return getRandomCandy();
    }
    private Candy getRandomCandy(){
        Random random = new Random();
        int randomGetal = random.nextInt(9)+1;
        return switch (randomGetal) {
            case 1 -> new MultiCandy();
            case 2 -> new RareCandy();
            case 3 -> new RowSnapper();
            case 4 -> new TurnMaster();
            default -> new normalCandy(random.nextInt(4));
        };
    }
    public String getSpeler() {
        return speler;
    }

    public int getScore(){return score;}

    public BoardSize getBoardSize(){
        return this.board.getBoardSize();
    }

    public void candyWithPositionSelected(Position position){
        if (position.toIndex() != -1){
            board.replaceCellAt(position,getRandomCandy());
            System.out.println("Random generate");
        }else{
            System.out.println("model:candyWithIndexSelected:indexWasMinusOne");
        }
    }

    public void increaseScore(){
        score++;
    }

    public void resetSpeelbord(){
        board.fill(this::getCandy);
    }

    public void changeNeighbours(Position position){
      ArrayList<Position> Neighbours = (ArrayList<Position>) getSameNeighbourPositions(position);
      if(Neighbours.size() >= 4) {
        candyWithPositionSelected(position);
        increaseScore();
        for (Position pos : Neighbours) {
            candyWithPositionSelected(pos);
            increaseScore();
        }
      }
    }

    public Iterable<Position> getSameNeighbourPositions(Position position){
        ArrayList<Position> neighbours = new ArrayList<>();
        for(Position p : position.neighbourPosition()){
            if(getCandyFromPosition(p).equals(getCandyFromPosition(position))){
                neighbours.add(p);
            }
        }
        return neighbours;
    }

    public Candy getCandyFromPosition(Position position) {
        return board.getCellAt(position);
    }
}
