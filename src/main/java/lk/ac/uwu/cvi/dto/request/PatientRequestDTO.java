package lk.ac.uwu.cvi.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class PatientRequestDTO extends RequestDTO {
    private Long id;
    private String registrationId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Integer age;
    private String address;
    private Integer gestationAtDelivery;
    private Double birthWeight;
    private String motherName;
    private Integer motherAge;
    private String fatherName;
    private Integer fatherAge;
    private String otherComorbidity;
}
