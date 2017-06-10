package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.function.IntPredicate;

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
        assertEquals(anzan.level.minimum, testAnzan.getCurrentLevel(), "Default constructor level failed");
    }

    @Test //Verify constructor works and sets level
    void testInitialLevel() {
        anzan testAnzan = new anzan(anzan.level.minimum);
        assertEquals(anzan.level.minimum, testAnzan.getCurrentLevel(), "Initial level not set by constructor");
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

    @Test //Verify minimum anzan delay is equal to minimum constant
    void testInitialAnzanDelay() {
        defaultAnzan.setCurrentLevel(anzan.level.minimum);
        assertEquals(anzan.flashDelay.initial, defaultAnzan.getAnzanDelay(), "Initial anzan delay is incorrect");
    }

    @Test //Verify set delay changes delay based on level
    void setAnzanDelay() {
        //Set minimum level for minimum delay
        defaultAnzan.setCurrentLevel(anzan.level.minimum);
        //Get minimum delay
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
        defaultAnzan.setCurrentLevel(anzan.level.minimum);
        //Anzan delay should be minimum since level is minimum level -- this is tested above
        Duration Delay = defaultAnzan.getAnzanDelay();

        for (int i = anzan.level.minimum; i < anzan.level.minimum; i++) {
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

    @Test //Test generate anzan sequence function doesn't return empty list
    void AnzanSequenceEmpty() {
        //Generate sequence
        int flashSequence[] = defaultAnzan.generateSequence();
        //Verify sequence not empty array
        assertFalse(flashSequence.length == 0, "empty flash sequence generated");
    }

    @Test //Test generateSequence return correct number of elements based on level
    void AnzanSequenceCorrectMinimumLength() {
        //Set to minimum level
        defaultAnzan.setCurrentLevel(anzan.level.minimum);
        //Generate sequence at minimum level
        int flashSequence[] = defaultAnzan.generateSequence();

        //Make sure sequence generated has correct amount of numbers in it
        assertEquals(anzan.numberSequence.minimum, flashSequence.length, "Incorrect minimum sequence length");
    }

    @Test //Test generateSequence increments the correct amount of numbers per level
    void AnzanSequenceLengthIncremet() {
        //Set to minimum level
        defaultAnzan.setCurrentLevel(anzan.level.minimum);
        int flashSequence[];
        int sequenceLength = anzan.numberSequence.minimum;

        //no need to test minimum level, previous test already does that

        for (int i = anzan.level.minimum; i < anzan.level.maximum; i++) {
            //go to next level
            defaultAnzan.advanceLevel();
            //Generate sequence
            flashSequence = defaultAnzan.generateSequence();

            //check generated sequence has correct length
            assertEquals((sequenceLength = sequenceLength + anzan.numberSequence.increment),
                         flashSequence.length,
                         "Incorrect sequence length");
        }
    }

    @Test //Verify random number generator is working correctly when generating flash sequence
    void testSequenceRandomness() {
        //This is hard to test so will generate sequence at max level and make sure numbers are not all the same
        defaultAnzan.setCurrentLevel(anzan.level.maximum);
        int flashSequence[] = defaultAnzan.generateSequence();

        //check if array is all same values by comparing each value with first value
        for (int i = 1; i < flashSequence.length; i++) {
            if(flashSequence[0] != flashSequence[i]) {
                //found a different element so not all elements are same - pass test
                return;
            }
        }

        //If got here all elements are the same so RNG not working -- probably
        fail("flash sequence has all same numbers");
    }

    @Test //Verify flash sequence parameter is of correct length given level
        // This function only tests that the getSequenceLength method returns expected value
    void testFlashSequenceLength() {
        int desiredLength = anzan.numberSequence.minimum;
        //Test length for subsequent levels
        for (int i = anzan.level.minimum; i < anzan.level.maximum; i++) {
            //Set level
            defaultAnzan.setCurrentLevel(i);
            //Make sure sequence length getter returns correct value
            //sequence length should start at minimum and increment every level
            assertEquals(desiredLength, defaultAnzan.getSequenceLength(), "Sequence length getter returns incorrect value");

            desiredLength = desiredLength + anzan.numberSequence.increment;
        }

    }

    @Test //Verify generate sequence has correct length
            //This method generated a sequence and confirms it has the expected length
    void testGeneratedSequenceLength() {
        int flashSequence[];
        //Test length for subsequent levels
        for (int i = anzan.level.minimum; i < anzan.level.maximum; i++) {
            //Set level
            defaultAnzan.setCurrentLevel(i);
            //Generate sequence
            flashSequence = defaultAnzan.generateSequence();

            //Make sure sequence length is correct
            //sequence length should start at minimum and increment every level
            assertEquals(defaultAnzan.getSequenceLength(), //Can just call this method here because its being testes above
                         flashSequence.length,
                        "Sequence length getter returns incorrect value");
        }
    }

    @Test //Verify maxNumber getter and setter works
    void maxNumberGetterSetter() {
        fail("unimplemented");
    }

    @Test //Verify flash sequence doesn't contain numbers outside allowable range
    void maxAndMinSequenceNumber() {
        fail("unimplemented");
    }
}