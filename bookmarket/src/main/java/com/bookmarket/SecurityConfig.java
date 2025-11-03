package com.bookmarket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // (더 이상 필요 X)

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        // [CTO 수정]
                        // AntPathRequestMatcher 대신, 더 간단하고 명확한
                        // requestMatchers("/member/signup")을 사용합니다.
                        // 이 방식은 GET, POST 등 모든 HTTP 요청을 허용합니다! (403 해결)
                        .requestMatchers("/member/signup").permitAll()

                        // 정적 파일(CSS, JS)들도 모두 허용합니다.
                        .requestMatchers("/**.css", "/**.js").permitAll()

                        // 그 외 모든 요청은 로그인이 필요합니다.
                        .anyRequest().authenticated()
                )
                .csrf((csrf) -> csrf.disable()) // CSRF 비활성화 (이전과 동일)

                // [CTO 추가]
                // '로그인' 관련 설정을 미리 추가합니다.
                // 회원가입 후 이쪽으로 연결될 겁니다.
                .formLogin((formLogin) -> formLogin
                        .loginPage("/member/login") // "우리의 커스텀 로그인 페이지 주소는 /member/login 입니다."
                        .defaultSuccessUrl("/") // "로그인 성공하면 메인페이지(/)로 보내주세요."
                        .permitAll() // "로그인 페이지는 누구나 접근할 수 있게 해주세요."
                )

                // [CTO 추가]
                // '로그아웃' 관련 설정도 미리 추가합니다.
                .logout((logout) -> logout
                        .logoutUrl("/member/logout") // "로그아웃 처리는 /member/logout 주소로 합니다."
                        .logoutSuccessUrl("/") // "로그아웃 성공하면 메인페이지(/)로 보내주세요."
                        .invalidateHttpSession(true) // "로그아웃하면 세션을 완전히 비워주세요."
                )
        ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}