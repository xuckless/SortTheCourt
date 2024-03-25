class PlayerStats {
  public static int getGold() {
    return DataEditor.getDataValue("Gold", "src/main/java/SAVEDATA.txt");
  }
  public static int getFaith() {
    return DataEditor.getDataValue("Faith", "src/main/java/SAVEDATA.txt");
  }
  public static int getPopulation() {
    return DataEditor.getDataValue("Population", "src/main/java/SAVEDATA.txt");
  }
  public static int getTech() {
    return DataEditor.getDataValue("Tech", "src/main/java/SAVEDATA.txt");
  }
}