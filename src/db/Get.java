/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author NWACHUKWU
 */
public class Get {

    public static byte[] getDetails(String firstname, String lastame, int num, String matric, int matric_num) {
        PreparedStatement selectFinger;
        Connection conn;
        Statement stmt;

        byte[] it = null;// "".getBytes(); // initialise to empty
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            // Step 1: Allocate a database 'Connection' object
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fingerauth?useSSL=false&serverTimezone=GMT%2B1", "ossai", "ossai"); // %2B is +
            // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password")
            // jdbc:postgresql://localhost:5432/StudentDB
            // jdbc:mysql://localhost:3306/doctor?useSSL=false

            selectFinger = conn.prepareStatement("SELECT * FROM fingers WHERE num = ?");

            selectFinger.setInt(1, num); // --

            // PreparedStatements can use variables and are more efficient
            // Step 2: Allocate a 'Statement' object in the Connection
            stmt = conn.createStatement();
            ResultSet yn = selectFinger.executeQuery();

            // to get total number of rows
            // https://stackoverflow.com/a/7545888
            yn.last();
            int totalRows = yn.getRow();
            System.out.println("total: " + totalRows);
            yn.beforeFirst();
            //---

            if (yn.first() && totalRows == 1) {
                // boolean answer = true;

                // should a try-catch block be in another try-catch block?!?!?!
                try {
                    // Retrieves all or part of the BLOB value that this Blob object represents, as an array of bytes
                    // https://docs.oracle.com/javase/7/docs/api/java/sql/Blob.html#getBytes(long,%20int)

                    it = yn.getBlob("fingers_byte_fmd_rt").getBytes(1, (int) yn.getBlob("fingers_byte_fmd_rt").length());
                }catch (SQLException es) {
                    System.out.println(es.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                System.out.println("Successful select finger for: " + num);
            } else {
                System.out.println("UNSuccessful select finger for: " + num);

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

        return it;
    }
}
