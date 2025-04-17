package book.loan.system.controler;

import book.loan.system.config.TokenService;
import book.loan.system.domain.APIClient;
import book.loan.system.repository.APIClientRepository;
import book.loan.system.request.UserLoginDTO;
import book.loan.system.request.UserRegisterDTO;
import book.loan.system.request.LoginResponseDTO;
import book.loan.system.service.APIClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private APIClientRepository apiUserRepository;

    @Autowired
    private APIClientService apiUserService;

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
    public ResponseEntity<APIClient> register(@RequestBody @Valid UserRegisterDTO userRegister){
        return new ResponseEntity<>(apiUserService.registerUser(userRegister), HttpStatus.CREATED);
    }
}
