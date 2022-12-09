package com.bgpark.user.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("sys").password("{noop}1111").roles("SYS");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1111").roles("ADMIN","USER");
        auth.inMemoryAuthentication().withUser("user").password("{noop}1111").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 인가
        http.authorizeRequests()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/sys").hasRole("SYS")
                .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
                .anyRequest().authenticated();

        // 인증
        http.formLogin()
                .defaultSuccessUrl("/user")
                .failureUrl("/login")
                .usernameParameter("id")
                .passwordParameter("pw")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        log.info("인증 성공={}", authentication.toString());
                        response.sendRedirect("/user");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        log.info("인증 실패={}", exception.toString());
                        response.sendRedirect("/login");
                    }
                });

        // 로그아웃
        http.logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID","remeber-me")
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                        log.info("로그아웃 처리={}", authentication.toString());
                        HttpSession session = request.getSession();
                        log.info("세션={}", session.toString());
                        // 세션 무효, 인증토큰 삭제
                        session.invalidate();
                    }
                })
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        log.info("로그아웃 성공={}", authentication.toString());
                    }
                });
//        http.rememberMe()
//                .rememberMeParameter("remem")
//                .tokenValiditySeconds(3600)
//                .alwaysRemember(true)
//                .userDetailsService(userDetailsService);

        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
        ;
    }
}
