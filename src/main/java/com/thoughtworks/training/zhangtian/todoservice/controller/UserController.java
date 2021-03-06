package com.thoughtworks.training.zhangtian.todoservice.controller;

import com.thoughtworks.training.zhangtian.todoservice.model.User;
import com.thoughtworks.training.zhangtian.todoservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${private.password}")
    private String privatePassword;

    @PostMapping("/users")
    public Integer create(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/users/{id}")
    public User getOne(@PathVariable Integer id) {
        User one = userService.getOne(id);
        return one;
    }

    @GetMapping("/users")
    public List<User> getAll() {
        List<User> users = userService.getAll();
        return users;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) throws UnsupportedEncodingException {
        user = userService.validateLogin(user);
        if (user != null) {
            return ResponseEntity.ok(user);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/users/current")
    public ResponseEntity getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PostMapping("/validate")
    public ResponseEntity validateUser(@RequestBody User user) throws UnsupportedEncodingException {
        if (userService.validateExist(user)) {
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
