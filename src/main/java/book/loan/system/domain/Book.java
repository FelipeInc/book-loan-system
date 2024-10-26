package book.loan.system.domain;

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
    @NotNull
    @Column(length = 200)
    private String title;
    @NotNull
    @Column(length = 200)
    private String author;
    @NotEmpty(message = "The ISBN can't be empty")
    @NotNull(message = "The ISBN can't be null")
    @Column(unique = true)
    @Pattern(regexp = "^[0-9]+$", message = "O campo deve conter apenas números.")
    @Size(min = 13, max = 13, message = "The ISBN must have 13 numbers")
    private String isbn;
}
