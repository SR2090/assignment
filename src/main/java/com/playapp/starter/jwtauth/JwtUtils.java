package com.playapp.starter.jwtauth;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.playapp.starter.service.UserDetailsImplementation;
import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// generate JWT from username, date, expiration and secret, get username, and validate JWT
@Component
public class JwtUtils{
    private static final Logger loggerJwtUtils = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${playyo.app.jwtSecret}")
    private String jwtSecret;

    @Value("${playyo.app.jwtExpirationMs}")
    private long jwtExpirationInMs;

    // generate the jwt token
    public String generateJwtToken(Authentication authentication) {

        // get the current logged in user
		UserDetailsImplementation principleUser = (UserDetailsImplementation) authentication.getPrincipal();

        // till what time will the token remain valid
		return Jwts.builder()
				.setSubject((principleUser.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationInMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

    public String getUserNameFromJWTToken(String token){
        // need to understand what this chain is doing
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			loggerJwtUtils.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			loggerJwtUtils.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			loggerJwtUtils.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			loggerJwtUtils.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			loggerJwtUtils.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}   