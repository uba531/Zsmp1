package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    /**
     * データベース(H2)からユーザー情報を取得するように変更
     * Spring Bootが自動で用意するDataSourceを引数で受け取ります
     */
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        // JdbcUserDetailsManagerは、schema.sqlで作った「users」と「authorities」テーブルを自動で読み取ります
        return new JdbcUserDetailsManager(dataSource);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}