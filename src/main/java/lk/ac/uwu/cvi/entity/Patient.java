package lk.ac.uwu.cvi.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String characterId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String address;
    private String motherName;
    private Integer motherAge;
    private String fatherName;
    private Integer fatherAge;
    @CreationTimestamp
    private LocalDateTime registeredDateTime;
}
