package _testtest;

import dto.*;
import util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    // 관리자 회원정보 등록(INSERT) 트랜잭션
    public void addAdmin(Admin admin) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);
            String insertSql = "INSERT INTO admin(admin_id, admin_name) \" + \"VALUES (?, ?) ";

            System.out.println("adminId : " + admin.getAdminId());
            System.out.println("adminName : " + admin.getAdminName());

            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, admin.getAdminId());
                pstmt.setString(2, admin.getAdminName());
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("등록에 실패했습니다");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }//try-catch-finally
    }//addAdmin

    // 관리자 회원정보 조회(SELECT)
    public List<Admin> getAllAdmin() throws SQLException {

        List<Admin> adminList = new ArrayList<>();
        String selectSql = "SELECT * FROM admin ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Admin adminDto = new Admin();

                adminDto.setAdminPk(rs.getInt("admin_Pk"));
                adminDto.setAdminId(rs.getString("admin_id"));
                adminDto.setAdminName(rs.getString("admin_name"));

                adminList.add(adminDto);
            }//while
        }//try
        return adminList;
    }//getAllAdmin

    //관리자 회원정보 수정(UPDATE) 트랜잭션
    public void updateAdmin(Admin admin) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String newAdminName = null;

            String checkSql = "SELECT * FROM admin WHERE admin_pk = ? ";

            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setInt(1, admin.getAdminPk());
                ResultSet rs = checkPstmt.executeQuery();

                if (!rs.next()) {
                    throw new SQLException("해당 관리자 정보가 존재하지 않습니다.");
                }
                newAdminName = admin.getAdminName();
            }

            String updateNameSql = "UPDATE admin SET admin_name = ? WHERE admin_pk = ? ";
            try (PreparedStatement pstmt = conn.prepareStatement(updateNameSql)) {
                pstmt.setString(1, newAdminName);
                pstmt.setInt(2, admin.getAdminPk());
                pstmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("관리자 정보 수정에 실패했습니다");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }//try-catch-finally
    }//updateAdmin

    //관리자 회원정보 삭제(Delete) 트랜잭션
    public void deleteAdmin(String adminId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String checkSql = "SELECT * FROM admin WHERE admin_id = ? ";
            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setString(1, adminId);
                ResultSet rs = checkPstmt.executeQuery();

                if (!rs.next()) {
                    throw new SQLException("해당 관리자 정보가 존재하지 않습니다");
                }
            }
            String deleteSql = "DELETE FROM admin WHERE admin_id = ? ";
            try (PreparedStatement deletepstmt = conn.prepareStatement(deleteSql)) {
                deletepstmt.setString(1, adminId);
                deletepstmt.executeUpdate();
                System.out.println("관리자 정보가 삭제됐습니다");
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("관리자 정보 삭제에 실패했습니다");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.commit();
            }
        }//try-catch-finally
    }//deleteAdmin

    //관리자 admin_id로 관리자 인증(로그인용) 기능
    public Admin authenticateAdmin(String adminId) throws SQLException {
        String sql = "SELECT * FROM admin WHERE admin_id = ? ";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, adminId);
            ResultSet rs = pstmt.executeQuery();

            //단일행 출력
            if (rs.next()) {
                Admin adminDto = new Admin();
                adminDto.setAdminId(rs.getString("admin_id"));
                adminDto.setAdminName(rs.getString("admin_name"));
                return adminDto;
            }
        }//try
        return null;
    }//authenticateAdmin

    //강의 개설
    public void addCourse(Course course) throws SQLException {

        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);
            String insetSql = "INSERT INTO course (course_title, course_capacity, start_date, end_date, teacher_id) " +
                    "VALUES (?, ?, ?, ?, ?) ";

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("강의 개설에 실피했습니다");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }//try-catch-finally
    }//addCourse

    //강의 수정(Update)
    public void updateCourse(Course course) throws SQLException {

        Connection conn = null;

        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String newCourseTitle = null;
            String newTeacherId = null;
            LocalDate newStartDate = null;
            LocalDate newEndDate = null;

            String checkSql = "SELECT * FROM course WHERE course_pk = ? ";

            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)){
                checkPstmt.setInt(1, course.getCoursePk());
                ResultSet rs = checkPstmt.executeQuery();

                if (!rs.next()) {
                    throw new SQLException("해당 강의는 존재하지 않습니다");
                }
                newCourseTitle = course.getCourseTitle();
                newTeacherId = course.getTeacherId();
                newStartDate = course.getStartDate();
                newEndDate = course.getEndDate();
            }
            String updateNameSql = "UPDATE course " + "SET teacher_id = ?, course_title = ?, start_date = ?, end_date = ? "
                    + " WHERE course_pk = ? ";

            try(PreparedStatement pstmt = conn.prepareStatement(updateNameSql)){
                pstmt.setString(1, newTeacherId);
                pstmt.setString(2, newCourseTitle);
                pstmt.setDate(3, Date.valueOf(newStartDate));
                pstmt.setDate(4, Date.valueOf(newEndDate));
                pstmt.setInt(5, course.getCoursePk());
                pstmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }//try-catch-finally
    }//updateCourse

    // 강의 정보 삭제(DELETE)
    public void deleteCourse(int coursePk) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String checkSql = "select * from course where course_pk = ? " +
                    " and start_date > current_date ";

            try(PreparedStatement checkPstmt = conn.prepareStatement(checkSql)){
                checkPstmt.setInt(1, coursePk);
                ResultSet rs = checkPstmt.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("해당 강의가 존재하지 않습니다");
                }
            }//

            String deleteIdSql = "delete from course where course_pk = ? ";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteIdSql)){
                pstmt.setInt(1, coursePk);
                pstmt.executeUpdate();
                System.out.println("강의가 삭제됐습니다");
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("강의 삭제에 실패했습니다");

        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }//try-catch-finally
    }//delete

    // 강사 회원정보 등록(INSERT)
    public void addTeacher(Teacher teacher) throws SQLException {
        Connection conn = null;

        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String insertSql = "insert into teacher(teacher_id, teacher_name, teacher_phone, teacher_email) " +
                    " values (?, ?, ?, ?) ";

            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, teacher.getTeacherId());
                pstmt.setString(2, teacher.getTeacherName());
                pstmt.setString(3, teacher.getTeacherPhone());
                pstmt.setString(4, teacher.getTeacherEmail());
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.commit();
            }
        }//try-catch-finally
    }//addTeacher

    // 강사 회원정보 수정(UPDATE)
    public void updateTeacher(Teacher teacher) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String newTeacherName = null;
            String newTeacherPhone = null;
            String newTeacherEmail = null;

            String checkSql = "select*from teacher where teacher_pk = ? ";

            try(PreparedStatement checkPstmt = conn.prepareStatement(checkSql)){

                checkPstmt.setInt(1, teacher.getTeacherPk());
                ResultSet rs = checkPstmt.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("해당 강사는 존재하지 않습니다");
                }
                newTeacherName = teacher.getTeacherName();
                newTeacherPhone = teacher.getTeacherPhone();
                newTeacherEmail = teacher.getTeacherEmail();
            }

            String updateSql = "update teacher set teacher_name = ?, teacher_phone = ?, " +
                    "teacher_email = ? where teacher_pk = ? ";

            try(PreparedStatement updatePstmt = conn.prepareStatement(updateSql)){
                updatePstmt.setString(1, newTeacherName);
                updatePstmt.setString(2, newTeacherPhone);
                updatePstmt.setString(3, newTeacherEmail);
                updatePstmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("강사 정부 수정에 실패했습니다");

        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }//try-catch-finally
    }//updateTeacher

    // 강사 회원정보 삭제(DELETE)
    public void deleteTeacher(String teacherId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String checkSql = "select * from teature where teacher_id ? ";

            try(PreparedStatement checkPstmt = conn.prepareStatement(checkSql)){

                checkPstmt.setString(1, teacherId);
                ResultSet rs = checkPstmt.executeQuery();

                if (!rs.next()) {
                    throw new SQLException("해당 강사 정보가 존재하지 않습니다");
                }
            }

            String deleteSql = "delete from teacher where teacher_id = ? ";

            try (PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)){

                deletePstmt.setString(1, teacherId);
                deletePstmt.executeUpdate();
                System.out.println("강사 정보가 삭제됐습니다");
            }
            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("강사 정보 삭제에 실패했습니다");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }//try-catch-finally
    }//deleteTeacher

    //학생 회원정보 등록(INSERT)
    public void addStudent(Students students) throws SQLException {
        Connection conn = null;

        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String insertSql = "INSERT INTO "
                    + "students(student_id, student_name, student_birth, student_phone, student_email) "
                    + "VALUES(?, ?, ?, ?, ?) ";

            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)){

                pstmt.setString(1, students.getStudentId());
                pstmt.setString(2, students.getStudentName());
                pstmt.setDate(3, Date.valueOf(students.getStudentBirth()));
                pstmt.setString(4, students.getStudentPhone());
                pstmt.setString(5, students.getStudentEmail());

                pstmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new RuntimeException("학생 정보 등록에 실패했습니다");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.commit();
            }
        }//try-catch-finally
    }//addStudent

    // 학생 회원정보 수정(UPDATE)
    public void updateStudents(Students students) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String checkSql = "select * from students where student_id = ? ";

            try(PreparedStatement checkPstmt = conn.prepareStatement(checkSql)){

                checkPstmt.setString(1, students.getStudentId());
                ResultSet rs = checkPstmt.executeQuery();

                if (!rs.next()) {
                    throw new SQLException("해당 학생은 존재하지 않습니다");
                }
            }

            String updateSql = "UPDATE students SET student_name = ?, student_birth = ?, student_phone = ?, student_email = ? "
                    + "WHERE student_id = ? ";

            try(PreparedStatement updatePstmt = conn.prepareStatement(updateSql)){

                updatePstmt.setString(1, students.getStudentName());
                updatePstmt.setDate(2, Date.valueOf(students.getStudentBirth()));
                updatePstmt.setString(3,students.getStudentPhone());
                updatePstmt.setString(4,students.getStudentEmail());
                updatePstmt.setString(5,students.getStudentId());

                updatePstmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException(e.getMessage() + "학생 정보 수정에 실패했습니다",e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }//try-catch-finally
    }//updateStudents

    //학생 회원정보 삭제(Delete)
    public void deleteStudent(String studentId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String checkSal = "select * from students where student_id = ? ";

            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSal)){
                checkPstmt.setString(1,studentId);
                ResultSet rs = checkPstmt.executeQuery();

                if (!rs.next()) {
                    throw new SQLException("해당 학생 정보가 존재하지 않습니다");
                }
            }

            String deleteSql = "delete from students where student_id = ? ";

            try (PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)) {
                deletePstmt.setString(1, studentId);
                deletePstmt.executeUpdate();
                System.out.println("학생 정보가 삭제되었습니다.");
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("학생 정보 삭제에 실패했습니다.");
        } finally{
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }//try-catch-finally
    }//deleteStudent

}//class
