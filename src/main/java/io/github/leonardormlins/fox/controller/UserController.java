package io.github.leonardormlins.fox.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.leonardormlins.fox.model.User;
import io.github.leonardormlins.fox.service.UserService;

@CrossOrigin
@RequestMapping("api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    @JsonView(User.Token.View.class)
    public User.Token login(@RequestBody final User user) throws JsonProcessingException, AuthenticationException {
        return userService.login(user);
    }

    @PostMapping("register")
    @JsonView(User.Token.View.class)
    public User.Token register(@RequestBody final User user)
            throws JsonProcessingException, AuthenticationException {
        return userService.register(user);
    }
    
    @PostMapping("follow/{name}")
    @JsonView(User.Token.View.class)
    public void follow(@PathVariable("name") final String followed, @RequestBody final User user)
            throws JsonProcessingException, AuthenticationException {
        userService.follow(followed, user);
    }
    
    @PostMapping("updatePhoto/{user}")
    @JsonView(User.Token.View.class)
    public void updatePhoto(@RequestBody final User link, @PathVariable("user") final String user)
            throws JsonProcessingException, AuthenticationException {
        userService.updatePhoto(link, user);
    }
    
    @GetMapping("{name}")
    @JsonView(User.View.class)
    public Optional<User> findByAuthor(@PathVariable("name") final String name) {
        return userService.findByName(name);
    }
    
    @GetMapping
    @JsonView(User.View.class)
    public Iterable<User> findAll() {
        return userService.findAll();
    }
    
    @GetMapping("followed/{name}")
    @JsonView(User.View.class)
    public Iterable<User> findFollowed(@PathVariable("name") final String name) {
        return userService.findFollowed(name);
    }

}