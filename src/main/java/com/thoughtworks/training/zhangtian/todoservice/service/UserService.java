package com.thoughtworks.training.zhangtian.todoservice.service;

import com.thoughtworks.training.zhangtian.todoservice.model.User;
import com.thoughtworks.training.zhangtian.todoservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Value("${private.password}")
    private String privatePassword;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public Integer create(User user) {
        String password = user.getPassword();

        user.setPassword(encoder.encode(password));

        return userRepository.save(user).getId();
    }

    public User getOne(Integer id) {
        return userRepository.findOne(id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Boolean validate(User user) {
        Optional<User> optionalUser = userRepository.findOneByName(user.getName());
        return optionalUser.filter(u -> encoder.matches(user.getPassword(), u.getPassword())).isPresent();
    }

    public User validateLogin(User user) {
        Optional<User> optionalUser = userRepository.findOneByName(user.getName());
        if (optionalUser.filter(u -> encoder.matches(user.getPassword(), u.getPassword())).isPresent()) {
            return optionalUser.get();
        }

        return null;
    }


    public User getUserByName(String name) {

        Optional<User> optionalUser = userRepository.findOneByName(name);

        return optionalUser.orElse(null);

    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        return userRepository.findOne(user.getId());
    }

    public Boolean validateExist(User user) {
        Optional<User> userOptional = userRepository.findOneByIdAndName(user.getId(), user.getName());
        return userOptional.isPresent();
    }

    public User validateToken(String token) throws UnsupportedEncodingException {
        Claims body = Jwts.parser()
                .setSigningKey(privatePassword.getBytes("UTF-8"))
                .parseClaimsJws(token)
                .getBody();


        int id = (int) body.get("id");
        String name = (String) body.get("name");
        User user = new User();
        user.setId(id);
        user.setName(name);
        if (validateExist(user)) {
            return user;
        } else {
            return null;
        }
    }
}
