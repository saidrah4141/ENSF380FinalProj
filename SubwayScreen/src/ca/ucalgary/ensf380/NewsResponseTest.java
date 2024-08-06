package ca.ucalgary.ensf380;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NewsResponseTest {

    private final Gson gson = new Gson();

    @Test
    public void testDeserialization() {
        
    	String json = "{"
                + "\"articles\": ["
                + "{"
                + "\"author\": \"John Doe\","
                + "\"title\": \"Sample News Title\""
                + "}"
                + "]"
                + "}";
        
        NewsResponse newsResponse = gson.fromJson(json, NewsResponse.class);

       
        assertNotNull(newsResponse, "NewsResponse should not be null");
        assertNotNull(newsResponse.getArticles(), "Articles should not be null");
        assertEquals(1, newsResponse.getArticles().size(), "There should be one article");

        NewsResponse.Article article = newsResponse.getArticles().get(0);
        assertEquals("John Doe", article.getAuthor(), "Author should be 'John Doe'");
        assertEquals("Sample News Title", article.getTitle(), "Title should be 'Sample News Title'");
    }
}
