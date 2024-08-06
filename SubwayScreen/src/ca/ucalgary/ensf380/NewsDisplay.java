package ca.ucalgary.ensf380;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class NewsDisplay extends JPanel {

    private NewsManager news;
    private Timer updateTimer;

    /**
     * Create the panel.
     * @param query The news query to initialize NewsManager.
     */
    public NewsDisplay(String query) {
        this.news = new NewsManager(query);
        initialize();
        startUpdating();
    }

    /**
     * Initialize the contents of the panel.
     */
    private void initialize() {
        setBounds(0, 0, 1215, 153);
        setLayout(null);
        setBackground(Color.WHITE);

        news.fetchNews();
        newsPanel();
    }

    private void newsPanel() {
        JPanel panel = new JPanel();
        panel.setForeground(new Color(255, 255, 255));
        panel.setBounds(0, 0, 1215, 153);
        add(panel);
        panel.setLayout(null);

        String author = news.getCurrentAuthor();
        String title = news.getCurrentTitle();

        JLabel authorLabel = new JLabel(author);
        authorLabel.setBounds(140, 0, 147, 40);
        authorLabel.setForeground(Color.BLACK);
        panel.add(authorLabel);

        JLabel newsBody = new JLabel(title);
        newsBody.setFont(new Font("Tahoma", Font.PLAIN, 20));
        newsBody.setVerticalAlignment(SwingConstants.TOP);
        newsBody.setBounds(140, 51, 746, 65);
        newsBody.setForeground(Color.BLACK);
        panel.add(newsBody);
        setForeground(Color.WHITE);
    }

    private void startUpdating() {
        updateTimer = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        updateTimer.start();
    }

    private void updateData() {
        news.moveToNextArticle();
        updateLayout();
    }

    private void updateLayout() {
        removeAll();
        newsPanel();
        revalidate();
        repaint();
    }
}
