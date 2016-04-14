package MineSweeper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

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
    private MineField field;



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


        mineField = new MineField();
        mineField.createMineField(level);
        field = mineField;
        System.out.println("You pressed start");
        makeButtons();
        expose(0, 0);
    }

    @FXML
    public int chooseLevel() {
        return 2;

    }

    public int expose (int column, int row ) {
        Cell cell = field.grid[row][column];

        if (cell.hashMine) { //game over, exposed mine
            return -1;
        }
        if (cell.exposed) return -2;

        cell.exposed = true;
        buttonMatrix[row][column].setVisible(false);


        int n = cell.numbSurroundingmines;

        if (n == 0) {
            int w = field.width, h = field.height;
            boolean changed =true;
            while(changed) {
                int rr, cc;
                changed = false;
                for (rr = 0; rr < h; rr++) {
                    for (cc = 0; cc < w; cc++) {
                        if (isExposed(cc, rr)) {
                            changed = true;
                        }
                    }
                }
            };
        }
        return n;
    }

    public boolean isExposed(int column, int row) {
        Cell cell = field.grid[row][column];
        if(!cell.exposed && !cell.hashMine) {
            int w = field.width, h = field.height;
            int i, j;
            for (j = -1; j <= +1; j++) {
                for (i = -1; i <= +1; i++) {
                    if (i == 0 && j ==0 ) continue;
                    int rr = row+j, cc = column+i;
                    if (rr < 0 || rr >= h ||  cc <0 || cc >= w) continue;
                    Cell neighbor = field.grid[rr][cc];
                    if (neighbor.exposed && neighbor.numbSurroundingmines == 0) {
                        cell.exposed = true;
                        buttonMatrix[row][column].setBackground(Background.EMPTY );

                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void makeButtons ( ) {
        gp.getColumnConstraints().removeAll(gp.getColumnConstraints());
        gp.getRowConstraints().removeAll(gp.getRowConstraints());
        gp.getChildren().removeAll(gp.getChildren());
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
                char ch = (char) field.grid[r][c].numbSurroundingmines;

                if (field.grid[r][c].hashMine) {
                    buttonMatrix[r][c].setText("X");

                } else if(field.grid[r][c].numbSurroundingmines != 0){
                    String numb = "" + field.grid[r][c].numbSurroundingmines;

                    buttonMatrix[r][c].setText(numb);
                } else {
                    buttonMatrix[r][c].setText("");
                }

                buttonMatrix[r][c].setPrefSize(60, 60);
                HBoxMatrix[r][c].setAlignment(Pos.CENTER);
                HBoxMatrix[r][c].getChildren().add(buttonMatrix[r][c]);
                gp.add(HBoxMatrix[r][c], c, r);

                HBox currentButton = HBoxMatrix[r][c];

                buttonMatrix[r][c].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        System.out.println("row = " + gp.getRowIndex(currentButton));
                        System.out.println("col = " + gp.getColumnIndex(currentButton));
                        expose(gp.getColumnIndex(currentButton), gp.getRowIndex(currentButton));

                    }
                });
            }
        }
        System.out.println(gp.getChildren().indexOf(gp.getChildren().get(4)));
        gp.getChildren().indexOf(gp.getChildren().get(9));
        gp.getChildren().get(100).setVisible(false);
        System.out.println("row = " + gp.getRowIndex(gp.getChildren().get(100)));
        System.out.println("col = " + gp.getColumnIndex(gp.getChildren().get(100)));

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
