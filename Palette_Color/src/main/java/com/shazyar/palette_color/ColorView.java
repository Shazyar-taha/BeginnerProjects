package com.shazyar.palette_color;

import com.shazyar.palette_color.color.Color;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ColorView extends VBox {

    private Rectangle rectangle;
    private Text text;
    private final Color color;


    public ColorView(Color hexCode){
        super(10);
        this.color = hexCode;
        // initialize the VBox
        setMaxHeight(200);
        setWidth(200);
        setFillWidth(true);
        setAlignment(Pos.CENTER);
        setCursor(Cursor.HAND);
        // initialize the rectangle
        setupRectangle(hexCode.getColor());
        // initialize the text
        setupText(hexCode.getColor());
        // adding node to the VBox
        getChildren().addAll(rectangle, text);
    }


    private void setupRectangle(String hexCode){
        rectangle = new Rectangle();
        rectangle.setStrokeWidth(0);
        rectangle.setArcHeight(80);
        rectangle.setArcWidth(100);
        rectangle.setWidth(90);
        rectangle.setHeight(180);
        rectangle.setSmooth(true);
        rectangle.setFill(Paint.valueOf(hexCode));
    }

    private void setupText(String hexCode){
        text = new Text(hexCode);
        text.setStyle("-fx-font:  14px \"Verdana\";" +
                "-fx-fill: "+hexCode+";");
    }

    public Color getColor() {
        return color;
    }

}
