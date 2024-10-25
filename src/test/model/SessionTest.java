package model;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionTest {
    private Session testSession;

    @BeforeEach
    void runBefore() {
        testSession = new Session(10);
    }

    @Test
    void testConstructor() {
        assertEquals(10, testSession.getDurationInHours());
        assertEquals(1, testSession.getId());
        assertEquals(LocalDate.now(), testSession.getDate());
    }

    @Test
    void testAddDuration() {
        testSession.addDuration(5);
        assertEquals(15, testSession.getDurationInHours());
    }

    @Test
    void testAddMultipleDurations() {
        testSession.addDuration(5);
        testSession.addDuration(10);
        assertEquals(25, testSession.getDurationInHours());
    }

    @Test
    void testSetId() {
        assertEquals(1, testSession.getId());
        testSession.setId(2);
        assertEquals(2, testSession.getId());
    }

    @Test
    void testSetDate() {
        testSession.setDate(LocalDate.of(2020, 01, 02));
        assertEquals(LocalDate.of(2020, 01, 02), testSession.getDate());
    }

    @Test
    void testToString() {
        assertEquals("Session ID: " + testSession.getId()
                + ", Duration: " + testSession.getDurationInHours() + " hours"
                + ", Date: " + testSession.getDate(), testSession.toString());
    }
}