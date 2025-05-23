package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AcademyUtil {

    private static final String DB_URL = "jdbc:mysql://192.168.0.184:3306/academy_management?serverTimezone=Asia/Seoul";
    private static final String DB_User = "academy_admin";
    private static final String DB_PASSWORD = "ghkdlxld";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_User, DB_PASSWORD);
    }//getConnection
}//AcademyUtil
