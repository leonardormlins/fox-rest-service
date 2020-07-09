package io.github.leonardormlins.fox.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import io.github.leonardormlins.fox.model.User;
import io.github.leonardormlins.fox.service.UserService;

@Component
public class SecurityUtil {

    private static UserService userService;

    @Autowired
    public void setUserService(final UserService userService) {
        SecurityUtil.userService = userService;
    }

    private SecurityUtil() {
        // Empty.
    }

    public static User getAuthenticatedUser() {
        final String name = getAuthenticatedUserName();

        return userService.findByName(name).orElseThrow(() -> new UsernameNotFoundException(name));
    }

    public static String getAuthenticatedUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}