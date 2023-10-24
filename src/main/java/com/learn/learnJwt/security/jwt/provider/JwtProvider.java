package com.learn.learnJwt.security.jwt.provider;

import com.learn.learnJwt.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
    @Value("${jwt.secretKey}")
    private String secretKeyString;
    public final static long validityInMilliseconds = 3600000L; // 토큰 유효 시간 (1시간)

    public String createToken(User user) {
        final Claims claims = Jwts.claims().setSubject(user.getLoginId());
        claims.put("roles", user.getRoles());

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        ZonedDateTime validity = now.plus(validityInMilliseconds, ChronoUnit.MILLIS);

        SecretKey secretKey = Keys.hmacShaKeyFor(getSecretKeyStringToEncodingUTF8Bytes());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(validity.toInstant()))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        final Jws<Claims> claimsJws = getClaimsJws(token);
        final Claims claimsJwsBody = claimsJws.getBody();
        return true;
    }

    public String getLoginIdFromToken(String token) {
        final Jws<Claims> claimsJws = getClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }

    public boolean isTokenExpiringSoon(String token) {
        try {
            final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(getSecretKeyStringToEncodingUTF8Bytes()).build();
            final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
            final Date expiration = claimsJws.getBody().getExpiration();
            final Date now = new Date();

            final long differenceInMilliseconds = expiration.getTime() - now.getTime();
            final long differenceInMinutes = differenceInMilliseconds / (1000 * 60);

            if (differenceInMinutes < 10) {
                return true;
            }
        } catch (ClaimJwtException e) {
            return false;
        }
        return false;
    }

    private Jws<Claims> getClaimsJws(String token) throws ExpiredJwtException, MalformedJwtException, SignatureException {
        final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(getSecretKeyStringToEncodingUTF8Bytes()).build();
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        return claimsJws;
    }

    private byte[] getSecretKeyStringToEncodingUTF8Bytes() {
        return secretKeyString.getBytes(StandardCharsets.UTF_8);
    }
}
