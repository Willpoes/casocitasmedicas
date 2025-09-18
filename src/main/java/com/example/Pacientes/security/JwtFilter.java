package com.example.Pacientes.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Override//define q hacer en cada peticion
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization"); //obtiene cabecera

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);//corta la palabray se queda con JWT
            try {
                String username = JwtUtil.validarToken(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {//verifica si no esta autenticado
                    UsernamePasswordAuthenticationToken authToken =//objeto autenticacion
                            new UsernamePasswordAuthenticationToken(username, null, null);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);//guarda suario auteticaso
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                return;
            }
        }
        //el reques siga su camino
        chain.doFilter(request, response);
    }
}
