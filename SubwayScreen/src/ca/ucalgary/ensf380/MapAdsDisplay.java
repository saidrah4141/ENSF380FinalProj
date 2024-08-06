package ca.ucalgary.ensf380;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

public class MapAdsDisplay extends JPanel {

    private JLabel imageLabel;
    private MapManager mapManager;
    private Timer displayTimer;
    private AdsManager adsManager;
    private int adId = 1;
    private boolean showAd = true;

    /**
     * Create the panel.
     * @param station the starting station code
     */
    public MapAdsDisplay(String station) {
        this.mapManager = new MapManager(station);
        this.adsManager = new AdsManager();
        setLayout(null);
        setPreferredSize(new Dimension(600, 350));
        initialize();
    }

    private void initialize() {
        setupTimers(); 
    }

    private void setupTimers() {
        
        displayTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showAd) {
                   
                    showAd();
                    showAd = false; 
                    displayTimer.setDelay(5000); 
                } else {
                   
                    showMaps();
                    mapManager.updateTrainData();
                    showAd = true; 
                    displayTimer.setDelay(10000); 
                }
            }
        });

      
        displayTimer.setInitialDelay(10);
        displayTimer.start(); 
    }

    private void showAd() {
        removeAll();
        imageLabel = new JLabel();
        imageLabel.setBounds(0, 0, getWidth(), getHeight()); 
        add(imageLabel);
        ImageIcon adImage = adsManager.getAdImageIcon(adId);
        if (adImage != null) {
            imageLabel.setIcon(adImage);
        } else {
            JOptionPane.showMessageDialog(this, "No advertisement found with ID: 1");
        }
        adId = (adId % 7) + 1;
        
        revalidate();
        repaint();
    }

    private void showMaps() {
        removeAll();
        Collection<Coordinate> redCoordinates = mapManager.getRedCoordinates();
        Collection<Coordinate> blueCoordinates = mapManager.getBlueCoordinates();
        Collection<Coordinate> greenCoordinates = mapManager.getGreenCoordinates();
        Collection<Coordinate> trainCoordinates = mapManager.getTrainCoordinates(); 

        
        DotTrailMap redMapPanel = new DotTrailMap(redCoordinates, Color.RED);
        DotTrailMap blueMapPanel = new DotTrailMap(blueCoordinates, Color.BLUE);
        DotTrailMap greenMapPanel = new DotTrailMap(greenCoordinates, Color.GREEN);
        DotTrailMap trainMapPanel = new DotTrailMap(trainCoordinates, Color.BLACK); 

        
        Dimension panelSize = new Dimension(700, 444);
        redMapPanel.setPreferredSize(panelSize);
        blueMapPanel.setPreferredSize(panelSize);
        greenMapPanel.setPreferredSize(panelSize);
        trainMapPanel.setPreferredSize(panelSize);

        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(panelSize);
        layeredPane.setLayout(null); 

        
        redMapPanel.setBounds(0, 0, panelSize.width, panelSize.height);
        blueMapPanel.setBounds(0, 0, panelSize.width, panelSize.height);
        greenMapPanel.setBounds(0, 0, panelSize.width, panelSize.height);
        trainMapPanel.setBounds(0, 0, panelSize.width, panelSize.height); 

        
        layeredPane.add(greenMapPanel, Integer.valueOf(1)); 
        layeredPane.add(blueMapPanel, Integer.valueOf(2)); 
        layeredPane.add(redMapPanel, Integer.valueOf(3)); 
        layeredPane.add(trainMapPanel, Integer.valueOf(4)); 

        
        setLayout(null);
        layeredPane.setBounds(0, 0, panelSize.width, panelSize.height);
        add(layeredPane);
        revalidate();
        repaint();
    }
}
