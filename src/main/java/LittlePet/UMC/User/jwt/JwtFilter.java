package LittlePet.UMC.User.jwt;

import LittlePet.UMC.User.dto.CustomOAuth2User;
import LittlePet.UMC.User.dto.UserDTO;
import LittlePet.UMC.domain.enums.RoleStatus;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        // 특정 URI는 필터를 통과하도록 설정
        if (requestUri.matches("^/login(?:/.*)?$") || requestUri.matches("^/oauth2(?:/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("Incoming request URI: " + requestUri);
        System.out.println("Incoming cookies: " + Arrays.toString(request.getCookies()));

        // Authorization 쿠키 추출
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            System.out.println("No cookies found in the request");
            filterChain.doFilter(request, response);
            return;
        }

        Optional<Cookie> authCookie = Arrays.stream(cookies)
                .filter(cookie -> "Authorization".equals(cookie.getName()))
                .findFirst();

        if (authCookie.isEmpty()) {
            System.out.println("Authorization cookie not found");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authCookie.get().getValue();

        // JWT 토큰 검증
        try {
            if (jwtUtil.isExpired(token)) {
                System.out.println("Token expired");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expired");
                return;
            }

            String socialId = jwtUtil.getSocialId(token);
            String role = jwtUtil.getRole(token);

            RoleStatus roleStatus = RoleStatus.fromString(role);

            UserDTO.UserJoinDTO userDTO = UserDTO.UserJoinDTO.builder()
                    .socialId(socialId)
                    .role(roleStatus)
                    .build();

            CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    customOAuth2User,
                    null,
                    customOAuth2User.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println("Authentication successful for user: " + socialId);

        } catch (Exception e) {
            System.out.println("Error validating token: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
            return;
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}