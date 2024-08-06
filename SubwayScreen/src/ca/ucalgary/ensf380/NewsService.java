package ca.ucalgary.ensf380;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * A service for fetching news articles from the News API based on a query.
 */
public class NewsService {

    private static final String APIKEY = "bd8ece0d754645b3a4be0827e924a39a";
    private static final String URL = "https://newsapi.org/v2/everything?q=%s&apiKey=%s";    
    private String query;

    /**
     * Constructs a NewsService with the specified query.
     * 
     * @param query the query string for searching news articles
     */
    public NewsService(String query) {
        this.query = query;
    }

    /**
     * Fetches news articles from the News API based on the query.
     * 
     * @return a JSON string containing news articles
     * @throws Exception if an error occurs while sending the request or receiving the response
     */
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
