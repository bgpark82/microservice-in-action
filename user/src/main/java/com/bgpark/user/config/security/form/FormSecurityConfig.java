package com.bgpark.user.config.security.form;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
//@Configuration
@RequiredArgsConstructor
public class FormSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService detailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}1234").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /** @see UsernamePasswordAuthenticationFilter#doFilter(ServletRequest, ServletResponse, FilterChain) */
        http.authorizeRequests()
                        .anyRequest().authenticated();

        /** @see UsernamePasswordAuthenticationFilter#doFilter(ServletRequest, ServletResponse, FilterChain) */
        http.formLogin()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        log.info("인증 성공={}", authentication);
                        response.sendRedirect("/user");
                    }
                }).failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        log.info("인증 실패={}", exception);
                    }
                });

        /** @see RememberMeAuthenticationFilter#doFilter(ServletRequest, ServletResponse, FilterChain) */
        http.rememberMe()
                .userDetailsService(detailsService)
                .alwaysRemember(false);

        /** @see LogoutFilter#doFilter(ServletRequest, ServletResponse, FilterChain) */
        http.logout()
                .deleteCookies("JSESSIONID","remember-me")
//                .addLogoutHandler(new LogoutHandler() {
//                    @Override
//                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//                        log.info("로그아웃 처리={}", authentication);
//                        HttpSession session = request.getSession();
//                        session.invalidate();
//                        Cookie[] cookies = request.getCookies();
//                        for (Cookie c : cookies) {
//                            c.setMaxAge(0);
//                        }
//                    }
//                })
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                        log.info("logout={}", authentication);
                    }
                })
                ;
    }
}
