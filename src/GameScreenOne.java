import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;


// there are some problems in this code where the text boxes arent updating as they should be
// I have created a template of input that is working.

public class GameScreenOne extends JPanel {
  private JLabel startLabel;
  private JButton optionsButton;
  
  protected static JTextArea consoleGameExtension;
  
  protected static JTextArea textBoxTwo;
  OptionsMenu optionsMenu;
  
  
  /**
   * GameScreenOne represents a specific game screen within the application.
   * It extends JPanel and contains elements like buttons and text areas for game interaction.
   */
  static ChoicesPanel choicesPanel;
  
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
    consoleGameExtension.setEditable(false);
    this.add(consoleGameExtension, createGridBagConstraints(0, 2, 2,
      1, GridBagConstraints.BOTH, GridBagConstraints.CENTER,1,
      0.8, new Insets(5, 5, 35, 5)));
    //0.8
  }
  
  
//  GridBagConstraints.REMAINDER
  
  private void addTextBoxTwo(){
    textBoxTwo = new JTextArea();
    textBoxTwo.setEditable(false);
    
    this.add(textBoxTwo, createGridBagConstraints(0, 3, GridBagConstraints.REMAINDER,
      1, GridBagConstraints.BOTH, GridBagConstraints.SOUTH,2,
      3, new Insets(5, 5, 50, 5)));
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
  public static void setMessageOne(String extension){
    SwingUtilities.invokeLater(() -> {
      consoleGameExtension.setText(extension);
      consoleGameExtension.repaint();
      consoleGameExtension.revalidate();
    });
  }
  
  /**
   * Appends a message to the first text area.
   * @param extension The message to append.
   */
  public static void appendMessageOne(String extension){
    SwingUtilities.invokeLater(() -> {
      consoleGameExtension.append(extension);
      consoleGameExtension.repaint();
      consoleGameExtension.revalidate();
    });
  }
  
  /**
   * Sets a message in the second text area.
   * @param extension The message to display.
   */
  public static void setMessageTwo(String extension){
    SwingUtilities.invokeLater(() -> {
      textBoxTwo.setText(extension);
      textBoxTwo.repaint();
      textBoxTwo.revalidate();
    });
  }
  
  /**
   * Appends a message to the second text area.
   * @param extension The message to append.
   */
  public static void appendMessageTwo(String extension){
    SwingUtilities.invokeLater(() -> {
      textBoxTwo.append(extension);
      textBoxTwo.repaint();
      textBoxTwo.revalidate();
    });
  }
  
  
  /**
   * Adds a choice button to the panel with a specific action.
   * @param buttonText The text of the button.
   */
  public static JButton createChoiceButton(String buttonText) {
    JButton button = new JButton(buttonText);
    return button;
  }
  
  public static void addChoiceButton(JButton button, ActionListener actionListener){
    button.addActionListener(actionListener);
    choicesPanel.add(button);
    choicesPanel.revalidate();
    choicesPanel.repaint();
  }
  
  /**
   * Clears all choice buttons from the panel.
   */
  public static void clearChoiceButtons() {
    choicesPanel.removeAll();
    choicesPanel.revalidate();
    choicesPanel.repaint();
  }
  
  public static void clearButton(JButton button) {
    choicesPanel.remove(button);
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
  
  private static GridBagConstraints createGridBagConstraints2(int gridx, int gridy,
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
  
  private static void refreshScreen(){
  }
}
