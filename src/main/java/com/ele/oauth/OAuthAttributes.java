package com.ele.oauth;

import com.ele.domain.cart.Cart;
import com.ele.domain.member.Member;
import com.ele.domain.member.Role;
import com.ele.domain.member.SocialType;
import com.ele.oauth.userinfo.KakaoOAuth2UserInfo;
import com.ele.oauth.userinfo.OAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

import static com.ele.domain.member.Role.GUEST;
import static com.ele.domain.member.SocialType.KAKAO;

@Getter
public class OAuthAttributes {

    private String usernameAttributeKey;
    private OAuth2UserInfo oAuth2UserInfo;

    @Builder
    public OAuthAttributes(String usernameAttributeKey, OAuth2UserInfo oAuth2UserInfo) {
        this.usernameAttributeKey = usernameAttributeKey;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    protected OAuthAttributes() {
    }

    public static OAuthAttributes of(SocialType socialType, String usernameAttributeName, Map<String, Object> attributes) {

        if(socialType.equals(KAKAO)) {
            return ofKakao(usernameAttributeName, attributes);
        }
         return null;
    }

    public static OAuthAttributes ofKakao(String usernameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .usernameAttributeKey(usernameAttributeName)
                .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public Member toEntity(SocialType socialType, OAuth2UserInfo oAuth2UserInfo) {
        return Member.builder()
                .socialType(socialType)
                .socialId(oAuth2UserInfo.getId())
                .username(oAuth2UserInfo.getNickName())
                .email(oAuth2UserInfo.getEmail())
                .role(GUEST)
                .cart(Cart.createCart())
                .build();
    }
}























