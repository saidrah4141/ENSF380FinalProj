package ca.ucalgary.ensf380;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a station in a transit system, including its location, line, and associated common stations.
 */
public class Station {

    private String stationName;
    private double x;
    private double y;
    private int stationNumber;    
    private String stationLine;
    private String stationCode;
    private List<String> commonStations;

    /**
     * Constructs a Station with the specified details.
     * 
     * @param name the name of the station
     * @param x the x-coordinate of the station
     * @param y the y-coordinate of the station
     * @param line the line to which the station belongs
     * @param number the number of the station
     * @param code the code of the station
     */
    public Station(String name, double x, double y, String line, int number, String code) {
        this.stationName = name;
        this.x = x;
        this.y = y;
        this.stationNumber = number;
        this.stationLine = line;
        this.commonStations = new ArrayList<>();
        this.stationCode = code;
    }

    /**
     * Returns the name of the station.
     * 
     * @return the name of the station
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * Returns the x-coordinate of the station.
     * 
     * @return the x-coordinate of the station
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the station.
     * 
     * @return the y-coordinate of the station
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the number of the station.
     * 
     * @return the number of the station
     */
    public int getStationNumber() {
        return stationNumber;
    }

    /**
     * Returns the line to which the station belongs.
     * 
     * @return the line of the station
     */
    public String getStationLine() {
        return stationLine;
    }

    /**
     * Returns the code of the station.
     * 
     * @return the code of the station
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     * Adds a common station code to the list of common stations.
     * 
     * @param commonStation the common station code to add
     */
    public void addCommonStation(String commonStation) {
        this.commonStations.add(commonStation);
    }

    /**
     * Returns the list of common station codes.
     * 
     * @return the list of common station codes
     */
    public List<String> getCommonStations() {
        return commonStations;
    }

    /**
     * Sets the name of the station.
     * 
     * @param stationName the new name of the station
     */
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    /**
     * Sets the x-coordinate of the station.
     * 
     * @param x the new x-coordinate of the station
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the station.
     * 
     * @param y the new y-coordinate of the station
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Sets the number of the station.
     * 
     * @param stationNumber the new number of the station
     */
    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    /**
     * Sets the line to which the station belongs.
     * 
     * @param stationLine the new line of the station
     */
    public void setStationLine(String stationLine) {
        this.stationLine = stationLine;
    }
}
