package com.example.Pacientes.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

    public static String generarToken(String username) {
        return Jwts.builder()//inica la contruccion
                .setSubject(username)
                .setIssuedAt(new Date())//guarda fecha
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String validarToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)//verifica la firma
                .build()
                .parseClaimsJws(token)//decodifica y valida el token
                .getBody()
                .getSubject();
    }
}

