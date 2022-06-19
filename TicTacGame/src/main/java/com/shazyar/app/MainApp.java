package com.shazyar.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getClassLoader()
                .getResource("com/shazyar/View.fxml")));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Tic Tac Toe");
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }


}
