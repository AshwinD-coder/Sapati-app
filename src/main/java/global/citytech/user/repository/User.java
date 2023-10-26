package global.citytech.user.repository;

import global.citytech.platform.common.enums.UserType;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Serdeable
@Introspected
@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserType userType;
    @DateCreated
    private Timestamp createdAt;

    @Column(name = "is_verified")
    private Boolean verifyStatus;

    @Column(name = "is_blacklist")
    private Boolean blacklistStatus;

    @Column(name = "is_active")
    private Boolean activeStatus;

    private String otp;

    public User(Long userId, String username, String password, String email, String phoneNumber, UserType userType, Timestamp createdAt, Boolean verifyStatus, Boolean blacklistStatus, Boolean activeStatus, String otp) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.createdAt = createdAt;
        this.verifyStatus = verifyStatus;
        this.blacklistStatus = blacklistStatus;
        this.activeStatus = activeStatus;
        this.otp = otp;
    }

    public User() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Boolean verifyStatus) {
        this.verifyStatus = verifyStatus;
    }
//
    public Boolean getBlacklistStatus() {
        return blacklistStatus;
    }

    public void setBlacklistStatus(Boolean blacklistStatus) {
        this.blacklistStatus = blacklistStatus;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
