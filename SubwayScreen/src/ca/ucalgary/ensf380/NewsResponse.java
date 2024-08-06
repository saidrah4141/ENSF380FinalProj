package ca.ucalgary.ensf380;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Represents the response from a news API containing a list of articles.
 */
public class NewsResponse {

    private List<Article> articles;

    /**
     * Returns the list of articles in the news response.
     * 
     * @return a list of {@link Article} objects
     */
    public List<Article> getArticles() {
        return articles;
    }

    /**
     * Represents an individual article in the news response.
     */
    public static class Article {
        private String author;
        private String title;

        /**
         * Returns the author of the article.
         * 
         * @return the author of the article
         */
        public String getAuthor() {
            return author;
        }

        /**
         * Returns the title of the article.
         * 
         * @return the title of the article
         */
        public String getTitle() {
            return title;
        }
    }
}
