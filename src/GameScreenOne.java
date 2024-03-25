import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameScreenOne extends JPanel {
  private JLabel startLabel;
  private JButton optionsButton;
  
  private JTextArea consoleGameExtension;
  
  private JTextArea textBoxTwo;
  OptionsMenu optionsMenu;
  
  /**
   * GameScreenOne represents a specific game screen within the application.
   * It extends JPanel and contains elements like buttons and text areas for game interaction.
   */
  ChoicesPanel choicesPanel;
  
  public GameScreenOne(){
    
    optionsMenu = new OptionsMenu();
    
    
    this.setLayout(new GridBagLayout());
    this.setBackground(Color.GREEN);
    this.setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));
    this.setOpaque(true);
    this.setVisible(true);
    this.setDoubleBuffered(true);
    
    
    this.addOptionsButton();
    this.addStartLabel();
    this.addTextField();
    this.addTextBoxTwo();
    this.addChoicesPanel();
    
    
    this.revalidate();
    this.repaint();
  }
  
  
  private void addOptionsButton(){
    this.optionsButton = new JButton("Options");
    
    this.optionsButton.addActionListener(e -> {
      Container parent = this.getParent();
      while (parent != null && !(parent instanceof GamePanel)) {
        parent = parent.getParent();
      }
      if (parent instanceof GamePanel) {
        ((GamePanel) parent).switchPanels("OptionsMenu");
      }
    });
    
    this.add(this.optionsButton, createGridBagConstraints(0, 0,
      1, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST,
      0, 0, new Insets(5, 5, 0, 0)));
  }
  
 
  
  private void addStartLabel(){
    startLabel = new JLabel("Game Screen 1", JLabel.CENTER);
    this.add(startLabel, createGridBagConstraints(0, 1, GridBagConstraints.REMAINDER,
      1, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH,0,
      0, new Insets(5, 5, 50, 0)));
  }
  
  private void addTextField(){
    consoleGameExtension = new JTextArea();
    consoleGameExtension.setText("Text area 1");
    consoleGameExtension.setEditable(false);
    this.add(consoleGameExtension, createGridBagConstraints(0, 2, 2,
      1, GridBagConstraints.BOTH, GridBagConstraints.CENTER,1,
      0.8, new Insets(5, 5, 35, 5)));
    //0.8
  }
  
  
//  GridBagConstraints.REMAINDER
  
  private void addTextBoxTwo(){
    textBoxTwo = new JTextArea();
    textBoxTwo.setText("Text area 2");
    textBoxTwo.setEditable(false);
    this.add(textBoxTwo, createGridBagConstraints(0, 3, GridBagConstraints.REMAINDER,
      1, GridBagConstraints.BOTH, GridBagConstraints.SOUTH,2,
      3, new Insets(5, 5, 5, 5)));
    //3
  }
  
  private void addChoicesPanel(){
    choicesPanel = new ChoicesPanel();
    
    
    
    this.add(choicesPanel, createGridBagConstraints(0, 4, 1,
     1, GridBagConstraints.BOTH, GridBagConstraints.CENTER,1,
      0.8, new Insets(5, 5, 5, 5)));
    
    this.revalidate();
    this.repaint();
  }
  
  /**
   * Sets a message in the first text area.
   * @param extension The message to display.
   */
  public void setMessageOne(String extension){
    this.consoleGameExtension.setText(extension);
  }
  
  /**
   * Appends a message to the first text area.
   * @param extension The message to append.
   */
  public void appendMessageOne(String extension){
    this.consoleGameExtension.append(extension);
  }
  
  /**
   * Sets a message in the second text area.
   * @param extension The message to display.
   */
  public void setMessageTwo(String extension){
    this.textBoxTwo.setText(extension);
  }
  
  /**
   * Appends a message to the second text area.
   * @param extension The message to append.
   */
  public void appendMessageTwo(String extension){
    this.textBoxTwo.append(extension);
  }
  
  /**
   * Adds a choice button to the panel with a specific action.
   * @param buttonText The text of the button.
   * @param actionListener The action to perform when the button is clicked.
   */
  public void addChoiceButton(String buttonText, ActionListener actionListener) {
    JButton button = new JButton(buttonText);
    button.addActionListener(actionListener);
    choicesPanel.add(button, createGridBagConstraints(0, GridBagConstraints.RELATIVE, GridBagConstraints.REMAINDER,
      GridBagConstraints.REMAINDER, GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER,1,
      1, new Insets(5, 5, 5, 5)));
    choicesPanel.revalidate();
    choicesPanel.repaint();
  }
  
  /**
   * Clears all choice buttons from the panel.
   */
  public void clearChoiceButtons() {
    choicesPanel.removeAll();
    choicesPanel.revalidate();
    choicesPanel.repaint();
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
}
