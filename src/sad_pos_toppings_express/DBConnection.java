/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sad_pos_toppings_express;

import java.sql.*;

/**
 *
 * @author reqba
 */
public class DBConnection {
    
    static final String DB_URL ="jdbc:mysql://localhost/Toppings_Express_db";
    static final String User_name = "root";
    static final String Password = "";
    
    public static Connection connectDB(){
          Connection conn =null;
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conn = DriverManager.getConnection(DB_URL, User_name, Password);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("There were errors while connectiong to db.");
            return null;
        }
    
    
    } 
    
}
