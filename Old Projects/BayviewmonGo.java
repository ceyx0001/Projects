
import java.util.*;
public class BayviewmonGo
{
    public static void main(String[]args) 
    {
        //Declaring replay string to restart the game (loop) if user enters that they want to replay
        String replay = ("yes");
        
        //Using a do while loop to loop the game if user enters yes, but also executes game at least once before checking
        do
        {
            //Declaration of new scanner called "input"    
            Scanner input = new Scanner(System.in);


            //A machine to generate balls (total of 15 balls)
            //Ball generation counter variables
            int bokeball = 0;
            int breatball = 0;
            int bultraball = 0;

            //Specific ball type generation machine, a total of 15 balls the player can use, stops generating when ballCounter hits 15
            for (int ballCounter =  0; ballCounter < 15; ballCounter++)
            {
                int ballTypeRng = (int)(Math.random()*3)+1;

                if (ballTypeRng == 1)
                {
                    bokeball = bokeball + 1;
                }
                
                else if (ballTypeRng == 2)
                {
                    breatball = breatball + 1;
                }

                else if (ballTypeRng == 3)
                {
                    bultraball = bultraball + 1;
                }
            }
        

            //Sum of score counter variable
            int totalScore = 0;




                
            //Intro gag/meme
            System.out.println("You enter the Bayviewmon Laboratory");
            System.out.println("You see a bag with your name on it containing " + bokeball + " Bokeballs, " + breatball + " Breatballs, " + bultraball + " Bultraballs");    
            System.out.println("Hello there! What is your name?");
            String name = input.nextLine();
            
            System.out.println("\n");
            System.out.println("Nice to meet you " + name + "! Welcome to the world of Bayviewmon! My name is Bowan! People call me the Bayviewmon Prof!");
            System.out.println("This world is inhabited by creatures called Bayviewmon! For some people, Bayviewmon are pets. Others use them for fights. Myself...I study Bayviewmon as a profession.");
            System.out.println("That is also where you come in! I need you to catch Bayviewmon for me in the wilderness. Will you help?");
            System.out.println("(yes/no)");
            String gameStart = input.nextLine();

            //Telling the player to enter the correct keyword to start the game
            while (!gameStart.equals("yes") && !gameStart.equals("no"))
            {
                System.out.println("I'm sorry, I didn't quite catch that.");
                System.out.println("(yes/no)");
                gameStart = input.nextLine();
                if (gameStart.equals("yes") || gameStart.equals("no"))
                {
                    continue;
                }
            }

            System.out.println("\n");
            //Output text to tell the player that the game has started if they entered the correct keyword
            if (gameStart.equals("yes")){
                System.out.println("Terrific! You can get started right away.");
                System.out.println("*You walk out of the lab and start your journey...");
            }

            if (gameStart.equals("no")){
                System.out.println("Hmm, that's too bad then. Come again if you decide to change your mind.");
                System.out.println("*You walk out of the lab and start your journey...eager to catch some Bokemon anyway.");
            }





            //Game begins
            /*
            Point value of monsters to be added
            Baykachu = 2 points
            Bulbabay = 2 points
            Charmanview = 3 points
            Squirtew = 4 points
            Eevay = 5 points
            */

            //A loop that will repeat until the player has exhausted all 15 balls
            for (int ballCounterTotal = 0; ballCounterTotal <= 15; ballCounterTotal++)
            {
                //Assigning one specific value to a Bayviewmon to randomly determine what Bayviewmon the player will encounter
                int monRng = (int)(Math.random()*5)+1;
                /*
                Which value a Bayviewmon is associated with
                Baykachu = 1
                Bulbabay= 2
                Charmanview = 3
                Squirtew = 4
                Eevay = 5
                */

                System.out.println("\n");
                //Telling the player what Bayviewmon they have encountered based on RNG, and displaying their worth
                if (monRng==1)
                {
                    System.out.println("You have encountered a Baykachu! It is worth 2 points.");
                }
                
                else if (monRng==2)
                {
                    System.out.println("You have encountered a Bulbabay! It is worth 2 points.");
                }

                else if (monRng==3)
                {
                    System.out.println("You have encountered a Charmanview! It is worth 3 points.");
                }

                else if (monRng==4)
                {
                    System.out.println("You have encountered a Squirtew! It is worth 4 points.");
                }

                else if (monRng==5)
                {
                    System.out.println("You have encountered an Eevay! It is worth 5 points.");
                }





                //Bokeball decision making mechanic
                //Deckaring ball type variables
                String bokeballChoice;
                String breatballChoice;
                String bultraballChoice;

                System.out.println("What type of ball wil you use? (Enter bokeball, breatball, or bultraball)");
                System.out.println(bokeball + " bokeballs, " + breatball + " breatballs, " + bultraball + " bultraballs");
                String ballChoice = input.nextLine();
                System.out.println("\n");
                
                /*
                Telling player to enter the correct input options
                Full words of balls are used because it prevents the user from accidentally using the wrong ball since the names are so distinct
                In my opinion, it is much better than having the user enter a ball type, this is less repetitive then having a confirmation prompt, and more safe than just typing a keyword such as "Enter 1, 2, or 3"
                */
                while(!ballChoice.equals("bokeball") && !ballChoice.equals("breatball") && !ballChoice.equals("bultraball"))
                {
                    System.out.println("Invalid input.");
                    System.out.println("Enter bokeball, or breatball, or bultraball");
                    ballChoice=input.nextLine();
                    System.out.println("\n");

                    //Condition to exit loop once they have inputted correctly
                    if(ballChoice.equals("bokeball") || ballChoice.equals("breatball") || ballChoice.equals("bultraball"))
                    {
                    break;
                    }
                }

                //Telling player to choose another ball type if they do not have any remaining of chosen ball type left
                while ((ballChoice.equals("bokeball") && bokeball == 0) || (ballChoice.equals("breatball") && breatball == 0) || (ballChoice.equals("bultraball") && bultraball == 0))
                {
                    System.out.println("You do not have enough of that ball type.");
                    System.out.println("Please choose another type of ball.");
                    ballChoice=input.nextLine();
                    System.out.println("\n");

                    while(!ballChoice.equals("bokeball") && !ballChoice.equals("breatball") && !ballChoice.equals("bultraball"))
                    {
                        System.out.println("Invalid input.");
                        System.out.println("Enter bokeball, or breatball, or bultraball");
                        ballChoice=input.nextLine();

                        if(ballChoice.equals("bokeball") || ballChoice.equals("breatball") || ballChoice.equals("bultraball"))
                        {
                        continue;
                        }
                    }

                    //Condition to exit loop if they have chosen a ball type that they have a positive amount of
                    if ((ballChoice.equals("bokeball") && bokeball != 0) || (ballChoice.equals("breatball") && breatball != 0) || (ballChoice.equals("bultraball") && bultraball != 0))
                    {
                        break;
                    }
                }

            



                /*
                Success rate percentages
                Bokeball = +10%
                Breatball = +20%
                Bultraball = +30%
                Base catch rate = 50%
                Base failure rate = 25%
                Base break rate = 25%
                Total rate range = 100%

                Therefore, each ball addds 10% of sucess rate to the base rate, decreasing the other 2 rates by a subsequent amount of n/2
                n is equal to the additional success rate
                n is divided by 2, as the sucess rate is being evenly split between 2 other rates and subtracting from those possibilities

                With simple math, then that means:
                Bokeball~ if catch rate is 60%, then 1-60 is success, therefore 60-80 is flee, 80-100 is break
                Breatball~ if catch rate is 70%, then 1-70 is success, therefore 70-85 is flee, 85-100 is break
                Bultraball~ if catch rate is 80%, then 1-80 is success, therefore 80-90 is flee, 90-100 is break
                */

                //Bokeball has been chosen scenario
                if (ballChoice.equals("bokeball"))
                {
                    int ballRngBokeball = (int)(Math.random()*100)+1;

                    //Subtract one ball since they have chosen it
                    bokeball--; 
                   
                    //Ball break scenario
                    while (ballRngBokeball >= 61 && ballRngBokeball <= 80)
                    {
                        while (bokeball !=0 )
                        {
                            System.out.println("The bokeball broke!");
                            System.out.println("What type of ball wil you use? (Enter bokeball, breatball, or bultraball)");
                            System.out.println(bokeball + " bokeballs, " + breatball + " breatballs, " + bultraball + " bultraballs");
                            ballChoice = input.nextLine();
                            System.out.println("\n");

                            //Subtract one ball since it broke, just like the real game
                            bokeball--;

                            //If ball breaks again, repeat the same loop
                            ballRngBokeball = (int)(Math.random()*100)+1;
                            if (ballRngBokeball >= 61 && ballRngBokeball <= 80)
                            {
                                continue;
                            }

                            while(!ballChoice.equals("bokeball") && !ballChoice.equals("breatball") && !ballChoice.equals("bultraball"))
                            {
                                System.out.println("Invalid input.");
                                System.out.println("Enter bokeball, or breatball, or bultraball");
                                ballChoice=input.nextLine();

                                if(ballChoice.equals("bokeball") || ballChoice.equals("breatball") || ballChoice.equals("bultraball"))
                                {
                                break;
                                }
                            }

                            break;
                        }
                    }
                    
                    
                    //Caught scenario
                    if (ballRngBokeball >= 1 && ballRngBokeball <= 60)
                    {
                        //Seeing what type of Bayviewmon was encountered and adding the appropriate amount of points
                        if (monRng==1)
                        {
                            //E.g. Monster rng chose 1 as a value --> Baykachu --> worth two points --> add 2 points
                            System.out.println("You have caught a Baykachu!");
                            System.out.println("+2 to your total score.");
                            totalScore = totalScore + 2;
                            System.out.println("You currently have a score of " + totalScore + ".");
                        }
                        
                        else if (monRng==2)
                        {
                            System.out.println("You have caught a Bulbabay!");
                            System.out.println("+2 to your total score.");
                            totalScore = totalScore + 2;
                            System.out.println("You currently have a score of " + totalScore + ".");
                        }

                        else if (monRng==3)
                        {
                            System.out.println("You have caught a Charmanview!");
                            System.out.println("+3 to your total score.");
                            totalScore = totalScore + 3;
                            System.out.println("You currently have a score of " + totalScore + ".");
                        }

                        else if (monRng==4)
                        {
                            System.out.println("You have caught a Squirtew!");
                            System.out.println("+4 to your total score.");
                            totalScore = totalScore + 4;
                            System.out.println("You currently have a score of " + totalScore + ".");
                        }

                        else if (monRng==5)
                        {
                            System.out.println("You have caught an Eevay!");
                            System.out.println("+5 to your total score.");
                            totalScore = totalScore + 5;
                            System.out.println("You currently have a score of " + totalScore + ".");
                        }

                    }
                    
    
                    //Flee scenario
                    else if (ballRngBokeball >= 81 && ballRngBokeball <=100)
                    {
                        System.out.println("The bokeball broke and the Bayviewmon ran away...");
                    }

                }





                //Breatball has been chosen scenario 
                if (ballChoice.equals("breatball"))
                {
                    int ballRngBreatball = (int)(Math.random()*100)+1;

                    breatball--;

                    //Ball break scenario
                    while (ballRngBreatball >= 71 && ballRngBreatball <= 85)
                    {
                        while (breatball != 0)
                        {   
                            System.out.println("The breatball broke!");
                            System.out.println("What type of ball wil you use? (Enter bokeball, breatball, or bultraball)");
                            System.out.println(bokeball + " bokeballs, " + breatball + " breatballs, " + bultraball + " bultraballs");
                            ballChoice = input.nextLine();
                            System.out.println("\n");

                            //Subtract one ball since it broke
                            breatball--;

                            //If ball breaks again, repeat the same loop
                            ballRngBreatball = (int)(Math.random()*100)+1;
                            if (ballRngBreatball >= 71 && ballRngBreatball <= 85)
                            {
                                continue;
                            }

                            while(!ballChoice.equals("bokeball") && !ballChoice.equals("breatball") && !ballChoice.equals("bultraball"))
                            {
                                System.out.println("Invalid input.");
                                System.out.println("Enter bokeball, or breatball, or bultraball");
                                ballChoice=input.nextLine();

                                if(ballChoice.equals("bokeball") || ballChoice.equals("breatball") || ballChoice.equals("bultraball"))
                                {
                                break;
                                }
                            }

                            break;
                        }
                    }


                    //Caught scenario
                    if (ballRngBreatball >= 1 && ballRngBreatball <= 70)
                    {

                        if (monRng==1)
                        {
                            System.out.println("You have caught a Baykachu!");
                            System.out.println("+2 to your total score.");
                            totalScore = totalScore + 2;
                            System.out.println("You currently have a score of " + totalScore + ".");
                        }
                        
                        else if (monRng==2)
                        {
                            System.out.println("You have caught a Bulbabay!");
                            System.out.println("+2 to your total score.");
                            totalScore = totalScore + 2;
                            System.out.println("You currently have a score of " + totalScore + ".");
                        }

                        else if (monRng==3)
                        {
                            System.out.println("You have caught a Charmanview!");
                            System.out.println("+3 to your total score.");
                            totalScore = totalScore + 3;
                            System.out.println("You currently have a score of " + totalScore + ".");
                        }

                        else if (monRng==4)
                        {
                            System.out.println("You have caught a Squirtew!");
                            System.out.println("+4 to your total score.");
                            totalScore = totalScore + 4;
                            System.out.println("You currently have a score of " + totalScore + ".");
                        }

                        else if (monRng==5)
                        {
                            System.out.println("You have caught an Eevay!");
                            System.out.println("+5 to your total score.");
                            totalScore = totalScore + 5;
                            System.out.println("You currently have a score of " + totalScore + ".");
                        }

                    }
                    

                    

                    //Flee scenario
                    else if (ballRngBreatball >= 86 && ballRngBreatball <=100)
                    {
                        System.out.println("The bokeball broke and the Bayviewmon ran away...");
                    }

                }





                //Bultraball chosen scenario
                if (ballChoice.equals("bultraball"))
                {
                    int ballRngBultraball = (int)(Math.random()*100)+1;

                    bultraball--;

                    //Ball break scenario
                    while (ballRngBultraball >= 81 && ballRngBultraball <= 90)
                    {
                        ballRngBultraball = (int)(Math.random()*100)+1;

                        while (bultraball != 0)
                        {
                            System.out.println("The bultraball broke!");
                            System.out.println("What type of ball wil you use? (Enter bokeball, breatball, or bultraball)");
                            System.out.println(bokeball + " bokeballs, " + breatball + " breatballs, " + bultraball + " bultraballs");
                            ballChoice = input.nextLine();
                            System.out.println("\n");

                            //Subtract one ball since it broke
                            bultraball--;

                            //If ball breaks again, repeat the same loop
                            ballRngBultraball = (int)(Math.random()*100)+1;
                            if (ballRngBultraball >= 81 && ballRngBultraball <= 90)
                            {
                                continue;
                            }

                            while(!ballChoice.equals("bokeball") && !ballChoice.equals("breatball") && !ballChoice.equals("bultraball"))
                            {
                                System.out.println("Invalid input.");
                                System.out.println("Enter bokeball, or breatball, or bultraball");
                                ballChoice=input.nextLine();

                                if(ballChoice.equals("bokeball") || ballChoice.equals("breatball") || ballChoice.equals("bultraball"))
                                {
                                    break;
                                }
                            }

                            break;
                        }
                    }
            

                    //Caught scenario
                    if (ballRngBultraball >= 1 && ballRngBultraball <= 80)
                    {

                        if (monRng==1)
                        {
                            System.out.println("You have caught a Baykachu!");
                            System.out.println("+2 to your total score.");
                            totalScore = totalScore + 2;
                            System.out.println("You currently have a score of " + totalScore + ".");
                        }
                        
                        else if (monRng==2)
                        {
                            System.out.println("You have caught a Bulbabay!");
                            System.out.println("+2 to your total score.");
                            totalScore = totalScore + 2;
                            System.out.println("You currently have a score of " + totalScore + ".");
                        }

                        else if (monRng==3)
                        {
                            System.out.println("You have caught a Charmanview!");
                            System.out.println("+3 to your total score.");
                            totalScore = totalScore + 3;
                            System.out.println("You currently have a score of " + totalScore + ".");
                        }

                        else if (monRng==4)
                        {
                            System.out.println("You have caught a Squirtew!");
                            System.out.println("+4 to your total score.");
                            totalScore = totalScore + 4;
                            System.out.println("You currently have a score of " + totalScore + ".");
                        }

                        else if (monRng==5)
                        {
                            System.out.println("You have caught an Eevay!");
                            System.out.println("+5 to your total score.");
                            totalScore = totalScore + 5;
                            System.out.println("You currently have a score of " + totalScore + ".");
                        }

                    }
                    

                    //Flee scenario
                    else if (ballRngBultraball >= 91 && ballRngBultraball <=100)
                    {
                        System.out.println("The bokeball broke and the Bayviewmon ran away...");
                    }
                    
                }


                //Stopping the game once all balls have been exhausted
                if (bokeball == 0 && breatball == 0 && bultraball == 0)
                {
                    break;
                }

            }

            //Output a message to tell player that they have run out of balls to use
            System.out.println("\n");
            System.out.println("You have run out of bokeballs!");
            //Outputting a message telling them their total score
            System.out.println("Your total score this run is " + totalScore);

            //Printing the appropriate message based on their score
            if (totalScore >= 30)
            {
                System.out.println("You are an amazing Bayviewmon catcher!");
            }

            if (totalScore < 15)
            {
                System.out.println("You could use a bit more practice.");
            }

            if (totalScore < 30 && totalScore > 15)
            {
                System.out.println("You're not a bad Bayviewmon catcher.");
            }


            //Asking the user if they would like to play again
            System.out.println("Would you like to play again?");
            System.out.println("(yes/no)");
            replay = input.nextLine();

            while(!replay.equals("yes") && !replay.equals("no"))
           {
                System.out.println("I'm sorry, I didn't quite understand.");
                System.out.println("(yes/no)");
                replay = input.nextLine();

                //If the answer is yes then replay, if no then exit the game
                if (replay.equals("yes") || replay.equals("no"))
                {
                    break;
                }
            }

        } while (replay.equals("yes"));
    }
}