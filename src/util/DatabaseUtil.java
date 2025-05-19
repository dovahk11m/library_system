package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 5.16
 db 연결을 관리하는 유틸리티 클래스
 dao에서 반복적으로 사용되는 상수들을 정의한다.
 */
public class DatabaseUtil {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/library?serverTimezone=Asia/Seoul";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "asd1234";

    //db 연결 객체를 반환하는 함수
    public static Connection getConnection() throws SQLException {//예외처리 던지기
        return DriverManager.getConnection(DB_URL, DB_USER,DB_PASSWORD);
    }


}
