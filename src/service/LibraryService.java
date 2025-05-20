package service;
import dao.BookDAO;
import dao.BorrowDAO;
import dao.StudentDAO;
import dto.Book;
import dto.Borrow;
import dto.Student;
import java.sql.SQLException;
import java.util.List;
/**
 5.20
 비즈니스 로직
 서비스로 구현

 이렇게 만들면 뭐가 좋은가?
 1.하나의 코드에 하나의 목적
 2.가독성
 */
public class LibraryService {

    //변수선언?
    private final BookDAO bookDAO = new BookDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final BorrowDAO borrowDAO = new BorrowDAO();

    //BookDAO의 메서드랑 뭐가 다르지?
    //책을 추가하는 서비스
    public void addBook(Book book) throws SQLException {

        //입력값 유효성 검사
        if (book.getTitle() == null || book.getAuthor() == null
                || book.getTitle().trim().isEmpty() || book.getAuthor().trim().isEmpty()) {
            throw new SQLException("책 제목과 저자를 입력해주세요");
        }
        //유효성 검사 완료, bookDAO에 협업 요청
        bookDAO.addBook(book);
    }//

    //책을 전체 조회하는 서비스
    public List<Book> getAllBooks() throws SQLException { //view에 예외처리 넘기기

        return bookDAO.getAllBooks();
    }//

    //책을 이름 조회하는 서비스
    public List<Book> searchBooksTitle(String title) throws SQLException {

        //입력값 유효성 검사
        if (title == null || title.trim().isEmpty()) {
            throw new SQLException("책 제목을 입력해주세요");
        }
        return bookDAO.searchBooksTitle(title);
    }//

    //학생 추가하는 서비스
    public void addStudent(Student student) throws SQLException { //view에 예외처리 넘기기

        //유효성 검사 TODO 직접 구현

        studentDAO.addStudent(student);
    }//

    //학생 전체 조회하는 서비스
    public List<Student> getAllStudent() throws SQLException {
        return studentDAO.getAllStudents();
    }//

    //도서 대출하는 서비스
    public void borrowBook(int bookID, int studentPK) throws SQLException {

        //입력값 유효성 검사 (validation)
        if (bookID <= 0 || studentPK <= 0) {
            throw new SQLException("유효한 도서id와 학생id를 입력해주세요");
        }

        //유효성 검사 완료, borrowDAO에 협업 요청 (insert 처리 책임)
        borrowDAO.borrowBook(bookID, studentPK);
    }//

    //도서 대출 조회하는 서비스
    public List<Borrow> getBorrowedBooks() throws SQLException {
        return borrowDAO.getBorrowedBooks();
    }//

    //도서 반납하는 서비스
    public void returnBook(int bookID, int studentPK) throws SQLException {

        //입력값 유효성 검사
        if (bookID <= 0 || studentPK <= 0) {
            throw new SQLException("유효한 도서id와 학생id를 입력해주세요");
        }
        //유효성 검사 완료, borrowDAO에 협업 요청
        borrowDAO.returnBook(bookID, studentPK);
    }//

    //인증 개념 추가
    //학번으로 학생 인증하는 서비스
    public Student authenticateStudent(String studentID)throws SQLException {
        //입력값 유효성 검사
        if (studentID == null || studentID.trim().isEmpty()) {
            throw new SQLException("유효한 학번을 입력해주세요");
        }
        //유효성 검사 완료, studentDAO에 협업 요청
        return studentDAO.authenticateStudent(studentID);
    }//

}//LibraryService
