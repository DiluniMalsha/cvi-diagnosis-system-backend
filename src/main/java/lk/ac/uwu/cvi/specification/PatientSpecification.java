package lk.ac.uwu.cvi.specification;

import jakarta.persistence.criteria.Predicate;
import lk.ac.uwu.cvi.dto.request.PatientRequestDTO;
import lk.ac.uwu.cvi.entity.Patient;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PatientSpecification {

    public Specification<Patient> getSearchCriteria(PatientRequestDTO request) {

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (request.getRegistrationId() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("registrationId"), "%" + request.getRegistrationId() + "%"));
            }

            if (request.getFirstName() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("firstName"), "%" + request.getFirstName() + "%"));
            }

            if (request.getLastName() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("lastName"), "%" + request.getLastName() + "%"));
            }

            return predicate;
        };
    }

}
