package _testtest;

import dto.Book;
import dto.Student;
import util.DatabaseUtil;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyStudentDAO {

    //학생추가
    public void addStud(Student stud)throws SQLException {

        String sql = "insert into Students (name, student_id) values (?, ?) ";

        try(Connection conn = DatabaseUtil.getConnection();
            PreparedStatement psmt = conn.prepareStatement(sql)){

            psmt.setString(1, stud.getName());
            psmt.setString(2, stud.getStudent_id());

            psmt.executeUpdate();
        }//try
    }//addStud

    //모든 학생 조회
    public List<Student> getAllStud() throws SQLException {
        List<Student> studentList = new ArrayList<>();

        String sql = "select*form students";

        try (Connection connection = DatabaseUtil.getConnection();
        PreparedStatement psmt = connection.prepareStatement(sql)) {

            ResultSet rs = psmt.executeQuery(sql);

        }
        return studentList;
    }


    public static void main(String[] args) {

        MyStudentDAO msd = new MyStudentDAO();

        try {
            msd.addStud(new Student(5,"김김김","12344321"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }//main
}//MyStudentDAO
