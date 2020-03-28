package com.gaf.anagram;

import com.gaf.anagram.config.CorsFilter;
import com.gaf.anagram.config.SpringSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class AnagramApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnagramApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean corsFilterRegistration(){
		FilterRegistrationBean registrationBean =
				new FilterRegistrationBean(new CorsFilter());
		registrationBean.setName("CORS FILTER");
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(1);
		return registrationBean;

	}

	@Bean
	public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
		return new SpringSecurityConfig();
	}

}
