package com.example.demo.config;


//package com.example.first.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import com.example.demo.token.jwtUtil;
 
@Configuration
public class JwtConfig {
	
	@Bean
	public jwtUtil jwtUtil() {
		return new jwtUtil();
	}
 
}
 