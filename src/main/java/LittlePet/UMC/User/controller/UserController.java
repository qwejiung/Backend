package LittlePet.UMC.User.controller;

import LittlePet.UMC.User.jwt.JwtUtil;
import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.User.service.UserService;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public UserController(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
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
                        // 사용자 정보 반환

                        User user = userRepository.findBySocialId(socialId);
                        if(user == null) {
                            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                    .body(Map.of("loggedIn", false, "message", "User not found"));
                        }
                        return ResponseEntity.ok(Map.of(
                                "loggedIn", true,
                                "user", Map.of(
                                        "id", user.getId(),
                                        "socialId", socialId,
                                        "role", role,
                                        "userName", user.getName()

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

    @GetMapping("/api/auth/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // 만료된 쿠키 생성 (maxAge 0)
        ResponseCookie expiredCookie = ResponseCookie.from("jwt", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)   // 운영 환경에 맞게 조정 (개발 환경에서는 false)
                .sameSite("None")  // cross-site 요청 허용
                .build();

        // 쿠키 헤더에 추가하여 클라이언트로 전송
        response.setHeader("Set-Cookie", expiredCookie.toString());
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }



}
