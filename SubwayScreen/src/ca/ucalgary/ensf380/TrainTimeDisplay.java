package ca.ucalgary.ensf380;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.Label;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;


enum LayoutType {
    ONE_LINE, TWO_LINE, THREE_LINE
}
public class TrainTimeDisplay {

    private JFrame frame;
    
    private LayoutType currentLayout;
    private ImageIcon blueTrainIcon = new ImageIcon("media/bluetrainicon.png");
    private ImageIcon redTrainIcon = new ImageIcon("media/redtrainicon.png");
    private ImageIcon greenTrainIcon = new ImageIcon("media/greentrainicon.png");
    private TrainManager homeStation;
    private String redDestForward = "Hilltop";
    private String redDestBackward = "Maplewood";
    private String blueDestForward = "South Park";
    private String blueDestBackward = "Northside";
    private String greenDestForward = "Broadview Heights";
    private String greenDestBackward = "Midway";
    private Timer updateTimer;
    List<String> layout;
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                // Prompt user for starting station name
                String startingStation = JOptionPane.showInputDialog(null, 
                        "Enter the starting station code:", 
                        "Starting Station", 
                        JOptionPane.QUESTION_MESSAGE);
                
                // Check if the user clicked cancel or entered no value
                if (startingStation == null || startingStation.trim().isEmpty()) {
                    startingStation = "Unknown"; // Default value or handle accordingly
                }

