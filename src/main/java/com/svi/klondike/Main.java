package com.svi.klondike;

import com.svi.klondike.enums.Ranks;
import com.svi.klondike.enums.Suits;
import com.svi.klondike.game.Board;
import com.svi.klondike.game.GameSetup;
import com.svi.klondike.models.Card;
import com.svi.klondike.models.Deck;
import com.svi.klondike.piles.*;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

       static void main(String[] args) {
              Scanner scanner = new Scanner(System.in);

              displayTitle();

              GameSetup gameSetup = new GameSetup(scanner);
              gameSetup.start();

              scanner.close();
       }

       private static void displayTitle(){
              System.out.println("========== KLONDIKE SOLITAIRE ==========");
              System.out.println();
       }
}
