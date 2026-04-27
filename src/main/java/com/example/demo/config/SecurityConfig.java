package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((requests) -> requests
						// 「登録画面」と「掲示板一覧」と「静的ファイル」はログインなしでOK
						.requestMatchers("/register", "/talent/list", "/css/**", "/js/**").permitAll()

						// 「投稿画面(/talent)」や「編集・削除」はログインが必要
						.requestMatchers("/talent/edit/**", "/talent/delete/**").hasRole("ADMIN")
						.anyRequest().authenticated())
				.formLogin((form) -> form
						.defaultSuccessUrl("/talent/list", true)
						.permitAll())
				.logout((logout) -> logout
						.logoutSuccessUrl("/login?logout")
						.permitAll());

		return http.build();
	}

	/**
	 * データベース(H2)からユーザー情報を取得するように変更
	 * Spring Bootが自動で用意するDataSourceを引数で受け取ります
	 */
	// メソッド名を userDetailsManager にし、型も具体的にします
	@Bean
	public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
	    return new JdbcUserDetailsManager(dataSource);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}