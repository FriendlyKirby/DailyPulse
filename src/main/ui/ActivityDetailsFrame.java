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
// Represents the sessions gui
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

    /*
     * REQUIRES: activity, activityTracker, and mainGUI are not null
     * MODIFIES: this
     * EFFECTS: initializes the ActivityDetailsFrame with the given activity,
     * activityTracker, and mainGUI;
     * sets up the GUI components
     */
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

    /*
     * MODIFIES: this
     * EFFECTS: initializes GUI components and layouts
     */
    private void initializeComponents() {
        setLayout(new BorderLayout());

        addInfoPanel();
        addSessionList();
        addButtonPanel();
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds information panel with total time and streak labels
     */
    private void addInfoPanel() {
        totalTimeLabel = new JLabel("Total Time: " + String.format("%.2f", activity.getTotalTime()) + " hours");
        streakLabel = new JLabel("Streak: " + activity.getStreak() + " days");

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 1));
        infoPanel.add(totalTimeLabel);
        infoPanel.add(streakLabel);

        add(infoPanel, BorderLayout.NORTH);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds the session list to the GUI
     */
    private void addSessionList() {
        sessionListModel = new DefaultListModel<>();
        sessionList = new JList<>(sessionListModel);
        loadSessionsIntoList();
        JScrollPane sessionScrollPane = new JScrollPane(sessionList);

        add(sessionScrollPane, BorderLayout.CENTER);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds buttons to the GUI and sets up their action listeners
     */
    private void addButtonPanel() {
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

    /*
     * MODIFIES: sessionListModel
     * EFFECTS: loads sessions from the activity into the session list model
     */
    private void loadSessionsIntoList() {
        sessionListModel.clear();
        for (Session session : activity.getSessions()) {
            sessionListModel.addElement(session.toString());
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: starts the timer and updates the GUI accordingly
     */
    private void startTimer() {
        startTime = LocalDateTime.now();
        JOptionPane.showMessageDialog(this, "Timer started at " + startTime.toString());
        startTimerButton.setEnabled(false);
        stopTimerButton.setEnabled(true);
    }

    /*
     * MODIFIES: this, activity
     * EFFECTS: stops the timer, creates a new session, updates activity and GUI
     */
    private void stopTimer() {
        if (startTime != null) {
            LocalDateTime endTime = LocalDateTime.now();
            Duration duration = Duration.between(startTime, endTime);
            double durationInHours = duration.toSeconds() / 3600.0;

            JOptionPane.showMessageDialog(this, "Timer stopped at " + endTime.toString()
                    + "\nTotal time: " + String.format("%.2f", durationInHours) + " hours");

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

    /*
     * MODIFIES: totalTimeLabel, streakLabel
     * EFFECTS: updates the labels showing total time and streak
     */
    private void updateInfoLabels() {
        totalTimeLabel.setText("Total Time: " + String.format("%.2f", activity.getTotalTime()) + " hours");
        streakLabel.setText("Streak: " + activity.getStreak() + " days");
    }

    /*
     * EFFECTS: opens a new GraphFrame to display the activity's session graph
     */
    private void viewGraph() {
        new GraphFrame(activity);
    }

    /*
     * MODIFIES: activity, mainGUI
     * EFFECTS: allows user to edit the activity's name and updates GUI accordingly
     */
    private void editActivityName() {
        String newName = JOptionPane.showInputDialog(
                this,
                "Enter new name for the activity:",
                activity.getName());

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

    /*
     * MODIFIES: mainGUI
     * EFFECTS: refreshes the activity list in the main GUI
     */
    private void updateActivityInList() {
        mainGUI.refreshActivityList();
    }

    /*
     * MODIFIES: activity, sessionListModel
     * EFFECTS: deletes the selected session from the activity and updates GUI
     */
    private void deleteSession() {
        int selectedIndex = sessionList.getSelectedIndex();
        if (selectedIndex != -1) {
            Session session = activity.getSessions().get(selectedIndex);

            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this session?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);

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
