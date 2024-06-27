package edu.sharif.webproject;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

import java.util.TimeZone;

@SpringBootApplication
@EnableCaching
@EnableRetry
public class WebProjectApplication {

	@Value("${application.timezone:UTC}")
	private static String applicationTimeZone = "UTC";

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone(applicationTimeZone));
		SpringApplication.run(WebProjectApplication.class, args);
	}

}
