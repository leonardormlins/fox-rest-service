package io.github.leonardormlins.fox.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.leonardormlins.fox.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    private static final String secret = "secret";

    private static ObjectMapper objectMapper;

    @Autowired
    private JwtUtil(final ObjectMapper objectMapper) {
        JwtUtil.objectMapper = objectMapper;
        JwtUtil.objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
    }

    public static String generateToken(final User user) throws JsonProcessingException {
        final String userJson = objectMapper.writerWithView(User.Token.View.class).writeValueAsString(user);

        return Jwts.builder().claim("user", userJson).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public static User parseToken(final String token) throws JsonProcessingException {
        final String userJson = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("user",
                String.class);

        return objectMapper.readerWithView(User.Token.View.class).forType(User.class).readValue(userJson);
    }

}