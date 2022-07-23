package com.shazyar.palette_color.color;

import com.shazyar.palette_color.group_color.Group;
import com.shazyar.palette_color.group_color.GroupService;
import com.shazyar.service.AlertMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class AddColorController implements Initializable {

    @FXML
    private ComboBox<Group> groupCombo;

    @FXML
    private TextField colorTf;

    @FXML
    private AlertMessage alertMessage;


    private final ColorService service = new ColorService();

    private final GroupService groupService = new GroupService();

    private Group selectedGroup;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupComboBox();
    }

    private void setupComboBox() {
        // adding all groups to the combo
        groupCombo.getItems().addAll(groupService.findAll());
        // set listener
        groupCombo.getSelectionModel().selectedItemProperty()
                .addListener((ob, oldValue, newValue) -> {
                    selectedGroup = newValue;
                });
    }


    @FXML
    private void addColor(ActionEvent event) {
        if (colorTf.getText().isEmpty() ||
                selectedGroup == null ||
                !colorTf.getText().matches("#[a-fA-F0-9]{3,6}")) {

            alertMessage.dangerAlert("Please Fill The Fields With Valid Value");
            alertMessage.resetAlert();

            return;
        }
        // creating a color object
        Color color = new Color(0, colorTf.getText(), selectedGroup.getId());
        // adding to the database
        int affectRow = service.add(color);

        if (affectRow > 0) {

            alertMessage.successAlert("Successfully Inserted");
            alertMessage.resetAlert();

            // clearing color text field and comboBox
            Platform.runLater(() -> {
                colorTf.clear();
                groupCombo.getSelectionModel().select(selectedGroup);
                // set focus to color text field
                colorTf.requestFocus();
            });
            return;
        }

        // show failed alert
        alertMessage.dangerAlert("Failed Insert");
        alertMessage.resetAlert();

    }

}
