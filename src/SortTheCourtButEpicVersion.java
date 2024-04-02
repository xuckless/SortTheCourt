import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


public class SortTheCourtButEpicVersion {
    
    
    public static void gameLogicCall() {
        
        ConsoleGame consoleGame = new ConsoleGame();
        
        //consoleGame.StartGame("SAVEDATA.txt", "Scenarios.txt");
        
        consoleGame.StartGame("src/SAVEDATA.txt", "src/Scenarios.txt");
        
    }
    
    public static Scenario loadScenario(String filePath, String scenarioTxtFilePath) {
        ArrayList<Scenario> scenarioList = SortTheCourtButEpicVersion.getFullScenarioList(scenarioTxtFilePath);
        ArrayList<DataItem> completedScenarios = DataEditor.getCompletedScenarioList(filePath);
        
        if (scenarioList.size() == completedScenarios.size()) return null;
        
        
        //Runs through every scenario to see if it has a start condition and checks if that condition is met
        for (Scenario scenario : scenarioList) {
            
            
            if ((scenario.goldStartCondition != 0 || scenario.faithStartCondition != 0 || scenario.populationStartCondition != 0 || scenario.techStartCondition != 0) && (scenario.startConditionScenarioID != null)) {
                System.out.println("Error: resource start condition and previous scenario completed condition both present in a single scenario.");
                System.exit(0);
            }
            
            if (scenario.goldStartCondition != 0 || scenario.faithStartCondition != 0 || scenario.populationStartCondition != 0 || scenario.techStartCondition != 0) {
                if (scenario.goldStartCondition != 0) {
                    if (DataEditor.getDataValue("Gold", filePath) <= scenario.goldStartCondition) {
                        if (scenario.canHavePriority) scenario.hasPriority = true;
                        scenario.startConditionMet = true;
                    }
                }
                if (scenario.faithStartCondition != 0) {
                    if (DataEditor.getDataValue("Faith", filePath) <= scenario.faithStartCondition) {
                        if (scenario.canHavePriority) scenario.hasPriority = true;
                        scenario.startConditionMet = true;
                    }
                }
                if (scenario.populationStartCondition != 0) {
                    if (DataEditor.getDataValue("Population", filePath) >= scenario.populationStartCondition) {
                        if (scenario.canHavePriority) scenario.hasPriority = true;
                        scenario.startConditionMet = true;
                    }
                }
                if (scenario.techStartCondition != 0) {
                    if (DataEditor.getDataValue("Tech", filePath) >= scenario.techStartCondition) {
                        if (scenario.canHavePriority) scenario.hasPriority = true;
                        scenario.startConditionMet = true;
                    }
                }
            } else if (scenario.startConditionScenarioID != null) {
                for (DataItem completedScenario : completedScenarios) {
                    if (completedScenario.getName().equals(scenario.startConditionScenarioID)) {
                        if ((completedScenario.getValue() == scenario.startConditionScenarioChoiceMade) || scenario.startConditionScenarioChoiceMade == 0) {
                            if (scenario.canHavePriority) scenario.hasPriority = true;
                            scenario.startConditionMet = true;
                        }
                    }
                }
            } else {
                if (scenario.ID.contains("=1") && !scenario.ID.contains("=10") && !scenario.ID.contains("=11") && !scenario.ID.contains("=12") && !scenario.ID.contains("=13") && !scenario.ID.contains("=14")) {
                    scenario.startConditionMet = true;
                }
            }
        }
        
        //eliminates completed scenarios from list
        for (Scenario scenario : scenarioList) {
            for (DataItem completedScenario : completedScenarios) {
                if (scenario.ID.equals(completedScenario.getName())) {
                    scenario.startConditionMet = false;
                    continue;
                }
            }
        }
        
        ArrayList<Scenario> validScenarios = new ArrayList<>();
        
        Scenario loadedScenario;
        
        for (Scenario scenario : scenarioList) {
            if (scenario.startConditionMet) validScenarios.add(scenario);
        }
        
        if (validScenarios.isEmpty()) return null;
        
        ArrayList<Scenario> scenariosWithPriority = new ArrayList<>();
        for (Scenario scenario : validScenarios) {
            if (scenario.hasPriority) {
                scenariosWithPriority.add(scenario);
            }
        }
        
        int priorityByResource = 0;
        int priorityByPreviousScenarioCompletion = 0;
        if (!scenariosWithPriority.isEmpty()) {
            for (Scenario scenario : scenariosWithPriority) {
                if (scenario.startConditionScenarioID != null) {
                    priorityByPreviousScenarioCompletion++;
                } else priorityByResource++;
            }
            if ((priorityByResource != 0) && (priorityByPreviousScenarioCompletion != 0)) {
                scenariosWithPriority.removeIf(scenario -> scenario.startConditionScenarioID == null);
                Random random = new Random();
                int randomIndex = random.nextInt(scenariosWithPriority.size());
                loadedScenario = scenariosWithPriority.get(randomIndex);
            } else {
                Random random = new Random();
                int randomIndex = random.nextInt(scenariosWithPriority.size());
                loadedScenario = scenariosWithPriority.get(randomIndex);
            }
        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(validScenarios.size());
            loadedScenario = validScenarios.get(randomIndex);
        }
        
        
        //checking choice start conditions of chosen scenario
        if (loadedScenario.choiceCount == 3) {
            if (DataEditor.getDataValue("Gold", filePath) < loadedScenario.choice1GoldCondition) loadedScenario.choice1StartConditionMet = false;
            if (DataEditor.getDataValue("Faith", filePath) < loadedScenario.choice1FaithCondition) loadedScenario.choice1StartConditionMet = false;
            if (DataEditor.getDataValue("Population", filePath) < loadedScenario.choice1PopulationCondition) loadedScenario.choice1StartConditionMet = false;
            if (DataEditor.getDataValue("Tech", filePath) < loadedScenario.choice1TechCondition) loadedScenario.choice1StartConditionMet = false;
            
            if (DataEditor.getDataValue("Gold", filePath) < loadedScenario.choice2GoldCondition) loadedScenario.choice2StartConditionMet = false;
            if (DataEditor.getDataValue("Faith", filePath) < loadedScenario.choice2FaithCondition) loadedScenario.choice2StartConditionMet = false;
            if (DataEditor.getDataValue("Population", filePath) < loadedScenario.choice2PopulationCondition) loadedScenario.choice2StartConditionMet = false;
            if (DataEditor.getDataValue("Tech", filePath) < loadedScenario.choice2TechCondition) loadedScenario.choice2StartConditionMet = false;
            
            if (DataEditor.getDataValue("Gold", filePath) < loadedScenario.choice3GoldCondition) loadedScenario.choice3StartConditionMet = false;
            if (DataEditor.getDataValue("Faith", filePath) < loadedScenario.choice3FaithCondition) loadedScenario.choice3StartConditionMet = false;
            if (DataEditor.getDataValue("Population", filePath) < loadedScenario.choice3PopulationCondition) loadedScenario.choice3StartConditionMet = false;
            if (DataEditor.getDataValue("Tech", filePath) < loadedScenario.choice3TechCondition) loadedScenario.choice3StartConditionMet = false;
            
            
            if (loadedScenario.choice1GoldCondition != 0) loadedScenario.choice1Description = loadedScenario.choice1Description + "(GOLD REQUIRED: " + loadedScenario.choice1GoldCondition + ")";
            if (loadedScenario.choice1FaithCondition != 0) loadedScenario.choice1Description = loadedScenario.choice1Description + "(FAITH REQUIRED: " + loadedScenario.choice1FaithCondition + ")";
            if (loadedScenario.choice1PopulationCondition != 0) loadedScenario.choice1Description = loadedScenario.choice1Description + "(POPULATION REQUIRED: " + loadedScenario.choice1PopulationCondition + ")";
            if (loadedScenario.choice1TechCondition != 0) loadedScenario.choice1Description = loadedScenario.choice1Description + "(TECH REQUIRED: " + loadedScenario.choice1TechCondition + ")";
            
            if (loadedScenario.choice2GoldCondition != 0) loadedScenario.choice2Description = loadedScenario.choice2Description + "(GOLD REQUIRED: " + loadedScenario.choice2GoldCondition + ")";
            if (loadedScenario.choice2FaithCondition != 0) loadedScenario.choice2Description = loadedScenario.choice2Description + "(FAITH REQUIRED: " + loadedScenario.choice2FaithCondition + ")";
            if (loadedScenario.choice2PopulationCondition != 0) loadedScenario.choice2Description = loadedScenario.choice2Description + "(POPULATION REQUIRED: " + loadedScenario.choice2PopulationCondition + ")";
            if (loadedScenario.choice2TechCondition != 0) loadedScenario.choice2Description = loadedScenario.choice2Description + "(TECH REQUIRED: " + loadedScenario.choice2TechCondition + ")";
            
            if (loadedScenario.choice3GoldCondition != 0) loadedScenario.choice3Description = loadedScenario.choice3Description + "(GOLD REQUIRED: " + loadedScenario.choice3GoldCondition + ")";
            if (loadedScenario.choice3FaithCondition != 0) loadedScenario.choice3Description = loadedScenario.choice3Description + "(FAITH REQUIRED: " + loadedScenario.choice3FaithCondition + ")";
            if (loadedScenario.choice3PopulationCondition != 0) loadedScenario.choice3Description = loadedScenario.choice3Description + "(POPULATION REQUIRED: " + loadedScenario.choice3PopulationCondition + ")";
            if (loadedScenario.choice3TechCondition != 0) loadedScenario.choice3Description = loadedScenario.choice3Description + "(TECH REQUIRED: " + loadedScenario.choice3TechCondition + ")";
            
            
            
        } else if (loadedScenario.choiceCount == 2) {
            if (DataEditor.getDataValue("Gold", filePath) < loadedScenario.choice1GoldCondition) loadedScenario.choice1StartConditionMet = false;
            if (DataEditor.getDataValue("Faith", filePath) < loadedScenario.choice1FaithCondition) loadedScenario.choice1StartConditionMet = false;
            if (DataEditor.getDataValue("Population", filePath) < loadedScenario.choice1PopulationCondition) loadedScenario.choice1StartConditionMet = false;
            if (DataEditor.getDataValue("Tech", filePath) < loadedScenario.choice1TechCondition) loadedScenario.choice1StartConditionMet = false;
            
            if (DataEditor.getDataValue("Gold", filePath) < loadedScenario.choice2GoldCondition) loadedScenario.choice2StartConditionMet = false;
            if (DataEditor.getDataValue("Faith", filePath) < loadedScenario.choice2FaithCondition) loadedScenario.choice2StartConditionMet = false;
            if (DataEditor.getDataValue("Population", filePath) < loadedScenario.choice2PopulationCondition) loadedScenario.choice2StartConditionMet = false;
            if (DataEditor.getDataValue("Tech", filePath) < loadedScenario.choice2TechCondition) loadedScenario.choice2StartConditionMet = false;
            
            
            if (loadedScenario.choice1GoldCondition != 0) loadedScenario.choice1Description = loadedScenario.choice1Description + "(GOLD REQUIRED: " + loadedScenario.choice1GoldCondition + ")";
            if (loadedScenario.choice1FaithCondition != 0) loadedScenario.choice1Description = loadedScenario.choice1Description + "(FAITH REQUIRED: " + loadedScenario.choice1FaithCondition + ")";
            if (loadedScenario.choice1PopulationCondition != 0) loadedScenario.choice1Description = loadedScenario.choice1Description + "(POPULATION REQUIRED: " + loadedScenario.choice1PopulationCondition + ")";
            if (loadedScenario.choice1TechCondition != 0) loadedScenario.choice1Description = loadedScenario.choice1Description + "(TECH REQUIRED: " + loadedScenario.choice1TechCondition + ")";
            
            if (loadedScenario.choice2GoldCondition != 0) loadedScenario.choice2Description = loadedScenario.choice2Description + "(GOLD REQUIRED: " + loadedScenario.choice2GoldCondition + ")";
            if (loadedScenario.choice2FaithCondition != 0) loadedScenario.choice2Description = loadedScenario.choice2Description + "(FAITH REQUIRED: " + loadedScenario.choice2FaithCondition + ")";
            if (loadedScenario.choice2PopulationCondition != 0) loadedScenario.choice2Description = loadedScenario.choice2Description + "(POPULATION REQUIRED: " + loadedScenario.choice2PopulationCondition + ")";
            if (loadedScenario.choice2TechCondition != 0) loadedScenario.choice2Description = loadedScenario.choice2Description + "(TECH REQUIRED: " + loadedScenario.choice2TechCondition + ")";
            
        } else if (loadedScenario.choiceCount == 1) {
            if (DataEditor.getDataValue("Gold", filePath) < loadedScenario.choice1GoldCondition) loadedScenario.choice1StartConditionMet = false;
            if (DataEditor.getDataValue("Faith", filePath) < loadedScenario.choice1FaithCondition) loadedScenario.choice1StartConditionMet = false;
            if (DataEditor.getDataValue("Population", filePath) < loadedScenario.choice1PopulationCondition) loadedScenario.choice1StartConditionMet = false;
            if (DataEditor.getDataValue("Tech", filePath) < loadedScenario.choice1TechCondition) loadedScenario.choice1StartConditionMet = false;
            
            
            if (loadedScenario.choice1GoldCondition != 0) loadedScenario.choice1Description = loadedScenario.choice1Description + "(GOLD REQUIRED: " + loadedScenario.choice1GoldCondition + ")";
            if (loadedScenario.choice1FaithCondition != 0) loadedScenario.choice1Description = loadedScenario.choice1Description + "(FAITH REQUIRED: " + loadedScenario.choice1FaithCondition + ")";
            if (loadedScenario.choice1PopulationCondition != 0) loadedScenario.choice1Description = loadedScenario.choice1Description + "(POPULATION REQUIRED: " + loadedScenario.choice1PopulationCondition + ")";
            if (loadedScenario.choice1TechCondition != 0) loadedScenario.choice1Description = loadedScenario.choice1Description + "(TECH REQUIRED: " + loadedScenario.choice1TechCondition + ")";
            
        } else {
            System.out.println("Error checking chosen scenario choices. Choice count: " + loadedScenario.choiceCount);
            System.exit(0);
        }
        
        return loadedScenario;
    }
    
