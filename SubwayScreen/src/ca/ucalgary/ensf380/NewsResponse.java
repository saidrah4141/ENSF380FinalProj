package ca.ucalgary.ensf380;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NewsResponse {
    private List<Article> articles;

    

    public static class Article {
        private String author;
        private String title;
        private String description;
        
        public String getAuthor() {return this.author;}
        public String getTitle() {return this.title;}
        public String getDescription() {return this.description;}

        
    }

    
}

	