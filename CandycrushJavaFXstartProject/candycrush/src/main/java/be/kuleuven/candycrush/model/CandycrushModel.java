package be.kuleuven.candycrush.model;

import be.kuleuven.CheckNeighboursInGrid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class CandycrushModel {
    private String speler;
    private ArrayList<Integer> speelbord;
    private int width;
    private int height;

    private int score;



    public CandycrushModel(String speler) {
        this.speler = speler;
        speelbord = new ArrayList<>();
        width = 10;
        height = 10;

        fillGrid();
    }

    public void changeName(String name){
        this.speler = name;
    }

    private void fillGrid(){
        speelbord.clear();
        for (int i = 0; i < width*height; i++){
            Random random = new Random();
            int randomGetal = random.nextInt(5) + 1;
            speelbord.add(randomGetal);
        }
    }
    public String getSpeler() {
        return speler;
    }

    public ArrayList<Integer> getSpeelbord() {
        return speelbord;
    }

    public int getScore(){return score;}

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpeelbordLength(){
        return speelbord.size();
    }

    public void candyWithIndexSelected(int index){
        //TODO: update method so it also changes direct neighbours of same type and updates score
        if (index != -1){
            Random random = new Random();
            int randomGetal = random.nextInt(5) + 1;
            speelbord.set(index,randomGetal);
            System.out.println("Random generate");
        }else{
            System.out.println("model:candyWithIndexSelected:indexWasMinusOne");
        }
    }

    public void increaseScore(){
        score++;
    }

    public void resetSpeelbord(){
        fillGrid();
    }

    public void setSpeelbord(ArrayList<Integer> speelbord){
        this.speelbord.clear();
        this.speelbord.addAll(speelbord);
    }

    public void setScorebord(int score){
        this.score = score;
    }

    public void changeNeighbours(int index){
        ArrayList<Integer> Neigbours = (ArrayList<Integer>) CheckNeighboursInGrid.getSameNeighboursIds(speelbord, width, height, index);
        if(Neigbours.size() >= 3) {
            candyWithIndexSelected(index);
            increaseScore();
            for (Integer i : Neigbours) {
                candyWithIndexSelected(i);
                increaseScore();
            }
        }
    }

    public int getIndexFromRowColumn(int row, int column) {
        return column+row*width;
    }
}
