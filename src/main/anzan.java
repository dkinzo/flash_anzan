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
        final static int initial = 1;           ///< Initial level
        final static int maximum = 99;          ///< Maximum level
        final static int normalIncrement = 1;       ///< Normal level increment
        //TODO implement advanced increment
        final static int advancedIncrement = 2;     ///< Advanced level increment (used when perfect score is achieved in level)
    }
    private int currentLevel = level.initial;            ///< Current anzan level

    /*
     * flashDelay abstracts constants related to delays between numbers that are displayed in the flash anzan sequence
     */
    static class flashDelay {
        final static Duration minimum = Duration.ofMillis(100);     ///< Minimum delay between flashing numbers
        final static Duration initial = Duration.ofMillis(1000);    ///< Initial anzan delay set for minimum level
        final static Duration increment = Duration.ofMillis(100);        ///< Decrease in delay between subsequent levels
    }
    private Duration anzanDelay = flashDelay.initial;               ///< Current flash delay for anzan object

    static class numberSequence {
        final static int initial = 2;       ///< Initial amount of numbers displayed at initial level
        final static int increment = 1;     ///< Amount of numbers added per level passed 
                                            ///  (e.g. if level 1 displays 2 numbers, level 2 will display 2 numbers + increment)
    }
    
    private Random randGenerator = new Random();    ///< Random number generator
    //endregion

    //region Constructors
    public anzan(int _level) {
        setCurrentLevel(_level);
    }

    public anzan() {
        //Set initial level to minimum level if not specified in constructor
        setCurrentLevel(level.initial);
    }

    //endregion

    //region Getters And Setters

    /**
     * Set anzan delay based on current level. Anzan delay is determined by calling calculateAnzanDelay() method
     */
    private void setAnzanDelay() {
        /* The anzan delay is calculated based on current level. The delay decreases by 100ms
         * after each level provided it doesn't go below minimum allowable delay.
         */
        anzanDelay = calculateAnzanDelay(getCurrentLevel());
    }

    Duration getAnzanDelay() {
        return anzanDelay;
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
        if(_level >= level.initial) {
            //If valid _level update member variable
            this.currentLevel = _level;
            //Compute new delay based on current level
            setAnzanDelay();
        } else {
            throw new IllegalArgumentException("currentLevel is less than minimum level() " + level.initial);
        }
    }
    //endregion

    //region private methods

    /**
     * Calculate anzan delay given a currentLevel. The anzan delay is the delay between flashing numbers. The initial value
     * is determined by the constant initial and decreases by increment every level until reaches
     * the minimum delay set by the minimum constant. The anzan delay should never be less than minimum.
     *
     * @return calculated anzan delay in milliseconds
     */
    private Duration calculateAnzanDelay(int targetLevel) {

        //For initial delay should be initialDelay
        //For initial + 1 delay should be initialDelay + 1
        //and so on
        Duration calculatedDelay = flashDelay.initial.minus(flashDelay.increment.multipliedBy(targetLevel - anzan.level.initial));
        if (calculatedDelay.compareTo(flashDelay.minimum) < 0){
            calculatedDelay = flashDelay.minimum;
        }

        return calculatedDelay;
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

        return null;
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
