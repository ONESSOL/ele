package com.ele.request.member;

import com.ele.domain.member.Address;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberCreateRequest {

    private String username;
    private String password;
    private String name;
    private String phoneNum;
    private String email;
    private Address address;

}
