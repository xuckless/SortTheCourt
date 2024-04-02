import javax.swing.*;
import java.awt.*;


/**
 * GamePanel serves as the primary panel where different game screens are displayed.
 * It extends JPanel and implements Runnable for threading capabilities.
 * This class manages the game loop and screen transitions.
 */
public class GamePanel extends JPanel implements Runnable {
  
  static final int ORIGINAL_TILE_SIZE = 28;
  static final int SCALE = 3; // 28 * 3 = 84
  static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 84 * 84
  static final int MAX_SCREEN_COL = 16;
  static final int MAX_SCREEN_ROW = 9;
  static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 84 * 16 = 1344
  static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 84 * 9 = 756
  
  public static Thread gameThread;
  JTextArea textArea;
  GameScreenOne gameScreenOne;
  MainMenu mainMenu;
  OptionsMenu optionsMenu;
  CardLayout cardLayout = new CardLayout();
  JPanel cards = new JPanel(cardLayout);
  final int FPS = 60;
  
  // No params in the constructor
  /**
   * Initializes the game panel by loading screens and setting up the layout.
   */
  public GamePanel(){
    this.addScreenImports();
    this.packageScreen();
  }
  
  /**
   * Initializes other screen panels.
   * Add any other screens to this function,
   */
  private void addScreenImports(){
    mainMenu = new MainMenu();
    gameScreenOne = new GameScreenOne();
    optionsMenu = new OptionsMenu();
  }
  
  /**
   * Refactors the main screen.
   * Add any other screen to the cards.add() argument.
   */
  private void packageScreen(){
    cards.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
    cards.add(mainMenu, "MainMenu");
    cards.add(gameScreenOne, "GameScreenOne");
    cards.add(optionsMenu, "OptionsMenu");
    
    this.setLayout(new BorderLayout());
    this.add(cards, BorderLayout.CENTER);
    
    this.setBackground(Color.BLACK);
    this.setDoubleBuffered(true);
    
//    CustomMouseListener listener = new CustomMouseListener(textArea);
//    this.addMouseListener(listener);
  }
  
  /**
   * Starts the game thread to begin the game loop.
   */
  public void startGameThread(){
    gameThread = new Thread(this);
    gameThread.start();
  }
  
  /**
   * The main game loop that updates the game state and repaints the panel.
   */
  public void run(){
    double drawInterval = (double) 1000 / FPS;
    double nextDrawTime = System.currentTimeMillis() + drawInterval;
    long timer = 0;
    int drawCount = 0;
    long startTime = System.currentTimeMillis();
    int frames = 0;
    
    
    SortTheCourtButEpicVersion.gameLogicCall();
//    try {
//      Tester.Main();
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
    
    while (gameThread != null){
      update();
      repaint();
      revalidate();
      
      frames++;
      
      if (System.currentTimeMillis() - startTime >= 1000) {
        mainMenu.setTextArea("FPS: " + frames); // Print the FPS
        frames = 0; // Reset frame count for the next second
        startTime = System.currentTimeMillis(); // Reset the start time for the next second
      }
      
      try {
        
        double remainingTime = nextDrawTime - System.currentTimeMillis();
        
        if (remainingTime < 0) {
          remainingTime = 0;
        }
        
        Thread.sleep((long) remainingTime);
        nextDrawTime += drawInterval;
        
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
  public void update(){
    GameScreenOne.consoleGameExtension.repaint();
    GameScreenOne.consoleGameExtension.revalidate();
    GameScreenOne.textBoxTwo.repaint();
    GameScreenOne.textBoxTwo.revalidate();
  }
  
  /**
   * Switches the visible panel based on the specified name.
   * @param cardName The name of the panel to display.
   */
  public void switchPanels(String cardName){
    cardLayout.show(cards, cardName);
  }
}