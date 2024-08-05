package ca.ucalgary.ensf380;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Font;
public class NewsDisplay {

    private JFrame frame;
    private NewsManager news;
    private Timer updateTimer;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            
                String newsQuery = JOptionPane.showInputDialog(null, 
                        "Enter your news query:", 
                        "News Query", 
                        JOptionPane.QUESTION_MESSAGE);
                
               
                if (newsQuery == null || newsQuery.trim().isEmpty()) {
                    newsQuery = "No query entered"; 
                }

               
                NewsDisplay window = new NewsDisplay(newsQuery);
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     * @wbp.parser.entryPoint
     */
    public NewsDisplay(String query) {
    	this.news = new NewsManager(query);
    	initialize();
    	startUpdating();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1215, 153);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        news.fetchNews();
        newsPanel();
        
        

        
    }
    private void newsPanel() {
    	 JPanel panel = new JPanel();
         panel.setForeground(new Color(255, 255, 255));
         panel.setBounds(0, 0, 1201, 116);
         frame.getContentPane().add(panel);
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
         frame.setForeground(Color.WHITE);
         
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
       
        frame.getContentPane().removeAll();
        newsPanel();
        frame.revalidate();
        frame.repaint();
    }
}
