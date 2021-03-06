package com.shazyar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getClassLoader().getResource("com/shazyar/palette_color/PaletteColor.fxml")));


        Scene scene = new Scene(root);

        stage.setTitle("Pallete Color");

        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getClassLoader().
                getResource("com/shazyar/palette_color/logo.png")).toExternalForm()));

        stage.setResizable(false);
        stage.setScene(scene);

        stage.show();
    }


    public static void main(String[] args) {
        launch(args);


    }


}
