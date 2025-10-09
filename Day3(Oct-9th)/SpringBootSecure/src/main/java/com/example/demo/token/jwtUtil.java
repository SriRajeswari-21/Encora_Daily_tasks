package com.example.demo.token;

import java.awt.RenderingHints.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class jwtUtil {
	
	private static final String SECRET_KEY="myverysecretkeymyverysecretkey12345";
	private static final long expiration=1000*60*60;
	private final java.security.Key key=Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+expiration)).signWith(key,SignatureAlgorithm.HS256).compact();
	}
	
	public String extractUsername(String Token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(Token).getBody().getSubject();
	}
	public boolean ValidateToken(String Token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(Token);
			return true;
		}catch(JwtException e) {
			return false;
		}
	}

}
