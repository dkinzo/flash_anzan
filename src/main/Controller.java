package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class Controller {

    @FXML
    private ComboBox comboRange = new ComboBox();

    /**
     * @Constant Array containing max values of random number
     */
    final private int maxValue[] = {
            9,
            99,
            999,
            9999,
            99999
    };

//    //Buttons
//    @FXML
//    private Button btnDivision;
//    @FXML
//    private Button btnPlus;
//    @FXML
//    private Button btnMinus;
//    @FXML
//    private Button btnMultiplication;


    public void initialize(){
        final String comboBoxMessage = "1 - ";
        //Initialize range comboBox
        comboRange.getItems().add(comboBoxMessage);

    }
}
