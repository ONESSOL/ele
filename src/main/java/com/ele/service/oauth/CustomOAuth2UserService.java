package com.ele.service.oauth;

import com.ele.domain.member.Member;
import com.ele.domain.member.SocialType;
import com.ele.oauth.CustomOAuth2User;
import com.ele.oauth.OAuthAttributes;
import com.ele.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

import static com.ele.domain.member.SocialType.NAVER;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private static final String KAKAO = "kakao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);
        Member member = getMember(socialType, extractAttributes);

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                attributes,
                userNameAttributeName,
                String.valueOf(member.getId()),
                member.getRole()
        );
    }

    private SocialType getSocialType(String registrationId) {
        if(KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        }
        return NAVER;
    }

    private Member getMember(SocialType socialType, OAuthAttributes oAuthAttributes) {
        Member member = memberRepository.findBySocialTypeAndSocialId(socialType, oAuthAttributes.getOAuth2UserInfo().getId())
                .orElse(null);
        if(member == null) {
            return saveMember(socialType, oAuthAttributes);
        }
        return member;

    }

    private Member saveMember(SocialType socialType, OAuthAttributes oAuthAttributes) {
        Member member = oAuthAttributes.toEntity(socialType, oAuthAttributes.getOAuth2UserInfo());
        return memberRepository.save(member);
    }
}


















