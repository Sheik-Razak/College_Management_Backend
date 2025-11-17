
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import custom_exception.DatabaseConnectionException;

public class DBUtil {
    public static Connection getConnection() throws DatabaseConnectionException {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/college_management";
            String user = "root";
            String pass = "root";
            return DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            throw new DatabaseConnectionException("JDBC Driver not found: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error while connecting to DB: " + e.getMessage(), e);
        }
    }
}
