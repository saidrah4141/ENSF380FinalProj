package ca.ucalgary.ensf380;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainTest {

    @Test
    public void testConstructorAndGetters() {
        Train train = new Train("R", 1, "R05", "forward", "R10");
        assertEquals("R", train.getLineName(), "Line Name should be 'R'");
        assertEquals(1, train.getTrainNum(), "Train number should be 1");
        assertEquals("R05", train.getStationCode(), "Station code should be 'R05'");
        assertEquals("forward", train.getDirection(), "Direction should be 'forward'");
        assertEquals("R10", train.getDestination(), "Destination should be 'R10'");
    }

    @Test
    public void testSetters() {
        Train train = new Train("R", 101, "R05", "forward", "R10");
        train.setLineName("B");
        train.setTrainNum(202);
        train.setStationCode("B12");
        train.setDirection("backward");
        train.setDestination("B15");
        
        assertEquals("B", train.getLineName(), "Line Name should be updated to 'B'");
        assertEquals(202, train.getTrainNum(), "Train number should be updated to 202");
        assertEquals("B12", train.getStationCode(), "Station code should be updated to 'B12'");
        assertEquals("backward", train.getDirection(), "Direction should be updated to 'backward'");
        assertEquals("B15", train.getDestination(), "Destination should be updated to 'B15'");
    }

    @Test
    public void testCurrentStationNum() {
        Train train = new Train("G", 2, "G15", "forward", "G20");
        int stationNum = train.currentStationNum();
        assertEquals(15, stationNum, "Current station number should be 15");

        Train train2 = new Train("R", 1, "R10", "backward", "R05");
        int stationNum2 = train2.currentStationNum();
        assertEquals(10, stationNum2, "Current station number should be 10");
    }

    @Test
    public void testGetId() {
        Train train = new Train("R", 2, "R12", "forward", "R18");
        assertEquals("R2", train.getId(), "ID should be 'R2'");
    }

    @Test
    public void testSetCoordinates() {
        Train train = new Train("R", 1, "R05", "forward", "R10");
        train.setX(12.5);
        train.setY(7.3);
        
        assertEquals(12.5, train.getX(), "X coordinate should be updated to 12.5");
        assertEquals(7.3, train.getY(), "Y coordinate should be updated to 7.3");
    }
}
