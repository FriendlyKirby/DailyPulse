package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActivityTrackerTest {
    private ActivityTracker testActivityTracker;
    private Activity activity1;
    private Activity activity2;

    @BeforeEach
    void runBefore() {
        testActivityTracker = new ActivityTracker();
        activity1 = new Activity("Coding");
        activity2 = new Activity("Drawing");
        
    }

    @Test
    void testConstructor() {
        assertEquals(0, testActivityTracker.getActivities().size());
    }

    @Test
    void testAddActivity() {
        testActivityTracker.addActivity(activity1);
        assertEquals(1, testActivityTracker.getActivities().size());
    }

    @Test
    void testAddActivityDuplicate() {
        testActivityTracker.addActivity(activity1);
        testActivityTracker.addActivity(activity1);
        assertEquals(1, testActivityTracker.getActivities().size());
    }

    @Test
    void testAddMultipleActivities() {
        testActivityTracker.addActivity(activity1);
        testActivityTracker.addActivity(activity2);
        assertEquals(2, testActivityTracker.getActivities().size());
    }

    @Test
    void testRemoveActivity() {
        testActivityTracker.addActivity(activity1);
        testActivityTracker.addActivity(activity2);
        assertEquals(2, testActivityTracker.getActivities().size());
        testActivityTracker.removeActivity(activity1);
        assertEquals(1, testActivityTracker.getActivities().size());
        assertEquals(activity2, testActivityTracker.getActivities().get(0));
        testActivityTracker.removeActivity(activity2);
        assertEquals(0, testActivityTracker.getActivities().size());
    }

   @Test
    void testRemoveActivityNotInList() {
        testActivityTracker.addActivity(activity1);
        try {
            testActivityTracker.removeActivity(activity2);
        } catch (IllegalArgumentException e) {
            assertEquals("Activity not found", e.getMessage());
        }
    }

    @Test
    void testGetActivityByName() {
        testActivityTracker.addActivity(activity1);
        testActivityTracker.addActivity(activity2);
        assertEquals(activity1, testActivityTracker.getActivityByName("Coding"));
        assertEquals(activity2, testActivityTracker.getActivityByName("Drawing"));
    }

    @Test
    void testGetActivityByNameNull() {
        try {
            testActivityTracker.getActivityByName(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Activity name cannot be null.", e.getMessage());
        }
    }

    @Test
    void testGetActivityByNameNotExist() {
        assertNull(testActivityTracker.getActivityByName("activity0"));
    }

    @Test
    void testGetActivities() {
        testActivityTracker.addActivity(activity1);
        testActivityTracker.addActivity(activity2);
        List<Activity> activities = testActivityTracker.getActivities();
        assertEquals(2, activities.size());
        assertTrue(activities.contains(activity1));
        assertTrue(activities.contains(activity2));
    }

    @Test
    void testGetActivityNames() {
        testActivityTracker.addActivity(activity1);
        testActivityTracker.addActivity(activity2);
        List<String> names = testActivityTracker.getActivityNames();
        assertEquals("Coding", names.get(0));
        assertEquals("Drawing", names.get(1));
    }

    @Test
    void testGetActivityNamesEmpty() {
        List<String> names = testActivityTracker.getActivityNames();
        assertTrue(names.isEmpty());
    }
    
    @Test
    void testSortByNameNoChange() {
        testActivityTracker.addActivity(activity1);
        testActivityTracker.addActivity(activity2);
        testActivityTracker.sortByName();
        assertEquals("Coding", testActivityTracker.getActivities().get(0).getName());
        assertEquals("Drawing", testActivityTracker.getActivities().get(1).getName());
    }

    @Test
    void testSortByNameChange() {
        testActivityTracker.addActivity(activity2);
        testActivityTracker.addActivity(activity1);
        testActivityTracker.sortByName();
        assertEquals("Coding", testActivityTracker.getActivities().get(0).getName());
        assertEquals("Drawing", testActivityTracker.getActivities().get(1).getName());
    }

    @Test
    void testSortByNameSameFirst() {
        testActivityTracker.addActivity(activity1);
        testActivityTracker.getActivityByName("Coding").setName("acb");
        testActivityTracker.addActivity(activity2);
        testActivityTracker.getActivityByName("Drawing").setName("abc");
        testActivityTracker.sortByName();
        assertEquals("abc", testActivityTracker.getActivities().get(0).getName());
        assertEquals("acb", testActivityTracker.getActivities().get(1).getName());
    }

    @Test
    void testSortByNameEmptyList() {
        testActivityTracker.sortByName();
        assertTrue(testActivityTracker.getActivities().isEmpty());
    }

    @Test
    void testSortByStreakNoChange() {
        testActivityTracker.addActivity(activity1);
        testActivityTracker.getActivityByName("Coding").setStreak(1);
        testActivityTracker.addActivity(activity2);
        testActivityTracker.getActivityByName("Drawing").setStreak(0);
        testActivityTracker.sortByStreak();
        assertEquals("Coding", testActivityTracker.getActivities().get(0).getName());
        assertEquals("Drawing", testActivityTracker.getActivities().get(1).getName());
    }

    @Test
    void testSortByStreakChange() {
        testActivityTracker.addActivity(activity1);
        testActivityTracker.getActivityByName("Coding").setStreak(0);
        testActivityTracker.addActivity(activity2);
        testActivityTracker.getActivityByName("Drawing").setStreak(1);
        testActivityTracker.sortByStreak();
        assertEquals("Drawing", testActivityTracker.getActivities().get(0).getName());
        assertEquals("Coding", testActivityTracker.getActivities().get(1).getName());
    }

    @Test
    void testSortByStreakSameStreak() {
        testActivityTracker.addActivity(activity1);
        testActivityTracker.getActivityByName("Coding").setStreak(0);
        testActivityTracker.addActivity(activity2);
        testActivityTracker.getActivityByName("Drawing").setStreak(0);
        testActivityTracker.sortByStreak();
        assertEquals("Coding", testActivityTracker.getActivities().get(0).getName());
        assertEquals("Drawing", testActivityTracker.getActivities().get(1).getName());
    }

    @Test
    void testSortByStreakEmptyList() {
        testActivityTracker.sortByStreak();
        assertTrue(testActivityTracker.getActivities().isEmpty());
    }
    @Test
    void testSortByTotalTimeNoChange() {
        testActivityTracker.addActivity(activity1);
        testActivityTracker.getActivityByName("Coding").setTotalTime(0);
        testActivityTracker.addActivity(activity2);
        testActivityTracker.getActivityByName("Drawing").setTotalTime(0);
        testActivityTracker.sortByTotalTime();
        assertEquals("Coding", testActivityTracker.getActivities().get(0).getName());
        assertEquals("Drawing", testActivityTracker.getActivities().get(1).getName());
    }

    @Test
    void testSortByTotalTimeChange() {
        testActivityTracker.addActivity(activity1);
        testActivityTracker.getActivityByName("Coding").setTotalTime(0);
        testActivityTracker.addActivity(activity2);
        testActivityTracker.getActivityByName("Drawing").setTotalTime(1);
        testActivityTracker.sortByTotalTime();
        assertEquals("Drawing", testActivityTracker.getActivities().get(0).getName());
        assertEquals("Coding", testActivityTracker.getActivities().get(1).getName());
    }

    @Test
    void testSortByTotalSameTime() {
        testActivityTracker.addActivity(activity1);
        testActivityTracker.getActivityByName("Coding").setTotalTime(0);
        testActivityTracker.addActivity(activity2);
        testActivityTracker.getActivityByName("Drawing").setTotalTime(0);
        testActivityTracker.sortByTotalTime();
        assertEquals("Coding", testActivityTracker.getActivities().get(0).getName());
        assertEquals("Drawing", testActivityTracker.getActivities().get(1).getName());
    }

    @Test
    void testSortByTotalTimeEmptyList() {
        testActivityTracker.sortByTotalTime();
        assertTrue(testActivityTracker.getActivities().isEmpty());
    }
}