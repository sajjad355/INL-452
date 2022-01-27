package com.internal.service.somruinternal.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtil {
  @Value("${user.session_expire_minute}")
  private String session_expire_minute_env;

	private final String SECRET_KEY = "somru-pirates";


	boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername(), null);
	}

	private String createToken(Map<String, Object> claims, String username, String expireMinute) {
	  Long sessionExpireMinute = (expireMinute != null) ? Long.parseLong(expireMinute) : Long.parseLong(session_expire_minute_env);

    System.out.println(sessionExpireMinute + " debug message");
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * sessionExpireMinute)))
      .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public String createSignupToken(String username) {
		Map<String, Object> claims = new HashMap<>();
    int sessionExpireMinute = Integer.parseInt(session_expire_minute_env);

		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * sessionExpireMinute)))
      .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
