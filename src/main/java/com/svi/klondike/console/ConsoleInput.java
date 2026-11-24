package com.svi.klondike.console;

import java.text.NumberFormat;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class ConsoleInput {
    private static final int FIRST_OPTION = 1;
    private static final String CHOICE_PROMPT = "> ";

    private final Scanner scanner;

    public ConsoleInput(Scanner scanner){
        this.scanner = scanner;
    }

    public String readLine(String prompt){
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt, int min, int max){
        while(true){
            String text = readLine(prompt);

            try{
                int value = Integer.parseInt(text);

                if (value >= min && value <= max){
                    return value;
                }

                System.out.println("Please enter a number between " + min + " and " + max + ".");
            }catch (NumberFormatException e){
                System.out.println("Please enter a whole number.");
            }
        }
    }

    public boolean readYesNo(String prompt){
        while (true){
            String answer = readLine(prompt);

            if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")){
                return true;
            }

            if (answer.equalsIgnoreCase("N") || answer.equalsIgnoreCase("NO")){
                return false;
            }

            System.out.println("Please enter Y or N.");
        }
    }

    public boolean readEnterOrQuit(String prompt){
        while (true){
            String answer = readLine(prompt);

            if (answer.isEmpty()){
                return true;
            }

            if(answer.equalsIgnoreCase("Q") || answer.equalsIgnoreCase("QUIT")){
                if(readYesNo("Are you sure you want to quit the game? (Y/N): ")){
                    return false;
                }
            }else{
                System.out.println("Invalid, please press Enter.");
            }
        }
    }

    public <T> T chooseOne(String title, List<T> options, Function<T, String> labelOf){
        System.out.println("\n" + title);

        for (int index = 0; index < options.size(); index++){
            System.out.println(" " + (index + FIRST_OPTION) + ". " + labelOf.apply(options.get(index)));
        }

        int choice = readInt(CHOICE_PROMPT, FIRST_OPTION, options.size());

        return options.get(choice - FIRST_OPTION);
    }
}
