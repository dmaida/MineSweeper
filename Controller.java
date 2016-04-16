package MineSweeper;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;

public class Controller {


    private int row;


    private int col;


    MineField mineField;

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
    private Label minesLeft;




    @FXML
    private void initialize() {

        level = 1;
        row = 9;
        col = 9;

        startButton();
        level1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                level = 1;
                row = 9;
                col = 9;
            }
        });

        level2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                level = 2;
                row = 16;
                col = 16;
            }
        });

        level3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                level = 3;
                row = 16;
                col = 30;
            }
        });
    }

    @FXML
    public void startButton ( ) {
        mineField = new MineField();
        mineField.createMineField(level);
        makeButtons();
    }

    public void gameOver() {
        minesLeft.setText("lost");

        for (int r = 0; r < row; r++) {
            for (int c = 0; c <  col; c++) {

                if (mineField.grid[r][c].hashMine) {
                    buttonMatrix[r][c].setBackground(Background.EMPTY);
                    Image bomb = new Image(getClass().getResourceAsStream("cartoon-evil-bomb.png"));
                    buttonMatrix[r][c].setGraphic(new ImageView(bomb));
                }
            }
        }
    }

    public void win() {
        minesLeft.setText("Won");
    }

    public void updateView() {

        minesLeft.setText(mineField.unexposedCount() +"");

        if (mineField.unexposedCount() == 0) {
            win();
        }
        if (mineField.lost) {
            gameOver();
        }

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
               if (mineField.grid[r][c].exposed && mineField.grid[r][c].numbSurroundingmines == 0 ) {
                   buttonMatrix[r][c].setVisible(false);
               }
                else if(mineField.grid[r][c].exposed) {
                   buttonMatrix[r][c].setBackground(Background.EMPTY);
                   buttonMatrix[r][c].setText(mineField.grid[r][c].numbSurroundingmines+"");
               }
                else if (mineField.grid[r][c].marked) {
                    buttonMatrix[r][c].setText("X");
               }
                else if (!mineField.grid[r][c].marked) {
                   buttonMatrix[r][c].setText("");
               }
            }
        }
    }

    public void makeButtons ( ) {
        minesLeft.setText(mineField.unexposedCount() +"");
        gp.getColumnConstraints().removeAll(gp.getColumnConstraints());
        gp.getRowConstraints().removeAll(gp.getRowConstraints());
        gp.getChildren().removeAll(gp.getChildren());
        buttonMatrix = new Button[row][col];
        HBoxMatrix = new HBox[row][col];

        for (int i = 0; i < row; i++) {
            RowConstraints row = new RowConstraints();
            row.setMaxHeight(45);
            gp.getRowConstraints().add(row);
        }

        for (int i = 0; i < col; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setMaxWidth(45);
            gp.getColumnConstraints().add(column);
        }

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                HBoxMatrix[r][c] = new HBox();
                buttonMatrix[r][c] = new Button();
                buttonMatrix[r][c].setPrefSize(60, 60);
                HBoxMatrix[r][c].setAlignment(Pos.CENTER);
                HBoxMatrix[r][c].getChildren().add(buttonMatrix[r][c]);
                gp.add(HBoxMatrix[r][c], c, r);

                HBox currentButton = HBoxMatrix[r][c];

                buttonMatrix[r][c].setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        int r = gp.getRowIndex(currentButton);
                        int c = gp.getColumnIndex(currentButton);

                        if (mineField.unexposedCount() != 0 && mineField.lost == false) {
                            if (event.isPrimaryButtonDown() && !mineField.grid[r][c].marked) {
                                mineField.expose(c, r);

                            } else if (event.isSecondaryButtonDown()) {
                                mineField.mark(gp.getColumnIndex(currentButton), gp.getRowIndex(currentButton));
                            }
                        }

                        updateView();
                    }
                });
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
