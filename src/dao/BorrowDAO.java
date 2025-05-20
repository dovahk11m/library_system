package dao;
import dto.Borrow;
import util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {

    //도서 대출
    public void borrowBook(int bookID, int studentPK) throws SQLException {

        //대출 가능여부 확인 select from books
        String sqlSelect = "select*from books where id = ? ";

        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement psmtSelect = conn.prepareStatement(sqlSelect)) {

            psmtSelect.setInt(1, bookID);

            ResultSet rsSelect = psmtSelect.executeQuery();

            //목록이 존재하고 && avilable 값이 true 일때
            if (rsSelect.next() && rsSelect.getBoolean("available")) {

                //대출 입력 insert into borrows
                String sqlInsert = "insert into borrows (student_id, book_id, borrow_date) " + "values (?, ?, current_date) ";
                //대출 완료 update books where available
                String sqlUpdate = "update books set available = false where id = ? ";


                try (PreparedStatement borrowStmt = conn.prepareStatement(sqlInsert); PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate)) {

                    borrowStmt.setInt(1, studentPK);
                    borrowStmt.setInt(2, bookID);
                    System.out.println("~~~~~ ~~~~~ ~~~~~ ~~~~~");
                    updateStmt.setInt(1, bookID);

                    borrowStmt.executeUpdate();
                    updateStmt.executeUpdate();
                }

            } else {
                throw new SQLException("대출 불가능한 도서입니다.");
            }//if
        }//try
    }//borrowBook

    //대출 현황
    public List<Borrow> getBorrowedBooks() throws SQLException {

        List<Borrow> borrowList = new ArrayList<>();
        String sql = "select*from borrows where return_date is null";

        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement psmt = conn.prepareStatement(sql)) {

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {
                Borrow borrowDTO = new Borrow();
                borrowDTO.setBook_id_(rs.getInt("id"));
                borrowDTO.setBook_id_(rs.getInt("book_id"));
                borrowDTO.setStudent_id(rs.getInt("student_id"));
                //javaDTO에서 데이터타입은 LocalDate 이다.
                //근데 java JDBC에서 LocalDate 지원 x
                //Date 를 사용한 뒤 형변환(toLocalDate())해야한다.
//                borrowDTO.setBorrow_date(rs.getLocalDate("borrow_date"));
                borrowDTO.setBorrow_date(rs.getDate("borrow_date").toLocalDate());
                borrowList.add(borrowDTO);
            }
        }
        return borrowList;
    }//getBorrowedBooks

    //도서 반납
    public void returnBook(int bookID, int studentPK) throws SQLException {

        //finally 자원해제를 위해 try문 외부에 변수 선언했다.
        Connection conn = null;

        try {
            conn = DatabaseUtil.getConnection();

            //트랜잭션
            conn.setAutoCommit(false); //자동저장을 막겠다는 뜻

            //이 쿼리의 결과집합에서 필요한 것은 borrows의 pk값(id) 뿐이다.
            int borrowId = 0;
            String checkSql = "select id from borrows " + " where book_id = ? " + " and student_id = ? " + " and return_date is null ";

            //1.반납하려는 책을 특정 book_id

            //2.같은 책을 빌린 다른 고객과 구분 student_id

            //3.같은 고객의 과거 이력과 구분 return_date

            try (PreparedStatement checkPsmt = conn.prepareStatement(checkSql)) {

                checkPsmt.setInt(1, bookID);
                checkPsmt.setInt(2, studentPK);
                checkPsmt.executeQuery();

                ResultSet rs = checkPsmt.executeQuery();

                if (!rs.next()) {
                    throw new SQLException("문의하신 대출기록이 존재하지 않거나 " + "이미 반납 처리 됐습니다.");
                }
                //borrows 테이블의 pk 특정
                borrowId = rs.getInt("id");
            }

            String sqlBorrow = "update borrows set return_date = ? where id = ? ";
            String sqlBook = "update books set available = true where id = ? ";

            try (PreparedStatement borrowPsmt = conn.prepareStatement(sqlBorrow); PreparedStatement bookPsmt = conn.prepareStatement(sqlBook)) {

                //borrow 설정
                borrowPsmt.setInt(1, borrowId);
                borrowPsmt.executeUpdate();

                //book 설정
                bookPsmt.setInt(1, bookID);
                bookPsmt.executeUpdate();
            }
            //트랜잭션 완료, 커밋
            conn.commit();

        } catch (SQLException e) {

            //오류 발생시 롤백 처리
            if (conn != null) {
                conn.rollback();
            }
            System.err.println("오류발생, rollback 처리 됐습니다");

        } finally {
            if (conn != null) {

                //오토커밋 복구
                conn.setAutoCommit(true);

                //자원 해제 (메모리 누수 방지)
                conn.close();
            }
        }

    }//returnBook


    public static void main(String[] args) {

        //도서 대출 테스트
        BorrowDAO borrowDAO = new BorrowDAO();

//        try {
//            borrowDAO.borrowBook(3, 1);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        try {
//            borrowDAO.getBorrowedBooks();
//            for (int i = 0; i < borrowDAO.getBorrowedBooks().size(); i++) {
//                System.out.println(borrowDAO.getBorrowedBooks().get(i));
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        try {
            borrowDAO.returnBook(3, 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }//main

}//BorrowDAO
