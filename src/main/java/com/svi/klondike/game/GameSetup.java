package com.svi.klondike.game;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class GameSetup {
    private final Scanner scanner;

    public GameSetup(Scanner scanner){
        this.scanner = scanner;
    }

    public void start(){
        boolean gameStarted = false;

        while (!gameStarted){
            System.out.print("Press Enter to Start | Q to Quit: ");

            try {
                String input = scanner.nextLine().trim();

                if(input.isEmpty()){
                    System.out.println("\nStarting Klondike Solitaire...");
                    gameStarted = true;
                }
                else if(input.equalsIgnoreCase("Q") || input.equalsIgnoreCase("QUIT")){
                    if (confirmQuit()) {
                        System.out.println("Exiting Klondike Solitaire...");
                        return;
                    }
                }else{
                    System.out.println("Invalid input. Please press Enter or Q to quit.");
                }
            }catch (NoSuchElementException e){
                System.out.println("\nInput closed. Exiting Klondike Solitaire Game.....");
                return;
            }
        }
    }

    public boolean confirmQuit(){
        while(true){
            System.out.print("Are you sure you want to quit? (Y/N): ");
            String answer = scanner.nextLine().trim();

            if(answer.equalsIgnoreCase("Y") ||  answer.equalsIgnoreCase("YES")){
                return true;
            }

            if(answer.equalsIgnoreCase("N") ||  answer.equalsIgnoreCase("NO")){
                return false;
            }

            System.out.println("Invalid, Please enter Y or N.");
        }
  }
}
