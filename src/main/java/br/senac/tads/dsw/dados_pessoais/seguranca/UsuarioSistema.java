
package br.senac.tads.dsw.dados_pessoais.seguranca;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;        
import com.fasterxml.jackson.annotation.JsonProperty;        
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// Representa um usuário no contexto do Spring Security.
// UserDetails é a interface padrão que o framework usa para carregar e inspecionar usuários. 
// Ao implementá-la, esta classe "fala a língua" do Spring Security.
@JsonPropertyOrder({"username", "senhaCodificada", "roles"})
public class UsuarioSistema implements UserDetails {
    private final String username;
    private final String password; // Senha já codificada pelo PasswordEncoder
    private final List<GrantedAuthority> authorities; // Permissões do usuário (ex: ROLE_ADMIN)

    public UsuarioSistema(String username, String password, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonProperty("senhaCodificada")
    public String getPassword() {
        return password;
    }

    // getAuthorities() retorna a lista de permissões (roles) do usuário.
    // GrantedAuthority é uma interface; SimpleGrantedAuthority é a implementação
    // padrão.
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // NOVO: getter customizado que devolve só os textos → ["ROLE_ADMIN", ...]
    @JsonProperty("roles")
    public List<String> getRoles() {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    // Os quatro métodos abaixo controlam o estado da conta.
    // Retornam true por simplicidade - em produção você implementaria lógica de
    // bloqueio,
    // expiração de senha, etc., consultando um banco de dados.
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
