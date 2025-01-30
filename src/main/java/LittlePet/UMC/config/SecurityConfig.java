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
    private final OAuth2LoggingFilter oAuth2LoggingFilter;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomSuccessHandler customSuccessHandler, JwtUtil jwtUtil,OAuth2LoggingFilter oAuth2LoggingFilter) {

        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
        this.jwtUtil = jwtUtil;
        this.oAuth2LoggingFilter = oAuth2LoggingFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 1. CORS 설정
        http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(Arrays.asList(
                        "http://localhost:3000",
                        "https://umclittlepet.shop",// 로컬 개발 환경
                        "http://54.180.205.177:8080" // EC2 서버 환경
                ));
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
                        .requestMatchers(
                                "/",
                                "/docs/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/auth/login",
                                "/auth/sign-in",
                                "/quiz",
                                "/users/**",
                                "/animal-categories",
                                "/animal-categories/**",
                                "/hospitals",
                                "/hospitals/**",
                                "/community",
                                "/community/**",
                                "/challenger",
                                "/health/records",
                                "/health/records/**",
                                "/badge",
                                "/badge/**",
                                "/oauth2/authorization/**"
                        ).permitAll()
                        .anyRequest().authenticated());

        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.addFilterBefore(oAuth2LoggingFilter, OAuth2LoginAuthenticationFilter.class);

        return http.build();
    }
}