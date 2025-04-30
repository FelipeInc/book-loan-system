package book.loan.system.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record APIClientLoginRequestDTO(
        @NotEmpty(message = "Enter a valid email address")
        @NotNull(message = "Enter a valid email address")
        @Email(message = "Enter a valid email address")
        String email,

        @NotEmpty(message = "This field can't be empty")
        @NotNull(message = "This field can't be empty")
        String password) {
}
