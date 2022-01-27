package com.internal.service.somruinternal.utils;

import com.internal.service.somruinternal.model2.UserV2;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

@Component
public class JwtTokenUtilV2 implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 3600; // 1h
    public static final String JWT_TOKEN_SECRET = "jf9i4jgu83nfl0jfu57ejf7";
    public static final String Object = null;

    private final static Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtilV2.class);

    // Get username from JWT token
    public String getUsername(String token) {
        LOGGER.info(String.format("getUsername called with token =", token ));
        String userName = getAllClaims(token, Claims::getSubject);
        LOGGER.info(String.format("Claims::getSubject result =", userName ));
        return userName;
    }

    // Get expiration date from JWT token
    public Date getExpirationDate(String token) {
        LOGGER.info(String.format("getExpirationDate called with token =", token ));
        Date expiration = getAllClaims(token, Claims::getExpiration);
        LOGGER.info(String.format("Claims::getExpiration result =", expiration ));
        return expiration;
    }

    // Use secret key to get all claim information from token
    public <T> T getAllClaims(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(
                Jwts.parser().setSigningKey(JWT_TOKEN_SECRET).parseClaimsJws(token).getBody()
        );
    }

    // Check if token has expired
    private Boolean isExpired(String token) {
        LOGGER.info(String.format("isExpired called with token =", token ));
        Boolean expired = getExpirationDate(token).before(new Date());
        LOGGER.info(String.format("expired result =", expired ));
        return getExpirationDate(token).before(new Date());
    }

    // Generate a user token
    public String generate(UserV2 user) {
        return Jwts.builder()
                .setSubject(user.getName())
                .claim("userID", user.getUserId())
                .claim("userPassword", user.getPassword())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, JWT_TOKEN_SECRET)
                .compact();
    }

    // Decode a user token
    public Claims decode(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(JWT_TOKEN_SECRET)
                    .parseClaimsJws(token).getBody();
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported JWT exception");
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty");
        }
        return null;
    }
}
