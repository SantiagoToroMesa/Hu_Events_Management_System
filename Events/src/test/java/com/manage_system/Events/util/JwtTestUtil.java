package com.manage_system.Events.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class JwtTestUtil {

    private static final String SECRET = "mi_clave_super_secreta_para_tests_123456789"; // No importa, solo tests

    public static String generateAdminToken() {
        return Jwts.builder()
                .setSubject("adminUser")
                .claim("roles", "ADMIN")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String getTestSecretKey() {
        return SECRET;
    }
}
