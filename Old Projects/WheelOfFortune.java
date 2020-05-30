import java.util.*;
public class WheelOfFortune{
    static int computerGuess = (int) (Math.random()*26)+0;
    static int phraseNo = (int)(Math.random() * 24) + 0; //a random number generator to determine an index representing a word in phraseList
    static int roundNo = 1;
    static int tempMoney = 0;
    static int comTempMoney = 0;
    static int totalMoney = 0;
    static int comTotalMoney = 0;
    static int outcome = 0;
    static int nRNG, sRNG, wRNG = 0;
    static int instances = 0;
    static char charGss = '\u0000';
    static char[] computerAlphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1'};
    static char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    static char[] guessedLttrs = new char[100]; //a character array to track what letter was guessed each round
    static String[] phraseList = {"show me the money","may the force be with you","e.t phone home","bond. james bond","i'm walking here","i'll have what she's having","you're gonna need a bigger boat","i'll be back","i see dead people","houston, we have a problem","say hello to my little friend","here's johnny","wax on, wax off","i'm the king of the world","this is sparta","why so serious","there's no place like home","run forrest run","where is my super suit","oh hi mark","you should've gone for the head","to infinity and beyond","i will find you and i will kill you","my precious","just keep swimming"};
    static String name;
    static String proceed;
    static String playerName;
    static String hiddenPhrase = phraseList[phraseNo].replaceAll("[a-z]", "-");
    static Scanner input = new Scanner(System.in);
    static boolean appeared = true;
    static boolean guessedAlready = false;
    static boolean wordSolved = false;

    //main method
    public static void main(String[]args){
        String replay;
        System.out.println(" _     _  __   __  _______  _______  ___       _______  _______   _______  _______  ______  _______  __   __  __    _  _______ ");
        System.out.println("| | _ | ||  | |  ||       ||       ||   |     |       ||       | |       ||       ||    _ ||       ||  | |  ||  |  | ||       |");
        System.out.println("| || || ||  |_|  ||    ___||    ___||   |     |   _   ||    ___| |    ___||   _   ||   | |||_     _||  | |  ||   |_| ||    ___|");
        System.out.println("|       ||       ||   |___ |   |___ |   |     |  | |  ||   |___  |   |___ |  | |  ||   |_||_ |   |  |  |_|  ||       ||   |__");
        System.out.println("|       ||       ||    ___||    ___||   |___  |  |_|  ||    ___| |    ___||  |_|  ||    __  ||   |  |       ||  _    ||    ___|");
        System.out.println("|   _   ||   _   ||   |___ |   |___ |       | |       ||   |     |   |    |       ||   |  | ||   |  |       || | |   ||   |___ ");
        System.out.println("|__| |__||__| |__||_______||_______||_______| |_______||___|     |___|    |_______||___|  |_||___|  |_______||_|  |__||_______|\n");
        System.out.println("What is your name?");
        playerName = input.next();
        System.out.println("\nHere are the rules:");
        System.out.println("1. Vowels can only be bought with money earned in that round.");
        System.out.println("2. If you guess a letter that's already been guessed, your turn ends.");
        System.out.println("3. If there are no appearances of the letter you guessed, your turn ends.");
        System.out.println("4. There are 3 rounds in total, one turn a player.");
        System.out.println("5. You may only guess one letter when prompted to enter a letter. Or else the first letter inputted will be your answer.");
        System.out.println("6. You can enter '1' when prompted to guess a letter to try to solve the phrase.");
        System.out.println("Free turn means you can guess incorrectly once, letter or phrase. However bankrupt still resets your money and skip turn will skip your turn entirely");
        System.out.println("Since you have no chance of winning, i'll let you go first.\n");
        System.out.println("Have you read the rules? (Spin wheel)");
        System.out.println("y/n");
        inputChecker();
        System.out.println("");
        do{
            //there are 3 rounds in total, runs each method 3 times
            resetVariables();
            playRound();
            resetVariables();
            playRound();
            resetVariables();
            playRound();
            System.out.println("The game is over."); //prompting user to restart the game
            System.out.println("You won $" + totalMoney + "!");
            System.out.println("Restart the game?");
            System.out.println("Enter 'y' to restart, enter anything else to exit program");
            replay = input.next();
            replay.toLowerCase();
            totalMoney = 0; //resetting game variables to anew
            comTotalMoney = 0;
            roundNo = 1;
            System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv\n");
        } while (replay.equals("y")); //condition for replaying the game after finishing first time, if user types "n" then the program exits
    }

