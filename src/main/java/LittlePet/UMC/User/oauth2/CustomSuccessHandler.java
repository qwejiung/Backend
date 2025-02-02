package LittlePet.UMC.User.oauth2;

import LittlePet.UMC.User.dto.Login.CustomOAuth2User;
import LittlePet.UMC.User.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    public CustomSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("[DEBUG] Authentication success for user: " + authentication.getName());

        try {
            // 사용자 정보 가져오기
            Object principal = authentication.getPrincipal();

            String socialId;
            String role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("USER"); // 기본 권한


            if (principal instanceof CustomOAuth2User) {
                // Naver 처리
                CustomOAuth2User customUserDetails = (CustomOAuth2User) principal;
                socialId = customUserDetails.getSocialId();
            } else if (principal instanceof DefaultOidcUser) {
                // Google 처리
                DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
                role = "USER";
                socialId = oidcUser.getSubject(); // Google 사용자 ID
                System.out.println("[DEBUG] Google attributes: " + oidcUser.getAttributes());
            } else {
                throw new IllegalArgumentException("[ERROR] Unsupported principal type: " + principal.getClass().getName());
            }



            System.out.println("[DEBUG] Social ID: " + socialId);
            System.out.println("[DEBUG] Role: " + role);


            // JWT 생성
            String token = jwtUtil.createJwt(socialId, role, 60 * 60 * 24 * 1000L); // 1일 유효
            System.out.println("[DEBUG] Generated JWT: " + token);

            // JWT를 쿠키에 추가
            Cookie jwtCookie = createCookie("Authorization", token);
            response.addCookie(jwtCookie);
            System.out.println("[DEBUG] JWT cookie added: " + jwtCookie.getValue());

            String setCookieHeader = response.getHeader("Set-Cookie");
            // "Authorization=xxxx; Path=/; HttpOnly; Max-Age=86400"

            setCookieHeader = setCookieHeader + "; SameSite=None";
            response.setHeader("Set-Cookie", setCookieHeader);

//            response.setHeader("Set-Cookie",
//                    "Authorization=" + token + "; Path=/; HttpOnly; Secure; SameSite=None");

            // 클라이언트로 리다이렉트
            response.sendRedirect("http://localhost:5173/");
        } catch (ExpiredJwtException e) {
            System.err.println("[ERROR] JWT expired. Refreshing token...");
            //handleExpiredJwt(response, authentication);
        } catch (Exception e) {
            System.err.println("[ERROR] Authentication error: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
        }
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 24); // 1일
        cookie.setHttpOnly(true);
        cookie.setSecure(false);// JavaScript에서 접근 금지
        cookie.setPath("/");
//        cookie.setDomain("localhost"); // Domain 설정

        System.out.println("[DEBUG] Created cookie: " + key + " = " + value);// 모든 경로에서 쿠키 사용 가능
        return cookie;
    }
}