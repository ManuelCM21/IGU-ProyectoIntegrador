
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    Connection con;
    
    public Connection getConnection() {
        try {
            String myBD = "jdbc:mysql://localhost:3307/sistemaventa";
            con = DriverManager.getConnection(myBD, "root", "");
            return con;
        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }
    
}
