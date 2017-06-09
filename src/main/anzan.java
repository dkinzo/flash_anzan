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
        final static int normalIncrement = 1;       ///< Normal level increment
        //TODO implement advanced increment
        final static int advancedIncrement = 2;     ///< Advanced level increment (used when perfect score is achieved in level)
    }
    private int currentLevel = level.minimum;            ///< Current anzan level

    /*
     * flashDelay abstracts constants related to delays between numbers that are displayed in the flash anzan sequence
     */
    static class flashDelay {
        final static Duration minimum = Duration.ofMillis(100);     ///< Minimum delay between flashing numbers
        final static Duration initial = Duration.ofMillis(1000);    ///< Initial anzan delay set for minimum level
        final static Duration increment = Duration.ofMillis(100);        ///< Decrease in delay between subsequent levels
    }
    private Duration anzanDelay = flashDelay.initial;               ///< Current flash delay for anzan object

    /*
     * numberSequence defines the parameters for the sequence of numbers generated to be displayed.
     */
    static class numberSequence {
        final static int minimum = 2;       ///< Initial amount of numbers displayed at minimum level
        final static int increment = 1;     ///< Amount of numbers added per level passed 
                                            ///  (e.g. if level 1 displays 2 numbers, level 2 will display 2 numbers + increment)
    }
    private int sequenceLength = numberSequence.minimum; ///< Define length of flash sequence

    private Random randGenerator = new Random();    ///< Random number generator

    //region Constructors
    public anzan(int _level) {
        setCurrentLevel(_level);
    }

    public anzan() {
        //Set minimum level to minimum level if not specified in constructor
        setCurrentLevel(level.minimum);
    }

    //endregion

    //region Getters And Setters

    //endregion

    int getSequenceLength() {
        return sequenceLength;
    }

    /**
     * Sets flash sequence length. This determined how many numbers a flash sequence contains. The mininum
     * numbers is defined by a constant and correspond to the minimum level. After every level the sequence length
     * increments by numberSequence.increment
     */
    private void setSequenceLength() throws IllegalArgumentException{
        int _sequenceLength = calculateSequenceLength(getCurrentLevel());

        //Make sure given argument is within allowable range
        //Since there is no maximum value, only check minimum value
        if (_sequenceLength < numberSequence.minimum) {
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
    private void setAnzanDelay() throws IllegalArgumentException{
        /* The anzan delay is calculated based on current level. The delay decreases by 100ms
         * after each level provided it doesn't go below minimum allowable delay.
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

    /**
     * currentLevel setter. Validate desired level by checking if desired level is not less than minimum level
     * @param _level desired level to be set
     * @throws IllegalArgumentException If desired level is less than minimum level
     */
    void setCurrentLevel(int _level) throws IllegalArgumentException{
        //Verify level is within allowable range
        if(_level >= level.minimum) {
            //If valid _level update member variable
            this.currentLevel = _level;
            //Compute new delay based on current level
            setAnzanDelay();
            //Compute flash sequence length based on current level
            setSequenceLength();
        } else {
            throw new IllegalArgumentException("currentLevel is less than minimum level() " + level.minimum);
        }
    }

    //endregion

    //region private methods


    /**
     * Calculate anzan delay given a currentLevel. The anzan delay is the delay between flashing numbers. The minimum value
     * is determined by the constant minimum and decreases by increment every level until reaches
     * the minimum delay set by the minimum constant. The anzan delay should never be less than minimum.
     *
     * @return calculated anzan delay in milliseconds
     */
    private Duration calculateAnzanDelay(int targetLevel) {

        //For minimum delay should be initialDelay
        //For minimum + 1 delay should be initialDelay + 1
        //and so on
        Duration calculatedDelay = flashDelay.initial.minus(flashDelay.increment.multipliedBy(targetLevel - anzan.level.minimum));
        if (calculatedDelay.compareTo(flashDelay.minimum) < 0){
            calculatedDelay = flashDelay.minimum;
        }

        return calculatedDelay;
    }

    /**
     * Calculates flash sequence length given level. Flash sequence at minimum level should contain numberSequence.minimum numbers
     * //TODO finish header
     * @param currentLevel
     * @return
     */
    private int calculateSequenceLength(int currentLevel) {
        return numberSequence.minimum;
    }
    //endregion

    //region public methods
    /**
     * Advances to next level (increment 1 level)
     */
    void advanceLevel() {
        //TODO implement advanced increment based on number of errors in level
        setCurrentLevel(getCurrentLevel() + level.normalIncrement);
    }

    /**
     * Generates sequence of random numbers to be displayed in flash anzan.
     * The numbers are generated based on the set limits and based on the current level.
     * @return Array of random numbers
     */
    int[] generateSequence() {

        int array[] = {0, 0, 0};

        return array;
    }

    //endregion

//    /**
//     * Generate random number according to range value specified by comboBox
//     * @return random number generated
//     */
//    private int getRandomNumber(){
//        int rangeIndex = 0; //Helper variable to store range value
//        try {
//            //check range value from comboBox
//            rangeIndex = comboRange.getSelectionModel().getSelectedIndex();
//
//            //generate random number
//            //Add 1 to value since generator may return value 0
//            return (randGenerator.nextInt(maxValue[rangeIndex]) + 1);
//
//        }
//        catch (IndexOutOfBoundsException e){
//            //If out of bounds or range hasn't been selected. Select 1-9 as default
//            comboRange.getSelectionModel().select(0);
//            rangeIndex = 0;
//
//            //generate random number
//            //Add 1 to value since generator may return value 0
//            return (randGenerator.nextInt(maxValue[rangeIndex]) + 1);
//        }
//        catch (Exception e){
//            throw e;
//        }
//    }
}
