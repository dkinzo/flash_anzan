package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

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
        defaultAnzan.setCurrentLevel(anzan.level.maximum);
        assertTrue(defaultAnzan.getAnzanDelay().compareTo(anzan.flashDelay.minimum) >= 0, "Delay is less than minimum delay");
    }

    //TODO add test maximum allowable level

    @Test //Verify anzan delay decreases by increment after each level
    void testAnzanDelayStep() {
        defaultAnzan.setCurrentLevel(anzan.level.initial);
        //Anzan delay should be minimum since level its minimum level -- this is tested above
        Duration Delay = defaultAnzan.getAnzanDelay();

        for (int i = 0; i < 99; i++) {
            //Increase level
            defaultAnzan.advanceLevel();
            //Compute new delay
            Delay = Delay.minus(anzan.flashDelay.increment);
            //Make sure Delay doesn't go below minimum delay
            if(Delay.compareTo(anzan.flashDelay.minimum) < 0){
                Delay = anzan.flashDelay.minimum;
            }
            //Assert new delay is same as delay at level
            assertEquals(Delay, defaultAnzan.getAnzanDelay(), "Delay increment not working");
        }
    }

    @Test //Test generate anzan sequence function by making sure doesn't return empty list
    void testGenerateAnzanSequence() {
        //Generate sequence
        int flashSequence[] = defaultAnzan.generateSequence();
        //Verify sequence not empty array
        assertFalse(flashSequence.length == 0, "empty flash sequence generated");
    }

    @Test //Test generateSequence return correct number of elements based on level
    void testGenerateSequenceLength() {
        //Set to initial level
        defaultAnzan.setCurrentLevel(anzan.level.initial);
        //Generate sequence at initial level
        int flashSequence[] = defaultAnzan.generateSequence();

        //Make sure sequence generated has correct amount of numbers in it
        assertEquals(anzan.numberSequence.initial, flashSequence.length, "Incorrect initial sequence length");
    }

    @Test //Test generateSequence increments the correct amount of numbers per level
    void testGenerateSequenceIncrement() {
        //Set to initial level
        defaultAnzan.setCurrentLevel(anzan.level.initial);
        //Generate sequence at initial level
        int flashSequence[] = defaultAnzan.generateSequence();

        //no need to test initial level, previous test already does that

        for (int i = 0; i < anzan.level.maximum; i++) {
            //go to next level
            defaultAnzan.advanceLevel();
            //Generate sequence
            flashSequence = defaultAnzan.generateSequence();

            //check generated sequence has correct length
            assertEquals(anzan.numberSequence.initial + anzan.numberSequence.increment*defaultAnzan.getCurrentLevel(),
                         flashSequence.length,
                         "Incorrect sequence length");
        }
    }

    @Test //Verify random number generator is working correctly when generating flash sequence
    void testSequenceRandomness() {
        //This is hard to test so will generate sequence at max level and make sure numbers are not all the same
        defaultAnzan.setCurrentLevel(anzan.level.maximum);
        int flashSequence[] = defaultAnzan.generateSequence();

        //Make sure sequence does not contain same numbers
//        boolean match = Arrays.stream(flashSequence).allMatch(IntPredicate.equals(flashSequence[0]));
//        assertFalse(match, "Flash sequence all same values");
    }
}