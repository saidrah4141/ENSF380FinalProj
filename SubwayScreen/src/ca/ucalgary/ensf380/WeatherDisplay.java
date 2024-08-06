package ca.ucalgary.ensf380;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Font;
import javax.swing.SwingConstants;

public class WeatherDisplay extends JPanel {

    private Timer updateTimer;
    private WeatherService weatherService;

    /**
     * Create the panel.
     */
    public WeatherDisplay(String code) {
        weatherService = new WeatherService(code);
        initialize();
        startUpdating();
    }

    /**
     * Initialize the contents of the panel.
     */
    private void initialize() {
        weatherLayout();
    }

    private void weatherLayout() {
        this.removeAll(); // Clear existing components if any

        JLabel lblNewLabel = new JLabel("Temperature");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 29));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
        lblNewLabel.setBounds(87, 30, 295, 59);
        this.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel(weatherService.getTemp() + " °C");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 26));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(97, 77, 280, 208);
        this.add(lblNewLabel_1);
        
        this.revalidate();
        this.repaint();
    }

    private void startUpdating() {
        updateTimer = new Timer(20000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        updateTimer.start();
    }

    private void updateData() {
        weatherLayout();
    }
}
