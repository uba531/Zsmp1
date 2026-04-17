package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .anyRequest().authenticated() // すべてのページに認証が必要
            )
            .formLogin((form) -> form
                .defaultSuccessUrl("/talent/list", true) // ログイン成功後の移動先
                .permitAll()
            );
        
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // メモリ上に仮のユーザーを作成
        UserDetails user = User.builder()
            .username("admin")      // ユーザー名
            .password("{noop}1234") // パスワード（{noop}は暗号化しないという意味）
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }
}