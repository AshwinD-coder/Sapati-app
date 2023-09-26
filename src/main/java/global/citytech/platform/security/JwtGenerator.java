package global.citytech.platform.security;

import global.citytech.platform.common.enums.UserType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtGenerator {

    public static String generateToken(String username, UserType userType) {
        Date issuedAt = new Date();
        Calendar calendar = Calendar.getInstance();
        Date expiresAt = new Date(calendar.getTimeInMillis() + (10 * 60 * 1000));
        Map<String, Object> claims = new HashMap<>();
        Map<String, Object> header = new HashMap<>();
        claims.put("issuer", username);
        claims.put("userRole", userType);
        header.put("type", "jwt");
        return Jwts.builder().setClaims(claims).setIssuedAt(issuedAt).setExpiration(expiresAt).signWith(getKey()).setHeader(header).compact();
    }

    public static SecretKey getKey() {
        String secret = "S3Cr3t1sT#eS3cr3t+0My3nergy#1#1#1@L01";
        String encodedString = Encoders.BASE64.encode(secret.getBytes());
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedString));

    }

}
