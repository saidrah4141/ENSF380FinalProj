package ca.ucalgary.ensf380;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WeatherService {
	private String URL = "https://openweathermap.org/";
	private static WebDriver driver;
	private static final String TEMPERATURE_REGEX = "(-?\\d+)";

	
	
	public WeatherService() {
	    
	    }
	
	
	public WeatherService(String code) {
		this.URL+="city/"+code;
	}
	public String getTemp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\nemoi\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        WeatherService.driver = new ChromeDriver();
        
        try {
            
            WeatherService.driver.get(this.URL); 

            
            Thread.sleep(10000); 

            String pageSource = this.driver.getPageSource();

            
            Document doc = Jsoup.parse(pageSource);

            
            Element tempElement = doc.selectFirst("span.heading[data-v-3e6e9f12]");
            
            if (tempElement != null) {
            	String temperature = tempElement.text();
                Pattern pattern = Pattern.compile(TEMPERATURE_REGEX);
                Matcher matcher = pattern.matcher(temperature);
                
                if (matcher.find()) {
                	return matcher.group() + "\u00B0C";

                } else {
                    return "Temperature format not recognized.";
                }
            } else {
            	
                return("Temperature element not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while fetching temperature.";
        } finally {
        	WeatherService.driver.quit();
        }
    }
	public static void main(String[] args) {
		WeatherService x = new WeatherService("6255152");
		System.out.println(x.getTemp());
	}
    
}
