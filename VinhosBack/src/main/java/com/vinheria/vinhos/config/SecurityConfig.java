package com.vinheria.vinhos.config;

import com.vinheria.vinhos.security.JwtFilter;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity          // habilita @PreAuthorize, etc.
public class SecurityConfig {

    /* === Beans de suporte ================================================= */

    /* PasswordEncoder usado no UserDetailsService e no AuthController */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* AuthenticationManager injetado no AuthController */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /* === Configuração da cadeia de filtros =============================== */

   @Bean
public SecurityFilterChain filterChain(HttpSecurity http,
                                       JwtFilter jwtFilter) throws Exception {

    return http
        /* NOVA LINHA ①  ──────────────── */
        .securityMatcher("/**")                         // aplica a tudo
        /* ───────────────────────────── */

        .cors(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable())
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/auth/**",
                "/swagger-ui/**",
                 "/usuarios/register",
                "/v3/api-docs/**").permitAll()
            .requestMatchers("/vinhos/**").permitAll()  // catálogo público
            .anyRequest().authenticated()
        )
        /* ALTERADA ②  ──────────────── */
        .addFilterBefore(jwtFilter,                    // seu filtro
                         UsernamePasswordAuthenticationFilter.class)
        /*  se preferir limitar o filtro
            .addFilterBefore(new JwtFilter("/api/**"),
                         UsernamePasswordAuthenticationFilter.class)   */
        /* ───────────────────────────── */
        .build();
}

    /* === CORS global ====================================================== */

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("http://localhost:5173"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("Authorization","Content-Type"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cfg);
        return src;
    }
}