    public static ArrayList<Scenario> getFullScenarioList(String scenarioTxtFilePath) {
        
        StringBuilder concatenatedText = new StringBuilder();
        
        try {
            FileReader fileReader = new FileReader(scenarioTxtFilePath);
            Scanner fileScanner = new Scanner(fileReader);
            
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                concatenatedText.append(line);
            }
            fileScanner.close();
            fileReader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        
        //System.out.println(concatenatedText);
        //System.exit(0);
        
        String scenariosAsString = concatenatedText.toString();
        String[] parts = scenariosAsString.split("\\|");
        
        ArrayList<String> scenarioStringList = new ArrayList<>();
        Collections.addAll(scenarioStringList, parts);
        
        ArrayList<Scenario> scenarioObjectList = new ArrayList<>();
        
        int scenarioIteration = 0;
        for (String scenarioString : scenarioStringList) {
            
            scenarioIteration++;
            if (scenarioString == null) {
                System.out.println("Error: Scenario " + scenarioIteration + " is null.");
                continue;
            }
            
            Scenario scenario = new Scenario();
            
            if (scenarioString.contains("3.")) {
                scenario.choiceCount = 3;
            } else if (scenarioString.contains("2.")) {
                scenario.choiceCount = 2;
            } else if (scenarioString.contains("1.")) {
                scenario.choiceCount = 1;
            } else {
                System.out.println("Error identifying scenario number count\n\n");
                System.out.println(scenarioString);
                System.exit(0);
            }
            
            String[] split1 = scenarioString.split(":", 3);
            
            scenario.character = split1[1];
            
            String conditionAndID = split1[0];
            
            if (conditionAndID.contains("{") && !conditionAndID.contains("<")) {
                scenario.canHavePriority = true;
                scenario.ID = conditionAndID.split("}",2)[1];
                
                if (conditionAndID.charAt(1) == '1') {
                    scenario.startConditionScenarioID = conditionAndID.split("\\{",2)[1].split("\\[",2)[0];
                    scenario.startConditionScenarioChoiceMade = Math.abs(Integer.parseInt(conditionAndID.split("\\[",2)[1].split("]",2)[0]));
                } else {
                    switch (conditionAndID.split("\\{",2)[1].split(";",2)[0]) {
                        case "Gold":
                            scenario.goldStartCondition = Integer.parseInt(conditionAndID.split(";",2)[1].split("}",2)[0]);
                            break;
                        
                        case "Faith":
                            scenario.faithStartCondition = Integer.parseInt(conditionAndID.split(";",2)[1].split("}",2)[0]);
                            break;
                        
                        case "Population":
                            scenario.populationStartCondition = Integer.parseInt(conditionAndID.split(";",2)[1].split("}",2)[0]);
                            break;
                        
                        case "Tech":
                            scenario.techStartCondition = Integer.parseInt(conditionAndID.split(";",2)[1].split("}",2)[0]);
                            break;
                        
                        default:
                            System.out.println("Error with detecting resource start condition name");
                            System.exit(0);
                            break;
                    }
                }
            } else if (conditionAndID.contains("<") && !conditionAndID.contains("{")) {
                scenario.canHavePriority = false;
                scenario.ID = conditionAndID.split(">",2)[1];
                
                if (conditionAndID.charAt(1) == '1') {
                    scenario.startConditionScenarioID = conditionAndID.split("<",2)[1].split("\\[",2)[0];
                    scenario.startConditionScenarioChoiceMade = Math.abs(Integer.parseInt(conditionAndID.split("\\[",2)[1].split("]",2)[0]));
                } else {
                    switch (conditionAndID.split("<",2)[1].split(";",2)[0]) {
                        case "Gold":
                            scenario.goldStartCondition = Integer.parseInt(conditionAndID.split(";",2)[1].split(">",2)[0]);
                            break;
                        
                        case "Faith":
                            scenario.faithStartCondition = Integer.parseInt(conditionAndID.split(";",2)[1].split(">",2)[0]);
                            break;
                        
                        case "Population":
                            scenario.populationStartCondition = Integer.parseInt(conditionAndID.split(";",2)[1].split(">",2)[0]);
                            break;
                        
                        case "Tech":
                            scenario.techStartCondition = Integer.parseInt(conditionAndID.split(";",2)[1].split(">",2)[0]);
                            break;
                    }
                }
            } else if (!conditionAndID.contains("<") && !conditionAndID.contains("{")) {
                scenario.canHavePriority = false;
                //scenario.startConditionMet = true;
                scenario.ID = conditionAndID;
            } else {
                System.out.println("Error with ID and condition checking.");
                System.out.println("Problematic condition and ID string: " + conditionAndID);
                System.exit(0);
            }
            
            
            String descriptionAndConsequence = split1[2].split("1\\.")[0];
            
            if (descriptionAndConsequence.contains("(")) {
                scenario.scenarioDescription = descriptionAndConsequence.split("\\(", 2)[0];
            } else {
                scenario.scenarioDescription = descriptionAndConsequence;
            }
            
            scenario.startingConsequenceCount = (int) descriptionAndConsequence.chars().filter(ch -> ch == '(').count();
            String startingConsequence;
            for (int i = 1; i <= scenario.startingConsequenceCount; i++) {
                startingConsequence = descriptionAndConsequence.split("\\(", (i+1))[i].split("\\)", 2)[0];
                
                if (startingConsequence.contains("Gold")) {
                    if (startingConsequence.contains("-")) scenario.goldConsequence = -1 * Integer.parseInt(startingConsequence.split("-")[1]);
                    if (startingConsequence.contains("+")) scenario.goldConsequence = Integer.parseInt(startingConsequence.split("\\+")[1]);
                }
                if (startingConsequence.contains("Faith")) {
                    if (startingConsequence.contains("-")) scenario.faithConsequence = -1 * Integer.parseInt(startingConsequence.split("-")[1]);
                    if (startingConsequence.contains("+")) scenario.faithConsequence = Integer.parseInt(startingConsequence.split("\\+")[1]);
                }
                if (startingConsequence.contains("Population")) {
                    if (startingConsequence.contains("-")) scenario.populationConsequence = -1 * Integer.parseInt(startingConsequence.split("-")[1]);
                    if (startingConsequence.contains("+")) scenario.populationConsequence = Integer.parseInt(startingConsequence.split("\\+")[1]);
                }
                if (startingConsequence.contains("Tech")) {
                    if (startingConsequence.contains("-")) scenario.techConsequence = -1 * Integer.parseInt(startingConsequence.split("-")[1]);
                    if (startingConsequence.contains("+")) scenario.techConsequence = Integer.parseInt(startingConsequence.split("\\+")[1]);
                }
            }
            
            String allChoices = "1." + split1[2].split("1\\.")[1];
            
            String choice1AndConsequenceAndCondition, choice2AndConsequenceAndCondition, choice3AndConsequenceAndCondition;
            String choice1AndConsequence = "Error: Choice description not found", choice2AndConsequence = "Error: Choice description not found", choice3AndConsequence = "Error: Choice description not found";
            String choice1Consequence, choice2Consequence, choice3Consequence;
            
            int choiceConditionValue = 0;
            int choiceConsequenceValue = 0;
            
            switch (scenario.choiceCount) {
                case 1:
                    choice1AndConsequenceAndCondition = allChoices.split("1\\.", 2)[1];
                    
                    if (choice1AndConsequenceAndCondition.contains("<") || choice1AndConsequenceAndCondition.contains("(")) {
                        if (choice1AndConsequenceAndCondition.contains("<")) {
                            choice1AndConsequence = choice1AndConsequenceAndCondition.split(">", 2)[1];
                        } else {
                            choice1AndConsequence = choice1AndConsequenceAndCondition;
                        }
                        if (choice1AndConsequenceAndCondition.contains("<")) choiceConditionValue = Math.abs(Integer.parseInt(choice1AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[1]));
                        if (choice1AndConsequenceAndCondition.contains("<")) if (choice1AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Gold")) {
                            scenario.choice1GoldCondition = choiceConditionValue;
                            scenario.choice1StartCondition = "Gold";
                        } else if (choice1AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Faith")) {
                            scenario.choice1FaithCondition = choiceConditionValue;
                            scenario.choice1StartCondition = "Faith";
                        } else if (choice1AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Population")) {
                            scenario.choice1PopulationCondition = choiceConditionValue;
                            scenario.choice1StartCondition = "Population";
                        } else if (choice1AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Tech")) {
                            scenario.choice1TechCondition = choiceConditionValue;
                            scenario.choice1StartCondition = "Tech";
                        } else System.out.println("Error with choice condition processing.");
                    } else choice1AndConsequence = choice1AndConsequenceAndCondition;
                    
                    
                    scenario.choice1Description = "1. " + choice1AndConsequence.split("@",2)[0];
                    if (choice1AndConsequence.contains("(")) {
                        scenario.choice1Followup = choice1AndConsequence.split("@",2)[1].split("\\(",2)[0];
                    } else {
                        scenario.choice1Followup = choice1AndConsequence.split("@",2)[1];
                    }
                    if (choice1AndConsequence.contains("(")) {
                        choice1Consequence = choice1AndConsequence.split("\\(",2)[1].split("\\)",2)[0];
                        if (choice1Consequence.contains("Gold")) {
                            if (choice1AndConsequence.contains("+")) scenario.choice1GoldConsequence = Integer.parseInt(choice1Consequence.split("\\+",2)[1]);
                            if (choice1AndConsequence.contains("-")) scenario.choice1GoldConsequence = -1 * Integer.parseInt(choice1Consequence.split("-",2)[1]);
                        }
                        if (choice1Consequence.contains("Faith")) {
                            if (choice1AndConsequence.contains("+")) scenario.choice1FaithConsequence = Integer.parseInt(choice1Consequence.split("\\+",2)[1]);
                            if (choice1AndConsequence.contains("-")) scenario.choice1FaithConsequence = -1 * Integer.parseInt(choice1Consequence.split("-",2)[1]);
                        }
                        if (choice1Consequence.contains("Population")) {
                            if (choice1AndConsequence.contains("+")) scenario.choice1PopulationConsequence = Integer.parseInt(choice1Consequence.split("\\+",2)[1]);
                            if (choice1AndConsequence.contains("-")) scenario.choice1PopulationConsequence = -1 * Integer.parseInt(choice1Consequence.split("-",2)[1]);
                        }
                        if (choice1Consequence.contains("Tech")) {
                            if (choice1AndConsequence.contains("+")) scenario.choice1TechConsequence = Integer.parseInt(choice1Consequence.split("\\+",2)[1]);
                            if (choice1AndConsequence.contains("-")) scenario.choice1TechConsequence = -1 * Integer.parseInt(choice1Consequence.split("-",2)[1]);
                        }
                    }
                    
                    
                    break;
                case 2:
                    choice1AndConsequenceAndCondition = allChoices.split("1\\.", 2)[1].split("2\\.",2)[0];
                    choice2AndConsequenceAndCondition = allChoices.split("2\\.", 2)[1];
                    
                    if (choice1AndConsequenceAndCondition.contains("<")) {
                        if (choice1AndConsequenceAndCondition.contains("<")) {
                            choice1AndConsequence = choice1AndConsequenceAndCondition.split(">", 2)[1];
                        } else {
                            choice1AndConsequence = choice1AndConsequenceAndCondition;
                        }
                        if (choice1AndConsequenceAndCondition.contains("<")) choiceConditionValue = Math.abs(Integer.parseInt(choice1AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[1]));
                        if (choice1AndConsequenceAndCondition.contains("<"))  if (choice1AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Gold")) {
                            scenario.choice1GoldCondition = choiceConditionValue;
                            scenario.choice1StartCondition = "Gold";
                        } else if (choice1AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Faith")) {
                            scenario.choice1FaithCondition = choiceConditionValue;
                            scenario.choice1StartCondition = "Faith";
                        } else if (choice1AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Population")) {
                            scenario.choice1PopulationCondition = choiceConditionValue;
                            scenario.choice1StartCondition = "Population";
                        } else if (choice1AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Tech")) {
                            scenario.choice1TechCondition = choiceConditionValue;
                            scenario.choice1StartCondition = "Tech";
                        } else System.out.println("Error with choice condition processing.");
                    }  else choice1AndConsequence = choice1AndConsequenceAndCondition;
                    
                    if (choice2AndConsequenceAndCondition.contains("<")) {
                        if (choice2AndConsequenceAndCondition.contains("<")) {
                            choice2AndConsequence = choice2AndConsequenceAndCondition.split(">", 2)[1];
                        } else {
                            choice2AndConsequence = choice2AndConsequenceAndCondition;
                        }
                        if (choice2AndConsequenceAndCondition.contains("<")) choiceConditionValue = Math.abs(Integer.parseInt(choice2AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[1]));
                        if (choice2AndConsequenceAndCondition.contains("<"))  if (choice2AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Gold")) {
                            scenario.choice2GoldCondition = choiceConditionValue;
                            scenario.choice2StartCondition = "Gold";
                        } else if (choice2AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Faith")) {
                            scenario.choice2FaithCondition = choiceConditionValue;
                            scenario.choice2StartCondition = "Faith";
                        } else if (choice2AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Population")) {
                            scenario.choice2PopulationCondition = choiceConditionValue;
                            scenario.choice2StartCondition = "Population";
                        } else if (choice2AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Tech")) {
                            scenario.choice2TechCondition = choiceConditionValue;
                            scenario.choice2StartCondition = "Tech";
                        } else System.out.println("Error with choice condition processing.");
                    }  else choice2AndConsequence = choice2AndConsequenceAndCondition;
                    
                    
                    scenario.choice1Description = "1. " + choice1AndConsequence.split("@",2)[0];
                    if (choice1AndConsequence.contains("(")) {
                        scenario.choice1Followup = choice1AndConsequence.split("@",2)[1].split("\\(",2)[0];
                    } else {
                        scenario.choice1Followup = choice1AndConsequence.split("@",2)[1];
                    }
                    if (choice1AndConsequence.contains("(")) {
                        choice1Consequence = choice1AndConsequence.split("\\(",2)[1].split("\\)",2)[0];
                        if (choice1Consequence.contains("Gold")) {
                            if (choice1AndConsequence.contains("+")) scenario.choice1GoldConsequence = Integer.parseInt(choice1Consequence.split("\\+",2)[1]);
                            if (choice1AndConsequence.contains("-")) scenario.choice1GoldConsequence = -1 * Integer.parseInt(choice1Consequence.split("-",2)[1]);
                        }
                        if (choice1Consequence.contains("Faith")) {
                            if (choice1AndConsequence.contains("+")) scenario.choice1FaithConsequence = Integer.parseInt(choice1Consequence.split("\\+",2)[1]);
                            if (choice1AndConsequence.contains("-")) scenario.choice1FaithConsequence = -1 * Integer.parseInt(choice1Consequence.split("-",2)[1]);
                        }
                        if (choice1Consequence.contains("Population")) {
                            if (choice1AndConsequence.contains("+")) scenario.choice1PopulationConsequence = Integer.parseInt(choice1Consequence.split("\\+",2)[1]);
                            if (choice1AndConsequence.contains("-")) scenario.choice1PopulationConsequence = -1 * Integer.parseInt(choice1Consequence.split("-",2)[1]);
                        }
                        if (choice1Consequence.contains("Tech")) {
                            if (choice1AndConsequence.contains("+")) scenario.choice1TechConsequence = Integer.parseInt(choice1Consequence.split("\\+",2)[1]);
                            if (choice1AndConsequence.contains("-")) scenario.choice1TechConsequence = -1 * Integer.parseInt(choice1Consequence.split("-",2)[1]);
                        }
                    }
                    
                    scenario.choice2Description = "2. " + choice2AndConsequence.split("@")[0];
                    if (choice2AndConsequence.contains("(")) {
                        scenario.choice2Followup = choice2AndConsequence.split("@",2)[1].split("\\(",2)[0];
                    } else {
                        scenario.choice2Followup = choice2AndConsequence.split("@",2)[1];
                    }
                    if (choice2AndConsequence.contains("(")) {
                        choice2Consequence = choice2AndConsequence.split("\\(",2)[1].split("\\)",2)[0];
                        if (choice2Consequence.contains("Gold")) {
                            if (choice2AndConsequence.contains("+")) scenario.choice2GoldConsequence = Integer.parseInt(choice2Consequence.split("\\+",2)[1]);
                            if (choice2AndConsequence.contains("-")) scenario.choice2GoldConsequence = -1 * Integer.parseInt(choice2Consequence.split("-",2)[1]);
                        }
                        if (choice2Consequence.contains("Faith")) {
                            if (choice2AndConsequence.contains("+")) scenario.choice2FaithConsequence = Integer.parseInt(choice2Consequence.split("\\+",2)[1]);
                            if (choice2AndConsequence.contains("-")) scenario.choice2FaithConsequence = -1 * Integer.parseInt(choice2Consequence.split("-",2)[1]);
                        }
                        if (choice2Consequence.contains("Population")) {
                            if (choice2AndConsequence.contains("+")) scenario.choice2PopulationConsequence = Integer.parseInt(choice2Consequence.split("\\+",2)[1]);
                            if (choice2AndConsequence.contains("-")) scenario.choice2PopulationConsequence = -1 * Integer.parseInt(choice2Consequence.split("-",2)[1]);
                        }
                        if (choice2Consequence.contains("Tech")) {
                            if (choice2AndConsequence.contains("+")) scenario.choice2TechConsequence = Integer.parseInt(choice2Consequence.split("\\+",2)[1]);
                            if (choice2AndConsequence.contains("-")) scenario.choice2TechConsequence = -1 * Integer.parseInt(choice2Consequence.split("-",2)[1]);
                        }
                    }
                    
                    
                    
                    break;
                case 3:
                    choice1AndConsequenceAndCondition = allChoices.split("1\\.", 2)[1].split("2\\.",2)[0];
                    choice2AndConsequenceAndCondition = allChoices.split("2\\.", 2)[1].split("3\\.", 2)[0];
                    choice3AndConsequenceAndCondition = allChoices.split("3\\.", 2)[1];
                    
                    if (choice1AndConsequenceAndCondition.contains("<")) {
                        if (choice1AndConsequenceAndCondition.contains("<")) {
                            choice1AndConsequence = choice1AndConsequenceAndCondition.split(">", 2)[1];
                        } else {
                            choice1AndConsequence = choice1AndConsequenceAndCondition;
                        }
                        if (choice1AndConsequenceAndCondition.contains("<")) choiceConditionValue = Math.abs(Integer.parseInt(choice1AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[1]));
                        if (choice1AndConsequenceAndCondition.contains("<")) if (choice1AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Gold")) {
                            scenario.choice1GoldCondition = choiceConditionValue;
                            scenario.choice1StartCondition = "Gold";
                        } else if (choice1AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Faith")) {
                            scenario.choice1FaithCondition = choiceConditionValue;
                            scenario.choice1StartCondition = "Faith";
                        } else if (choice1AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Population")) {
                            scenario.choice1PopulationCondition = choiceConditionValue;
                            scenario.choice1StartCondition = "Population";
                        } else if (choice1AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Tech")) {
                            scenario.choice1TechCondition = choiceConditionValue;
                            scenario.choice1StartCondition = "Tech";
                        } else System.out.println("Error with choice condition processing.");
                    }  else choice1AndConsequence = choice1AndConsequenceAndCondition;
                    
                    if (choice2AndConsequenceAndCondition.contains("<")) {
                        if (choice2AndConsequenceAndCondition.contains("<")) {
                            choice2AndConsequence = choice2AndConsequenceAndCondition.split(">", 2)[1];
                        } else {
                            choice2AndConsequence = choice2AndConsequenceAndCondition;
                        }
                        if (choice2AndConsequenceAndCondition.contains("<")) choiceConditionValue = Math.abs(Integer.parseInt(choice2AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[1]));
                        if (choice2AndConsequenceAndCondition.contains("<")) if (choice2AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Gold")) {
                            scenario.choice2GoldCondition = choiceConditionValue;
                            scenario.choice2StartCondition = "Gold";
                        } else if (choice2AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Faith")) {
                            scenario.choice2FaithCondition = choiceConditionValue;
                            scenario.choice2StartCondition = "Faith";
                        } else if (choice2AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Population")) {
                            scenario.choice2PopulationCondition = choiceConditionValue;
                            scenario.choice2StartCondition = "Population";
                        } else if (choice2AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Tech")) {
                            scenario.choice2TechCondition = choiceConditionValue;
                            scenario.choice2StartCondition = "Tech";
                        } else System.out.println("Error with choice condition processing.");
                    }  else choice2AndConsequence = choice2AndConsequenceAndCondition;
                    
                    if (choice3AndConsequenceAndCondition.contains("<")) {
                        if (choice3AndConsequenceAndCondition.contains("<")) {
                            choice3AndConsequence = choice3AndConsequenceAndCondition.split(">", 2)[1];
                        } else {
                            choice3AndConsequence = choice3AndConsequenceAndCondition;
                        }
                        if (choice3AndConsequenceAndCondition.contains("<")) choiceConditionValue = Math.abs(Integer.parseInt(choice3AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[1]));
                        if (choice3AndConsequenceAndCondition.contains("<")) if (choice3AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Gold")) {
                            scenario.choice3GoldCondition = choiceConditionValue;
                            scenario.choice3StartCondition = "Gold";
                        } else if (choice3AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Faith")) {
                            scenario.choice3FaithCondition = choiceConditionValue;
                            scenario.choice3StartCondition = "Faith";
                        } else if (choice3AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Population")) {
                            scenario.choice3PopulationCondition = choiceConditionValue;
                            scenario.choice3StartCondition = "Population";
                        } else if (choice3AndConsequenceAndCondition.split("<",2)[1].split(">",2)[0].split(";",2)[0].contains("Tech")) {
                            scenario.choice3TechCondition = choiceConditionValue;
                            scenario.choice3StartCondition = "Tech";
                        } else System.out.println("Error with choice condition processing.");
                    }  else choice3AndConsequence = choice3AndConsequenceAndCondition;
                    
                    
                    scenario.choice1Description = "1. " + choice1AndConsequence.split("@",2)[0];
                    if (choice1AndConsequence.contains("(")) {
                        scenario.choice1Followup = choice1AndConsequence.split("@",2)[1].split("\\(",2)[0];
                    } else {
                        scenario.choice1Followup = choice1AndConsequence.split("@",2)[1];
                    }
                    if (choice1AndConsequence.contains("(")) {
                        choice1Consequence = choice1AndConsequence.split("\\(",2)[1].split("\\)",2)[0];
                        if (choice1Consequence.contains("Gold")) {
                            if (choice1AndConsequence.contains("+")) scenario.choice1GoldConsequence = Integer.parseInt(choice1Consequence.split("\\+",2)[1]);
                            if (choice1AndConsequence.contains("-")) scenario.choice1GoldConsequence = -1 * Integer.parseInt(choice1Consequence.split("-",2)[1]);
                        }
                        if (choice1Consequence.contains("Faith")) {
                            if (choice1AndConsequence.contains("+")) scenario.choice1FaithConsequence = Integer.parseInt(choice1Consequence.split("\\+",2)[1]);
                            if (choice1AndConsequence.contains("-")) scenario.choice1FaithConsequence = -1 * Integer.parseInt(choice1Consequence.split("-",2)[1]);
                        }
                        if (choice1Consequence.contains("Population")) {
                            if (choice1AndConsequence.contains("+")) scenario.choice1PopulationConsequence = Integer.parseInt(choice1Consequence.split("\\+",2)[1]);
                            if (choice1AndConsequence.contains("-")) scenario.choice1PopulationConsequence = -1 * Integer.parseInt(choice1Consequence.split("-",2)[1]);
                        }
                        if (choice1Consequence.contains("Tech")) {
                            if (choice1AndConsequence.contains("+")) scenario.choice1TechConsequence = Integer.parseInt(choice1Consequence.split("\\+",2)[1]);
                            if (choice1AndConsequence.contains("-")) scenario.choice1TechConsequence = -1 * Integer.parseInt(choice1Consequence.split("-",2)[1]);
                        }
                    }
                    
                    scenario.choice2Description = "2. " + choice2AndConsequence.split("@")[0];
                    if (choice2AndConsequence.contains("(")) {
                        scenario.choice2Followup = choice2AndConsequence.split("@",2)[1].split("\\(",2)[0];
                    } else {
                        scenario.choice2Followup = choice2AndConsequence.split("@",2)[1];
                    }
                    if (choice2AndConsequence.contains("(")) {
                        choice2Consequence = choice2AndConsequence.split("\\(",2)[1].split("\\)",2)[0];
                        if (choice2Consequence.contains("Gold")) {
                            if (choice2AndConsequence.contains("+")) scenario.choice2GoldConsequence = Integer.parseInt(choice2Consequence.split("\\+",2)[1]);
                            if (choice2AndConsequence.contains("-")) scenario.choice2GoldConsequence = -1 * Integer.parseInt(choice2Consequence.split("-",2)[1]);
                        }
                        if (choice2Consequence.contains("Faith")) {
                            if (choice2AndConsequence.contains("+")) scenario.choice2FaithConsequence = Integer.parseInt(choice2Consequence.split("\\+",2)[1]);
                            if (choice2AndConsequence.contains("-")) scenario.choice2FaithConsequence = -1 * Integer.parseInt(choice2Consequence.split("-",2)[1]);
                        }
                        if (choice2Consequence.contains("Population")) {
                            if (choice2AndConsequence.contains("+")) scenario.choice2PopulationConsequence = Integer.parseInt(choice2Consequence.split("\\+",2)[1]);
                            if (choice2AndConsequence.contains("-")) scenario.choice2PopulationConsequence = -1 * Integer.parseInt(choice2Consequence.split("-",2)[1]);
                        }
                        if (choice2Consequence.contains("Tech")) {
                            if (choice2AndConsequence.contains("+")) scenario.choice2TechConsequence = Integer.parseInt(choice2Consequence.split("\\+",2)[1]);
                            if (choice2AndConsequence.contains("-")) scenario.choice2TechConsequence = -1 * Integer.parseInt(choice2Consequence.split("-",2)[1]);
                        }
                    }
                    
                    scenario.choice3Description = "3. " + choice3AndConsequence.split("@",2)[0];
                    if (choice3AndConsequence.contains("(")) {
                        scenario.choice3Followup = choice3AndConsequence.split("@",2)[1].split("\\(",2)[0];
                    } else {
                        scenario.choice3Followup = choice3AndConsequence.split("@",2)[1];
                    }
                    if (choice3AndConsequence.contains("(")) {
                        choice3Consequence = choice3AndConsequence.split("\\(",2)[1].split("\\)",2)[0];
                        if (choice3Consequence.contains("Gold")) {
                            if (choice3AndConsequence.contains("+")) scenario.choice3GoldConsequence = Integer.parseInt(choice3Consequence.split("\\+",2)[1]);
                            if (choice3AndConsequence.contains("-")) scenario.choice3GoldConsequence = -1 * Integer.parseInt(choice3Consequence.split("-",2)[1]);
                        }
                        if (choice3Consequence.contains("Faith")) {
                            if (choice3AndConsequence.contains("+")) scenario.choice3FaithConsequence = Integer.parseInt(choice3Consequence.split("\\+",2)[1]);
                            if (choice3AndConsequence.contains("-")) scenario.choice3FaithConsequence = -1 * Integer.parseInt(choice3Consequence.split("-",2)[1]);
                        }
                        if (choice3Consequence.contains("Population")) {
                            if (choice3AndConsequence.contains("+")) scenario.choice3PopulationConsequence = Integer.parseInt(choice3Consequence.split("\\+",2)[1]);
                            if (choice3AndConsequence.contains("-")) scenario.choice3PopulationConsequence = -1 * Integer.parseInt(choice3Consequence.split("-",2)[1]);
                        }
                        if (choice3Consequence.contains("Tech")) {
                            if (choice3AndConsequence.contains("+")) scenario.choice3TechConsequence = Integer.parseInt(choice3Consequence.split("\\+",2)[1]);
                            if (choice3AndConsequence.contains("-")) scenario.choice3TechConsequence = -1 * Integer.parseInt(choice3Consequence.split("-",2)[1]);
                        }
                    }
                    
                    break;
                default:
                    System.out.println("Error with choice separation and processing.");
            }
            
            scenarioObjectList.add(scenario);
        }
        
        return scenarioObjectList;
    }
}
