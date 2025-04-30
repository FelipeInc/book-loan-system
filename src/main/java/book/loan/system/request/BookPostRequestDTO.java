package book.loan.system.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record BookPostRequestDTO(@NotNull(message = "The book title can't be null")
                                 @NotEmpty(message = "The book title can't be Empty")
                                 String title,

                                 @NotNull(message = "The author name can't be null")
                                 @NotEmpty(message = "The author name can't be Empty")
                                 String author,

                                 @NotEmpty(message = "The ISBN can't be empty")
                                 @NotNull(message = "The ISBN can't be null")
                                 @Pattern(regexp = "^[0-9]+$", message = "This field must contain only numbers")
                                 @Size(min = 13, max = 13, message = "The ISBN must have 13 numbers")
                                 String isbn) {

}
