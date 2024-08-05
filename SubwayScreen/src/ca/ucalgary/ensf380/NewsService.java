package ca.ucalgary.ensf380;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NewsService {
	
	private static final String APIKEY = "bd8ece0d754645b3a4be0827e924a39a";
	private static final String URL = "https://newsapi.org/v2/everything?q=%s&apiKey=%s";	
	private String query;
	
	public NewsService(String query) {
		this.query=query;
	}
	
	
	
    public String getNews() throws Exception {
        String url = String.format(URL, query.replace(" ", "+"), APIKEY);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
