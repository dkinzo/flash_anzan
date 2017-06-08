package main;

import java.util.Random;

/**
 * Created by derekkinzo on 6/7/17.
 *
 * Class models behaviour of flash anzan engine
 */
public class anzan {

    /*
     * The level determines the amount of numbers to be displayed in each iteration.
     * The time delay between numbers to be displayed is also directly coupled with level.
     */
    private int Level;                      ///< Current anzan level
    final static public int minLevel = 1;         ///< Minimum valid level
    private int anzanMillisDelay;           ///< Delay between numbers displayed in milliseconds
    final static public int minMillisDelay = 300; ///< Minimum delay in milliseconds

    //region Constructors
    public anzan(int _level) {
        setLevel(_level);
    }

    public anzan() {
        setLevel(0);
    }

    //endregion

    //region Getters And Setters

    protected void setAnzanMillisDelay() {
        /* The anzan delay is calculated based on current level. The delay decreases by 100ms
         * after each level provided it doesn't go below minimum allowable delay.
         */

        this.anzanMillisDelay = anzanMillisDelay;
    }

    public int getAnzanMillisDelay() {
        return anzanMillisDelay;
    }

    public int getLevel() {
        return this.Level;
    }

    public void setLevel(int _level) throws IllegalArgumentException{
        if(_level >= minLevel) {
            this.Level = _level;
        } else {
            throw new IllegalArgumentException("Level is less than minimum level() " + minLevel);
        }
    }
    //endregion


//    private int anumber;
//
//    public void setAnumber(int newanumber) {
//        if (newanumber >= 1 && newanumber <= 3) {
//            anumber = newanumber;
//        } else {
//            throw new IllegalArgumentException("anumber out of range");
//        }
//    }


    private Random randGenerator = new Random(); ///< Random number generator

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
