package ca.ucalgary.ensf380;

public class NewsManager {

    private NewsService newsService;
    private NewsData newsData;
    private NewsResponse newsResponse;
    private int currentArticleIndex = 0; 
    private String currentAuthor;
    private String currentTitle;

    public NewsManager(String query) {
        this.newsService = new NewsService(query);
        this.newsData = new NewsData();
    }

   
    public void fetchNews() {
        try {
          
            String jsonResponse = newsService.getNews();
            
           
            newsResponse = newsData.parseNewsJson(jsonResponse);
            
            
            currentArticleIndex = 0;

          
            moveToNextArticle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    public void moveToNextArticle() {
        if (newsResponse != null && currentArticleIndex < newsResponse.getArticles().size()) {
            NewsResponse.Article currentArticle = newsResponse.getArticles().get(currentArticleIndex);
            currentAuthor = currentArticle.getAuthor();
            currentTitle = currentArticle.getTitle();
            currentArticleIndex++;
        } else {
            
            currentAuthor = null;
            currentTitle = null;
        }
    }

    public String getCurrentAuthor() {
        return currentAuthor;
    }

 
    public String getCurrentTitle() {
        return currentTitle;
    }
    
}
