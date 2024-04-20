import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TicTacToe implements Runnable{
  public JPanel inputPane;
  private JButton r0C0;
  private JButton r1C0;
  private JButton r2C0;
  private JButton r0C1;
  private JButton r1C1;
  private JButton r2C1;
  private JButton r0C2;
  private JButton r1C2;
  private JButton r2C2;
  private JFormattedTextField bufferField;
  private JButton resetGameButton;
  
  TicTacToeLogic ttl;
  public static Thread MAIN_THREAD;
  JButton buttons[][];
  
  TicTacToe(){
    this.ttl = new TicTacToeLogic();
    this.buttons = new JButton[3][3];
    this.buttons[0][0] = r0C0;
    this.buttons[0][1] = r0C1;
    this.buttons[0][2] = r0C2;
    this.buttons[1][0] = r1C0;
    this.buttons[1][1] = r1C1;
    this.buttons[1][2] = r1C2;
    this.buttons[2][0] = r2C0;
    this.buttons[2][1] = r2C1;
    this.buttons[2][2] = r2C2;
  }
  
  
  
  public void startThread(){
    MAIN_THREAD = new Thread(this);
    MAIN_THREAD.start();
  }
  
  @Override
  public void run() {
    this.packAll();
  }
  
  private void actionListeners(){
    int rowIndex = 0;
    for (JButton[] buttonRow : buttons){
      int columnIndex = 0;
      for (JButton button : buttonRow){
        final JButton btn = button;
        final int finalRowIndex = rowIndex;
        final int finalColIndex = columnIndex;
        button.addActionListener(e -> {
          ttl.appendCounter(); bufferField.setText(" ");
          btn.setText(ttl.returnStr());
          this.removeButtonHighlights();
          ttl.updateArray(finalRowIndex, finalColIndex, ttl.returnStr());
          
          ttl.logMove(finalRowIndex+" "+finalColIndex);
          if(ttl.getRemovedElement() != null){
            removeButtonText(ttl.getRemovedElement());
          }
          
          ttl.printBoard();
          this.victoryCheck();
          btn.setEnabled(false);
          this.highlightNextOut(ttl.returnNextOut());
        });
        columnIndex++;
      }
      rowIndex++;
    }
    resetGameButton.addActionListener(e -> {
      resetGame();
    });
  }
  
  
  private void resetGame() {
    this.removeButtonHighlights();
    bufferField.setText("Game has been reset");
    ttl.flushGame();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        this.buttons[i][j].setText(" ");
      }
    }
    ttl.printBoard();
    for (JButton[] button : buttons) {
      for (JButton button1 : button) {
        button1.setEnabled(true);
      }
    }
  }
  
  private void victoryCheck(){
    if (ttl.winCondition()){
      bufferField.setText(ttl.returnStr() + " Wins!");
      for (JButton[] button : buttons) {
        for (JButton button1 : button) {
          button1.setEnabled(false);
        }
      }
    }
  }
  
  private void packAll(){
    this.actionListeners();
  }
  
  private void removeButtonText(String key){
    String i = String.valueOf(key.charAt(0));
    String j = String.valueOf(key.charAt(key.length()-1));

    int row = Integer.parseInt(i);
    int col = Integer.parseInt(j);
    buttons[row][col].setText(" ");
    buttons[row][col].setEnabled(true);
  }
  
  private void highlightNextOut(String key){
    if (key == null){
      return;
    }
    String i = String.valueOf(key.charAt(0));
    String j = String.valueOf(key.charAt(key.length()-1));
    
    int row = Integer.parseInt(i);
    int col = Integer.parseInt(j);
    
    if (ttl.counter > 6) {
      buttons[row][col].setBorderPainted(true);
      buttons[row][col].setBorder(new LineBorder(Color.RED, 2));
    }
  }
  
  private void removeButtonHighlights(){
    for (JButton[] button : buttons) {
      for (JButton button1 : button) {
        button1.setBorder(resetGameButton.getBorder());
      }
    }
  }
  
}
