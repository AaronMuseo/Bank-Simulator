package BankSimulator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aaron
 */
public class DBOperations {
    
    public String Create(String name, int id, int balance){
        
        String output = "";
        
        try{
            String query = "INSERT INTO customer (name, id, balance) VALUES (?,?,?)";
            
            DBConnection dbc = new DBConnection();
            PreparedStatement pst = dbc.con.prepareStatement(query);
            pst.setString(1, name);
            pst.setInt(2, id);
            pst.setInt(3, balance);
            
            
            boolean result = pst.execute();
            
            if(!result){
                output = "Customer "+ id +" Created";
                System.out.println("Customer "+ id +" Created");
            }else{
                output = "Insert Failed";
                System.out.println("Insert Failed");
            }
            
        }catch(SQLException sqle){
            output = "Insert operation failed:\n "+sqle.getMessage();
            System.out.println("Insert operation failed: "+sqle.getMessage());
        }
        return output;
    }
    
    public String[] CheckFunds(int id){
        
        String[] output = new String[4];
        
        try{
            String query = "SELECT id, name, balance FROM customer WHERE id = ?";
            
            DBConnection dbc = new DBConnection();
            PreparedStatement pst = dbc.con.prepareStatement(query);
            pst.setInt(1, id);
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                String cid = rs.getString("id");
                String cname = rs.getString("name");
                String cbalance = rs.getString("balance");
               
                
                output[0] = cid;
                output[1] = cname;
                output[2] = cbalance;
                
                
                System.out.println("Customer ID: " + cid + "\nName: " +cname+"\nBalance: "+cbalance);
            }            
        }catch(SQLException sqle){
            System.out.println(sqle.getMessage());
        }
        
        return output;
        
    }
    
    public String[] GetBalance(int id){
        
        String[] output = new String[4];
        
        try{
            String query = "SELECT balance FROM customer WHERE id = ?";
            
            DBConnection dbc = new DBConnection();
            PreparedStatement pst = dbc.con.prepareStatement(query);
            pst.setInt(1, id);
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                String cbalance = rs.getString("balance");
                
                output[0] = cbalance;
                                
                System.out.println("Customer ID: " + id + "\nBalance: "+cbalance);
            }            
        }catch(SQLException sqle){
            System.out.println(sqle.getMessage());
        }
        
        return output;
        
    }
    
    public String[] Withdraw(int id, int amount){
    String[] output = new String[4];

    try {
        DBConnection dbc = new DBConnection();

        // Step 1: SELECT balance
        String selectQuery = "SELECT balance FROM customer WHERE id = ?";
        PreparedStatement selectPst = dbc.con.prepareStatement(selectQuery);
        selectPst.setInt(1, id);
        ResultSet rs = selectPst.executeQuery();

        if(rs.next()) {
            double currentBalance = rs.getDouble("balance");

            if(currentBalance >= amount) {
                // Step 2: UPDATE balance
                String updateQuery = "UPDATE customer SET balance = balance - ? WHERE id = ?";
                PreparedStatement updatePst = dbc.con.prepareStatement(updateQuery);
                updatePst.setDouble(1, amount);
                updatePst.setInt(2, id);

                int rows = updatePst.executeUpdate();

                if(rows > 0) {
                    double newBalance = currentBalance - amount;
                    output[0] = "Success";
                    output[1] = String.valueOf(newBalance);
                    System.out.println("Withdrawn: " + amount + "\nNew Balance: " + newBalance);
                } else {
                    output[0] = "Failed";
                }
            } else {
                output[0] = "Insufficient Funds";
            }
        } else {
            output[0] = "Customer Not Found";
        }

    } catch(SQLException sqle) {
        output[0] = "Error";
        System.out.println("SQL Error: " + sqle.getMessage());
    }

    return output;
}

    public String[] Deposit(int id, int amount){
    String[] output = new String[4];

    try {
        DBConnection dbc = new DBConnection();

        // Step 1: SELECT balance
        String selectQuery = "SELECT balance FROM customer WHERE id = ?";
        PreparedStatement selectPst = dbc.con.prepareStatement(selectQuery);
        selectPst.setInt(1, id);
        ResultSet rs = selectPst.executeQuery();

        if(rs.next()) {
            double currentBalance = rs.getDouble("balance");

            
                // Step 2: UPDATE balance
                String updateQuery = "UPDATE customer SET balance = balance + ? WHERE id = ?";
                PreparedStatement updatePst = dbc.con.prepareStatement(updateQuery);
                updatePst.setDouble(1, amount);
                updatePst.setInt(2, id);

                int rows = updatePst.executeUpdate();

                if(rows > 0) {
                    double newBalance = currentBalance + amount;
                    output[0] = "Success";
                    output[1] = String.valueOf(newBalance);
                    System.out.println("Withdrawn: " + amount + "\nNew Balance: " + newBalance);
                } else {
                    output[0] = "Failed";
                }
            
        } else {
            output[0] = "Customer Not Found";
        }

    } catch(SQLException sqle) {
        output[0] = "Error";
        System.out.println("SQL Error: " + sqle.getMessage());
    }

    return output;
}
       
    
    
    
}
