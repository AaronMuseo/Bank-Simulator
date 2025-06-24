package BankSimulator;

import java.sql.*;

/**
 *
 * @author aaron
 */
public class DBConnection {
    
    Connection con;
    
    public DBConnection(){
        
        try{
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver Loaded Succesfully");
        }catch(ClassNotFoundException cnfe){
            System.out.println("Driver loading failed" + cnfe.getMessage());            
        }
        
        String url = "jdbc:postgresql://localhost:5432/bank";
        String user = "postgres";
        String password = "";
        
        try{
            con = DriverManager.getConnection(url,user,password);
            System.out.println("Connection Succesfull");
        }catch(SQLException sqle){
            System.out.println("Connection Failed: " + sqle.getMessage());
        }
    }
}
