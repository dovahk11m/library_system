package dao;

import dto.Book;
import dto.Student;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 5.19
 */
public class StudentDAO {

    //학생 추가

    public void addStudent(Student student) throws SQLException {
        //쿼리문 TODO
        String sql = "insert into Students (name, student_id)" +
                "values (?, ?) ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)){

            psmt.setString(1,student.getName());
            psmt.setString(2,student.getStudent_id());

            psmt.executeUpdate();
        }
    }//addStudent

    //모든 학생 조회
    public List<Student> getAllStudents() throws SQLException {
        List<Student> studentList = new ArrayList<>();

        String sql = "select*from students";

        try(Connection conn = DatabaseUtil.getConnection();
            PreparedStatement psmt = conn.prepareStatement(sql)){

            ResultSet rs = psmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String studentId = rs.getString("student_id");

                Student student = new Student(id, name, studentId);
                studentList.add(student);
            }
        }

        return studentList;
    }//getAllStudent

    //학생 학번 (student_id) 조회 - 로그인
    public Student authenticateStudent(String studentId) throws SQLException {

        List<Student> sList = new ArrayList<>();
        String sql = "select*from students where student_id = ?";

        try(Connection conn = DatabaseUtil.getConnection();
        PreparedStatement psmt = conn.prepareStatement(sql)){

            psmt.setString(1, studentId);
            ResultSet rs = psmt.executeQuery();
        }

        //정확한 학번 입력시 Student 객체 생성 리턴


        //잘못된 학번 입력시 null 반환
        return null;
    }//auth..

    public static void main(String[] args) {

        //전체 조회 테스트
        StudentDAO sdao = new StudentDAO();
        try {
            sdao.getAllStudents();
            //ArrayList<Book> sBookList = (ArrayList) bookDAO.searchBooksTitle("자바");

            for (int i = 0; i < sdao.getAllStudents().size(); i++) {
                System.out.println(sdao.getAllStudents().get(i));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }//main
}//class
