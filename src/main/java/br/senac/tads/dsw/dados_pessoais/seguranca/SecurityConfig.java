package br.senac.tads.dsw.dados_pessoais.seguranca;

import java.util.List;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Define a cadeia de filtros HTTP que o Spring Security aplica em cada
    // requisição.
    // A ordem das regras em authorizeHttpRequests importa: a primeira regra que
    // casar é aplicada.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF - não é necessário em APIS REST stateless
                // (CSRF protege formulários HTML com sessão; aqui usaremos JWT por requisição)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())

                // Permite renderização em frames - necessário para o console H2 funcionar no
                // navegador
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()))

                // Define a política de sessão como STATELESS: o servidor não cria nem usa
                // sessão HTTP.
                // Cada requisição deve ser autenticada de forma independente (via JWT no
                // header).
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Regras de autorização por URL ----
                .authorizeHttpRequests(auth -> auth
                        // Endpoint de login: sempre público (é aquí que o usuário obtém o token)
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        // Console H2: público durante o desenvolvimento (REMOVA em produção!)
                        .requestMatchers("/h2-console/**").permitAll()
                        // Swagger UI: público para facilitar testes
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
                        .permitAll()
                        // Demais endpoints precisam de autenticação - MANTER SEMPRE COMO ÚLTIMA
                        // CONFIGURAÇÃO
                        .anyRequest().authenticated());
        return http.build();
    }

    // AuthenticationManager: componente responsável por executar a autenticação.
    // DaoAuthenticationProvider é a implementação que usa um UserDetailsService
    // (nosso
    // UsuarioSistemaService) para buscar o usuário e um PasswordEncoder para
    // comparar a senha.
    // OBS: Esse código abaixo é válido para o Spring Boot 4 em diante.
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    // PasswordEncoder: define como as senhas são codificadas e comparadas.
    // ATENÇÃO: NoOpPasswordEncoder armazena senhas em TEXTO PURO.
    // Use APENAS para fins didáticos - será substituído por BCrypt na Etapa 7.
    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Origens permitidas - substitua pela URL real do frontend em produção
        config.setAllowedOrigins(List.of("http://localhost:3000", "https://meu-frontend.com"));

        // Métodos HTTP que o cliente pode usar
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Headers que o cliente pode enviar - "Authorization" é necessário para o
        // Bearer token
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // aplica a todos os caminhos
        return source;
    }

}
