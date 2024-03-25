import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class ConsoleGame2D {
  
  private GameScreenOne gameScreenOne;
  
  public ConsoleGame2D(GameScreenOne gameScreenOne) {
    this.gameScreenOne = gameScreenOne;
  }
  
  private void loadDefaultSave(String filePath, String defaultSaveContents) {
    try (FileWriter writer = new FileWriter(new File(filePath))) {
      writer.write(defaultSaveContents);
      gameScreenOne.setMessageOne("Old save deleted. Starting a new game.");
    } catch (IOException e) {
      gameScreenOne.setMessageOne("Failed to initialize new game.");
      throw new RuntimeException(e);
    }
  }
  
  public void startGame(String filePath, String scenarioTxtFilePath) {
    File gameSave = new File(filePath);
    if (!gameSave.exists()) {
      loadDefaultSave(filePath, "FirstStart:0\nDay:1\nGold:150\nFaith:40\nTech:1\nPopulation:150");
      gameScreenOne.setMessageOne("Welcome to the game! Choose 'Start New Game' to begin.");
    } else {
      gameScreenOne.setMessageOne("Welcome back! Choose 'Continue' to keep playing, or 'Start New Game' to begin anew.");
    }
    
    setupInitialChoices(filePath, scenarioTxtFilePath);
  }
  
  private void setupInitialChoices(String filePath, String scenarioTxtFilePath) {
    gameScreenOne.clearChoiceButtons();
    
    gameScreenOne.addChoiceButton("Continue", e -> startOrContinueGame(filePath, scenarioTxtFilePath, true));
    
    gameScreenOne.addChoiceButton("Start New Game", e -> startOrContinueGame(filePath, scenarioTxtFilePath, false));
  }
  
  private void startOrContinueGame(String filePath, String scenarioTxtFilePath, boolean continueGame) {
    if (!continueGame) {
      loadDefaultSave(filePath, "FirstStart:0\nDay:1\nGold:150\nFaith:40\nTech:1\nPopulation:150");
      gameScreenOne.setMessageTwo("New game initialized.");
    } else {
      gameScreenOne.setMessageTwo("Continuing game...");
      // Here, you would load the game state and proceed accordingly
    }
    
    // Further game logic for loading scenarios and handling game progression
    // would be implemented here, possibly utilizing other methods in GameScreenOne
  }
  
  // Other methods, such as gameLost, startPart1, etc., need to be adapted
  // to remove console I/O and instead use GameScreenOne for graphical interaction.
}
