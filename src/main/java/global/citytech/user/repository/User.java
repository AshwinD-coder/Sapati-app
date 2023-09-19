package global.citytech.user.repository;

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
//
    @Column(name = "is_verified")
    private Boolean verifyStatus;
//
//    @Column(name = "is_blacklist")
//    private String blacklistStatus;
//    private String accountStatus;
//    private String verifiedBy;

    public User(Long userId, String username, String password, String email, String phoneNumber, UserType userType,Timestamp createdAt, Boolean verifyStatus) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.createdAt = createdAt;
        this.verifyStatus = verifyStatus;
//        this.blacklistStatus = blacklistStatus;
//        this.accountStatus = accountStatus;
//        this.verifiedBy = verifiedBy;
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
//    public String getBlacklistStatus() {
//        return blacklistStatus;
//    }
//
//    public void setBlacklistStatus(String blacklistStatus) {
//        this.blacklistStatus = blacklistStatus;
//    }
//
//    public String getAccountStatus() {
//        return accountStatus;
//    }
//
//    public void setAccountStatus(String accountStatus) {
//        this.accountStatus = accountStatus;
//    }
//
//    public String getVerifiedBy() {
//        return verifiedBy;
//    }
//
//    public void setVerifiedBy(String verifiedBy) {
//        this.verifiedBy = verifiedBy;
//    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", username='" + username + '\'' + ", email='" + email + '\'' + ", phoneNumber='" + phoneNumber + '\'' + '}';
    }
}
