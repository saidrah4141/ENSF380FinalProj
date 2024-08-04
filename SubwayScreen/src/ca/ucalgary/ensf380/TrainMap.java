package ca.ucalgary.ensf380;
import java.util.HashMap;
import java.util.Map;

public class TrainMap {
	
    private Map<String,Station> stationMap;
    
    public TrainMap() {
        this.stationMap = new HashMap<>();
    }
    public void addStation(Station station) {
        stationMap.put(station.getStationName(), station);
    }

    public Station getStation(String name) {
        return stationMap.get(name);
    }

    public void printStations() {
        for (Station station : stationMap.values()) {
            System.out.println(station);
        }
    }
}
