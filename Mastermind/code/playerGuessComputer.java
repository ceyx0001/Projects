package Mastermind.code;
import java.util.*;
public class playerGuessComputer {
    static final int colourNum = 6;
    static final int nodeNum = 4;
    public int[] computerSetCode() { //Computer sets a random code
        Random ran = new Random();
        int code[] = new int[nodeNum];
        for (int i = 0; i < nodeNum; i++) {
            code[i] = ran.nextInt(6) + 1;
        }
        return code;
    }
}