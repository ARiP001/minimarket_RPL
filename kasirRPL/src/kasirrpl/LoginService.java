/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasirrpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
/**
 *
 * @author Nathaleon
 */
public class LoginService {
    private connector k = new connector();
    private static int loggedInLevelId;
    private static boolean isLoggedIn = false; 
    private static String loggedInUsername;

    public static void setLoggedInLevelId(int levelId) {
        loggedInLevelId = levelId;
    }

    public static int getLoggedInLevelId() {
        return loggedInLevelId;
    }

    public static void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }


    public LoginService() {
        k.connection(); // Menghubungkan ke database saat objek LoginService dibuat
    }
    
    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public boolean checkLogin(String username, String password) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = k.connection().prepareStatement("select * from user where username=? and password=?");
            statement.setString(1, username);
            statement.setString(2, password);
            rs = statement.executeQuery();
            if (rs.next()) {
                loggedInLevelId = rs.getInt("level_id");
                isLoggedIn = true; // Setelah login berhasil, tandai bahwa pengguna sudah login
                loggedInUsername = username; // Simpan username yang berhasil login
                // Login berhasil, tampilkan menu
                Menu menu = new Menu(loggedInLevelId, loggedInUsername);
                menu.setLocationRelativeTo(null);
                menu.setVisible(true);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Akun tidak ditemukan");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    
}

