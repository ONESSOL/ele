package com.ele.domain.member;

import com.ele.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member")
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phoneNum;
    private String email;
    @Embedded
    private Address address;
    @Enumerated(STRING)
    private Role role;

    @Builder
    public Member(String username, String password, String name, String phoneNum, String email, Address address, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
        this.address = address;
        this.role = role;
    }

    protected Member() {
    }

    public void update(String phoneNum, String email, Address address) {
        this.phoneNum = phoneNum;
        this.email = email;
        this.address = address;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}



























