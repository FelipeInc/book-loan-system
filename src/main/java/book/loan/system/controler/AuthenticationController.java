package book.loan.system.controler;

import book.loan.system.config.TokenService;
import book.loan.system.domain.APIClient;
import book.loan.system.exception.BadRequestException;
import book.loan.system.repository.APIClientRepository;
import book.loan.system.request.UserLoginDTO;
import book.loan.system.request.UserRegisterDTO;
import book.loan.system.request.LoginResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
@RequestMapping("api/v1/books/auth")
@Validated
public class AuthenticationController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private APIClientRepository bookLoanUserRepository;

    @Autowired
    private AuthenticationManager manager;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserLoginDTO user){
        var usernamePassword = new UsernamePasswordAuthenticationToken(user.email(), user.password());
        var auth = manager.authenticate(usernamePassword);

        var token = tokenService.generateToken((APIClient) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserRegisterDTO userRegister){
        if (bookLoanUserRepository.findByEmailIgnoreCase(userRegister.email()) != null){
            throw new BadRequestException("This email is already registered");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRegister.password());
        APIClient user = new APIClient(userRegister.name(), userRegister.email(), encryptedPassword, userRegister.authorities());

        this.bookLoanUserRepository.save(user);

        return ResponseEntity.ok().build();

    }
}
