package br.appAuth.appAuthentication.Controller;

import br.appAuth.appAuthentication.model.User;
import br.appAuth.appAuthentication.repository.UserRepository;
import br.appAuth.appAuthentication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository,UserService userService){
        this.userRepository = userRepository;
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user){
        Object userSave = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body((User) userSave);

    }
    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok("Usuario deletado");
    }
}
