/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 17182
 */
public class Reservations {
    MyConnection myConnection = new MyConnection();
    Rooms room = new Rooms();
    
   /* public Reservation(int reservationID, int customerID, String arrivalDate, String departureDate){
        
    }*/
    public boolean addReservation(int customerID, int roomNumber, String arrivalDate, String departureDate)
    {
        PreparedStatement st;
        String addQuery = "INSERT INTO `reservations`(`customerID`, `roomNumber`, `ArrivalDate`, `DepartureDate`) VALUES (?,?,?,?)";
        
        try {
            
            st = myConnection.getConnection().prepareStatement(addQuery);
            
            st.setInt(1, customerID);
            st.setInt(2, roomNumber);
           
            st.setString(3, arrivalDate);
            st.setString(4, departureDate);
            
           

                    
            
        ArrayList<String> arrivalDateOfSameRoom = getArrivalDates(roomNumber);
        ArrayList<String> departureDateOfSameRoom = getDepartureDates(roomNumber);
            
            
            
       if(isReservationAllowed(arrivalDate,departureDate, arrivalDateOfSameRoom, departureDateOfSameRoom)){
               if (st.executeUpdate() > 0){
                   return true;
               }
               else{
                   return false;
               }
            }
            else{
                JOptionPane.showMessageDialog(null, "This Room Is Already Reserved for that date", "Room Reserved", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            
            
           
            
        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
       
        
    }
    
      
    public boolean removeReservation(int reservationID)
    {
        PreparedStatement st;
        String deleteQuery = "DELETE FROM `reservations` WHERE `reservationID`=?";
        
        try {
            
            st = myConnection.getConnection().prepareStatement(deleteQuery);

            st.setInt(1, reservationID);
            
            return (st.executeUpdate() > 0);
             
           } 
           catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public void fillReservationsJTable(JTable table)
    {
        PreparedStatement ps;
        ResultSet rs;
        String selectQuery = "SELECT * FROM `reservations`";
        
        try {
            
            ps = myConnection.getConnection().prepareStatement(selectQuery);
            
            rs = ps.executeQuery();
            
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next())
            {
               row = new Object[5];
               row[0] = rs.getInt(1);
               row[1] = rs.getInt(2);
               row[2] = rs.getInt(3);
               row[3] = rs.getString(4);
               row[4] = rs.getString(5);

               
               tableModel.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    public void fillCustomerReservationsJTable(JTable table, int ID)
    {
        PreparedStatement ps;
        ResultSet rs;
        String selectQuery = "SELECT * FROM `reservations`";
        
        try {
            
            ps = myConnection.getConnection().prepareStatement(selectQuery);
            
            rs = ps.executeQuery();
            
            DefaultTableModel tableModelOne = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next())
            {
               row = new Object[5];
               row[0] = rs.getInt(1);
               row[1] = rs.getInt(2);
               row[2] = rs.getInt(3);
               row[3] = rs.getString(4);
               row[4] = rs.getString(5);

               if(ID ==rs.getInt(2)){
               tableModelOne.addRow(row);
               }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }


        
        
    
    
    public ArrayList <String>  getArrivalDates(int roomNumber){
        ArrayList<String> arrivalDates = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        String compareQuery = "SELECT `ArrivalDate` FROM `reservations` WHERE `roomNumber`=?";
     
        try{
            
            ps = myConnection.getConnection().prepareStatement(compareQuery);
            
            ps.setInt(1, roomNumber);
            
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                arrivalDates.add(rs.getString(1));
            }
    
        } catch (SQLException ex){
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
            return arrivalDates;
        }
        return arrivalDates ;
        
    }
    public ArrayList <String>  getDepartureDates(int roomNumber){
        ArrayList<String> departureDates = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        String compareQuery = "SELECT `DepartureDate` FROM `reservations` WHERE `roomNumber`=?";
     
        try{
            
            ps = myConnection.getConnection().prepareStatement(compareQuery);
            
            ps.setInt(1, roomNumber);
            
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                departureDates.add(rs.getString(1));
            }
    
        } catch (SQLException ex){
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
            return departureDates;
        }
        return departureDates ;
    }
    public boolean isReservationAllowed(String arrivalDate, String departureDate, ArrayList <String> otherArrivalDates, ArrayList <String> otherDepartureDates){
        if(otherArrivalDates.isEmpty() || otherDepartureDates.isEmpty()){
                return true;
        }
        else{
            try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            int counterYes =0;
            int counterNo =0;
            
            Date dateArrivalDate = dateFormat.parse(arrivalDate);
            Date dateDepartureDate = dateFormat.parse(departureDate);
            
            for(int i=0; i<otherArrivalDates.size(); i++)
            {
                String otherArrivalDate =otherArrivalDates.get(i);
                Date otherDateArrivalDate = dateFormat.parse(otherArrivalDate);
                
                String otherDepartureDate =otherDepartureDates.get(i);
                Date otherDateDepartureDate =dateFormat.parse(otherDepartureDate);
                
                if(otherDateArrivalDate.after(dateDepartureDate) || otherDateArrivalDate.equals(dateDepartureDate))
                {
                    counterYes++;
                }
                else if(dateArrivalDate.after(otherDateDepartureDate) || dateArrivalDate.equals(otherDateDepartureDate))
                {
                    counterYes++;
                } 
                else{
                    counterNo++;
                }

            }
                return counterNo < 1;

            } catch (ParseException ex) {
                Logger.getLogger(Reservations.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

}
    
    public int getRoomNumberFromReservation(int reservationID)
    {
        PreparedStatement ps;
        ResultSet rs;
        String selectQuery = "SELECT `roomNumber` FROM `reservations` WHERE `reservationID` = ?";
        
        try {
            
            ps = myConnection.getConnection().prepareStatement(selectQuery);
            
            ps.setInt(1, reservationID);
            
            rs = ps.executeQuery();
            
            if(rs.next()) 
            {
                return rs.getInt(1);
            }
            else{
                return 0;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } 
    }    
    
    

  
   

    
}
