package ca.ucalgary.ensf380;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TrainTimeDisplayApp {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	
                String cityCode = JOptionPane.showInputDialog(null,
                        "Enter the city code:",
                        "City Code",
                        JOptionPane.QUESTION_MESSAGE);

                
                if (cityCode == null || cityCode.trim().isEmpty()) {
                    cityCode = "5913490"; 
                }
                
                String startingStation = JOptionPane.showInputDialog(null,
                        "Enter the starting station code:",
                        "Starting Station",
                        JOptionPane.QUESTION_MESSAGE);

                
                if (startingStation == null || startingStation.trim().isEmpty()) {
                    startingStation = "R01"; 
                }

                
                String newsQuery = JOptionPane.showInputDialog(null, 
                        "Enter your news query:", 
                        "News Query", 
                        JOptionPane.QUESTION_MESSAGE);
                
                
                if (newsQuery == null || newsQuery.trim().isEmpty()) {
                    newsQuery = "Calgary";
                }
                
                /*
                String directoryPath = "C:\\Users\\nemoi\\Desktop\\SubwayScreen\\ENSF380FinalProj\\SubwayScreen\\out"; 
                String fileExtension = ".csv"; 
                String jarFilePath = "C:\\Users\\nemoi\\Downloads\\SubwaySimulator.jar";
                String inputPath = "C:\\Users\\nemoi\\Desktop\\SubwayScreen\\ENSF380FinalProj\\SubwayScreen\\data\\subway.csv";
                String outputPath = "C:\\Users\\nemoi\\Desktop\\SubwayScreen\\ENSF380FinalProj\\SubwayScreen\\out";
                
                
                JarRunner jarRunner = new JarRunner(jarFilePath, inputPath, outputPath);
                jarRunner.runJar();
                
                FileFinder fileFinder = new FileFinder(directoryPath, fileExtension);
                fileFinder.searchFiles();

                String latestFilePath = fileFinder.getLatestFile();
                 */
               
                JFrame frame = new JFrame("Train Time Display");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1215, 750);
                frame.setLayout(null); 

               
                JPanel trainPanel = new TrainTime(startingStation);
                trainPanel.setBounds(0, 597, 1215, 153); 
                frame.add(trainPanel); 

                
                NewsDisplay newsPanel = new NewsDisplay(newsQuery);
                newsPanel.setBounds(0, 444, 1215, 153); 
                frame.add(newsPanel); 
                
                
                MapAdsDisplay mapAdsPanel = new MapAdsDisplay(startingStation);
                mapAdsPanel.setBounds(0, 0, 700, 444); 
                frame.add(mapAdsPanel); 
                
                
                WeatherDisplay weatherPanel = new WeatherDisplay(cityCode); 
                weatherPanel.setBounds(700, 0, 515, 444); 
                frame.add(weatherPanel); 
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
