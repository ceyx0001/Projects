package Hangman;
import java.util.*;
import java.io.*;
public class Hangman{

    //Global variables
    static Scanner input = new Scanner(System.in);
    static String chosenWord = "";
    static Map<String, List<String>> familyMap;
    static List<String> wordFamily;
    static boolean valid = false; //input flagger
    static int[] chosenIndex = {0}; //variable for chosen word index
    static char[] guessedLttrs = new char[100]; //char array to store guessed letters
    static String[] availableWords;
    static int tempInc = 0;
    static String tempCopy = "";

    public static void main(String[]args) throws Exception{
        String replay;

        //Reading and storing words from dictionary
        BufferedReader fileReader = new BufferedReader (new FileReader(new File("dictionary.txt"))); 
        List<String> tempWords = new ArrayList<String>(); //string list used to store the words found on each line
        for (String x = fileReader.readLine(); x != null; x = fileReader.readLine()){ //while the scanner detects another line, increase line counter by 1
            tempWords.add(fileReader.readLine()); //adding the word scanned to string list
        }
        fileReader.close();
        
        String[] words = tempWords.toArray(new String[tempWords.size()]); //declaring string array and converting string list into a string array using .toArray
        do {
            int[] gameType = {0}; //menu variable
            int[] wordLength = {0}; //word length variable
            int[] guessNo = {0}; //# of guesses variable
            reset();
            
            //Fetching longest and shortest word
            String longestWord = getLongestString(words);
            String shortestWord = getShortestString(words);
            System.out.println("\nThe longest word is " + longestWord.length() + " letters long.\nThe shortest is " + shortestWord.length() + " letters long.\n");//outputting longest word length to user

            //Menu and start of game
            System.out.println("1. Evil Hangman\n2. Normal Hangman");
            valid = false;
            do {
                System.out.println("\nPlease enter the options 1 or 2.");
                checkOption(gameType);
            } while (valid == false || gameType[0] != 1 && gameType[0] != 2); //reloop if invalid input entered 

            //Prompting word length
            valid = false;
            do {
                System.out.println("\nEnter a valid word length to guess.");
                checkOption(wordLength);
            } while (valid == false || wordLength[0] < shortestWord.length() || wordLength[0] > longestWord.length()); //reloop if word length does not exist / is not a number

            //Prompting amount of guesses
            valid = false;
            do {
                System.out.println("\nEnter the amount of permitted guesses (More than 0 of course!).");
                checkOption(guessNo);
            } while (valid == false || guessNo[0] < 0); //reloop if input is not a number / < 0
            
            //Start of game
            if (gameType[0] == 1){
            evilHangman(wordLength, guessNo, words); //execute evilhangman difficulty
            } else{
            normalHangman(wordLength, guessNo, words); //execute normal difficulty
            }    

            System.out.println("Replay?\ny/n");
            replay = input.next();
        } while (replay != "y");
    } 

    //method to reset variables to a new game state
    public static void reset(){
        chosenWord = "";
        try{
            familyMap.clear();
            wordFamily.clear();
            valid = false;
            chosenIndex[0] = 0;
            Arrays.fill(guessedLttrs, '\u0000');
            Arrays.fill(availableWords, "");
            tempInc = 0;
            tempCopy = "";
        } catch (Exception e){
        }
    }

    //getLongestString, returns longestString
    public static String getLongestString(String[] array){
        String longestString = array[0]; //settings longestString to one of the objects in string array
        for (String str : array){ //enhanced for loop to scan entire array, loops for each string in string array
            if (str.length() > longestString.length()){ //finding if .length() is larger than longestString.length()
                longestString = str; //set string found to new longestString
            }
        }
        return longestString;
    }

    //getShortestString, returns shortestString
    public static String getShortestString(String[] array){ //same as longest string method except the logic is reversed to get shortest word
        String shortestString = array[0];
        for (String str : array){ 
            if (str.length() < shortestString.length()){ 
                shortestString = str; 
            }
        }
        return shortestString;
    }

    //checks for correct input
    public static void checkOption(int[] option){
        if (input.hasNextInt()){ //checks if input is int
            option[0] = input.nextInt();
            valid = true; //flag valid to true if it is
        } else {
            System.out.print("\nThat is not an option.\n"); //tell user to enter int if not 
            input.next();
        }
    }

    public static void getIndex(String[] words, int[] wordLength){
        //Scanning and storing available words from user input information and dictionary list
        List<String> tempAvailableWords = new ArrayList<String>();
        for(String str : words){
            if(str.length() == wordLength[0]){
                tempAvailableWords.add(str);
            }
        }
        availableWords = tempAvailableWords.toArray(new String[tempAvailableWords.size()]); //copying string list to string array
        
        //Prompting word from non-guesser 
        System.out.println("\nHere are your available words:");
        int indexCount = 1; //temp counter variable to display index no.
        for (String str : availableWords){ //prints out every element in availableWords array
            System.out.println(indexCount + ". " + str);
            indexCount++;
        }
        
        //Prompting word to guess
        valid = false;
        do {
            System.out.println("\nPlease enter the index number of the word you want the opponent to guess.");
            checkOption(chosenIndex);
        } while (valid == false); //loops if a non-number is inputted

        boolean appeared = false; //temp boolean to check if index input is valid
        do {
            try {  
                chosenWord = availableWords[chosenIndex[0]-1];
                appeared = true;
            } catch (Exception e){ //catching invalid indexes
                System.out.println("\nThat is not an available index. Please enter another index.");
                chosenIndex[0] = input.nextInt();
                appeared = false;
            }
        } while (appeared == false); //loops if there is no valid index
    }

