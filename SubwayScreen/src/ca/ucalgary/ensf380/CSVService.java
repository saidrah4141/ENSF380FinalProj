package ca.ucalgary.ensf380;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides methods for extracting data from CSV files related to stations, trains, and line coordinates.
 */
public class CSVService {

    /**
     * Extracts map data from a CSV file and creates a map of Station objects.
     * 
     * @param mapFilePath the path to the CSV file containing station data
     * @return a map where the key is the station code and the value is a Station object
     */
    public Map<String, Station> extractMapData(String mapFilePath) {
        Map<String, Station> stations = new HashMap<>();
        BufferedReader reader = null;
        String line = "";
        try {
            reader = new BufferedReader(new FileReader(mapFilePath));
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                String stationName = row[4].trim();
                double x = Double.parseDouble(row[5].trim());
                double y = Double.parseDouble(row[6].trim());
                String stationCode = row[3].trim();
                String lineName = row[1].trim();
                int stationNumber = Integer.parseInt(row[2].trim());
                Station station = new Station(stationName, x, y, lineName, stationNumber, stationCode);
                if (row.length > 7) {
                    for (int i = 7; i < row.length; i++) {
                        station.addCommonStation(row[i].replace("\"", "").trim());
                    }
                }
                stations.put(stationCode, station);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stations;
    }

    /**
     * Extracts train data from a CSV file and creates a map of Train objects.
     * 
     * @param trainFilePath the path to the CSV file containing train data
     * @return a map where the key is a combination of line name and train number, and the value is a Train object
     */
    public Map<String, Train> extractTrainData(String trainFilePath) {
        Map<String, Train> trains = new HashMap<>();
        BufferedReader reader = null;
        String line = "";
        try {
            reader = new BufferedReader(new FileReader(trainFilePath));
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                String lineName = row[0].trim();
                int trainNum = Integer.parseInt(row[1].trim());
                String stationCode = row[2].trim();
                String direction = row[3].trim();
                String destination = row[4].trim();

                Train train = new Train(lineName, trainNum, stationCode, direction, destination);
                trains.put(lineName + trainNum, train);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return trains;
    }

    /**
     * Extracts line coordinates from a CSV file and creates a map of Coordinate objects.
     * 
     * @param lineFilePath the path to the CSV file containing line coordinates
     * @return a map where the key is an integer representing the index, and the value is a Coordinate object
     */
    public Map<Integer, Coordinate> extractLineCoordinates(String lineFilePath) {
        Map<Integer, Coordinate> lineCoord = new HashMap<>();
        BufferedReader reader = null;
        String line = "";
        int counter = 1;
        try {
            reader = new BufferedReader(new FileReader(lineFilePath));
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                double x = Double.parseDouble(row[0].trim());
                double y = Double.parseDouble(row[1].trim());

                Coordinate pair = new Coordinate(x, y);
                lineCoord.put(counter, pair);
                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lineCoord;
    }
    public Map<String, String> extractMapNameData(String mapFilePath) {
        Map<String, String> stations = new HashMap<>();
        BufferedReader reader = null;
        String line = "";
        try {
            reader = new BufferedReader(new FileReader(mapFilePath));
            reader.readLine(); 
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                String stationName = row[4].trim();
                String stationCode = row[3].trim();            
                stations.put(stationCode, stationName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stations;
    }
    
}
