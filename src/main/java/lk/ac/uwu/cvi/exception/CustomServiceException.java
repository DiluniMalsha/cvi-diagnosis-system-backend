package lk.ac.uwu.cvi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomServiceException extends RuntimeException {
    private Integer code;
    private String message;
}
