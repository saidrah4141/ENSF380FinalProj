package ca.ucalgary.ensf380;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InsideTrainManager {

	
	private Map<String, Train> trains;
	private Train train;
    private CSVService service = new CSVService();
    private Map<String, String> map;
    private final String TRAIN_PATH = "data/Trains_1680832574555.csv";
    private final String MAP_PATH = "data/map.csv";
    private String code;
    private final String RED_END = "R43";
    private final String RED_START = "R01";
    private final String BLUE_END = "B44";
    private final String BLUE_START = "B01";
    private final String GREEN_END = "G33";
    private final String GREEN_START = "G01";
    
    public InsideTrainManager(String code) {
    	this.trains= service.extractTrainData(TRAIN_PATH);
    	this.train = this.trains.get(code);
    	this.map = service.extractMapNameData(MAP_PATH);
    }
    
    public String getPreviousStation() {
    	
    	if (train.currentStationNum()==1) {
    		return(null);
    	} else {
    		String code = decrementCode(train.getStationCode());
    		return(map.get(code));
    	}
    }
    
    public String getCurrentStation() {
    	return(map.get(train.getStationCode()));
    }
    public List<String> getNextFourStations() {
        List<String> nextStations = new ArrayList<>();
        String lineName = train.getLineName();
        String currentCode = train.getStationCode();
        String endCode = getEndCodeForLine(lineName);

        if (train.getDirection().equalsIgnoreCase("forward")) {
            for (int i = 1; i <= 4; i++) {
                String nextCode = incrementCode(currentCode);
                if (nextCode.equals(endCode) || map.get(nextCode) == null) {
                    break;
                }
                nextStations.add(map.get(nextCode));
                currentCode = nextCode;
            }
        } else {
            for (int i = 1; i <= 4; i++) {
                String nextCode = decrementCode(currentCode);
                if (nextCode.equals(getStartCodeForLine(lineName)) || map.get(nextCode) == null) {
                    break;
                }
                nextStations.add(map.get(nextCode));
                currentCode = nextCode;
            }
        }

        // Fill the remaining slots with null if there are fewer than 4 stations
        while (nextStations.size() < 4) {
            nextStations.add(null);
        }

        return nextStations;
    }
    
    private String getEndCodeForLine(String lineName) {
        switch (lineName) {
            case "R":
                return RED_END;
            case "B":
                return BLUE_END;
            case "G":
                return GREEN_END;
            default:
                return null;
        }
    }

    private String getStartCodeForLine(String lineName) {
        switch (lineName) {
            case "R":
                return RED_START;
            case "B":
                return BLUE_START;
            case "G":
                return GREEN_START;
            default:
                return null;
        }
    }
    public void updateTrainData() {
        
            String lineName = this.train.getLineName();
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
    

    /**
     * Updates the data for a red train, changing direction and station code as needed.
     * 
     * @param train the Train object to update
     */
    private void updateRedTrain(Train train) {
        if (train.getDirection().equalsIgnoreCase("forward")) {
            if (train.getStationCode().equals(train.getDestination())) {
                train.setDirection("backward");
                train.setStationCode(decrementCode(train.getStationCode()));
                train.setDestination(RED_START);
            } else {
                train.setStationCode(incrementCode(train.getStationCode()));
            }
        } else {
            if (train.getStationCode().equals(train.getDestination())) {
                train.setDirection("forward");
                train.setStationCode(decrementCode(train.getStationCode()));
                train.setDestination(RED_END);
            } else {
                train.setStationCode(decrementCode(train.getStationCode()));
            }
        }
    }

    /**
     * Updates the data for a blue train, changing direction and station code as needed.
     * 
     * @param train the Train object to update
     */
    private void updateBlueTrain(Train train) {
        if (train.getDirection().equalsIgnoreCase("forward")) {
            if (train.getStationCode().equals(train.getDestination())) {
                train.setDirection("backward");
                train.setStationCode(decrementCode(train.getStationCode()));
                train.setDestination(BLUE_START);
            } else {
                train.setStationCode(incrementCode(train.getStationCode()));
            }
        } else {
            if (train.getStationCode().equals(train.getDestination())) {
                train.setDirection("forward");
                train.setStationCode(decrementCode(train.getStationCode()));
                train.setDestination(BLUE_END);
            } else {
                train.setStationCode(decrementCode(train.getStationCode()));
            }
        }
    }

    /**
     * Updates the data for a green train, changing direction and station code as needed.
     * 
     * @param train the Train object to update
     */
    private void updateGreenTrain(Train train) {
        if (train.getDirection().equalsIgnoreCase("forward")) {
            if (train.getStationCode().equals(train.getDestination())) {
                train.setDirection("backward");
                train.setStationCode(decrementCode(train.getStationCode()));
                train.setDestination(GREEN_START);
            } else {
                train.setStationCode(incrementCode(train.getStationCode()));
            }
        } else {
            if (train.getStationCode().equals(train.getDestination())) {
                train.setDirection("forward");
                train.setStationCode(decrementCode(train.getStationCode()));
                train.setDestination(GREEN_END);
            } else {
                train.setStationCode(decrementCode(train.getStationCode()));
            }
        }
    }
    
    private static String incrementCode(String code) {
        return changeCode(code, 1);
    }

    /**
     * Decrements a station code by 1.
     * 
     * @param code the current station code
     * @return the decremented station code
     */
    private static String decrementCode(String code) {
        return changeCode(code, -1);
    }

    /**
     * Changes the station code by the specified amount.
     * 
     * @param code the current station code
     * @param amount the amount to change the code by (positive or negative)
     * @return the new station code
     */
    private static String changeCode(String code, int amount) {
        char prefix = code.charAt(0);
        int numericPart = Integer.parseInt(code.substring(1));
        numericPart += amount;

        if (numericPart < 0) {
            numericPart = 0;
        } else if (numericPart > 99) {
            numericPart = 99;
        }

        return String.format("%c%02d", prefix, numericPart);
    }

   
	
}

