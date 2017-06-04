package main;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Random;

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

    @FXML
    private ImageView playButton = new ImageView();

    @FXML
    private Label labelRandomNumber = new Label();

    private Random randGenerator = new Random();

    /**
     * Generate random number according to range value specified by comboBox
     * @return random number generated
     */
    private int getRandomNumber(){
        int rangeIndex = 0; //Helper variable to store range value
        try {
            //check range value from comboBox
            rangeIndex = comboRange.getSelectionModel().getSelectedIndex();

            //generate random number
            //Add 1 to value since generator may return value 0
            return (randGenerator.nextInt(maxValue[rangeIndex]) + 1);

        }
        catch (IndexOutOfBoundsException e){
            //If out of bounds or range hasn't been selected. Select 1-9 as default
            comboRange.getSelectionModel().select(0);
            rangeIndex = 0;

            //generate random number
            //Add 1 to value since generator may return value 0
            return (randGenerator.nextInt(maxValue[rangeIndex]) + 1);
        }
        catch (Exception e){
            throw e;
        }
    }

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

    public void playOnClick(){
        int _randomNumber;

        //make play button invisible
        playButton.setVisible(false);

        //Sanity check label is visible
        labelRandomNumber.setVisible(true);
        //Generate random number according to range
        _randomNumber = getRandomNumber();
        //Display number on text area
        labelRandomNumber.setText(Integer.toString(_randomNumber));
    }

}
