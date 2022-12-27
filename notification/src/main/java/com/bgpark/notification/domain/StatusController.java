package com.bgpark.notification.domain;

import io.micrometer.core.annotation.Timed;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {

    private final Environment env;

    @GetMapping("/health_check")
    @Timed(value="status.health_check", longTask=true)
    public String status() {
        return String.format("it's working in notification service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration=" + env.getProperty("token.expiration_time")
                + ", slack token=" + env.getProperty("slack.chat.token"));
    }

    @GetMapping("/error")
    public void error() {
        try {
            throw new Exception("This is a test");
        } catch (Exception e) {
            Sentry.captureException(e);
        }
    }
}
