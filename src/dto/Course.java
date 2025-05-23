package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private int coursePk;
    private String teacherId;
    private String courseTitle;
    private int courseCapacity;
    private LocalDate startDate;
    private LocalDate endDate;

}
