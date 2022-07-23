package com.shazyar.palette_color.color;

import com.shazyar.palette_color.group_color.Group;

import java.time.LocalDate;

public class Color {

    private int id;
    private short groupId;
    private String color;

    public Color(int id, String color,  short groupId) {
        this.id = id;
        this.groupId = groupId;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public short getGroupId() {
        return groupId;
    }

    public void setGroupId(short groupId) {
        this.groupId = groupId;
    }
}
