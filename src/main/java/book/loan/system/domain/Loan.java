package book.loan.system.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate loanDate;

    @OneToOne(mappedBy = "idLoan", cascade = CascadeType.ALL)
    private Book bookRented;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User userEmail;

    private LocalDate dateToGiveBack;
}
