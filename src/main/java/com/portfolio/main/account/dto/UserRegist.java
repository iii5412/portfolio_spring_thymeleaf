package com.portfolio.main.account.dto;

import com.portfolio.main.account.exception.InvalidRegistUser;
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

    public boolean validate() {
        final InvalidRegistUser invalidRegistUser = new InvalidRegistUser();

        if(!StringUtils.hasText(this.loginId))
            invalidRegistUser.addValidation("loginId", "입력해주세요.");

        if(!StringUtils.hasText(username))
            invalidRegistUser.addValidation("username", "입력해주세요.");

        if(!this.loginPw1.equals(loginPw2)){
            invalidRegistUser.addValidation("loginPw1", "비밀번호가 맞지 않습니다.");
            invalidRegistUser.addValidation("loginPw2", "비밀번호가 맞지 않습니다.");
        }

        if(!invalidRegistUser.getValidation().isEmpty())
            throw invalidRegistUser;

        return true;
    }
}
