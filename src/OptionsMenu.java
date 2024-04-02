import javax.swing.*;
import java.awt.*;

public class OptionsMenu extends JPanel {
  private JButton resumeButton;
  private JButton exitButton;
  private JButton mainMenuButton;
  
  public OptionsMenu(){
    this.packageScreen();
  }
  
  private void packageScreen(){
    this.setLayout(new GridBagLayout());
    this.setBackground(Color.RED);
    this.setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));
    this.setOpaque(true);
    this.setVisible(true);
    this.setDoubleBuffered(true);
    
    this.addResumeButton();
    this.addMainMenuButton();
    this.addExitGameButton();
  }
  
  private void addMainMenuButton(){
    this.mainMenuButton = new JButton("Return To Main Menu");
    
    this.mainMenuButton.addActionListener(e -> {
      Container parent = this.getParent();
      while (parent != null && !(parent instanceof GamePanel)) {
        parent = parent.getParent();
      }
      if (parent instanceof GamePanel) {
        ((GamePanel) parent).switchPanels("MainMenu");
      }
    });
    
    this.add(this.mainMenuButton, createGridBagConstraints(1, 1,
      1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER,
      0, 0, new Insets(5, 5, 5, 5)));
  }
  
  private void addResumeButton(){
    this.resumeButton = new JButton("Resume Game");
    
    this.resumeButton.addActionListener(e -> {
      Container parent = this.getParent();
      while (parent != null && !(parent instanceof GamePanel)) {
        parent = parent.getParent();
      }
      if (parent instanceof GamePanel) {
        ((GamePanel) parent).switchPanels("GameScreenOne");
      }
    });
    
    this.add(this.resumeButton, createGridBagConstraints(1, 2,
      1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER,
      0, 0, new Insets(5, 5, 5, 5)));
  }
  
  private void addExitGameButton(){
    this.exitButton = new JButton("Exit Game");
    
    this.exitButton.addActionListener(e -> System.exit(0));
    
    this.add(this.exitButton, createGridBagConstraints(1, 3,
      1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER,
      0, 0, new Insets(5, 5, 5, 5)));
  }
  
  
  private GridBagConstraints createGridBagConstraints(int gridx, int gridy,
                                                      int gridwidth, int gridheight,
                                                      int fill, int anchor,
                                                      double weightx, double weighty,
                                                      Insets insets) {
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = gridx; // X position
    gbc.gridy = gridy; // Y position
    gbc.gridwidth = gridwidth; // Number of columns to span
    gbc.gridheight = gridheight; // Number of rows to span
    gbc.fill = fill; // Fill behavior
    gbc.anchor = anchor; // Anchoring behavior
    gbc.weightx = weightx; // How to distribute extra horizontal space
    gbc.weighty = weighty; // How to distribute extra vertical space
    gbc.insets = insets; // External padding
    return gbc;
  }
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (GamePanel.backgroundImage != null) {
      g.drawImage(GamePanel.backgroundImage, 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT, this);
    }
  }
}
