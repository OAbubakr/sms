/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omari
 */
public class DatabaseConn {
    private static final String URL = "jdbc:sqlite:C:\\Users\\omari\\Documents\\NetBeansProjects\\SMS\\SMS.sqlite";
    
    public static Connection conn ;
    static{
        try {
            conn =  DriverManager.getConnection(URL);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
}
