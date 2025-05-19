package dto;
import lombok.*;
/**
 5.16
 모델링이란 것을 해보자

 여기 객체들은
 while문에 불려가는 역할

 그래서 dto
 Data Trasition Object라고 한다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private int id;
    private String title;
    private String author;
    private String publisher;
    private int publicationYear;
    private String isbn;
    private boolean available;

}//Books
