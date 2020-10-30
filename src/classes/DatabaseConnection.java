package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    public static Connection getConnection(){
       Connection con=null;        
        try{
        Class.forName("org.sqlite.JDBC");
        con=DriverManager.getConnection("jdbc:sqlite:data.db");
        }
        catch(ClassNotFoundException | SQLException ex){
        ex.printStackTrace();
        }
        
        return con;
    }
    
}
