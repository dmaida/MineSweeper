package MineSweeper;


import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class Cell {
    boolean hashMine; //Cell contain a mine?
    boolean exposed;  // Has the cell been exposed (may or may not have mine)?
    boolean marked;   // Cell marked has a mine (perhaps incorrectly)?
    int numbSurroundingmines; //Number of mines in 8 adjacent cells;
}
