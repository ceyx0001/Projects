import java.util.*;
import java.io.*;
public class Gymnist{
    static Scanner input = new Scanner(System.in);
    public static void main(String[]args)throws Exception{
        System.out.println("1. Manual input");
        System.out.println("2. Read from file");
        int readOption = input.nextInt();
        if (readOption == 1){
            System.out.println("\nEnter the number of competitors.");
            int personCount = input.nextInt();
            while (personCount < 0){
                System.out.println("Invalid input, enter more than 0 competitors.");
                personCount = input.nextInt();
            }
            System.out.println("\nEnter the number of judges.");
            int judgeCount = input.nextInt();
            while (judgeCount < 3){
                System.out.println("Invalid input, enter more than 2 judges.");
                judgeCount = input.nextInt();
            }
            double[] execution = new double[judgeCount];
            double[] difficulty = new double[judgeCount];
            int currentPerson = 0;
            String[] firstNames = new String[personCount];
            String[] lastNames = new String[personCount];
            String[] school = new String[personCount];
            double[] finalScores = new double[personCount];
            storeNames(personCount, firstNames, lastNames, school);
            String exit = "";
            int roundNo = 1;
            
            do{
                System.out.println("\nROUND " + roundNo);
                while (currentPerson < personCount){
                    finalScore(finalScores, execution, difficulty, currentPerson, judgeCount, firstNames, lastNames, school);
                    currentPerson++;
                    System.out.println("\nEnter any key to continue.");
                    String temp = input.next();
                }
            rank(finalScores, firstNames, lastNames, school);
            roundNo++;
            printResults(firstNames, lastNames, school, finalScores);
            System.out.println("\nStart next round? y/n");
            exit = input.next();
            } while (exit.equals("y"));
        }

        else if (readOption == 2){
            System.out.println("Enter the file name with the extension.");
            String fileName = input.next();
            File myFile = new File(fileName);
            Scanner reader = new Scanner(myFile);
            int personCount = 0;
            while (reader.hasNext()){
                reader.nextLine(); 
                personCount++;
            }
            reader.close();
            System.out.println("\nEnter the number of judges.");
            int judgeCount = input.nextInt();
            while (judgeCount < 3){
                System.out.println("Invalid input, enter more than 2 judges.");
                judgeCount = input.nextInt();
            }
            double[] execution = new double[judgeCount];
            double[] difficulty = new double[judgeCount];
            int currentPerson = 0;
            String[] firstNames = new String[personCount/2];
            String[] lastNames = new String[personCount/2];
            String[] school = new String[personCount/2];
            double[] finalScores = new double[personCount/2];

            File namesInFile = new File(fileName);
            Scanner nameReader = new Scanner(namesInFile);
            int counter = 0;
            while (nameReader.hasNextLine()){ 
                firstNames[counter] = nameReader.next().toUpperCase(); 
                String[] nameParts = nameReader.nextLine().split(" ");
                lastNames[counter] = nameParts[1].toUpperCase();
                school[counter] = nameReader.next().toUpperCase();
                counter++;
            }
            nameReader.close();
            String exit = "";
            int roundNo = 1;
            do{
                System.out.println("\nROUND " + roundNo);
                
                while (currentPerson < personCount/2){
                    finalScore(finalScores, execution, difficulty, currentPerson, judgeCount, firstNames, lastNames, school);
                    System.out.println("\nEnter any key to start the judging of the next competitor.");
                    String temp = input.next();
                    currentPerson++;
                }
            rank(finalScores, firstNames, lastNames, school);
            roundNo++;
            printResults(firstNames, lastNames, school, finalScores);
            System.out.println("\nStart next round? y/n");
            exit = input.next();
            } while (exit.equals("y"));
        }
    }

    public static void storeNames(int personCount, String[] firstNames, String[] lastNames, String[] school){
        for (int i=0; i<firstNames.length; i++){
            input.nextLine();
            System.out.println("\nEnter the name of competitor no. " + (i+1));
            String[] nameParts = input.nextLine().split(" ");
            firstNames[i] = nameParts[0].toUpperCase();
            lastNames[i] = nameParts[1].toUpperCase();
            System.out.println("\nEnter the school of " + firstNames[i] + " " + lastNames[i]);
            System.out.println("An appropriate 4 letter abbreviation must be entered.");
            school[i] = input.next().toUpperCase();
        }
    }

