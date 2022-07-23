package com.shazyar.palette_color.color;

import com.shazyar.palette_color.Repository;
import com.shazyar.palette_color.group_color.Group;
import com.shazyar.service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ColorService extends DBConnection implements ColorRepository {


    @Override
    public List<Color> findAll() {
        // initialize the group list
        final List<Color> colorList = new LinkedList<>();
        // creating a query
        final String query = "SELECT * FROM color;";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            // getting data
            while (rs.next()) {
                // creating color object
                Color color = new Color(rs.getInt(1),
                        rs.getString("color"), rs.getShort("group_id"));
                // adding to the color list
                colorList.add(color);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colorList;
    }

    @Override
    public HashMap<Group, List<Color>> findByGroup(Group group) {
        // declaring a hashmap
        final HashMap<Group, List<Color>> groupColors = new HashMap<>();
        // creating a query
        final String query = "SELECT * FROM color WHERE group_id = ?";
        // getting data from database
        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            // setting parameters
            ps.setShort(1, group.getId());
            // execute query
            ResultSet rs = ps.executeQuery();
            // declare a list
            final List<Color> colors = new LinkedList<>();

            while (rs.next()){
                // creating color object
                Color color = new Color(rs.getInt(1),
                        rs.getString("color"), rs.getShort("group_id"));
                // adding to the color list
                colors.add(color);
            }
            // adding to the hashmap
            groupColors.put(group, colors);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupColors;
    }

    @Override
    public int add(Color color) {
        final String insertStatement = "INSERT INTO color(group_id, color) VALUES(?, ?)";
        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(insertStatement)) {
            // setting parameters
            ps.setShort(1, color.getGroupId());
            ps.setString(2, color.getColor());
            // execute
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int remove(int id) {
        final String deleteStatement = "DELETE FROM color WHERE id = ?";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(deleteStatement)) {
            // setting parameters
            ps.setInt(1, id);
            // execute
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
