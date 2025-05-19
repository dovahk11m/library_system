package dao;

import dto.Student;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 5.19
 */
public class StudentDAO {

    //학생 추가

    public void addStudent(Student student) throws SQLException {
        //쿼리문 TODO
        String sql = "insert into Students (name, student_id) " +
                "values (?, ?) ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, student.getName());
            psmt.setString(2, student.getStudent_id());

            psmt.executeUpdate();
        }
    }//addStudent

    //모든 학생 조회
    public List<Student> getAllStudents() throws SQLException {
        List<Student> studentList = new ArrayList<>();

        String sql = "select*from students";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            ResultSet rs = psmt.executeQuery(sql);

            while (rs.next()) {

                //객체를 먼저 생성해두고 값을 할당하는 코드
                Student studentDto = new Student();

                studentDto.setId(rs.getInt("id"));
                studentDto.setName(rs.getString("name"));
                studentDto.setStudent_id(rs.getString("student_id"));

                studentList.add(studentDto);
            }
        }

        return studentList;
    }//getAllStudent

    //학생 학번 (student_id) 조회 - 로그인
    public Student authenticateStudent(String searchStudId) throws SQLException {

        String sql = "select*from students where student_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, searchStudId);
            ResultSet rs = psmt.executeQuery();

            if (rs.next()) { //어차피 단일문이라 굳이 while이 아니어도 된다.
                Student studentDTO = new Student();

                studentDTO.setId(rs.getInt("id"));
                studentDTO.setName(rs.getString("name"));
                studentDTO.setStudent_id(rs.getString("student_id"));

                return studentDTO;
            }
        }//authenticateStudent

        //정확한 학번 입력시 Student 객체 생성 리턴

        //잘못된 학번 입력시 null 반환
        return null;
    }//auth..

    public static void main(String[] args) {

        //전체 조회 테스트
//        StudentDAO sdao = new StudentDAO();
//        try {
//            sdao.getAllStudents();
//
//            for (int i = 0; i < sdao.getAllStudents().size(); i++) {
//                System.out.println(sdao.getAllStudents().get(i));
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        StudentDAO sdao = new StudentDAO();
        try {
            System.out.println(sdao.authenticateStudent("20230001"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }//main
}//class
