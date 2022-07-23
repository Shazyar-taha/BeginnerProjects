package com.shazyar.service;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class AlertMessage extends Label{


    public AlertMessage() {
        super();
    }

    public void successAlert(String message){
        getStyleClass().add("alert-success");
        setText(message);
        setVisible(true);
    }

    public void dangerAlert(String message){
        getStyleClass().add("alert-danger");
        setText(message);
        setVisible(true);
    }


    public void resetAlert() {
        // after 2 second clear text
        new Timeline(new KeyFrame(
                Duration.millis(2500),
                ae -> {
                    getStyleClass().clear();
                    setText("");
                    setVisible(false);
                })).play();
    }
}
