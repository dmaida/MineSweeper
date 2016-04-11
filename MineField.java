package MineSweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Random;

import static java.lang.Math.min;
import static java.lang.Math.random;

public class MineField extends Application {

    private static final int TILE_SIZE = 40;
    private static final int W = 300;
    private static final int H = 300;

    private static final int X_TILES = W / TILE_SIZE;
    private static final int Y_TILES = H / TILE_SIZE;

    private static final int numbMines = 20; // total number of mines
    private static int numExposedCells = 0;  // total cell exposed

    private Cell[][] grid = new Cell[X_TILES][Y_TILES];



    public void createMineField() {
        int w = X_TILES, h = Y_TILES;
        int n = w*h; //total cells left
        int m = numbMines; //Number of mines to set

        int row, col;

        numExposedCells = 0;

        for (row = 0; row < grid.length; row++) { // place mines
            for ( col = 0; col < grid[0].length; col++) {
                Cell cell = new Cell();
                cell.exposed = cell.marked = cell.hashMine = false;
                double p = (double) m / (double )n ; //probability of placing mine
                double g = random();
                if (g < p) {
                    cell.hashMine =true;
                    m--;
                }
                n--;
                grid[row][col] = cell;
                if (grid[row][col].hashMine) {
                    System.out.print("X" + " ");
                } else {
                    System.out.print("*"+ " ");
                }
            }
            System.out.print("\n");
        }
            System.out.print("\n");
        for (row = 0; row < grid.length; row++) {//calculate surrounding mine counts
            for (col = 0; col < grid[0].length; col++) {
                int i, j, count = 0;
                Cell cell = grid[row][col];
                for (j = -1; j < +1; j++) {
                    for (i = -1; i < +1; i++) {
                        if (i == 0 && j == 0) continue;
                        int rr = row + j, cc = col + i;
                        if (rr < 0 || rr >= h || cc < 0 || cc >= w) continue;
                        Cell neighbor = grid[rr][cc];
                        if (neighbor.hashMine) {
                            count++;
                        }
                    }
                }
                cell.numbSurroundingmines = count;
            }
        }
        System.out.print("\n");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j].numbSurroundingmines+ " ");
            }
            System.out.print("\n");
        }
    }

    public boolean mark(int column, int row ) { //mark a Cell

        Cell cell = grid[row][column];

        if (!cell.marked) { // if cell has NOT been marked, mark it
            cell.marked = true;
            return true;
        }
        else if (cell.marked) {// if cell is already  marked, unmark it
            cell.marked = false;
            return false;
        }
        return false;
    }


    /*
        expose(int column, int row)
        0 if a cell was safely exposed and no bombs are  adjacent to it. In this case,
        all adjacent cells with 0 adjacent bombs should also be exposed. These newly
        revealed neighbors can be revealed by calls to isExposed

        1-8 if a cell was safely exposed and 1 or more bombs is adjacent to it

        -1 if a bomb was exposed at this time, game over.

     */



    public int expose (int column, int row ) {
        return 0;
    }

    public int isExposed(int column, int row) {

        return 0;
    }

    /*
            At any point in time a call to unexposedCount() should return how many cells
            can be exposed without setting off a bomb
     */

    public int unexposedCount() {
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

        System.out.print("\n");
        
        //launch(args);
    }
}
