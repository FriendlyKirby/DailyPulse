package ui;

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

        System.out.println("Welcome to DailyPulse.");

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

    private void doViewActivities() {
        // !!!
    }

    private void doAddOrRemoveActivity() {
        // !!!
    }

    private void doFindActivity() {
        // !!!
    }

    private void doSortActivities() {
        // !!!
    }
}