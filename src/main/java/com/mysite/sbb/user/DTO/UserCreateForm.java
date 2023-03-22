package com.mysite.sbb.user.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min=3, max=25)
    @NotEmpty(message = "ID를 입력해주세요.")
    String username;

    @NotEmpty(message = " 비밀번호를 입력해주세요.")
    String password1;

    @NotEmpty(message = " 비밀번호 확인을 입력해주세요.")
    String password2;

    @NotEmpty(message = " 이메일을 입력해주세요.")
    String email;
}
