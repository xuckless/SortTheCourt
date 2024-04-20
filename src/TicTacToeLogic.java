import java.awt.desktop.SystemEventListener;

public class TicTacToeLogic {
  String table[][];
  int counter;
  String moveLogger[];
  String removedElement = null;
  
  TicTacToeLogic(){
    this.table = new String[3][3];
    this.moveLogger = new String[7];
    this.counter = 0;
  }
  
  void appendCounter(){
    this.counter++;
  }
  void logMove(String key){
//    if (this.moveCount < 7){
//      moveLogger[moveCount] = key;
//      moveCount++;
//    }
    if (this.counter-1 <= 6){
      moveLogger[counter-1] = key;
    }
    else {
      for (int i = 0; i < 6; i++) {
        removedElement = moveLogger[0];
        moveLogger[i] = moveLogger[i + 1];
      }
      moveLogger[moveLogger.length-1] = key;
      this.removeFromArray(removedElement);
    }
  }
  
  public String getRemovedElement() {
    return removedElement;
  }
  
  String[][] getTable(){
    return this.table;
  }
  
  boolean winCondition() {
    for (int i = 0; i < 3; i++ ){
      if (isRowWin(i) || isColumnWin(i)) {
        return true;
      }
    }
    
    if (isDiagonalWin()) {
      return true;
    }
    return false;
  }
  
  int getCounter(){
    return counter;
  }
  
  String returnStr(){
    if (counter % 2 == 0){
      return "O";
    }
    else return "X";
  }
  
  void updateArray(int row, int col, String returnStr){
    table[col][row] = returnStr;
  }
  
  void removeFromArray(String key){
    String i = String.valueOf(key.charAt(0));
    String j = String.valueOf(key.charAt(key.length()-1));
    
    int row = Integer.parseInt(i);
    int col = Integer.parseInt(j);
    
    this.table[col][row] = null;
  }
  
   void flushGame(){
     this.table = new String[3][3];
     this.moveLogger = new String[7];
     this.counter = 0;
  }
  
  
  
  private boolean isRowWin(int row) {
    String first = table[row][0];
    if (first != null && first.equals(table[row][1]) && first.equals(table[row][2])) {
      return true;
    }
    return false;
  }
  
  private boolean isColumnWin(int col) {
    String first = table[0][col];
    if (first != null && first.equals(table[1][col]) && first.equals(table[2][col])) {
      return true;
    }
    return false;
  }
  
  private boolean isDiagonalWin() {
    String center = table[1][1];
    if (center != null) {
      if (center.equals(table[0][0]) && center.equals(table[2][2])) {
        return true;
      }
      if (center.equals(table[0][2]) && center.equals(table[2][0])) {
        return true;
      }
    }
    return false;
  }
  
  
  String returnNextOut(){
    return moveLogger[1];
  }
  
  String highlightNextOut(String key){
    if (key == null){
      return null;
    }
    String i = String.valueOf(key.charAt(0));
    String j = String.valueOf(key.charAt(key.length()-1));
    
    int row = Integer.parseInt(i);
    int col = Integer.parseInt(j);
    
    if (this.table[col][row] != null && counter > 6) {
      return this.table[col][row];
    }
    else {
      return null;
    }
  }
  
  
  
  
  void printBoard(){ // Just for test
    System.out.println("\n\n");
    
    for (String columns[] : table){
      for (String items : columns){
        if (items != null){
          System.out.print("\t"+items + "\t");
        }
        else{
          System.out.print("\t__\t");
        }
      }
      System.out.println("\n\n");
    }
    
    
    System.out.println("\n\n");
    for (String things : moveLogger){
      if (things != null){
        System.out.print("\t"+things + "\t");
      }
      else{
        System.out.print("\t__\t");
      }
    }
    
    System.out.println("\tCounter: "+counter+" Win: "+this.winCondition());
    System.out.println(highlightNextOut(returnNextOut()));
  }
}
