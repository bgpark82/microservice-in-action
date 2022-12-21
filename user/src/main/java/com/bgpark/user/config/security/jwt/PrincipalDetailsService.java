package com.bgpark.user.config.security.jwt;

import com.bgpark.user.domain.user.User;
import com.bgpark.user.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("사용자 검증..");
        Optional<User> user = userRepository.findByEmail(username);
        log.info("user={}", user);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Can not find username");
        }
        return new PrincipalDetail(user.get());
//        return new org.springframework.security.core.userdetails.User(
//                user.get().getEmail(), user.get().getPassword(), user.get().getRoles());
    }
}
