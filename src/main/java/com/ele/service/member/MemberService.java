package com.ele.service.member;

import com.ele.domain.member.Member;
import com.ele.exception.member.CheckAgainNewPasswordException;
import com.ele.exception.member.MemberNotFoundException;
import com.ele.exception.member.PasswordMisMatchException;
import com.ele.repository.member.MemberRepository;
import com.ele.request.member.MemberUpdateRequest;
import com.ele.request.member.PasswordChangeRequest;
import com.ele.response.member.MemberDetailResponse;
import com.ele.response.member.MemberUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public MemberDetailResponse myInfo(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return MemberDetailResponse.toSave(member);
    }

    @Transactional
    public MemberUpdateResponse update(Long memberId, MemberUpdateRequest request) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        member.update(request.getPhoneNum(), request.getEmail(), request.getAddress());
        return MemberUpdateResponse.toSave(member);
    }

    @Transactional
    public void changePassword(Long memberId, PasswordChangeRequest request) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        if(passwordEncoder.matches(request.getBeforePassword(), member.getPassword())) {
            if(request.getCheckNewPassword().equals(request.getNewPassword())) {
                String encodePassword = passwordEncoder.encode(request.getNewPassword());
                member.changePassword(encodePassword);
            } else {
                throw new CheckAgainNewPasswordException();
            }
        } else {
            throw new PasswordMisMatchException();
        }
    }

    @Transactional
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}


























