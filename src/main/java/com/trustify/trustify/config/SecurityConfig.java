// java
package com.trustify.trustify.config;

import com.trustify.trustify.dto.Req.OAuth2UserInfo;
import com.trustify.trustify.entity.User;
import com.trustify.trustify.filter.JwtAuthenticationFilter;
import com.trustify.trustify.service.OAuth2Service;
import com.trustify.trustify.service.RedisService;
import com.trustify.trustify.service.Customer.UserService;
import com.trustify.trustify.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.UUID;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final OAuth2Service oAuth2Service;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // nếu là API, thường disable CSRF; tuỳ nhu cầu
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**", "/oauth2/**", "/login/**", "/api/companies/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                .successHandler(customOAuth2SuccessHandler())

        );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "https://headlong-noncongregative-chantell.ngrok-free.dev",
                "http://localhost:3000",
                "https://trustify-gux53e1bo-vwthu-22s-projects.vercel.app/"
        ));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationSuccessHandler customOAuth2SuccessHandler() {
        return (request, response, authentication) -> {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

            // Extract registrationId từ request path
            String requestUri = request.getRequestURI();
            String registrationId = requestUri.contains("google") ? "google" : "facebook";

            // Xử lý user và tạo token
            OAuth2UserInfo userInfo = oAuth2Service.extractUserInfo(oauth2User, registrationId);

            // Lưu user vào db và tạo token
            User user = userService.createOrUpdateUser(userInfo);
            String token = jwtUtil.generateToken(user);

            String stateCode = UUID.randomUUID().toString();
            redisService.saveStateCode(stateCode, token);

            response.sendRedirect("https://trustify-gux53e1bo-vwthu-22s-projects.vercel.app/auth/callback?state=" + stateCode);
        };
    }
}
