package com.bikkadit.electoronic.store.security;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

    // 1.Generate Token
    // 2.Validate Token
    //3.Get UserName From Token

    @Value("${jwt.secret}")
    private String secret;

    // retrieve username from jwtToken
    public String getUsernamefromToken(String token) {

        return getClaimFromToken(token, Claims::getSubject);

    }

    // retrieve Expiration date from token
    public Date getExpirationDateFromToken(String token) {

        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {

        final Claims claims = getAllClaimsfromToken(token);

        return claimResolver.apply(claims);

    }

    public Claims getAllClaimsfromToken(String token) {

        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // check if token is expired
    public Boolean isTokenExpired(String token) {

        final Date expiration = getExpirationDateFromToken(token);

        return expiration.before(new Date());
    }

    // generate token for user

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();

        return doGenerateToken(claims, userDetails.getUsername());
    }


    // Here We Provide Algorithm
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + AppConstants.JWT_TOKEN_VALIDITY * 100))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    public Boolean validateToken(String token, UserDetails userDetails) {

        final String username = getUsernamefromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }
}



