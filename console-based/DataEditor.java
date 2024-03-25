import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class DataEditor {
  private static void saveData(ArrayList<DataItem> dataList, String filePath) {
    
    File file = new File(filePath);
    //file.delete();
    
    try {
      FileWriter fileWriter = new FileWriter(file);
      
      for (DataItem dataItem : dataList) {
        String name = dataItem.getName();
        int value = dataItem.getValue();
        fileWriter.write(name + ":" + value + "\n");
      }
      
      fileWriter.close();
      
    } catch (IOException e) {
      System.err.println("Error writing to the file: " + e.getMessage());
    }
  }
  
  private static ArrayList<DataItem> loadDataList(String fileDirectory) {
    
    ArrayList<DataItem> dataList = new ArrayList<>();
    
    try {
      FileReader fileReader = new FileReader(new File(fileDirectory));
      Scanner fileScanner = new Scanner(fileReader);
      
      while (fileScanner.hasNextLine()) {
        String line = fileScanner.nextLine();
        
        String[] parts = line.split(":");
        String name = parts[0];
        int value = Integer.parseInt(parts[1].trim());
        
        dataList.add(new DataItem(name, value));
      }
      fileScanner.close();
      fileReader.close();
    } catch (IOException e) {
      System.err.println("Error reading the file: " + e.getMessage());
    }
    return dataList;
  }
  
  public static int getDataValue(String name, String fileDirectory) {
    
    int dataValue = -1;
    
    for (DataItem dataItem : loadDataList(fileDirectory)) {
      if (dataItem.getName().equals(name)) {
        dataValue = dataItem.getValue();
      }
    }
    if (dataValue == -1) System.out.println("Error accessing data.");
    return dataValue;
  }
  
  public static void changeDataValue(String name, int newValue, String fileDirectory) {
    
    ArrayList<DataItem> dataList = loadDataList(fileDirectory);
    
    for (DataItem dataItem : dataList) {
      if (dataItem.getName().equals(name)) {
        int originalValue = dataItem.getValue();
        dataItem.setValue(newValue);
        saveData(dataList, fileDirectory);
        break;
      }
    }
    //System.out.println("data saved");
    saveData(dataList, fileDirectory);
  }
  
  public static void addDataItem(String name, int value, String fileDirectory) {
    ArrayList<DataItem> dataList = loadDataList(fileDirectory);
    dataList.add(new DataItem(name, value));
    saveData(dataList, fileDirectory);
  }
  
  public static void printData(String fileDirectory) {
    
    System.out.println("Data:");
    
    for (DataItem dataItem : loadDataList(fileDirectory)) {
      System.out.println("\t" + dataItem.getName() + ": " + dataItem.getValue());
    }
    System.out.println();
    
  }
  
  
  
  
  public static int getCompletedScenarioCount(String fileDirectory) {
    
    int tally = 0;
    
    for (DataItem dataItem : loadDataList(fileDirectory)) {
      if (dataItem.getName().contains("=")) {
        tally++;
      }
    }
    return tally;
  }
  
  public static void addCompletedScenario(String name, int choiceMade, String fileDirectory) {
    ArrayList<DataItem> dataList = loadDataList(fileDirectory);
    dataList.add(new DataItem(name, choiceMade));
    saveData(dataList, fileDirectory);
  }
  
  public static ArrayList<DataItem> getCompletedScenarioList(String fileDirectory) {
    ArrayList<DataItem> scenarioList = new ArrayList<>();
    
    for (DataItem dataItem : loadDataList(fileDirectory)) {
      if (dataItem.getName().contains("1-")) {
        DataItem completedScenario = new DataItem(dataItem.getName(), dataItem.getValue());
        scenarioList.add(completedScenario);
      }
    }
    return scenarioList;
  }
}