package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by derekkinzo on 6/7/17.
 * Unit tests for anzan class
 */
class anzanTest {

    private anzan defaultAnzan;

    @BeforeEach
    void setUp() {
        defaultAnzan = new anzan();
    }

    @Test //Verify default constructor initializes level to minimum Level
    void testDefaultConstructor() {
        anzan testAnzan = new anzan();
        assertEquals(anzan.minLevel, testAnzan.getLevel(), "Default constructor level failed");
    }

    @Test //Verify constructor works and sets level
    void testInitialLevel() {
        anzan testAnzan = new anzan(anzan.minLevel);
        assertEquals(anzan.minLevel, testAnzan.getLevel(), "Initial level not set by constructor");
    }

    @Test   //Verify object construction given invalid level throws exception
    void testConstructorException() {
        assertThrows(IllegalArgumentException.class, () -> {
            anzan testAnzan = new anzan(-1);
        });
    }

    @Test //Verify level setter and getter works
    void setLevel() {
        defaultAnzan.setLevel(19);
        assertEquals(19, defaultAnzan.getLevel(), "Level setter not working");
    }

    @Test //Verify set delay changes delay based on level
    void setAnzanMillisDelay() {
        //Set initial level for initial delay
        defaultAnzan.setLevel(anzan.minLevel);

        //Get initial delay
        int initialDelay = defaultAnzan.getAnzanMillisDelay();

        //Change level and verify delay is smalled as level increases
        defaultAnzan.setLevel(20);
        int finalDelay = defaultAnzan.getAnzanMillisDelay();
        assertTrue(initialDelay < finalDelay, "Delay not decreasing with level increase");

        //Assert delay not less than minimum
        assertTrue(finalDelay >= anzan.minMillisDelay, "Delay exeeds minimum delay");
    }

    @Test //Verify delay never less than minimum delay
    void testMinimumAnzanDelay() {
        defaultAnzan.setLevel(9999);
        assertTrue(defaultAnzan.getAnzanMillisDelay() >= anzan.minMillisDelay, "Delay is less than minimum delay");
    }
}