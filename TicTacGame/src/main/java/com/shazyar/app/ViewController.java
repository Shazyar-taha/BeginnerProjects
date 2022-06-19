package com.shazyar.app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ViewController implements Initializable {

    @FXML
    private GridPane gridView;
    // declare the array
    private byte[][] board;
    // this variable to know who is a player start the game
    private boolean isX = true;
    // increment the i
    private byte i = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.board = new byte[3][3];
        initializeGridView();
    }


    // initialize the grid pane because when you clicked the cells that do not return null
    private void initializeGridView() {
        int numCols = 3;
        int numRows = 3;

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                Pane pane = new Pane();
                pane.setMaxWidth(110);
                pane.setMaxHeight(100);
                pane.getStyleClass().add("box");
                gridView.add(pane, j, i);
            }
        }
    }

    // the first time player x start the game
    @FXML
    private void onClicked(MouseEvent event) {
        // get the node
        Node source = event.getPickResult().getIntersectedNode();
        // getting row and column
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        System.out.println("Column "+colIndex );
        System.out.println("Row "+rowIndex );


        if (isX) {
            // creating a text and set style
            Text text = new Text("X");
            text.getStyleClass().add("text");
            text.getStyleClass().add("red");
            // adding to the grid container
            gridView.add(text, colIndex, rowIndex);
            //
            board[rowIndex][colIndex] = 1;
            isX = false;
        } else {
            Text text = new Text("O");
            text.getStyleClass().add("text");
            text.getStyleClass().add("blue");
            gridView.add(text, colIndex, rowIndex);
            board[rowIndex][colIndex] = 5;
            isX = true;
        }

        i++;
        if (i > 3) {
            // checking who is winner
            String s = checkWinner();

            if (s.equals("X")) {
                // show the message
                showMessage("The X Player Is Win");
                // clear the Grid Pane
                clearNodes();
                // reinitialize the board array
                board = new byte[3][3];
                i = 0;
            } else if (s.equals("O")) {
                // show the message
                showMessage("The O Player Is Win");
                // clear the Grid Pane
                clearNodes();
                // reinitialize the board array
                board = new byte[3][3];
                i = 0;
            }
        }
        if (i == 8) {
            // show the message
            showMessage("No one can not win");
            // clear the Grid Pane
            clearNodes();
            // reinitialize the board array
            board = new byte[3][3];
            i = 0;
        }

    }

    // checking the winner
    private String checkWinner() {
        // declare diagonal variable
        byte diagonal;
        // check diagonal
        diagonal = (byte) (board[0][0] + board[1][1] + board[2][2]);

        if (diagonal == 3) {
            return "X";
        } else if (diagonal == 15) {
            return "O";
        }

        // check opposite diagonal
        diagonal = (byte) (board[0][2] + board[1][1] + board[2][0]);

        if (diagonal == 3) {
            return "X";
        } else if (diagonal == 15) {
            return "O";
        }

        // initializing variables
        byte row = 0, column = 0;
        // checking rows and columns
        for (int i = 0; i < board[0].length; i++) {
            row = 0;
            column = 0;
            for (int j = 0; j < board[1].length; j++) {
                // summation of a row
                row += board[i][j];
                System.out.println("Row : " + row);
                // summation of a column
                column += board[j][i];
                System.out.println("Column " + column);
            }

            if ((row == 3 || row == 15) || (column == 3 || column == 15))
                break;
        }

        if (row == 3 || column == 3) {
            return "X";
        } else if (row == 15 || column == 15) {
            return "O";
        } else {
            // the N letter determines the no one did not success
            return "N";
        }
    }
    // clearing all texts from the grid pane
    private void clearNodes() {
        List<Node> nodes = gridView.getChildren().stream().filter(n -> n instanceof Text)
                .collect(Collectors.toList());
        gridView.getChildren().removeAll(nodes);
    }
    // showing the message
    private void showMessage(String text) {
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ok);
        alert.setTitle("Message");
        alert.setHeaderText(text);
        alert.show();
    }


}
