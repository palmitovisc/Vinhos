package com.vinheria.vinhos.security;

import com.vinheria.vinhos.services.UsuarioDetailsService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;                        //  ← aqui
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired private JwtProvider jwt;
    @Autowired private UsuarioDetailsService uds;

    /* ───── rotas que NÃO exigem JWT ───── */
    private static final List<String> PUBLIC = List.of(
    "/auth/",
    "/swagger-ui/",          // HTML, JS, CSS gerados pelo springdoc
    "/swagger-ui.html",      // acesso clássico
    "/swagger-resources/",   // configuração interna
    "/v3/api-docs",          // JSON OpenAPI
    "/v3/api-docs/",         // versões com path extra
    "/webjars/",             // libs JS/CSS empacotadas
    "/vinhos/"               // catálogo público (se quiser)
);
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,   // ←
                                    @NonNull HttpServletResponse res,  // ←
                                    @NonNull FilterChain chain)        // ←
                                    throws ServletException, java.io.IOException {
                                        String path1 = req.getRequestURI();   //  ← sem isso dá o erro “path cannot be resolved”

    // ► se rota pública → pula validação
    if (PUBLIC.stream().anyMatch(path1::startsWith)) {
        chain.doFilter(req, res);
        return;
}

        String path = req.getRequestURI();

        // ► se rota pública → pula validação
        if (PUBLIC.stream().anyMatch(path::startsWith)) {
            chain.doFilter(req, res);
            return;
        }

        /* ─── valida JWT apenas nas rotas protegidas ─── */
        String auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                String email = jwt.extrairEmail(token);
                UserDetails user = uds.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authTok =
                        new UsernamePasswordAuthenticationToken(
                                user, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authTok);
            } catch (Exception ignored) { /* token inválido → sem autenticação */ }
        }

        chain.doFilter(req, res);
    }
}