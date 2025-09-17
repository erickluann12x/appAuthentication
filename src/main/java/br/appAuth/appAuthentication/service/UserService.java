package br.appAuth.appAuthentication.service;

import br.appAuth.appAuthentication.exceptions.ResourceNotFoundException;
import br.appAuth.appAuthentication.model.User;
import br.appAuth.appAuthentication.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(String username,String email ,String password) {
        String passwordCript = passwordEncoder.encode(password);
        User user = new User(username,email,passwordCript);
        return userRepository.save(user);

    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario "+username+"não encontrado"));
    }
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado com id"));
    }
}