    public static void finalScore(double[] finalScores, double[] execution, double[] difficulty, int currentPerson, int judgeCount, String[] firstNames, String[] lastNames, String[] school){
        double sum = 0;
        for (int i=1; i<=judgeCount; i++){
            System.out.println("\nJudge " + i +", enter the execution score for " + firstNames[currentPerson] + " " + lastNames[currentPerson] + " : " + school[currentPerson]);
            execution[i-1] = input.nextDouble();
            scoreTryCatch(i, execution, difficulty);
            System.out.println("\nJudge " + i +", enter the difficulty score for " + firstNames[currentPerson] + " " + lastNames[currentPerson] + " : " + school[currentPerson]);
            difficulty[i-1] = input.nextDouble();
            scoreTryCatch(i, execution, difficulty);
            System.out.println("\n" + execution[i-1] + " execution.");
            System.out.println(difficulty[i-1] + " difficulty.");
        }
        for (int i=0; i<execution.length; i++){
            sum += execution[i] + difficulty[i];
        }
        System.out.println("\n" + firstNames[currentPerson] + " " + lastNames[currentPerson] + " : " + school[currentPerson] + " total score = " + sum);
        Arrays.sort(execution);
        Arrays.sort(difficulty);
        sum = 0;
        execution[0] = 0;
        difficulty[0] = 0;
        execution[judgeCount-1] = 0;
        difficulty[judgeCount-1] = 0;
        for (int i=0; i<execution.length; i++){
            sum += execution[i] + difficulty[i];
        }
        finalScores[currentPerson] = sum/(judgeCount-2);
        System.out.println("Final score = " + finalScores[currentPerson]);
    }

    public static void rank(double[] finalScores, String[] firstNames, String[] lastNames, String[] school){
        for (int i=1; i<finalScores.length; i++){
            for (int j=0; j<finalScores.length-i; j++){
                if (finalScores[j] < finalScores[j+1]){
                    double scoreSwap;
                    String firstNameSwap;
                    String lastNameSwap;
                    String schoolSwap;
                    scoreSwap = finalScores[j];
                    finalScores[j] = finalScores[j+1];
                    finalScores[j+1] = scoreSwap;
                    firstNameSwap = firstNames[j];
                    firstNames[j] = firstNames[j+1];
                    firstNames[j+1] = firstNameSwap;
                    lastNameSwap = lastNames[j];
                    lastNames[j] = lastNames[j+1];
                    lastNames[j+1] = lastNameSwap;
                    schoolSwap = school[j];
                    school[j] = school[j+1];
                    school[j+1] = schoolSwap;
                }
            }
        }
        for (int i=0; i<finalScores.length; i++) {
            System.out.println("\n"+ (i+1) + ". " + firstNames[i] + " " + lastNames[i] + " : " + school[i] + " --> " + finalScores[i]);
        }
    }

    public static void scoreTryCatch(int i, double[] execution, double[] difficulty){
        if (execution[i-1] < 0 || execution[i-1] > 10){
            while (execution[i-1] < 0 || execution[i-1] > 10){
                System.out.println("\nInvalid entry. Please re-enter.");
                execution[i-1] = input.nextDouble();
            } 
        }
        if (difficulty[i-1] < 0){
            while (difficulty[i-1] < 0){
                System.out.println("\nInvalid entry. Please re-enter.");
                difficulty[i-1] = input.nextDouble();
            } 
        }
    }

    public static void printResults(String[] firstNames, String[] lastNames, String[] school, double[] finalScores)throws Exception{
        File myFile = new File("Results.txt");
        PrintWriter output = new PrintWriter(myFile);
        for (int i = 0; i<finalScores.length; i++){
            output.println((i+1) + ". " + firstNames[i] + " " + lastNames[i]);
            output.println(school[i]);
            output.println(finalScores[i]);
        }
        output.close(); 
    }
}