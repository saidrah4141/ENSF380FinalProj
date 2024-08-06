package ca.ucalgary.ensf380;

/**
 * Represents a coordinate in a 2D space with x and y values.
 */
public class Coordinate {
    private double x;
    private double y;

    /**
     * Constructs a Coordinate object with specified x and y values.
     * 
     * @param x the x-coordinate value
     * @param y the y-coordinate value
     */
    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate value.
     * 
     * @return the x-coordinate value
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate value.
     * 
     * @param x the new x-coordinate value
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate value.
     * 
     * @return the y-coordinate value
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate value.
     * 
     * @param y the new y-coordinate value
     */
    public void setY(double y) {
        this.y = y;
    }
}
