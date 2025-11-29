package com.trustify.trustify.controller;

import com.trustify.trustify.service.RedisService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final RedisService redisService;

    @PostMapping("/exchange-token")
    public ResponseEntity<TokenResponse> exchangeToken(
            @RequestBody StateRequest request,
            HttpServletResponse response) {

        String jwtToken = redisService.getAndRemoveStateToken(request.getState());

        if (jwtToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new TokenResponse("", "Invalid or expired state code"));
        }

        // Gửi token qua HttpOnly Cookie
        Cookie jwtCookie = new Cookie("access_token", jwtToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(3600);
        jwtCookie.setAttribute("SameSite", "None");
        response.addCookie(jwtCookie);

        return ResponseEntity.ok(new TokenResponse(jwtToken, "Token set successfully"));
    }

    @Data
    public static class StateRequest {
        private String state;
    }

    @Data
    @AllArgsConstructor
    public static class TokenResponse {
        private String token;
        private String error;
    }
}
