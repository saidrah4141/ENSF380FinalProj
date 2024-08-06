package ca.ucalgary.ensf380;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StationTest {

    @Test
    public void testConstructorAndGetters() {
        Station station = new Station("Central", 10.5, 20.5, "R", 1, "R01");
        assertEquals("Central", station.getStationName(), "Station name should be 'Central'");
        assertEquals(10.5, station.getX(), "X coordinate should be 10.5");
        assertEquals(20.5, station.getY(), "Y coordinate should be 20.5");
        assertEquals(1, station.getStationNumber(), "Station number should be 1");
        assertEquals("R", station.getStationLine(), "Station line should be 'R'");
        assertEquals("R01", station.getStationCode(), "Station code should be 'R01'");
    }

    @Test
    public void testSetters() {
        Station station = new Station("Central", 10.5, 20.5, "R", 1, "R01");
        station.setStationName("North");
        station.setX(12.0);
        station.setY(22.0);
        station.setStationNumber(2);
        station.setStationLine("B"); 
        
        assertEquals("North", station.getStationName(), "Station name should be updated to 'North'");
        assertEquals(12.0, station.getX(), "X coordinate should be updated to 12.0");
        assertEquals(22.0, station.getY(), "Y coordinate should be updated to 22.0");
        assertEquals(2, station.getStationNumber(), "Station number should be updated to 2");
        assertEquals("B", station.getStationLine(), "Station line should be updated to 'B'");
    }

    @Test
    public void testAddCommonStation() {
        Station station = new Station("Central", 10.5, 20.5, "R", 1, "R01");
        station.addCommonStation("CommonA");
        station.addCommonStation("CommonB");
        
        List<String> commonStations = station.getCommonStations();
        assertTrue(commonStations.contains("CommonA"), "Common stations should contain 'CommonA'");
        assertTrue(commonStations.contains("CommonB"), "Common stations should contain 'CommonB'");
        assertEquals(2, commonStations.size(), "There should be 2 common stations");
    }
}
