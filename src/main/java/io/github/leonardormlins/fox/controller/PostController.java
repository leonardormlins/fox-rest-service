package io.github.leonardormlins.fox.controller;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.leonardormlins.fox.model.Post;
import io.github.leonardormlins.fox.service.PostService;

@CrossOrigin
@RequestMapping("api/post")
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    @JsonView(Post.View.class)
    public Iterable<Post> findAll() {
        return postService.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Post.View.class)
    public Post findById(@PathVariable("id") final Long id) {
        return postService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("user/{name}")
    @JsonView(Post.View.class)
    public List<Post> findByAuthor(@PathVariable("name") final String name) {
        return postService.findByAuthor(name);
    }

    @PostMapping
    @JsonView(Post.View.class)
    public Post save(@RequestBody final Post post) {
        return postService.save(post);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") final Long id) {
        postService.deleteById(id);
    }

}