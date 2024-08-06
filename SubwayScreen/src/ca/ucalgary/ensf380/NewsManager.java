package ca.ucalgary.ensf380;

/**
 * Manages the fetching and processing of news articles, including navigation through articles.
 */
public class NewsManager {

    private NewsService newsService;
    private NewsData newsData;
    private NewsResponse newsResponse;
    private int currentArticleIndex = 0; 
    private String currentAuthor;
    private String currentTitle;

    /**
     * Constructs a NewsManager instance with the specified query.
     * 
     * @param query the search query to be used for fetching news
     */
    public NewsManager(String query) {
        this.newsService = new NewsService(query);
        this.newsData = new NewsData();
    }

    /**
     * Fetches news articles using the news service and parses the response.
     * Resets the article index and moves to the first article.
     */
    public void fetchNews() {
        try {
            // Fetch JSON response from the news service
            String jsonResponse = newsService.getNews();
            
            // Parse JSON response into NewsResponse object
            newsResponse = newsData.parseNewsJson(jsonResponse);
            
            // Reset current article index
            currentArticleIndex = 0;

            // Move to the first article
            moveToNextArticle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Moves to the next article in the list of fetched articles.
     * Updates the current author and title based on the current article.
     * If there are no more articles, sets author and title to null.
     */
    public void moveToNextArticle() {
        if (newsResponse != null && currentArticleIndex < newsResponse.getArticles().size()) {
            NewsResponse.Article currentArticle = newsResponse.getArticles().get(currentArticleIndex);
            currentAuthor = currentArticle.getAuthor();
            currentTitle = currentArticle.getTitle();
            currentArticleIndex++;
        } else {
            // No more articles available
            currentAuthor = null;
            currentTitle = null;
        }
    }

    /**
     * Returns the author of the current article.
     * 
     * @return the author of the current article, or null if no article is selected
     */
    public String getCurrentAuthor() {
        return currentAuthor;
    }

    /**
     * Returns the title of the current article.
     * 
     * @return the title of the current article, or null if no article is selected
     */
    public String getCurrentTitle() {
        return currentTitle;
    }
}
