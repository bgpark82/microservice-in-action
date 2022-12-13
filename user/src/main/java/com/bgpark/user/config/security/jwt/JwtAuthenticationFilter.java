package com.bgpark.user.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bgpark.user.domain.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


/**
 * form 로그인을 해제한 순간부터 /login을 필터링 되지 않는다
 * 왜냐하면 form 로그인을 하는 UsernamePasswordAuthenticationFilter를 등록하지 않기때문에 matcher로 해당 경로는 필터링 되지 않는다
 * 그러면 Filter로 UsernamePasswordAuthenticationFilter를 수동으로 등록하여 처리한다.
 * 대신 AuthenticationManager가 필요하다
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("authentication filter");
        log.info(authenticationManager.toString());

        // username, password
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(request.getInputStream(), User.class);
            log.info(user.toString());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

            /** @see PrincipalDetailsService#loadUserByUsername(String) */
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            PrincipalDetail principal = (PrincipalDetail) authenticate.getPrincipal();
            log.info(principal.getUsername());
            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 인증 완료 이후 해당 메소드 실행
     * JWT 토큰을 생성하여 요청한 사용자에게 JWT 토큰 응답
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("인증 성공!");

        PrincipalDetail principal = (PrincipalDetail) authResult.getPrincipal();

        // RSA가 아닌 HMAC 방식
        String jwt = JWT.create()
                .withSubject("jwt")
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 1000 * 10))
                .withClaim("id", principal.getUser().getId()) // 비공개 Claim
                .sign(Algorithm.HMAC512("secret"));

        response.setHeader("Authorization","Bearer " + jwt);
    }
}
