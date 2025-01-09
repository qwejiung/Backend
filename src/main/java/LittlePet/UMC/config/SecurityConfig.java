package LittlePet.UMC.config;



import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 1. CORS 설정
        http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                configuration.addAllowedMethod("*");
                configuration.setAllowCredentials(true);
                configuration.addAllowedHeader("*");
                configuration.setMaxAge(3600L);
                configuration.addExposedHeader("Set-Cookie");
                configuration.addExposedHeader("Authorization");

                return configuration;
            }
        }));


        http.csrf(csrf -> csrf.disable());


        http.formLogin(formLogin -> formLogin.disable());


        http.httpBasic(httpBasic -> httpBasic.disable());

        // 5. JWTFilter 추가
        // OAuth2AuthorizationCodeGrantFilter 이후에 동작하도록 설정
        // http.addFilterAfter(new JWTFilter(jwtUtil), OAuth2AuthorizationCodeGrantFilter.class);

        // 6. OAuth2 로그인 설정
//        http.oauth2Login(oauth2 -> oauth2
//                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
//                .successHandler(customSuccessHandler)
//        );

        http.authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
        );

        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.logout(logout -> logout
                .logoutUrl("/logout") // 로그아웃 경로
                .logoutSuccessHandler((request, response, authentication) -> {
                    // 로그아웃 성공 시 처리
                    response.setStatus(200); // HTTP 상태 200
                    response.getWriter().write("Logout successful");
                })
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID", "Authorization") // 쿠키 삭제
        );

        return http.build();
    }
}