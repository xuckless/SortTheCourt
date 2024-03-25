class DataItem {
  
  private String name;
  private int value;
  
  public DataItem(String name, int value) {
    this.name = name;
    this.value = value;
  }
  
  public String getName() {
    return this.name;
  }
  
  public int getValue() {
    return this.value;
  }
  
  public void setValue(int newValue) {
    this.value = newValue;
  }
  public void setName(String newName) {
    this.name = newName;
  }
  
}
