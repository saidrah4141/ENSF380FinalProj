package ca.ucalgary.ensf380;

/**
 * Represents a train in the transit system, including its line, number, current station, direction, and destination.
 */
public class Train implements StationNumber {
    private String lineName;
    private int trainNum;
    private String stationCode;
    private String direction;
    private String destination;
    private double x;
    private double y;
    private int currentStationNum;

    /**
     * Constructs a Train with the specified details.
     * 
     * @param lineName the name of the line the train is on
     * @param trainNum the number of the train
     * @param stationCode the current station code of the train
     * @param direction the direction in which the train is moving
     * @param destination the destination station code of the train
     */
    public Train(String lineName, int trainNum, String stationCode, String direction, String destination) {
        this.lineName = lineName;
        this.trainNum = trainNum;
        this.stationCode = stationCode;
        this.direction = direction;
        this.destination = destination;
    }

    /**
     * Returns the name of the line the train is on.
     * 
     * @return the name of the line
     */
    public String getLineName() {
        return lineName;
    }

    /**
     * Returns the number of the train.
     * 
     * @return the train number
     */
    public int getTrainNum() {
        return trainNum;
    }

    /**
     * Returns a unique identifier for the train, combining the line name and train number.
     * 
     * @return the unique identifier for the train
     */
    public String getId() {
        return lineName + trainNum;
    }

    /**
     * Returns the current station code of the train.
     * 
     * @return the station code
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     * Returns the direction in which the train is moving.
     * 
     * @return the direction of the train
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Returns the destination station code of the train.
     * 
     * @return the destination station code
     */
    public String getDestination() {
        return destination;
    }
    
    /**
     * Returns the x-coordinate of the train's location.
     * 
     * @return the x-coordinate of the train
     */
    public double getX() {
        return this.x;
    }
    
    /**
     * Returns the y-coordinate of the train's location.
     * 
     * @return the y-coordinate of the train
     */
    public double getY() {
        return this.y;
    }

    /**
     * Returns the current station number extracted from the station code.
     * 
     * @return the current station number
     */
    @Override
    public int currentStationNum() {
        String numericPart = stationCode.substring(1);
        return Integer.parseInt(numericPart);
    }

    /**
     * Sets the name of the line the train is on.
     * 
     * @param lineName the new line name
     */
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    /**
     * Sets the number of the train.
     * 
     * @param trainNum the new train number
     */
    public void setTrainNum(int trainNum) {
        this.trainNum = trainNum;
    }

    /**
     * Sets the current station code of the train.
     * 
     * @param stationCode the new station code
     */
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    /**
     * Sets the direction in which the train is moving.
     * 
     * @param direction the new direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Sets the destination station code of the train.
     * 
     * @param destination the new destination station code
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    /**
     * Sets the x-coordinate of the train's location.
     * 
     * @param x the new x-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }
    
    /**
     * Sets the y-coordinate of the train's location.
     * 
     * @param y the new y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }
}
