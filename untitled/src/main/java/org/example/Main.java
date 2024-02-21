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

        System.out.println(CheckNeighboursInGrid.getSameNeighboursIds(speelVeld, 5, 5, 14));

    }
}