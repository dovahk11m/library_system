package _testtest;

import dto.Book;
import dto.Student;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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


    public static void main(String[] args) {

        MyStudentDAO msd = new MyStudentDAO();

        try {
            msd.addStud(new Student(5,"김김김","12344321"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }//main
}//MyStudentDAO
