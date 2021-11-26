package hotel.admin;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class MyConnection {

   
    public Connection getConnection(){
        
        Connection connection = null;

        
        MysqlDataSource mds = new MysqlDataSource();
        
        mds.setServerName("localhost");
        mds.setPortNumber(3306);
        mds.setUser("root");
        mds.setPassword("");
        mds.setDatabaseName("hotel");
        
        try {
            connection = mds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connection;       
    }
    
    
    
}
