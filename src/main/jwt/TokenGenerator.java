package global.citytech.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenGenerator {
    public String generateToken(String username, String id){
        Map<String,Object> claims = getClaims(username,id);
        Calendar calendar = Calendar.getInstance();
        Date iat = new Date();
        Date eat = new Date(calendar.getTimeInMillis()+(60*10*1000));
        return Jwts.builder().setClaims(claims).setIssuedAt(iat).setExpiration(eat).signWith(getKey()).compact();
    }

    public SecretKey getKey(){
        SecretKey secret = Keys.hmacShaKeyFor("This is a long @ string containgajasu21-01-1212".getBytes(StandardCharsets.UTF_8));
        return secret;
    }
    public Map<String,Object> getClaims(String username, String id){
        Map<String,Object> claims = new HashMap<>();
        claims.put("issuer",username);
        claims.put("id",id);
        return claims;
    }
}
