package com.ele.controller.auth;

import com.ele.jwt.AuthTokens;
import com.ele.jwt.JwtTokenProvider;
import com.ele.request.member.LoginRequest;
import com.ele.request.member.MemberCreateRequest;
import com.ele.response.member.MemberCreateResponse;
import com.ele.service.auth.AuthService;
import com.ele.service.redis.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @PostMapping("/save")
    public ResponseEntity<MemberCreateResponse> saveMember(@RequestBody MemberCreateRequest request) {
        return ResponseEntity.ok(authService.saveMember(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokens> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PatchMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {

        String accessToken = jwtTokenProvider.resolveToken(request);
        String refreshToken = redisService.getValues(jwtTokenProvider.extractSubject(accessToken));
        authService.logout(accessToken, refreshToken);
        return ResponseEntity.ok().build();
    }
}
