package com.shazyar.palette_color.group_color;

import com.shazyar.service.AlertMessage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class AddGroupController implements Initializable {

    @FXML
    private TextField groupNameTf;

    @FXML
    private AlertMessage alertMessage;

    private final GroupService service = new GroupService();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    private void addGroupName(ActionEvent event) {
        if (groupNameTf.getText().isEmpty()
                || !groupNameTf.getText().matches("\\w+")) {

            alertMessage.dangerAlert("Please Fill The Group Name With Valid Value");
            alertMessage.resetAlert();

            return;
        }

        // the first letter to capital
        String groupName = groupNameTf.getText().substring(0, 1).toUpperCase()
                + groupNameTf.getText().substring(1);
        // adding to the database
        int isInserted = service.add(new Group((short) 0, groupName));

        if (isInserted > 0) {
            // show success alert
            alertMessage.successAlert("Successfully Inserted");
            alertMessage.resetAlert();

            // clearing group text field and set focus
            Platform.runLater(() -> {
                groupNameTf.clear();
                groupNameTf.requestFocus();
            });
        }

    }


}
