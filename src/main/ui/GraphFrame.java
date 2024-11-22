package ui;

import model.Activity;
import model.Session;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class GraphFrame extends JFrame {

    public GraphFrame(Activity activity) {
        super("Session Graph - " + activity.getName());
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new GraphPanel(activity));

        setVisible(true);
    }

    // Inner class for the graph panel
    class GraphPanel extends JPanel {
        private Map<LocalDate, Double> dateTimeMap;

        public GraphPanel(Activity activity) {
            // Process the session data
            dateTimeMap = new TreeMap<>();
            for (Session session : activity.getSessions()) {
                LocalDate date = session.getDate();
                double duration = session.getDurationInHours();
                dateTimeMap.put(date, dateTimeMap.getOrDefault(date, 0.0) + duration);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (dateTimeMap.isEmpty()) {
                g.drawString("No sessions to display.", getWidth() / 2 - 50, getHeight() / 2);
                return;
            }

            // Set up drawing parameters
            int padding = 50;
            int labelPadding = 25;
            int numYDivisions = 10;
            double maxDuration = getMaxDuration();

            // Calculate width and height
            int width = getWidth() - 2 * padding - labelPadding;
            int height = getHeight() - 2 * padding - labelPadding;

            // Draw background
            g.setColor(Color.WHITE);
            g.fillRect(padding + labelPadding, padding, width, height);
            g.setColor(Color.BLACK);

            // Draw y-axis lines and labels
            for (int i = 0; i <= numYDivisions; i++) {
                int y = padding + (i * height / numYDivisions);
                g.drawLine(padding + labelPadding, y, getWidth() - padding, y);
                String yLabel = String.format("%.1f", maxDuration - (i * maxDuration / numYDivisions));
                int labelWidth = g.getFontMetrics().stringWidth(yLabel);
                g.drawString(yLabel, padding + labelPadding - labelWidth - 5, y + (g.getFontMetrics().getHeight() / 2) - 3);
            }

            // Draw x-axis labels and bars
            int barWidth = width / dateTimeMap.size();
            int index = 0;

            for (Map.Entry<LocalDate, Double> entry : dateTimeMap.entrySet()) {
                LocalDate date = entry.getKey();
                double duration = entry.getValue();

                int x = padding + labelPadding + (index * barWidth);
                int barHeight = (int) ((duration / maxDuration) * height);

                // Draw bar
                g.setColor(Color.BLUE);
                g.fillRect(x, padding + height - barHeight, barWidth - 10, barHeight);

                // Draw date label
                g.setColor(Color.BLACK);
                String xLabel = date.toString();
                int labelWidth = g.getFontMetrics().stringWidth(xLabel);
                g.drawString(xLabel, x + (barWidth - 10) / 2 - labelWidth / 2, padding + height + g.getFontMetrics().getHeight() + 3);

                index++;
            }
        }

        private double getMaxDuration() {
            double max = 0;
            for (double duration : dateTimeMap.values()) {
                if (duration > max) {
                    max = duration;
                }
            }
            return max;
        }
    }
}
