package com.recruiter;

import com.recruiter.repository.UserRepository;
import com.recruiter.service.FileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class RecruiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecruiterApplication.class, args);
	}
}