package ca.ucalgary.ensf380;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages train data, including updating train positions, calculating times to stations,
 * and determining the nearest trains based on the current home station.
 */
public class TrainManager {
    private Map<String, Train> trains;
    private Map<String, Station> mapStations;
    private CSVService service = new CSVService();
    private Station homeStation;
    private final String MAP_PATH = "data/map.csv";
    private final String TRAIN_PATH = "data/Trains_1680832574555.csv";
    private final String RED_END = "R43";
    private final String RED_START = "R01";
    private final String BLUE_END = "B44";
    private final String BLUE_START = "B01";
    private final String GREEN_END = "G33";
    private final String GREEN_START = "G01";

    /**
     * Constructs a TrainManager with the specified home station code.
     * 
     * @param homeStationCode the code of the home station
     */
    public TrainManager(String homeStationCode) {
        this.trains = service.extractTrainData(TRAIN_PATH);
        this.mapStations = service.extractMapData(MAP_PATH);
        this.homeStation = getStationFromCode(homeStationCode);
    }

    /**
     * Returns the home station managed by this TrainManager.
     * 
     * @return the home station
     */
    public Station getHomeStation() {
        return this.homeStation;
    }

    /**
     * Retrieves a Station object based on its code.
     * 
     * @param homeStationCode the code of the station
     * @return the Station object corresponding to the code
     */
    public Station getStationFromCode(String homeStationCode) {
        return this.mapStations.get(homeStationCode);
    }

    /**
     * Updates the data for all trains based on their current state and direction.
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

    /**
     * Increments a station code by 1.
     * 
     * @param code the current station code
     * @return the incremented station code
     */
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

    /**
     * Extracts the numeric part from a station code.
     * 
     * @param code the station code
     * @return the numeric part of the code
     */
    private static int getNumericPart(String code) {
        String numericPart = code.substring(1);
        return Integer.parseInt(numericPart);
    }

    /**
     * Calculates the time in minutes from the home station to a given station code.
     * 
     * @param homeStationCode the code of the home station
     * @param code the code of the destination station
     * @return the time in minutes
     */
    private static int calculateTime(String homeStationCode, String code) {
        int homeStationNumericPart = getNumericPart(homeStationCode);
        int codeNumericPart = getNumericPart(code);
        int difference = Math.abs(homeStationNumericPart - codeNumericPart);
        int seconds = difference * 15;
        return (int) Math.round(seconds / 60.0);
    }

    /**
     * Checks if the home station is a starting station.
     * 
     * @return true if the home station is a starting station, false otherwise
     */
    public boolean isStartingStation() {
        String code = this.homeStation.getStationCode();
        return code.equals(RED_START) || code.equals(BLUE_START) || code.equals(GREEN_START);
    }

    /**
     * Checks if the home station is an ending station.
     * 
     * @return true if the home station is an ending station, false otherwise
     */
    public boolean isEndingStation() {
        String code = this.homeStation.getStationCode();
        return code.equals(RED_END) || code.equals(BLUE_END) || code.equals(GREEN_END);
    }

    /**
     * Finds the nearest train time from the home station to the next starting station.
     * 
     * @param lineName the name of the train line
     * @return the time in minutes to the nearest train
     */
    public int getNearestTrainTimeForStartingStation(String lineName) {
        int minTime = 100;
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
        return minTime;
    }

    /**
     * Finds the nearest train time from the home station to the next ending station.
     * 
     * @param lineName the name of the train line
     * @return the time in minutes to the nearest train
     */
    public int getNearestTrainTimeForEndingStation(String lineName) {
        int minTime = 100;
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
        return minTime;
    }

    /**
     * Finds the nearest train time based on the line name and direction.
     * 
     * @param lineName the name of the train line
     * @param direction the direction of the train ("forward" or "backward")
     * @return the time in minutes to the nearest train
     */
    public int getNearestTrainTime(String lineName, String direction) {
        if (isStartingStation()) {
            return getNearestTrainTimeForStartingStation(lineName);
        } else if (isEndingStation()) {
            return getNearestTrainTimeForEndingStation(lineName);
        } else {
            int minTime = 100;
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
            // if there is no forward train heading towards the early stations calculate when the nearest backward train 
            //will switch to forward and vice versa for end ones
            if (minTime == 100) {
                if (direction.equalsIgnoreCase("forward")) {
                    return getNearestTrainTime(lineName, "backward") + calculateTime(code, "R01"); // All home stations start with 01
                } else if (direction.equalsIgnoreCase("backward")) {
                    switch (lineName) {
                        case "R":
                            return getNearestTrainTime(lineName, "forward") + calculateTime(code, "R43");
                        case "B":
                            return getNearestTrainTime(lineName, "forward") + calculateTime(code, "B44");
                        case "G":
                            return getNearestTrainTime(lineName, "forward") + calculateTime(code, "G33");
                        default:
                            break;
                    }
                }
            }
            return minTime;
        }
    }
}