    public static void evilHangman(int[] wordLength, int[] guessNo, String[] words) throws Exception{
        boolean guessedAlready = false; //boolean to flag if user guessed a letter already
        char charGuess = '\u0000'; //variable containing letter guess

        getIndex(words, wordLength);

        while(guessNo[0] != 0){

            //Prompting for letter guess and swapping symbols
            int x = 0; //temp variable used to track last stored guess
            do{
                guessedAlready = false;
                System.out.println("\nEnter your letter guess.");
                charGuess = input.next().charAt(0);
                for (int i = 0; i < guessedLttrs.length; i++){ 
                    if (guessedLttrs[i] == '\u0000'){ //if index is empty add letter guess to index
                        guessedLttrs[i] = charGuess; 
                        x=i; //set tracker to index value
                        break;
                    }
                }
                for (int i = 0; i < x; i++){ //scans for every index before the most recently added one
                    if (guessedLttrs[i] == charGuess){
                        guessedAlready = true; //flag to true if it detects a matching letter guess
                    }
                }
                if(guessedAlready == true){
                    System.out.println("\nYou have already guessed the letter " + charGuess);
                }
            } while(guessedAlready == true); //loops while a character was already guessed
    
            System.out.print("Guessed letters : ");
            System.out.println(guessedLttrs);

            //Shrinking the list
            getWordFamily(charGuess, availableWords, wordLength); //fetching word families
            Object[] orderedMapArray = familyMap.values().toArray(); //creating object array based on word family patterns (creating from values of map)
            String[] cutList = new String[orderedMapArray.length]; //creating string array based on object array length
            for (int i = 0 ; i < orderedMapArray.length ; i ++){
                try {
                    cutList[i] = orderedMapArray[i].toString(); //copying elements of object array to string array
                } catch (NullPointerException ex){
                }
             }  

            for (int i = 0; i < cutList.length; i++){ //sorting shortened list with bubble sort
                for (int j = 0; j < cutList.length; j++){
                    if (cutList[i].length() > cutList[j].length()){
                        String temp = cutList[i];
                        cutList[i] = cutList[j];
                        cutList[j] = temp;
                    }
                }
            }
            
            String[] finalList = cutList[0].split(" "); //creating final string array based on individual words from that word family
            for (int i = 0; i < finalList.length; i++){
                finalList[i] = finalList[i].replace("[", "").replace("]", "").replace(",", ""); //remove excess characters
            }
            availableWords = finalList; //new array of available words becomes this final list

            if (tempInc == 0) { //line of code that runs only once, just to get a copy of the first available word
                tempCopy = availableWords[0].replaceAll("[a-z]", "-");
                tempInc++;
            }

            for (int i = 0; i < wordLength[0]; i++){
                if(availableWords[0].charAt(i) == charGuess){
                    char[] letterArray = tempCopy.toCharArray(); //temporary letter array to convert hidden phrase
                    letterArray[i] = charGuess; //changes the character at index i of letter array to letter guess
                    tempCopy = String.valueOf(letterArray); //converts letter array back into copied hidden phrase with letter switched
                }
            }

            guessNo[0]--;
            System.out.println("\nYou have " + guessNo[0] + " guesses left.");
            System.out.println(tempCopy);

            if (!tempCopy.contains("-") || guessNo[0] == 0){
                System.out.println("\nThe game has ended!\nThe word was " + availableWords[0]);
                break;
            }
        }

    }

    public static void normalHangman(int[] wordLength, int[] guessNo, String[] words){

    }

    //Method for generating wordPattern and returning hiddenWord containing pattern
    public static String wordPattern(char charGuess, String chosenWord, int wordLength[]){
        String hiddenWord = chosenWord.replaceAll("[a-z]", "-");
        for (int i = 0; i < wordLength[0]; i++){
            if (chosenWord.charAt(i) == charGuess){
                hiddenWord = hiddenWord.substring(0, i) + charGuess + hiddenWord.substring(i+1); //replacing hidden word with the character at that substring
            }
        }
        return hiddenWord; //return pattern
    }

    //Method for storing lists of strings within the same word family into a list and sorting them by key values
    public static void getWordFamily(char guess, String[] availableWords, int[] wordLength){
        familyMap = new TreeMap<String, List<String>>(); //Creating new map, string is the key, string list is the value (pattern is the key, wordFamily is the value)
    
        for (String word : availableWords){ //for every string in string array of available words
            String pattern = wordPattern(guess, word, wordLength); //getting word pattern

            if (familyMap.containsKey(pattern)){ //fetch the list of word patterns from map (if map contains key)
                wordFamily = familyMap.get(pattern); //returns a non null value (sets the retun value as string list wordFamily) if it detects the pattern is pre-existing based on wordFamily (key)
            } else {
                wordFamily = new ArrayList<String>(); //if familyMap does not have the pattern, convert wordFamily to string array list with the word family and add this pattern
            }
    
            if (!wordFamily.contains(word)){ //adding new word to wordFamily list if pattern was not there
                wordFamily.add(word);
            }
    
            familyMap.put(pattern, wordFamily); //mapping the pattern and adding wordFamily to the pattern (mapping key and value)
        }
    }
}