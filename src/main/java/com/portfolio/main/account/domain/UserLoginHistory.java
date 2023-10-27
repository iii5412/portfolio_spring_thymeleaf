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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "actionType")
    @Enumerated(EnumType.STRING)
    private LoginActionType actionType;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "device_info")
    private String deviceInfo;

    @Column(name = "actionTime", nullable = false)
    private LocalDateTime loginTime;

    public UserLoginHistory(User user, LoginActionType actionType, String ipAddress, String deviceInfo) {
        this.user = user;
        this.actionType = actionType;
        this.ipAddress = ipAddress;
        this.deviceInfo = deviceInfo;
    }
}
