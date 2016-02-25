/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author wainwetun
 */
public class DatabaseUtility {
    private static Connection connection;
    
    public static Connection openConnection()throws SQLException,ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:sentimentclassification.db");
        return connection;
    }
    
    public static void closeConnection()throws SQLException{
        if(connection!=null && !connection.isClosed()){
            connection.close();
        }
    }
    
}
