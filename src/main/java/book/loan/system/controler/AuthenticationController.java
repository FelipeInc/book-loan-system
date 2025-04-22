package book.loan.system.controler;

import book.loan.system.service.TokenService;
import book.loan.system.domain.APIClient;
import book.loan.system.request.LoginResponseDTO;
import book.loan.system.request.UserLoginDTO;
import book.loan.system.request.UserRegisterDTO;
import book.loan.system.service.APIClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Service
@RestController
@RequestMapping("api/v1/books/auth")
@Validated
@RequiredArgsConstructor
public class AuthenticationController {

    private final TokenService tokenService;

    private final APIClientService apiUserService;

    private final AuthenticationManager manager;

    @GetMapping(path = "/find")
    public ResponseEntity<Page<APIClient>> findUsers(Pageable pageable){
        return ResponseEntity.ok(apiUserService.findAllUsers(pageable));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid UserLoginDTO user) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(user.email(), user.password());
        var auth = manager.authenticate(usernamePassword);

        var token = tokenService.generateToken((APIClient) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<APIClient> register(@RequestBody @Valid UserRegisterDTO userRegister) {
        return new ResponseEntity<>(apiUserService.registerUser(userRegister), HttpStatus.CREATED);
    }
}
