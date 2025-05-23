package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Students {

    private int studentPk;
    private String studentId;
    private String studentName;
    private LocalDate studentBirth;
    private String studentPhone;
    private String studentEmail;

    private String courseTitle;
    private LocalDate courseStartDate;
    private LocalDate courseEndDate;

    //생성자
    public Students(int studentPk, String studentId, String studentName, LocalDate studentBirth, String studentPhone, String studentEmail) {
        this.studentPk = studentPk;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentBirth = studentBirth;
        this.studentPhone = studentPhone;
        this.studentEmail = studentEmail;
    }//

}//Students

