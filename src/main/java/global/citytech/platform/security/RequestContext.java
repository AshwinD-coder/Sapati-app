package global.citytech.platform.security;

public class RequestContext {
    private String username;

    private String userType;


    public RequestContext() {}

    public RequestContext(String username, String userType) {
        this.username = username;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "RequestContext{" +
                "username='" + username + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}