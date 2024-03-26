import javax.swing.*;

public class Tester {
  
  public void sequentialRun(){
    JButton next = GameScreenOne.createChoiceButton("Next");
    
    GameScreenOne.addChoiceButton(next, e->{
      GameScreenOne.setMessageOne("This is the override");
      GameScreenOne.clearButton(next);
      this.sequenceTwo();
    });
  }
  
  public void sequenceTwo(){
    JButton next = GameScreenOne.createChoiceButton("Next");
    
    GameScreenOne.addChoiceButton(next, e->{
      GameScreenOne.appendMessageOne("This is the append");
      GameScreenOne.clearButton(next);
      this.sequenceThree();
    });
  }
  
  public void sequenceThree(){
    JButton next = GameScreenOne.createChoiceButton("Next");
    
    GameScreenOne.addChoiceButton(next, e->{
      GameScreenOne.appendMessageOne("\nThis is the append");
      GameScreenOne.clearButton(next);
      this.sequenceFour();
    });
  }
  
  public void sequenceFour(){
    JButton next = GameScreenOne.createChoiceButton("Next");
    
    GameScreenOne.addChoiceButton(next, e->{
      GameScreenOne.appendMessageOne("\nThis is the append");
      GameScreenOne.setMessageTwo("This is the override");
      GameScreenOne.appendMessageTwo("\tThis is the append");
      GameScreenOne.clearButton(next);
      this.sequenceFive();
    });
  }
  
  public void sequenceFive(){
    JButton next = GameScreenOne.createChoiceButton("Next");
    GameScreenOne.addChoiceButton(next, e->{
      GameScreenOne.setMessageOne("Button is pressed!");
      GameScreenOne.setMessageTwo("Button is pressed!");
      GameScreenOne.clearButton(next);
      this.sequenceSix();
    });
  }
  
  public void sequenceSix(){
    JButton next = GameScreenOne.createChoiceButton("Next");
    
    GameScreenOne.addChoiceButton(next, e->{
      GameScreenOne.setMessageTwo("This is the second override");
      GameScreenOne.setMessageOne("This is the second override");
      GameScreenOne.clearButton(next);
      this.sequentialRun();
    });
  }
  
  public static void Main() throws InterruptedException {
    Tester tester = new Tester();
    GameScreenOne.clearChoiceButtons();
    tester.sequentialRun();
  }
}
