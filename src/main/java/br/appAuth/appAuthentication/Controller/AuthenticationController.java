package br.appAuth.appAuthentication.Controller;

import br.appAuth.appAuthentication.dto.request.LoginRequest;
import br.appAuth.appAuthentication.dto.request.ResgisterUserRequest;
import br.appAuth.appAuthentication.dto.response.LoginResponse;
import br.appAuth.appAuthentication.dto.response.RegisterUserResponse;
import br.appAuth.appAuthentication.entities.Role;
import br.appAuth.appAuthentication.entities.User;
import br.appAuth.appAuthentication.repository.UserRepository;
import br.appAuth.appAuthentication.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;


    public AuthenticationController(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        var userNamePassword = new UsernamePasswordAuthenticationToken(request.email(),request.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        User user = (User) auth.getPrincipal();
        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/create")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody ResgisterUserRequest request) {
        User newUser = new User();
        newUser.setName(request.name());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));

        if (request.role() != null){
            newUser.setRoles(Set.of(request.role()));
        } else {
            newUser.setRoles(Set.of(Role.ROLE_USER));
        }

        userRepository.save(newUser);


        String token = this.tokenService.generateToken(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterUserResponse(newUser.getUsername(), newUser.getEmail()));
    }
}
