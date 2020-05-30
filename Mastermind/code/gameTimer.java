package Mastermind.code;
public class gameTimer { // methods to calculate and store guess times
    public static long startTime;
    public static long endTime;
    public void startTimer() {
        startTime = System.currentTimeMillis(); // run at least once for accurate results
    }

    public long stopTimer(long guessTime) { //Stops timer for accuracy
        endTime = System.currentTimeMillis();
        long timeTook = endTime - startTime;
        guessTime += timeTook; //Accumulator
        startTime = 0;
        endTime = 0;
        return guessTime; //Returns accumulated time
    }
}