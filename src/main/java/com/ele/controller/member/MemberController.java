package com.ele.controller.member;

import com.ele.config.SecurityUtil;
import com.ele.request.member.MemberUpdateRequest;
import com.ele.request.member.PasswordChangeRequest;
import com.ele.response.member.MemberDetailResponse;
import com.ele.response.member.MemberUpdateResponse;
import com.ele.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/my_info")
    public ResponseEntity<MemberDetailResponse> myInfo() {
        return ResponseEntity.ok(memberService.myInfo(SecurityUtil.CurrentMemberId()));
    }

    @PutMapping("/update")
    public ResponseEntity<MemberUpdateResponse> update(@RequestBody MemberUpdateRequest request) {
        return ResponseEntity.ok(memberService.update(SecurityUtil.CurrentMemberId(), request));
    }

    @PutMapping("/change_password")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeRequest request) {
        memberService.changePassword(SecurityUtil.CurrentMemberId(), request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMember() {
        memberService.deleteMember(SecurityUtil.CurrentMemberId());
        return ResponseEntity.ok().build();
    }
}
