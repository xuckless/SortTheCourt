import javax.swing.*;
import java.awt.*;

/**
 * MainMenu represents the main menu screen of the game.
 * It extends JPanel and is designed to offer options like starting the game.
 */
public class MainMenu extends JPanel {
  private JTextArea textArea;
  private JLabel startLabel;
  private JButton startButton;
  
  // No params in the constructor
  
  /**
   * Initializes the main menu screen with UI components.
   */
  public MainMenu(){
    this.packageScreen();
  }
  
  private void packageScreen(){
    this.setLayout(new GridBagLayout());
    this.setBackground(Color.WHITE);
    this.setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));
    this.setOpaque(true);
    this.setVisible(true);
    this.setDoubleBuffered(true);
    
    this.addStartLabel();
    this.addStartButton();
    this.addTextArea();
    
    revalidate();
    repaint();
  }
  
  private void addStartButton() {
    this.startButton = new JButton("Start");
    
    startButton.setOpaque(true);
    startButton.setContentAreaFilled(true);
    startButton.setBorderPainted(true);
    
    this.startButton.addActionListener(e -> {
      Container parent = this.getParent();
      while (parent != null && !(parent instanceof GamePanel)) {
        parent = parent.getParent();
      }
      if (parent instanceof GamePanel) {
        ((GamePanel) parent).switchPanels("GameScreenOne");
      }
    });
    
    this.add(startButton, createGridBagConstraints(0,1,1,
      1, GridBagConstraints.NONE,1,1,
      new Insets(5,5,5,5)));
  }
  
  private void addStartLabel(){
    startLabel = new JLabel("Start Menu", JLabel.CENTER);
    this.add(startLabel, createGridBagConstraints(0,0,2,
      1, GridBagConstraints.CENTER,1,1,
      new Insets(10,0,10,0)));
    
  }
  
  private void addTextArea(){
    textArea = new JTextArea();
    textArea.setEditable(false);
    textArea.setBackground(Color.LIGHT_GRAY);
    textArea.setForeground(Color.BLACK);
    
    this.add(textArea, createGridBagConstraints(0,2,2,
      1, GridBagConstraints.BOTH, 1,1,
      new Insets(5,5,5,5)));
  }
  
  public void updateTextArea(String str){
    this.textArea.append(str);
  }
  
  public void setTextArea(String str){
    this.textArea.setText(str);
  }
  
  private GridBagConstraints createGridBagConstraints(int gridx, int gridy, int gridwidth,
                                                      int gridheight, int fill, double weightx,
                                                      double weighty, Insets insets) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = gridx;
    gbc.gridy = gridy;
    gbc.gridwidth = gridwidth;
    gbc.gridheight = gridheight;
    gbc.fill = fill;
    gbc.weightx = weightx;
    gbc.weighty = weighty;
    gbc.insets = insets;
    return gbc;
  }
  
}
