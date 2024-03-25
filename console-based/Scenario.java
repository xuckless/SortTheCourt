class Scenario {
  public String ID;
  public String character;
  public String scenarioDescription;
  
  public int startingConsequenceCount;
  public int populationConsequence;
  public int goldConsequence;
  public int faithConsequence;
  public int techConsequence;
  
  public int populationStartCondition;
  public int goldStartCondition;
  public int faithStartCondition;
  public int techStartCondition;
  
  public boolean hasBeenCompleted;
  public int choiceMadeWhenCompleted;
  
  public boolean canHavePriority;
  public boolean hasPriority;
  
  public boolean startConditionMet;
  public String startConditionScenarioID;
  public int startConditionScenarioChoiceMade;
  
  public int choiceCount;
  
  public String choice1Description;
  public String choice1Followup;
  public String choice1StartCondition;
  public boolean choice1StartConditionMet;
  public int choice1PopulationConsequence;
  public int choice1GoldConsequence;
  public int choice1FaithConsequence;
  public int choice1TechConsequence;
  public int choice1PopulationCondition;
  public int choice1GoldCondition;
  public int choice1FaithCondition;
  public int choice1TechCondition;
  
  public String choice2Description;
  public String choice2Followup;
  public String choice2StartCondition;
  public boolean choice2StartConditionMet;
  public int choice2PopulationConsequence;
  public int choice2GoldConsequence;
  public int choice2FaithConsequence;
  public int choice2TechConsequence;
  public int choice2PopulationCondition;
  public int choice2GoldCondition;
  public int choice2FaithCondition;
  public int choice2TechCondition;
  
  public String choice3Description;
  public String choice3Followup;
  public String choice3StartCondition;
  public boolean choice3StartConditionMet;
  public int choice3PopulationConsequence;
  public int choice3GoldConsequence;
  public int choice3FaithConsequence;
  public int choice3TechConsequence;
  public int choice3PopulationCondition;
  public int choice3GoldCondition;
  public int choice3FaithCondition;
  public int choice3TechCondition;
  
  public Scenario() {
    this.choice1GoldCondition = 0;
    this.choice2GoldCondition = 0;
    this.choice3GoldCondition = 0;
    this.choice1FaithCondition = 0;
    this.choice2FaithCondition = 0;
    this.choice3FaithCondition = 0;
    this.choice1PopulationCondition = 0;
    this.choice2PopulationCondition = 0;
    this.choice3PopulationCondition = 0;
    this.choice1TechCondition = 0;
    this.choice2TechCondition = 0;
    this.choice3TechCondition = 0;
    this.choice1GoldConsequence = 0;
    this.choice2GoldConsequence = 0;
    this.choice3GoldConsequence = 0;
    this.choice1FaithConsequence = 0;
    this.choice2FaithConsequence = 0;
    this.choice3FaithConsequence = 0;
    this.choice1PopulationConsequence = 0;
    this.choice2PopulationConsequence = 0;
    this.choice3PopulationConsequence = 0;
    this.choice1TechConsequence = 0;
    this.choice2TechConsequence = 0;
    this.choice3TechConsequence = 0;
    
    this.choiceMadeWhenCompleted = 0;
    
    this.choice1StartConditionMet = true;
    this.choice2StartConditionMet = true;
    this.choice3StartConditionMet = true;
    
    this.startConditionMet = false;
    this.hasPriority = false;
    this.canHavePriority = false;
    
  }
  
  
  public void print() {
    System.out.println("ID: " + this.ID);
    System.out.println("Character: " + this.character);
    System.out.println("Description: " + this.scenarioDescription);
    System.out.println("Starting consequence count: " + this.startingConsequenceCount);
    System.out.println("Starting consequence - Gold: " + this.goldConsequence);
    System.out.println("Starting consequence - Faith: " + this.faithConsequence);
    System.out.println("Starting consequence - Population: " + this.populationConsequence);
    System.out.println("Starting consequence - Technology: " + this.techConsequence);
    System.out.println("Starting condition - Gold: " + this.goldStartCondition);
    System.out.println("Starting condition - Faith: " + this.faithStartCondition);
    System.out.println("Starting condition - Population: " + this.populationStartCondition);
    System.out.println("Starting condition - Technology: " + this.techStartCondition);
    System.out.println("Choice count: " + this.choiceCount);
    System.out.println("Has been completed: " + this.hasBeenCompleted);
    System.out.println("Choice made when completed: " + this.choiceMadeWhenCompleted);
    System.out.println("Can have priority: " + this.canHavePriority);
    System.out.println("Has priority: " + this.hasPriority);
    System.out.println();
    System.out.println("Choice 1 description: " + this.choice1Description);
    System.out.println("Choice 1 followup: " + this.choice1Followup);
    System.out.println("Choice 1 start condition met: " + this.choice1StartConditionMet);
    System.out.println("Choice 1 Consequence - Gold: " + this.choice1GoldConsequence);
    System.out.println("Choice 1 Consequence - Faith: " + this.choice1FaithConsequence);
    System.out.println("Choice 1 Consequence - Population: " + this.choice1PopulationConsequence);
    System.out.println("Choice 1 Consequence - Technology: " + this.choice1TechConsequence);
    System.out.println("Choice 1 Condition - Gold: " + this.choice1GoldCondition);
    System.out.println("Choice 1 Condition - Faith: " + this.choice1FaithCondition);
    System.out.println("Choice 1 Condition - Population: " + this.choice1PopulationCondition);
    System.out.println("Choice 1 Condition - Technology: " + this.choice1TechCondition);
    System.out.println();
    if (this.choiceCount >= 2) {
      System.out.println("Choice 2 description: " + this.choice2Description);
      System.out.println("Choice 2 followup: " + this.choice2Followup);
      System.out.println("Choice 2 start condition met: " + this.choice2StartConditionMet);
      System.out.println("Choice 2 Consequence - Gold: " + this.choice2GoldConsequence);
      System.out.println("Choice 2 Consequence - Faith: " + this.choice2FaithConsequence);
      System.out.println("Choice 2 Consequence - Population: " + this.choice2PopulationConsequence);
      System.out.println("Choice 2 Consequence - Technology: " + this.choice2TechConsequence);
      System.out.println("Choice 2 Condition - Gold: " + this.choice2GoldCondition);
      System.out.println("Choice 2 Condition - Faith: " + this.choice2FaithCondition);
      System.out.println("Choice 2 Condition - Population: " + this.choice2PopulationCondition);
      System.out.println("Choice 2 Condition - Technology: " + this.choice2TechCondition);
    }
    System.out.println();
    if (this.choiceCount == 3) {
      System.out.println("Choice 3 description: " + this.choice3Description);
      System.out.println("Choice 3 followup: " + this.choice3Followup);
      System.out.println("Choice 3 start condition met: " + this.choice3StartConditionMet);
      System.out.println("Choice 3 Consequence - Gold: " + this.choice3GoldConsequence);
      System.out.println("Choice 3 Consequence - Faith: " + this.choice3FaithConsequence);
      System.out.println("Choice 3 Consequence - Population: " + this.choice3PopulationConsequence);
      System.out.println("Choice 3 Consequence - Technology: " + this.choice3TechConsequence);
      System.out.println("Choice 3 Condition - Gold: " + this.choice3GoldCondition);
      System.out.println("Choice 3 Condition - Faith: " + this.choice3FaithCondition);
      System.out.println("Choice 3 Condition - Population: " + this.choice3PopulationCondition);
      System.out.println("Choice 3 Condition - Technology: " + this.choice3TechCondition);
    }
    System.out.println();
    System.out.println();
    System.out.println();
  }
}

