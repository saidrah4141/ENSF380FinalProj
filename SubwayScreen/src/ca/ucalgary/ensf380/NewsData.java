package ca.ucalgary.ensf380;

import com.google.gson.Gson;

/**
 * Handles the processing of news data, including parsing JSON responses into NewsResponse objects.
 */
public class NewsData {

    private Gson gson;

    /**
     * Constructs a NewsData instance and initializes the Gson parser.
     */
    public NewsData() {
        this.gson = new Gson();
    }

    /**
     * Parses a JSON string into a NewsResponse object.
     * 
     * @param json the JSON string to be parsed
     * @return a NewsResponse object representing the parsed JSON data
     */
    public NewsResponse parseNewsJson(String json) {
        return gson.fromJson(json, NewsResponse.class);
    }
}
