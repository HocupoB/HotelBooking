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



import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 17182
 */
public class Rooms {


    MyConnection myConnection = new MyConnection();
    
    // create a function to display all rooms type in jtable

    
    public void fillRoomsTypeJComboBox(JComboBox combobox)
    {
        PreparedStatement ps;
        ResultSet rs;
        String selectQuery = "SELECT * FROM `roomtypes`";
        
        try {
            
            ps = myConnection.getConnection().prepareStatement(selectQuery);
            
            rs = ps.executeQuery();

            
            while(rs.next())
            {

               
               combobox.addItem(rs.getString(1));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }     
}  
    
    public boolean addRoom(int roomNumber, String type, String price)
    {
        PreparedStatement st;
        String addQuery = "INSERT INTO `rooms`(`roomNumber`, `type`, `price`) VALUES (?,?,?)";
        
        try {
            
            st = myConnection.getConnection().prepareStatement(addQuery);
            
            st.setInt(1, roomNumber);
            st.setString(2, type);
            st.setString(3, price);


            
            return (st.executeUpdate() > 0);
           
            
        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    public boolean editRoom(int roomNumber, String type, String price){
        PreparedStatement ps;
        
        String editQuery = "UPDATE `rooms` SET `type`=?,`price`=? WHERE `roomNumber`=? ";
            
        try{ 
            ps = myConnection.getConnection().prepareStatement(editQuery);
            ps.setString(1, type);
            ps.setString(2, price);

            ps.setInt(3, roomNumber);
       
        
            
            return (ps.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterForm.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }        
        
           
    }
    
        public boolean removeRoom(int roomNumber)
    {
        PreparedStatement st;
        String deleteQuery = "DELETE FROM `rooms` WHERE `roomNumber`=?";
        
        try {
            
            st = myConnection.getConnection().prepareStatement(deleteQuery);

            st.setInt(1, roomNumber);
            
            return (st.executeUpdate() > 0);
             
           } 
           catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void fillRoomsJTable(JTable table)
    {
        PreparedStatement ps;
        ResultSet rs;
        String selectQuery = "SELECT * FROM `rooms`";
        
        try {
            
            ps = myConnection.getConnection().prepareStatement(selectQuery);
            
            rs = ps.executeQuery();
            
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next())
            {
               row = new Object[3];
               row[0] = rs.getInt(1);
               row[1] = rs.getString(2);
               row[2] = rs.getString(3);
               
               tableModel.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    
    
    
    
}
