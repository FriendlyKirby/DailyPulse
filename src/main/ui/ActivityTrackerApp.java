package ui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.Activity;
import model.ActivityTracker;
import model.Session;

import java.util.Scanner;

// Activity tracker application
public class ActivityTrackerApp {
    private ActivityTracker activityTracker; // The activity tracker which is the list of activities
    private Scanner input;

    // EFFECTS: runs the activity tracker application
    public ActivityTrackerApp() {
        runActivityTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runActivityTracker() {
        boolean keepGoing = true;
        String command = null;

        init();

        System.out.println("Welcome to DailyPulse");

        while (keepGoing) {
            displayMainMenu();
            command = input.next().toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                mainMenuInput(command);
            }
        }

        System.out.println("Make sure to keep on top of your goals!");
    }

    // MODIFIES: this
    // EFFECTS: initializes activity tracker
    private void init() {
        activityTracker = new ActivityTracker();
        input = new Scanner(System.in);
    }

    // EFFECTS: displays option menu to user
    private void displayMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> View your activities");
        System.out.println("\ta -> Add or remove an activity");
        System.out.println("\tf -> Find a specific activity");
        System.out.println("\ts -> Sort activities");
        System.out.println("\tq -> Quit");
    }

    // MODIFIES: this
    // EFFECTS: takes in input prompt from user and proceeds with action
    private void mainMenuInput(String command) {
        switch (command) {
            case "v":
                doViewActivities();
                break;
            case "a":
                doAddOrRemoveActivity();
                break;
            case "f":
                doFindActivity();
                break;
            case "s":
                doSortActivities();
                break;
            default:
                System.out.println("Selection not valid");
                break;
        }
    }

    // EFFECTS: displays a lit of the activity names
    private void doViewActivities() {
        List<Activity> activities = activityTracker.getActivities();

        if (activities.isEmpty()) {
            System.out.println("No activities to display");
        } else {
            System.out.println("Activities:");
            for (Activity activity : activities) {
                System.out.println("\t- " + activity.getName());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: takes in input prompt from user and proceeds with action
    private void doAddOrRemoveActivity() {
        System.out.println("Add or remove an activity?");
        System.out.println("\ta -> Add");
        System.out.println("\tr -> Remove");

        String command = input.nextLine().toLowerCase();

        switch (command) {
            case "a":
                doAddActivity();
                break;
            case "r":
                doRemoveActivity();
                break;
            default:
                System.out.println("Selection not valid");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds activity to activity tracker
    private void doAddActivity() {
        System.out.print("Enter the name of the new activity you want to add: ");
        String name = input.nextLine();
        if (name.isEmpty()) {
            System.out.println("Activity name cannot be empty");
            return;
        }
        Activity activity = new Activity(name);
        activityTracker.addActivity(activity);
        System.out.println(name + " has been added to your list of activities");
    }

    // MODIFIES: this
    // EFFECTS: removes activity from activity tracker, nothing if null
    private void doRemoveActivity() {
        System.out.print("Enter the name of the activity you want to remove: ");
        String name = input.nextLine();
        Activity activity = activityTracker.getActivityByName(name);
        if (activity != null) {
            activityTracker.removeActivity(activity);
            System.out.println(name + " has been removed from your list of activities");
        } else {
            System.out.println(name + " is not found in your list of activities");
        }
    }


    // MODIFIES: this
    // EFFECTS: takes activity name as input then proceeds you into a new menu
    private void doFindActivity() {
        System.out.print("Enter the name of the activity you want to look at: ");
        String name = input.nextLine();
        Activity activity = activityTracker.getActivityByName(name);
        if (activity != null) {
            boolean keepGoingActivity = false;
            while (!keepGoingActivity) {
                displayActivityMenu(activity);
                String command = input.nextLine().trim().toLowerCase();
                keepGoingActivity = activityMenuInput(command, activity);
            }
        } else {
            System.out.println("Activity '" + name + "' not found.");
        }
    }

    // EFFECTS: displays activity information and menu options
    private void displayActivityMenu(Activity activity) {
        System.out.println("\nActivity: " + activity.getName());
        System.out.println("Total Time: " + activity.getTotalTime() + " hours");
        System.out.println("Streak: " + activity.getStreak() + " days");
        System.out.println("Select from:");
        System.out.println("\ta -> Add or remove a session");
        System.out.println("\tv -> View your sessions");
        System.out.println("\te -> Edit the activity name");
        System.out.println("\tg -> View session graph");
        System.out.println("\tb -> Back to main menu");
    }

    // MODIFIES: this
    // EFFECTS: takes in input prompt from user and proceeds with action
    private boolean activityMenuInput(String command, Activity activity) {
        switch (command) {
            case "a":
                doAddOrRemoveSession(activity);
                return false;
            case "v":
                doViewSessions(activity);
                return false;
            case "e":
                doEditActivityName(activity);
                return false;
            case "g":
                doViewGraph(activity);
                return false;
            case "b":
                return true;
            default:
                System.out.println("Selection is not valid");
                return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: takes in input prompt from user and proceeds with action
    private void doAddOrRemoveSession(Activity activity) {
        System.out.println("Add or remove a session?");
        System.out.println("\ta -> Add");
        System.out.println("\tr -> Remove");

        String command = input.nextLine().toLowerCase();

        switch (command) {
            case "a":
                doAddSession(activity);
                break;
            case "r":
                doRemoveSession(activity);
                break;
            default:
                System.out.println("Selection not valid");
                break;
        }
    }

    // MODIFIES: activity
    // EFFECTS: adds new session by starting and stopping a timer
    private void doAddSession(Activity activity) {
        System.out.println("Type 'start' to begin the timer: ");
        String command = input.nextLine().toLowerCase();
        
        if (command.equals("start")) {
            LocalDateTime startTime = LocalDateTime.now();
            System.out.println("Timer started at " + startTime);
            System.out.println("Type 'stop' to end the timer: ");
            command = input.nextLine().toLowerCase();

            while (!command.equals("stop")) {
                System.out.println("Input is not valid, type 'stop' to finish your session");
                command = input.nextLine().toLowerCase();
            }

            LocalDateTime endTime = LocalDateTime.now();
            System.out.println("Timer stopped at " + endTime);

            Duration duration = Duration.between(startTime, endTime);
            double durationInHours = duration.getSeconds() / 3600.0;

            System.out.println("Total time: " + String.format("%.2f", durationInHours) + " hours");

            // new session
            Session session = new Session((int) Math.round(durationInHours));
            session.setDate(LocalDate.now());
            activity.addSession(session);

            System.out.println("Your session has been added to the activity, great job!");
        } else {
            System.out.println("Your input is not valid, returning to the activity menu.");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes session by id from activity
    private void doRemoveSession(Activity activity) {
        doViewSessions(activity);
        System.out.print("Enter the session ID to remove: ");
        int sessionId = Integer.parseInt(input.nextLine());
        Session sessionToRemove = null;
        for (Session session : activity.getSessions()) {
            if (session.getId() == sessionId) {
                sessionToRemove = session;
                break;
            }
        }
        if (sessionToRemove != null) {
            activity.removeSession(sessionToRemove);
            System.out.println("Session ID " + sessionId + " removed.");
        } else {
            System.out.println("Session ID " + sessionId + " not found.");
        }
    }

    // EFFECTS: displays all the sessions for the activity
    private void doViewSessions(Activity activity) {
        List<Session> sessions = activity.getSessions();
        if (sessions.isEmpty()) {
            System.out.println("You have no sessions for this activity");
        } else {
            System.out.println("Sessions:");
            for (Session session : sessions) {
                System.out.println("\t" + session.toString());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the activity name to the input
    private void doEditActivityName(Activity activity) {
        System.out.println("What would you like to rename the activity to? It can not be empty");

        String name = input.nextLine().toLowerCase();

        if (name != null) {
            activity.setName(name);
        } else {
            System.out.println("You can not enter an empty name");
        }
    }

    // EFFECTS: displays graph of sessions over days for activity
    private void doViewGraph(Activity activity) {
        Map<LocalDate, Integer> dateTimeMap = new TreeMap<>();
        for (Session session : activity.getSessions()) {
            LocalDate date = session.getDate();
            int duration = session.getDurationInHours();
            dateTimeMap.put(date, dateTimeMap.getOrDefault(date, 0) + duration);
        }

        if (dateTimeMap.isEmpty()) {
            System.out.println("You have no sessions for this activity.");
            return;
        } else {
            System.out.println("Daily Time Spent on Activity:");
            for (Map.Entry<LocalDate, Integer> entry : dateTimeMap.entrySet()) {
                LocalDate date = entry.getKey();
                int time = entry.getValue();
                String bar = new String(new char[time]).replace('\0', '#');
                System.out.printf("%s | %d hours | %s%n", date, time, bar);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sorts the activities based on user input
    private void doSortActivities() {
        System.out.println("Sort activities by:");
        System.out.println("\tn -> Name");
        System.out.println("\ts -> Streak");
        System.out.println("\tt -> Total Time");
        
        String command = input.nextLine().toLowerCase();

        switch (command) {
            case "n":
                activityTracker.sortByName();
                System.out.println("Activities sorted by name.");
                break;
            case "s":
                activityTracker.sortByStreak();
                System.out.println("Activities sorted by streak.");
                break;
            case "t":
                activityTracker.sortByTotalTime();
                System.out.println("Activities sorted by total time.");
                break;
            default:
                System.out.println("You have entered an invalid option.");
                break;
        }
    }
}