package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.model.candies.*;
import com.google.common.collect.Streams;
import javafx.geometry.Pos;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CandycrushModel {
    private String speler;
    private int score;
    private final Board<Candy> board;
//    private Random random = new Random(0);

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
//        random = new Random(0);
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

    public Set<List<Position>> findAllMatches(){
        var streamHor = horizontalStartingPosition()
                .map(this::longestMatchToRight)
                .filter(list -> list.size() >= 3);
        var streamVer = verticalStartingPosition()
                .map(this::longestMatchDown)
                .filter(list -> list.size() >= 3);
        return Streams.concat(streamHor, streamVer).collect(Collectors.toSet());
    }

    private boolean firstTwoHaveSameCandy(Candy candy, Stream<Position> positions){
        if(candy instanceof EmptyCandy){
            return false;
        }
        return positions.limit(2).allMatch(position -> candy.equals(getCandyFromPosition(position)));
    }

    public Stream<Position> horizontalStartingPosition(){
        return IntStream.range(0, board.getBoardSize().width())
                .boxed()
                .flatMap(y -> IntStream.range(0, board.getBoardSize().height())
                    .mapToObj(x -> new Position(x, y, board.getBoardSize()))
                    .filter(position -> firstTwoHaveSameCandy(getCandyFromPosition(position), position.walkRight())));
    }
    public Stream<Position> verticalStartingPosition(){
        return IntStream.range(0, board.getBoardSize().width())
                .boxed()
                .flatMap(y -> IntStream.range(0, board.getBoardSize().height())
                    .mapToObj(x -> new Position(x, y, board.getBoardSize()))
                    .filter(position -> firstTwoHaveSameCandy(getCandyFromPosition(position), position.walkDown())));
    }

    public List<Position> longestMatchToRight(Position position){
        return position.walkRight()
                .takeWhile(position1 -> getCandyFromPosition(position1).equals(getCandyFromPosition(position)))
                .collect(Collectors.toList());
    }

    public List<Position> longestMatchDown(Position position){
        return position.walkDown()
                .takeWhile(position1 -> getCandyFromPosition(position1).equals(getCandyFromPosition(position)))
                .collect(Collectors.toList());
    }

    public void clearMatch(List<Position> match){
        if(match == null || match.isEmpty()){
            return;
        }
        board.replaceCellAt(match.getFirst(), new EmptyCandy());
        clearMatch(match.subList(1, match.size()));
    }

    public void fallDownTo(Position position){
        try{
            Position posAbove = new Position(position.x(), position.y() - 1, board.getBoardSize());
            if (!(getCandyFromPosition(position) instanceof EmptyCandy)){
                fallDownTo(posAbove);
                return;
            }
            while(getCandyFromPosition(posAbove) instanceof EmptyCandy){
                posAbove = new Position(posAbove.x(), posAbove.y() - 1, board.getBoardSize());
            }
            board.replaceCellAt(position, getCandyFromPosition(posAbove));
            board.replaceCellAt(posAbove, new EmptyCandy());
            fallDownTo(new Position(position.x(), position.y() - 1, board.getBoardSize()));
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean updateBoard(){
       return updateBoard(true);
    }

    public boolean updateBoard(boolean isFirstCall){
        var matches = findAllMatches();
        if(matches.isEmpty() && isFirstCall){
            return false;
        } else if (matches.isEmpty()){
            return true;
        } else {
            matches.forEach(this::clearMatch);
            matches.forEach(list -> list.forEach(this::fallDownTo));
            return updateBoard(false);
        }
    }
}
