package com.shazyar.palette_color.group_color;

public class Group {

    private short id;
    private String groupName;

    public Group(short id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return groupName;
    }
}
