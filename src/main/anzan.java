package main;

import java.time.Duration;
import java.util.Random;

/**
 * Created by derekkinzo on 6/7/17.
 *
 * Class models behaviour of flash anzan engine
 */
class anzan {

    //region Member Variables
    /*
     * The level determines the amount of numbers to be displayed in each iteration.
     * The time delay between numbers to be displayed is also directly coupled with level.
     */
    static class level{
        final static int minimum = 1;           ///< Initial level
        final static int maximum = 99;          ///< Maximum level
        final static int normalIncrement = 1;       ///< Normal level incrementLength
        //TODO implement advanced incrementLength
        final static int advancedIncrement = 2;     ///< Advanced level incrementLength (used when perfect score is achieved in level)
    }
    private int currentLevel = level.minimum;            ///< Current anzan level

    /*
     * flashDelay abstracts constants related to delays between numbers that are displayed in the flash anzan sequence
     */
    static class flashDelay {
        final static Duration minimum = Duration.ofMillis(100);     ///< Minimum delay between flashing numbers
        final static Duration initial = Duration.ofMillis(1000);    ///< Initial anzan delay set for minimumLength level
        final static Duration increment = Duration.ofMillis(100);        ///< Decrease in delay between subsequent levels
    }
    private Duration anzanDelay = flashDelay.initial;               ///< Current flash delay for anzan object

    /*
     * numberSequence defines the parameters for the sequence of numbers generated to be displayed.
     */
    static class numberSequence {
        final static int minimumLength = 2;       ///< Initial amount of numbers displayed at minimumLength level
        final static int incrementLength = 1;     ///< Amount of numbers added per level passed
                                                  ///  (e.g. if level 1 displays 2 numbers, level 2 will display 2 numbers + incrementLength)

        final static int defaultMaxNumber = 9;   ///< Default range of random numbers
        final static int minNumber = 0;          ///< Sets minimum number allowed in sequece
    }
    private int sequenceLength = numberSequence.minimumLength; ///< Define length of flash sequence
    private int maxNumber = 0;                       ///< max allowable number in flash sequence

    //RNG generate only positive numbers to in order to get negative values need to randomize operation (+,-) as well
    private Random randGenerator = new Random();    ///< Random number generator

    //    endregion

    //region Constructors
    public anzan(int _level) {
        setCurrentLevel(_level);
    }

    public anzan() {
        //Set minimumLength level to minimumLength level if not specified in constructor
        setCurrentLevel(level.minimum);
    }

    //    endregion

    //region Getters And Setters

    int getMaxNumber() {
        return maxNumber;
    }

    void setMaxNumber(int maxNumber) throws  IllegalArgumentException{
        //maxNumber cannot be negative.
        if (maxNumber < numberSequence.minNumber) {
            throw new IllegalArgumentException("maxNumber cannot be negative!");
        } else {
            this.maxNumber = maxNumber;
        }
    }

    int getSequenceLength() {
        return sequenceLength;
    }

    /**
     * Sets flash sequence length. This determined how many numbers a flash sequence contains. The mininum
     * numbers is defined by a constant and correspond to the minimumLength level. After every level the sequence length
     * increments by numberSequence.incrementLength
     */
    private void updateSequenceLength() throws IllegalArgumentException{
        int _sequenceLength = calculateSequenceLength(getCurrentLevel());

        //Make sure given argument is within allowable range
        //Since there is no maximum value, only check minimumLength value
        if (_sequenceLength < numberSequence.minimumLength) {
            throw new IllegalArgumentException("Attempted to set sequence length to smaller than acceptable");
        }

        this.sequenceLength = _sequenceLength;
    }

    Duration getAnzanDelay() {
        return anzanDelay;
    }

