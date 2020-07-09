package io.github.leonardormlins.fox.service;

import java.util.List;
import java.util.Optional;

import io.github.leonardormlins.fox.model.Post;

public interface PostService {

    Iterable<Post> findAll();

    Optional<Post> findById(final Long id);

    List<Post> findByAuthor(final String name);

    Post save(final Post post);

    void deleteById(final Long id);

}
