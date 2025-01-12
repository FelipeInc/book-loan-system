package book.loan.system.request;

import jakarta.validation.constraints.Email;

public record LoanPostReqestDTO(String email, Long id) {
}
