package com.ele.oauth.handler;

import com.ele.domain.member.Member;
import com.ele.exception.member.MemberNotFoundException;
import com.ele.jwt.AuthTokenGenerator;
import com.ele.jwt.JwtTokenProvider;
import com.ele.oauth.CustomOAuth2User;
import com.ele.repository.member.MemberRepository;
import com.ele.service.redis.RedisService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;

import static com.ele.domain.member.Role.GUEST;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            if (oAuth2User.getRole().equals(GUEST)) {
                long now = new Date().getTime();
                Member member = memberRepository.findBySocialId(authentication.getName()).orElseThrow(MemberNotFoundException::new);
                String subject = String.valueOf(member.getId());
                String accessToken = jwtTokenProvider.generate(subject, new Date(now + AuthTokenGenerator.ACCESS_TOKEN_EXPIRE_TIME), authentication);
                String refreshToken = jwtTokenProvider.generate(subject, new Date(now + AuthTokenGenerator.REFRESH_TOKEN_EXPIRE_TIME), authentication);

                redisService.setValues(subject, refreshToken, Duration.ofMillis(AuthTokenGenerator.REFRESH_TOKEN_EXPIRE_TIME));
                jwtTokenProvider.accessTokenSetHeaders(accessToken, response);
                response.sendRedirect("/oauth2/login");
            } else {
                loginSuccess(response, authentication);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, Authentication authentication) {

        long now = new Date().getTime();
        String subject = authentication.getName();
        String accessToken = jwtTokenProvider.generate(subject, new Date(now + AuthTokenGenerator.ACCESS_TOKEN_EXPIRE_TIME), authentication);
        String refreshToken = jwtTokenProvider.generate(subject, new Date(now + AuthTokenGenerator.REFRESH_TOKEN_EXPIRE_TIME), authentication);
        redisService.setValues(subject, refreshToken, Duration.ofMillis(AuthTokenGenerator.REFRESH_TOKEN_EXPIRE_TIME));
        jwtTokenProvider.accessTokenSetHeaders(accessToken, response);
    }
}




















