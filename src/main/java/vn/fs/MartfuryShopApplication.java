package vn.fs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class MartfuryShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(MartfuryShopApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
}


//package vn.fs;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@SpringBootApplication
//@EnableScheduling
//public class MartfuryShopApplication {
//
//	public static void main(String[] args) {
//		// Khởi tạo một đối tượng PasswordEncoder
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//		// Tạo user với username là "imtoiteu" và password là "123"
//		String username = "imtoiteu";
//		String password = "123";
//
//		// Mã hóa password
//		String hashedPassword = passwordEncoder.encode(password);
//
//		// In ra màn hình username và password đã được mã hóa
//		System.out.println("Username: " + username);
//		System.out.println("Password: " + hashedPassword);
//
//		// Nếu bạn muốn khởi chạy ứng dụng Spring Boot, bạn có thể bỏ comment dòng sau:
//		// SpringApplication.run(MartfuryShopApplication.class, args);
//	}
//}