package global.citytech.platform.security;

import global.citytech.platform.common.enums.UserType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtGenerator {

    public static String generateToken(String username, UserType userType){
        Date issuedAt = new Date();
        Calendar calendar = Calendar.getInstance();
        Date expiresAt = new Date(calendar.getTimeInMillis()+(10*60*1000));
        Map<String,Object> claims = new HashMap<>();
        claims.put("Issuer",username);
        claims.put("Role",userType);
        String token = Jwts.builder().setClaims(claims).setIssuedAt(issuedAt).setExpiration(expiresAt).signWith(getKey()).compact();
        return token;
    }

    public static SecretKey getKey(){
        String secret = "S3Cr3t1sTheS3cr3t+0My3nergyh1h1h1@L01";
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

}
