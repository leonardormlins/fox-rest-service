package io.github.leonardormlins.fox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import io.github.leonardormlins.fox.model.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

    @Query("SELECT p FROM Post p JOIN p.author a WHERE a.name = :name")
    List<Post> findByAuthor(@Param("name") final String name);

}