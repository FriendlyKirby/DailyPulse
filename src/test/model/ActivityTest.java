package model;

import static org.junit.Assert.assertEquals;
//import java.time.LocalDate; !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActivityTest {
    private Activity testActivity;

    @BeforeEach
    void runBefore() {
        testActivity = new Activity("Coding");
    }

    @Test
    void testConstructor() {
        assertEquals("Coding", testActivity.getName());
        assertEquals(0, testActivity.getSessions().size());
        assertEquals(0, testActivity.getStreak());
        assertEquals(0, testActivity.getTotalTime());
        assertEquals(1, testActivity.getNextSessionId());
    }

    @Test
    void testSetName() {
        testActivity.setName("Drawing");
        assertEquals("Drawing", testActivity.getName());
    }

    @Test
    void testAddSession() {
        testActivity.addSession(new Session(5));
        assertEquals(1, testActivity.getSessions().size());
    }

    @Test
    void testAddMultipleSessions() {
        testActivity.addSession(new Session(5));
        testActivity.addSession(new Session(10));
        assertEquals(2, testActivity.getSessions().size());
    }

    @Test
    void testAddSessionSameDate() {
        testActivity.updateSessionTime();
    }

    @Test
    void testRemoveSession() {

    }

    @Test
    void testGetSessionsOnDate() {

    }

    @Test
    void testGetDateTotalTime() {

    }

    @Test
    void testToString() {

    }

}