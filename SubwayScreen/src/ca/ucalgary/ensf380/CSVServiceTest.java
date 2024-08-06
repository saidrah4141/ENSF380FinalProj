package ca.ucalgary.ensf380;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.HashMap;

public class CSVServiceTest {

    private CSVService csvService;
    
    
    private static final String MAP_FILE_PATH = "data/Map.csv";
    private static final String TRAIN_FILE_PATH = "data/Trains_1680832574555.csv";
    private static final String LINE_FILE_PATH = "data/Red.csv";
    
    @BeforeEach
    public void setUp() {
        csvService = new CSVService();
    }

   
    @Test
    public void testExtractMapData() {
        Map<String, Station> stations = csvService.extractMapData(MAP_FILE_PATH);

        assertNotNull(stations, "Stations map should not be null");
        assertFalse(stations.isEmpty(), "Stations map should not be empty");

        Station station = stations.get("R01"); 
        assertNotNull(station, "Station with code 'R01' should not be null");

        assertEquals("Maplewood Station", station.getStationName(), "Station name should match");
       
    }

    @Test
    public void testExtractTrainData() {
        Map<String, Train> trains = csvService.extractTrainData(TRAIN_FILE_PATH);

        assertNotNull(trains, "Trains map should not be null");
        assertFalse(trains.isEmpty(), "Trains map should not be empty");

        Train train = trains.get("R1"); 
        assertNotNull(train, "Train with line 'R' and number 1 should not be null");

        assertEquals("R", train.getLineName(), "Train line name should match");
        assertEquals(1, train.getTrainNum(), "Train number should match");
        assertEquals("R08", train.getStationCode(), "Station code should match");
        assertEquals("forward", train.getDirection(), "Direction should match");
        assertEquals("R43", train.getDestination(), "Destination should match");
    }

    @Test
    public void testExtractLineCoordinates() {
        Map<Integer, Coordinate> lineCoordinates = csvService.extractLineCoordinates(LINE_FILE_PATH);

        assertNotNull(lineCoordinates, "Line coordinates map should not be null");
        assertFalse(lineCoordinates.isEmpty(), "Line coordinates map should not be empty");

        Coordinate coordinate = lineCoordinates.get(1); 
        assertNotNull(coordinate, "Coordinate at index 1 should not be null");

        assertEquals(8.756969332695, coordinate.getX(), 0.01, "X coordinate should match");
        assertEquals(14.790168762207, coordinate.getY(), 0.01, "Y coordinate should match");
    }
}