    /**
     * Set anzan delay based on current level. Anzan delay is determined by calling calculateAnzanDelay() method
     */
    private void updateAnzanDelay() throws IllegalArgumentException{
        /* The anzan delay is calculated based on current level. The delay decreases by 100ms
         * after each level provided it doesn't go below minimumLength allowable delay.
         */
        Duration _anzanDelay = calculateAnzanDelay(getCurrentLevel());

        //Validate delay to make sure within allowable range
        if((_anzanDelay.toMillis() < flashDelay.minimum.toMillis()) ||
           (_anzanDelay.toMillis() > flashDelay.initial.toMillis())){
            throw new IllegalArgumentException("Desired anzan delay is out of bounds");
        }

        //Set delay parameter
        this.anzanDelay = _anzanDelay;
    }

    int getCurrentLevel() {
        return this.currentLevel;
    }
    //    endregion

    //region private methods
    /**
     * Calculate anzan delay given a currentLevel. The anzan delay is the delay between flashing numbers. The minimumLength value
     * is determined by the constant minimumLength and decreases by incrementLength every level until reaches
     * the minimumLength delay set by the minimumLength constant. The anzan delay should never be less than minimumLength.
     *
     * @return calculated anzan delay in milliseconds
     */
    private Duration calculateAnzanDelay(int targetLevel) {

        //For minimumLength delay should be initialDelay
        //For minimumLength + 1 delay should be initialDelay + 1
        //and so on
        Duration calculatedDelay = flashDelay.initial.minus(flashDelay.increment.multipliedBy(targetLevel - anzan.level.minimum));
        if (calculatedDelay.compareTo(flashDelay.minimum) < 0){
            calculatedDelay = flashDelay.minimum;
        }

        return calculatedDelay;
    }

    /**
     * Calculates flash sequence length given level. Flash sequence at minimumLength level should contain numberSequence.minimumLength numbers
     * Sequence length should incrementLength by numberSequence.incrementLength after every level
     * @param desiredLevel The level for which the sequence length should be calculated
     * @return The sequence length
     */
    private int calculateSequenceLength(int desiredLevel) {
        return numberSequence.minimumLength + (getCurrentLevel() - level.minimum) * numberSequence.incrementLength;
    }
    //endregion

    //region public methods

    /**
     * currentLevel setter. Validate desired level by checking if desired level is not less than minimumLength level
     * @param _level desired level to be set
     * @throws IllegalArgumentException If desired level is less than minimumLength level
     */
    void setCurrentLevel(int _level) throws IllegalArgumentException{
        //Verify level is within allowable range
        if(_level >= level.minimum) {
            //If valid _level update member variable
            this.currentLevel = _level;
            //Compute new delay based on current level
            updateAnzanDelay();
            //Compute flash sequence length based on current level
            updateSequenceLength();
        } else {
            throw new IllegalArgumentException("currentLevel is less than minimumLength level() " + level.minimum);
        }
    }

    /**
     * Advances to next level (incrementLength 1 level)
     */
    void advanceLevel() {
        //TODO implement advanced incrementLength based on number of errors in level
        setCurrentLevel(getCurrentLevel() + level.normalIncrement);
    }

    /**
     * Generates sequence of random numbers to be displayed in flash anzan.
     * The numbers are generated based on the set limits and based on the current level.
     * @return Array of random numbers
     */
    int[] generateSequence() {

        int flashSequence[] = new int[getSequenceLength()];

        for (int i = 0; i < flashSequence.length; i++) {
            flashSequence[i] = generateRandomNumber();
        }

        return flashSequence;
    }

    /**
     * Generate random number according to desired range value
     * @return The random number
     */
    private int generateRandomNumber() {
        try {
            //Make sure maxNumber is valid
            if (maxNumber < 0) {
                maxNumber = numberSequence.defaultMaxNumber;
            }
            return (randGenerator.nextInt(maxNumber) + 1); //Increment 1 to value since randGenerator generated 0 - (n-1) numbers
        } catch (Exception e){
            //If couldn't generate random number then reset macNumber and return 0;
            maxNumber = numberSequence.defaultMaxNumber;
            return 0;
        }
    }

    //endregion
}
