/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author NWACHUKWU
 */
public class Save {
    public static void saveDetails(byte[] finger_byte){
        PreparedStatement insertFinger;
        Connection conn;
        Statement stmt;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            // Step 1: Allocate a database 'Connection' object
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fingerauth?useSSL=false&serverTimezone=GMT%2B1", "ossai", "ossai"); // %2B is +
            // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password")
            // jdbc:postgresql://localhost:5432/StudentDB
            
            insertFinger = conn.prepareStatement("INSERT INTO fingers (fingers_byte_fiv) VALUES (?)");
            
            insertFinger.setBytes(1, finger_byte);
            
            // PreparedStatements can use variables and are more efficient
            // Step 2: Allocate a 'Statement' object in the Connection
            stmt = conn.createStatement();
            int yn = insertFinger.executeUpdate();

            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            ex.getMessage();
        } catch (ClassNotFoundException ec) {
            ec.printStackTrace();
            ec.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }
}
