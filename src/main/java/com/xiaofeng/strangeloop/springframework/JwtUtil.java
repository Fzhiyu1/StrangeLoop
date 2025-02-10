package com.xiaofeng.strangeloop.springframework;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    // 生成的安全密钥
    private static final SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // 生成 JWT Token
    public static String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 8小时有效期
                .signWith(KEY) // 使用密钥签名
                .compact();
    }

    // 从 Token 中提取用户名
    public static String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(KEY) // 使用密钥而不是错误的 `SECRET_KEY`
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 验证 Token 是否有效
    public static boolean validateToken(String token, String account) {
        try {
            String username = getUsernameFromToken(token);
            return username.equals(account) && !isTokenExpired(token);
        } catch (JwtException e) {
            return false;  // 如果 Token 解析失败或已过期，返回 false
        }
    }

    public static boolean isTokenExpired(String token) {
        try {
            // 使用 parserBuilder 解析 token
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(KEY)  // 使用密钥而不是错误的 SECRET_KEY
                    .build()
                    .parseClaimsJws(token)  // 解析 token
                    .getBody()
                    .getExpiration();

            return expiration.before(new Date()); // 判断是否过期
        } catch (JwtException | IllegalArgumentException e) {
            // 处理解析异常（例如 token 不合法、过期等）
            return true;
        }
    }

}
