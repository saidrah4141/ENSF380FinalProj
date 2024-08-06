package ca.ucalgary.ensf380;

public class Train implements StationNumber {
    private String lineName;
    private int trainNum;
    private String stationCode;
    private String direction;
    private String destination;
    private double x;
    private double y;
    private int currentStationNum;

    public Train(String lineName, int trainNum, String stationCode, String direction, String destination) {
        this.lineName = lineName;
        this.trainNum = trainNum;
        this.stationCode = stationCode;
        this.direction = direction;
        this.destination = destination;
    }

    public String getLineName() {
        return lineName;
    }

    public int getTrainNum() {
        return trainNum;
    }
    public String getId() {
    	return(lineName+trainNum);
    }

    public String getStationCode() {
        return stationCode;
    }

    public String getDirection() {
        return direction;
    }

    public String getDestination() {
        return destination;
    }
    @Override
    public int currentStationNum() {
            String numericPart = stationCode.substring(1);
            return Integer.parseInt(numericPart);
        
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public void setTrainNum(int trainNum) {
        this.trainNum = trainNum;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public void setX(double x) {
    	this.x=x;
    }
    public void setY(double y) {
    	this.y=y;
    }

    
}
