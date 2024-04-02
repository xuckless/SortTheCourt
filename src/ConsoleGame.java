

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

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
    int lineWidth = 200;
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
  
  public static void StartGame(String filePath, String scenarioTxtFilePath) {
    
    File gameSave = new File(filePath);
    if (!gameSave.exists())
      loadDefaultSave(filePath, "FirstStart:0\nDay:1\nGold:150\nFaith:40\nTech:1\nPopulation:150");
    
    if (DataEditor.getDataValue("FirstStart", filePath) == 0) {
      GameScreenOne.setMessageOne("Welcome to the game!");
      JButton beginGame = GameScreenOne.createChoiceButton("Begin Game");
      GameScreenOne.addChoiceButton(beginGame, a -> {
        GameScreenOne.clearChoiceButtons();
        GameScreenOne.setMessageOne(formatText("You awake to an abyss of nothingness all around. 'Ah, little light,' a voice called, but you could not see the speaker. 'Do not worry, little light, your reaction is normal. We're going to try the plan again. Just do not forget your task: Keep the reality on the desired path. Keep the kingdom from collapse. Are you ready to continue?'"));
        JButton yes = GameScreenOne.createChoiceButton("Yes");
        GameScreenOne.addChoiceButton(yes, b -> {
          GameScreenOne.clearChoiceButtons();
          ConsoleGame.StartPart1(filePath, scenarioTxtFilePath, true);
        });
        JButton no = GameScreenOne.createChoiceButton("No");
        GameScreenOne.addChoiceButton(no, c -> {
          GameScreenOne.clearChoiceButtons();
          System.exit(0);
        });
      });
    } else {
      GameScreenOne.setMessageOne("Welcome back to the game! Choose to continue, or start a new game.");
      JButton continueGame = GameScreenOne.createChoiceButton("Continue Game");
      GameScreenOne.addChoiceButton(continueGame, e -> {
        GameScreenOne.clearChoiceButtons();
        ConsoleGame.StartPart1(filePath, scenarioTxtFilePath, false);
      });
      JButton newGame = GameScreenOne.createChoiceButton("New Game");
      GameScreenOne.addChoiceButton(newGame, e -> {
        GameScreenOne.clearChoiceButtons();
        loadDefaultSave(filePath, "FirstStart:0\nDay:1\nGold:150\nFaith:40\nTech:1\nPopulation:150");
        ConsoleGame.StartPart1(filePath, scenarioTxtFilePath, true);
      });
    }
  }
  
  public static void gameLost(String filePath){
    JButton continueGame = GameScreenOne.createChoiceButton("Start a New Journey!");
    GameScreenOne.setMessageOne(formatText("'Ah, little light,' the somewhat familiar voice called. 'You're back. You must have strayed from the the desired path,' the voice added. You knew this person, didn't you? For whatever reason, you could not see them. 'Do not worry, little light, your reaction is normal. We're going to try the plan again. Just do not forget your task: Keep the reality on the desired path. Keep the kingdom from collapse. Are you ready to get back to work?"));
    GameScreenOne.addChoiceButton(continueGame, e -> {
      GameScreenOne.clearChoiceButtons();
      loadDefaultSave(filePath, "FirstStart:1\nDay:1\nGold:150\nFaith:40\nTech:1\nPopulation:150");
    });
  }
  
  public static void StartPart1(String filepath, String scenarioTxtFilePath, boolean runIntroScene){
    boolean completedPart = false;
    
    if (runIntroScene) {
      GameScreenOne.setMessageOne(formatText("You suddenly find yourself in a body. It was strange, this feeling, like it had been some time since your soul had... well, a physical form to manifest itself in. You could feel the droplets of water on your skin, soaking into your clothes. You could hear the soft pattering of rain drops hitting stone and wood. But those senses paled in comparison to your sight. You could see a castle towering in the distance. You could see the sombre, clouded sky above. You could see the crows flying overhead. And before you, you could see a group of important-looking people, all wearing dark clothes fit for mourning, gathering around a wooden coffin that was being carefully lowered into a hole in the ground. This was a funeral, you realized, but for who? A man to your left suddenly looked over at you expectantly. At the sight of him, a memory seemed to call out from your new body: Alistair Kyntha, Hand of the King. The memory was likely a residual from the soul that had occupied this body before you. 'I'm sorry, young prin... Your Grace.' The Hand hesitated on using the royal title, as though he had never referred to you with it before. 'Your father was a good man, and a good king. I'm sorry.'"));
      
      //GameScreenOne.appendMessageTwo("\n\tSelect choice: ");
      JButton choice1 = GameScreenOne.createChoiceButton("For what? It wasn't your fault he died.");
      JButton choice2 = GameScreenOne.createChoiceButton("I just feel... empty, I guess.");
      JButton yes = GameScreenOne.createChoiceButton("Yes.");
      
      GameScreenOne.addChoiceButton(choice1, e -> {
        GameScreenOne.clearChoiceButtons();
        GameScreenOne.setMessageOne(formatText("'No, but...,' The Hand started. 'I suppose I just wish we could have prepared you a little more for the crown. Together, I mean.' You nodded in understanding. You didn't know your father, but... there were fragments. Images that formed in your mind. Memories not your own. All of them spoke to your father being a good man. After a moment spent silently watching the birds overhead, The Hand spoke up. 'I know you're still in mourning, Your Grace, but the crown has passed to you now, and the kingdom needs a ruler. It's a small one, I know, but it still needs daily management or it will all crumble. Are you ready to get to work?'"));
        GameScreenOne.addChoiceButton(yes, i -> {
          GameScreenOne.clearChoiceButtons();
          // Use SwingWorker to run long-running task outside the EDT
          SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
              // Long-running task here; for example:
              DataEditor.changeDataValue("FirstStart", 1, filepath);
              ConsoleGame.nextPart(filepath, scenarioTxtFilePath);
              return null;
            }
            
            @Override
            protected void done() {
              // Any GUI update after the background task; for example:
              // GameScreenOne.setMessageOne("Ready for the next part!");
            }
          };
          worker.execute(); // Execute the SwingWorker
        });
      });
      GameScreenOne.addChoiceButton(choice2, e -> {
        GameScreenOne.clearChoiceButtons();
        GameScreenOne.setMessageOne(formatText("'I understand, Your Grace,' The Hand said with a sigh. 'I know you're still in mourning, Your Grace, but the crown has passed to you now, and the kingdom needs a ruler. It's a small one, I know, but it still needs daily management or it will all crumble. Are you ready to get to work?'"));
        GameScreenOne.addChoiceButton(yes, i -> {
          GameScreenOne.clearChoiceButtons();
          // Use SwingWorker to run long-running task outside the EDT
          SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
              // Long-running task here; for example:
              DataEditor.changeDataValue("FirstStart", 1, filepath);
              ConsoleGame.nextPart(filepath, scenarioTxtFilePath);
              return null;
            }
            
            @Override
            protected void done() {
              // Any GUI update after the background task; for example:
              // GameScreenOne.setMessageOne("Ready for the next part!");
            }
          };
          worker.execute(); // Execute the SwingWorker
        });
      });
    } else {
      GameScreenOne.setMessageOne(formatText("Waking up from a deep, dreamless sleep, you find yourself in your room. Everything about it spoke royalty, from the burgundy wood and the gold that adorned the walls, to the luxurious, velvet drapes that framed the large windows. It was nice, this place. It was safe, and free from the drama of ruling a kingdom. Still, you had to get back to work. You had to 'Keep the kingdom on the desired path.' At that thought, you frowned, realising that you had forgotten where you had heard that phrase. Either way, there was work to do."));
      JButton getOutOfBed = GameScreenOne.createChoiceButton("Get out of bed");
      GameScreenOne.addChoiceButton(getOutOfBed, e -> {
        GameScreenOne.clearButton(getOutOfBed);
        ConsoleGame.nextPart(filepath, scenarioTxtFilePath);
      });
    }
  }
  

  public static void nextPart(String filePath, String scenarioTxtFilePath){
    do {
      Scenario currentScenario = SortTheCourtButEpicVersion.loadScenario(filePath, scenarioTxtFilePath);
      
      if (currentScenario == null) {
        System.out.println("All scenarios completed.");
        System.exit(0);
      }
      GameScreenOne.appendImageInMessageOne(String.valueOf(currentScenario.character));
      
      DataEditor.changeDataValue("Gold", DataEditor.getDataValue("Gold", filePath) + currentScenario.goldConsequence, filePath);
      DataEditor.changeDataValue("Faith", DataEditor.getDataValue("Faith", filePath) + currentScenario.faithConsequence, filePath);
      DataEditor.changeDataValue("Population", DataEditor.getDataValue("Population", filePath) + currentScenario.populationConsequence, filePath);
      DataEditor.changeDataValue("Tech", DataEditor.getDataValue("Tech", filePath) + currentScenario.techConsequence, filePath);
      
      if (DataEditor.getDataValue("Population", filePath) < 0) gameLost(filePath);
      
      StringBuilder resources = new StringBuilder();
      resources.append("Scenario Number: " + DataEditor.getDataValue("Day", filePath));
      resources.append("\n\nGold: " + DataEditor.getDataValue("Gold", filePath));
      resources.append("\nFaith: " + DataEditor.getDataValue("Faith", filePath));
      resources.append("\nPopulation: " + DataEditor.getDataValue("Population", filePath));
      resources.append("\nTech: " + DataEditor.getDataValue("Tech", filePath));
      resources.append("\n\nAudience: " + currentScenario.character);
      GameScreenOne.setMessageTwo(String.valueOf(resources));
      
      StringBuilder description = new StringBuilder(currentScenario.scenarioDescription);
      if (currentScenario.goldConsequence > 0) description.append("(Gold +" + currentScenario.goldConsequence + ")");
      if (currentScenario.goldConsequence < 0) description.append("(Gold " + currentScenario.goldConsequence + ")");
      if (currentScenario.populationConsequence > 0) description.append("(Population +" + currentScenario.populationConsequence + ")");
      if (currentScenario.populationConsequence < 0) description.append("(Population " + currentScenario.populationConsequence + ")");
      if (currentScenario.faithConsequence > 0) description.append("(Faith +" + currentScenario.faithConsequence + ")");
      if (currentScenario.faithConsequence < 0) description.append("(Faith " + currentScenario.faithConsequence + ")");
      if (currentScenario.techConsequence > 0) description.append("(Tech +" + currentScenario.techConsequence + ")");
      if (currentScenario.techConsequence < 0) description.append("(Tech " + currentScenario.techConsequence + ")");
      GameScreenOne.setMessageOne(formatText(String.valueOf(description)));
      
      
      AtomicBoolean choiceMade = new AtomicBoolean(false);
      
      GameScreenOne.clearChoiceButtons(); // Clear any previous choices
      
      if (currentScenario.choiceCount == 3) {
        if (currentScenario.choice1StartConditionMet) {
          JButton choice1 = GameScreenOne.createChoiceButton(currentScenario.choice1Description);
          GameScreenOne.addChoiceButton(choice1, e -> {
            GameScreenOne.clearChoiceButtons();
            GameScreenOne.setMessageOne(formatText(currentScenario.choice1Followup));
            currentScenario.choiceMadeWhenCompleted = 1;
            choiceMade.set(true); // Set the flag to true when a choice is made.
          });
        }
        if (currentScenario.choice2StartConditionMet) {
          JButton choice2 = GameScreenOne.createChoiceButton(currentScenario.choice2Description);
          GameScreenOne.addChoiceButton(choice2, e -> {
            GameScreenOne.clearChoiceButtons();
            GameScreenOne.setMessageOne(formatText(currentScenario.choice2Followup));
            currentScenario.choiceMadeWhenCompleted = 2;
            choiceMade.set(true); // Set the flag to true when a choice is made.
          });
        }
        if (currentScenario.choice3StartConditionMet) {
          JButton choice3 = GameScreenOne.createChoiceButton(currentScenario.choice3Description);
          GameScreenOne.addChoiceButton(choice3, e -> {
            GameScreenOne.clearChoiceButtons();
            GameScreenOne.setMessageOne(formatText(currentScenario.choice3Followup));
            currentScenario.choiceMadeWhenCompleted = 3;
            choiceMade.set(true); // Set the flag to true when a choice is made.
          });
        }
      } else if (currentScenario.choiceCount == 2) {
        if (currentScenario.choice1StartConditionMet) {
          JButton choice1 = GameScreenOne.createChoiceButton(currentScenario.choice1Description);
          GameScreenOne.addChoiceButton(choice1, e -> {
            GameScreenOne.clearChoiceButtons();
            GameScreenOne.setMessageOne(formatText(currentScenario.choice1Followup));
            currentScenario.choiceMadeWhenCompleted = 1;
            choiceMade.set(true); // Set the flag to true when a choice is made.
          });
        }
        if (currentScenario.choice2StartConditionMet) {
          JButton choice2 = GameScreenOne.createChoiceButton(currentScenario.choice2Description);
          GameScreenOne.addChoiceButton(choice2, e -> {
            GameScreenOne.clearChoiceButtons();
            GameScreenOne.setMessageOne(formatText(currentScenario.choice2Followup));
            currentScenario.choiceMadeWhenCompleted = 2;
            choiceMade.set(true); // Set the flag to true when a choice is made.
          });
        }
      } else if (currentScenario.choiceCount == 1) {
        if (currentScenario.choice1StartConditionMet) {
          JButton choice1 = GameScreenOne.createChoiceButton(currentScenario.choice1Description);
          GameScreenOne.addChoiceButton(choice1, e -> {
            GameScreenOne.clearChoiceButtons();
            GameScreenOne.setMessageOne(formatText(currentScenario.choice1Followup));
            currentScenario.choiceMadeWhenCompleted = 1;
            choiceMade.set(true); // Set the flag to true when a choice is made.
          });
        }
      } else {
        System.out.println("Error with loaded scenario choice count.");
      }

// Wait for a choice to be made
      while (!choiceMade.get()) {
        try {
          Thread.sleep(100); // Brief sleep to reduce CPU usage
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          System.out.println("Interrupted while waiting for the user's choice.");
          break;
        }
      }
      
      //String currentScenarioCharacter = currentScenario.character;
      
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
      
      StringBuilder resources2 = new StringBuilder();
      resources2.append("Scenario Number: " + (DataEditor.getDataValue("Day", filePath) - 1));
      resources2.append("\n\nGold: " + DataEditor.getDataValue("Gold", filePath));
      resources2.append("\nFaith: " + DataEditor.getDataValue("Faith", filePath));
      resources2.append("\nPopulation: " + DataEditor.getDataValue("Population", filePath));
      resources2.append("\nTech: " + DataEditor.getDataValue("Tech", filePath));
      resources2.append("\n\nAudience: " + currentScenario.character);
      GameScreenOne.setMessageTwo(String.valueOf(resources2));
      GameScreenOne.appendImageInMessageOne(String.valueOf(currentScenario.character));
      
      AtomicBoolean continueToNext = new AtomicBoolean(false);
      
      JButton continueButton = GameScreenOne.createChoiceButton("Continue");
      GameScreenOne.addChoiceButton(continueButton, e -> {
        GameScreenOne.clearChoiceButtons();
        continueToNext.set(true); // Set the flag to true when a choice is made.
      });
      
      while (!continueToNext.get()) {
        try {
          Thread.sleep(100); // Brief sleep to reduce CPU usage
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          System.out.println("Interrupted while waiting for the user's choice.");
          break;
        }
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
      
    } while(!completedPart);
    
    System.out.println("Congratulations! You've completed the game!");
    System.exit(0);
  }
}

