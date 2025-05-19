package dto;
import lombok.*;
import java.time.LocalDate;
/**
 5.16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Borrow {

    private int id;
    private int student_id;
    private int book_id_;
    private LocalDate borrow_date;
    private LocalDate return_date;

}//Books
