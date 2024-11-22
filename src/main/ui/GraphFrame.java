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

            drawBackground(g, padding, labelPadding, width, height);
            drawYAxis(g, padding, labelPadding, numYDivisions, maxDuration, width, height);
            drawXAxisAndBars(g, padding, labelPadding, maxDuration, width, height);
        }

        private void drawBackground(Graphics g, int padding, int labelPadding, int width, int height) {
            g.setColor(Color.WHITE);
            g.fillRect(padding + labelPadding, padding, width, height);
            g.setColor(Color.BLACK);
        }

        private void drawYAxis(Graphics g, int padding, int labelPadding, int numYDivisions,
                double maxDuration, int width, int height) {
            for (int i = 0; i <= numYDivisions; i++) {
                int y = padding + (i * height / numYDivisions);
                g.drawLine(padding + labelPadding, y, getWidth() - padding, y);
                String labelY = String.format("%.1f", maxDuration - (i * maxDuration / numYDivisions));
                int labelWidth = g.getFontMetrics().stringWidth(labelY);
                g.drawString(labelY, padding + labelPadding - labelWidth - 5,
                        y + (g.getFontMetrics().getHeight() / 2) - 3);
            }
        }

        private void drawXAxisAndBars(Graphics g, int padding, int labelPadding,
                double maxDuration, int width, int height) {
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
                String labelX = date.toString();
                int labelWidth = g.getFontMetrics().stringWidth(labelX);
                g.drawString(labelX, x + (barWidth - 10) / 2 - labelWidth / 2,
                        padding + height + g.getFontMetrics().getHeight() + 3);

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
