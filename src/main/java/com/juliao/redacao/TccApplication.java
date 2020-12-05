package com.juliao.redacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TccApplication {

	public static void main(String[] args) {
		/*System.out.println(new BCryptPasswordEncoder().encode("123"));
		System.out.println(new BCryptPasswordEncoder().encode("123"));*/
		SpringApplication.run(TccApplication.class, args);
	}

}
