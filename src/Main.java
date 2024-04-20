import javax.swing.*;
import java.awt.*;

public class Main {
  
  public static void main(String[] args) {
    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setMinimumSize(new Dimension(500, 600));
    window.setResizable(true);
    window.setTitle("TicTacToeTwo");
    
    TicTacToe ticTacToe = new TicTacToe();
    window.add(ticTacToe.inputPane);
    
    window.pack();
    
    window.setLocationRelativeTo(null);
    window.setVisible(true);
    
    ticTacToe.startThread();
  }
}
