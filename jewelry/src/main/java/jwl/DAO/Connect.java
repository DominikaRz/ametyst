/**
 * "Oprogramowanie dla sklepu internetowego z biżuterią"
 * @author DRzepka
 * created: 28.05.2021 r.
 */
package jwl.DAO;

import java.sql.*;

public class Connect {
    private final String jdbcURL;
    private final String jdbcUsrN;
    private final String jdbcPasswd;
    public Connection jdbcConnection;
      
  //-----Constructor-----  
    public Connect(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsrN = jdbcUsername;
        this.jdbcPasswd = jdbcPassword;
    }
    
  //-----Connect-----  
    public void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsrN, jdbcPasswd);
        }
    }

  //----Disconnect----- 
    public void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

}
