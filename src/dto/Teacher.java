package dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    private int teacherPk;
    private String teacherId;
    private String teacherName;
    private String teacherPhone;
    private String teacherEmail;

    // 학생 혹은 강사가 수강중인, 혹은 담당중인 메서드 호출 시 필요한 값을 넣어주기 위한 변수
    private String courseTitle;
    private LocalDate startDate;
    private LocalDate endDate;


    public Teacher(int teacherPk, String teacherId, String teacherName, String teacherPhone, String teacherEmail) {
        this.teacherPk = teacherPk;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherPhone = teacherPhone;
        this.teacherEmail = teacherEmail;
    }


}
