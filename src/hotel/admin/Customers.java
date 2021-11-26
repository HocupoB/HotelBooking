/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.admin;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 17182
 */
public class Customers {
    
    public boolean addCustomer(String firstname, String lastname, String username, String password, String email){
        
        PreparedStatement ps;
        MyConnection myConnection = new MyConnection();
        String addQuery = "INSERT INTO `customers`(`firstname`, `lastname`, `username`, `password`, `email`) VALUES (?,?,?,?,?)";
            
        try{ 
            ps = myConnection.getConnection().prepareStatement(addQuery);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, username);
            ps.setString(4, password);
            ps.setString(5, email);
            
        
            
            
            return (ps.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterForm.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }        
        
        
    }
    
    public boolean editCustomer(int ID, String firstname, String lastname, String username, String password, String email){
        PreparedStatement ps;
        MyConnection myConnection = new MyConnection();
        String editQuery = "UPDATE `customers` SET `firstname`=?,`lastname`=?,`username`=?,`password`=?,`email`=? WHERE `ID`=?";
            
        try{ 
            ps = myConnection.getConnection().prepareStatement(editQuery);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, username);
            ps.setString(4, password);
            ps.setString(5, email);
            ps.setInt(6, ID);
            
            
            
            return (ps.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterForm.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }        
        
           
    }
    
    public boolean deleteCustomer(int ID){
        
        MyConnection myConnection = new MyConnection();
        PreparedStatement ps;
        String deleteQuery = "DELETE FROM `customers` WHERE `ID`=?";
        
        try {
            
            ps = myConnection.getConnection().prepareStatement(deleteQuery);

            ps.setInt(1, ID);
            
            return (ps.executeUpdate() > 0);
             
           } 
           catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public void fillCustomerJTable(JTable table){
        PreparedStatement ps;
        ResultSet rs;
        MyConnection myConnection = new MyConnection();
        String selectQuery = "SELECT * FROM `customers`";
        
        try {
            
            ps = myConnection.getConnection().prepareStatement(selectQuery);
            
            rs = ps.executeQuery();
            
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next())
            {
               row = new Object[6];
               row[0] = rs.getInt(1);
               row[1] = rs.getString(2);
               row[2] = rs.getString(3);
               row[3] = rs.getString(4);
               row[4] = rs.getString(5);
               row[5] = rs.getString(6);
               tableModel.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }
    public void fillOneCustomerJTable(JTable table, int ID){
        PreparedStatement ps;
        ResultSet rs;
        MyConnection myConnection = new MyConnection();
        String selectQuery = "SELECT * FROM `customers`";
        
        try {
            
            ps = myConnection.getConnection().prepareStatement(selectQuery);
            
            rs = ps.executeQuery();
            
            
            
            DefaultTableModel tableModelOne = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next())
            {
               row = new Object[6];
               row[0] = rs.getInt(1);
               row[1] = rs.getString(2);
               row[2] = rs.getString(3);
               row[3] = rs.getString(4);
               row[4] = rs.getString(5);
               row[5] = rs.getString(6);
               if(ID ==rs.getInt(1)){
                tableModelOne.addRow(row);
               }
               
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    } 
    
    public int getCustomerID(String username){
    
        PreparedStatement ps;
        ResultSet rs;
        MyConnection myConnection = new MyConnection();        
        String selectQuery = "SELECT `ID` FROM `customers` WHERE `username`=?";
        
        
        try {
            ps = myConnection.getConnection().prepareStatement(selectQuery);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }
            
                
       return 0;
    }
}
