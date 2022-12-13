package com.bgpark.user.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    Environment env;

    public UserController(Environment env) {
        this.env = env;
    }

    @GetMapping()
    public ResponseEntity hello(HttpServletRequest request) {
        log.info("server port={}, server port={}", request.getServerPort(), env.getProperty("local.server.port"));
        return ResponseEntity.ok("hello");
    }

    @GetMapping("/header")
    public ResponseEntity hello2(@RequestHeader("user-request") String header) {
        log.info("header={}", header);
        return ResponseEntity.ok(header);
    }
}
