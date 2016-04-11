package MineSweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static java.lang.Math.random;

public class MineField extends Application {

    private static final int TILE_SIZE = 40;
    private static final int W = 300;
    private static final int H = 300;

    private static final int X_TILES = W / TILE_SIZE;
    private static final int Y_TILES = H / TILE_SIZE;

    private static final int numbMines = 20;
    private static int numExposedCells;

    private Cell[][] grid = new Cell[X_TILES][Y_TILES];



    public void createMineField() {
        int w = X_TILES, h = Y_TILES;
        int n = w*h; //total cells left
        int m = numbMines; //Number of mines to set

        numExposedCells = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                Cell cell = new Cell();
                cell.exposed = cell.marked = cell.hashMine = false;
                double p = (double) m / (double )n ; //probability of placing mine
                double g = random();
                if (g < p) {
                    cell.hashMine =true;
                    m--;
                }
                n--;
                grid[i][j] = cell;
               System.out.print(grid[i][j].hashMine + " ");
            }
            System.out.print("\n");
        }

        for (int i = 0; i < grid.length; i++) {//compute surrounding mine counts
            for (int j = 0; j < grid[0].length; j++) {
            }
        }

    }

    public boolean mark(int column, int row ) {

        // mark a Cell

        return false;
    }

    public int expose (int column, int row ) {
        /*

        0 if a cell was safely exposed and no bombs are  adjacent to it. In this case,
        all adjacent cells with 0 adjacent bombs should also be exposed. These newly
        revealed neighbors can be revealed by calls to isExposed

        1-8 if a cell was safely exposed and 1 or more bombs is adjacent to it

        -1 if a bomb was exposed at this time, game over.

        At any point in time a call

        */

        return 0;
    }

    public int isExposed(int column, int row) {
        return 0;
    }

    public int unexposedCount() {
        /*
            At any point in time a call to unexposedCount() should return how many cells
            can be exposed without setting off a bomb
        */

        return 0;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MineSweeper.fxml"));
        primaryStage.setTitle("MineSweeper");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {

        MineField mineField = new MineField();
        mineField.createMineField();
        launch(args);
    }
}
