package br.appAuth.appAuthentication.Controller;

import br.appAuth.appAuthentication.model.User;
import br.appAuth.appAuthentication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody CreateUserDto createUserDto){
        var userId = userService.registerUser(createUserDto);
        return ResponseEntity.created(URI.create("/users/" + userId.toString())).build();

    }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") String userId){
        var user = userService.getUserById(userId);
         if (user.isPresent()){
             return ResponseEntity.ok(user.get());
         }else {
             return ResponseEntity.notFound().build();
         }
    }
    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        var users = userService.listUsers();

        return ResponseEntity.ok(users);
    }

    @PutMapping
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId,@RequestBody UpdateUserDto updateUserDto) {
        userService.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId){
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
