package br.appAuth.appAuthentication.service;

import br.appAuth.appAuthentication.Controller.CreateUserDto;
import br.appAuth.appAuthentication.Controller.UpdateUserDto;
import br.appAuth.appAuthentication.entities.User;
import br.appAuth.appAuthentication.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UUID registerUser(CreateUserDto createUserDto) {

        var passwordHash = passwordEncoder.encode(createUserDto.password());

        var entity = new User(
                UUID.randomUUID(),
                createUserDto.username(),
                createUserDto.email(),
                passwordHash,
                Instant.now(),
                null);


        var userSaved = userRepository.save(entity);
        return userSaved.getUserId();
    }

    public Optional <User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto) {
        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();
            if (updateUserDto.username() !=null ) {
                user.setUsername(updateUserDto.username());
            }
            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }
    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }

}