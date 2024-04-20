public class TicTacToeLogicTest {
  public static void main(String[] args) {
    testWinConditions();
    testCounter();
    testTableUpdates();
    System.out.println("All tests passed!");
  }
  
  private static void testWinConditions() {
    TicTacToeLogic game = new TicTacToeLogic();
    game.updateArray(0, 0, "X");
    game.updateArray(0, 1, "X");
    game.updateArray(0, 2, "X");
    assert game.winCondition() : "Horizontal win condition failed.";
    
    game.flushGame();
    game.updateArray(0, 0, "O");
    game.updateArray(1, 0, "O");
    game.updateArray(2, 0, "O");
    assert game.winCondition() : "Vertical win condition failed.";
    
    game.flushGame();
    game.updateArray(0, 0, "X");
    game.updateArray(1, 1, "X");
    game.updateArray(2, 2, "X");
    assert game.winCondition() : "Diagonal win condition failed.";
    
    game.flushGame();
    game.updateArray(0, 2, "O");
    game.updateArray(1, 1, "O");
    game.updateArray(2, 0, "O");
    assert game.winCondition() : "Anti-diagonal win condition failed.";
  }
  
  private static void testCounter() {
    TicTacToeLogic game = new TicTacToeLogic();
    assert game.counter == 0 : "Counter should start at 0.";
    game.appendCounter();
    assert game.counter == 1 : "Counter increment failed.";
  }
  
  private static void testTableUpdates() {
    TicTacToeLogic game = new TicTacToeLogic();
    String expectedPlayer = "O";  // First move should be "O" since counter starts at 0.
    game.updateArray(1, 1, game.returnStr());
    assert game.getTable()[1][1].equals(expectedPlayer) : "Table update failed.";
  }
}
