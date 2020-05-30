import java.util.*;

public class MarkMenu {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    System.out.println("How many students will you be indexing?");
    int students = input.nextInt();

    String[] name = new String[students];
    int[] mark = new int[students];
    int sum = 0; // sum and count used to calculate average by adding all array indexes to sum,
                 // then dividing by number of times an index was added by using count
    int count = 0;
    int countB = 0;
    int high = 0;
    String nameHigh = ("");
    String reAdd;
    int temp = 0;
    String tempN;
    String restart;

    for (int i = 0; i < students; i++) {
      System.out.println("Enter individual number " + (i + 1) + "'s name.");
      String nameInput = input.next();
      if (!nameInput.equals("-1")) {
        name[i] = nameInput;
        countB++;
      }

      System.out.println("Enter " + name[i] + "'s mark.");
      int markInput = input.nextInt();
      if (markInput < -1 || markInput > 100) {
        System.out.println("Invalid input");
        markInput = input.nextInt();
      }

      if (markInput > -1) {
        mark[i] = markInput;
        countB++;
      }

      if (countB == students) {
        break;
      }
    }

    do {
      System.out.println("1. Display names and marks");
      System.out.println("2. Highest mark");
      System.out.println("3. Averages");
      System.out.println("4. Change a student's mark");
      System.out.println("5. Add a student and their mark");
      System.out.println("6. Delete a student and their mark");
      System.out.println("7. Display names in alphabetical ascending order");
      System.out.println("8. Display marks in ascending order");
      System.out.println("9. Exit");

      int menuInput = input.nextInt();

      if (menuInput == 4) { // mark change
        System.out.println("What is the student's name? Type -1 to exit");
        String nameChange = input.next();
        for (int i = 0; i < students; i++) {
          if (nameChange.equals(name[i])) {
            System.out.println("What is " + name[i] + "'s mark?'");
            int markInputB = input.nextInt();
            if (markInputB < -1 || markInputB > 100) {
              System.out.println("Invalid input");
              markInputB = input.nextInt();
            } else if (markInputB < 101 && markInputB > -1) {
              mark[i] = markInputB;
            }
          }
          if (nameChange.equals("-1")) {
            break;
          }
        }
      }

      if (menuInput == 2) { // finding highest mark by comparing mark of an index to variable high. This
                            // will work as high is set to the first index, then just keeps replacing with
                            // higher values if it is greater
        nameHigh = name[0]; // finding the person with highest mark is the same, as index 0 of mark would
                            // refer to index 0 of name as an example
        high = mark[0];
        for (int i = 0; i < students; i++) {
          if (mark[i] > high) {
            high = mark[i];
            nameHigh = name[i];
          }
        }
        System.out.println(nameHigh + " has the highest mark of " + high);
      }

      if (menuInput == 3) {
        for (int i = 0; i < students; i++) { // using a for loop to sum all indexes by setting sum as index[i], then
                                             // index[i] adds onto the previous index[i]
          if (mark[i] != 0) {
            sum = sum + mark[i];
            count++; // counter variable to see how many indexes were added
          }
        }
        if (count != 0) {
          System.out.println("The average is " + (sum / count));
        } else if (count == 0) {
          System.out.println("The average is " + sum);
        }
      }

      if (menuInput == 5) {
        do {
          System.out.println("Type -1 to exit at any time");
          System.out.println("Enter a student's name");
          String nameAdd = input.next();
          System.out.println("Enter their mark");
          int markInputC = input.nextInt();
          if (markInputC < 0 || markInputC > 100) {
            System.out.println("Invalid input");
            markInputC = input.nextInt();
          } else if (markInputC > -1 && markInputC < 101) {
            for (int i = 0; i < students; i++) {
              if (name[i] == null) { // to see if index is empty, for object types it is equal to null, for primitive
                                     // types it is equal to 0
                mark[i] = markInputC; // if the index is empty, add corresponding user inputs
                name[i] = nameAdd;
                break;
              }
            }
          }
          System.out.println("Enter another mark? y/n");
          reAdd = input.next();
          continue;
        } while (reAdd.equals("y"));
      }

      if (menuInput == 1) {
        for (int i = 0; i < students; i++) {
          if (mark[i] != 0) {
            System.out.println(name[i] + " has a mark of " + mark[i]);
          } else {
            break;
          }
        }
      }

      if (menuInput == 6) {
        System.out.println("What is the student's name? (Their mark will also be deleted!)");
        String nameDel = input.next();
        for (int i = 0; i < students; i++) {
          if (name[i].contains(nameDel)) {
            name[i] = "";
            mark[i] = 0;
            break;
          }
        }
      }

      if (menuInput == 7) {
        for (int i = 1; i < name.length; i++) {
          for (int j = 0; j < name.length - 1; j++) {
            if (name[j].compareTo(name[j + 1]) >= 0) { // swap > to < to descending order
              tempN = name[j];
              name[j] = name[j + 1];
              name[j + 1] = tempN;
            }
          }
        }
        System.out.println("The names in ascending order are");
        for (int i = 0; i < name.length; i++) {
          if (name[i] != null) {
            System.out.println(name[i]);
          }
        }
      }

      if (menuInput == 8) {
        for (int i = 1; i < mark.length; i++) {
          for (int j = 0; j < mark.length - 1; j++) {
            if (mark[j] > mark[j + 1]) {
              temp = mark[j];
              mark[j] = mark[j + 1];
              mark[j + 1] = temp;
            }
          }
        }
        System.out.println("The marks in ascending order are");
        for (int i = 0; i < mark.length; i++) {
          if (mark[i] != 0) {
            System.out.println(mark[i]);
          }
        }
      }

      if (menuInput == 9) {
        break;
      }

      input.nextLine();

      System.out.println("Go back to menu? y/n");
      restart = input.next();
    } while (restart.equals("y"));
  }
}
