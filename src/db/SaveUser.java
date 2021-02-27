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
public class SaveUser {
    public static boolean saveDetails(byte[] finger_byte_lt, byte[] finger_byte_rt, String ln, String mn, String fn){
        PreparedStatement insertFinger;
        Connection conn;
        Statement stmt;
        
        boolean saved = false;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            // Step 1: Allocate a database 'Connection' object
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fingerauth?useSSL=false&serverTimezone=GMT%2B1", "ossai", "ossai"); // %2B is +
            // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password")
            // jdbc:postgresql://localhost:5432/StudentDB
            
            insertFinger = conn.prepareStatement("INSERT INTO fingers (fingers_byte_fmd_lt, fingers_byte_fmd_rt, lastname, middlename, firstname) VALUES (?,?,?,?,?)");
            
            insertFinger.setBytes(1, finger_byte_lt);
            insertFinger.setBytes(2, finger_byte_rt);
            
            insertFinger.setString(3, ln);
            insertFinger.setString(4, mn);
            insertFinger.setString(5, fn);
            
            // PreparedStatements can use variables and are more efficient
            // Step 2: Allocate a 'Statement' object in the Connection
            stmt = conn.createStatement();
            int yn = insertFinger.executeUpdate(); // 1 if successful else 0
            if (yn == 1) {
                saved = true;
            }

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
        
        return saved;
    }
}
