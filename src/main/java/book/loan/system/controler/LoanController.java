package book.loan.system.controler;

import book.loan.system.domain.Loan;
import book.loan.system.request.LoanPostRequestDTO;
import book.loan.system.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/books/loan")
@Log4j2
@RequiredArgsConstructor
public class LoanController {
    @Autowired
    private final LoanService loanService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Loan> findLoanById(@PathVariable Long id){
        return ResponseEntity.ok(loanService.findLoanByID(id));
    }


    @PostMapping(path = "/rent")
    public ResponseEntity<Loan> rent(@RequestBody LoanPostRequestDTO loanPostRequestDTO){
        return new ResponseEntity<>(loanService.createLoan(loanPostRequestDTO), HttpStatus.CREATED);
    }
}
