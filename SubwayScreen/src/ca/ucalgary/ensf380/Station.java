package ca.ucalgary.ensf380;
import java.util.ArrayList;
import java.util.List;

public class Station {

    private String stationName;
    private double x;
    private double y;
    private int stationNumber;    
    private String stationLine;
    private String stationCode;
    private List<String> commonStations;

    public Station(String name, double x, double y, String line, int number,String code) {
        this.stationName = name;
        this.x = x;
        this.y = y;
        this.stationNumber = number;
        this.stationLine = line;
        this.commonStations = new ArrayList<>(); 
        this.stationCode=code;
    }


    public String getStationName() {
        return stationName;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public String getStationLine() {
        return stationLine;
    }
    
    public String getStationCode() {
    	return (stationCode);
    }
    
    
    public void addCommonStation(String commonStation) {
        this.commonStations.add(commonStation);
    }

    
    public List<String> getCommonStations() {
        return commonStations;
    }
   
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public void setStationLine(String stationLine) {
        this.stationLine = stationLine;
    }

    
}
