package LittlePet.UMC.config;



import LittlePet.UMC.User.jwt.JwtFilter;
import LittlePet.UMC.User.jwt.JwtUtil;
import LittlePet.UMC.User.oauth2.CustomSuccessHandler;
import LittlePet.UMC.User.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtUtil jwtUtil;
    private final CustomSuccessHandler customSuccessHandler;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomSuccessHandler customSuccessHandler, JwtUtil jwtUtil) {

        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 1. CORS 설정
        http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                //configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080"));
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

         http
                 .addFilterAfter(new JwtFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class);

        // 6. OAuth2 로그인 설정
//        http.oauth2Login(oauth2 -> oauth2
//                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
//                .successHandler(customSuccessHandler)
//        );
        http
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)


                );

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/docs/**","/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated());

        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );


        return http.build();
    }
}