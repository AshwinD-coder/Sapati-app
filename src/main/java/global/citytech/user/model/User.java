package global.citytech.user.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

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



//    @CreationTimestamp
//    private Timestamp createdAt;
////
//    @Column(name = "user_type")
//    private String userType;
//
//    private Long roleId;
//    @Column(name = "is_verified")
//    private String verifyStatus;
//
//    @Column(name = "is_blacklist")
//    private String blacklistStatus;
//    private String accountStatus;
//    private String verifiedBy;

    public User(Long userId, String username, String password, String email, String phoneNumber,UserType userType) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
//        this.dateOfBirth = dateOfBirth;

//        this.createdAt = createdAt;
//        this.userType = userType;
//        this.roleId = roleId;
//        this.verifyStatus = verifyStatus;
//        this.blacklistStatus = blacklistStatus;
//        this.accountStatus = accountStatus;
//        this.verifiedBy = verifiedBy;
    }
    public User(){

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
    //    public Date getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(Date dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }
//
//    public Timestamp getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Timestamp createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getUserType() {
//        return userType;
//    }
//
//    public void setUserType(String userType) {
//        this.userType = userType;
//    }
//
//    public Long getRoleId() {
//        return roleId;
//    }
//
//    public void setRoleId(Long roleId) {
//        this.roleId = roleId;
//    }
//
//    public String getVerifyStatus() {
//        return verifyStatus;
//    }
//
//    public void setVerifyStatus(String verifyStatus) {
//        this.verifyStatus = verifyStatus;
//    }
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
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
