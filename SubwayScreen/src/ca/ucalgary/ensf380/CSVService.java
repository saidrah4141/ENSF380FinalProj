package ca.ucalgary.ensf380;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
public class CSVService {



	public Map<String, Station> extractMapData(String mapFilePath) {
	    Map<String, Station> stations = new HashMap<>();
	    BufferedReader reader =null;
	    String line ="";
	    try {
	    	reader = new BufferedReader(new FileReader(mapFilePath));
	    	reader.readLine(); 
	    	while((line = reader.readLine())!=null) {
	    		String[] row =line.split(",");
	    		String stationName = row[4].trim();
                double x = Double.parseDouble(row[5].trim());
                double y = Double.parseDouble(row[6].trim());
                String stationCode = row[3].trim();
                String lineName = row[1].trim();
                int stationNumber= Integer.parseInt(row[2].trim());
                Station station = new Station(stationName, x, y, lineName, stationNumber,stationCode);
                if (row.length>7) {
                	for(int i=7; i<row.length; i++) {
                		station.addCommonStation(row[i].replace("\"", "").trim());
                	}
                }
	    		stations.put(stationCode, station);
	    		
	    	}
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    return stations;
	}
	
	public Map<String, Train> extractTrainData(String trainFilePath) {
	    Map<String, Train> trains = new HashMap<>();
	    BufferedReader reader =null;
	    String line ="";
	    try {
	    	reader = new BufferedReader(new FileReader(trainFilePath));
	    	reader.readLine(); 
	    	while((line = reader.readLine())!=null) {
	    		String[] row = line.split(",");
	    		
	    		String lineName = row[0].trim();
	    		int trainNum = Integer.parseInt(row[1].trim());
	    		String stationCode= row[2].trim();
	    		String direction = row[3].trim();
	    		String destination  = row[4].trim();
	    		
	    		Train train = new Train(lineName,trainNum, stationCode, direction, destination);
	    		trains.put((lineName+trainNum), train);
	    	}
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    return trains;
	}

	
	public Map<Integer, Coordinate> extractLineCoordinates(String linneFilePath) {
		 Map<Integer, Coordinate> lineCoord = new HashMap<>();
		    BufferedReader reader =null;
		    String line ="";
		    int counter=1;
		    try {
		    	reader = new BufferedReader(new FileReader(linneFilePath));
		    	reader.readLine(); 
		    	while((line = reader.readLine())!=null) {
		    		String[] row = line.split(",");
		    		
		    		
		    		double x = Double.parseDouble(row[0].trim());
		    		double y = Double.parseDouble(row[1].trim());
		    		
		    		Coordinate pair = new Coordinate(x,y);
		    		lineCoord.put(counter, pair);
		    		counter++;
		    	}
		    }catch(Exception e) {
		    	e.printStackTrace();	
		    }finally {
		    	try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    
		    return lineCoord;
	}
	

}
