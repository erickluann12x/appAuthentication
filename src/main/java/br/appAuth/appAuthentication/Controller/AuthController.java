package br.appAuth.appAuthentication.Controller;

import br.appAuth.appAuthentication.model.User;
import br.appAuth.appAuthentication.security.JwtUtil;
import br.appAuth.appAuthentication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        User user = userService.registerUser(request.get("username"), "email", "password");
        return ResponseEntity.ok(user);

    }
    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody Map<String, String> request) {
        Optional<User> user = Optional.ofNullable(userService.findByUsername(request.get("username")));
        if (user.isPresent() && user.get().getPassword().equals(request.get("password"))){
            String token = JwtUtil.generateToken(user.get().getUsername());
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}
