package MineSweeper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class Controller {

    public void startButton ( ) {
        MineField mineField = new MineField();
        mineField.createMineField();

        System.out.println("You pressed start");
    }

    @FXML
    private GridPane gp;

    @FXML
    private void initialize() {
        //Button newBT = new Button("Oh Yeah");
        gp.setGridLinesVisible(true);
    }

    /*

    The Controller should create a new Minefield Model in response to the programs
    initialization or in response to a press of the "start" button

    Should call a mark method in the MineField object to toggle a cells marked status
    in response to a left mouse click

    Should call a expose method in the MineField object to expose a cell in response
    to a right mouse click

    Should update the View in response to mark/expose/start requests
     */

}
