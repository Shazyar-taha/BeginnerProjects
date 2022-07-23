package com.shazyar.palette_color.color;

import com.shazyar.palette_color.group_color.Group;

import java.util.HashMap;
import java.util.List;

public interface ColorRepository {

    List<Color> findAll();

    HashMap<Group, List<Color>> findByGroup(Group group);

    int add(Color color);

    int remove(int id);


}
