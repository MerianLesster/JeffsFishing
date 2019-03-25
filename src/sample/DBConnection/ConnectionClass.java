package sample.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
   public Connection connection;

    public Connection getConnection(){
        String dbName = "Jfs_db";
        String dbUserName = "root";
        String dbPassword = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + dbName,dbUserName,dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

}
