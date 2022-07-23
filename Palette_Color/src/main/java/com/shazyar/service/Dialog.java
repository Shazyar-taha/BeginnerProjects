package com.shazyar.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Dialog {

    private AnchorPane rootPane;
    private String url, title;
    private FXMLLoader load;

    private Parent parent;

    public Dialog(AnchorPane rootPane, String url, String title) {
        this.rootPane = rootPane;
        this.url = url;
        this.title = title;
        // load the fxml
        loadFXML();
    }

    // load the fxml
    private void loadFXML() {
        try {
            //load fxml
            load = new FXMLLoader(getClass().getClassLoader().getResource(url));
            parent = (Parent) load.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // get FXML
    public FXMLLoader getLoad() {
        return load;
    }


    // open the dialog
    public void open() {
        //create a new Stage
        Stage stage = new Stage();
        stage.setTitle(title);

        stage.initOwner(rootPane.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getClassLoader().
                getResource("com/shazyar/palette_color/logo.png")).toExternalForm()));

        Scene scene = new Scene(parent);
        stage.setResizable(false);
        stage.setScene(scene);

        stage.setResizable(false);

        stage.showAndWait();

    }


}
