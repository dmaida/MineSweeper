package MineSweeper;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    private int row;

    private int col;

    MineField mineField;

    Timer timer;

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
    private Button startBt;

    @FXML
    private MenuButton menuDif;

    @FXML
    private Label clock;

    @FXML
    private boolean isTimerSet;

    @FXML
    private void initialize() {
        level = 1;
        row = 9;
        col = 9;

        Image bomb = new Image(getClass().getResourceAsStream("smiley.png"));
        startBt.setBackground(Background.EMPTY.EMPTY);
        startBt.setGraphic(new ImageView(bomb));

        startButton();
        level1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                level = 1;
                row = 9;
                col = 9;
                menuDif.setText("Beginner");
            }
        });

        level2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                level = 2;
                row = 16;
                col = 16;
                menuDif.setText("Intermediate");
            }
        });

        level3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                level = 3;
                row = 16;
                col = 30;
                menuDif.setText("Expert");
            }
        });
    }

    @FXML
    public void startButton ( ) {

        restartTime();
        mineField = new MineField();
        mineField.createMineField(level);
        makeButtons();
    }

    public void setTimer () {

        timer = new Timer();
        TimerTask task = new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        clock.setText(i+"");
                        i++;
                    }
                });
            }
        };

        timer.schedule(task, 0 , 1000);
    }

    public void restartTime () {

        if(isTimerSet) {
            isTimerSet = false;
            timer.cancel();
            clock.setText("0");
        }
    }

    public void gameOver() {
        minesLeft.setText("You lost");

        timer.cancel();
        timer = new Timer();
        isTimerSet = false;

        for (int r = 0; r < mineField.height; r++) {
            for (int c = 0; c <  mineField.width; c++) {

                if (mineField.grid[r][c].hashMine) {
                    buttonMatrix[r][c].setBackground(Background.EMPTY);
                    Image bomb = new Image(getClass().getResourceAsStream("cartoon-evil-bomb.png"));
                    buttonMatrix[r][c].setGraphic(new ImageView(bomb));
                }
            }
        }
    }

    public void win() {
        timer.cancel();
        timer = new Timer();
        minesLeft.setText("You won");
    }

    public void updateView() {

        minesLeft.setText(mineField.unexposedCount() +"");

        if (mineField.unexposedCount() == 0) {
            win();
        }
        if (mineField.lost) {
            gameOver();
        }

        for (int r = 0; r < mineField.height; r++) {
            for (int c = 0; c < mineField.width; c++) {
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
            row.setMaxHeight(30);
            row.setMinHeight(30);
            gp.getRowConstraints().add(row);
        }

        for (int i = 0; i < col; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setMaxWidth(30);
            column.setMinWidth(30);
            gp.getColumnConstraints().add(column);
        }

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                HBoxMatrix[r][c] = new HBox();
                buttonMatrix[r][c] = new Button();
                buttonMatrix[r][c].setPrefSize(30, 30);
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

                            if (!isTimerSet) {
                                setTimer();
                                isTimerSet = true;
                            }

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
}
