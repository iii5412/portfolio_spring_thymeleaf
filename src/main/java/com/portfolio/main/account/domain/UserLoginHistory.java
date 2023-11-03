package com.portfolio.main.account.domain;

import com.portfolio.main.account.login.service.LoginActionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_login_history")
@Getter
@NoArgsConstructor
public class UserLoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "action_type")
    @Enumerated(EnumType.STRING)
    private LoginActionType actionType;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "device_info")
    private String deviceInfo;

    @Column(name = "action_time", nullable = false, insertable = false)
    private LocalDateTime actionTime;

    public UserLoginHistory(String loginId, LoginActionType actionType, String ipAddress, String deviceInfo) {
        this.loginId = loginId;
        this.actionType = actionType;
        this.ipAddress = ipAddress;
        this.deviceInfo = deviceInfo;
    }

    public void changeActionType(LoginActionType actionType) {
        this.actionType = actionType;
    }
}