                // Initialize the main GUI with the starting station name
                TrainTimeDisplay window = new TrainTimeDisplay(startingStation);
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     * @wbp.parser.entryPoint
     */
    public TrainTimeDisplay(String station) {
       
            this.homeStation = new TrainManager(station);
            initialize();
            startUpdating();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
    	frame = new JFrame();
    	frame.setBounds(100, 100, 1215, 153);  
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.getContentPane().setLayout(null);
    	
    	layout = homeStation.getHomeStation().getCommonStations();
    	int numStation = layout.size();
    	
    	switch (numStation) {
        case 1:
        	currentLayout = LayoutType.TWO_LINE;
            twoLineLayout(layout.get(0));	
            break;
        case 2:
        	currentLayout = LayoutType.THREE_LINE;
            threeLineLayout(layout.get(0),layout.get(1));
            break;
        
        default:
        	currentLayout = LayoutType.ONE_LINE;
            oneLineLayout();
            break;
    }

    	
    }
    private void startUpdating() {
        updateTimer = new Timer(15000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        updateTimer.start();
    }
    private void updateData() {
        homeStation.updateTrainData();  // Update train data
        // Update your GUI components based on the updated data
        updateLayout();
    }
    private void updateLayout() {
        // Clear existing components from the frame
        frame.getContentPane().removeAll();

        // Recreate and configure components based on the current layout
        switch (currentLayout) {
            case ONE_LINE:
                oneLineLayout();
                break;
            case TWO_LINE:
                twoLineLayout(layout.get(0));
                break;
            case THREE_LINE:
                threeLineLayout(layout.get(0),layout.get(1));
                break;
        }

        // Refresh the frame to apply changes
        frame.revalidate();
        frame.repaint();
    }

    
    private void oneLineLayout() {
    	
    	
    	Color leftPanelColor = Color.RED;
    	Color rightPanelColor = Color.RED;
    	String leftPanelDest ="";
    	String rightPanelDest="";
    	int forwardTrainTime=0;
    	int backwardTrainTime=0;
    	JLabel Line1 = new JLabel();
    	JLabel Line2 = new JLabel();
    	
    	//Check if station is a destination
    	
    	
    	// Setup Left and Right Panel
    	switch (homeStation.getHomeStation().getStationCode().charAt(0)) {
        case 'R':
          
            leftPanelColor = Color.RED;
            rightPanelColor = Color.RED;
            if(homeStation.isStartingStation()) {
            	leftPanelDest = redDestForward;
                rightPanelDest= redDestForward;
        	} else if(homeStation.isEndingStation()) {
        		leftPanelDest = redDestBackward;
                rightPanelDest= redDestBackward;
        	} else {
        		leftPanelDest = redDestForward;
                rightPanelDest= redDestBackward;
        	}
            
            forwardTrainTime = homeStation.getNearestTrainTime("R", "forward");
            backwardTrainTime = homeStation.getNearestTrainTime("R", "backward");
            Line1.setIcon(redTrainIcon);
            Line2.setIcon(redTrainIcon);
            break;
        case 'B':
            leftPanelColor = Color.BLUE;
            rightPanelColor = Color.BLUE;
            if(homeStation.isStartingStation()) {
            	leftPanelDest = blueDestForward;
                rightPanelDest= blueDestForward;
        	} else if(homeStation.isEndingStation()) {
        		leftPanelDest = blueDestBackward;
                rightPanelDest= blueDestBackward;
        	}else {
	            leftPanelDest = blueDestForward;
	            rightPanelDest= blueDestBackward;
        	}
            forwardTrainTime = homeStation.getNearestTrainTime("B", "forward");
            backwardTrainTime = homeStation.getNearestTrainTime("B", "backward");
            Line1.setIcon(blueTrainIcon);
            Line2.setIcon(blueTrainIcon);

            break;
        case 'G':
            leftPanelColor = Color.GREEN;
            rightPanelColor = Color.GREEN;
            if(homeStation.isStartingStation()) {
            	leftPanelDest = greenDestForward;
                rightPanelDest= greenDestForward;
        	} else if(homeStation.isEndingStation()) {
        		leftPanelDest = greenDestBackward;
                rightPanelDest= greenDestBackward;
        	}else {
	            leftPanelDest = greenDestForward;
	            rightPanelDest= greenDestBackward;
        	}
          
            forwardTrainTime = homeStation.getNearestTrainTime("G", "forward");
            backwardTrainTime = homeStation.getNearestTrainTime("G", "backward");
            Line1.setIcon(greenTrainIcon);
            Line2.setIcon(greenTrainIcon);

       
    }
    	
    	// Train Icon Labels
    	

    	Line1.setBounds(0, 0, 138, 115); 
    	frame.getContentPane().add(Line1);
    	
    	Line2.setBounds(607, 0, 138, 115);  
    	frame.getContentPane().add(Line2);

    	
    
    	// Left Panel
    	Panel leftPanel = new Panel();
    	leftPanel.setBounds(138, 0, 469, 115);
    	leftPanel.setBackground(leftPanelColor);
    	frame.getContentPane().add(leftPanel);
    	leftPanel.setLayout(null);

    	JLabel forwardStationLeft = new JLabel(leftPanelDest);
    	forwardStationLeft.setFont(new Font("Arial Narrow", Font.BOLD, 29));
    	forwardStationLeft.setForeground(Color.WHITE);
    	forwardStationLeft.setBounds(0, 0, 231, 115);
    	leftPanel.add(forwardStationLeft);

    	JLabel forwardTimeLeft = new JLabel(String.valueOf(forwardTrainTime) + " min");
    	forwardTimeLeft.setBounds(287, 29, 92, 57);
    	forwardTimeLeft.setFont(new Font("Arial Narrow", Font.BOLD, 29));
    	forwardTimeLeft.setForeground(Color.WHITE);
    	leftPanel.add(forwardTimeLeft);


    	// Right Panel
    	Panel rightPanel = new Panel();
    	rightPanel.setBounds(745, 0, 469, 115);  
    	rightPanel.setBackground(rightPanelColor);
    	frame.getContentPane().add(rightPanel);
    	rightPanel.setLayout(null);

    	JLabel backwardStationRight = new JLabel(rightPanelDest);
    	backwardStationRight.setBounds(0, 0, 255, 115);
    	backwardStationRight.setFont(new Font("Arial Narrow", Font.BOLD, 29));
    	backwardStationRight.setForeground(Color.WHITE);
    	rightPanel.add(backwardStationRight);

    	JLabel backwardTimeRight = new JLabel(String.valueOf(backwardTrainTime)+" min");
    	backwardTimeRight.setBounds(265, 25, 92, 64);
    	backwardTimeRight.setFont(new Font("Arial Narrow", Font.BOLD, 29));
    	backwardTimeRight.setForeground(Color.WHITE);
    	rightPanel.add(backwardTimeRight);
    	frame.setVisible(true);
    }
    private void threeLineLayout(String station2, String station3) {

    	
    	
    	Color leftPanelColor = Color.RED;
    	Color rightPanelColor = Color.RED;
    	Color centerPanelColor= Color.RED;
    	String leftPanelDest1 ="";
    	String leftPanelDest2 ="";
    	String rightPanelDest1="";
    	String rightPanelDest2="";
    	String centerPanelDest1="";
    	String centerPanelDest2="";
    	int leftForwardTrainTime=0;
    	int leftBackwardTrainTime=0;
    	int rightForwardTrainTime=0;
    	int rightBackwardTrainTime=0;
    	int centerForwardTrainTime=0;
    	int centerBackwardTrainTime=0;
    	JLabel Line1 = new JLabel();
    	JLabel Line2 = new JLabel();
    	JLabel Line3 = new JLabel();
    	

    	// Setup Left Panel
    	switch (homeStation.getHomeStation().getStationCode().charAt(0)) {
        case 'R':
            leftPanelColor = Color.RED;
            leftPanelDest1 = redDestForward;
            leftPanelDest2= redDestBackward;
            leftForwardTrainTime = homeStation.getNearestTrainTime("R", "forward");
            leftBackwardTrainTime = homeStation.getNearestTrainTime("R", "backward");
            Line1.setIcon(redTrainIcon);
            break;
        case 'B':
            leftPanelColor = Color.BLUE;
            leftPanelDest1 = blueDestForward;
            leftPanelDest2= blueDestBackward;
            leftForwardTrainTime = homeStation.getNearestTrainTime("B", "forward");
            leftBackwardTrainTime = homeStation.getNearestTrainTime("B", "backward");
            Line1.setIcon(blueTrainIcon);
            break;
        case 'G':
            leftPanelColor = Color.GREEN;
            leftPanelDest1 = greenDestForward;
            leftPanelDest2= greenDestBackward;
            leftForwardTrainTime = homeStation.getNearestTrainTime("G", "forward");
            leftBackwardTrainTime = homeStation.getNearestTrainTime("G", "backward");
            Line1.setIcon(greenTrainIcon);
       
    }
    	
    	// Setup Right Panel
    	switch (station2.charAt(0)) {
        case 'R':
            rightPanelColor = Color.RED;
            rightPanelDest1 = redDestForward;
            rightPanelDest2= redDestBackward;
            rightForwardTrainTime = homeStation.getNearestTrainTime("R", "forward");
            rightBackwardTrainTime = homeStation.getNearestTrainTime("R", "backward");
            Line3.setIcon(redTrainIcon);
            break;
        case 'B':
        	rightPanelColor = Color.BLUE;
        	rightPanelDest1 = blueDestForward;
        	rightPanelDest2= blueDestBackward;
        	rightForwardTrainTime = homeStation.getNearestTrainTime("B", "forward");
        	rightBackwardTrainTime = homeStation.getNearestTrainTime("B", "backward");
            Line3.setIcon(blueTrainIcon);
            break;
        case 'G':
        	rightPanelColor = Color.GREEN;
        	rightPanelDest1 = greenDestForward;
        	rightPanelDest2= greenDestBackward;
        	rightForwardTrainTime = homeStation.getNearestTrainTime("G", "forward");
        	rightBackwardTrainTime = homeStation.getNearestTrainTime("G", "backward");
            Line3.setIcon(greenTrainIcon);
       
    }
    	
    	// Setup Center Panel
    	switch (station3.charAt(0)) {
        case 'R':
            centerPanelColor = Color.RED;
            centerPanelDest1 = redDestForward;
            centerPanelDest2= redDestBackward;
            centerForwardTrainTime = homeStation.getNearestTrainTime("R", "forward");
            centerBackwardTrainTime = homeStation.getNearestTrainTime("R", "backward");
            Line2.setIcon(redTrainIcon);
            break;
        case 'B':
        	centerPanelColor = Color.BLUE;
        	centerPanelDest1 = blueDestForward;
        	centerPanelDest2= blueDestBackward;
        	centerForwardTrainTime = homeStation.getNearestTrainTime("B", "forward");
        	centerBackwardTrainTime = homeStation.getNearestTrainTime("B", "backward");
        	Line2.setIcon(blueTrainIcon);
            break;
        case 'G':
        	centerPanelColor = Color.GREEN;
        	centerPanelDest1 = greenDestForward;
        	centerPanelDest2= greenDestBackward;
        	centerForwardTrainTime = homeStation.getNearestTrainTime("G", "forward");
        	centerBackwardTrainTime = homeStation.getNearestTrainTime("G", "backward");
        	Line2.setIcon(greenTrainIcon);
       
    }
	
	
    	// Train Icons
    
    	Line1.setBounds(0, 0, 138, 115); 
    	frame.getContentPane().add(Line1);
    	
    	Line2.setBounds(422, 0, 122, 115);  
    	frame.getContentPane().add(Line2);
    	
    	Line3.setBounds(828, 0, 122, 115);  
    	frame.getContentPane().add(Line3);

    

    	// Left Panel
    	Panel leftPanel = new Panel();
    	leftPanel.setBounds(138, 0, 284, 115);
    	leftPanel.setBackground(leftPanelColor);
    	frame.getContentPane().add(leftPanel);
    	leftPanel.setLayout(null);

    	JLabel forwardStationLeft = new JLabel(leftPanelDest1);
    	forwardStationLeft.setFont(new Font("Arial Narrow", Font.BOLD, 22));
    	forwardStationLeft.setForeground(Color.WHITE);
    	forwardStationLeft.setBounds(0, 0, 209, 68);
    	leftPanel.add(forwardStationLeft);

    	JLabel forwardTimeLeft = new JLabel(String.valueOf(leftForwardTrainTime)+" min");
    	forwardTimeLeft.setBounds(192, 0, 92, 57);
    	forwardTimeLeft.setFont(new Font("Arial Narrow", Font.BOLD, 22));
    	forwardTimeLeft.setForeground(Color.WHITE);
    	leftPanel.add(forwardTimeLeft);
    	
    	JLabel backwardStationLeft = new JLabel(leftPanelDest2);
    	backwardStationLeft.setForeground(Color.WHITE);
    	backwardStationLeft.setFont(new Font("Arial Narrow", Font.BOLD, 22));
    	backwardStationLeft.setBounds(0, 47, 209, 68);
    	leftPanel.add(backwardStationLeft);
    	
    	JLabel backwardTimeLeft = new JLabel(String.valueOf(leftBackwardTrainTime)+" min");
    	backwardTimeLeft.setForeground(Color.WHITE);
    	backwardTimeLeft.setFont(new Font("Arial Narrow", Font.BOLD, 22));
    	backwardTimeLeft.setBounds(192, 58, 92, 57);
    	leftPanel.add(backwardTimeLeft);

    	// Center Panel
    	Panel centerPanel = new Panel();
    	centerPanel.setBounds(544, 0, 284, 115);  
    	centerPanel.setBackground(centerPanelColor);
    	frame.getContentPane().add(centerPanel);
    	centerPanel.setLayout(null);

    	JLabel forwardStationCenter = new JLabel(centerPanelDest1);
    	forwardStationCenter.setFont(new Font("Arial Narrow", Font.BOLD, 22));
    	forwardStationCenter.setForeground(Color.WHITE);
    	forwardStationCenter.setBounds(0, 0, 209, 62);
    	centerPanel.add(forwardStationCenter);

    	JLabel forwardTimeCenter = new JLabel(String.valueOf(centerForwardTrainTime)+" min");
    	forwardTimeCenter.setBounds(192, 0, 92, 62);
    	forwardTimeCenter.setFont(new Font("Arial Narrow", Font.BOLD, 22));
    	forwardTimeCenter.setForeground(Color.WHITE);
    	centerPanel.add(forwardTimeCenter);
    	
    	JLabel backwardStationCenter = new JLabel(centerPanelDest2);
    	backwardStationCenter.setForeground(Color.WHITE);
    	backwardStationCenter.setFont(new Font("Arial Narrow", Font.BOLD, 22));
    	backwardStationCenter.setBounds(0, 53, 209, 62);
    	centerPanel.add(backwardStationCenter);
    	
    	JLabel backwardTimeCenter = new JLabel(String.valueOf(centerBackwardTrainTime)+" min");
    	backwardTimeCenter.setForeground(Color.WHITE);
    	backwardTimeCenter.setFont(new Font("Arial Narrow", Font.BOLD, 22));
    	backwardTimeCenter.setBounds(192, 53, 92, 62);
    	centerPanel.add(backwardTimeCenter);

    	// Right Panel
    	Panel rightPanel = new Panel();
    	rightPanel.setBounds(950, 0, 265, 115);  
    	rightPanel.setBackground(rightPanelColor);
    	frame.getContentPane().add(rightPanel);
    	rightPanel.setLayout(null);

    	JLabel forwardStationRight = new JLabel(rightPanelDest1);
    	forwardStationRight.setBounds(0, 0, 209, 64);
    	forwardStationRight.setFont(new Font("Arial Narrow", Font.BOLD, 22));
    	forwardStationRight.setForeground(Color.WHITE);
    	rightPanel.add(forwardStationRight);

    	JLabel forwardTimeRight = new JLabel(String.valueOf(rightForwardTrainTime)+" min");
    	forwardTimeRight.setBounds(163, 0, 92, 64);
    	forwardTimeRight.setFont(new Font("Arial Narrow", Font.BOLD, 22));
    	forwardTimeRight.setForeground(Color.WHITE);
    	rightPanel.add(forwardTimeRight);
    	
    	JLabel backwardStationRight = new JLabel(rightPanelDest2);
    	backwardStationRight.setForeground(Color.WHITE);
    	backwardStationRight.setFont(new Font("Arial Narrow", Font.BOLD, 22));
    	backwardStationRight.setBounds(0, 51, 209, 64);
    	rightPanel.add(backwardStationRight);
    	
    	JLabel backwardTimeRight = new JLabel(String.valueOf(rightBackwardTrainTime)+" min");
    	backwardTimeRight.setForeground(Color.WHITE);
    	backwardTimeRight.setFont(new Font("Arial Narrow", Font.BOLD, 22));
    	backwardTimeRight.setBounds(163, 51, 92, 64);
    	rightPanel.add(backwardTimeRight);
    	frame.setVisible(true);

    }
    private void twoLineLayout(String station2) {

    	
    	
    	Color leftPanelColor = Color.RED;
    	Color rightPanelColor = Color.RED;
    	String leftPanelDest1 ="";
    	String leftPanelDest2 ="";
    	String rightPanelDest1="";
    	String rightPanelDest2="";
    	int leftForwardTrainTime=0;
    	int leftBackwardTrainTime=0;
    	int rightForwardTrainTime=0;
    	int rightBackwardTrainTime=0;
    	JLabel Line1 = new JLabel();
    	JLabel Line2 = new JLabel();
    	
    	// Setup Left Panel
    	switch (homeStation.getHomeStation().getStationCode().charAt(0)) {
        case 'R':
            leftPanelColor = Color.RED;
            leftPanelDest1 = redDestForward;
            leftPanelDest2= redDestBackward;
            leftForwardTrainTime = homeStation.getNearestTrainTime("R", "forward");
            leftBackwardTrainTime = homeStation.getNearestTrainTime("R", "backward");
            Line1.setIcon(redTrainIcon);
            break;
        case 'B':
            leftPanelColor = Color.BLUE;
            leftPanelDest1 = blueDestForward;
            leftPanelDest2= blueDestBackward;
            leftForwardTrainTime = homeStation.getNearestTrainTime("B", "forward");
            leftBackwardTrainTime = homeStation.getNearestTrainTime("B", "backward");
            Line1.setIcon(blueTrainIcon);
            break;
        case 'G':
            leftPanelColor = Color.GREEN;
            leftPanelDest1 = greenDestForward;
            leftPanelDest2= greenDestBackward;
            leftForwardTrainTime = homeStation.getNearestTrainTime("G", "forward");
            leftBackwardTrainTime = homeStation.getNearestTrainTime("G", "backward");
            Line1.setIcon(greenTrainIcon);
       
    }
    	
    	// Right Panel
    	switch (station2.charAt(0)) {
        case 'R':
            rightPanelColor = Color.RED;
            rightPanelDest1 = redDestForward;
            rightPanelDest2= redDestBackward;
            rightForwardTrainTime = homeStation.getNearestTrainTime("R", "forward");
            rightBackwardTrainTime = homeStation.getNearestTrainTime("R", "backward");
            Line2.setIcon(redTrainIcon);
            break;
        case 'B':
        	rightPanelColor = Color.BLUE;
        	rightPanelDest1 = blueDestForward;
        	rightPanelDest2= blueDestBackward;
        	rightForwardTrainTime = homeStation.getNearestTrainTime("B", "forward");
        	rightBackwardTrainTime = homeStation.getNearestTrainTime("B", "backward");
            Line2.setIcon(blueTrainIcon);
            break;
        case 'G':
        	rightPanelColor = Color.GREEN;
        	rightPanelDest1 = greenDestForward;
        	rightPanelDest2= greenDestBackward;
        	rightForwardTrainTime = homeStation.getNearestTrainTime("G", "forward");
        	rightBackwardTrainTime = homeStation.getNearestTrainTime("G", "backward");
            Line2.setIcon(greenTrainIcon);
       
    }
        
        
    	Line2.setBounds(607, 0, 138, 115);
        frame.getContentPane().add(Line2);
        
        
       
        Line1.setBounds(0, 0, 138, 115); 
		frame.getContentPane().add(Line1);
        
		//Left Panel
		Panel leftPanel = new Panel();
		leftPanel.setBounds(138, 0, 469, 115);
        leftPanel.setBackground(leftPanelColor);
        frame.getContentPane().add(leftPanel);
        leftPanel.setLayout(null);
        
        JLabel forwardStationLeft = new JLabel(leftPanelDest1);
        forwardStationLeft.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        forwardStationLeft.setForeground(Color.WHITE);
        forwardStationLeft.setBounds(0, 0, 174, 115);
        leftPanel.add(forwardStationLeft);
        
        JLabel forwardTimeLeft = new JLabel(String.valueOf(leftForwardTrainTime) + " min");
        forwardTimeLeft.setBounds(173, 0, 92, 115);
        forwardTimeLeft.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        forwardTimeLeft.setForeground(Color.WHITE);
        leftPanel.add(forwardTimeLeft);
        
        JLabel backwardStationLeft = new JLabel(leftPanelDest2);
        backwardStationLeft.setForeground(Color.WHITE);
        backwardStationLeft.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        backwardStationLeft.setBounds(237, 0, 174, 115);
        leftPanel.add(backwardStationLeft);
        
        JLabel backwardTimeLeft = new JLabel(String.valueOf(leftBackwardTrainTime) + " min");
        backwardTimeLeft.setForeground(Color.WHITE);
        backwardTimeLeft.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        backwardTimeLeft.setBounds(411, 0, 92, 115);
        leftPanel.add(backwardTimeLeft);
        
        
        
        //Right Panel
        Panel rightPanel = new Panel();
        rightPanel.setBounds(745, 0, 469, 115);
        rightPanel.setBackground(rightPanelColor);
        frame.getContentPane().add(rightPanel);
        rightPanel.setLayout(null);
        
        JLabel forwardStationRight = new JLabel(rightPanelDest1);
        forwardStationRight.setBounds(0, 0, 209, 115);
        forwardStationRight.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        forwardStationRight.setForeground(Color.WHITE);
        rightPanel.add(forwardStationRight);
        
        JLabel forwardTimeRight = new JLabel(String.valueOf(rightForwardTrainTime) + " min");
        forwardTimeRight.setBounds(171, 0, 92, 115);
        forwardTimeRight.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        forwardTimeRight.setForeground(Color.WHITE);
        rightPanel.add(forwardTimeRight);
        
        JLabel backwardStationRight = new JLabel(rightPanelDest2);
        backwardStationRight.setForeground(Color.WHITE);
        backwardStationRight.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        backwardStationRight.setBounds(229, 0, 209, 115);
        rightPanel.add(backwardStationRight);
        
        JLabel backwardTimeRight = new JLabel(String.valueOf(rightBackwardTrainTime) + " min");
        backwardTimeRight.setForeground(Color.WHITE);
        backwardTimeRight.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        backwardTimeRight.setBounds(397, 0, 92, 115);
        rightPanel.add(backwardTimeRight);
        frame.setVisible(true);
    }
   
   
}