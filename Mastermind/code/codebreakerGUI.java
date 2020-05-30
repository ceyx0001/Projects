package Mastermind.code;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class codebreakerGUI extends JFrame implements ActionListener {
  private static final long serialVersionUID = 1L;
  // Creating class variables
  gameplayMethods gameplay = new gameplayMethods();
  playerGuessComputer playerGuess = new playerGuessComputer();
  computerGuessPlayer computerGuess = new computerGuessPlayer();
  gameData gD = new gameData();
  gameTimer timer = new gameTimer();
  // Initializing variables
  final int GUESSNUM = 10;
  final int NODENUM = 4;
  final int COLOURNUM = 6;
  final int RED = 1;
  final int ORANGE = 2;
  final int YELLOW = 3;
  final int GREEN = 4;
  final int BLUE = 5;
  final int PURPLE = 6;
  final int BLACK = 2;
  final int WHITE = 1;
  final int NONE = 0;
  int computerGuessCounter = 0;
  int colorPicked = -1;
  int slotPicked = 0;
  int keyColorPicked = -1;
  int keySlotPicked = 0;
  int guessNo = 1;
  boolean humanIsBreaking = false; // Boolean that determines whether or not the human is the code breaker
  boolean codeSet; // Determines whether the code has been set or not
  long guessTime = 0;
  double computerGuessTime = 0.0;
  int[] compGuess = { 1, 1, 2, 2 }; // The computer's guess
  boolean isSecondTurn = false; // Determines whether the game is in its second phase
  boolean playerGuessed = false; // Determines whether or not the player has guessed correctly
  String computerGuessMessage = "Computer: I guess the code: " + Arrays.toString(compGuess);
  ArrayList<Integer> set = new ArrayList<Integer>(); // The set of possible codes used by the computer when it guesses
  ArrayList<String> guessList = new ArrayList<String>(); // List of guesses for exporting game data
  // Creating globals for the menu panel
  JLabel introMessage = new JLabel("Welcome to Codebreaker! Please select a game mode!");
  JPanel menuPanel = new JPanel();
  JPanel introPanel = new JPanel();
  JButton howToPlay = new JButton("How To Play");
  JButton computerBreakHuman = new JButton("Start as code maker");
  JButton humanBreakComputer = new JButton("Start as code breaker");
  JButton quit = new JButton("Quit");
  JButton playAgain = new JButton("Play Again");
  // game board variables
  FlowLayout fL = new FlowLayout();
  JPanel right = new JPanel();
  JPanel left = new JPanel();
  JPanel setSecretCode = new JPanel();
  JPanel userButtons = new JPanel();
  JPanel codeToBreak = new JPanel();
  JPanel computerGuessInfo = new JPanel();
  JPanel computerGuessPegs = new JPanel();
  JPanel setPegPanel = new JPanel();
  JPanel titleScreen = new JPanel();
  JPanel slotPanel = new JPanel();
  JPanel feedback;
  JPanel lastGuess;
  JButton confirm = new JButton("Confirm");
  JButton setCode = new JButton("Set Code");
  JLabel instruction = new JLabel();
  JLabel guesses = new JLabel("Guesses Count : " + Integer.toString(guessNo));
  JButton nextButton = new JButton("Next");
  int guess[] = { -1, -1, -1, -1 }; // The player's guess
  int secretCode[] = { -1, -1, -1, -1 }; // The code
  int hint[] = { -1, -1, -1, -1 }; // The hint that is to be returned
  int playerHint[] = { 0, 0, 0, 0 }; // The hint that the player creates (is later compared to hint[])
  Color purpleColor = new Color(139, 0, 255);
  JLabel computerOutput = new JLabel(computerGuessMessage);
  
  public codebreakerGUI() { // Constructor
    FlowLayout flowLayout = new FlowLayout(); // Layout for the intro, nodes, and menu panels
    GridLayout menuPageLayout = new GridLayout(2, 1); // Layout for the menu page
    setDefaultCloseOperation(EXIT_ON_CLOSE); // exit on close
    // Adding background
    try {
      BufferedImage img = ImageIO.read(new File("Images/titleScreen.jpg"));
      setContentPane(new JLabel(new ImageIcon(img)));
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("File not found.");
    }
    // Setting frame layout
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    // Formatting the menu panel
    menuPanel.setLayout(flowLayout);
    howToPlay.addActionListener(this);
    computerBreakHuman.addActionListener(this);
    humanBreakComputer.addActionListener(this);
    quit.addActionListener(this);
    menuPanel.add(howToPlay);
    menuPanel.add(computerBreakHuman);
    menuPanel.add(humanBreakComputer);
    menuPanel.add(quit);
    menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    // Formatting the intro panel
    introPanel.setLayout(flowLayout);
    introPanel.add(introMessage);
    introPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    // Setting the layout of the window
    titleScreen.setLayout(menuPageLayout);
    // Adding all the panels to the window
    titleScreen.add(introPanel);
    titleScreen.add(menuPanel);
    add(titleScreen);
    // Setting up the window
    setTitle("Codebreaker");
    setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
    setVisible(true);
  }
  
  // button presses
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    if (command.equals("How To Play")) {
      gameInfo();
    } else if (command.equals("Quit")) {
      dispose();
    } else if (command.equals("Start as code maker") || command.equals("Become code maker")) { // Initializes the phase where the player is the code maker
      instruction.setText(
                          "Please click on these pegs then the holes on the bottom to create a code. Press confirm to confirm your code.");
      humanIsBreaking = false;
      newGame(humanIsBreaking); // Setting up the board
      playerFeedback(); // Method for the player giving the computer feedback
      setList(); // Initalizes the set of possible codes (1296 to start)
    } else if (command.equals("Start as code breaker") || command.equals("Become code breaker")) { // Initializes the phase where the player is the code breaker
      instruction.setText("Please click on these pegs then the holes on the right to create a guess. Press confirm to confirm your code.");
      instruction.updateUI();
      timer.startTimer();
      codeSet = false;
      secretCode = playerGuess.computerSetCode(); // Computer sets the code randomly
      humanIsBreaking = true;
      newGame(humanIsBreaking); // Setting up the board
    } else if (command.equals("Confirm")) { // Player guesses
      boolean isFull = true;
      for (int i = 0; i < NODENUM; i++) { // Checks to see if the nodes have been filled
        if (guess[i] == -1) {
          isFull = false;
          break;
        }
      }
      if (!isFull) {
        guesses.setText("Error! Missed colour slot!");
      } else {
        guessList.add(Arrays.toString(guess)); // Adds the guess to the guess list
        setCursor(Cursor.getDefaultCursor()); // Sets cursor back to default cursor
        newPlyrGuess(); // Player making a guess
        right.add(slotPanel); // Adds the confirm button to the panel
        if (playerGuessed) { // Checks to see if the player guessed correctly
          guesses.setText("You Win!");
        } else {
          guessNo++;
          if (guessNo == 11) { // Checks to see if the player has exceeded the number of guesses
            guesses.setText("You Lose!");
          } else { // Updates the guess number
            guesses.setText("Guess Number: " + Integer.toString(guessNo));
          }
        }
        if (guessNo == 11 || playerGuessed) { // Runs if the player guessing phase is over
          guessNo--;
          guessTime = timer.stopTimer(guessTime);
          if (isSecondTurn) { // The game is finished
            confirm.setText("Finish");
          } else { // Goes to the second phase of the game
            confirm.setText("Become code maker");
          }
          isSecondTurn = true; // States that the first phase has been completed
          gD.exportData(guessNo, secretCode, guessList, guessTime, humanIsBreaking); // Exports game data
          // Resets game data
          guessList.clear();
          guessTime = 0;
        }
      }
    } else if (command.equals("Finish")) { // Game is over
      clearPanels(); // Clears all panels
      setFinishPage(); // Method for setting up game over page
    } else if (command.equals("Play Again")) { // Player would like to play again
      dispose(); // Closes old game
      new codebreakerGUI(); // Starts a new game
    } else if (command.equals("Start")) { // Computer starts guessing
      computerGuessCounter++;
      nextButton.setText("Give Hint");
      // Information on the computer's guess
      computerGuessInfo.add(computerOutput);
      computerGuessInfo.add(computerGuessPegs);
      setSecretCode.add(computerGuessInfo);
      // Displays the number of guesses the computer has made
      guesses.setText("Guess Count: " + computerGuessCounter);
      displayCompGuess();
    } else if (command.equals("Give Hint")) { // Player confirms their hint
      if (playerHint[0] + playerHint[1] + playerHint[2] + playerHint[3] == 8) {
        guesses.setText("Guess Count: " + computerGuessCounter); // Updates guess counter
        guessList.add(Arrays.toString(compGuess)); // Adds the computer's guess to the guess list
        gD.exportData(computerGuessCounter, compGuess, guessList, guessTime, humanIsBreaking); // Exports game data
        // Resets game data
        guessList.clear();
        guessTime = 0;// Checks to see if the computer has guessed the code
        instruction.setText("The computer guessed your code!");
        if (!isSecondTurn) { // Goes to the second phase of the game
          nextButton.setText("Become code breaker");
          isSecondTurn = true;
        } else { // Game is finished
          nextButton.setText("Finish");
        }
        newCompGuess();
      } else {
        computerGuessCounter++;
        computerGuessPhase();
      }
      displayFeedback(playerHint, feedback); // Displays the feedback from the computer's previous guess
      lastGuess.setVisible(true);
      feedback.setVisible(true);
    }
  }
  
  public void playerFeedback() { // Player makes the hint
    setSecretCode.removeAll();
    JPanel feedbackSlots = new JPanel();
    JPanel kPegSlots = new JPanel();
    for (int i = 0; i < NODENUM; i++) { // Displays the slots for the feedback from the player
      final JLabel kPeg = new JLabel();
      // Formating the peg
      kPeg.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(20, 20, 20, 20)));
      kPeg.setOpaque(true);
      kPeg.setName(Integer.toString(i));
      kPeg.addMouseListener(new MouseAdapter() { // Drag and drop
        @Override
        public void mousePressed(MouseEvent e) {
          Component component = e.getComponent();
          keySlotPicked = Integer.valueOf(component.getName()); // Finds the slot that the player has "filled"
          playerHint[keySlotPicked] = keyColorPicked; // Enters the hint into the player hint array
          switchKeyBackground(keyColorPicked, kPeg); // Changes the colour of the slot
        }
      });
      feedbackSlots.add(kPeg); // Adds the feedback slots to the panel
    }
    // Labels for the keys
    JLabel wKey = new JLabel();
    JLabel bKey = new JLabel();
    JLabel gKey = new JLabel();
    // Adding key names
    gKey.setName("0");
    wKey.setName("1");
    bKey.setName("2");
    MouseListener cursorForKey = new MouseAdapter() { // Mouse click action listener
      @Override
      public void mousePressed(MouseEvent e) {
        Component component = e.getComponent();
        keyColorPicked = Integer.valueOf(component.getName()); // Determines which key has been picked up
        switchCursorColorKey(keyColorPicked); // Switches cursor to circular cursor with picked colour
      }
    };
    // Formatting the keys
    gKey.setBackground(Color.LIGHT_GRAY);
    gKey.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(20, 20, 20, 20)));
    gKey.setOpaque(true);
    wKey.setBackground(Color.WHITE);
    wKey.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(20, 20, 20, 20)));
    wKey.setOpaque(true);
    bKey.setBackground(Color.BLACK);
    bKey.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(20, 20, 20, 20)));
    bKey.setOpaque(true);
    // Adding mouse click actions listener
    gKey.addMouseListener(cursorForKey);
    wKey.addMouseListener(cursorForKey);
    bKey.addMouseListener(cursorForKey);
    // Adding keys to the panel
    kPegSlots.add(gKey);
    kPegSlots.add(wKey);
    kPegSlots.add(bKey);
    // Formatting the rest of the window
    instruction.setText("Please give the proper feedback in accordance with the computer's guess by clicking on these hint pegs.");
    setSecretCode.add(instruction);
    setSecretCode.add(kPegSlots); // Adds keys
    setSecretCode.add(Box.createVerticalStrut(25)); // Spacing
    setSecretCode.add(feedbackSlots); // Adds slots
    setSecretCode.add(Box.createVerticalStrut(25)); // Spacing
    setSecretCode.add(Box.createVerticalStrut(25)); // Spacing
    setCursor(Cursor.getDefaultCursor()); // Sets cursor to default cursor
    setSecretCode.add(Box.createVerticalStrut(25)); // Spacing
    guesses.setText("Guess Count: " + Integer.toString(computerGuessCounter)); // Displays guess counter
    setSecretCode.add(guesses);
    setSecretCode.add(Box.createVerticalStrut(25)); // Spacing
    // Formatting button
    nextButton.setText("Start");
    nextButton.addActionListener(this);
    nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    setSecretCode.add(nextButton);
    setSecretCode.add(Box.createVerticalStrut(25)); // Spacing
    computerOutput.setAlignmentX(Component.CENTER_ALIGNMENT);
    setSecretCode.updateUI(); // Updates the frame
  }
  
  public void newGame(boolean humanIsBreaking) { // Setting up game board
    clearPanels(); // Clears all panels
    // Changes background
    try {
      BufferedImage img = ImageIO.read(new File("Images/gameBoard.jpg"));
      setContentPane(new JLabel(new ImageIcon(img)));
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("File not found.");
    }
    // Sets frame layout
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    createSlots(); // Creates slots for the player to enter nodes into
    instruction.setAlignmentX(Component.CENTER_ALIGNMENT); // Centers the instruction JLabel
    humanSetCode(); // Human sets code
    left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS)); // Sets layout for the left side of the frame
    right.setLayout(new GridLayout(11, 1)); // Sets layout for the right side of the fram
    guesses.setAlignmentX(Component.CENTER_ALIGNMENT); // Centers the guesses JLabel in the left panel
    if (humanIsBreaking) { // Runs if human is the code breaker
      // Adding content to the left panel
      left.add(instruction);
      left.add(Box.createVerticalStrut(50)); // Spacing
      left.add(setPegPanel);
      left.add(Box.createVerticalStrut(50)); // Spacing
      left.add(Box.createVerticalStrut(50)); // Spacing
      guesses.setText("Guess Number: " + Integer.toString(guessNo));
      left.add(guesses);
      // Adding content to the right panel
      confirm.addActionListener(this);
      slotPanel.add(confirm);
      right.add(slotPanel);
      // Adding panels to the frame
      add(left);
      add(right);
    } else { // Runs if human is the code maker
      setSecretCode.setLayout(new BoxLayout(setSecretCode, BoxLayout.PAGE_AXIS)); // Sets layout of the frame
      // Adding content
      setSecretCode.add(instruction);
      setSecretCode.add(Box.createVerticalStrut(50)); // Spacing
      setSecretCode.add(Box.createVerticalStrut(50)); // Spacing
      setSecretCode.add(slotPanel);
      // Adding the panel to the frame
      add(setSecretCode);
    }
    setLayout(fL);
    revalidate();
    repaint();
  }
  
  public void humanSetCode() { // Human sets the code
    for (int i = 1; i < COLOURNUM + 1; i++) { // Creating nodes
      final JButton peg = new JButton();
      // Formatting nodes
      peg.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(20, 20, 20, 20)));
      switchBackground(i, peg);
      peg.setOpaque(true);
      peg.setName(Integer.toString(i));
      peg.addMouseListener(new MouseAdapter() { // Drag and drop
        @Override
        public void mousePressed(MouseEvent e) {
          Component component = e.getComponent();
          colorPicked = Integer.valueOf(component.getName()); // Finds the colour selected
          switchCursorColorPeg(colorPicked); // Changes cursor
        }
      });
      setPegPanel.add(peg); // Adding nodes
    }
  }
  
  public Cursor createCircleCursor(Color cursorColor) { // Changes cursor
    BufferedImage bi = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
    Graphics g = bi.getGraphics();
    g.setColor(cursorColor); // Sets colour
    g.fillOval(0, 0, 10, 10);
    return Toolkit.getDefaultToolkit().createCustomCursor(bi, new Point(5, 5), cursorColor + " Circle"); // Makes cursor into a circle
  }
  
  public void createSlots() { // Creates slots for player to enter nodes into
    fL.setHgap(50); // Spacing
    slotPanel.setLayout(fL);
    for (int i = 0; i < NODENUM; i++) { // Adds slots
      final JLabel slot = new JLabel();
      // Formatting slots
      slot.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(20, 20, 20, 20)));
      slot.setOpaque(true);
      slot.setName(Integer.toString(i));
      slot.addMouseListener(new MouseAdapter() { // Drag and drop
        @Override
        public void mousePressed(MouseEvent e) {
          Component component = e.getComponent();
          slotPicked = Integer.valueOf(component.getName());
          if (humanIsBreaking == true && codeSet == false) { // Stores value into the player's guess
            guess[slotPicked] = colorPicked;
            switchBackground(colorPicked, slot); // Changes the colour of the slot
          } else if (humanIsBreaking == false && codeSet == false) { // Stores value into the code
            secretCode[slotPicked] = colorPicked;
            switchBackground(colorPicked, slot); // Changes the colour of the slot
          }
        }
      });
      slotPanel.add(slot); // Adds slot to the panel
    }
  }
  
  public void newPlyrGuess() { // Player makes a guess
    JPanel row = new JPanel();
    feedback = new JPanel();
    lastGuess = new JPanel();
    fL.setHgap(50); // Spacing
    row.setLayout(fL);
    feedback.setLayout(new GridLayout(2, 2)); // Feedback panel (2x2 grid)
    lastGuess.setLayout(fL); // Player's previous guess
    lastGuess.setAlignmentX(Component.CENTER_ALIGNMENT);
    hint = gameplay.getHint(guess, secretCode); // Gets the feedback for the player's guess
    displayLastGuess(lastGuess, feedback, guess); // Displays the player's last guess
    displayFeedback(hint, feedback); // Displays the player's feedback
    // Adding content
    row.add(lastGuess);
    row.add(feedback);
    right.add(row);
    if (hint[0] + hint[1] + hint[2] + hint[3] == 8) { // Checks to see if the player has guessed correctly
      playerGuessed = true;
    }
  }
  
  public void newCompGuess() { // Displays computer's previous guess
    JPanel row = new JPanel();
    feedback = new JPanel();
    lastGuess = new JPanel();
    // Keeps the panels hidden until the player clicks "Next"
    lastGuess.setVisible(false);
    feedback.setVisible(false);
    fL.setHgap(50); // Spacing
    row.setLayout(fL);
    feedback.setLayout(new GridLayout(2, 2)); // Feedback for the computer
    lastGuess.setLayout(fL);
    lastGuess.setAlignmentX(Component.CENTER_ALIGNMENT);
    displayLastGuess(lastGuess, feedback, compGuess); // Displays the computer's previous guess
    // Adding content
    row.add(lastGuess);
    row.add(feedback);
    setSecretCode.add(row);
  }
  
  public void displayLastGuess(JPanel lastGuess, JPanel feedback, int[] guessed) { // Displays the previous guess
    for (int i = 0; i < NODENUM; i++) { // get values of guess
      JLabel cPeg = new JLabel();
      cPeg.setOpaque(true);
      cPeg.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(20, 20, 20, 20)));
      if (humanIsBreaking) { // If the human is the code breaker, it displays the player's previous guess
        switchBackground(guessed[i], cPeg);
      } else { // If the humand is the code maker, it displays the computer's previous guess
        switchBackground(compGuess[i], cPeg);
      }
      lastGuess.add(cPeg);
    }
  }
  
  public void displayFeedback(int[] hints, JPanel feedback) { // Displays the feedback/hint
    for (int i = 0; i < NODENUM; i++) { // get values of hints
      JLabel kPeg = new JLabel();
      kPeg.setOpaque(true);
      kPeg.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
      switchKeyBackground(hints[i], kPeg); // Changes the colour of the hint slots
      feedback.add(kPeg);
    }
  }
  
  public void computerGuessPhase() { // Computer makes a guess
    int temp = 0;
    newCompGuess();
    timer.startTimer(); // Times the computer's guess
    if (playerHint[0] + playerHint[1] + playerHint[2] + playerHint[3] != 8) { // Checks to see if the computer guessed correctly
      guessList.add(Arrays.toString(compGuess)); // Adds the computer's guess to the guess list
      set = computerGuess.start(playerHint, NODENUM, COLOURNUM, set); // Generates the computer's next guess
      temp = set.get(set.size() - 1); // The computer's guess is the last value of the set
      for (int i = NODENUM - 1; i >= 0; i--) { // Changes integer guess into array guess
        compGuess[i] = temp % 10;
        temp /= 10;
      }
      guessTime = timer.stopTimer(guessTime); // Stops the timer
    }
    displayCompGuess();
    guesses.setText("Guess Count: " + computerGuessCounter); // Updates guess counter
  }
  
  public void displayCompGuess() { // Displays the computer's current guess
    computerGuessMessage = "Computer: I guess the code: " + Arrays.toString(compGuess);
    computerGuessPegs.removeAll();
    for (int i = 0; i < NODENUM; i++) { // Runs through the computer's guess
      final JLabel slot = new JLabel();
      slot.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(20, 20, 20, 20)));
      slot.setOpaque(true);
      switch (compGuess[i]) { // Displays the apporiate colour according to the computer's current guess
        case RED:
          slot.setBackground(Color.RED);
          break;
        case ORANGE:
          slot.setBackground(Color.ORANGE);
          break;
        case YELLOW:
          slot.setBackground(Color.YELLOW);
          break;
        case GREEN:
          slot.setBackground(Color.GREEN);
          break;
        case BLUE:
          slot.setBackground(Color.BLUE);
          break;
        case PURPLE:
          slot.setBackground(purpleColor);
          break;
      }
      computerGuessPegs.add(slot); // Adds the coloured slot
    }
    computerOutput.setText(computerGuessMessage); // Updates computer's guess message
  }
  
  public void setList() { // Initalize an array list with all possible code combinations. Size is to shrink as computer makes more guesses
    int temp = 0;
    int temp1 = 0;
    int temp2 = 0;
    for (int i = 1; i < COLOURNUM + 1; i++) {
      temp = i;
      for (int j = 1; j < COLOURNUM + 1; j++) {
        temp2 = temp;
        temp = temp * 10 + j;
        for (int k = 1; k < COLOURNUM + 1; k++) {
          temp1 = temp;
          temp = temp * 10 + k;
          for (int l = 1; l < COLOURNUM + 1; l++) {
            set.add(temp * 10 + l);
          }
          temp = temp1;
        }
        temp = temp2;
      }
    }
    set.add(1122); // Adds the computer's first guess which is always 1122 according to the five guess algorithm
  }
  
  public void switchBackground(int colour, Component c) { // Changes the colour of a slot
    switch (colour) { // Finds the desired colour
      case RED:
        c.setBackground(Color.RED);
        break;
      case ORANGE:
        c.setBackground(Color.ORANGE);
        break;
      case YELLOW:
        c.setBackground(Color.YELLOW);
        break;
      case GREEN:
        c.setBackground(Color.GREEN);
        break;
      case BLUE:
        c.setBackground(Color.BLUE);
        break;
      case PURPLE:
        c.setBackground(purpleColor);
        break;
    }
  }
  
  public void switchKeyBackground(int colour, Component c) { // Changes the background of the hint slots
    switch (colour) {
      case 0:
        c.setBackground(null);
        break;
      case 1:
        c.setBackground(Color.WHITE);
        break;
      case 2:
        c.setBackground(Color.BLACK);
        break;
    }
  }
  
  public void switchCursorColorPeg(int colour) { // Changes the colour of the cursor
    switch (colour) {
      case RED:
        setCursor(createCircleCursor(Color.RED));
        break;
      case ORANGE:
        setCursor(createCircleCursor(Color.ORANGE));
        break;
      case YELLOW:
        setCursor(createCircleCursor(Color.YELLOW));
        break;
      case GREEN:
        setCursor(createCircleCursor(Color.GREEN));
        break;
      case BLUE:
        setCursor(createCircleCursor(Color.BLUE));
        break;
      case PURPLE:
        setCursor(createCircleCursor(purpleColor));
        break;
    }
  }
  
  public void switchCursorColorKey(int colour) { // Changes the colour of the cursor for when the player creates feedback
    switch (colour) {
      case 0:
        setCursor(createCircleCursor(Color.LIGHT_GRAY));
        break;
      case 1:
        setCursor(createCircleCursor(Color.WHITE));
        break;
      case 2:
        setCursor(createCircleCursor(Color.BLACK));
        break;
    }
  }
  
  public void clearPanels() { // Clears all panels
    titleScreen.removeAll();
    right.removeAll();
    left.removeAll();
    setSecretCode.removeAll();
    userButtons.removeAll();
    codeToBreak.removeAll();
    setPegPanel.removeAll();
    slotPanel.removeAll();
    revalidate();
    repaint();
  }
  
  public void setFinishPage() { // Creating the game over page
    BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS); // Frame layout
    // Determines who won
    if (computerGuessCounter > guessNo) {
      introMessage.setText("Congratulations! You beat the computer!");
    } else if (computerGuessCounter == guessNo) {
      introMessage.setText("It's a tie!");
    } else {
      introMessage.setText("The computer wins!");
    }
    setLayout(layout);
    // Add content
    introPanel.add(introMessage);
    playAgain.addActionListener(this);
    userButtons.add(playAgain);
    userButtons.add(quit);
    add(introPanel);
    add(userButtons);
    revalidate();
    repaint();
  }
  
  public void gameInfo() { // How To Play
    JFrame rules = new JFrame(); // Creates new window
    rules.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    rules.setLocation(10, 390);
    rules.setTitle("How to Play"); // Sets title
    rules.setSize(1520, 200);
    JPanel rulesPanel = new JPanel();
    rulesPanel.setLayout(new BoxLayout(rulesPanel, BoxLayout.PAGE_AXIS));
    // Managing and displaying rules
    JLabel rulesText = new JLabel(
                                  "The goal of MASTERMIND is to guess a secret code consisting of a series of 4 colored pegs. Each guest results in feedback narrowing down the possibilities of the code. The winner is the player who solves his opponent's secret code with fewer guesses.");
    JLabel rulesText2 = new JLabel("The play of the game goes as follows:");
    JLabel rulesText3 = new JLabel(
                                   "1) One player, known as the Codemaker, makes a 4 color code using any combination of the 6 colors they want. They can also use 2 or more of the same color if they want.");
    JLabel rulesText4 = new JLabel(
                                   "2) The other player, known as the Codebreaker, places Code Pegs to guess. The Codebreaker is attempting to duplicate the exact colors and positions of the secret code.");
    JLabel rulesText5 = new JLabel(
                                   "3) The Codemaker responds by placing 0, 1, 2, 3, or 4 Hint Pegs in the Hint Peg holes to aid the Codebreaker in their guesses.");
    JLabel rulesText6 = new JLabel(
                                   "4) A black Hint Peg means correct colour AND position, a white Hint Peg means correct colour BUT wrong position, no Hint Pegs (Gray color) means wrong colour and position.");
    JLabel rulesText7 = new JLabel(
                                   "Note: If you are the Codemaker, please place 4 black Hint Pegs to indicate that the other player has correctly guessed so the next round can start.");
    JLabel rulesText8 = new JLabel(
                                   "When you are entering hints, the hints must have all black hint nodes on the left, then all white hint nodes afterwards. If you do not order it in that way, you will have trouble setting the code.");
    JLabel rulesText9 = new JLabel("You only get 10 guesses, the player with the least guesses wins!");
    // Centering all the JLabels
    rulesText.setAlignmentX(Component.CENTER_ALIGNMENT);
    rulesText2.setAlignmentX(Component.CENTER_ALIGNMENT);
    rulesText3.setAlignmentX(Component.CENTER_ALIGNMENT);
    rulesText4.setAlignmentX(Component.CENTER_ALIGNMENT);
    rulesText5.setAlignmentX(Component.CENTER_ALIGNMENT);
    rulesText6.setAlignmentX(Component.CENTER_ALIGNMENT);
    rulesText7.setAlignmentX(Component.CENTER_ALIGNMENT);
    rulesText8.setAlignmentX(Component.CENTER_ALIGNMENT);
    rulesText9.setAlignmentX(Component.CENTER_ALIGNMENT);
    // Adding all the JLabels
    rulesPanel.add(rulesText);
    rulesPanel.add(rulesText2);
    rulesPanel.add(rulesText3);
    rulesPanel.add(rulesText4);
    rulesPanel.add(rulesText5);
    rulesPanel.add(rulesText6);
    rulesPanel.add(rulesText7);
    rulesPanel.add(rulesText8);
    rulesPanel.add(rulesText9);
    rules.add(rulesPanel);
    rulesPanel.setVisible(true);
    rules.setVisible(true);
  }
  
  public static void main(String[] args) {
    new codebreakerGUI();
  }
}
