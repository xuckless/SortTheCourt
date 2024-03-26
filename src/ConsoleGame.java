import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class ConsoleGame {
  
  private JButton next;
  private static boolean completedPart;
  
  private static void loadDefaultSave(String filePath, String defaultSaveContents) {
    File defaultSave = new File(filePath);
    if (defaultSave.exists() && defaultSave.isFile()) {
      if (defaultSave.delete()) {
        //System.out.println("Old save deleted.");
      } else {
        //System.err.println("Failed to delete old save.");
      }
    }
    try (FileWriter writer = new FileWriter(defaultSave)) {
      writer.write(defaultSaveContents);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  private static void loadSave(String filePath) {
    File gameSave = new File(filePath);
    
  }
  
  private static String formatText(String text) {
    int lineWidth = 100;
    StringBuilder formattedText = new StringBuilder();
    String[] words = text.split("\\s+");
    int currentLineWidth = 0;
    
    for (String word : words) {
      if (currentLineWidth + word.length() + 1 > lineWidth) {
        // Start a new line
        formattedText.append("\n");
        currentLineWidth = 0;
      }
      // Append the word to the line
      formattedText.append(word).append(" ");
      currentLineWidth += word.length() + 1;
    }
    return formattedText.toString();
  }
  
  
  public static void StartGame(String filePath, String scenarioTxtFilePath){
    File gameSave = new File(filePath);
    if (!gameSave.exists()) loadDefaultSave(filePath, "FirstStart:0\nDay:1\nGold:150\nFaith:40\nTech:1\nPopulation:150");
    
    if (DataEditor.getDataValue("FirstStart", filePath) == 0) {
      GameScreenOne.setMessageOne("Welcome to the game!");
    } else {
      GameScreenOne.setMessageOne("Welcome back to the game! Enter '1' to continue, or '2' to start a new game.");
    }
    
    JButton startGame = GameScreenOne.createChoiceButton("New Game");
    JButton continueGame = GameScreenOne.createChoiceButton("Continue Game");
    JButton yesContinue = GameScreenOne.createChoiceButton("Yes! Continue");
    
    GameScreenOne.addChoiceButton(startGame, e->{
      if (DataEditor.getDataValue("FirstStart", filePath) == 0) {
        GameScreenOne.clearButton(startGame);
        GameScreenOne.clearButton(continueGame);
        GameScreenOne.setMessageOne("Starting New Game...");
        GameScreenOne.setMessageTwo(formatText("You awake to an abyss of nothingness all around. 'Ah, little light,' a voice called, but you could not see the speaker. 'Do not worry, little light, your reaction is normal. We're going to try the plan again. Just do not forget your task: Keep the reality on the desired path. Keep the kingdom from collapse. Are you ready to continue?'"));
        GameScreenOne.addChoiceButton(yesContinue, i->{
          ConsoleGame.StartPart1(filePath, scenarioTxtFilePath, true);
        });
      }
      if (DataEditor.getDataValue("FirstStart", filePath) == 1) {
        ConsoleGame.StartPart1(filePath, scenarioTxtFilePath, false);
      }
    });
    
    GameScreenOne.addChoiceButton(continueGame, e -> {
      GameScreenOne.setMessageTwo(formatText("You awake to an abyss of nothingness all around. 'Ah, little light,' a voice called, but you could not see the speaker. 'Do not worry, little light, your reaction is normal. We're going to try the plan again. Just do not forget your task: Keep the reality on the desired path. Keep the kingdom from collapse. Are you ready to continue?'"));
      GameScreenOne.clearButton(continueGame);
      GameScreenOne.clearButton(startGame);
      GameScreenOne.addChoiceButton(yesContinue, i->{
        GameScreenOne.clearButton(yesContinue);
        ConsoleGame.StartPart1(filePath, scenarioTxtFilePath, true);
      });
    });
  }
  
//  public static void startGame(String filePath, String scenarioTxtFilePath) {
//
//    File gameSave = new File(filePath);
//    if (!gameSave.exists()) loadDefaultSave(filePath, "FirstStart:0\nDay:1\nGold:150\nFaith:40\nTech:1\nPopulation:150");
//
//    if (DataEditor.getDataValue("FirstStart", filePath) == 0) {
//      System.out.println("Welcome to the game! Enter '1' to begin.");
//    } else {
//      System.out.println("Welcome back to the game! Enter '1' to continue, or '2' to start a new game.");
//    }
//
//    Scanner userInput = new Scanner(System.in);
//    switch (userInput.nextLine()) {
//      case "1":
//        if (DataEditor.getDataValue("FirstStart", filePath) == 0) {
//          System.out.println("===================================================================================================");
//          System.out.println(formatText("You awake to an abyss of nothingness all around. 'Ah, little light,' a voice called, but you could not see the speaker. 'Do not worry, little light, your reaction is normal. We're going to try the plan again. Just do not forget your task: Keep the reality on the desired path. Keep the kingdom from collapse. Are you ready to continue?' (Y/N)"));
//          System.out.println();
//          if (!userInput.nextLine().equals("Y")) {
//            System.out.println("===================================================================================================");
//            System.out.println(formatText("Despite having no vision, you could feel a sudden disappointment from the voice's possessor. 'Well, take your time, little light. We have all the time in the world.' You knew it to be an expression, yet something about the voice told you he had meant it literally."));
//            System.out.println("===================================================================================================");
//            System.exit(0);
//          }
//          ConsoleGame.StartPart1(filePath, scenarioTxtFilePath, true);
//        }
//        if (DataEditor.getDataValue("FirstStart", filePath) == 1) {
//          ConsoleGame.StartPart1(filePath, scenarioTxtFilePath, false);
//        }
//        break;
//
//      case "2":
//        loadDefaultSave(filePath, "FirstStart:0\nDay:1\nGold:150\nFaith:40\nTech:1\nPopulation:150");
//        System.out.println("Starting New Game");
//        System.out.println();
//        System.out.println("===================================================================================================");
//        System.out.println(formatText("You awake to an abyss of nothingness all around. 'Ah, little light,' a voice called, but you could not see the speaker. 'Do not worry, little light, your reaction is normal. We're going to try the plan again. Just do not forget your task: Keep the reality on the desired path. Keep the kingdom from collapse. Are you ready to continue?' (Y/N)"));
//        System.out.println();
//        if (!userInput.nextLine().equals("Y")) {
//          System.out.println("===================================================================================================");
//          System.out.println(formatText("Despite having no vision, you could feel a sudden disappointment from the voice's possessor. 'Well, take your time, little light. We have all the time in the world.' You knew it to be an expression, yet something about the voice told you he had meant it literally."));
//          System.out.println("===================================================================================================");
//          System.exit(0);
//        }
//        ConsoleGame.StartPart1(filePath, scenarioTxtFilePath, true);
//        break;
//
//      default:
//        System.exit(0);
//    }
//
//
//  }
  
//  public static void GameLost(String filePath) {
//    loadDefaultSave(filePath, "FirstStart:1\nDay:1\nGold:150\nFaith:40\nTech:1\nPopulation:150");
//    Scanner userInput = new Scanner(System.in);
//    System.out.println("===================================================================================================");
//    System.out.println(formatText("'Ah, little light,' the somewhat familiar voice called. 'You're back. You must have strayed from the the desired path,' the voice added. You knew this person, didn't you? For whatever reason, you could not see them. 'Do not worry, little light, your reaction is normal. We're going to try the plan again. Just do not forget your task: Keep the reality on the desired path. Keep the kingdom from collapse. Are you ready to get back to work? (Y/N)"));
//    System.out.println();
//    if (!userInput.nextLine().equals("Y")) {
//      System.out.println("===================================================================================================");
//      System.out.println(formatText("Even without sight, you could practically see the disappointment on the face of that voice's possessor. 'Well, take your time, little light. We have all the time in the world.' You knew it to be an expression, yet something about the voice told you he had meant it literally."));
//      System.out.println("===================================================================================================");
//      System.exit(0);
//    }
//  }
  
  public static void gameLost(String filePath){
    JButton continueGame = GameScreenOne.createChoiceButton("Start a New Journey!");
    GameScreenOne.setMessageOne(formatText("'Ah, little light,' the somewhat familiar voice called. 'You're back. You must have strayed from the the desired path,' the voice added. You knew this person, didn't you? For whatever reason, you could not see them. 'Do not worry, little light, your reaction is normal. We're going to try the plan again. Just do not forget your task: Keep the reality on the desired path. Keep the kingdom from collapse. Are you ready to get back to work?"));
    GameScreenOne.addChoiceButton(continueGame, e -> {
      GameScreenOne.clearButton(continueGame);
      loadDefaultSave(filePath, "FirstStart:1\nDay:1\nGold:150\nFaith:40\nTech:1\nPopulation:150");
    });
  }
  
  public static void StartPart1(String filepath, String scenarioTxtFilePath, boolean runIntroScene){
    boolean completedPart = false;
    
    if (runIntroScene) {
      GameScreenOne.setMessageTwo(formatText("You suddenly find yourself in a body. It was strange, this feeling, like it had been some time since your soul had... well, a physical form to manifest itself in. You could feel the droplets of water on your skin, soaking into your clothes. You could hear the soft pattering of rain drops hitting stone and wood. But those senses paled in comparison to your sight. You could see a castle towering in the distance. You could see the sombre, clouded sky above. You could see the crows flying overhead. And before you, you could see a group of important-looking people, all wearing dark clothes fit for mourning, gathering around a wooden coffin that was being carefully lowered into a hole in the ground. This was a funeral, you realized, but for who?"));
      GameScreenOne.setMessageTwo(formatText("\nA man to your left suddenly looked over at you expectantly. At the sight of him, a memory seemed to call out from your new body: Alistair Kyntha, Hand of the King. The memory was likely a residual from the soul that had occupied this body before you. 'I'm sorry, young prin... Your Grace.' The Hand hesitated on using the royal title, as though he had never referred to you with it before. 'Your father was a good man, and a good king. I'm sorry.'"));
      
      GameScreenOne.appendMessageTwo("\n\tSelect choice: ");
      JButton choice1 = GameScreenOne.createChoiceButton("1. For what? It wasn't your fault he died.");
      JButton choice2 = GameScreenOne.createChoiceButton("2. I just feel... empty, I guess.");
      JButton yesContinue = GameScreenOne.createChoiceButton("Yes! Continue");
      
      GameScreenOne.addChoiceButton(choice1, e -> {
        GameScreenOne.clearButton(choice1);
        GameScreenOne.clearButton(choice2);
        GameScreenOne.setMessageTwo(formatText("'No, but...,' The Hand started. 'I suppose I just wish we could have prepared you a little more for the crown. Together, I mean.' You nodded in understanding. You didn't know your father, but... there were fragments. Images that formed in your mind. Memories not your own. All of them spoke to your father being a good man. After a moment spent silently watching the birds overhead, The Hand spoke up. 'I know you're still in mourning, Your Grace, but the crown has passed to you now, and the kingdom needs a ruler. It's a small one, I know, but it still needs daily management or it will all crumble. Are you ready to get to work?' (Y/N)"));
        GameScreenOne.addChoiceButton(yesContinue, i->{
          GameScreenOne.clearButton(yesContinue);
          System.out.println("The Hand smiled. 'Let's begin, then.'");
          DataEditor.changeDataValue("FirstStart", 1, "SAVEDATA.txt");
          ConsoleGame.nextPart(filepath, scenarioTxtFilePath);
        });
      });
      GameScreenOne.addChoiceButton(choice2, e -> {
        GameScreenOne.clearButton(choice2);
        GameScreenOne.clearButton(choice1);
        System.out.println(formatText("'I understand, Your Grace,' The Hand said with a sigh. 'I know you're still in mourning, Your Grace, but the crown has passed to you now, and the kingdom needs a ruler. It's a small one, I know, but it still needs daily management or it will all crumble. Are you ready to get to work?' (Y/N)"));
        GameScreenOne.addChoiceButton(yesContinue, i->{
          GameScreenOne.clearButton(yesContinue);
          GameScreenOne.setMessageTwo("The Hand smiled. 'Let's begin, then.'");
          DataEditor.changeDataValue("FirstStart", 1, "SAVEDATA.txt");
          ConsoleGame.nextPart(filepath, scenarioTxtFilePath);
        });
      });
    } else {
      GameScreenOne.setMessageTwo(formatText("Waking up from a deep, dreamless sleep, you find yourself in your room. Everything about it spoke royalty, from the burgundy wood and the gold that adorned the walls, to the luxurious, velvet drapes that framed the large windows. It was nice, this place. It was safe, and free from the drama of ruling a kingdom. Still, you had to get back to work. You had to 'Keep the kingdom on the desired path.' At that thought, you frowned, realising that you had forgotten where you had heard that phrase. Either way, there was work to do."));
      JButton getOutOfBed = GameScreenOne.createChoiceButton("Get out of bed");
      GameScreenOne.addChoiceButton(getOutOfBed, e -> {
        GameScreenOne.clearButton(getOutOfBed);
        GameScreenOne.setMessageTwo("You get up and get dressed, ready for another day.");
        ConsoleGame.nextPart(filepath, scenarioTxtFilePath);
      });
    }
  }
  
  public static void startPart1(String filePath, String scenarioTxtFilePath, boolean runIntroScene) {
    
    completedPart = false;
    Scanner userInput = new Scanner(System.in);
    
    if (runIntroScene) {
      System.out.println("===================================================================================================");
      System.out.println(formatText("You suddenly find yourself in a body. It was strange, this feeling, like it had been some time since your soul had... well, a physical form to manifest itself in. You could feel the droplets of water on your skin, soaking into your clothes. You could hear the soft pattering of rain drops hitting stone and wood. But those senses paled in comparison to your sight. You could see a castle towering in the distance. You could see the sombre, clouded sky above. You could see the crows flying overhead. And before you, you could see a group of important-looking people, all wearing dark clothes fit for mourning, gathering around a wooden coffin that was being carefully lowered into a hole in the ground. This was a funeral, you realized, but for who?"));
      System.out.println();
      System.out.println(formatText("A man to your left suddenly looked over at you expectantly. At the sight of him, a memory seemed to call out from your new body: Alistair Kyntha, Hand of the King. The memory was likely a residual from the soul that had occupied this body before you. 'I'm sorry, young prin... Your Grace.' The Hand hesitated on using the royal title, as though he had never referred to you with it before. 'Your father was a good man, and a good king. I'm sorry.'"));
      System.out.println();
      System.out.println("Dialogue Choices:");
      System.out.println("1. For what? It wasn't your fault he died.");
      System.out.println("2. I just feel... empty, I guess.");
      System.out.print("Select choice: ");
      switch (userInput.nextLine()){
        case "1":
          System.out.println();
          System.out.println(formatText("'No, but...,' The Hand started. 'I suppose I just wish we could have prepared you a little more for the crown. Together, I mean.' You nodded in understanding. You didn't know your father, but... there were fragments. Images that formed in your mind. Memories not your own. All of them spoke to your father being a good man. After a moment spent silently watching the birds overhead, The Hand spoke up. 'I know you're still in mourning, Your Grace, but the crown has passed to you now, and the kingdom needs a ruler. It's a small one, I know, but it still needs daily management or it will all crumble. Are you ready to get to work?' (Y/N)"));
          System.out.println();
          if (!userInput.nextLine().equals("Y")) System.exit(0);
          break;
        
        case "2":
          System.out.println();
          System.out.println(formatText("'I understand, Your Grace,' The Hand said with a sigh. 'I know you're still in mourning, Your Grace, but the crown has passed to you now, and the kingdom needs a ruler. It's a small one, I know, but it still needs daily management or it will all crumble. Are you ready to get to work?' (Y/N)"));
          System.out.println();
          if (!userInput.nextLine().equals("Y")) System.exit(0);
          break;
        
        default:
          System.out.println();
          System.out.println(formatText("When you didn't respond, The Hand only sighed. 'I know you're still in mourning, Your Grace, but the crown has passed to you now, and the kingdom needs a ruler. It's a small one, I know, but it still needs daily management or it will all crumble. Are you ready to get to work?' (Y/N)"));
          System.out.println();
          if (!userInput.nextLine().equals("Y")) System.exit(0);
      }
      System.out.println();
      System.out.println("The Hand smiled. 'Let's begin, then.'");
      DataEditor.changeDataValue("FirstStart", 1, filePath);
      System.out.println("===================================================================================================");
    }
    
    
    
    else {
      System.out.println("===================================================================================================");
      System.out.println(formatText("Waking up from a deep, dreamless sleep, you find yourself in your room. Everything about it spoke royalty, from the burgundy wood and the gold that adorned the walls, to the luxurious, velvet drapes that framed the large windows. It was nice, this place. It was safe, and free from the drama of ruling a kingdom. Still, you had to get back to work. You had to 'Keep the kingdom on the desired path.' At that thought, you frowned, realising that you had forgotten where you had heard that phrase. Either way, there was work to do."));
      System.out.println("Get out of bed? (Y/N)");
      if (userInput.nextLine().equals("Y")) {
        System.out.println();
        System.out.println("You get up and get dressed, ready for another day.");
      } else {
        System.out.println();
        System.out.println(formatText("Unwilling to do your job as the King, you close your eyes, allowing yourself to fall back asleep."));
        System.out.println("===================================================================================================");
        System.exit(0);
      }
      System.out.println("===================================================================================================");
    }
    
    
    do {
      Scenario currentScenario = SortTheCourtButEpicVersion.loadScenario(filePath, scenarioTxtFilePath);
      
      if (currentScenario == null) {
        System.out.println("All scenarios completed.");
        System.exit(0);
      }
      
      DataEditor.changeDataValue("Gold", DataEditor.getDataValue("Gold", filePath) + currentScenario.goldConsequence, filePath);
      DataEditor.changeDataValue("Faith", DataEditor.getDataValue("Faith", filePath) + currentScenario.faithConsequence, filePath);
      DataEditor.changeDataValue("Population", DataEditor.getDataValue("Population", filePath) + currentScenario.populationConsequence, filePath);
      DataEditor.changeDataValue("Tech", DataEditor.getDataValue("Tech", filePath) + currentScenario.techConsequence, filePath);
      
      if (DataEditor.getDataValue("Population", filePath) < 0) gameLost(filePath);
      
      System.out.println("===================================================================================================");
      System.out.println("Scenario " + DataEditor.getDataValue("Day", filePath));
      System.out.println("Gold: " + DataEditor.getDataValue("Gold", filePath));
      System.out.println("Faith: " + DataEditor.getDataValue("Faith", filePath));
      System.out.println("Population: " + DataEditor.getDataValue("Population", filePath));
      System.out.println("Tech: " + DataEditor.getDataValue("Tech", filePath));
      System.out.println();
      System.out.println(formatText("Audience: " + currentScenario.character));
      System.out.println(formatText(currentScenario.scenarioDescription));
      
      if (currentScenario.goldConsequence > 0) System.out.println("(Gold +" + currentScenario.goldConsequence + ")");
      if (currentScenario.goldConsequence < 0) System.out.println("(Gold " + currentScenario.goldConsequence + ")");
      if (currentScenario.populationConsequence > 0) System.out.println("(Population +" + currentScenario.populationConsequence + ")");
      if (currentScenario.populationConsequence < 0) System.out.println("(Population " + currentScenario.populationConsequence + ")");
      if (currentScenario.faithConsequence > 0) System.out.println("(Faith +" + currentScenario.faithConsequence + ")");
      if (currentScenario.faithConsequence < 0) System.out.println("(Faith " + currentScenario.faithConsequence + ")");
      if (currentScenario.techConsequence > 0) System.out.println("(Tech +" + currentScenario.techConsequence + ")");
      if (currentScenario.techConsequence < 0) System.out.println("(Tech " + currentScenario.techConsequence + ")");
      
      System.out.println();
      System.out.println("Choices:");
      
      if (currentScenario.choiceCount == 3) {
        
        if (currentScenario.choice1StartConditionMet) {
          System.out.println(formatText(currentScenario.choice1Description));
        } else {
          System.out.println(formatText(currentScenario.choice1Description + " [Not Met]"));
        }
        if (currentScenario.choice2StartConditionMet) {
          System.out.println(formatText(currentScenario.choice2Description));
        } else {
          System.out.println(formatText(currentScenario.choice2Description + " [Not Met]"));
        }
        if (currentScenario.choice3StartConditionMet) {
          System.out.println(formatText(currentScenario.choice3Description));
        } else {
          System.out.println(formatText(currentScenario.choice3Description + " [Not Met]"));
        }
        System.out.println();
        
      } else if (currentScenario.choiceCount == 2) {
        
        if (currentScenario.choice1StartConditionMet) {
          System.out.println(formatText(currentScenario.choice1Description));
        } else {
          System.out.println(formatText(currentScenario.choice1Description + " [Not Met]"));
        }
        if (currentScenario.choice2StartConditionMet) {
          System.out.println(formatText(currentScenario.choice2Description));
        } else {
          System.out.println(formatText(currentScenario.choice2Description + " [Not Met]"));
        }
        System.out.println();
        
      } else if (currentScenario.choiceCount == 1) {
        
        if (currentScenario.choice1StartConditionMet) {
          System.out.println(formatText(currentScenario.choice1Description));
        } else {
          System.out.println(formatText(currentScenario.choice1Description + "[Not Met]"));
        }
        System.out.println();
        
      } else System.out.println("Error with loaded scenario choice count.");
      
      System.out.print("Select choice: ");
      
      boolean choiceSelected = false;
      do {
        String input = userInput.nextLine();
        switch (input) {
          
          case "1":
            if (!currentScenario.choice1StartConditionMet) {
              System.out.print("You do not meet the requirements for choice 1. Please select a valid choice: ");
            } else {
              System.out.println();
              System.out.println(formatText(currentScenario.choice1Followup));
              currentScenario.choiceMadeWhenCompleted = 1;
              choiceSelected = true;
              System.out.println();
              if (currentScenario.choice1GoldConsequence > 0) System.out.println("(Gold +" + currentScenario.choice1GoldConsequence + ")");
              if (currentScenario.choice1GoldConsequence < 0) System.out.println("(Gold " + currentScenario.choice1GoldConsequence + ")");
              if (currentScenario.choice1FaithConsequence > 0) System.out.println("(Faith +" + currentScenario.choice1FaithConsequence + ")");
              if (currentScenario.choice1FaithConsequence < 0) System.out.println("(Faith " + currentScenario.choice1FaithConsequence + ")");
              if (currentScenario.choice1PopulationConsequence > 0) System.out.println("(Population +" + currentScenario.choice1PopulationConsequence + ")");
              if (currentScenario.choice1PopulationConsequence < 0) System.out.println("(Population " + currentScenario.choice1PopulationConsequence + ")");
              if (currentScenario.choice1TechConsequence > 0) System.out.println("(Tech +" + currentScenario.choice1TechConsequence + ")");
              if (currentScenario.choice1TechConsequence < 0) System.out.println("(Tech " + currentScenario.choice1TechConsequence + ")");
              System.out.println();
            }
            break;
          
          case "2":
            if (currentScenario.choiceCount < 2) {
              System.out.print("Choice 2 does not exist. Please select a valid choice: ");
            } else if (!currentScenario.choice2StartConditionMet) {
              System.out.print("You do not meet the requirements for choice 2. Please select a valid choice: ");
            } else {
              System.out.println();
              System.out.println(formatText(currentScenario.choice2Followup));
              currentScenario.choiceMadeWhenCompleted = 2;
              choiceSelected = true;
              System.out.println();
              if (currentScenario.choice2GoldConsequence > 0) System.out.println("(Gold +" + currentScenario.choice2GoldConsequence + ")");
              if (currentScenario.choice2GoldConsequence < 0) System.out.println("(Gold " + currentScenario.choice2GoldConsequence + ")");
              if (currentScenario.choice2FaithConsequence > 0) System.out.println("(Faith +" + currentScenario.choice2FaithConsequence + ")");
              if (currentScenario.choice2FaithConsequence < 0) System.out.println("(Faith " + currentScenario.choice2FaithConsequence + ")");
              if (currentScenario.choice2PopulationConsequence > 0) System.out.println("(Population +" + currentScenario.choice2PopulationConsequence + ")");
              if (currentScenario.choice2PopulationConsequence < 0) System.out.println("(Population " + currentScenario.choice2PopulationConsequence + ")");
              if (currentScenario.choice2TechConsequence > 0) System.out.println("(Tech +" + currentScenario.choice2TechConsequence + ")");
              if (currentScenario.choice2TechConsequence < 0) System.out.println("(Tech " + currentScenario.choice2TechConsequence + ")");
              System.out.println();
            }
            break;
          
          case "3":
            if (currentScenario.choiceCount < 3) {
              System.out.println("Choice 3 does not exist. Please select a valid choice: ");
            } else if (!currentScenario.choice3StartConditionMet) {
              System.out.print("You do not meet the requirements for choice 3. Please select a valid choice: ");
            } else {
              System.out.println();
              System.out.println(formatText(currentScenario.choice3Followup));
              currentScenario.choiceMadeWhenCompleted = 3;
              choiceSelected = true;
              System.out.println();
              if (currentScenario.choice3GoldConsequence > 0) System.out.println("(Gold +" + currentScenario.choice3GoldConsequence + ")");
              if (currentScenario.choice3GoldConsequence < 0) System.out.println("(Gold " + currentScenario.choice3GoldConsequence + ")");
              if (currentScenario.choice3FaithConsequence > 0) System.out.println("(Faith +" + currentScenario.choice3FaithConsequence + ")");
              if (currentScenario.choice3FaithConsequence < 0) System.out.println("(Faith " + currentScenario.choice3FaithConsequence + ")");
              if (currentScenario.choice3PopulationConsequence > 0) System.out.println("(Population +" + currentScenario.choice3PopulationConsequence + ")");
              if (currentScenario.choice3PopulationConsequence < 0) System.out.println("(Population " + currentScenario.choice3PopulationConsequence + ")");
              if (currentScenario.choice3TechConsequence > 0) System.out.println("(Tech +" + currentScenario.choice3TechConsequence + ")");
              if (currentScenario.choice3TechConsequence < 0) System.out.println("(Tech " + currentScenario.choice3TechConsequence + ")");
              System.out.println();
            }
            break;
          
          default:
            System.out.print(input + " is not a valid choice. Please select valid choice: ");
        }
      } while (!choiceSelected);
      
      
      
      
      //SAVING DATA
      DataEditor.changeDataValue("Day", DataEditor.getDataValue("Day", filePath) + 1, filePath);
      switch (currentScenario.choiceMadeWhenCompleted) {
        case 1:
          DataEditor.changeDataValue("Gold", DataEditor.getDataValue("Gold",filePath) + currentScenario.choice1GoldConsequence, filePath);
          DataEditor.changeDataValue("Faith", DataEditor.getDataValue("Faith",filePath) + currentScenario.choice1FaithConsequence, filePath);
          DataEditor.changeDataValue("Population", DataEditor.getDataValue("Population",filePath) + currentScenario.choice1PopulationConsequence, filePath);
          DataEditor.changeDataValue("Tech", DataEditor.getDataValue("Tech",filePath) + currentScenario.choice1TechConsequence, filePath);
          break;
        
        case 2:
          DataEditor.changeDataValue("Gold", DataEditor.getDataValue("Gold",filePath) + currentScenario.choice2GoldConsequence, filePath);
          DataEditor.changeDataValue("Faith", DataEditor.getDataValue("Faith",filePath) + currentScenario.choice2FaithConsequence, filePath);
          DataEditor.changeDataValue("Population", DataEditor.getDataValue("Population",filePath) + currentScenario.choice2PopulationConsequence, filePath);
          DataEditor.changeDataValue("Tech", DataEditor.getDataValue("Tech",filePath) + currentScenario.choice2TechConsequence, filePath);
          break;
        
        case 3:
          DataEditor.changeDataValue("Gold", DataEditor.getDataValue("Gold",filePath) + currentScenario.choice3GoldConsequence, filePath);
          DataEditor.changeDataValue("Faith", DataEditor.getDataValue("Faith",filePath) + currentScenario.choice3FaithConsequence, filePath);
          DataEditor.changeDataValue("Population", DataEditor.getDataValue("Population",filePath) + currentScenario.choice3PopulationConsequence, filePath);
          DataEditor.changeDataValue("Tech", DataEditor.getDataValue("Tech",filePath) + currentScenario.choice3TechConsequence, filePath);
          break;
      }
      
      
      DataEditor.addCompletedScenario(currentScenario.ID, currentScenario.choiceMadeWhenCompleted, filePath);
      
      if (DataEditor.getDataValue("Population", filePath) < 0) gameLost(filePath);
      if (DataEditor.getDataValue("Faith", filePath) > 100) DataEditor.changeDataValue("Faith", 100, filePath);
      if (DataEditor.getDataValue("Faith", filePath) < 0) DataEditor.changeDataValue("Faith", 0, filePath);
      if (DataEditor.getDataValue("Gold", filePath) < 0) {
        System.out.println("Error: Gold is negative.");
        System.exit(0);
      }
      
      
      if (DataEditor.getCompletedScenarioCount(filePath) >= 100000) {
        completedPart = true;
      }
      System.out.println("===================================================================================================");
      
    } while(!completedPart);
    
    System.out.println("Congratulations! You've completed the game!");
    System.exit(0);
  }
  
  public static void nextPart(String filePath, String scenarioTxtFilePath){
    do {
      Scenario currentScenario = SortTheCourtButEpicVersion.loadScenario(filePath, scenarioTxtFilePath);
      
      if (currentScenario == null) {
        System.out.println("All scenarios completed.");
        System.exit(0);
      }
      
      DataEditor.changeDataValue("Gold", DataEditor.getDataValue("Gold", filePath) + currentScenario.goldConsequence, filePath);
      DataEditor.changeDataValue("Faith", DataEditor.getDataValue("Faith", filePath) + currentScenario.faithConsequence, filePath);
      DataEditor.changeDataValue("Population", DataEditor.getDataValue("Population", filePath) + currentScenario.populationConsequence, filePath);
      DataEditor.changeDataValue("Tech", DataEditor.getDataValue("Tech", filePath) + currentScenario.techConsequence, filePath);
      
      if (DataEditor.getDataValue("Population", filePath) < 0) gameLost(filePath);
      
      System.out.println("===================================================================================================");
      System.out.println("Scenario " + DataEditor.getDataValue("Day", filePath));
      System.out.println("Gold: " + DataEditor.getDataValue("Gold", filePath));
      System.out.println("Faith: " + DataEditor.getDataValue("Faith", filePath));
      System.out.println("Population: " + DataEditor.getDataValue("Population", filePath));
      System.out.println("Tech: " + DataEditor.getDataValue("Tech", filePath));
      System.out.println();
      System.out.println(formatText("Audience: " + currentScenario.character));
      System.out.println(formatText(currentScenario.scenarioDescription));
      
      if (currentScenario.goldConsequence > 0) System.out.println("(Gold +" + currentScenario.goldConsequence + ")");
      if (currentScenario.goldConsequence < 0) System.out.println("(Gold " + currentScenario.goldConsequence + ")");
      if (currentScenario.populationConsequence > 0) System.out.println("(Population +" + currentScenario.populationConsequence + ")");
      if (currentScenario.populationConsequence < 0) System.out.println("(Population " + currentScenario.populationConsequence + ")");
      if (currentScenario.faithConsequence > 0) System.out.println("(Faith +" + currentScenario.faithConsequence + ")");
      if (currentScenario.faithConsequence < 0) System.out.println("(Faith " + currentScenario.faithConsequence + ")");
      if (currentScenario.techConsequence > 0) System.out.println("(Tech +" + currentScenario.techConsequence + ")");
      if (currentScenario.techConsequence < 0) System.out.println("(Tech " + currentScenario.techConsequence + ")");
      
      System.out.println();
      System.out.println("Choices:");
      
      if (currentScenario.choiceCount == 3) {
        
        if (currentScenario.choice1StartConditionMet) {
          System.out.println(formatText(currentScenario.choice1Description));
        } else {
          System.out.println(formatText(currentScenario.choice1Description + " [Not Met]"));
        }
        if (currentScenario.choice2StartConditionMet) {
          System.out.println(formatText(currentScenario.choice2Description));
        } else {
          System.out.println(formatText(currentScenario.choice2Description + " [Not Met]"));
        }
        if (currentScenario.choice3StartConditionMet) {
          System.out.println(formatText(currentScenario.choice3Description));
        } else {
          System.out.println(formatText(currentScenario.choice3Description + " [Not Met]"));
        }
        System.out.println();
        
      } else if (currentScenario.choiceCount == 2) {
        
        if (currentScenario.choice1StartConditionMet) {
          System.out.println(formatText(currentScenario.choice1Description));
        } else {
          System.out.println(formatText(currentScenario.choice1Description + " [Not Met]"));
        }
        if (currentScenario.choice2StartConditionMet) {
          System.out.println(formatText(currentScenario.choice2Description));
        } else {
          System.out.println(formatText(currentScenario.choice2Description + " [Not Met]"));
        }
        System.out.println();
        
      } else if (currentScenario.choiceCount == 1) {
        
        if (currentScenario.choice1StartConditionMet) {
          System.out.println(formatText(currentScenario.choice1Description));
        } else {
          System.out.println(formatText(currentScenario.choice1Description + "[Not Met]"));
        }
        System.out.println();
        
      } else System.out.println("Error with loaded scenario choice count.");
      
      System.out.print("Select choice: ");
      
      Scanner userInput = new Scanner(System.in);
      
      boolean choiceSelected = false;
      do {
        String input = userInput.nextLine();
        switch (input) {
          
          case "1":
            if (!currentScenario.choice1StartConditionMet) {
              System.out.print("You do not meet the requirements for choice 1. Please select a valid choice: ");
            } else {
              System.out.println();
              System.out.println(formatText(currentScenario.choice1Followup));
              currentScenario.choiceMadeWhenCompleted = 1;
              choiceSelected = true;
              System.out.println();
              if (currentScenario.choice1GoldConsequence > 0) System.out.println("(Gold +" + currentScenario.choice1GoldConsequence + ")");
              if (currentScenario.choice1GoldConsequence < 0) System.out.println("(Gold " + currentScenario.choice1GoldConsequence + ")");
              if (currentScenario.choice1FaithConsequence > 0) System.out.println("(Faith +" + currentScenario.choice1FaithConsequence + ")");
              if (currentScenario.choice1FaithConsequence < 0) System.out.println("(Faith " + currentScenario.choice1FaithConsequence + ")");
              if (currentScenario.choice1PopulationConsequence > 0) System.out.println("(Population +" + currentScenario.choice1PopulationConsequence + ")");
              if (currentScenario.choice1PopulationConsequence < 0) System.out.println("(Population " + currentScenario.choice1PopulationConsequence + ")");
              if (currentScenario.choice1TechConsequence > 0) System.out.println("(Tech +" + currentScenario.choice1TechConsequence + ")");
              if (currentScenario.choice1TechConsequence < 0) System.out.println("(Tech " + currentScenario.choice1TechConsequence + ")");
              System.out.println();
            }
            break;
          
          case "2":
            if (currentScenario.choiceCount < 2) {
              System.out.print("Choice 2 does not exist. Please select a valid choice: ");
            } else if (!currentScenario.choice2StartConditionMet) {
              System.out.print("You do not meet the requirements for choice 2. Please select a valid choice: ");
            } else {
              System.out.println();
              System.out.println(formatText(currentScenario.choice2Followup));
              currentScenario.choiceMadeWhenCompleted = 2;
              choiceSelected = true;
              System.out.println();
              if (currentScenario.choice2GoldConsequence > 0) System.out.println("(Gold +" + currentScenario.choice2GoldConsequence + ")");
              if (currentScenario.choice2GoldConsequence < 0) System.out.println("(Gold " + currentScenario.choice2GoldConsequence + ")");
              if (currentScenario.choice2FaithConsequence > 0) System.out.println("(Faith +" + currentScenario.choice2FaithConsequence + ")");
              if (currentScenario.choice2FaithConsequence < 0) System.out.println("(Faith " + currentScenario.choice2FaithConsequence + ")");
              if (currentScenario.choice2PopulationConsequence > 0) System.out.println("(Population +" + currentScenario.choice2PopulationConsequence + ")");
              if (currentScenario.choice2PopulationConsequence < 0) System.out.println("(Population " + currentScenario.choice2PopulationConsequence + ")");
              if (currentScenario.choice2TechConsequence > 0) System.out.println("(Tech +" + currentScenario.choice2TechConsequence + ")");
              if (currentScenario.choice2TechConsequence < 0) System.out.println("(Tech " + currentScenario.choice2TechConsequence + ")");
              System.out.println();
            }
            break;
          
          case "3":
            if (currentScenario.choiceCount < 3) {
              System.out.println("Choice 3 does not exist. Please select a valid choice: ");
            } else if (!currentScenario.choice3StartConditionMet) {
              System.out.print("You do not meet the requirements for choice 3. Please select a valid choice: ");
            } else {
              System.out.println();
              System.out.println(formatText(currentScenario.choice3Followup));
              currentScenario.choiceMadeWhenCompleted = 3;
              choiceSelected = true;
              System.out.println();
              if (currentScenario.choice3GoldConsequence > 0) System.out.println("(Gold +" + currentScenario.choice3GoldConsequence + ")");
              if (currentScenario.choice3GoldConsequence < 0) System.out.println("(Gold " + currentScenario.choice3GoldConsequence + ")");
              if (currentScenario.choice3FaithConsequence > 0) System.out.println("(Faith +" + currentScenario.choice3FaithConsequence + ")");
              if (currentScenario.choice3FaithConsequence < 0) System.out.println("(Faith " + currentScenario.choice3FaithConsequence + ")");
              if (currentScenario.choice3PopulationConsequence > 0) System.out.println("(Population +" + currentScenario.choice3PopulationConsequence + ")");
              if (currentScenario.choice3PopulationConsequence < 0) System.out.println("(Population " + currentScenario.choice3PopulationConsequence + ")");
              if (currentScenario.choice3TechConsequence > 0) System.out.println("(Tech +" + currentScenario.choice3TechConsequence + ")");
              if (currentScenario.choice3TechConsequence < 0) System.out.println("(Tech " + currentScenario.choice3TechConsequence + ")");
              System.out.println();
            }
            break;
          
          default:
            System.out.print(input + " is not a valid choice. Please select valid choice: ");
        }
      } while (!choiceSelected);
      
      
      
      
      //SAVING DATA
      DataEditor.changeDataValue("Day", DataEditor.getDataValue("Day", filePath) + 1, filePath);
      switch (currentScenario.choiceMadeWhenCompleted) {
        case 1:
          DataEditor.changeDataValue("Gold", DataEditor.getDataValue("Gold",filePath) + currentScenario.choice1GoldConsequence, filePath);
          DataEditor.changeDataValue("Faith", DataEditor.getDataValue("Faith",filePath) + currentScenario.choice1FaithConsequence, filePath);
          DataEditor.changeDataValue("Population", DataEditor.getDataValue("Population",filePath) + currentScenario.choice1PopulationConsequence, filePath);
          DataEditor.changeDataValue("Tech", DataEditor.getDataValue("Tech",filePath) + currentScenario.choice1TechConsequence, filePath);
          break;
        
        case 2:
          DataEditor.changeDataValue("Gold", DataEditor.getDataValue("Gold",filePath) + currentScenario.choice2GoldConsequence, filePath);
          DataEditor.changeDataValue("Faith", DataEditor.getDataValue("Faith",filePath) + currentScenario.choice2FaithConsequence, filePath);
          DataEditor.changeDataValue("Population", DataEditor.getDataValue("Population",filePath) + currentScenario.choice2PopulationConsequence, filePath);
          DataEditor.changeDataValue("Tech", DataEditor.getDataValue("Tech",filePath) + currentScenario.choice2TechConsequence, filePath);
          break;
        
        case 3:
          DataEditor.changeDataValue("Gold", DataEditor.getDataValue("Gold",filePath) + currentScenario.choice3GoldConsequence, filePath);
          DataEditor.changeDataValue("Faith", DataEditor.getDataValue("Faith",filePath) + currentScenario.choice3FaithConsequence, filePath);
          DataEditor.changeDataValue("Population", DataEditor.getDataValue("Population",filePath) + currentScenario.choice3PopulationConsequence, filePath);
          DataEditor.changeDataValue("Tech", DataEditor.getDataValue("Tech",filePath) + currentScenario.choice3TechConsequence, filePath);
          break;
      }
      
      
      DataEditor.addCompletedScenario(currentScenario.ID, currentScenario.choiceMadeWhenCompleted, filePath);
      
      if (DataEditor.getDataValue("Population", filePath) < 0) gameLost(filePath);
      if (DataEditor.getDataValue("Faith", filePath) > 100) DataEditor.changeDataValue("Faith", 100, filePath);
      if (DataEditor.getDataValue("Faith", filePath) < 0) DataEditor.changeDataValue("Faith", 0, filePath);
      if (DataEditor.getDataValue("Gold", filePath) < 0) {
        System.out.println("Error: Gold is negative.");
        System.exit(0);
      }
      
      
      if (DataEditor.getCompletedScenarioCount(filePath) >= 100000) {
        completedPart = true;
      }
      System.out.println("===================================================================================================");
      
    } while(!completedPart);
    
    System.out.println("Congratulations! You've completed the game!");
    System.exit(0);
  }
  }
