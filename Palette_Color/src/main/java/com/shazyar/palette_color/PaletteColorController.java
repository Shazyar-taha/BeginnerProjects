package com.shazyar.palette_color;

import com.shazyar.palette_color.color.Color;
import com.shazyar.palette_color.color.ColorService;
import com.shazyar.palette_color.group_color.Group;
import com.shazyar.palette_color.group_color.GroupService;
import com.shazyar.service.Dialog;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaletteColorController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private FlowPane colorContainer;

    @FXML
    private ListView<Group> groupColorList;

    @FXML
    private Pane squareView;

    @FXML
    private TextField hexTf;

    @FXML
    private TextField hslTf;

    @FXML
    private TextField rgbTf;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Label copyTxt;


    private final GroupService groupService = new GroupService();

    private final ColorService colorService = new ColorService();

    private Group selectedGroup;

    private Color selectedColor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupGroupList();

        squareClick();

        setupList();

        actionColorPicker();

    }

    private void actionColorPicker() {
        colorPicker.setOnAction(e -> {
            // change the color of square view
            String color = "#" + colorPicker.getValue().toString().substring(2, 8);
            // setting hex code to the hex field
            hexTf.setText(color);
            // hexa to rgb and set to the field
            short[] rgb = hexadecimalToRGB(color);
            String rgbTxt = "(" + rgb[0] + ", " + rgb[1] + ", " + rgb[2] + ")";
            // set rgb to the text fiedl
            rgbTf.setText(rgbTxt);
            float[] hsv = new float[3];
            java.awt.Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsv);
            String hsl = "(" + Math.round(hsv[0] * 100) + ", " + Math.round((hsv[1] * 100)) + "%, " + Math.round(hsv[2] * 100) + "%)";
            hslTf.setText(hsl);
            squareView.setStyle("-fx-background-color: " + color + ";");

        });
    }

    private void setupList() {
        groupColorList.setCellFactory(new Callback<ListView<Group>, ListCell<Group>>() {
            @Override
            public ListCell<Group> call(ListView<Group> param) {
                return new ListCell<Group>() {
                    @Override
                    protected void updateItem(Group item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setGraphic(null);
                            setText("");
                            return;
                        }


                        Button btn = new Button();
                        MaterialDesignIconView deleteIcon = new
                                MaterialDesignIconView(MaterialDesignIcon.DELETE);
                        Label txt = new Label("");
                        GridPane box = new GridPane();

                        // creating columns and rows to the grid pane
                        box.getColumnConstraints().add(new ColumnConstraints(140));
                        box.getColumnConstraints().add(new ColumnConstraints(50));
                        box.getRowConstraints().add(new RowConstraints(30));
                        box.getRowConstraints().add(new RowConstraints(10));

                        // designing the text
                        txt.setPrefWidth(130);
                        txt.getStyleClass().add("list-text");
                        txt.setWrapText(true);
                        // set style icon
                        deleteIcon.setSize("20");
                        deleteIcon.setFill(Paint.valueOf("#ee1313"));
                        // design button
                        btn.setPrefWidth(120);
                        btn.setPrefHeight(20);
                        btn.setGraphic(deleteIcon);
                        btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        btn.getStyleClass().add("list-btn");

                        txt.setText(item.getGroupName());
                        box.add(txt, 0, 0);
                        box.add(btn, 1, 0);

                        setGraphic(box);
                        setText("");

                        // set action to the button
                        btn.setOnAction((e) -> {
                            boolean isDeletedMessage = showDeleteMessage("You Want To Delete This Group ?");

                            if (isDeletedMessage) {
                                Platform.runLater(() -> {

                                    int isDeleted = groupService.remove(item.getId());

                                    if (isDeleted == 1) {
                                        groupColorList.getItems().remove(item);
                                        //                                        groupColorList.getItems().setAll(groupService.findAll());
                                    }

                                });
                            }
                        });


                    }
                };
            }
        });
    }


    private void setupGroupList() {
        // adding all items
        groupColorList.getItems().addAll(groupService.findAll());

        // when the user click the group
        groupColorList.getSelectionModel().selectedItemProperty()
                .addListener((ob, oldValue, newValue) -> {
                    if (newValue != null) {
                        // get value
                        selectedGroup = newValue;

                        // the first thing we clear the container
                        colorContainer.getChildren().clear();
                        // getting the all colors by group from color service
                        HashMap<Group, List<Color>> groupColors = colorService.findByGroup(selectedGroup);
                        // for each color we create the color view and
                        createColorView(groupColors);
                    }
                });


        // selecting the first item
        Platform.runLater(() -> groupColorList.getSelectionModel().selectFirst());
    }


    private short[] hexadecimalToRGB(String hexadecimalCode) {
        // this string to the handle letter
        // By index of the string we can get the decimal number
        // example if I say index of F this returns the 15
        String hexadecimal = "0123456789ABCDEF";
        // remove the # from start the string
        String hexa = hexadecimalCode.replace("#", "").toUpperCase();
        // checking the hexadecimal if the length of hexa is three character
        // we concat the same hexa code to be the right hexadecimal
        if (hexa.length() == 3) {
            hexa += hexa;
        }

        System.out.println("Your hexadecimal is " + hexa);

        short[] rgb = new short[3];

        for (int i = 0; i < rgb.length; i++) {
            // declare the sum to stores the summation of two decimal
            int sum = 0;
            int h = 2;
            for (int j = (i * 2); j < (i * 2) + 2; j++) {
                // decrement the h
                // this variable determines the power of base
                // left to right for example 5BC
                // the length is 3 we decrement the h will be 2 so
                // the 5 * 16 power 2
                h--;
                // getting index of the hexadecimal
                int number = hexadecimal.indexOf(hexa.substring(j, j + 1));
                // result
                int result = (int) (number * Math.pow(16, h));
                sum += result;
            }
            rgb[i] = (short) sum;
        }

        return rgb;
    }


    private void squareClick() {
        squareView.setOnMouseClicked(e -> {
            // copy the color
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(hexTf.getText());
            Clipboard clipboard = Clipboard.getSystemClipboard();
            clipboard.setContent(clipboardContent);

            Platform.runLater(() -> {
                copyTxt.setVisible(true);


                new Timeline(new KeyFrame(Duration.millis(2000), event -> {
                    copyTxt.setVisible(false);
                })).play();
            });

        });
    }


    @FXML
    private void copyColor(ActionEvent event) {
        if (selectedColor == null) {
            return;
        }

        // copy the color
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(selectedColor.getColor());
        Clipboard clipboard = Clipboard.getSystemClipboard();
        clipboard.setContent(clipboardContent);
    }

    @FXML
    private void deleteColor(ActionEvent event) {
        if (selectedColor == null) {
            return;
        }
        // show alert
        boolean isDeletable = showDeleteMessage("You Want To Delete This Color ?");

        if (isDeletable) {
            colorService.remove(selectedColor.getId());
            // refresh the color view
            if (selectedColor != null) {
                // the first thing we clear the container
                colorContainer.getChildren().clear();
                // getting the all colors by group from color service
                HashMap<Group, List<Color>> groupColors = colorService.findByGroup(selectedGroup);
                // for each color we create the color view and
                createColorView(groupColors);
            }
        }

    }

    @FXML
    private void insertColor(ActionEvent event) {
        Dialog dialog = new Dialog(root, "com/shazyar/palette_color/AddColor.fxml", "Add Color");
        // open dialog
        dialog.open();
        // refresh the color view
        if (selectedColor != null) {
            // the first thing we clear the container
            colorContainer.getChildren().clear();
            // getting the all colors by group from color service
            HashMap<Group, List<Color>> groupColors = colorService.findByGroup(selectedGroup);
            // for each color we create the color view and
            createColorView(groupColors);
        }
    }

    @FXML
    private void insertGroup(ActionEvent event) {
        Dialog dialog = new Dialog(root, "com/shazyar/palette_color/AddGroup.fxml", "Add Group");
        // open dialog
        dialog.open();
        groupColorList.getItems().clear();
        // after that refresh the groups
        groupColorList.getItems().addAll(groupService.findAll());
        // select the first group
        Platform.runLater(() -> groupColorList.getSelectionModel().selectFirst());

    }


    private void createColorView(HashMap<Group, List<Color>> groupColors) {
        // for each color we create the color view and
        // adding to the color container
        groupColors.get(selectedGroup).
                forEach(c -> {
                    // creating color view object and pass the hex color
                    ColorView colorView = new ColorView(c);
                    // setting mouse listener to the color view object
                    colorView.setOnMouseClicked(e -> {
                        // getting object of color view
                        ColorView obj = (ColorView) e.getSource();
                        // getting a color object
                        selectedColor = obj.getColor();
                        // setting hex code to the hex field
                        hexTf.setText(selectedColor.getColor());
                        // hexa to rgb and set to the field
                        short[] rgb = hexadecimalToRGB(selectedColor.getColor());

                        String rgbTxt = "(" + rgb[0] + ", " + rgb[1] + ", " + rgb[2] + ")";
                        rgbTf.setText(rgbTxt);
                        float[] hsv = new float[3];
                        java.awt.Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsv);
                        String hsl = "(" + Math.round(hsv[0] * 100) + ", " + Math.round((hsv[1] * 100)) + "%, " + Math.round(hsv[2] * 100) + "%)";
                        hslTf.setText(hsl);
                        // change the color of square view
                        squareView.setStyle("-fx-background-color: " + selectedColor.getColor() + ";");

                    });
                    // adding to the container
                    colorContainer.getChildren().add(colorView);
                });
    }


    private boolean showDeleteMessage(String msg) {
        // create  button types
        ButtonType yes = ButtonType.YES;
        ButtonType no = ButtonType.NO;
        Alert alert = new Alert(Alert.AlertType.NONE,
                msg);
        alert.getButtonTypes().addAll(yes, no);
        alert.setTitle("Delete Message");
        alert.setHeaderText("Be careful");

        Optional<ButtonType> res = alert.showAndWait();

        return res.isPresent() && res.get() == yes;
    }


}
