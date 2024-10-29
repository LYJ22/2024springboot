package com.sample.spring.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sample.spring.security.APILoginFailHandler;
import com.sample.spring.security.APILoginSuccessHandler;
import com.sample.spring.security.filter.JWTCheckFilter;
import com.sample.spring.security.handler.CustomAccessDeniedHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
@RequiredArgsConstructor
@EnableMethodSecurity
public class CustomSecurityConfig {
	
	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info("################### security config ######################");

		http.cors(httpSecurityCorsConfigurer -> {
			httpSecurityCorsConfigurer.configurationSource(CorsConfigurationSource());
		});
		
		http.csrf(config -> config.disable());
		
		http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //세션비활성화

		
		http.formLogin(
				config->{
					config.loginPage("/api/member/login");
					config.successHandler(new APILoginSuccessHandler());	//token 발행, 200 ok
					config.failureHandler(new APILoginFailHandler());	//유저가 아닙니다!, 200 ok(오류가 아님)
				}
				);
		
		http.addFilterBefore(new JWTCheckFilter(), UsernamePasswordAuthenticationFilter.class);
		
		http.exceptionHandling(config->{config.accessDeniedHandler(new CustomAccessDeniedHandler()); });
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource CorsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD","GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization","Cache-Control","Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
	}
	
	
	
}
