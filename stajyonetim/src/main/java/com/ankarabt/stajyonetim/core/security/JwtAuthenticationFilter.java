package com.ankarabt.stajyonetim.core.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //İsteğin başlığındaki "Authorization"ı al
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String eposta;

        //Filtreyi atla login olabilir
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //Bearer'ı kes
        jwt = authHeader.substring(7);

        try {
            //tokenden epostayı çek
            eposta = jwtService.epostaCikar(jwt);

            if (eposta != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                String rol = jwtService.rolCikar(jwt);

                //Authentication Token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        eposta,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority(rol))
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Onay
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
        }

        filterChain.doFilter(request, response);
    }
}