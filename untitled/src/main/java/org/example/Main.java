package org.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> speelVeld= new ArrayList<>();
        speelVeld.add(0);speelVeld.add(0);speelVeld.add(1);speelVeld.add(0);speelVeld.add(1);
        speelVeld.add(1);speelVeld.add(2);speelVeld.add(1);speelVeld.add(1);speelVeld.add(0);
        speelVeld.add(0);speelVeld.add(2);speelVeld.add(1);speelVeld.add(2);speelVeld.add(0);
        speelVeld.add(1);speelVeld.add(1);speelVeld.add(0);speelVeld.add(1);speelVeld.add(1);
        speelVeld.add(2);speelVeld.add(1);speelVeld.add(0);speelVeld.add(0);speelVeld.add(0);

        for (int i = 0; i < 25 ; i++) {
            System.out.println(i + " " + CheckNeighboursInGrid.getSameNeighboursIds(speelVeld, 5, 5, i));
        }


    }
}