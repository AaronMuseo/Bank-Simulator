/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankSimulator;

import java.sql.*;

/**
 *
 * @author aaron
 */
public class DBSetup {
    
    public static void initializeDatabase(){
        String url = "jdbc:postgres://localhost/";
        String user = "postgres";
        String password = "";
        
        try{
            DBConnection dbc = new DBConnection();
            
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE bank");
            System.out.println("Databse 'bank' created.");
            conn.close();
        }catch(SQLException e){
            if(e.getMessage().contains("already exists")){
                System.out.println("Database already exists.");
            }else{
                System.out.println("Database created error: " + e.getMessage());
            }
        }
        
        try{
            Connection conn = DriverManager.getConnection(url + "bank", user, password);
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS customer (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "balance INTEGER)";
            stmt.executeUpdate(sql);
            System.out.println("Table 'customer' is ready.");
            conn.close();
        }catch (SQLException e){
            System.out.println("TABLE creation error: " + e.getMessage());
        }
        
    }
    
}