    //method for resetting rounds
    public static void resetVariables(){
        wordSolved = false; //when this condition is set to false, a new word is generated and the next round begins
        phraseNo = (int)(Math.random() * 24) + 0; //generating a new phrase
        hiddenPhrase = phraseList[phraseNo].replaceAll("[a-z]", "-"); //resetting hiddenPhrase to symbols
        Arrays.fill(guessedLttrs, '\u0000'); //no guessed letters. empty array list
        tempMoney = 0; //resetting round money
        comTempMoney = 0;
    }

    //method that determines pick order
    public static void playRound(){
        if (roundNo != 2){ //during round 1 and 3, player goes first
            while (wordSolved == false){
                System.out.println("ROUND " + roundNo);
                playerTurn();
                if (wordSolved == true){ //exits the while loop and continues to the next round if the word had been solved
                    break;
                }
                computerTurn();
                if (wordSolved == true){
                    break;
                }
            }
        }
        else if (roundNo == 2){ //during round 2, computer goes first
            while (wordSolved == false){
                System.out.println("ROUND " + roundNo);
                computerTurn();
                if (wordSolved == true){
                    break;
                }
                playerTurn();
                if (wordSolved == true){
                    break;
                }
            }
        }
        roundNo++; //increases round counter at the end of each round
    }

    //method to scan for correct input
    public static void inputChecker(){
        String in = input.next();
        in.toLowerCase();
        while (!in.equals("y") && !in.equals("n")){ //if input is not "y" or "n" then it will repeat the prompt
            System.out.println("y/n");
            in = input.next();
            System.out.println("");
        }
        if (in.equals("n")){ //if "n" is the input, then exit program
            System.exit(0);
        }
    }

    //method stating the outcome of spinning the wheel
    public static void outputSpinOutcome(){
        System.out.println(name + "'s turn.");
        if (outcome == 0){
            System.out.println("Bankrupt! " + name + " has lost all their winnings!\n"); //when bankrupt, round money is 0, lose winnings.
            if (name == playerName){ //this line of code determines who is currently playing their turn
                tempMoney = 0;
            } else{
                comTempMoney = 0;
            }
        } else if (outcome == 450){ //special outcome
            int prizeNo = (int) (Math.random()*9)+0; //rolls 1 of 10 special prizes
            String[] prizes = {"a Tesla Model X!", "a Koenigsegg Agera!", "a trip on the Seven Seas Explorer!", "a Cessna Citation II!", "an all inclusive resort at Baha Mar in the Bahamas!", "a luxury mansion in San Fransisco!", "half a million dollars in cash!", "a flawless taaffeite ring!", "a Lamborghini Aventador SV", "one hundred thousand dollars worth of Google stocks!"};
            System.out.println("You have won a special prize...Claim at the end of the show!\n");
            System.out.println(name + " got " + prizes[prizeNo]);
            System.out.println("Your turn multiplier is $450.");
        } else if (outcome == 2){ //2 is free turn
            System.out.println(name + " got a free turn!");
        } else if (outcome == 1){ //1 is skip turn
            System.out.println(name + "'s turn was skipped!");
        } else{ //if none of the above were the outcome, output normal statement
            System.out.println(name + " got $" + outcome + ".\n");
        }
    }

    //method stating round info
    public static void outputInfo(){
        System.out.println(hiddenPhrase);
        System.out.println("Guessed letters: ");
        System.out.print(guessedLttrs);
        System.out.println("");
        if (name == playerName){
            System.out.println(name + " gained $" + tempMoney + " this round.");
            System.out.println(name + "'s total money: $" + totalMoney);
        }
        else{
            System.out.println(name + " gained $" + comTempMoney + " this round.");
            System.out.println(name + "'s total money: $" + comTotalMoney);
        }
        System.out.println("Enter your letter guess.");
    }

