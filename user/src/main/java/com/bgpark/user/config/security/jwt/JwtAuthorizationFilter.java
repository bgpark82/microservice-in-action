package com.bgpark.user.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bgpark.user.domain.user.User;
import com.bgpark.user.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * 로그인한 사용자가 요청한 헤더에 Authorization을 확인하여 데이터를 처리
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("jwt authorization filter");

        String authorization = request.getHeader("Authorization");
        log.info("authorization header={}", authorization);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authorization.replace("Bearer ", "");
        log.info("authorization token={}", token);

        Long userId = JWT.require(Algorithm.HMAC512("secret"))
                .build()
                .verify(token)
                .getClaim("id").asLong();
        log.info("requested userId={}", userId);

        if (userId != null) {
            Optional<User> savedUser = userRepository.findById(userId);
            if (savedUser.isPresent()) {
                log.info("registered user={}", savedUser);
                PrincipalDetail principal = new PrincipalDetail(savedUser.get());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
                log.info("jwt authentication token={}", authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                chain.doFilter(request,response);
            }
        }
    }
}
