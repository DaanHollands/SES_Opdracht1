package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        ArrayList<Integer> speelVeld= new ArrayList<>();
        /*
        speelVeld.add(0);speelVeld.add(0);speelVeld.add(1);speelVeld.add(0);speelVeld.add(1);
        speelVeld.add(1);speelVeld.add(2);speelVeld.add(1);speelVeld.add(1);speelVeld.add(0);
        speelVeld.add(0);speelVeld.add(2);speelVeld.add(1);speelVeld.add(2);speelVeld.add(0);
        speelVeld.add(1);speelVeld.add(1);speelVeld.add(0);speelVeld.add(1);speelVeld.add(1);
        speelVeld.add(2);speelVeld.add(1);speelVeld.add(0);speelVeld.add(0);speelVeld.add(0);
        */
        speelVeld.add(0);speelVeld.add(0);speelVeld.add(1);speelVeld.add(0);
        speelVeld.add(1);speelVeld.add(1);speelVeld.add(0);speelVeld.add(2);
        speelVeld.add(2);speelVeld.add(0);speelVeld.add(1);speelVeld.add(3);
        speelVeld.add(0);speelVeld.add(1);speelVeld.add(1);speelVeld.add(1);

        for (int i = 0; i < speelVeld.size() ; i++) {
            System.out.println(i + " " + CheckNeighboursInGrid.getSameNeighboursIds(speelVeld, 4, 4, i));
        }


    }
}