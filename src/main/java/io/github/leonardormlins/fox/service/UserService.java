package io.github.leonardormlins.fox.service;

import java.util.Optional;

import org.springframework.security.core.AuthenticationException;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.leonardormlins.fox.model.User;

public interface UserService {

    User.Token login(final User user) throws JsonProcessingException, AuthenticationException;

    User.Token register(final User user) throws JsonProcessingException, AuthenticationException;
    
    void follow(final User followed) throws JsonProcessingException, AuthenticationException;

    Optional<User> findByName(final String name);
    
    Iterable<User> findFollowed(final String user);
    
    Iterable<User> findAll();
    
    void updatePhoto(final User link);

}
