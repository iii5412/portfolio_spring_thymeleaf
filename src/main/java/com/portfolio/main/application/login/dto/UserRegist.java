package com.portfolio.main.application.login.dto;

import com.portfolio.main.application.login.exception.InvalidRegistUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Setter
@Getter
public class UserRegist {
    private String loginId;
    private String username;
    private String loginPw1;
    private String loginPw2;

    public UserRegist() {
    }

    @Builder
    public UserRegist(String loginId, String username, String loginPw1, String loginPw2) {
        this.loginId = loginId;
        this.username = username;
        this.loginPw1 = loginPw1;
        this.loginPw2 = loginPw2;

        validate();
    }

    public void validate() {
        final InvalidRegistUser invalidRegistUser = new InvalidRegistUser();

        if(!StringUtils.hasText(this.loginId))
            invalidRegistUser.addValidation("loginId", "입력해주세요.");

        if(!StringUtils.hasText(username))
            invalidRegistUser.addValidation("username", "입력해주세요.");

        if(!StringUtils.hasText(loginPw1))
            invalidRegistUser.addValidation("loginPw1", "입력해주세요.");

        if(!StringUtils.hasText(loginPw2))
            invalidRegistUser.addValidation("loginPw2", "입력해주세요.");

        if(isEqualPasswords()){
            invalidRegistUser.addValidation("loginPw1", "비밀번호가 일치하지 않습니다.");
            invalidRegistUser.addValidation("loginPw2", "비밀번호가 일치하지 않습니다.");
        }

        if(invalidRegistUser.hasErrors())
            throw invalidRegistUser;
    }

    private boolean isEqualPasswords() {
        return StringUtils.hasText(loginPw1) && StringUtils.hasText(loginPw2) && !this.loginPw1.equals(loginPw2);
    }
}
