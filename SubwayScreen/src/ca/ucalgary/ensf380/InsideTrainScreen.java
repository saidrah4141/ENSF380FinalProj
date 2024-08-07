package ca.ucalgary.ensf380;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class InsideTrainScreen {

    private JFrame frame;
    private InsideTrainManager manager;
    private Timer updateTimer;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                String trainCode = JOptionPane.showInputDialog(null,
                        "Enter the train code:",
                        "Train Code",
                        JOptionPane.QUESTION_MESSAGE);

                if (trainCode == null || trainCode.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, 
                            "No train code entered. Exiting...", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(0); 
                }

                InsideTrainScreen window = new InsideTrainScreen(trainCode);
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public InsideTrainScreen(String code) {
        manager = new InsideTrainManager(code);
        initialize();
        startUpdating();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1100, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
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
    	manager.updateTrainData();
        insideDisplay();
    }

    /**
     * @wbp.parser.entryPoint
     */
    private void insideDisplay() {
    	frame.getContentPane().removeAll();

       
        List<String> nextStations = manager.getNextFourStations();
        String previousStation = manager.getPreviousStation();
        String currentStation = manager.getCurrentStation();
        
        
        JPanel previousPanel = new JPanel();
        previousPanel.setBounds(10, 139, 143, 10);
        previousPanel.setBackground(Color.RED);
        frame.getContentPane().add(previousPanel);
       
        JLabel previousStationLabel = new JLabel(previousStation != null ? previousStation : "N/A");
        previousStationLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        previousStationLabel.setBounds(10, 157, 158, 43);
        frame.getContentPane().add(previousStationLabel);

        JLabel currentStationLabel = new JLabel(currentStation != null ? currentStation : "N/A");
        currentStationLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        currentStationLabel.setBounds(171, 157, 158, 43);
        frame.getContentPane().add(currentStationLabel);

        JLabel[] nextStationLabels = new JLabel[4];
        JPanel[] nextStationPanels = new JPanel[4];

        for (int i = 0; i < 4; i++) {
            nextStationLabels[i] = new JLabel((i < nextStations.size() ? nextStations.get(i) : "N/A"));
            nextStationLabels[i].setFont(new Font("Tahoma", Font.PLAIN, 12));
            nextStationLabels[i].setBounds(330 + i * 155, 157, 158, 43);
            frame.getContentPane().add(nextStationLabels[i]);

            nextStationPanels[i] = new JPanel();
            nextStationPanels[i].setBounds(330 + i * 155, 139, 143, 10);
            frame.getContentPane().add(nextStationPanels[i]);

            if (i >= nextStations.size()) {
                nextStationPanels[i].setBackground(Color.GRAY);
    
            } else {
                nextStationPanels[i].setBackground(Color.GREEN);
            }
        }

        
        frame.revalidate();
        frame.repaint();
    }
}
