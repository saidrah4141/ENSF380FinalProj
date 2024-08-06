package ca.ucalgary.ensf380;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TrainManagerTest {

    private TrainManager trainManager;
    

    @BeforeEach
    public void setUp() throws IOException {
        trainManager = new TrainManager("R10");
    }


    @Test
    public void testInitialization() {
        Station homeStation = trainManager.getHomeStation();
        assertNotNull(homeStation, "Home station should not be null");
        assertEquals("R10", homeStation.getStationCode(), "Home station code should be R10");
    }    
    

    @Test
    public void testNearestForwardTrain() {
        int time = trainManager.getNearestTrainTime("R", "Forward");
        assertEquals(1, time, "The nearest train time for starting station should be 1 minute");
    }

    @Test
    public void testNearestBackwardTrain() {
        
        int time = trainManager.getNearestTrainTime("R","Backward");
        assertEquals(7, time, "The nearest train time for ending station should be 7 minute");
    }

    
}
