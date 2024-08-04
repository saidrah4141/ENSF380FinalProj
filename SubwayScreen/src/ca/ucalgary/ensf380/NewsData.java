package ca.ucalgary.ensf380;

import com.google.gson.Gson;

public class NewsData {
    private Gson gson;

    public NewsData() {
        this.gson = new Gson();
    }

    public NewsResponse parseNewsJson(String json) {
        return gson.fromJson(json, NewsResponse.class);
    }
}

