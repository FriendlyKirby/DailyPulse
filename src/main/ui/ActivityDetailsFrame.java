package ui;

import model.Activity;
import model.Session;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Referenced CPSC210 GUI Projects
public class ActivityDetailsFrame extends JFrame {

    private Activity activity;
    private DefaultListModel<String> sessionListModel;
    private JLabel totalTimeLabel;
    private JLabel streakLabel;
    private JButton startTimerButton;
    private JButton stopTimerButton;
    private LocalDateTime startTime;
    private JButton viewGraphButton;

    public ActivityDetailsFrame(Activity activity) {
        super(activity.getName());
        this.activity = activity;

        initializeComponents();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        // Labels for total time and streak
        totalTimeLabel = new JLabel("Total Time: " + String.format("%.2f", activity.getTotalTime()) + " hours");
        streakLabel = new JLabel("Streak: " + activity.getStreak() + " days");

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 1));
        infoPanel.add(totalTimeLabel);
        infoPanel.add(streakLabel);

        add(infoPanel, BorderLayout.NORTH);

        // Session list
        sessionListModel = new DefaultListModel<>();
        JList<String> sessionList = new JList<>(sessionListModel);
        loadSessionsIntoList();
        JScrollPane sessionScrollPane = new JScrollPane(sessionList);

        add(sessionScrollPane, BorderLayout.CENTER);

        // Buttons
        startTimerButton = new JButton("Start Timer");
        stopTimerButton = new JButton("Stop Timer");
        stopTimerButton.setEnabled(false); // Initially disabled

        startTimerButton.addActionListener(e -> startTimer());
        stopTimerButton.addActionListener(e -> stopTimer());

        viewGraphButton = new JButton("View Graph");
        viewGraphButton.addActionListener(e -> viewGraph());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startTimerButton);
        buttonPanel.add(stopTimerButton);
        buttonPanel.add(viewGraphButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadSessionsIntoList() {
        sessionListModel.clear();
        for (Session session : activity.getSessions()) {
            sessionListModel.addElement(session.toString());
        }
    }

    private void startTimer() {
        startTime = LocalDateTime.now();
        JOptionPane.showMessageDialog(this, "Timer started at " + startTime.toString());
        startTimerButton.setEnabled(false);
        stopTimerButton.setEnabled(true);
    }

    private void stopTimer() {
        if (startTime != null) {
            LocalDateTime endTime = LocalDateTime.now();
            Duration duration = Duration.between(startTime, endTime);
            double durationInHours = duration.toSeconds() / 3600.0;

            JOptionPane.showMessageDialog(this, "Timer stopped at " + endTime.toString() +
                    "\nTotal time: " + String.format("%.2f", durationInHours) + " hours");

            // Create a new session
            Session session = new Session(durationInHours);
            session.setDate(LocalDate.now());
            activity.addSession(session);

            // Update UI
            sessionListModel.addElement(session.toString());
            updateInfoLabels();

            // Reset timer
            startTime = null;
            startTimerButton.setEnabled(true);
            stopTimerButton.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this, "Timer was not started.");
        }
    }

    private void updateInfoLabels() {
        totalTimeLabel.setText("Total Time: " + String.format("%.2f", activity.getTotalTime()) + " hours");
        streakLabel.setText("Streak: " + activity.getStreak() + " days");
    }

    private void viewGraph() {
        new GraphFrame(activity);
    }
}
