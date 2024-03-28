package sad_pos_toppings_express;



import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author reqba
 */

public class dbSearch {
    
    static Connection c;
    
    public static Connection getCon() throws Exception{
    
        if (c == null){
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost/Toppings_Express_db","root","");
                    
        }
    return c;
    }
    public static void insertData(String sql) throws Exception{
    getCon().createStatement().executeUpdate(sql);
    }
    public static ResultSet searchData(String sql) throws Exception{
        
        ResultSet rs = dbSearch.getCon().createStatement().executeQuery(sql);
        return rs;
    
    }
}  

