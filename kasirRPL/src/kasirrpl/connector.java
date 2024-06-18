package kasirrpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connector {

    private static Connection connection = null;

    private static final String DB_URL = "jdbc:mysql://localhost/kasirrpl";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Koneksi DB Berhasil");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Koneksi DB gagal : " + ex.getMessage());
        }
    }

    public static Connection connection() {
        return connection;
    }
}