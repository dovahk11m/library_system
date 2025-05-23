package _testtest;

import dto.Course;
import util.AcademyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    //수강 신청
    public void resisterCourse(int coursePk, String studentId) throws SQLException {

        Connection conn = null;

        try {
            conn = AcademyUtil.getConnection();
            conn.setAutoCommit(false);

            String checkSql = "select*from course_history where student_id = ? ";

            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {

                checkPstmt.setString(1, studentId);
                ResultSet rs = checkPstmt.executeQuery();

                if (rs.next()) {
                    throw new SQLException("해당 학생은 이미 강의를 수강중입니다");
                }
            }

            String insertSql = "insert into course_history(course_pk, student_id) " +
                    "values (?, ?) ";

            try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)){

                insertPstmt.setInt(1, coursePk);
                insertPstmt.setString(2, studentId);
                insertPstmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException(e.getMessage() + "수강정보로 등록에 실패했습니다", e);
        }finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }//try-catch-finally
    }//resisterCourse

    //수강취소
    public void cancelCourse(String studentId) throws SQLException {

        Connection conn = null;

        try {
            conn = AcademyUtil.getConnection();
            conn.setAutoCommit(false);

            String checkSql = "select * from course_history where student_id = ? ";

            try(PreparedStatement checkPstmt = conn.prepareStatement(checkSql)){

                checkPstmt.setString(1, studentId);
                ResultSet rs = checkPstmt.executeQuery();

                if (!rs.next()) {
                    throw new SQLException("수강기록이 존재하지 않거나 이미 삭제됐습니다");
                }
            }

            String deleteSql = "delete from course_history where student_id = ? ";

            try(PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)){

                deletePstmt.setString(1, studentId);
                deletePstmt.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException(e.getMessage() + "수강취소에 실패했습니다", e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }//try-catch-finally
    }//cancelCourse

    //전체조회
    public List<Course> getAllCourse() throws SQLException {

        List<Course> courseList = new ArrayList<>();

        String sql = "select*from course ";

        try(Connection conn = AcademyUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Course courseDto = new Course();

                courseDto.setCoursePk(rs.getInt("course_pk"));
                courseDto.setTeacherId(rs.getString("teacher_id"));
                courseDto.setCourseTitle(rs.getString("course_title"));
                courseDto.setCourseCapacity(rs.getInt("course_capacity"));
                courseDto.setStartDate(rs.getDate("start_date").toLocalDate());
                courseDto.setEndDate(rs.getDate("end_date").toLocalDate());

                courseList.add(courseDto);
            }//while
        }//try
        return courseList;
    }//getAllCourse

    //제목조회
    public List<Course> searchCourseByTitle(String courseTitle) throws SQLException {

        List<Course> courseList = new ArrayList<>();

        String sql = "select*from course where course_title like ? ";

        try(Connection conn = AcademyUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, "%" + courseTitle + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Course courseDto = new Course();

                courseDto.setCoursePk(rs.getInt("course_pk"));
                courseDto.setTeacherId(rs.getString("teacher_id"));
                courseDto.setCourseTitle(rs.getString("course_title"));
                courseDto.setCourseCapacity(rs.getInt("course_capacity"));
                courseDto.setStartDate(rs.getDate("start_date").toLocalDate());
                courseDto.setEndDate(rs.getDate("end_date").toLocalDate());

                courseList.add(courseDto);
            }//while
        }//try
        return courseList;
    }//getAllCourse

    public static void main(String[] args) {

        CourseDAO courseDAO = new CourseDAO();

        try {
            System.out.println(courseDAO.searchCourseByTitle("자바"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }//main
}//CourseDAO
