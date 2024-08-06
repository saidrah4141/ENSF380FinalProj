package ca.ucalgary.ensf380;

import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Manages the maps and coordinates for different train lines and provides functionality for train data management.
 * The class uses a CSVService to extract line coordinates and train data.
 */
public class MapManager {

    private Map<Integer, Coordinate> redMap;
    private Map<Integer, Coordinate> blueMap;
    private Map<Integer, Coordinate> greenMap;
    private CSVService service = new CSVService();
    private Map<String, Train> trains;
    private final String RED_END = "R43";
    private final String RED_START = "R01";
    private final String BLUE_END = "B44";
    private final String BLUE_START = "B01";
    private final String GREEN_END = "G33";
    private final String GREEN_START = "G01";
    private final String TRAIN_PATH = "data/Trains_1680832574555.csv";

    /**
     * Constructs a MapManager and initializes the maps with line coordinates and trains data.
     * 
     * @param home the path to the home directory (currently unused)
     */
    public MapManager(String home) {
        this.redMap = service.extractLineCoordinates("data/Red.csv");
        this.blueMap = service.extractLineCoordinates("data/Blue.csv");
        this.greenMap = service.extractLineCoordinates("data/Green.csv");
        this.trains = service.extractTrainData(TRAIN_PATH);
    }

    /**
     * Gets the map of trains.
     * 
     * @return a Map where the key is the train identifier and the value is the Train object
     */
    public Map<String, Train> getTrains() {
        return this.trains;
    }

    /**
     * Gets the collection of coordinates for the red line.
     * 
     * @return a Collection of Coordinate objects for the red line
     */
    public Collection<Coordinate> getRedCoordinates() {
        return redMap.values();
    }

    /**
     * Gets the collection of coordinates for the blue line.
     * 
     * @return a Collection of Coordinate objects for the blue line
     */
    public Collection<Coordinate> getBlueCoordinates() {
        return blueMap.values();
    }

    /**
     * Gets the collection of coordinates for the green line.
     * 
     * @return a Collection of Coordinate objects for the green line
     */
    public Collection<Coordinate> getGreenCoordinates() {
        return greenMap.values();
    }

    /**
     * Gets the Coordinate object for a given train code.
     * 
     * @param code the train code
     * @return the Coordinate object corresponding to the train code, or null if not found
     */
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

    /**
     * Gets the collection of coordinates for all trains.
     * 
     * @return a Collection of Coordinate objects for all trains
     */
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

    /**
     * Updates the train data, including train directions and positions, based on their current state and destination.
     */
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

    /**
     * Updates the data for a red line train, including direction and station code.
     * 
     * @param train the Train object to be updated
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
     * Updates the data for a blue line train, including direction and station code.
     * 
     * @param train the Train object to be updated
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
     * Updates the data for a green line train, including direction and station code.
     * 
     * @param train the Train object to be updated
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

    /**
     * Increments the station code by one.
     * 
     * @param code the current station code
     * @return the incremented station code
     */
    private static String incrementCode(String code) {
        return changeCode(code, 1);
    }

    /**
     * Decrements the station code by one.
     * 
     * @param code the current station code
     * @return the decremented station code
     */
    private static String decrementCode(String code) {
        return changeCode(code, -1);
    }

    /**
     * Changes the station code by a specified amount.
     * 
     * @param code the current station code
     * @param amount the amount to change the station code by
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
