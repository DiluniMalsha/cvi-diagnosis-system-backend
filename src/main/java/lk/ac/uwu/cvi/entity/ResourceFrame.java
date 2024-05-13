package lk.ac.uwu.cvi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ResourceFrame {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer time;
    private Integer frameNumber;

    @ManyToOne
    private Resource resource;
}
