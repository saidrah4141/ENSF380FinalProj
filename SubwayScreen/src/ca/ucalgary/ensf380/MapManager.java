package ca.ucalgary.ensf380;

import java.util.Map;
import java.util.Set;



import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
public class MapManager {

	private Map<Integer, Coordinate> redMap;
	private Map<Integer,Coordinate> blueMap;
	private Map<Integer,Coordinate> greenMap;
	private CSVService service = new CSVService();
    private Map<String,Train> trains;
    private final String RED_END="R43";
    private final String RED_START="R01";
    private final String BLUE_END="B44";
    private final String BLUE_START="B01";
    private final String GREEN_END="G33";
    private final String GREEN_START="G01";
    private final String TRAIN_PATH = "data/Trains_1680832574555.csv";
    

	
	
	public MapManager(String home) {
		this.redMap=service.extractLineCoordinates("data/Red.csv");
		this.blueMap=service.extractLineCoordinates("data/Blue.csv");
		this.greenMap=service.extractLineCoordinates("data/Green.csv");
		this.trains=service.extractTrainData(TRAIN_PATH);
	}
	
	public Map<String,Train> getTrains(){
		return this.trains;
	}
	public Collection<Coordinate> getRedCoordinates() {
        return redMap.values();
    }

    public Collection<Coordinate> getBlueCoordinates() {
        return blueMap.values();
    }

    public Collection<Coordinate> getGreenCoordinates() {
        return greenMap.values();
    }
    public Coordinate getCoordinateForTrainCode(String code) {
        String lineCode = code.substring(0, 1);
        int coordinateNumber = Integer.parseInt(code.substring(1));
        
        switch (lineCode) {
            case "R":
                return redMap.get(coordinateNumber);
            case "B":
                return blueMap.get(coordinateNumber);
            case "G":
                return greenMap.get(coordinateNumber);
            default:
                return null;
        }
    }

    public Collection<Coordinate> getTrainCoordinates() {
        Set<Coordinate> coordinates = new HashSet<>();
        
       
        for (Train train : trains.values()) {
            String code = train.getStationCode();
            Coordinate coord = getCoordinateForTrainCode(code);
            if (coord != null) {
                coordinates.add(coord);
            }
        }
        updateTrainData();
        return coordinates;
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
    
}

