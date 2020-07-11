package io.github.leonardormlins.fox.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.leonardormlins.fox.model.User;
import io.github.leonardormlins.fox.repository.UserRepository;
import io.github.leonardormlins.fox.security.JwtUtil;
import io.github.leonardormlins.fox.security.SecurityUtil;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PreAuthorize("permitAll()")
    public User.Token login(final User user) throws JsonProcessingException, AuthenticationException {
        final Authentication authentication = new UsernamePasswordAuthenticationToken(user.getName(),
                user.getPassword());
        
        final User authenticatedUser = (User) authenticationManager.authenticate(authentication).getPrincipal();

        final String token = JwtUtil.generateToken(authenticatedUser);

        return new User.Token(authenticatedUser, token);
    }

    @PreAuthorize("permitAll()")
    public User.Token register(final User user)
            throws JsonProcessingException, AuthenticationException {
        return login(save(user));
    }
   
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public void follow(final User followed) {
    	final User userFollowing = SecurityUtil.getAuthenticatedUser();
    	
    	final Optional<User> found = userRepository.findByName(followed.getName());
    	
    	final List<User> followingList = userFollowing.getFollowed();
    	
    	if(found != null) {
        	followingList.add(found.get());
        	userFollowing.setFollowed(followingList);
    		userRepository.save(userFollowing);
    	}
    }
    
    @PreAuthorize("isAuthenticated()")
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    private User save(final User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByName(final String name) {
        return userRepository.findByName(name);
    }
    
    public Iterable<User> findFollowed(final String name) {
        final Optional<User> found = userRepository.findByName(name);
    	
    	return found.get().getFollowed();
    }
    
    public void updatePhoto(final User link) {
    	final User user = SecurityUtil.getAuthenticatedUser();
    	
    	user.setProfilePhoto(link.getProfilePhoto());
    	
    	userRepository.save(user);
    }

    public UserDetails loadUserByUsername(final String name) throws UsernameNotFoundException {
        return findByName(name).orElseThrow(() -> new UsernameNotFoundException(name));
    }

}