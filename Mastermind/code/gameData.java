package Mastermind.code;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDateTime;
public class gameData { 
    public void exportData(int guessNo, int[] code, ArrayList<String> guessList, long guessTime,
            boolean humanIsBreaking) { //Method for exporting game data
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); //Saves the date
        LocalDateTime now = LocalDateTime.now(); //Saves the time
        try { //Create new file for this round
            PrintWriter export;
            if (humanIsBreaking) { //Determines whether or not the human is the code breaker and writes the correct data into the appropriate file
                export = new PrintWriter("lastGameStats/HumanStats.txt");
                export.println("Codebreaker : Human");
                export.println("Time of game : " + date.format(now)); // time of round
                export.println("Code to break : " + Arrays.toString(code)); // secret code
            } else {
                export = new PrintWriter("lastGameStats/AIStats.txt");
                export.println("Time of game : " + date.format(now)); // time of round
                export.println("Codebreaker : Computer");
            }
            export.println("Amount of guesses made : " + guessNo); // total guesses made
            export.println("Time to guess (ms) : " + guessTime); // write to file
            export.println(); //Spacing
            for (int i = 0; i < guessList.size(); i++) { // displaying each guess
                export.println("Guess " + (i + 1) + " : " + guessList.get(i));
                export.println();
            }
            export.close();
        } catch (FileNotFoundException e) {
            System.out.println("directory could not be found");
            e.printStackTrace();
        }
    }
}