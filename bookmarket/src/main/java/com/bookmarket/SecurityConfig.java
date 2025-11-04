package com.bookmarket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        // "메인 페이지(/)"와 "로그인 페이지(/member/login)"는 모두 통과
                        .requestMatchers("/", "/member/login").permitAll()

                        // 회원가입 페이지도 모두 통과
                        .requestMatchers("/member/signup").permitAll()

                        // 정적 파일(CSS, JS)들도 모두 통과
                        .requestMatchers("/**.css", "/**.js").permitAll()

                        // 그 외 모든 요청은 로그인이 필요
                        .anyRequest().authenticated()
                )
                .csrf((csrf) -> csrf.disable())

                .formLogin((formLogin) -> formLogin
                        .loginPage("/member/login") // 로그인 페이지 주소
                        .defaultSuccessUrl("/") // 로그인 성공 시 메인페이지(/)로
                        .permitAll()
                )

                .logout((logout) -> logout
                        .logoutUrl("/member/logout") // 로그아웃 처리 주소
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 메인페이지(/)로
                        .invalidateHttpSession(true) // 세션 비우기
                )
        ;
        return http.build();
    }

    // [CTO 확인]
    // 아까 지워졌던 '암호화 기계'가 바로 이 메소드입니다!
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}