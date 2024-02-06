package com.ele.request.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordChangeRequest {

    private String beforePassword;
    private String newPassword;
    private String checkNewPassword;

}
