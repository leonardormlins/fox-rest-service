package io.github.leonardormlins.fox.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import io.github.leonardormlins.fox.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByName(final String name);
    
    List<User> findByNameIn(final List<String> names);

}