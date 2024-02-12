package com.ele.service.auth;

import com.ele.config.SecurityUtil;
import com.ele.domain.cart.Cart;
import com.ele.domain.member.Member;
import com.ele.exception.member.UsernameExistException;
import com.ele.jwt.AuthTokenGenerator;
import com.ele.jwt.AuthTokens;
import com.ele.repository.cart.CartRepository;
import com.ele.repository.member.MemberRepository;
import com.ele.request.member.LoginRequest;
import com.ele.request.member.MemberCreateRequest;
import com.ele.response.member.MemberCreateResponse;
import com.ele.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

import static com.ele.domain.member.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AuthTokenGenerator authTokenGenerator;
    private final RedisService redisService;
    private final CartRepository cartRepository;

    @Transactional
    public MemberCreateResponse saveMember(MemberCreateRequest request) {

        Optional<Member> optionalMember = memberRepository.findByUsername(request.getUsername());
        if (optionalMember.isPresent()) {
            throw new UsernameExistException();
        }
        String encodePassword = passwordEncoder.encode(request.getPassword());
        Member member = memberRepository.save(Member.builder()
                .username(request.getUsername())
                .password(encodePassword)
                .name(request.getName())
                .phoneNum(request.getPhoneNum())
                .email(request.getEmail())
                .address(request.getAddress())
                .role(USER)
                .cart(cartRepository.save(Cart.createCart()))
                .build());
        return MemberCreateResponse.toSave(member);
    }

    @Transactional
    public AuthTokens login(LoginRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken = request.getAuthenticationToken();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        AuthTokens authToken = authTokenGenerator.generate(authentication);

        redisService.setValues(authentication.getName(), authToken.getRefreshToken(), Duration.ofMillis(AuthTokenGenerator.REFRESH_TOKEN_EXPIRE_TIME));
        return authToken;
    }

    @Transactional
    public void logout(String accessToken, String refreshToken) {

        if (redisService.checkValues(refreshToken)) {
            redisService.deleteValues(String.valueOf(SecurityUtil.CurrentMemberId()));
            redisService.setValues(accessToken, "logout", Duration.ofMillis(AuthTokenGenerator.ACCESS_TOKEN_EXPIRE_TIME));
        }
    }
}




























