package ca.ucalgary.ensf380;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class TrainTime extends JPanel {
    private static final long serialVersionUID = 1L;

    enum LayoutType {
        ONE_LINE, TWO_LINE, THREE_LINE
    }

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
    private List<String> layout;

    public TrainTime(String station) {
        this.homeStation = new TrainManager(station);
        initialize();
        startUpdating();
    }

    private void initialize() {
        layout = homeStation.getHomeStation().getCommonStations();
        int numStation = layout.size();

        setLayout(null);
        switch (numStation) {
            case 1:
                currentLayout = LayoutType.TWO_LINE;
                twoLineLayout(layout.get(0));
                break;
            case 2:
                currentLayout = LayoutType.THREE_LINE;
                threeLineLayout(layout.get(0), layout.get(1));
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
        updateLayout();
    }

    private void updateLayout() {
        // Clear existing components from the panel
        this.removeAll();

        // Recreate and configure components based on the current layout
        switch (currentLayout) {
            case ONE_LINE:
                oneLineLayout();
                break;
            case TWO_LINE:
                twoLineLayout(layout.get(0));
                break;
            case THREE_LINE:
                threeLineLayout(layout.get(0), layout.get(1));
                break;
        }

        // Refresh the panel to apply changes
        this.revalidate();
        this.repaint();
    }

    private void oneLineLayout() {
        Color leftPanelColor = Color.RED;
        Color rightPanelColor = Color.RED;
        String leftPanelDest = "";
        String rightPanelDest = "";
        int forwardTrainTime = 0;
        int backwardTrainTime = 0;
        
        JLabel Line1 = new JLabel();
        JLabel Line2 = new JLabel();
        
        switch (homeStation.getHomeStation().getStationCode().charAt(0)) {
            case 'R':
                leftPanelColor = Color.RED;
                rightPanelColor = Color.RED;
                if(homeStation.isStartingStation()) {
                    leftPanelDest = redDestForward;
                    rightPanelDest = redDestForward;
                } else if(homeStation.isEndingStation()) {
                    leftPanelDest = redDestBackward;
                    rightPanelDest = redDestBackward;
                } else {
                    leftPanelDest = redDestForward;
                    rightPanelDest = redDestBackward;
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
                    rightPanelDest = blueDestForward;
                } else if(homeStation.isEndingStation()) {
                    leftPanelDest = blueDestBackward;
                    rightPanelDest = blueDestBackward;
                } else {
                    leftPanelDest = blueDestForward;
                    rightPanelDest = blueDestBackward;
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
                    rightPanelDest = greenDestForward;
                } else if(homeStation.isEndingStation()) {
                    leftPanelDest = greenDestBackward;
                    rightPanelDest = greenDestBackward;
                } else {
                    leftPanelDest = greenDestForward;
                    rightPanelDest = greenDestBackward;
                }
                forwardTrainTime = homeStation.getNearestTrainTime("G", "forward");
                backwardTrainTime = homeStation.getNearestTrainTime("G", "backward");
                Line1.setIcon(greenTrainIcon);
                Line2.setIcon(greenTrainIcon);
                break;
        }
        
        // Train Icon Labels
        Line1.setBounds(0, 0, 138, 115);
        this.add(Line1);
        
        Line2.setBounds(607, 0, 138, 115);
        this.add(Line2);

        // Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(138, 0, 469, 115);
        leftPanel.setBackground(leftPanelColor);
        this.add(leftPanel);
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
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(745, 0, 469, 115);
        rightPanel.setBackground(rightPanelColor);
        this.add(rightPanel);
        rightPanel.setLayout(null);

        JLabel backwardStationRight = new JLabel(rightPanelDest);
        backwardStationRight.setBounds(0, 0, 255, 115);
        backwardStationRight.setFont(new Font("Arial Narrow", Font.BOLD, 29));
        backwardStationRight.setForeground(Color.WHITE);
        rightPanel.add(backwardStationRight);

        JLabel backwardTimeRight = new JLabel(String.valueOf(backwardTrainTime) + " min");
        backwardTimeRight.setBounds(265, 25, 92, 64);
        backwardTimeRight.setFont(new Font("Arial Narrow", Font.BOLD, 29));
        backwardTimeRight.setForeground(Color.WHITE);
        rightPanel.add(backwardTimeRight);
    }
    private void threeLineLayout(String station2, String station3) {

        Color leftPanelColor = Color.RED;
        Color centerPanelColor = Color.RED;
        Color rightPanelColor = Color.RED;
        String leftPanelDest1 = "";
        String leftPanelDest2 = "";
        String centerPanelDest1 = "";
        String centerPanelDest2 = "";
        String rightPanelDest1 = "";
        String rightPanelDest2 = "";
        int leftForwardTrainTime = 0;
        int leftBackwardTrainTime = 0;
        int centerForwardTrainTime = 0;
        int centerBackwardTrainTime = 0;
        int rightForwardTrainTime = 0;
        int rightBackwardTrainTime = 0;
        JLabel Line1 = new JLabel();
        JLabel Line2 = new JLabel();
        JLabel Line3 = new JLabel();

        // Setup Left Panel
        switch (homeStation.getHomeStation().getStationCode().charAt(0)) {
            case 'R':
                leftPanelColor = Color.RED;
                leftPanelDest1 = redDestForward;
                leftPanelDest2 = redDestBackward;
                leftForwardTrainTime = homeStation.getNearestTrainTime("R", "forward");
                leftBackwardTrainTime = homeStation.getNearestTrainTime("R", "backward");
                Line1.setIcon(redTrainIcon);
                break;
            case 'B':
                leftPanelColor = Color.BLUE;
                leftPanelDest1 = blueDestForward;
                leftPanelDest2 = blueDestBackward;
                leftForwardTrainTime = homeStation.getNearestTrainTime("B", "forward");
                leftBackwardTrainTime = homeStation.getNearestTrainTime("B", "backward");
                Line1.setIcon(blueTrainIcon);
                break;
            case 'G':
                leftPanelColor = Color.GREEN;
                leftPanelDest1 = greenDestForward;
                leftPanelDest2 = greenDestBackward;
                leftForwardTrainTime = homeStation.getNearestTrainTime("G", "forward");
                leftBackwardTrainTime = homeStation.getNearestTrainTime("G", "backward");
                Line1.setIcon(greenTrainIcon);
                break;
        }

        // Setup Center Panel
        switch (station2.charAt(0)) {
            case 'R':
                centerPanelColor = Color.RED;
                centerPanelDest1 = redDestForward;
                centerPanelDest2 = redDestBackward;
                centerForwardTrainTime = homeStation.getNearestTrainTime("R", "forward");
                centerBackwardTrainTime = homeStation.getNearestTrainTime("R", "backward");
                Line2.setIcon(redTrainIcon);
                break;
            case 'B':
                centerPanelColor = Color.BLUE;
                centerPanelDest1 = blueDestForward;
                centerPanelDest2 = blueDestBackward;
                centerForwardTrainTime = homeStation.getNearestTrainTime("B", "forward");
                centerBackwardTrainTime = homeStation.getNearestTrainTime("B", "backward");
                Line2.setIcon(blueTrainIcon);
                break;
            case 'G':
                centerPanelColor = Color.GREEN;
                centerPanelDest1 = greenDestForward;
                centerPanelDest2 = greenDestBackward;
                centerForwardTrainTime = homeStation.getNearestTrainTime("G", "forward");
                centerBackwardTrainTime = homeStation.getNearestTrainTime("G", "backward");
                Line2.setIcon(greenTrainIcon);
                break;
        }

        // Setup Right Panel
        switch (station3.charAt(0)) {
            case 'R':
                rightPanelColor = Color.RED;
                rightPanelDest1 = redDestForward;
                rightPanelDest2 = redDestBackward;
                rightForwardTrainTime = homeStation.getNearestTrainTime("R", "forward");
                rightBackwardTrainTime = homeStation.getNearestTrainTime("R", "backward");
                Line3.setIcon(redTrainIcon);
                break;
            case 'B':
                rightPanelColor = Color.BLUE;
                rightPanelDest1 = blueDestForward;
                rightPanelDest2 = blueDestBackward;
                rightForwardTrainTime = homeStation.getNearestTrainTime("B", "forward");
                rightBackwardTrainTime = homeStation.getNearestTrainTime("B", "backward");
                Line3.setIcon(blueTrainIcon);
                break;
            case 'G':
                rightPanelColor = Color.GREEN;
                rightPanelDest1 = greenDestForward;
                rightPanelDest2 = greenDestBackward;
                rightForwardTrainTime = homeStation.getNearestTrainTime("G", "forward");
                rightBackwardTrainTime = homeStation.getNearestTrainTime("G", "backward");
                Line3.setIcon(greenTrainIcon);
                break;
        }

        // Train Icon Labels
        Line1.setBounds(0, 0, 138, 115);
        this.add(Line1);

        Line2.setBounds(422, 0, 122, 115);
        this.add(Line2);

        Line3.setBounds(828, 0, 122, 115);
        this.add(Line3);

        // Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(138, 0, 284, 115);
        leftPanel.setBackground(leftPanelColor);
        this.add(leftPanel);
        leftPanel.setLayout(null);

        JLabel forwardStationLeft = new JLabel(leftPanelDest1);
        forwardStationLeft.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        forwardStationLeft.setForeground(Color.WHITE);
        forwardStationLeft.setBounds(0, 0, 209, 68);
        leftPanel.add(forwardStationLeft);

        JLabel forwardTimeLeft = new JLabel(String.valueOf(leftForwardTrainTime) + " min");
        forwardTimeLeft.setBounds(192, 0, 92, 57);
        forwardTimeLeft.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        forwardTimeLeft.setForeground(Color.WHITE);
        leftPanel.add(forwardTimeLeft);

        JLabel backwardStationLeft = new JLabel(leftPanelDest2);
        backwardStationLeft.setForeground(Color.WHITE);
        backwardStationLeft.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        backwardStationLeft.setBounds(0, 47, 209, 68);
        leftPanel.add(backwardStationLeft);

        JLabel backwardTimeLeft = new JLabel(String.valueOf(leftBackwardTrainTime) + " min");
        backwardTimeLeft.setForeground(Color.WHITE);
        backwardTimeLeft.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        backwardTimeLeft.setBounds(192, 58, 92, 57);
        leftPanel.add(backwardTimeLeft);

        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(544, 0, 284, 115);
        centerPanel.setBackground(centerPanelColor);
        this.add(centerPanel);
        centerPanel.setLayout(null);

        JLabel forwardStationCenter = new JLabel(centerPanelDest1);
        forwardStationCenter.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        forwardStationCenter.setForeground(Color.WHITE);
        forwardStationCenter.setBounds(0, 0, 209, 62);
        centerPanel.add(forwardStationCenter);

        JLabel forwardTimeCenter = new JLabel(String.valueOf(centerForwardTrainTime) + " min");
        forwardTimeCenter.setBounds(192, 0, 92, 62);
        forwardTimeCenter.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        forwardTimeCenter.setForeground(Color.WHITE);
        centerPanel.add(forwardTimeCenter);

        JLabel backwardStationCenter = new JLabel(centerPanelDest2);
        backwardStationCenter.setForeground(Color.WHITE);
        backwardStationCenter.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        backwardStationCenter.setBounds(0, 53, 209, 62);
        centerPanel.add(backwardStationCenter);

        JLabel backwardTimeCenter = new JLabel(String.valueOf(centerBackwardTrainTime) + " min");
        backwardTimeCenter.setForeground(Color.WHITE);
        backwardTimeCenter.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        backwardTimeCenter.setBounds(192, 53, 92, 62);
        centerPanel.add(backwardTimeCenter);

        // Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(950, 0, 265, 115);
        rightPanel.setBackground(rightPanelColor);
        this.add(rightPanel);
        rightPanel.setLayout(null);

        JLabel forwardStationRight = new JLabel(rightPanelDest1);
        forwardStationRight.setBounds(0, 0, 209, 64);
        forwardStationRight.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        forwardStationRight.setForeground(Color.WHITE);
        rightPanel.add(forwardStationRight);

        JLabel forwardTimeRight = new JLabel(String.valueOf(rightForwardTrainTime) + " min");
        forwardTimeRight.setBounds(170, 0, 92, 64);
        forwardTimeRight.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        forwardTimeRight.setForeground(Color.WHITE);
        rightPanel.add(forwardTimeRight);

        JLabel backwardStationRight = new JLabel(rightPanelDest2);
        backwardStationRight.setForeground(Color.WHITE);
        backwardStationRight.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        backwardStationRight.setBounds(0, 51, 209, 64);
        rightPanel.add(backwardStationRight);

        JLabel backwardTimeRight = new JLabel(String.valueOf(rightBackwardTrainTime) + " min");
        backwardTimeRight.setForeground(Color.WHITE);
        backwardTimeRight.setFont(new Font("Arial Narrow", Font.BOLD, 22));
        backwardTimeRight.setBounds(170, 51, 92, 64);
        rightPanel.add(backwardTimeRight);

        this.setVisible(true);
    }


    private void twoLineLayout(String station2) {

        Color leftPanelColor = Color.RED;
        Color rightPanelColor = Color.RED;
        String leftPanelDest1 = "";
        String leftPanelDest2 = "";
        String rightPanelDest1 = "";
        String rightPanelDest2 = "";
        int leftForwardTrainTime = 0;
        int leftBackwardTrainTime = 0;
        int rightForwardTrainTime = 0;
        int rightBackwardTrainTime = 0;
        JLabel Line1 = new JLabel();
        JLabel Line2 = new JLabel();

        // Setup Left Panel
        switch (homeStation.getHomeStation().getStationCode().charAt(0)) {
            case 'R':
                leftPanelColor = Color.RED;
                leftPanelDest1 = redDestForward;
                leftPanelDest2 = redDestBackward;
                leftForwardTrainTime = homeStation.getNearestTrainTime("R", "forward");
                leftBackwardTrainTime = homeStation.getNearestTrainTime("R", "backward");
                Line1.setIcon(redTrainIcon);
                break;
            case 'B':
                leftPanelColor = Color.BLUE;
                leftPanelDest1 = blueDestForward;
                leftPanelDest2 = blueDestBackward;
                leftForwardTrainTime = homeStation.getNearestTrainTime("B", "forward");
                leftBackwardTrainTime = homeStation.getNearestTrainTime("B", "backward");
                Line1.setIcon(blueTrainIcon);
                break;
            case 'G':
                leftPanelColor = Color.GREEN;
                leftPanelDest1 = greenDestForward;
                leftPanelDest2 = greenDestBackward;
                leftForwardTrainTime = homeStation.getNearestTrainTime("G", "forward");
                leftBackwardTrainTime = homeStation.getNearestTrainTime("G", "backward");
                Line1.setIcon(greenTrainIcon);
                break;
        }

        // Right Panel
        switch (station2.charAt(0)) {
            case 'R':
                rightPanelColor = Color.RED;
                rightPanelDest1 = redDestForward;
                rightPanelDest2 = redDestBackward;
                rightForwardTrainTime = homeStation.getNearestTrainTime("R", "forward");
                rightBackwardTrainTime = homeStation.getNearestTrainTime("R", "backward");
                Line2.setIcon(redTrainIcon);
                break;
            case 'B':
                rightPanelColor = Color.BLUE;
                rightPanelDest1 = blueDestForward;
                rightPanelDest2 = blueDestBackward;
                rightForwardTrainTime = homeStation.getNearestTrainTime("B", "forward");
                rightBackwardTrainTime = homeStation.getNearestTrainTime("B", "backward");
                Line2.setIcon(blueTrainIcon);
                break;
            case 'G':
                rightPanelColor = Color.GREEN;
                rightPanelDest1 = greenDestForward;
                rightPanelDest2 = greenDestBackward;
                rightForwardTrainTime = homeStation.getNearestTrainTime("G", "forward");
                rightBackwardTrainTime = homeStation.getNearestTrainTime("G", "backward");
                Line2.setIcon(greenTrainIcon);
                break;
        }

        // Train Icon Labels
        Line1.setBounds(0, 0, 138, 115);
        this.add(Line1);

        Line2.setBounds(607, 0, 138, 115);
        this.add(Line2);

        // Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(138, 0, 469, 115);
        leftPanel.setBackground(leftPanelColor);
        this.add(leftPanel);
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

        // Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(745, 0, 469, 115);
        rightPanel.setBackground(rightPanelColor);
        this.add(rightPanel);
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

        this.setVisible(true);
    }

}