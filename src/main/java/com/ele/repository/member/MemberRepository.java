package com.ele.repository.member;

import com.ele.domain.member.Member;
import com.ele.domain.member.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);
    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
    Optional<Member> findBySocialId(String socialId);
}
