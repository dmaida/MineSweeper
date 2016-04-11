package MineSweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MineField extends Application {



    public MineField createMineField(int column, int row) {

        MineField mineField = new MineField();

        Cell[][] cellMatrix = new Cell[row][column];


        Cell cell = new Cell();

        cell.hashMine = false;
        cell.exposed = false;
        cell.marked = false;
        cell.numbSurroundingmines = 0;

        for (int i = 0; i < cellMatrix.length; i++) {
            for (int j = 0; j < cellMatrix[0].length; j++) {
                cellMatrix[i][j] = cell;

                System.out.print(cellMatrix[i][j].exposed + " ");
            }
            System.out.print("\n");
        }
        return mineField ;
    }

    public boolean mark(int column, int row ) {
        return false;
    }

    public int expose (int column, int row ) {
        return 0;
    }

    public int isExposed(int column, int row) {
        return 0;
    }

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
        mineField.createMineField(10, 5);
        launch(args);
    }
}
