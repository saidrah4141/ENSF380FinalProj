package ca.ucalgary.ensf380;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NewsResponse {
    private List<Article> articles;

    
    public List<Article> getArticles() {
        return articles;
    }

    public static class Article {
        private String author;
        private String title;
        
        
        public String getAuthor() {
            return author;
        }
        
        public String getTitle() {
            return title;
        }
    }
}
