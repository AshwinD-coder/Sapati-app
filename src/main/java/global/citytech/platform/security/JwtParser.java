package global.citytech.platform.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtParser {
    private JwtParser(){}
    public static void parseToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(JwtGenerator.getKey()).build().parse(token);
        }
        catch (JwtException e ){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
