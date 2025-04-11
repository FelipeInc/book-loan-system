package book.loan.system.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The book title can't be null")
    @NotEmpty(message = "The book title can't be Empty")
    @Column(length = 200)
    private String title;

    @NotNull(message = "The author name can't be null")
    @NotEmpty(message = "The author name can't be Empty")
    @Column(length = 200)
    private String author;

    @NotEmpty(message = "The ISBN can't be empty")
    @NotNull(message = "The ISBN can't be null")
    @Column(unique = true)
    @Pattern(regexp = "^[0-9]+$", message = "This field must contain only numbers")
    @Size(min = 13, max = 13, message = "The ISBN must have 13 numbers")
    private String isbn;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    @JoinColumn(name = "loan_id")
    private Loan idLoan;

}
