import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CustomMouseListener implements MouseListener {
  
  JTextArea textArea;
  
  public CustomMouseListener(JTextArea textArea) {
    this.textArea = textArea;
  }
  public CustomMouseListener(){
  
  }
  
  boolean isClicked = false;
  boolean isSelected = false;
  
  @Override
  public void mouseClicked(MouseEvent e) {
    textArea.append("Mouse clicked at: " + e.getX() + ", " + e.getY());
  }
  
  @Override
  public void mousePressed(MouseEvent e) {
    this.isClicked = true;
  }
  @Override
  public void mouseReleased(MouseEvent e) {
    this.isClicked = false;
  }
  
  @Override
  public void mouseEntered(MouseEvent e) {
    this.isSelected = true;
  }
  
  @Override
  public void mouseExited(MouseEvent e) {
    this.isSelected = false;
  }
}