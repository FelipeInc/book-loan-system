package book.loan.system.controler;

import book.loan.system.domain.Loan;
import book.loan.system.request.LoanDeleteRequestDTO;
import book.loan.system.request.LoanPostRequestDTO;
import book.loan.system.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books/loan")
@RequiredArgsConstructor
public class LoanController {
    @Autowired
    private LoanService loanService;

    @GetMapping(path = "/find")
    public ResponseEntity<Page<Loan>> findAllLoans(Pageable pageable){
        return ResponseEntity.ok(loanService.findAllLoans(pageable));
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Loan> findLoanById(@PathVariable Long id){
        return ResponseEntity.ok(loanService.findLoanByIDorThrows404(id));
    }

    @DeleteMapping(path = "/return/book")
    public ResponseEntity<Loan> deleteLoan(@RequestBody @Valid LoanDeleteRequestDTO loanDeletePostDTO){
        loanService.deleteLoan(loanDeletePostDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/rent")
    public ResponseEntity<Loan> rent(@RequestBody @Valid LoanPostRequestDTO loanPostRequestDTO){
        return new ResponseEntity<>(loanService.createLoan(loanPostRequestDTO), HttpStatus.CREATED);
    }
}
