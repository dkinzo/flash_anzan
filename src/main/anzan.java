package main;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by derekkinzo on 6/7/17.
 *
 * Class models behaviour of flash anzan engine
 */
class anzan {

    /*
     * The level determines the amount of numbers to be displayed in each iteration.
     * The time delay between numbers to be displayed is also directly coupled with level.
     */
    final static int minLevel = 1;           ///< Minimum valid level
    private int Level = minLevel;                   ///< Current anzan level

    final static Duration minAnzanDelay = Duration.ofMillis(100);        ///< Minimum delay between flashing numbers
    final static Duration initialAnzanDelay = Duration.ofMillis(1000);   ///< Initial anzan delay set for minimum level
    final static Duration anzanDelayStep = Duration.ofMillis(100);       ///< Decrease in delay between subsequent levels
    private Duration anzanDelay =  minAnzanDelay;

    private Random randGenerator = new Random();    ///< Random number generator

    //region Constructors
    public anzan(int _level) {
        setLevel(_level);
    }

    public anzan() {
        //Set initial level to minimum level if not specified in constructor
        setLevel(minLevel);
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
        anzanDelay = calculateAnzanDelay(getLevel());
    }

    Duration getAnzanDelay() {
        return anzanDelay;
    }

    int getLevel() {
        return this.Level;
    }

    /**
     * Level setter. Validate desired level by checking if desired level is not less than minimum level
     * @param _level desired level to be set
     * @throws IllegalArgumentException If desired level is less than minimum level
     */
    void setLevel(int _level) throws IllegalArgumentException{
        //Verify level is within allowable range
        if(_level >= minLevel) {
            //If valid _level update member variable
            this.Level = _level;
            //Compute new delay based on current level
            setAnzanDelay();
        } else {
            throw new IllegalArgumentException("Level is less than minimum level() " + minLevel);
        }
    }
    //endregion


    //region private methods

    /**
     * Calculate anzan delay given a Level. The anzan delay is the delay between flashing numbers. The intial value
     * is determined by the constant initialAnzanDelay and decreases by anzanDelayStep every level until reaches
     * the mininum delay set by the minAnzanDelay constant. The anzan delay should never be less than minAnzanDelay.
     *
     * @return calculated anzan delay in milliseconds
     */
    private Duration calculateAnzanDelay(int targetLevel) {

        //For minLevel delay should be initialDelay
        //For minLevel + 1 delay should be initialDelay + 1
        //and so on
        Duration calculatedDelay = initialAnzanDelay.minus(anzanDelayStep.multipliedBy(targetLevel - anzan.minLevel));
        if (calculatedDelay.compareTo(minAnzanDelay) < 0){
            calculatedDelay = minAnzanDelay;
        }

        return calculatedDelay;
    }


    //endregion


    /**
     * Advances to next level (increment 1 level)
     */
    void advanceLevel() {
        setLevel(getLevel() + 1);
    }

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
