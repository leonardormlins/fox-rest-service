package io.github.leonardormlins.fox.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.github.leonardormlins.fox.model.User;

public class JwtAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            final HttpServletRequest httpRequest = (HttpServletRequest) request;
            final HttpServletResponse httpResponse = (HttpServletResponse) response;

            final String token = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);

            try {
                if (token != null) {
                    
                    final User user = JwtUtil.parseToken(token.substring(7));

                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                            user.getUsername(), user.getPassword(), user.getAuthorities()));
                }

                chain.doFilter(request, response);
            } catch (final Throwable throwable) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, throwable.getMessage());
            }
        } else {
            chain.doFilter(request, response);
        }
    }

}