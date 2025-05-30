package book.loan.system.controler;

import book.loan.system.domain.APIClient;
import book.loan.system.request.APIClientLoginRequestDTO;
import book.loan.system.request.APIClientRegisterRequestDTO;
import book.loan.system.request.LoginResponseDTO;
import book.loan.system.service.APIClientService;
import book.loan.system.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/v1/books/auth")
@Validated
@RequiredArgsConstructor
public class AuthenticationController {

    private final TokenService tokenService;

    private final APIClientService apiClientService;

    private final AuthenticationManager manager;

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid APIClientLoginRequestDTO user) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(user.email(), user.password());
        var auth = manager.authenticate(usernamePassword);

        var token = tokenService.generateToken((APIClient) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<APIClient> register(@RequestBody @Valid APIClientRegisterRequestDTO userRegister) {
        return new ResponseEntity<>(apiClientService.registerUser(userRegister), HttpStatus.CREATED);
    }
}
