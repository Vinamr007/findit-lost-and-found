
package com.findit.findit.service;

import com.findit.findit.dto.UserLoginRequest;
import com.findit.findit.dto.UserRegistrationRequest;
import com.findit.findit.entity.User;
import com.findit.findit.exception.EmailAlreadyExistsException;
import com.findit.findit.exception.InvalidCredentialsException;
import com.findit.findit.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;

    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // for user Registeration

    public void registerUser(UserRegistrationRequest request){

        if(emailExists(request.getEmail())){
            throw new EmailAlreadyExistsException("email already exists . please login insted with correct password");

        }

            User user = new User();

            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole("USER");

            userRepository.save(user);


    }

    // login cheak

    public boolean loginUser(UserLoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(), user.getPassword())) {

            throw new InvalidCredentialsException("Invalid email or password");
        }

        return true;
    }


}
