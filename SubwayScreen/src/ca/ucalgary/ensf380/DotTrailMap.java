package ca.ucalgary.ensf380;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;

public class DotTrailMap extends JPanel {
    private Collection<Coordinate> coordinates;
    private Color color;

    public DotTrailMap(Collection<Coordinate> coordinates, Color color) {
        this.coordinates = coordinates;
        this.color = color;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color); 

        
        double xScale = getWidth() / 1000.0; 
        double yScale = getHeight() / 1000.0; 
        
        for (Coordinate coord : coordinates) {
          
            double scaledX = coord.getX() * 0.8 * xScale;
            double scaledY = coord.getY() * 0.8 * yScale;

            
            int x = (int) scaledX+10;
            int y = (int) scaledY+10;

            g.fillOval(x - 5, y - 5, 10, 10); 
        }
    }
}
