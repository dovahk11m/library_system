package dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Admin {

    private int adminPk;
    private String adminId;
    private String adminName;

}
