import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;


public class GameScreenOne extends JPanel {
  private JLabel startLabel;
  private JButton optionsButton;
  protected static JTextPane consoleGameExtension;
  protected static JTextPane textBoxTwo;
  OptionsMenu optionsMenu;
  
  
  /**
   * game screen is the playable area of the game.
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
  
  /**
   * private class that adds options menu button on top left of the screen
   * has action listener to switch panels to options menu when clicked
   */
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
  
  /**
   * creates a new label on top of the game screen
   */
  private void addStartLabel(){
    startLabel = new JLabel("Sort The Court", JLabel.CENTER);
    this.add(startLabel, createGridBagConstraints(0, 1, GridBagConstraints.REMAINDER,
      1, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH,0,
      0, new Insets(5, 5, 50, 0)));
  }
  
  private void addTextField(){
    consoleGameExtension = new JTextPane();
    consoleGameExtension.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(consoleGameExtension);
    this.add(scrollPane, createGridBagConstraints(0, 2, 2,
      1, GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1,
      3, new Insets(5, 5, 35, 5)));
  }
  
  
//  GridBagConstraints.REMAINDER
  
  private void addTextBoxTwo(){
    textBoxTwo = new JTextPane();
    textBoxTwo.setEditable(false);
    JScrollPane scrollPaneTwo = new JScrollPane(textBoxTwo);
    this.add(scrollPaneTwo, createGridBagConstraints(0, 3, GridBagConstraints.REMAINDER,
      1, GridBagConstraints.BOTH, GridBagConstraints.SOUTH, 2,
      0.8, new Insets(5, 5, 50, 5)));
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
   * Clears the existing message and displays a new message in the first text area;
   * @param extension The message to display.
   */
  public static void setMessageOne(String extension){
    SwingUtilities.invokeLater(() -> {
      try {
        StyledDocument doc = consoleGameExtension.getStyledDocument();
        doc.remove(0, doc.getLength());
        // Insert new text
        doc.insertString(0, extension, null);
      } catch (BadLocationException e) {
        e.printStackTrace();
      }
    });
  }
  
  /**
   * Appends a message to the first text area.
   * @param extension The message to append.
   */
  public static void appendMessageOne(String extension){
    SwingUtilities.invokeLater(() -> {
      try {
        StyledDocument doc = consoleGameExtension.getStyledDocument();
        doc.insertString(doc.getLength(), extension, null);
      } catch (BadLocationException e) {
        e.printStackTrace();
      }
    });
  }
  
  public static void appendImageInMessageOne(String characterName){
    StyledDocument doc = consoleGameExtension.getStyledDocument();
    ImageIcon image = parseImage(characterName);
    if (image != null){
      Style style = consoleGameExtension.addStyle("ImageStyle", null);
      StyleConstants.setIcon(style, image);
      try {
        doc.insertString(doc.getLength(), "\n", null);
        doc.insertString(doc.getLength(), "Ignored", style);
      } catch (BadLocationException e) {
        e.printStackTrace();
      }
    } else {
      try {
        doc.insertString(doc.getLength(), "\n", null);
      } catch (BadLocationException e) {
        e.printStackTrace();
      }
    }
    
  }
  
  /**
   * Clears the existing message and displays a new message in the text area
   * @param extension The message to display.
   */
  public static void setMessageTwo(String extension){
    SwingUtilities.invokeLater(() -> {
      try {
        // Access the JTextPane's document
        StyledDocument doc = textBoxTwo.getStyledDocument();
        // Remove all existing content
        doc.remove(0, doc.getLength());
        // Insert new text
        doc.insertString(0, extension, null);
      } catch (BadLocationException e) {
        e.printStackTrace();
      }
    });
  }
  
  /**
   * Appends a message to the second text area.
   * @param extension The message to append.
   */
  public static void appendMessageTwo(String extension){
    SwingUtilities.invokeLater(() -> {
      try {
        StyledDocument doc = textBoxTwo.getStyledDocument();
        doc.insertString(doc.getLength(), extension, null);
      } catch (BadLocationException e) {
        e.printStackTrace();
      }
    });
  }
  
  
  /**
   * Creates a button that can be initiated inside the choice area.
   * It won't make the button appear
   * @param buttonText set the text on the button.
   */
  public static JButton createChoiceButton(String buttonText) {
    JButton button = new JButton(buttonText);
    return button;
  }
  
  /**
   * initiates the button in the choice area, should be called with an action listener expression.
   * make sure to use the clear button in every action listener of the button to make sure it disappears
   * from the screen.
   * @param button -- is the button needed to show up on the screen.
   * @param actionListener -- expression for the button
   */
  public static void addChoiceButton(JButton button, ActionListener actionListener){
    button.addActionListener(actionListener);
    choicesPanel.add(button);
    choicesPanel.revalidate();
    choicesPanel.repaint();
  }
  
  /**
   * Clears all choice buttons from the panel. Should be rarely used.
   * It refreshes the whole choice panel
   */
  public static void clearChoiceButtons() {
    choicesPanel.removeAll();
    choicesPanel.revalidate();
    choicesPanel.repaint();
  }
  
  /**
   * Clears a specific button from the choice panel. Takes a button type as an argument
   * @param button -- clears this specific button off from the choice panel
   */
  public static void clearButton(JButton button) {
    choicesPanel.remove(button);
    choicesPanel.revalidate();
    choicesPanel.repaint();
  }
  
  
  /**
   * a private class aiming to provide constraints and layout options for the elements on the
   * screen. The function is called whenever there is a new element is added on the screen, it
   * gives us options to customise the size, alignment, padding and other appearance features
   * for the elements.
   * @param gridx -- x position of the element
   * @param gridy -- y position of the element
   * @param gridwidth -- Number of column the element should span for
   * @param gridheight -- number of rows the element should span for
   * @param fill -- fill behaviour of the element
   * @param anchor -- where the element should be anchored in its layout grid
   * @param weightx -- ratio; how to distribute extra horizontal space between other elements
   * @param weighty -- ratio; how to distribute extra vertical space between other elements
   * @param insets -- external padding around the element, takes insets object
   */
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
  
  
  private static ImageIcon parseImage(String characterName){
    if (characterName.equals("Ser Alaric, Leader of the Guards")){
      return new ImageIcon("resources/leader-of-the-gurads-x1.png");
    }
    else if (characterName.equals("Master Crafter Silas")){
      return new ImageIcon("resources/master-builder-x2.png");
    }
    else if (characterName.equals("Adonis, Imperial Inquisitor")){
      return new ImageIcon("resources/imperial-inquisitor-mouthopen-x2.png");
    }
    else if (characterName.equals("Dr Kynes, Independent Scholar")){
      return new ImageIcon("resources/independant-scholar-mouthopen-x2.png");
    }
    else if (characterName.equals("Cloaked Figure")){
      return new ImageIcon("resources/cloaked-figure-x3.png");
    }
    else if (characterName.equals("Counsellor Bahtia")){
      return new ImageIcon("resources/counseller-x3.png");
    }
    else if (characterName.equals("Aliana Salua, Imperial Banker")){
      return new ImageIcon("resources/imperial-banker-mouth-open-x2.png");
    }
    else if (characterName.equals("Alistair Kyntha, The Hand of The King")){
      return new ImageIcon("resources/kings-hand-mouthopen-x2.png");
    }
    else if (characterName.equals("High Priestess")){
      return new ImageIcon("resources/priest-mouthopen-x2.png");
    }
    else if (characterName.equals("Dr. Elend")){
      return new ImageIcon("resources/doctor-x2.png");
    }
    else {
      return null;
    }
  }
}
