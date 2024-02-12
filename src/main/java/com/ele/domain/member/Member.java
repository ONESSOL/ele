package com.ele.domain.member;

import com.ele.domain.BaseTimeEntity;
import com.ele.domain.cart.Cart;
import com.ele.domain.order.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

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
    @Enumerated(STRING)
    private SocialType socialType;
    private String socialId;
    @OneToOne(fetch = LAZY, cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Member(String username, String password, String name, String phoneNum, String email, Address address, Role role, SocialType socialType, String socialId, Cart cart) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
        this.address = address;
        this.role = role;
        this.socialType = socialType;
        this.socialId = socialId;
        createCart(cart);
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

    public void createCart(Cart cart) {
        this.cart = cart;
        cart.createMember(this);
    }
}



























