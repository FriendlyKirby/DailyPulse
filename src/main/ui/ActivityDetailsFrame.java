package ui;

import model.Activity;
import model.ActivityTracker;
import model.Session;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Referenced CPSC210 GUI Projects
public class ActivityDetailsFrame extends JFrame {

    private Activity activity;
    private ActivityTracker activityTracker;
    private ActivityTrackerGUI mainGUI;
    private DefaultListModel<String> sessionListModel;
    private JList<String> sessionList;
    private JLabel totalTimeLabel;
    private JLabel streakLabel;
    private JButton startTimerButton;
    private JButton stopTimerButton;
    private JButton viewGraphButton;
    private JButton editNameButton;
    private JButton deleteSessionButton;
    private LocalDateTime startTime;

    public ActivityDetailsFrame(Activity activity, ActivityTracker activityTracker, ActivityTrackerGUI mainGUI) {
        super(activity.getName());
        this.activity = activity;
        this.activityTracker = activityTracker;
        this.mainGUI = mainGUI;

        initializeComponents();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
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
        sessionList = new JList<>(sessionListModel);
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

        editNameButton = new JButton("Edit Name");
        editNameButton.addActionListener(e -> editActivityName());

        deleteSessionButton = new JButton("Delete Session");
        deleteSessionButton.addActionListener(e -> deleteSession());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startTimerButton);
        buttonPanel.add(stopTimerButton);
        buttonPanel.add(viewGraphButton);
        buttonPanel.add(editNameButton);
        buttonPanel.add(deleteSessionButton);

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

    private void editActivityName() {
        String newName = JOptionPane.showInputDialog(
                this,
                "Enter new name for the activity:",
                activity.getName()
        );

        if (newName != null && !newName.trim().isEmpty()) {
            // Check if the name already exists in the tracker
            if (activityTracker.getActivityByName(newName) == null) {
                activity.setName(newName);
                setTitle(newName);

                // Update the activity in the activity list
                updateActivityInList();

                JOptionPane.showMessageDialog(this, "Activity name updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "An activity with that name already exists.");
            }
        } else if (newName != null) {
            JOptionPane.showMessageDialog(this, "Activity name cannot be empty.");
        }
    }

    private void updateActivityInList() {
        mainGUI.refreshActivityList();
    }

    private void deleteSession() {
        int selectedIndex = sessionList.getSelectedIndex();
        if (selectedIndex != -1) {
            Session session = activity.getSessions().get(selectedIndex);

            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this session?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION
            );

            if (response == JOptionPane.YES_OPTION) {
                activity.removeSession(session);
                sessionListModel.remove(selectedIndex);
                updateInfoLabels();
                JOptionPane.showMessageDialog(this, "Session deleted successfully.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a session to delete.");
        }
    }
}