    //method for letter and phrase processing
    public static void letterCheck(){
        //checking for vowels
        for (int i = 0; i < vowels.length; i++){ //scans every index of vowels array to see if letter guess matches any index
            if (name == playerName){
                while (charGss == vowels[i] && tempMoney < 250) { //if player does not have enough money and guesses a vowel, prompt to guess consonant
                    System.out.println(name + " does not have enough money to guess a vowel. Guess a consonant.");
                    charGss = input.next().charAt(0);
                    System.out.println("");
                }
                if (charGss == vowels[i] && tempMoney >= 250){
                    if (tempMoney >= 250){
                    tempMoney -= 250; //cost of vowel is 250 from round money
                    System.out.println(name + " bought a vowel for $250.\n");
                    }
                }
            }
            else { //computer hard coded to not buy a vowel when it does not have sufficient funding, therefore simply output message when it buys vowels
                if (charGss == vowels[i]){
                    comTempMoney -= 250; //cost of vowel is 250 from round money
                    System.out.println(name + " bought a vowel for $250.\n");
                }
            }
        }

        int j = 0; //counter variable
        //checking for guessed letters, and storing the guessed letter
        for (int i = 0; i < guessedLttrs.length; i++){ //scans every index of guessedLttrs
            if (guessedLttrs[i] == '\u0000'){ //if the index is empty
                guessedLttrs[i] = charGss; //store the charGss in that index
                j=i; //setting the index value to the variable j
                break;
            }
        }
        for (int i = 0; i < j; i++){ //makes sure that the loop is not checking for the letter that had just been stored by comparing int i to j, scans everything before index of j
            if (guessedLttrs[i] == charGss){
                guessedAlready = true; //if a letter guess matches an index, then letter was previously guessed, flag to true
            }
        }

        //revealing word with letter guessed
        appeared = false; //has not appeared yet
        for (int i = 0; i < phraseList[phraseNo].length(); i++){ //scans every letter of chosen phrase, i represents letter sequence/the place value of the letter
            if (phraseList[phraseNo].charAt(i) == charGss){ //checks if character index at loop i = letter guess, if it matches that means the letter guessed appeared at letter no. i in chosen phrase
                instances++; //counter for amount of occurrences
                appeared = true; //appeared
                char[] letterArray = hiddenPhrase.toCharArray(); //temporary letter array to convert hidden phrase
                letterArray[i] = charGss; //changes the character at index i of letter array to letter guess
                hiddenPhrase = String.valueOf(letterArray); //converts letter array back into hidden phrase with letter switched
            }
        }
    }

    //method stating guess outcome
    public static void roundOutcome(){
        if (charGss == '1'){ //player wants to solve word by typing "1"
            solveWord(phraseNo, tempMoney, phraseList); //call solve word method
        }
        else if (guessedAlready == true){
            System.out.println("You already guessed that letter! Your turn has ended.\n");
        }
        else if (instances != 0 && charGss != 'a' && charGss != 'e' && charGss != 'i' && charGss != 'o' && charGss != 'u'){
            System.out.println("The letter appeared " + instances + " times.");
            if (name == playerName){
                tempMoney += instances * outcome;
            }
            else{
                comTempMoney += instances * outcome;
            }
            System.out.println(name + " gained $" + (instances*outcome) + " for that.\n");
            appeared = true;
            System.out.println("Enter any key to continue.");
            proceed = input.next();
            System.out.println("");
        } else if (charGss == 'a' || charGss == 'e' || charGss == 'i' || charGss == 'o' || charGss == 'u' && instances != 0){ //if they guessed a vowel, no money is added
            System.out.println("The letter appeared " + instances + " times.\n");
            appeared = true;
            System.out.println("Enter any key to continue.");
            proceed = input.next();
            System.out.println("");
        } else{
            System.out.println("The letter appeared 0 times. Your turn has ended.\n");
            appeared = false;
        }
        instances = 0; //resets variables
        charGss = '\u0000'; 
    }

    //method calculating total money and if the word is solved
    public static void solveWord(int phraseNo, int tempMoney, String[] phraseList){
        input.nextLine();
        System.out.println("Type out the final phrase including spaces and punctuation.");
        String phraseGuess = input.nextLine();
        phraseGuess.toLowerCase();
        if (phraseGuess.equals(phraseList[phraseNo]) && tempMoney >= 1000){ //if phraseGuess matches chosen phrase, the word is solved
            System.out.println(name + " has successfully solved the word. +$" + tempMoney + " to your bank.\n");
            wordSolved = true; //condition to start next round flagged to true to start next round
            totalMoney += tempMoney; //add round money to total money
        } else if (phraseGuess.equals(phraseList[phraseNo]) && tempMoney < 1000){
            wordSolved = true;
            totalMoney += 1000;
            System.out.println(name + " has successfully solved the word. +$1000 to your bank. \n"); //pity rule, when player solves with less than 1000 round money, add 1000 to total
        } else if (!phraseGuess.equals(phraseList[phraseNo])){
            System.out.println("Wrong!\n");
            wordSolved = false;
        }
    }

