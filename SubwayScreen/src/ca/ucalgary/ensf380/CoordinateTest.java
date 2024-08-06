package ca.ucalgary.ensf380;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinateTest {

    @Test
    public void testConstructorAndGetters() {
        Coordinate coord = new Coordinate(5.0, 10.0);
        assertEquals(5.0, coord.getX(), "X coordinate should be 5.0");
        assertEquals(10.0, coord.getY(), "Y coordinate should be 10.0");
    }

    @Test
    public void testSetters() {
        Coordinate coord = new Coordinate(5.0, 10.0);
        coord.setX(15.0);
        coord.setY(20.0);
        assertEquals(15.0, coord.getX(), "X coordinate should be updated to 15.0");
        assertEquals(20.0, coord.getY(), "Y coordinate should be updated to 20.0");
    }

    @Test
    public void testAdditionalCoordinates() {
        Coordinate coord = new Coordinate(-3.5, 7.8);
        assertEquals(-3.5, coord.getX(), "X coordinate should be -3.5");
        assertEquals(7.8, coord.getY(), "Y coordinate should be 7.8");
        
        coord.setX(0.0);
        coord.setY(0.0);
        assertEquals(0.0, coord.getX(), "X coordinate should be updated to 0.0");
        assertEquals(0.0, coord.getY(), "Y coordinate should be updated to 0.0");
    }
}
