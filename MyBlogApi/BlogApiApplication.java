package com.example.MyBlogApi;

import com.example.MyBlogApi.entity.Role;
import com.example.MyBlogApi.entity.User;
import com.example.MyBlogApi.repository.RoleRepository;
import com.example.MyBlogApi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BlogApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);

	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User(1, "admin", passwordEncode.encode("password"), roles,"admin@gmail.com",99);

			userRepository.save(admin);
		};
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
