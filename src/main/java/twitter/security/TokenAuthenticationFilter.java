package twitter.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import twitter.models.Token;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static twitter.security.TokenConstrains.EXPIRATION_TIME;
import static twitter.security.TokenConstrains.SECRET;

public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authManager;

    public TokenAuthenticationFilter(AuthenticationManager authManager) {
        this.authManager = authManager;
        setFilterProcessesUrl("/token");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Token token = null;

        try {
            token = new ObjectMapper().readValue(request.getInputStream(), Token.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return authManager.authenticate(new UsernamePasswordAuthenticationToken(token.getUserName(), token.getPassword(), new ArrayList<>())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));

        res.setContentType("application/json");
        res.getWriter().write(new ObjectMapper().writeValueAsString(new Token(token, EXPIRATION_TIME, "Bearer", ((User) auth.getPrincipal()).getUsername(), ((User) auth.getPrincipal()).getPassword())));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}