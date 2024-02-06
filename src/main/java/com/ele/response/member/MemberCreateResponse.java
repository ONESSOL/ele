package com.ele.response.member;

import com.ele.domain.member.Address;
import com.ele.domain.member.Member;
import com.ele.domain.member.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberCreateResponse {

    private String username;
    private String password;
    private String name;
    private String phoneNum;
    private String email;
    private Address address;
    private Role role;

    public static MemberCreateResponse toSave(Member member) {

        MemberCreateResponse response = new MemberCreateResponse();
        response.setUsername(member.getUsername());
        response.setPassword(member.getPassword());
        response.setName(member.getName());
        response.setPhoneNum(member.getPhoneNum());
        response.setEmail(member.getEmail());
        response.setAddress(member.getAddress());
        response.setRole(member.getRole());
        return response;
    }
}
