package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.model.candies.*;
import com.google.common.collect.Streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CandycrushModel implements Cloneable{
    private String speler;
    private int score;
    private Board<Candy> board;
    private Random random = new Random(0);


    public Board<Candy> getBoard() {
        return board;
    }

    public CandycrushModel(String speler, BoardSize boardSize) {
        this.speler = speler;
        this.board = new Board<Candy>(boardSize, this::getCandy);
    }

    public CandycrushModel(String speler) {
        this.speler = speler;
        this.board = new Board<Candy>(new BoardSize(4, 4), this::getCandy);
    }

    public void changeName(String name){
        this.speler = name;
    }

    public Candy getCandy(Position position){
        return getRandomCandy();
    }

    private Candy getRandomCandy(){
//        Random random = new Random();
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
        }else{
//            System.out.println("model:candyWithIndexSelected:indexWasMinusOne");
        }
    }

    public void increaseScore(){
        score++;
    }

    public void resetSpeelbord(){
        random = new Random(0);
        board.fill(this::getCandy);
        this.score = 0;
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

    private Set<List<Position>> findAllMatches(){
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

    private boolean leftIsDifferentCandy(Position position){
        if(position.x() == 0){
            return true;
        }
        return !getCandyFromPosition(position).equals(getCandyFromPosition(new Position(position.x() - 1, position.y(), getBoardSize())));
    }

    private boolean upIsDifferentCandy(Position position){
        if(position.y() == 0){
            return true;
        }
        return !getCandyFromPosition(position).equals(getCandyFromPosition(new Position(position.x(), position.y() - 1, getBoardSize())));
    }

    private Stream<Position> horizontalStartingPosition(){
        return IntStream.range(0, board.getBoardSize().width())
                .boxed()
                .flatMap(y -> IntStream.range(0, board.getBoardSize().height())
                    .mapToObj(x -> new Position(x, y, board.getBoardSize()))
                    .filter(position -> leftIsDifferentCandy(position) && firstTwoHaveSameCandy(getCandyFromPosition(position), position.walkRight())));
    }
    private Stream<Position> verticalStartingPosition(){
        return IntStream.range(0, board.getBoardSize().width())
                .boxed()
                .flatMap(y -> IntStream.range(0, board.getBoardSize().height())
                    .mapToObj(x -> new Position(x, y, board.getBoardSize()))
                    .filter(position -> upIsDifferentCandy(position) && firstTwoHaveSameCandy(getCandyFromPosition(position), position.walkDown())));
    }

    private List<Position> longestMatchToRight(Position position){
        return position.walkRight()
                .takeWhile(position1 -> getCandyFromPosition(position1).equals(getCandyFromPosition(position)))
                .collect(Collectors.toList());
    }

    private List<Position> longestMatchDown(Position position){
        return position.walkDown()
                .takeWhile(position1 -> getCandyFromPosition(position1).equals(getCandyFromPosition(position)))
                .collect(Collectors.toList());
    }

    private void clearMatch(List<Position> match){
        if(match == null || match.isEmpty()){
            return;
        }
        board.replaceCellAt(match.getFirst(), new EmptyCandy());
        score++;
        clearMatch(match.subList(1, match.size()));
    }

    private void fallDownTo(Position position){
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
//            System.out.println(e.getMessage());
        }
    }

    public void applySwap(PositionPair swap){
        var tmpCandy = getCandyFromPosition(swap.position1());
        board.replaceCellAt(swap.position1(), getCandyFromPosition(swap.position2()));
        board.replaceCellAt(swap.position2(), tmpCandy);
        updateBoard();
    }

    public boolean updateBoard(){
       return updateBoard(true);
    }

    private boolean updateBoard(boolean isFirstCall){
        var matches = findAllMatches();
        if(matches.isEmpty() && isFirstCall){
            return false;
        } else if (matches.isEmpty()){
            return true;
        } else {
            matches.forEach(this::clearMatch)   ;
            matches.forEach(list -> list.forEach(this::fallDownTo));
            return updateBoard(false);
        }
    }

    public Set<PositionPair> getAllValidSwaps(){
        var allSwaps = getAllSwaps();
        return validateSwaps(allSwaps);
    }

    private Set<PositionPair> validateSwaps(Set<PositionPair> allSwaps){
        Set<PositionPair> swaps = new HashSet<>();
        for(PositionPair pair : allSwaps){
            if(isValidSwap(pair)){
                swaps.add(pair);
            }
        }
        return swaps;
    }

    private boolean isValidSwap(PositionPair pair){
        var candyPos1 = getCandyFromPosition(pair.position1());
        var candyPos2 = getCandyFromPosition(pair.position2());

        board.swapCells(pair);
        var matches = findAllMatches();
        for (List list : matches){
            if (list.contains(pair.position1()) || list.contains(pair.position2())){
                board.replaceCellAt(pair.position1(), candyPos1);
                board.replaceCellAt(pair.position2(), candyPos2);
                return true;
            }
        }
        board.replaceCellAt(pair.position1(), candyPos1);
        board.replaceCellAt(pair.position2(), candyPos2);
        return false;
    }

    private Set<PositionPair> getAllSwaps() {
        Set<PositionPair> swaps = new HashSet<>();
        var boardSize = this.board.getBoardSize(); // Cache board size for faster access
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Pre-define directions

        for (int y = 0; y < boardSize.height(); y++) {
            for (int x = 0; x < boardSize.width(); x++) {
                Position position = new Position(x, y, boardSize);
                for (int[] direction : directions) {
                    int newX = x + direction[0];
                    int newY = y + direction[1];

                    if (!isValidPosition(newX, newY, boardSize)) {
                        continue; // Skip positions outside the board
                    }

                    Position neighbour = new Position(newX, newY, boardSize);
                    if (!(getCandyFromPosition(neighbour) instanceof EmptyCandy) && !(getCandyFromPosition(position) instanceof EmptyCandy)) {
                        swaps.add(new PositionPair(position, neighbour));
                    }
                }
            }
        }

        return swaps;
    }

    public SolvedBoard maximizeScore(){
        var test = new SolvedBoard();
        return test.solve(this);
    }

    public void replaceCellAt(Position position, Candy candy){
        board.replaceCellAt(position, candy);
    }

    private boolean isValidPosition(int x, int y, BoardSize boardSize) {
        return x >= 0 && x < boardSize.width() && y >= 0 && y < boardSize.height();
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        CandycrushModel model = (CandycrushModel) super.clone();
        model.board = (Board<Candy>) board.clone();
        model.random = new Random(0); // Reset the random seed for consistent behavior in the clone
        return model;
    }
}