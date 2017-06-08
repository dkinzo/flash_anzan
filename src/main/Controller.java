package main;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
        //int _randomNumber;

//        //make play button invisible
//        playButton.setVisible(false);
//
//        //Sanity check label is visible
//        labelRandomNumber.setVisible(true);
//
//        //Generate random number according to range
//        _randomNumber = getRandomNumber();
//        //Display number on text area
//        labelRandomNumber.setText(Integer.toString(_randomNumber));
//
//        //Wait before displaying next number
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        //Generate random number according to range
//        _randomNumber = getRandomNumber();
//        //Display number on text area
//        labelRandomNumber.setText(Integer.toString(_randomNumber));
    }

}
