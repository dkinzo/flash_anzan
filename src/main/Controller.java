package main;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class Controller {

    @FXML
    private ComboBox<String> comboRange = new ComboBox<>();

    /**
     * Array containing max values of random number
     */
    final private int maxValue[] = {
            9,
            99,
            999,
            9999,
            99999
    };



    public void initialize(){

        //Initialize Range for random number values
        /* Expected comboBox values - Fixed values, should not change during application
         * 1 - 9
         * 1 - 99
         * 1 - 999
         * 1 - 9999
         * 1 - 99999
         */
        //Constant string in items
        final String comboBoxMessage = "1 - ";
        //Add values to comboBox range
        for (int i = 0; i < 5; i++){
            comboRange.getItems().add(comboBoxMessage + Integer.toString(maxValue[i]));
        }


    }
}
