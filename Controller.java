package MineSweeper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;

public class Controller {
    private int row;
    private int col;



    @FXML
    private GridPane gp;

    @FXML
    private MenuItem level1;

    @FXML
    private MenuItem level2;

    @FXML
    private MenuItem level3;


    @FXML
    private int level;

    @FXML
    private Button[][] buttonMatrix;

    @FXML
    private HBox[][]  HBoxMatrix;


    @FXML
    private void initialize() {

        level = 1;
        row = 9;
        col = 9;

        level1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                level = 1;
                row = 9;
                col = 9;
                System.out.println("level == " + level );
            }
        });

        level2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                level = 2;
                row = 16;
                col = 16;
                System.out.println("level == " + level );
            }
        });

        level3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                level = 3;
                row = 16;
                col = 30;
                System.out.println("level == " + level );
            }
        });


    }

    @FXML
    public void startButton ( ) {


        MineField mineField = new MineField();
        mineField.createMineField(level);
        System.out.println("You pressed start");
        makeButtons();
    }
    @FXML
    public int chooseLevel() {
        return 2;

    }
    public void makeButtons ( ) {
        gp.getColumnConstraints().removeAll(gp.getColumnConstraints());
        gp.getRowConstraints().removeAll(gp.getRowConstraints());
        gp.setGridLinesVisible(true);
        buttonMatrix = new Button[row][col];
        HBoxMatrix = new HBox[row][col];


        for (int i = 0; i < row; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(25);
            gp.getRowConstraints().add(row);
        }

        for (int i = 0; i < col; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(25);
            gp.getColumnConstraints().add(column);


        }



        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                HBox hbBtn = new HBox(10);
                HBoxMatrix[r][c] = new HBox();
                buttonMatrix[r][c] = new Button();

                buttonMatrix[r][c].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                    }
                });

                buttonMatrix[r][c].setPrefSize(60, 60);
                HBoxMatrix[r][c].setAlignment(Pos.CENTER);
                HBoxMatrix[r][c].getChildren().add(buttonMatrix[r][c]);
                gp.add(HBoxMatrix[r][c], c, r);
            }
        }
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
