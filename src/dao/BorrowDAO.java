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

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

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
        //borrows select (book_id, return_date)
        String sqlCheck = "select*from borrows where student_id = ? and book_id = ? and return_date is null";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement psmtCheck = conn.prepareStatement(sqlCheck)) {

            psmtCheck.setInt(1, bookID);
            psmtCheck.setInt(2, studentPK);

            ResultSet rsCheck = psmtCheck.executeQuery();

            if (rsCheck.next()) {

                //borrows update (return_date)
                String sqlBorrows = "update borrows set return_date = current_date " +
                        " where student_id = " + studentPK +
                        " and book_id = " + bookID + " and return_date is null ";
                //books update (available)
                String sqlBooks = "update books set available = 1 where id = "+bookID;

                try (PreparedStatement psmtBorrows = conn.prepareStatement(sqlBorrows);
                     PreparedStatement psmtBooks = conn.prepareStatement(sqlBooks)){

                    psmtBorrows.executeUpdate();
                    psmtBooks.executeUpdate();
                }//try
            }//if
        }//try


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
            borrowDAO.returnBook(3,1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }//main

}//BorrowDAO
