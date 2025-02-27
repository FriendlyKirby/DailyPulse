package ui;

import model.Activity;
import model.ActivityTracker;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Referenced CPSC210 GUI Projects
// Represents the activity gui
public class ActivityTrackerGUI extends JFrame {

    private ActivityTracker activityTracker;
    private DefaultListModel<String> activityListModel;
    private JList<String> activityList;
    private JScrollPane activityScrollPane;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Buttons and components
    private JButton addButton;
    private JButton removeButton;
    private JButton viewButton;
    private JButton saveButton;
    private JButton loadButton;
    private JComboBox<String> sortComboBox;

    public ActivityTrackerGUI() {
        super("DailyPulse Activity Tracker");
        activityTracker = new ActivityTracker();

        // Clears event log on start
        EventLog.getInstance().clear();

        initializeComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Add window listener to print events on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
            }
        });

        setVisible(true);
    }

    /*
     * EFFECTS: prints the events from the event log to the console
     */
    private void printLog(EventLog el) {
        System.out.println("Event Log:");
        for (model.Event next : el) {
            System.out.println(next.toString());
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes GUI components and layouts
     */
    private void initializeComponents() {
        setLayout(new BorderLayout());

        initializeActivityList();
        add(activityScrollPane, BorderLayout.CENTER);

        createButtons();
        addButtonPanel();

        addDoubleClickListener();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes the activity list component
     */
    private void initializeActivityList() {
        activityListModel = new DefaultListModel<>();
        activityList = new JList<>(activityListModel);
        activityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        activityScrollPane = new JScrollPane(activityList);

        loadActivitiesIntoList();
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates buttons and adds action listeners
     */
    private void createButtons() {
        addButton = new JButton("Add Activity");
        removeButton = new JButton("Remove Activity");
        viewButton = new JButton("View Activity");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");

        addButton.addActionListener(e -> addActivity());
        removeButton.addActionListener(e -> removeActivity());
        viewButton.addActionListener(e -> viewActivity());
        saveButton.addActionListener(e -> saveActivityTracker());
        loadButton.addActionListener(e -> loadActivityTracker());

        String[] sortOptions = { "Name", "Streak", "Total Time" };
        sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.addActionListener(e -> sortActivities(sortComboBox.getSelectedItem().toString()));
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds the button panel to the GUI
     */
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(new JLabel("Sort By:"));
        buttonPanel.add(sortComboBox);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a double-click listener to the activity list to open the
     * activity details
     */
    private void addDoubleClickListener() {
        activityList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    viewActivity();
                }
            }
        });
    }

    /*
     * MODIFIES: activityListModel
     * EFFECTS: loads activities from the activity tracker into the list model
     */
    private void loadActivitiesIntoList() {
        activityListModel.clear();
        for (Activity activity : activityTracker.getActivities()) {
            activityListModel.addElement(activity.getName());
        }
    }

    /*
     * MODIFIES: this, activityTracker
     * EFFECTS: adds a new activity to the tracker and updates the GUI
     */
    private void addActivity() {
        String name = JOptionPane.showInputDialog(this, "Enter the name of the new activity:");
        if (name != null && !name.trim().isEmpty()) {
            if (activityTracker.getActivityByName(name.trim()) == null) {
                Activity activity = new Activity(name.trim());
                activityTracker.addActivity(activity);
                activityListModel.addElement(activity.getName());
            } else {
                JOptionPane.showMessageDialog(this, "An activity with that name already exists.");
            }
        } else if (name != null) {
            JOptionPane.showMessageDialog(this, "Activity name cannot be empty.");
        }
    }

    /*
     * MODIFIES: this, activityTracker
     * EFFECTS: removes the selected activity from the tracker and updates the GUI
     */
    private void removeActivity() {
        int selectedIndex = activityList.getSelectedIndex();
        if (selectedIndex != -1) {
            String activityName = activityListModel.getElementAt(selectedIndex);

            // Show confirmation dialog
            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to remove the activity \"" + activityName + "\"?",
                    "Confirm Removal",
                    JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                // Proceed with removal
                Activity activity = activityTracker.getActivityByName(activityName);
                activityTracker.removeActivity(activity);
                activityListModel.remove(selectedIndex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an activity to remove.");
        }
    }

    /*
     * EFFECTS: opens a new ActivityDetailsFrame for the selected activity
     */
    private void viewActivity() {
        String name = activityList.getSelectedValue();
        if (name != null) {
            Activity activity = activityTracker.getActivityByName(name);
            new ActivityDetailsFrame(activity, activityTracker, this);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an activity to view.");
        }
    }

    /*
     * EFFECTS: saves the activity tracker to a file chosen by the user
     */
    private void saveActivityTracker() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getPath();
            try {
                jsonWriter = new JsonWriter(filePath);
                jsonWriter.open();
                jsonWriter.write(activityTracker);
                jsonWriter.close();
                JOptionPane.showMessageDialog(this, "Saved activity tracker to " + filePath);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Unable to write to file: " + filePath);
            }
        }
    }

    /*
     * MODIFIES: this, activityTracker
     * EFFECTS: loads the activity tracker from a file chosen by the user and
     * updates the GUI
     */
    private void loadActivityTracker() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getPath();
            try {
                jsonReader = new JsonReader(filePath);
                activityTracker = jsonReader.read();
                loadActivitiesIntoList();
                JOptionPane.showMessageDialog(this, "Loaded activity tracker from " + filePath);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Unable to read from file: " + filePath);
            }
        }
    }

    /*
     * MODIFIES: activityTracker
     * EFFECTS: sorts activities in the tracker based on the selected criterion and
     * updates the GUI
     */
    private void sortActivities(String criterion) {
        switch (criterion) {
            case "Name":
                activityTracker.sortByName();
                break;
            case "Streak":
                activityTracker.sortByStreak();
                break;
            case "Total Time":
                activityTracker.sortByTotalTime();
                break;
        }
        loadActivitiesIntoList();
    }

    // Add this method to refresh the activity list
    /*
     * MODIFIES: activityListModel
     * EFFECTS: refreshes the activity list in the GUI
     */
    public void refreshActivityList() {
        loadActivitiesIntoList();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ActivityTrackerGUI();
        });
    }
}
