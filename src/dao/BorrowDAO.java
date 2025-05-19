package dao;

import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowDAO {

    //도서 대출
    public void borrowBook(int bookID, int studentPK) throws SQLException {

        //대출 가능여부 확인 select from books
        String sqlSelect = "select*from books where id = ? ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement psmtSelect = conn.prepareStatement(sqlSelect)) {

            psmtSelect.setInt(1, bookID);

            ResultSet rsSelect = psmtSelect.executeQuery();

            //목록이 존재하고 && avilable 값이 true 일때
            if (rsSelect.next() && rsSelect.getBoolean("available")) {

                //대출 입력 insert into borrows
                String sqlInsert = "insert into borrows (student_id, book_id, borrow_date) " +
                        "values (?, ?, current_date) ";
                //대출 완료 update books where available
                String sqlUpdate = "update books set available = false where id = ? ";


                try (PreparedStatement borrowStmt = conn.prepareStatement(sqlInsert);
                     PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate)) {

                    borrowStmt.setInt(1,studentPK);
                    borrowStmt.setInt(2,bookID);
                    System.out.println("~~~~~ ~~~~~ ~~~~~ ~~~~~");
                    updateStmt.setInt(1,bookID);

                    borrowStmt.executeUpdate();
                    updateStmt.executeUpdate();
                }

            } else {
                throw new SQLException("대출 불가능한 도서입니다.");
            }//if
        }//try
    }//borrowBook

    public static void main(String[] args) {

        //도서 대출 테스트
        BorrowDAO borrowDAO = new BorrowDAO();

        try {
            borrowDAO.borrowBook(1, 3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }//main

}//BorrowDAO
