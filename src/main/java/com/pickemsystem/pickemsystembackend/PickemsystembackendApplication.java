package com.pickemsystem.pickemsystembackend;

import com.pickemsystem.pickemsystembackend.security.EncoderManager;
import com.pickemsystem.pickemsystembackend.services.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class PickemsystembackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PickemsystembackendApplication.class, args);
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserServiceImpl();
	}
}
