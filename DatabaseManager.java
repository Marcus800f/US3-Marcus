import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

   
    private static final String URL = "jdbc:mysql://localhost:3306/école?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String USER = "root";  
    private static final String PASSWORD = "";  

    static {
        try {
            // (j'ai du trouver une solution car j'utillise vs code et j'avais des pb de driver)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver JDBC chargé avec succès !");
        } catch (ClassNotFoundException e) {
            System.out.println("Impossible de charger le driver JDBC MySQL !");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
