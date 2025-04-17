package book.loan.system.request;

import book.loan.system.domain.UserRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserRegisterDTO(
        @NotEmpty(message = "This field can't be empty")
        @NotNull(message = "This field can't be empty")
        String name,

        @NotEmpty(message = "Email is required")
        @NotNull(message = "Email is required")
        @Email(message = "Enter a valid email address")
        String email,

        @NotEmpty(message = "This field can't be empty")
        @NotNull(message = "This field can't be empty")
        String password,

        @NotEmpty(message = "This field can't be empty")
        @NotNull(message = "This field can't be empty")
        UserRoles authorities) {

}
