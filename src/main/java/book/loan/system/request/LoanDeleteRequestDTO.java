package book.loan.system.request;

import lombok.Builder;

@Builder
public record LoanDeleteRequestDTO(
        Long idLoan,
        String bookTitle) {
}
