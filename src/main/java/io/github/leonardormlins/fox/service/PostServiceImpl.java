package io.github.leonardormlins.fox.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.leonardormlins.fox.model.Post;
import io.github.leonardormlins.fox.model.User;
import io.github.leonardormlins.fox.repository.PostRepository;
import io.github.leonardormlins.fox.security.SecurityUtil;

@Service
public class PostServiceImpl implements PostService {


    @Autowired
    private PostRepository postRepository;

    @PreAuthorize("isAuthenticated()")
    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }

    @PreAuthorize("isAuthenticated()")
    public Optional<Post> findById(final Long id) {
        return postRepository.findById(id);
    }

    @PreAuthorize("permitAll()")
    public List<Post> findByAuthor(final String name) {
        return postRepository.findByAuthor(name);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public Post save(final Post post) {
        final User user = SecurityUtil.getAuthenticatedUser();

        post.setAuthor(user);

        return postRepository.save(post);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(final Long id) {
        postRepository.deleteById(id);
    }


}
