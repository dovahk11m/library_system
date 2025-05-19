package _testtest;

import dto.Book;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyBookDAO {

    //추가
    public void addBook(Book book) throws SQLException {

        String sql = "insert into books (title, author, publisher, publication_year, isbn " +
                "values (?, ?, ?, ?, ?) ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)){

            psmt.setString(1, book.getTitle());
            psmt.setString(2, book.getAuthor());
            psmt.setString(3, book.getPublisher());
            psmt.setInt(4, book.getPublicationYear());
            psmt.setString(5, book.getIsbn());

            psmt.executeUpdate();
        }
    }

    //전체조회
    public List<Book> getAllBooks() throws SQLException {
        List<Book> bookList = new ArrayList<>();

        String sql = "select * from books";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()){

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
    }

    public static void main(String[] args) {

        MyBookDAO mbd = new MyBookDAO();

        try{
            mbd.getAllBooks();
            for (int i = 0; i < mbd.getAllBooks().size(); i++) {
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }//main
}//class
