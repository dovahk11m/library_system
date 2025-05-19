package dao;

import dto.Book;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 5.19
 * 도서 관련 db 작업을 처리하는 DAO 클래스
 * CRUD
 */
public class BookDAO {

    //새 도서를 db에 추가
    public void addBook(Book book) throws SQLException { //dto가 제공해준다.
        String sql = "insert into books (title, author, publisher, publication_year, isbn) " +
                "values (?, ?, ?, ?, ?) ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, book.getTitle());
            psmt.setString(1, book.getAuthor());
            psmt.setString(1, book.getPublisher());
            psmt.setInt(1, book.getPublicationYear());
            psmt.setString(1, book.getIsbn());

            psmt.executeUpdate();
        }
    }//addBook

    //모든 도서 조회
    public List<Book> getAllBooks() throws SQLException {
        List<Book> bookList = new ArrayList<>();

        String sql = "select * from books";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                int publicationYear = rs.getInt("publication_year");
                String isbn = rs.getString("isbn");
                boolean available = rs.getBoolean("available");

                Book book = new Book(id, title, author, publisher, publicationYear, isbn, available);
                bookList.add(book);
            }
        }
        return bookList;
    }//getAllBooks

    //도서 제목 조회
    public List<Book> searchBooksTitle(String stitle) throws SQLException {

        List<Book> bookList = new ArrayList<>();

        String sql = "select * from books where title like ? ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, "%" + stitle + "%");
            ResultSet rs = psmt.executeQuery();

            while (rs.next()) { //검색된 내용이 있다면 실행

                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                int publicationYear = rs.getInt("publication_year");
                String isbn = rs.getString("isbn");
                boolean available = rs.getBoolean("available");

                Book book = new Book(id, title, author, publisher, publicationYear, isbn, available);
                bookList.add(book);
            }
        }

        return bookList;
    }

    //수정, 삭제

    //TODO 삭제예정
    public static void main(String[] args) {

        //전체 조회 테스트
        BookDAO bookDAO = new BookDAO();

        try {
            //bookDAO.getAllBooks();

            ArrayList<Book> sBookList = (ArrayList) bookDAO.searchBooksTitle("자바");


            for (int i = 0; i < sBookList.size(); i++) {
                System.out.println(sBookList.get(i));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }//main
}//class
