/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.digitalpersona.uareu.Engine;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.UareUGlobal;
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
public class GetAll {

    public static Fmd[] /*byte[][]*/ getDetails() {
        PreparedStatement selectFinger;
        Connection conn;
        Statement stmt;
        // byte[][] all = new byte[1][];
        Fmd[] all = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            // Step 1: Allocate a database 'Connection' object
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fingerauth?useSSL=false&serverTimezone=GMT%2B1", "ossai", "ossai"); // %2B is +
            // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password")
            // jdbc:postgresql://localhost:5432/StudentDB
            // jdbc:mysql://localhost:3306/doctor?useSSL=false

            selectFinger = conn.prepareStatement("SELECT * FROM fingers");

            // selectFinger.setInt(1, num); // --
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
            
            // create an array that is just enough for all the results we have
            // byte[][] all = new byte[1][totalRows];
            //---
            all = new Fmd[totalRows];

            if (totalRows > 0) {

                // should a try-catch block be in another try-catch block?!?!?!
                try {
                    // to extract features and do comparison
                        Engine engine = UareUGlobal.GetEngine();
                    // Retrieves all or part of the BLOB value that this Blob object represents, as an array of bytes
                    // https://docs.oracle.com/javase/7/docs/api/java/sql/Blob.html#getBytes(long,%20int)

                    while (yn.next()) {
                        // https://stackoverflow.com/questions/25652483/how-to-create-an-array-of-byte-arrays-in-java
                        // all[yn.getRow()] = yn.getBlob("fingers_byte_fmd_rt").getBytes(1, (int) yn.getBlob("fingers_byte_fmd_rt").length());
                        
                        // we know most o' these values
                         all[yn.getRow()] = engine.CreateFmd(yn.getBlob("fingers_byte_fmd_rt").getBytes(1, (int) yn.getBlob("fingers_byte_fmd_rt").length()), 252, 324, 500, 1, 3407615, Fmd.Format.ANSI_378_2004);
                    }
                } catch (SQLException es) {
                    System.out.println(es.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                System.out.println("Successful select finger");
            } else {
                System.out.println("UNSuccessful select finger");

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

        return all;
    }
}
