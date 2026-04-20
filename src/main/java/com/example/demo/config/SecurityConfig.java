package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                // ① 管理者(ADMIN)だけがアクセスできるURL
                .requestMatchers("/talent/edit/**", "/talent/delete/**").hasRole("ADMIN")
                // ② それ以外はログインしていればOK
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .defaultSuccessUrl("/talent/list", true)
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // 暗号化マシンを用意
        PasswordEncoder encoder = passwordEncoder();
        
        // 管理者
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("1234")) // 半角カッコに修正
                .roles("ADMIN")
                .build();

        // 一般ユーザー
        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("1111")) // 半角カッコに修正
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}