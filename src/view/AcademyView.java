package view;

import dto.Admin;
import dto.Students;
import dto.Teacher;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;

public class AcademyView {

    //while문 제어변수
    private boolean isLoginEnd = false;

    //포매팅 변수
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //멤버변수
    private Students studentInfo = new Students();
    private Teacher teacherInfo = new Teacher();
    private Admin adminInfo = new Admin();





}
