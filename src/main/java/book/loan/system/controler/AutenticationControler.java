package book.loan.system.controler;

import book.loan.system.request.BookLoanUserPostRequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
@RequestMapping("books/V1.1/Login")
public class AutenticationControler {
    @Autowired
    private AuthenticationManager manager;


    @PostMapping
    public ResponseEntity login(@RequestBody @Valid BookLoanUserPostRequestBody body){
        var token = new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());
        var authentication = manager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
