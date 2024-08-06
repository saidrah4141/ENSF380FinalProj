package ca.ucalgary.ensf380;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;

/**
 * A custom JPanel that displays a trail of dots based on a collection of coordinates.
 * The dots are scaled and colored according to the specified parameters.
 */
public class DotTrailMap extends JPanel {

    private Collection<Coordinate> coordinates;
    private Color color;

    /**
     * Constructs a DotTrailMap with the specified coordinates and color.
     * 
     * @param coordinates a collection of {@link Coordinate} objects representing the points to be displayed
     * @param color the color of the dots to be drawn on the panel
     */
    public DotTrailMap(Collection<Coordinate> coordinates, Color color) {
        this.coordinates = coordinates;
        this.color = color;
        setOpaque(false);
    }

    /**
     * Paints the component by drawing dots at the specified coordinates.
     * 
     * The coordinates are scaled based on the panel's size, and each dot is drawn with the specified color.
     * The coordinates are adjusted to fit within the panel's dimensions.
     * 
     * @param g the {@link Graphics} object used to paint the component
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color); 

        double xScale = getWidth() / 1000.0; 
        double yScale = getHeight() / 1000.0; 

        for (Coordinate coord : coordinates) {
            double scaledX = coord.getX() * 0.8 * xScale;
            double scaledY = coord.getY() * 0.8 * yScale;

            int x = (int) scaledX + 10;
            int y = (int) scaledY + 10;

            g.fillOval(x - 5, y - 5, 10, 10); 
        }
    }
}
