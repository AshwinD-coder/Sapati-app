package global.citytech.platform.security;

import global.citytech.user.repository.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Singleton
public class SecurityUtils {
    @Inject
    public SecurityUtils() {
    }

    public static String token(User user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);

        String jwtToken = Jwts.builder()
                .claim("username", user.getUsername())
                .claim("role", user.getUserType())
                .signWith(getKey())
                .setIssuedAt(new Date())
                .setExpiration(calendar.getTime())
                .compact();

        return jwtToken;
    }

    public Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody();
    }

    public static Key getKey() {
        String jwtSignature = "9f1ba31f-86b8-42aa-9449-1eed715b464d";
        return Keys.hmacShaKeyFor(jwtSignature.getBytes(StandardCharsets.UTF_8));
    }

    public RequestContext parseTokenAndGetContext(String token) {
        try {
            Claims claims = this.parseToken(token);
            RequestContext requestContext = new RequestContext();
            requestContext.setUsername(claims.get("username", String.class));
            requestContext.setUserType(claims.get("role", String.class));
            return requestContext;

        } catch (Exception exception) {
            if (exception instanceof ExpiredJwtException)
                throw new IllegalArgumentException("Security Token is expired");
            throw new IllegalArgumentException("Security Token is invalid");
        }
    }

}