package model;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionTest {
    private Session testSession;

    @BeforeEach
    void runBefore() {
        testSession = new Session(10.0);
    }

    @Test
    void testConstructor() {
        assertEquals(10.0, testSession.getDurationInHours(), 0.001);
        // Initially, sessionId is not set until added to an Activity
        assertEquals(0, testSession.getId());
        assertEquals(LocalDate.now(), testSession.getDate());
    }

    @Test
    void testAddDuration() {
        testSession.addDuration(5.0);
        assertEquals(15.0, testSession.getDurationInHours(), 0.001);
    }

    @Test
    void testAddMultipleDurations() {
        testSession.addDuration(5.0);
        testSession.addDuration(10.0);
        assertEquals(25.0, testSession.getDurationInHours(), 0.001);
    }

    @Test
    void testSetDurationInHours() {
        assertEquals(10.0, testSession.getDurationInHours(), 0.001);
        testSession.setDurationInHours(5.0);
        assertEquals(5.0, testSession.getDurationInHours(), 0.001);
        testSession.setDurationInHours(15.0);
        assertEquals(15.0, testSession.getDurationInHours(), 0.001);
        testSession.setDurationInHours(0.0);
        assertEquals(0.0, testSession.getDurationInHours(), 0.001);
    }

    @Test
    void testSetId() {
        assertEquals(0, testSession.getId());
        testSession.setId(2);
        assertEquals(2, testSession.getId());
    }

    @Test
    void testSetDate() {
        testSession.setDate(LocalDate.of(2020, 1, 2));
        assertEquals(LocalDate.of(2020, 1, 2), testSession.getDate());
    }

    @Test
    void testToString() {
        testSession.setId(1);
        String expected = "Session ID: 1, Duration: 10.00 hours, Date: " + testSession.getDate();
        assertEquals(expected, testSession.toString());
    }
}
