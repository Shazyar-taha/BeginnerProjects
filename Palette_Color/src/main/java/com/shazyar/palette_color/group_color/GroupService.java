package com.shazyar.palette_color.group_color;

import com.shazyar.palette_color.Repository;
import com.shazyar.service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GroupService extends DBConnection implements Repository<Group> {

    @Override
    public Group findOne(int id) {
        final String query = "SELECT * FROM group_color WHERE id = ?";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            // set parameter
            ps.setInt(1, id);
            // execute query
            ResultSet rs = ps.executeQuery();
            // getting data
            if (rs.next()) {
                Group group = new Group(rs.getShort(1), rs.getString(2));
                rs.close();
                return group;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Group> findAll() {
        // initialize the group list
        final List<Group> groupList = new LinkedList<>();
        // creating a query
        final String query = "SELECT * FROM group_color ORDER BY group_name ASC";

        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // creating group object
                Group group = new Group(rs.getShort(1),
                        rs.getString(2));
                // adding to the group list
                groupList.add(group);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupList;
    }

    @Override
    public int add(Group group) {
        final String insertStatement = "INSERT INTO group_color(group_name) VALUES(?)";
        try (Connection cn = getConnection();
             PreparedStatement ps = cn.prepareStatement(insertStatement)) {
            // setting parameters
            ps.setString(1, group.getGroupName());
            // execute
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int remove(int id) {
        Connection cn = null;
        int isDeleted = 0;
        // the first thing we must delete all color from color table
        // because the color table has a relationship with group_color table
        // creating a delete statement
        final String deleteColorStatement = "DELETE FROM color WHERE group_id = ?";
        // after that we should delete the group
        final String deleteGroupStatement = "DELETE FROM group_color WHERE id = ?";

        try{
            cn = getConnection();
            cn.setAutoCommit(false); // not auto commit
            // delete color
            PreparedStatement ps = cn.prepareStatement(deleteColorStatement);
            // setting parameters and execute
            ps.setInt(1, id);
            ps.executeUpdate();
            // clearing parameters
            ps.clearParameters();
            // delete group
            ps = cn.prepareStatement(deleteGroupStatement);
            // setting parameters and execute
            ps.setInt(1, id);
            isDeleted = ps.executeUpdate();
            // commit the transaction
            cn.commit();
            // close all
            ps.close();
            cn.close();
        }catch (SQLException e){
            e.printStackTrace();
            // roll back
            try {
                cn.rollback();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return isDeleted;
    }
}
