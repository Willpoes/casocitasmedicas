package com.example.Pacientes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // desactiva CSRF porque usas JWT (stateless)
                .authorizeHttpRequests()
                .requestMatchers("/auth/login").permitAll() // cualquiera puede loguearse
                .requestMatchers("/pacientes/{id}").permitAll() // endpoint público
                .anyRequest().authenticated() //todolo demas requiere autenticación
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // no hay sesiones en servidor, cada request debe traer su JWT

        // agrega tu filtro JWT antes del filtro estándar de login por username/password
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}