    //method simulating wheel spin
    public static void spinWheel(){
        int[] moneyValues = {500, 550, 600, 650, 700, 800, 900, 2500, 3500, 5000, 450, 0};
        int nRNG =(int) (Math.random()*24)+1; //24 spaces on the board, 1-24 options
        int sRNG =(int) (Math.random()*3)+1; //3 options when landing on a special space
        int wRNG =(int) (Math.random()*9)+1; //9 values of a wild number
        if (nRNG == 23){ //rolls a 23, wild option, multiply wRNG by 100 to get dollar value
            outcome = wRNG * 100;
        } else if (nRNG == 24){
            outcome = sRNG != 3 ? moneyValues[11] : moneyValues[10]; //rolls a 24, special option, roll sRNG to see if player bankrupts or receives million dollars
            if (outcome == moneyValues[11]){
                outcome = moneyValues[11];
            }
        } else if (nRNG == 1 || nRNG == 2 || nRNG == 3 || nRNG == 4){ //4 $500 spaces
            outcome = moneyValues[0];
        } else if (nRNG == 5){ //1 $550 space
            outcome = moneyValues[1];
        } else if (nRNG == 6 || nRNG == 7 || nRNG == 8 || nRNG == 9){ //4 $600 spaces
            outcome = moneyValues[2];
        } else if (nRNG == 10 || nRNG == 11 || nRNG == 12){ //3 $650 spaces
            outcome = moneyValues[3];
        } else if (nRNG == 13 || nRNG == 14 || nRNG == 15){ //3 $700 spaces
            outcome = moneyValues[4];
        } else if (nRNG == 16){ //1 $800 space
            outcome = moneyValues[5];
        } else if (nRNG == 17){ //1 $900 space
            outcome = moneyValues[6];
        } else if (nRNG == 18){ //top dollar space, receive based on round No
            if (roundNo == 1){
                outcome = moneyValues[7];
            } else if (roundNo == 2){
                outcome = moneyValues[8];
            } else if (roundNo == 3){
                outcome = moneyValues[9];
            }
        } else if (nRNG == 19 || nRNG == 20){ //2 bankrupt spaces
            outcome = moneyValues[11];
        } else if (nRNG == 21){ //skip turn space
            outcome = 1;
        } else if (nRNG == 22){ //free turn space
            outcome = 2;
        }
    }

    public static void playerTurn(){
        name = playerName; //set name to playerName so methods can identify it is the player's turn
        appeared = true;
        guessedAlready = false;
        while (appeared == true && guessedAlready == false){ //loops guess if no repeat guesses were made or a letter was guessed correctly
            spinWheel(); //spins wheel
            outputSpinOutcome(); //tells player outcome
            //System.out.println(phraseList[phraseNo]); //used to reveal word while debugging
            if (outcome == 0){ //bankrupt turn
                break;
            }
            else if (outcome != 2 && outcome != 1){ //outcome != free round or skip turn
                outputInfo();
                String playerChar = input.next(); //scans and stores letter guess
                playerChar.toLowerCase();
                charGss = playerChar.charAt(0);
                letterCheck();
                roundOutcome();
            }
            else if (outcome == 2){
                playerTurn();
                if (appeared == false || guessedAlready == true){ //grace guess, if they guess falsely, repeat their turn
                    playerTurn();
                }
            }
            else if (outcome == 1){ //skips turn
                break;
            }
        }
        System.out.println("Enter any key to continue.");
        proceed = input.next();
        System.out.println("============================================================================================\n");
    }

    public static void computerTurn(){
        int computerGuessPhrase = (int) (Math.random()*24)+0;
        name = "SUPER AI";
        appeared = true;
        guessedAlready = false;
        if (computerAlphabet[computerGuess] == '1'){ //1 chance that the computer tries solving 
            System.out.println("Is the phrase " + phraseList[computerGuessPhrase]); //telling user that the computer is solving
            if (phraseList[computerGuessPhrase].equals(phraseList[phraseNo])){ //solve word rules apply the same
                if (comTempMoney >1000){
                    System.out.println(name + " has successfully solved the word. +$" + comTempMoney + " to your bank.\n");
                    comTotalMoney += comTempMoney;
                }
                if (comTempMoney < 1000){
                    System.out.println(name + " has successfully solved the word. +$1000 to your bank. \n");
                    comTotalMoney += 1000;
                }
            }
        }
        while (appeared == true && guessedAlready == false){ //computer plays the same as the user
            computerGuess = (int) (Math.random()*26)+0;
            charGss = computerAlphabet[computerGuess];
            spinWheel();
            outputSpinOutcome();
            //System.out.println(phraseList[phraseNo]); //used to reveal word while debugging
            if (outcome == 0){
                break;
            }
            else if (outcome != 2 && outcome != 1){ 
                outputInfo();
                while (computerAlphabet[computerGuess] == 'a' || computerAlphabet[computerGuess] == 'e'|| computerAlphabet[computerGuess] == 'i'|| computerAlphabet[computerGuess] == 'o'|| computerAlphabet[computerGuess] == 'u' && comTempMoney < 250){
                    computerGuess = (int) (Math.random()*25)+0;
                    charGss = computerAlphabet[computerGuess];
                }
                System.out.println(charGss);
                letterCheck();
                roundOutcome();
            }
            else if (outcome == 2){
                computerTurn();
                if (appeared == false || guessedAlready == true){
                    computerTurn();
                }
            }
            else if (outcome == 1){
                break;
            }
        }
        System.out.println("Enter any key to continue.");
        proceed = input.next();
        System.out.println("********************************************************************************************\n");
    }
}