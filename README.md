# DailyPulse: Log Time and Maintain Streaks to Achieve Your Goals

## What will the application do?
The core idea of this app revolves around building consistency in your routines
by keeping track of how many continuous days you've worked on your goals and
logging your minutes/ hours to visualize the time you've committed.
Key Features:
- Create different activities
- Timer to record how long each session for an activity lasts
- Daily streak tracker for each activity
- Overall time spent tracker for each activity
- Visual bar graph to see daily time commitment for each activity

## Who will use it?
DailyPulse is designed for indivuals that are aiming to improve their productivity
and time management. This app is meant for users who need just that extra bit of
motivation, in the form of keeping up a daily streak. It's meant for those that
want to track their time spent and visualize it in a clean manner.

## Why is this project of interest to you?
I've been looking into self improvement and productivity for a long time in my
journey to become the best version of myself. One of the biggest walls that I ran
into was a lack of motivation to keep up consistency in whatever tasks were
required. One day I noticed that there was an exception to this issue, my Duolingo
progress. The idea of wanting to maintain my daily streak kept pushing me to
practice and learn a new language. Along side this realization, I've also been
using an app called Toggl to keep a visual track of my time spent learning art.
However, my main issue with the app is that it's not actually meant for this purpose
and so it has a lot of unnecessary features. With the concepts of streaks combined
with the visual time loggins features, and a few more quality of life additions,
DailyPulse is the perfect productivity companion app that I've been
looking for.

One of the core concepts that inspired this app is from a book called Atomic 
Habits. I gave this book a read from the reccomendation of a friend, and it helped
in changing my perspective on life goals and how to achieve them. It entails how
tiny behaviours can compound into remarkable results, which is what this app is
all about.

## User Stories
- As a user, I want to be able to add multiple sessions to an activity.
- As a user, I want to be able to view and list all sessions for an activity.
- As a user, I want to be able to delete activities or sessions.
- As a user, I want to be able to record the time I spent on a session.
- As a user, I want to be able to see my streak for an activity.
- As a user, I want to be able to see my overall time spent on an activity.
- As a user, I want to be able to see a graph detailing my daily time spent on each activity.
- As a user, I want to be able to save my activities and sessions to file (if I so choose)
- As a user, I want to be able to be able to load activities and sessions from file (if I so choose)

## Citations
- https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    - persitence is modeled after this

# Instructions for End User
- You can generate the first required action related to the user story "adding multiple sessions to an activity" by:
    - Selecting an activity from the list displayed in the main window of the application.
    - Clicking on the "View Activity" button, or double-clicking the activity name in the list to open the Activity Details window.
    - In the Activity Details window, clicking on the "Start Timer" button to begin recording a new session.
    - When you are finished with your session, clicking on the "Stop Timer" button. The session will be automatically added to the activity with the duration recorded.
- You can generate the second required action related to the user story "adding multiple sessions to an activity" by:
    - In the Activity Details window, clicking on the "Add Session" button (if available) to manually add a session.
    - Entering the duration of the session and the date when prompted.
    - Confirming your input to add the session to the activity.
- You can locate my visual component by:
    - Opening the Activity Details window for the desired activity.
    - Clicking on the "View Graph" button.
    - A new window will appear showing a bar graph that visualizes the time you have spent on the activity each day.
- You can save the state of my application by:
    - In the main window of the application, clicking on the "Save" button.
    - A file chooser dialog will appear. Select the location where you want to save your data and provide a file name.
    - Confirming the save operation. A confirmation message will appear indicating that the activity tracker has been saved.
- You can reload the state of my application by:
    - In the main window, clicking on the "Load" button.
    - A file chooser dialog will appear. Navigate to the location of your previously saved activity tracker file.
    - Selecting the file and confirming the load operation.
    - The application will load your activities and sessions from the file, and the main window will update to reflect the loaded data.