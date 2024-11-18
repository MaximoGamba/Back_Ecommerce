package com.ecomerce.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static org.springframework.security.config.Customizer.withDefaults;
import com.ecomerce.demo.Clases.Role;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .cors(withDefaults())
                                .authorizeHttpRequests(req -> req.requestMatchers("usuarios/**").permitAll()
                                                .requestMatchers("/productos/**").hasAnyAuthority(Role.USER.name())
                                                .requestMatchers("/categorias/ObtenerCategorias").permitAll()
                                                .requestMatchers("/categorias/filtro").hasAnyAuthority(Role.USER.name())
                                                .requestMatchers("/categorias/CrearCat").hasAnyAuthority(Role.ADMIN.name())
                                                .requestMatchers("/carrito/**").hasAnyAuthority(Role.USER.name())
                                                .requestMatchers("/productosUser/**").hasAnyAuthority(Role.USER.name())
                                                .requestMatchers("/productosAdmin/**").hasAnyAuthority(Role.ADMIN.name())
                                                .requestMatchers("/pedidos/realizar").hasAnyAuthority(Role.USER.name())
                                                .requestMatchers("/pedidos/ObtenerTodosUser").hasAnyAuthority(Role.USER.name())
                                                .requestMatchers("/pedidos/ObtenerTodosAdmin").hasAnyAuthority(Role.ADMIN.name())
                                                .anyRequest()
                                                .authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
                configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                configuration.setAllowCredentials(true);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
        return source;
        }
}