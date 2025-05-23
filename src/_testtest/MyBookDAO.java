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
    }//addBook

    //전체조회
    public List<Book> getAllBooks() throws SQLException {
        List<Book> bookList = new ArrayList<>();

        String sql = "select * from books";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                Book bookDTO = new Book();

                bookDTO.setId(rs.getInt("id"));
                bookDTO.setTitle(rs.getString("title"));
                bookDTO.setAuthor(rs.getString("author"));
                bookDTO.setPublisher(rs.getString("publisher"));
                bookDTO.setPublicationYear(rs.getInt("publication_year"));
                bookDTO.setIsbn(rs.getString("isbn"));
                bookDTO.setAvailable(rs.getBoolean("available"));

                bookList.add(bookDTO);
            }//while
        }//try
        return bookList;
    }//getAllBooks

    //도서 제목 조회
    public List<Book> searchByTitle(String byTitle) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        String sql = "select * from books where title like ?";

        try (Connection conn = DatabaseUtil.getConnection();
        PreparedStatement psmt = conn.prepareStatement(sql)){
            psmt.setString(1, "%"+byTitle+"%");
            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {

                Book bookDTO = new Book();

                bookDTO.setId(rs.getInt("id"));
                bookDTO.setTitle(rs.getString("title"));
                bookDTO.setAuthor(rs.getString("author"));
                bookDTO.setPublisher(rs.getString("publisher"));
                bookDTO.setPublicationYear(rs.getInt("publication_year"));
                bookDTO.setIsbn(rs.getString("isbn"));
                bookDTO.setAvailable(rs.getBoolean("available"));

                bookList.add(bookDTO);
            }//while
        }//try
        return bookList;
    }

    public static void main(String[] args) {

        MyBookDAO mbd = new MyBookDAO();

//        try{
//            mbd.getAllBooks();
//            for (int i = 0; i < mbd.getAllBooks().size(); i++) {
//                System.out.println();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        try {
//            for (int i = 0; i < mbd.searchByTitle("자바").size(); i++) {
//                System.out.println(mbd.searchByTitle("자바").get(i));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            ArrayList<Book> bookArrayList;
//            bookArrayList = (ArrayList<Book>) mbd.searchByTitle("자바");
//            for (int i = 0; i < bookArrayList.size(); i++) {
//                System.out.println(bookArrayList.get(i));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        try {
            ArrayList<Book> list = (ArrayList) mbd.searchByTitle("자바");
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } catch (SQLException e) {
        }



    }//main
}//class
