package com.ele.config;

import com.ele.jwt.JwtAccessDeniedHandler;
import com.ele.jwt.JwtAuthenticationEntryPoint;
import com.ele.jwt.JwtTokenProvider;
import com.ele.oauth.handler.OAuth2LoginFailureHandler;
import com.ele.oauth.handler.OAuth2LoginSuccessHandler;
import com.ele.repository.member.MemberRepository;
import com.ele.service.oauth.CustomOAuth2UserService;
import com.ele.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final MemberRepository memberRepository;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("favicon.ico")
                .requestMatchers("/error")
                .requestMatchers("/index.html");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/save").permitAll();
                    auth.requestMatchers("/auth/login").permitAll();
                    auth.requestMatchers("/oauth2/login").permitAll();
                    auth.requestMatchers("/").permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(oauth -> {
                    oauth.successHandler(new OAuth2LoginSuccessHandler(memberRepository, jwtTokenProvider, redisService));
                    oauth.failureHandler(new OAuth2LoginFailureHandler());
                    oauth.userInfoEndpoint(config -> config.userService(new CustomOAuth2UserService(memberRepository)));
                })
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(STATELESS);
                })
                .headers(headers -> {
                    headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                })
                .exceptionHandling(exception -> {
                    exception.accessDeniedHandler(jwtAccessDeniedHandler);
                    exception.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                })
                .apply(new JwtSecurityConfig(jwtTokenProvider, redisService));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}























