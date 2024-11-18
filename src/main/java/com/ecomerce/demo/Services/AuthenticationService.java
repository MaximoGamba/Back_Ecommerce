package com.ecomerce.demo.Services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ecomerce.demo.Clases.Usuarios;
import com.ecomerce.demo.Config.JwtService;
import com.ecomerce.demo.Exceptions.EmailAlreadyExistsException;
import com.ecomerce.demo.Repositorys.UsuariosRepository;
import com.ecomerce.demo.Request.AuthenticationRequest;
import com.ecomerce.demo.Request.RegisterRequest;
import com.ecomerce.demo.Response.AuthenticationResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
        private final UsuariosRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) throws EmailAlreadyExistsException{
                if (!repository.existsByEmail(request.getEmail())) {
                        var user = Usuarios.builder()
                                .firstName(request.getFirstname())
                                .lastName(request.getLastname())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .rol(request.getRole())
                                .build();

                repository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
                    }
                throw new EmailAlreadyExistsException();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));
                var user = repository.findByEmail(request.getEmail())
                                .orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .roles(user.getRol())
                                .build();
        }

        public Usuarios obtenerUsuarioAutenticado() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return (Usuarios) authentication.getPrincipal();
        }
}
