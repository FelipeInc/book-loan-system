package book.loan.system.exception;


import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected int status;
    protected String error;
    protected String developerMessage;
    protected String details;
    protected LocalDateTime timestamp;
}
