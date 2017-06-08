package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

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
        assertEquals(anzan.level.initial, testAnzan.getCurrentLevel(), "Default constructor level failed");
    }

    @Test //Verify constructor works and sets level
    void testInitialLevel() {
        anzan testAnzan = new anzan(anzan.level.initial);
        assertEquals(anzan.level.initial, testAnzan.getCurrentLevel(), "Initial level not set by constructor");
    }

    @Test   //Verify object construction given invalid level throws exception
    void testConstructorException() {
        assertThrows(IllegalArgumentException.class, () -> {
            anzan testAnzan = new anzan(-1);
        });
    }

    @Test //Verify level setter and getter works
    void setLevel() {
        defaultAnzan.setCurrentLevel(19);
        assertEquals(19, defaultAnzan.getCurrentLevel(), "Level setter not working");
    }

    @Test //Verify initial anzan delay is equal to initial constant
    void testInitialAnzanDelay() {
        defaultAnzan.setCurrentLevel(anzan.level.initial);
        assertEquals(anzan.flashDelay.initial, defaultAnzan.getAnzanDelay(), "Initial anzan delay is incorrect");
    }

    @Test //Verify set delay changes delay based on level
    void setAnzanDelay() {
        //Set initial level for initial delay
        defaultAnzan.setCurrentLevel(anzan.level.initial);
        //Get initial delay
        Duration initialDelay = defaultAnzan.getAnzanDelay();

        //Change level and verify delay is smalled as level increases
        defaultAnzan.setCurrentLevel(20);
        Duration finalDelay = defaultAnzan.getAnzanDelay();
        assertTrue(initialDelay.toMillis() >= finalDelay.toMillis(), "Delay not decreasing with level increase");

        //Assert delay not less than minimum
        assertTrue(finalDelay.compareTo(anzan.flashDelay.minimum) >= 0, "Delay exceeds minimum delay");
    }

    @Test //Verify delay never less than minimum delay
    void testMinimumAnzanDelay() {
        defaultAnzan.setCurrentLevel(9999);
        assertTrue(defaultAnzan.getAnzanDelay().compareTo(anzan.flashDelay.minimum) >= 0, "Delay is less than minimum delay");
    }

    @Test //Verify anzan delay decreases by step after each level
    void testAnzanDelayStep() {
        defaultAnzan.setCurrentLevel(anzan.level.initial);
        //Anzan delay should be minimum since level its minimum level -- this is tested above
        Duration Delay = defaultAnzan.getAnzanDelay();

        for (int i = 0; i < 99; i++) {
            //Increase level
            defaultAnzan.advanceLevel();
            //Compute new delay
            Delay = Delay.minus(anzan.flashDelay.step);
            //Make sure Delay doesn't go below minimum delay
            if(Delay.compareTo(anzan.flashDelay.minimum) < 0){
                Delay = anzan.flashDelay.minimum;
            }
            //Assert new delay is same as delay at level
            assertEquals(Delay, defaultAnzan.getAnzanDelay(), "Delay step not working");
        }
    }

    @Test //Test generate anzan sequence function by making sure doesn't return empty list
    void testGenerateAnzanSequence() {
        defaultAnzan.generateSequence();
        //TODO implement
    }
}