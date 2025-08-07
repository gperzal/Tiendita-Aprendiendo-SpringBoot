package com.bootcamp.security.config;

import com.bootcamp.security.jwt.JwtAuthenticationFilter;
import com.bootcamp.security.service.UsuarioDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final UsuarioDetailsServiceImpl userDetailsService;

    // 🔹 Se puede cambiar en application.properties
    @Value("${app.security.cors.dev:true}")
    private boolean devCorsEnabled;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        // 🔹 Si estamos en modo dev, deshabilitamos CORS (Postman libre)
        // 🔹 Si no, habilitamos CORS para React u orígenes específicos
        if (devCorsEnabled) {
            http.cors(AbstractHttpConfigurer::disable);
        } else {
            http.cors(cors -> cors.configurationSource(request -> {
                var corsConfig = new CorsConfiguration();
                corsConfig.setAllowedOrigins(List.of("http://localhost:5173")); // React Dev
                corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                corsConfig.setAllowedHeaders(List.of("*"));
                corsConfig.setAllowCredentials(true);
                return corsConfig;
            }));
        }

        return http
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/productos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasAuthority("ROLE_ADMIN")

                        // Endpoints protegidos
                        .requestMatchers("/api/clientes/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")

                        // Resto requiere autenticación
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
