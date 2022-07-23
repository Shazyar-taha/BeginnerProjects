package com.shazyar.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public Connection getConnection(){
        Connection cn = null;
        try{
            Class.forName("org.sqlite.JDBC");

            cn = DriverManager.getConnection("jdbc:sqlite:./db/palette_color.db");
            System.out.println("Connection is success");
            return cn;

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return cn;
    }

}
