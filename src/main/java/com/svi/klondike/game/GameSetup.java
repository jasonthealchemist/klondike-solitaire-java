package com.svi.klondike.game;

import java.util.Scanner;

public class GameSetup {
    private final Scanner scanner;

    public GameSetup(Scanner scanner){
        this.scanner = scanner;
    }

    public boolean pressEnterToPlay(){
        while(true){
            System.out.print("Press Enter to Start | Q to Quit: ");
            String input = scanner.nextLine().trim();

            if(input.isEmpty()){
                System.out.println("Game Starting....");
                return true;
            }

            if(input.equalsIgnoreCase("Q") ||  input.equalsIgnoreCase("QUIT")){
                if(confirmQuit()){
                    System.out.println("Exiting War Card Game.....");
                    return false;
                }
            }else {
                System.out.println("Invalid, Please press Enter.");
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

    public void start(){

    }

}
