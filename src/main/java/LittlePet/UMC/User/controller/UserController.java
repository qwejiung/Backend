package LittlePet.UMC.User.controller;

import LittlePet.UMC.User.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private final JwtUtil jwtUtil;

    public UserController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/api/auth/status")
    public ResponseEntity<?> getAuthStatus(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    try {
                        // 토큰 검증
                        if (jwtUtil.isExpired(token)) {
                            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                    .body(Map.of("loggedIn", false, "message", "Token expired"));
                        }

                        String socialId = jwtUtil.getSocialId(token);

                        String role = jwtUtil.getRole(token);
                        // 추가 클레임 추출 (토큰에 userId, userName이 포함되어 있어야 함)
                        String userId = jwtUtil.getUserId(token);
                        String userName = jwtUtil.getUserName(token);

                        // 사용자 정보 반환
                        return ResponseEntity.ok(Map.of(
                                "loggedIn", true,
                                "user", Map.of(
                                        "socialId", socialId,
                                        "role", role,
                                        "userId", userId,
                                        "userName", userName
                                )
                        ));
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("loggedIn", false, "message", "Invalid token"));
                    }
                }
            }
        }
        return ResponseEntity.ok(Map.of("loggedIn", false, "message", "No token found"));
    }
}
