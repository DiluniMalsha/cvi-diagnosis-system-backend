package lk.ac.uwu.cvi.dto.request;

import lombok.Data;

@Data
public class PatientRequestDTO {
    private Long id;
    private String characterId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String address;
    private String motherName;
    private Integer motherAge;
    private String fatherName;
    private Integer fatherAge;
}
