package MineSweeper;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Timer;
import java.util.concurrent.SynchronousQueue;

import static java.lang.Math.min;
import static java.lang.Math.random;

public class MineField extends Application {


    private static  int numbMines = 10; // total number of mines
    private static int numExposedCells = 0;  // total cell exposed
    public static int height;
    public static int width;

    public Cell[][] grid;

    public void createMineField(int level) {

        int w = 0;
        int h = 0;

        if (level == 1){
            h = 9;
            w = 9;
            numbMines = 10;
        }
        if (level == 2){
            h = 16;
            w = 16;
            numbMines = 40;
        }
        if (level == 3){
            h = 16;
            w = 30;
            numbMines = 99;
        }

        height= h;
        width = w;

        grid = new Cell[h][w];

        int n = w*h; //total cells left
        int m = numbMines; //Number of mines to set

        int row, col;

        numExposedCells = 0;

        for (row = 0; row < h; row++) { // place mines
            for ( col = 0; col < w; col++) {
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
            }
        }

        numbMines = m;

        for (row = 0; row < h; row++) {//calculate surrounding mine counts
            for (col = 0; col < w; col++) {
                int i, j, count = 0;
                Cell cell = grid[row][col];
                for (j = -1; j <= +1; j++) {
                    for (i = -1; i <= +1; i++) {
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

    public int expose (int column, int row ) {
        Cell cell = grid[row][column];

        if (cell.hashMine) { //game over, exposed mine
            return -1;
        }
        if (cell.exposed) return -2;

        cell.exposed = true;
        numExposedCells++;

        int n = cell.numbSurroundingmines;

        if (n == 0) {
            int w = width, h = height;
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

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {


                System.out.print(grid[i][j].numbSurroundingmines+ " ");
            }
            System.out.print("\n");
        }
        return n;
    }

    public boolean isExposed(int column, int row) {
        Cell cell = grid[row][column];
        if(!cell.exposed && !cell.hashMine) {
            int w = width, h = height;
            int i, j;
            for (j = -1; j <= +1; j++) {
                for (i = -1; i <= +1; i++) {
                    if (i == 0 && j ==0 ) continue;
                    int rr = row+j, cc = column+i;
                    if (rr < 0 || rr >= h ||  cc <0 || cc >= w) continue;
                    Cell neighbor = grid[rr][cc];
                    if (neighbor.exposed && neighbor.numbSurroundingmines == 0) {
                        cell.exposed = true;
                        numExposedCells++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int unexposedCount() {
        return 0;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MineSweeper.fxml"));
        primaryStage.setTitle("MineSweeper");
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        //MineField mineField = new MineField();
        //mineField.createMineField(3);

       launch(args);
    }
}
