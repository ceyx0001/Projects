package Mastermind.code;
import java.util.*;
public class computerGuessPlayer {
    gameplayMethods gameplay = new gameplayMethods(); //Gameplay methods class variable 
    int tempGuess = 0;
    int loopNum = 0;
    int NODENUM = 0;
    int COLOURNUM = 0;
    int last = 0; //Computer's previous guess
    int allPossible[] = new int[1296]; //ALl possible code combinations (static)
    int hint[] = new int[NODENUM]; //Hint
    int temp[] = new int[NODENUM];
    int lastGuess[] = new int[4];
    ArrayList<Integer> set = new ArrayList<Integer>();
    String hintCombos[] = { "00", "01", "02", "03", "04", "10", "11", "12", "13", "20", "21", "22", "30", "31", "40" }; //All different possible hint combinations
    public ArrayList<Integer> start(int[] getHint, int NODENUMBER, int COLOURNUMBER, ArrayList<Integer> newSet) { //returns computer guess and current set
        //Setting constants
        NODENUM = NODENUMBER;
        COLOURNUM = COLOURNUMBER;
        hint = getHint; //Storing hint
        int tempGuess = 0;
        setList(COLOURNUM); //Fills the array allPossible with all possible code combinations
        set = newSet; //Updates the set
        last = set.get(set.size() - 1); //Gets computer's previous guess
        set.remove(set.size() - 1);
        elimSet(); //Eliminate combinations from the set that do not match
        if (set.size() == 1) { //Generates guess
            tempGuess = set.get(0);
        } else {
            tempGuess = miniMax();
        }
        set.add(tempGuess); //Adds guess to the end of the array list that is to be returned
        return set;
    }
    public void setList(int COLOURNUM) { //Fills allPossible array
        int temp = 0;
        int temp1 = 0;
        int temp2 = 0;
        int counter = 0;
        for (int i = 1; i < COLOURNUM + 1; i++) {
            temp = i;
            for (int j = 1; j < COLOURNUM + 1; j++) {
                temp2 = temp;
                temp = temp * 10 + j;
                for (int k = 1; k < COLOURNUM + 1; k++) {
                    temp1 = temp;
                    temp = temp * 10 + k;
                    for (int l = 1; l < COLOURNUM + 1; l++) {
                        allPossible[counter] = temp * 10 + l;
                        counter++;
                    }
                    temp = temp1;
                }
                temp = temp2;
            }
        }
    }
    public void elimSet() { //Eliminates combinations that would not have yielded the same hint as the previous guess
        int key = 0;
        int[] tempHint = new int[NODENUM];
        if (set.contains(last)) { //Eliminates the previous guess
            set.remove(set.indexOf(last));
        }
        for (int i = NODENUM - 1; i >= 0; i--) { //Last guess int to array
            lastGuess[i] = last % 10;
            last /= 10;
        }
        for (int j = 0; j < set.size(); j++) {
            //The rationale of this step of the algorithm is to compare the previous guess to each code in the set
            //The computer finds the hint that is generated if the selected code was the code
            //If the hint does not match the one that was given after the previous guess, it is to be eliminated
            key = set.get(j);
            temp = intToArray(key);
            tempHint = gameplay.getHint(lastGuess, temp);
            if (!(hint[0] == tempHint[0] && hint[1] == tempHint[1] && hint[2] == tempHint[2]
                    && hint[3] == tempHint[3])) {
                set.remove(j);
                j--;
            }
        }
    }
    public int miniMax() { //Finds the best next guess based on the information it gives and NOT how close it is to the code
        //This part of the algorithm finds the number of codes that would be eliminated from the set if the selected code were to be the next guess
        //Here you can see that the next guess will not always be in the set
        //The computer does this by finding the running through all different codes (not just the ones in the set)
        //The computer takes the selected code from allPossible and compares it to each code in the set
        //If the code would hypothetically be eliminated, a counter is increased
        //The maximum number of codes that would be eliminated for the given hypothetical code in the set is recorded for each element in allPossible
        //The computer then finds the minimum value of all of those maximums and uses it as the next guess
        int tempHint[] = new int[NODENUM];
        int bwCode[] = new int[NODENUM];
        int nextGuess = 0;
        int counter;
        int max = -2147483648;
        int min = 2147483647;
        for (int i = 0; i < allPossible.length; i++) {
            max = 0;
            for (int j = 0; j < hintCombos.length; j++) {
                counter = 0;
                bwCode = getBW(hintCombos[j]);
                for (int k = 0; k < set.size(); k++) {
                    tempHint = gameplay.getHint(intToArray(allPossible[i]), intToArray(set.get(k)));
                    if (tempHint[0] == bwCode[0] && tempHint[1] == bwCode[1] && tempHint[2] == bwCode[2]
                            && tempHint[3] == bwCode[3]) {
                        counter++;
                    }
                }
                if (counter > max) {
                    max = counter;
                }
            }
            if (max < min) {
                min = max;
                nextGuess = allPossible[i];
            }
        }
        return nextGuess;
    }
    public int[] intToArray(int num) { //Converts an integer into an array
        int array[] = new int[NODENUM];
        for (int i = NODENUM - 1; i >= 0; i--) {
            array[i] = num % 10;
            num /= 10;
        }
        return array;
    }
    public int[] getBW(String key) { //Converts the black and white string combinations into an array
        int blackNum = key.charAt(0) - '0';
        int whiteNum = key.charAt(1) - '0';
        int bw[] = new int[NODENUM];
        for (int i = 0; i < blackNum; i++) {
            bw[i] = 2;
        }
        for (int i = blackNum; i < whiteNum + blackNum; i++) {
            bw[i] = 1;
        }
        for (int i = blackNum + whiteNum; i < NODENUM; i++) {
            bw[i] = 0;
        }
        return bw;
    }
}