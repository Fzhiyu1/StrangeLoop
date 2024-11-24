package com.xiaofeng.strangeloop.springframework;

import com.xiaofeng.strangeloop.system.domain.SysUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private static final SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512); // 生成安全的密钥

    public static String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 10800000)) // 1小时有效期
                .signWith(KEY) // 使用安全密钥签名
                .compact();
    }

//    public static String validateToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(SECRET)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
}
