package com.bgpark.notification;

import com.bgpark.notification.domain.naver.mail.EmailEvent;
import com.bgpark.notification.domain.naver.sms.SmsEvent;
import com.bgpark.notification.domain.slack.SlackEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding({
		SlackEvent.class,
		SmsEvent.class,
		EmailEvent.class
})
@SpringBootApplication
public class NotificationApplication  {

	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}

}
