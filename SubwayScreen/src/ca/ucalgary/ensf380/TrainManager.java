package ca.ucalgary.ensf380;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainManager {
    private Map<String,Train> trains;
    private Map<String, Station> mapStations;
    private CSVService service = new CSVService();
    private Station homeStation;
    private final String MAP_PATH = "data/map.csv";
    private final String TRAIN_PATH = "data/Trains_1680832574555.csv";
    private final String RED_END="R43";
    private final String RED_START="R01";
    private final String BLUE_END="B44";
    private final String BLUE_START="B01";
    private final String GREEN_END="R33";
    private final String GREEN_START="G01";
    
    public TrainManager(String homeStationCode) {
        this.trains = service.extractTrainData(TRAIN_PATH);
        this.mapStations = service.extractMapData(MAP_PATH);
        this.homeStation=getStationFromCode(homeStationCode);
        
    }
    public Station getHomeStation() {
    	return this.homeStation;
    }
    public Station getStationFromCode(String homeStationCode) {
    	return(this.mapStations.get(homeStationCode));
    }

    public void updateTrainData() {
        for (Train train : this.trains.values()) {
            String lineName = train.getLineName();
            switch (lineName) {
                case "R":
                    updateRedTrain(train);
                    break;
                case "B":
                    updateBlueTrain(train);
                    break;
                case "G":
                    updateGreenTrain(train);
                    break;
                default:
                    break;
            }
        }
    }

    
    private void updateRedTrain(Train train) {
    	if(train.getDirection().equalsIgnoreCase("forward")) {
    		if(train.getStationCode().equals(train.getDestination())) {
    			train.setDirection("backward");
    			train.setStationCode(decrementCode(train.getStationCode()));
    			train.setDestination(RED_START);
    		}else {
    			train.setStationCode(incrementCode(train.getStationCode()));
    		}
    	} else {
    		if(train.getStationCode().equals(train.getDestination())) {
    			train.setDirection("forward");
    			train.setStationCode(decrementCode(train.getStationCode()));
    			train.setDestination(RED_END);
    		}else {
    			train.setStationCode(decrementCode(train.getStationCode()));
    		}
    		
    	}
    }
    
    private void updateBlueTrain(Train train) {
    	if(train.getDirection().equalsIgnoreCase("forward")) {
    		if(train.getStationCode().equals(train.getDestination())) {
    			train.setDirection("backward");
    			train.setStationCode(decrementCode(train.getStationCode()));
    			train.setDestination(BLUE_START);
    		}else {
    			train.setStationCode(incrementCode(train.getStationCode()));
    		}
    	} else {
    		if(train.getStationCode().equals(train.getDestination())) {
    			train.setDirection("forward");
    			train.setStationCode(decrementCode(train.getStationCode()));
    			train.setDestination(BLUE_END);
    		}else {
    			train.setStationCode(decrementCode(train.getStationCode()));
    		}
    		
    	}
    }
    
    private void updateGreenTrain(Train train) {
    	if(train.getDirection().equalsIgnoreCase("forward")) {
    		if(train.getStationCode().equals(train.getDestination())) {
    			train.setDirection("backward");
    			train.setStationCode(decrementCode(train.getStationCode()));
    			train.setDestination(GREEN_START);
    		}else {
    			train.setStationCode(incrementCode(train.getStationCode()));
    		}
    	} else {
    		if(train.getStationCode().equals(train.getDestination())) {
    			train.setDirection("forward");
    			train.setStationCode(decrementCode(train.getStationCode()));
    			train.setDestination(GREEN_END);
    		}else {
    			train.setStationCode(decrementCode(train.getStationCode()));
    		}
    		
    	}
    }

    private static String incrementCode(String code) {
        return changeCode(code, 1);
    }

   
    private static String decrementCode(String code) {
        return changeCode(code, -1);
    }

 
    private static String changeCode(String code, int amount) {
        

        
        char prefix = code.charAt(0);
        int numericPart = Integer.parseInt(code.substring(1));

        
        numericPart += amount;

        
        if (numericPart < 0) {
            numericPart = 0;
        } else if (numericPart > 99) {
            numericPart = 99;
        }

        
        String newCode = String.format("%c%02d", prefix, numericPart);

        return newCode;
    }

    private static int getNumericPart(String code) {
        String numericPart = code.substring(1);
        return Integer.parseInt(numericPart);
    }

    private static int calculateTime(String homeStationCode, String code) {
        
        int homeStationNumericPart = getNumericPart(homeStationCode);
        int codeNumericPart = Integer.parseInt(code.substring(1));

       
        int difference = Math.abs(homeStationNumericPart - codeNumericPart);

       
        int seconds = difference * 15;

       
        int minutes = (int) Math.round(seconds / 60.0);

        return minutes;
    }

    public boolean isStartingStation() {
        String code = this.homeStation.getStationCode();
        return code.equals(RED_START) || code.equals(BLUE_START) || code.equals(GREEN_START);
    }

    public boolean isEndingStation() {
        String code = this.homeStation.getStationCode();
        return code.equals(RED_END) || code.equals(BLUE_END) || code.equals(GREEN_END);
    }
    
  public int getNearestTrainTimeForStartingStation(String lineName) {
     int minTime = 1000;
     String code = this.homeStation.getStationCode();

     for (Train train : this.trains.values()) {
         if (train.getLineName().equalsIgnoreCase(lineName) &&
             train.getDirection().equalsIgnoreCase("backward")) {
             int time = calculateTime(code, train.getStationCode());
             if (time < minTime) {
                 minTime = time;
             }
         }
     }
     return minTime == 1000 ? -1 : minTime; 
 }

 
 public int getNearestTrainTimeForEndingStation(String lineName) {
     int minTime = 1000;
     String code = this.homeStation.getStationCode();

     for (Train train : this.trains.values()) {
         if (train.getLineName().equalsIgnoreCase(lineName) &&
             train.getDirection().equalsIgnoreCase("forward") &&
             train.currentStationNum() <= this.homeStation.getStationNumber()) {
             int time = calculateTime(code, train.getStationCode());
             if (time < minTime) {
                 minTime = time;
             }
         }
     }
     return minTime == 1000 ? -1 : minTime; 
 }

 public int getNearestTrainTime(String lineName, String direction) {
     if (isStartingStation()) {
         return getNearestTrainTimeForStartingStation(lineName);
     } else if (isEndingStation()) {
         return getNearestTrainTimeForEndingStation(lineName);
     } else {
         int minTime = 1000;
         String code = this.homeStation.getStationCode();
         int homeStationNumber = this.homeStation.getStationNumber();

         for (Train train : this.trains.values()) {
             if (train.getLineName().equalsIgnoreCase(lineName) &&
                 train.getDirection().equalsIgnoreCase(direction)) {

                 boolean isValidTrain = false;
                 if (direction.equalsIgnoreCase("forward")) {
                     isValidTrain = train.currentStationNum() <= homeStationNumber;
                 } else if (direction.equalsIgnoreCase("backward")) {
                     isValidTrain = train.currentStationNum() >= homeStationNumber;
                 }

                 if (isValidTrain) {
                     int time = calculateTime(code, train.getStationCode());
                     if (time < minTime) {
                         minTime = time;
                     }
                 }
             }
         }
         return minTime == 1000 ? -1 : minTime; 
     }
 }

    
    
}
