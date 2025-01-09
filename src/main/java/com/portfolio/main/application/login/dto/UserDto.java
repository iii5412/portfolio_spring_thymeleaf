package com.portfolio.main.application.login.dto;

import com.portfolio.main.domain.model.account.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 사용자 정보를 담는 DTO 클래스
 * User 엔티티의 정보를 클라이언트에 전달하기 위한 용도로 사용
 * 
 * @see com.portfolio.main.domain.model.account.User
 */
@Getter
public class UserDto {
    private Long id;
    private String loginId;
    private String userName;
    private LocalDateTime createdAt;
    private List<UserRoleDto> userRoles = new ArrayList<>();

    public UserDto(User user) {
        this.id = user.getId();
        this.loginId = user.getLoginId();
        this.userName = user.getUserName();
        this.createdAt = user.getCreatedAt();

        if(!user.getUserRoles().isEmpty()) {
            this.userRoles = user.getUserRoles().stream().map(UserRoleDto::new).toList();
        }
    }
    /**
     * 사용자의 권한 코드 목록을 반환하는 메서드
     * 
     * @return 사용자가 가진 권한 코드 목록
     */
    public List<String> getRoleCodes() {
        return userRoles.stream()
                .map(userRole -> userRole.getRoleCode().name())
                .collect(Collectors.toList());
    }
}
