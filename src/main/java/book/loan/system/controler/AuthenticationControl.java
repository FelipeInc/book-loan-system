package book.loan.system.controler;

import book.loan.system.config.TokenService;
import book.loan.system.domain.User;
import book.loan.system.repository.BookLoanUserRepository;
import book.loan.system.request.BookLoanUserLoginDTO;
import book.loan.system.request.BookLoanUserRegisterDTO;
import book.loan.system.request.LoginResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
@RequestMapping("auth")
public class AuthenticationControl {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private BookLoanUserRepository bookLoanUserRepository;

    @Autowired
    private AuthenticationManager manager;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid BookLoanUserLoginDTO user){
        var usernamePassword = new UsernamePasswordAuthenticationToken(user.username(), user.password());
        var auth = manager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid BookLoanUserRegisterDTO userRegister){
        if (this.bookLoanUserRepository.findByUsername(userRegister.username()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRegister.password());
        User user = new User(userRegister.name(), userRegister.username(), encryptedPassword, userRegister.authorities());

        this.bookLoanUserRepository.save(user);

        return ResponseEntity.ok().build();

    }
}
